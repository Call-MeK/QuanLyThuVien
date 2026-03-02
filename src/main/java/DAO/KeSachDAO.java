/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author LePhuc
 */


import DTO.KeSachDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class KeSachDAO {

    public List<KeSachDTO> getAll() {
        List<KeSachDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM KESACH WHERE isdeleted = 0"; 
        
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                KeSachDTO ke = new KeSachDTO(
                    rs.getString("MaKeSach"),
                    rs.getString("TenKe"),
                    rs.getString("MaTheLoai")
                );
                list.add(ke);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(KeSachDTO ke) {
        String sql = "INSERT INTO KESACH (MaKeSach, TenKe, MaTheLoai, isdeleted) VALUES (?, ?, ?, 0)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, ke.getMaKeSach());
            ps.setString(2, ke.getTenKe());
            ps.setString(3, ke.getMaTheLoai());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(KeSachDTO ke) {
        String sql = "UPDATE KESACH SET TenKe = ?, MaTheLoai = ? WHERE MaKeSach = ? AND isdeleted = 0";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, ke.getTenKe());
            ps.setString(2, ke.getMaTheLoai());
            ps.setString(3, ke.getMaKeSach());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String maKeSach) {
        String sql = "UPDATE KESACH SET isdeleted = 1 WHERE MaKeSach = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, maKeSach);
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
