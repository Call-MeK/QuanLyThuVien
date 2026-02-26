/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.TheLoaiDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class TheLoaiDAO {

    // Lấy tất cả thể loại
    public ArrayList<TheLoaiDTO> getAll() {
        ArrayList<TheLoaiDTO> danhSach = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM THELOAI";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TheLoaiDTO theLoai = new TheLoaiDTO(
                        rs.getString("MaTheLoai"),
                        rs.getString("TenTheLoai"));
                danhSach.add(theLoai);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSach;
    }

    // Lấy thể loại theo mã
    public TheLoaiDTO getById(String maTheLoai) {
        TheLoaiDTO theLoai = null;
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM THELOAI WHERE MaTheLoai = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maTheLoai);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                theLoai = new TheLoaiDTO(
                        rs.getString("MaTheLoai"),
                        rs.getString("TenTheLoai"));
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return theLoai;
    }

    // Thêm thể loại mới
    public boolean add(TheLoaiDTO theLoai) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO THELOAI (MaTheLoai, TenTheLoai) VALUES (?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, theLoai.getMaTheLoai());
            ps.setString(2, theLoai.getTenTheLoai());

            int result = ps.executeUpdate();
            ps.close();

            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Cập nhật thể loại
    public boolean update(TheLoaiDTO theLoai) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "UPDATE THELOAI SET TenTheLoai = ? WHERE MaTheLoai = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, theLoai.getTenTheLoai());
            ps.setString(2, theLoai.getMaTheLoai());

            int result = ps.executeUpdate();
            ps.close();

            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Xóa thể loại
    public boolean delete(String maTheLoai) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "DELETE FROM THELOAI WHERE MaTheLoai = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maTheLoai);

            int result = ps.executeUpdate();
            ps.close();

            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
