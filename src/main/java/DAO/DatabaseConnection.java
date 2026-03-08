package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String URL = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyThuVien;trustServerCertificate=true";
            String UserName = "sa";
            String Password = "12345";

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