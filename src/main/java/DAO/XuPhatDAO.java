/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.XuPhatDTO;

/**
 *
 * @author Admin
 */
public class XuPhatDAO {
    public ArrayList<XuPhatDTO> getAll() {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM XUPHAT";
        ArrayList<XuPhatDTO> list = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                XuPhatDTO xp = new XuPhatDTO(
                        rs.getString("MaXP"),
                        rs.getString("LyDo"),
                        rs.getFloat("SoTien"),
                        rs.getString("MaPM"),
                        rs.getString("MaNQL"),
                        rs.getString("MaCuonSach"));
                list.add(xp);
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

    public XuPhatDTO getById(String MaXP) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM XUPHAT WHERE MaXP = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, MaXP);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                XuPhatDTO xp = new XuPhatDTO(
                        rs.getString("MaXP"),
                        rs.getString("LyDo"),
                        rs.getFloat("SoTien"),
                        rs.getString("MaPM"),
                        rs.getString("MaNQL"),
                        rs.getString("MaCuonSach"));
                return xp;
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

    public boolean add(XuPhatDTO xp) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO XUPHAT (MaXP, LyDo, SoTien, MaPM, MaNQL, MaCuonSach) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, xp.getMaXP());
            stmt.setString(2, xp.getLyDo());
            stmt.setFloat(3, xp.getSoTien());
            stmt.setString(4, xp.getMaPM());
            stmt.setString(5, xp.getMaNQL());
            stmt.setString(6, xp.getMaCuonSach());
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

    public boolean update(XuPhatDTO xp) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "UPDATE XUPHAT SET LyDo = ?, SoTien = ?, MaPM = ?, MaNQL = ?, MaCuonSach = ? WHERE MaXP = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, xp.getLyDo());
            stmt.setFloat(2, xp.getSoTien());
            stmt.setString(3, xp.getMaPM());
            stmt.setString(4, xp.getMaNQL());
            stmt.setString(5, xp.getMaCuonSach());
            stmt.setString(6, xp.getMaXP());
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

    public boolean delete(String MaXP) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "DELETE FROM XUPHAT WHERE MaXP = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, MaXP);
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
