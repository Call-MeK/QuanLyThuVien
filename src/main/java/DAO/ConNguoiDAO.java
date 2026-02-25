/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.ConNguoiDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ConNguoiDAO {
    public ArrayList<ConNguoiDTO> getAll() {
        ArrayList<ConNguoiDTO> danhsach = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();
        String stmt = "SELECT * FROM CONNGUOI";
        try {
            PreparedStatement ps = conn.prepareStatement(stmt);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ConNguoiDTO connguoi = new ConNguoiDTO(
                        rs.getString("MaNguoi"),
                        rs.getString("HoTen"),
                        rs.getString("NgaySinh"),
                        rs.getString("TenDangNhap"),
                        rs.getString("MatKhau"),
                        rs.getString("GioiTinh"),
                        rs.getString("DiaChi"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email"),
                        rs.getString("TrangThai"));
                danhsach.add(connguoi);
            }
            rs.close();
            ps.close();
            return danhsach;
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

    public ConNguoiDTO getByID(String MaNguoi) {
        ConNguoiDTO nguoi = null;
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM CONNGUOI WHERE MaNguoi = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, MaNguoi);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nguoi = new ConNguoiDTO(
                        rs.getString("MaNguoi"),
                        rs.getString("HoTen"),
                        rs.getString("NgaySinh"),
                        rs.getString("TenDangNhap"),
                        rs.getString("MatKhau"),
                        rs.getString("GioiTinh"),
                        rs.getString("DiaChi"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email"),
                        rs.getString("TrangThai"));
            }
            rs.close();
            ps.close();
            return nguoi;
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

    public boolean add(ConNguoiDTO connguoi) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO CONNGUOI (MaNguoi, HoTen, NgaySinh, TenDangNhap, MatKhau, GioiTinh, DiaChi, SoDienThoai, Email, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, connguoi.getMaNguoi());
            ps.setString(2, connguoi.getHoTen());
            ps.setString(3, connguoi.getNgaySinh());
            ps.setString(4, connguoi.getTenDangNhap());
            ps.setString(5, connguoi.getMatKhau());
            ps.setString(6, connguoi.getGioiTinh());
            ps.setString(7, connguoi.getDiaChi());
            ps.setString(8, connguoi.getSoDienThoai());
            ps.setString(9, connguoi.getEmail());
            ps.setString(10, connguoi.getTrangThai());
            int result = ps.executeUpdate();
            ps.close();
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

    public boolean update(ConNguoiDTO connguoi) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "UPDATE CONNGUOI SET HoTen = ?, NgaySinh = ?, TenDangNhap = ?, MatKhau = ?, GioiTinh = ?, DiaChi = ?, SoDienThoai = ?, Email = ?, TrangThai = ? WHERE MaNguoi = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, connguoi.getHoTen());
            ps.setString(2, connguoi.getNgaySinh());
            ps.setString(3, connguoi.getTenDangNhap());
            ps.setString(4, connguoi.getMatKhau());
            ps.setString(5, connguoi.getGioiTinh());
            ps.setString(6, connguoi.getDiaChi());
            ps.setString(7, connguoi.getSoDienThoai());
            ps.setString(8, connguoi.getEmail());
            ps.setString(9, connguoi.getTrangThai());
            ps.setString(10, connguoi.getMaNguoi());
            int result = ps.executeUpdate();
            ps.close();
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

    public boolean delete(String MaNguoi) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "DELETE FROM CONNGUOI WHERE MaNguoi = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, MaNguoi);
            int result = ps.executeUpdate();
            ps.close();
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
