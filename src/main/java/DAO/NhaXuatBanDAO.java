/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.NhaXuatBanDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class NhaXuatBanDAO {
    public ArrayList<NhaXuatBanDTO> getAll() {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM NHAXUATBAN";
        ArrayList<NhaXuatBanDTO> list = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                NhaXuatBanDTO nxb = new NhaXuatBanDTO(
                        rs.getString("MaNXB"),
                        rs.getString("TenNXB"),
                        rs.getString("DiaChi"),
                        rs.getString("Email"),
                        rs.getString("SoDienThoai"));
                list.add(nxb);
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
    public NhaXuatBanDTO getById(String MaNXB){
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM NHAXUATBAN WHERE MaNXB = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, MaNXB);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                NhaXuatBanDTO nxb = new NhaXuatBanDTO(
                        rs.getString("MaNXB"),
                        rs.getString("TenNXB"),
                        rs.getString("DiaChi"),
                        rs.getString("Email"),
                        rs.getString("SoDienThoai"));
                return nxb;
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
    public boolean add(NhaXuatBanDTO nxb){
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO NHAXUATBAN (MaNXB, TenNXB, DiaChi, Email, SoDienThoai) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nxb.getMaNXB());
            stmt.setString(2, nxb.getTenNXB());
            stmt.setString(3, nxb.getDiaChi());
            stmt.setString(4, nxb.getEmail());
            stmt.setString(5, nxb.getSoDienThoai());
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
    public boolean update(NhaXuatBanDTO nxb){
        Connection conn = DatabaseConnection.getConnection();
        String sql = "UPDATE NHAXUATBAN SET TenNXB = ?, DiaChi = ?, Email = ?, SoDienThoai = ? WHERE MaNXB = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nxb.getTenNXB());
            stmt.setString(2, nxb.getDiaChi());
            stmt.setString(3, nxb.getEmail());
            stmt.setString(4, nxb.getSoDienThoai());
            stmt.setString(5, nxb.getMaNXB());
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
    public boolean delete(String MaNXB){
        Connection conn = DatabaseConnection.getConnection();
        String sql = "DELETE FROM NHAXUATBAN WHERE MaNXB = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, MaNXB);
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
