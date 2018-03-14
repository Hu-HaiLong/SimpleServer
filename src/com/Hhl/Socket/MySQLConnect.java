package com.Hhl.Socket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnect {
	
 
    public static void getAll() {
        Connection conn = getConn();
        Statement stmt = null;
        try{
        
            // 执行查询
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM ysm_accountinfos";
            ResultSet rs = stmt.executeQuery(sql);
        
            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索
                int id  = rs.getInt("id");
                String name = rs.getString("serverAccount");
                String pwd = rs.getString("serverPwd");
    
                // 输出数据
                System.out.print("ID: " + id);
                System.out.print(", 服务端账户: " + name);
                System.out.print(", 服务端密码: " + pwd);
                System.out.print("\n");
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }
    
    private static int update(UserAccount user) {
        Connection conn = getConn();
        Statement stmt = null;
        
        // 执行查询
        try {
			stmt = conn.createStatement();
	        String sql;
	        sql = "SELECT * FROM ysm_accountinfos";
			ResultSet rs = stmt.executeQuery(sql);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        int i = 0;
        //String sql = //"update ysm_accountinfos set Age='" + user.ServerAccount + "' where Name='" + user.ServerAccount + "'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            System.out.println("resutl: " + i);
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
    
    private static Connection getConn() {
    	// JDBC 驱动名及数据库 URL
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
        final String DB_URL = "jdbc:mysql://localhost:3306/ysm01db?useUnicode=true&characterEncoding=utf-8&useSSL=false";
     
        // 数据库的用户名与密码，需要根据自己的设置
        final String USER = "root";
        final String PASS = "555555";
        Connection conn = null;
        try {
            Class.forName(JDBC_DRIVER); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
