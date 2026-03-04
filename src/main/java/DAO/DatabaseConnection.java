/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class DatabaseConnection {
    private static Connection connection =null;
    public static Connection getConnection(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String URL = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyThuVien;trustServerCertificate=true";
            String UserName="sa";
<<<<<<< HEAD
            String Password="nhiheo";
=======
            String Password="Phuong2006";
>>>>>>> 6a0ca875679f0335fd3c59213b99632d74efdcc2
            
            if(connection==null|| connection.isClosed()){
                connection = DriverManager.getConnection(URL,UserName,Password);   
                System.out.println("Ket noi thanh cong");    
            }
            
        } catch (ClassNotFoundException e) {
            System.out.println("Khong tim thay Driver");
            e.printStackTrace();
        }
        catch (SQLException e){
            System.out.println("khong the ket noi den Database");
            e.printStackTrace();
        }
            return connection;
    }   
//    // Thêm hàm main này vào để chạy test thử
//    public static void main(String[] args) {
//        Connection conn = DatabaseConnection.getConnection();
//        if (conn != null) {
//            System.out.println("Chúc mừng! Bạn đã kết nối Database thành công!");
//        } else {
//            System.out.println("Kết nối thất bại, hãy kiểm tra lại lỗi ở trên.");
//        }
//    }
}
