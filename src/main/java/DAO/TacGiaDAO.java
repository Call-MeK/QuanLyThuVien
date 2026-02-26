/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.TacGiaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class TacGiaDAO {
    public ArrayList<TacGiaDTO> getAll() {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM TACGIA";
        ArrayList<TacGiaDTO> list = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TacGiaDTO tg = new TacGiaDTO(
                        rs.getString("MaTacGia"),
                        rs.getString("TenTacGia"),
                        rs.getString("QuocTich"),
                        rs.getString("DiaChi"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email"));
                list.add(tg);
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

    public TacGiaDTO getById(String MaTacGia) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM TACGIA WHERE MaTacGia = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, MaTacGia);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TacGiaDTO tg = new TacGiaDTO(
                        rs.getString("MaTacGia"),
                        rs.getString("TenTacGia"),
                        rs.getString("QuocTich"),
                        rs.getString("DiaChi"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email"));
                return tg;
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

    public boolean add(TacGiaDTO tg) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO TACGIA (MaTacGia, TenTacGia, QuocTich, DiaChi, SoDienThoai, Email) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, tg.getMaTacGia());
            stmt.setString(2, tg.getTenTacGia());
            stmt.setString(3, tg.getQuocTich());
            stmt.setString(4, tg.getDiaChi());
            stmt.setString(5, tg.getSoDienThoai());
            stmt.setString(6, tg.getEmail());
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

    public boolean update(TacGiaDTO tg) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "UPDATE TACGIA SET TenTacGia = ?, QuocTich = ?, DiaChi = ?, SoDienThoai = ?, Email = ? WHERE MaTacGia = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, tg.getTenTacGia());
            stmt.setString(2, tg.getQuocTich());
            stmt.setString(3, tg.getDiaChi());
            stmt.setString(4, tg.getSoDienThoai());
            stmt.setString(5, tg.getEmail());
            stmt.setString(6, tg.getMaTacGia());
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

    public boolean delete(String MaTacGia) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "DELETE FROM TACGIA WHERE MaTacGia = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, MaTacGia);
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
