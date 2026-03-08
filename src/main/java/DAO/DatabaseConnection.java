package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Lớp quản lý kết nối cơ sở dữ liệu SQL Server
 */
public class DatabaseConnection {
    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            // Nạp Driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Cấu hình URL kết nối
            String URL = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyThuVien;trustServerCertificate=true";
            String UserName = "sa";
         // Đã cập nhật mật khẩu của bạn
            String Password = "12345"; // Đã cập nhật mật khẩu của bạn


            // Kiểm tra nếu kết nối chưa tồn tại hoặc đã bị đóng thì tạo mới
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, UserName, Password);
                System.out.println("Ket noi thanh cong");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Khong tim thay Driver JDBC");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Loi: Khong the ket noi den Database SQL Server");
            e.printStackTrace();
        }
        return connection;
    }
}