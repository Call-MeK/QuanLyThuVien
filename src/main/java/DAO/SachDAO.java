/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author LePhuc
 */


import DTO.SachDTO;
import DTO.TacGiaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SachDAO {

    public List<SachDTO> getAll() {
        List<SachDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM SACH WHERE isdeleted = 0";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SachDTO sach = new SachDTO(
                        rs.getString("MaSach"),
                        rs.getString("tenSach"),
                        rs.getString("TheLoai"),
                        rs.getString("MaNXB"),
                        rs.getInt("NamXB"),
                        rs.getString("NgonNgu"),
                        rs.getFloat("GiaBia"),
                        rs.getBoolean("isdeleted"),
                        new ArrayList<TacGiaDTO>()
                );
                list.add(sach);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(SachDTO sach) {
        String sql = "INSERT INTO SACH (MaSach, tenSach, TheLoai, MaNXB, NamXB, NgonNgu, GiaBia, isdeleted) VALUES (?, ?, ?, ?, ?, ?, ?, 0)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, sach.getMaSach());
            ps.setString(2, sach.getTenSach());
            ps.setString(3, sach.getTheLoai());
            ps.setString(4, sach.getMaNXB());
            ps.setInt(5, sach.getNamXB());
            ps.setString(6, sach.getNgonNgu());
            ps.setFloat(7, sach.getGiaBia());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(SachDTO sach) {
        String sql = "UPDATE SACH SET tenSach = ?, TheLoai = ?, MaNXB = ?, NamXB = ?, NgonNgu = ?, GiaBia = ? WHERE MaSach = ? AND isdeleted = 0";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, sach.getTenSach());
            ps.setString(2, sach.getTheLoai());
            ps.setString(3, sach.getMaNXB());
            ps.setInt(4, sach.getNamXB());
            ps.setString(5, sach.getNgonNgu());
            ps.setFloat(6, sach.getGiaBia());
            ps.setString(7, sach.getMaSach());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String maSach) {
        String sql = "UPDATE SACH SET isdeleted = 1 WHERE MaSach = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maSach);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
