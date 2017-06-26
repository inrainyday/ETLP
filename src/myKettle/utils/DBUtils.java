package myKettle.utils;



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
    public static void main(String[] args) {
        DBUtils dbUtils = new DBUtils();
        Connection conn = dbUtils.getConnection();
        try{
            dbUtils.query(conn,"select body from job where job_id = 8");
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
