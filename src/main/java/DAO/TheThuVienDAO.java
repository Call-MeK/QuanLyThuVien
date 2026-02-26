/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.TheThuVienDTO;

/**
 *
 * @author Admin
 */
public class TheThuVienDAO {
    public ArrayList<TheThuVienDTO> getAll() {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM THETHUVIEN";
        ArrayList<TheThuVienDTO> list = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TheThuVienDTO the = new TheThuVienDTO(
                        rs.getString("MaThe"),
                        rs.getString("TenThe"),
                        rs.getString("MaDocGia"),
                        rs.getString("NgayCap"),
                        rs.getString("NgayHetHan"),
                        rs.getString("MaNQL"));
                list.add(the);
            }
            rs.close();
            stmt.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public TheThuVienDTO getById(String MaThe) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM THETHUVIEN WHERE MaThe = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, MaThe);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TheThuVienDTO the = new TheThuVienDTO(
                        rs.getString("MaThe"),
                        rs.getString("TenThe"),
                        rs.getString("MaDocGia"),
                        rs.getString("NgayCap"),
                        rs.getString("NgayHetHan"),
                        rs.getString("MaNQL"));
                return the;
            }
            rs.close();
            stmt.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean add(TheThuVienDTO the) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO THETHUVIEN (MaThe, TenThe, MaDocGia, NgayCap, NgayHetHan, MaNQL) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, the.getMaThe());
            stmt.setString(2, the.getTenThe());
            stmt.setString(3, the.getMaDocGia());
            stmt.setString(4, the.getNgayCap());
            stmt.setString(5, the.getNgayHetHan());
            stmt.setString(6, the.getMaNQL());
            int result = stmt.executeUpdate();
            stmt.close();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean update(TheThuVienDTO the) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "UPDATE THETHUVIEN SET TenThe = ?, MaDocGia = ?, NgayCap = ?, NgayHetHan = ?, MaNQL = ? WHERE MaThe = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, the.getTenThe());
            stmt.setString(2, the.getMaDocGia());
            stmt.setString(3, the.getNgayCap());
            stmt.setString(4, the.getNgayHetHan());
            stmt.setString(5, the.getMaNQL());
            stmt.setString(6, the.getMaThe());
            int result = stmt.executeUpdate();
            stmt.close();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean delete(String MaThe) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "DELETE FROM THETHUVIEN WHERE MaThe = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, MaThe);
            int result = stmt.executeUpdate();
            stmt.close();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
