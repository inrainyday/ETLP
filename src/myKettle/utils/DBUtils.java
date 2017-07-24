package myKettle.utils;



import myKettle.model.DBMessage;

import java.sql.*;
import java.util.*;

/**
 * Created by zhangzhimin on 6/15/17.
 */
public class DBUtils {
    public Connection getConnection(){
        Properties properties = new Properties();
        Connection connection = null;
        try {
            System.out.println(this.getClass().getResource("/").getPath());
            properties.load(this.getClass().getResourceAsStream("/db.properties"));
            String driver = properties.getProperty("driver");
            String url = properties.getProperty("url");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            Class.forName(driver);
            connection = DriverManager.getConnection(url,username,password);


        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }
    public String query(Connection conn,String sql) throws Exception{
        conn.prepareStatement(sql);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        String data = convertList2(rs).get(0).toString();
        System.out.println(data);
        return data;
    }
    public List queryList(Connection conn,String sql) throws Exception{
        conn.prepareStatement(sql);
        System.out.println(sql);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List data = convertList4(rs);
//        for(int i = 0; i < data.size(); i++){
//            Map map = (Map) data.get(i);
//            System.out.println(map.get("password"));
//        }
        return data;
    }
    public int updateOne(Connection conn,String sql,Object[] args) throws Exception{
        conn.prepareStatement(sql);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        for (int i =0;i<args.length;i++){
            pstmt.setObject(i+1,args[i]);
        }
        int rs = pstmt.executeUpdate();
        return rs;
    }
    public int deleteOne(Connection conn,String sql) throws Exception{
        conn.prepareStatement(sql);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        int rs = pstmt.executeUpdate();
        return rs;
    }
    public int insertOne(Connection conn,String sql,Object[] args) throws Exception{
        conn.prepareStatement(sql);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        for (int i =0;i<args.length;i++){
            pstmt.setObject(i+1,args[i]);
        }
        int rs = pstmt.executeUpdate();
        return rs;
    }
    private static List convertList(ResultSet rs) throws SQLException{
        List list = new ArrayList();
        ResultSetMetaData md = rs.getMetaData();//获取键名
        int columnCount = md.getColumnCount();//获取行的数量
        while (rs.next()) {
            Map rowData = new HashMap();//声明Map
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i));//获取键名及值
            }
            list.add(rowData);
        }
        return list;
    }
    private static List convertList2(ResultSet rs) throws SQLException{
        List list = new ArrayList();
        ResultSetMetaData md = rs.getMetaData();//获取键名
        int columnCount = md.getColumnCount();//获取行的数量
        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                list.add(rs.getObject(i));
            }
        }
        return list;
    }
    private static List convertList3(ResultSet rs) throws SQLException{
        List list = new ArrayList();
        ResultSetMetaData md = rs.getMetaData();//获取键名
        int columnCount = md.getColumnCount();//获取行的数量
        List temp = null;
        while (rs.next()) {
            temp = new ArrayList();
            for (int i = 1; i <= columnCount; i++) {
                temp.add(rs.getObject(i));
            }
            list.add(temp);
        }
        return list;
    }
    private static List convertList4(ResultSet rs) throws SQLException{
        List list = new ArrayList();
        ResultSetMetaData md = rs.getMetaData();//获取键名
        int columnCount = md.getColumnCount();//获取col的数量
        Map temp = null;
        while (rs.next()) {
            temp = new HashMap();
            for (int i = 1; i <= columnCount; i++) {
                temp.put(md.getColumnName(i),rs.getObject(i));
            }
            list.add(temp);
        }
        return list;
    }
    public static void main(String[] args1) {
        DBUtils dbUtils = new DBUtils();
        Connection conn = dbUtils.getConnection();
        try{
            Object[] args = new Object[9];
            conn = dbUtils.getConnection();
            String sql = "update dbs set name = ?,ip=?,db_name=?,table_name=?,type=?,user=?,password=?,owner_id=? " +
                    "where db_id = ?";
            DBMessage dbMessage = new DBMessage();
            dbMessage.setDb_id(5);
            dbMessage.setDb_name("2121");

            System.out.println(dbMessage.toString());
            //int result = dbUtils.updateOne(conn,sql,args);
//
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
