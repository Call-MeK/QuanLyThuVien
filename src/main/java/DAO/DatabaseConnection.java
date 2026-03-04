package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    private static Connection connection = null;
    
    public static Connection getConnection() {
        try {
            // Khai báo Driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
<<<<<<< HEAD
            String URL = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyThuVien;trustServerCertificate=true";
            String UserName="sa";
            String Password="nhiheo";
            if(connection==null|| connection.isClosed()){
                connection = DriverManager.getConnection(URL,UserName,Password);   
=======
            
            // Đường dẫn kết nối đến SQL Server
            String URL = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyThuVien;trustServerCertificate=true";
            String UserName = "sa";
            String Password = "12345"; // Mật khẩu của bạn
            
            // Kiểm tra và mở kết nối
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, UserName, Password);   
>>>>>>> 241b90453e7ee88cf25bb2d7f9fdf09ed7501ae2
                System.out.println("Ket noi thanh cong");    
            }
            
        } catch (ClassNotFoundException e) {
            System.out.println("Khong tim thay Driver");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("khong the ket noi den Database");
            e.printStackTrace();
        }
        
        return connection;
    }   
}