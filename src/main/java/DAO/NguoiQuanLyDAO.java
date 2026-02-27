/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.NguoiQuanLyDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class NguoiQuanLyDAO {
    public ArrayList<NguoiQuanLyDTO> getAll() {
        ArrayList<NguoiQuanLyDTO> list = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT nql.MaNQL, nql.VaiTro, nql.IsDeleted, " +
                "cn.MaNguoi, cn.HoTen, cn.NgaySinh, cn.TenDangNhap, cn.MatKhau, cn.GioiTinh, " +
                "cn.DiaChi, cn.SoDienThoai, cn.Email, cn.TrangThai " +
                "FROM NGUOIQUANLY nql JOIN CONNGUOI cn ON nql.MaNQL = cn.MaNguoi";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                NguoiQuanLyDTO nql = new NguoiQuanLyDTO(
                        rs.getString("MaNQL"),
                        rs.getString("VaiTro"),
                        rs.getBoolean("IsDeleted"),
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
                list.add(nql);
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

    public NguoiQuanLyDTO getById(String MaNguoiQuanLy) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT nql.MaNQL, nql.VaiTro, nql.IsDeleted, " +
                "cn.MaNguoi, cn.HoTen, cn.NgaySinh, cn.TenDangNhap, cn.MatKhau, cn.GioiTinh, " +
                "cn.DiaChi, cn.SoDienThoai, cn.Email, cn.TrangThai " +
                "FROM NGUOIQUANLY nql JOIN CONNGUOI cn ON nql.MaNQL = cn.MaNguoi " +
                "WHERE nql.MaNQL = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, MaNguoiQuanLy);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                NguoiQuanLyDTO nql = new NguoiQuanLyDTO(
                        rs.getString("MaNQL"),
                        rs.getString("VaiTro"),
                        rs.getBoolean("IsDeleted"),
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
                return nql;
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

    public boolean add(NguoiQuanLyDTO nql) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO NGUOIQUANLY (MaNQL, VaiTro, IsDeleted) VALUES (?, ?, ?)";
        String sql2 = "INSERT INTO CONNGUOI (MaNguoi, HoTen, NgaySinh, TenDangNhap, MatKhau, GioiTinh, DiaChi, SoDienThoai, Email, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nql.getMaNQL());
            stmt.setString(2, nql.getVaiTro());
            stmt.setBoolean(3, nql.getIsDeleted());
            PreparedStatement stmt2 = conn.prepareStatement(sql2);
            stmt2.setString(1, nql.getMaNguoi());
            stmt2.setString(2, nql.getHoTen());
            stmt2.setString(3, nql.getNgaySinh());
            stmt2.setString(4, nql.getTenDangNhap());
            stmt2.setString(5, nql.getMatKhau());
            stmt2.setString(6, nql.getGioiTinh());
            stmt2.setString(7, nql.getDiaChi());
            stmt2.setString(8, nql.getSoDienThoai());
            stmt2.setString(9, nql.getEmail());
            stmt2.setString(10, nql.getTrangThai());
            int result = stmt.executeUpdate();
            int result2 = stmt2.executeUpdate();
            conn.commit();
            stmt.close();
            stmt2.close();
            return result > 0 && result2 > 0;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean update(NguoiQuanLyDTO nql) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "UPDATE NGUOIQUANLY SET VaiTro = ?, IsDeleted = ? WHERE MaNQL = ?";
        String sql2 = "UPDATE CONNGUOI SET HoTen = ?, NgaySinh = ?, TenDangNhap = ?, MatKhau = ?, GioiTinh = ?, DiaChi = ?, SoDienThoai = ?, Email = ?, TrangThai = ? WHERE MaNguoi = ?";
        try {
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nql.getVaiTro());
            stmt.setBoolean(2, nql.getIsDeleted());
            stmt.setString(3, nql.getMaNQL());
            PreparedStatement stmt2 = conn.prepareStatement(sql2);
            stmt2.setString(1, nql.getMaNguoi());
            stmt2.setString(2, nql.getHoTen());
            stmt2.setString(3, nql.getNgaySinh());
            stmt2.setString(4, nql.getTenDangNhap());
            stmt2.setString(5, nql.getMatKhau());
            stmt2.setString(6, nql.getGioiTinh());
            stmt2.setString(7, nql.getDiaChi());
            stmt2.setString(8, nql.getSoDienThoai());
            stmt2.setString(9, nql.getEmail());
            stmt2.setString(10, nql.getTrangThai());
            int result = stmt.executeUpdate();
            int result2 = stmt2.executeUpdate();
            conn.commit();
            stmt.close();
            stmt2.close();
            return result > 0 && result2 > 0;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean softdelete(String MaNQL) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "UPDATE NGUOIQUANLY SET IsDeleted = ? WHERE MaNQL = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setBoolean(1, true);
            stmt.setString(2, MaNQL);
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
