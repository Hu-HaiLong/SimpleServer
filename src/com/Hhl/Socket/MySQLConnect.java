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
        
            // ִ�в�ѯ
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM ysm_accountinfos";
            ResultSet rs = stmt.executeQuery(sql);
        
            // չ����������ݿ�
            while(rs.next()){
                // ͨ���ֶμ���
                int id  = rs.getInt("id");
                String name = rs.getString("serverAccount");
                String pwd = rs.getString("serverPwd");
    
                // �������
                System.out.print("ID: " + id);
                System.out.print(", ������˻�: " + name);
                System.out.print(", ���������: " + pwd);
                System.out.print("\n");
            }
            // ��ɺ�ر�
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // ���� JDBC ����
            se.printStackTrace();
        }catch(Exception e){
            // ���� Class.forName ����
            e.printStackTrace();
        }finally{
            // �ر���Դ
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// ʲô������
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
        
        // ִ�в�ѯ
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
    	// JDBC �����������ݿ� URL
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
        final String DB_URL = "jdbc:mysql://localhost:3306/ysm01db?useUnicode=true&characterEncoding=utf-8&useSSL=false";
     
        // ���ݿ���û��������룬��Ҫ�����Լ�������
        final String USER = "root";
        final String PASS = "555555";
        Connection conn = null;
        try {
            Class.forName(JDBC_DRIVER); //classLoader,���ض�Ӧ����
            conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
