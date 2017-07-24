package myKettle.controller;


import myKettle.model.DBMessage;
import myKettle.utils.DBUtils;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/db")
public class DBConnectionJudge {
    /**
     * 测试数据库连接信息是否正确
     * @param dbMessage
     * @param modelMap
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "DBConnection", method = {RequestMethod.POST, RequestMethod.GET})
    public void run(DBMessage dbMessage, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{

        String ip = dbMessage.getIp();
        String db_name = dbMessage.getDb_name();
        String type = dbMessage.getType();
        String user = dbMessage.getUser();
        String password = dbMessage.getPassword();
        Connection connection = null;
        try {
            String driver ="";
            String url = "";
            if("mysql".equals(type.trim())){
                driver = "com.mysql.jdbc.Driver";
                url = "jdbc:mysql://"+ip+"/"+db_name;
            }else if("oracle".equals(type.trim())){
                driver = "oracle.jdbc.driver.OracleDriver";
                url = "jdbc:oracle:thin:@"+ip+":"+db_name;
            }

            Class.forName(driver);
            connection = DriverManager.getConnection(url,user,password);
            if(connection != null){
                response.getWriter().write("true");
            }
        }catch (Exception e){
                response.getWriter().write("false");
        }finally {
            connection.close();;
        }
    }
    @RequestMapping(value = "getDBMsgByUserId", method = {RequestMethod.POST, RequestMethod.GET})
    public void getDBMsgByUserId(String userId, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
        List list = getDBMsgListByUserId(userId);
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getJsonFactory();
        JsonGenerator responseJsonGenerator = factory.createJsonGenerator(response.getOutputStream(), JsonEncoding.UTF8);
        responseJsonGenerator.writeObject(list);
    }
    @RequestMapping(value = "updateDBMsg", method = {RequestMethod.POST, RequestMethod.GET})
    public void updateDBMsg(DBMessage dbMessage, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Object[] args = new Object[10];
        Connection conn = null;
        DBUtils dbUtils = new DBUtils();
        try {
            conn = dbUtils.getConnection();
            String sql = "update dbs set name = ?,ip=?,db_name=?,table_name=?,type=?,user=?,password=?,owner_id=?,description=? " +
                    "where db_id = ?";
            args[0] = dbMessage.getName();
            args[1] = dbMessage.getIp();
            args[2] = dbMessage.getDb_name();
            args[3] = dbMessage.getTable_name();
            args[4] = dbMessage.getType();
            args[5] = dbMessage.getUser();
            args[6] = dbMessage.getPassword();
            args[7] = dbMessage.getOwner_id();
            args[8] = dbMessage.getDescription();
            args[9] = dbMessage.getDb_id();
            int result = dbUtils.updateOne(conn,sql,args);
            response.getWriter().print(result);
        }catch (Exception e){
            response.getWriter().write("false");
        }finally {
            conn.close();;
        }
    }
    @RequestMapping(value = "insertDBMsg", method = {RequestMethod.POST, RequestMethod.GET})
    public void insertDBMsg(DBMessage dbMessage, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
        System.out.println(dbMessage.toString());
        Object[] args = new Object[9];
        Connection conn = null;
        DBUtils dbUtils = new DBUtils();
        try {
            conn = dbUtils.getConnection();
            String sql = "insert into dbs (name,ip,db_name,table_name,type,user,password,owner_id,description) values (?,?,?,?,?,?,?,?,?)";
            args[0] = dbMessage.getName();
            args[1] = dbMessage.getIp();
            args[2] = dbMessage.getDb_name();
            args[3] = dbMessage.getTable_name();
            args[4] = dbMessage.getType();
            args[5] = dbMessage.getUser();
            args[6] = dbMessage.getPassword();
            args[7] = dbMessage.getOwner_id();
            args[8] = dbMessage.getDescription();
            int result = dbUtils.insertOne(conn,sql,args);
            response.getWriter().print(result);
        }catch (Exception e){
            response.getWriter().write("false");
        }finally {
            conn.close();;
        }
    }
    @RequestMapping(value = "deleteDBMsgByDBId", method = {RequestMethod.POST, RequestMethod.GET})
    public void deleteDBMsgByDBId(int db_id, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{

        Connection conn = null;
        DBUtils dbUtils = new DBUtils();
        try {
            conn = dbUtils.getConnection();
            String sql = "delete from dbs where db_id = "+db_id;
            int result = dbUtils.deleteOne(conn,sql);
            response.getWriter().print(result);
        }catch (Exception e){
            response.getWriter().write("false");
        }finally {
            conn.close();;
        }
    }
    public List getDBMsgListByUserId(String userId) throws SQLException {
        DBUtils dbUtils = new DBUtils();
        Connection connection = null;
        try {
            connection = dbUtils.getConnection();
            List list = dbUtils.queryList(connection,"select db_id,name,ip,db_name,table_name,type,user,password,owner_id " +
                    "from dbs where owner_id = "+userId);
            return list;
        }catch (Exception e){
            return null;
        }finally {
            connection.close();
        }
    }
}
