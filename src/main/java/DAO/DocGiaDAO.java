/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.DocGiaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class DocGiaDAO {
    public ArrayList<DocGiaDTO> getAll() {
        ArrayList<DocGiaDTO> danhsach = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT dg.MaDocGia, dg.NgayDangKi, dg.LoaiDocGia, dg.IsDeleted, dg.NgayXoa, "
                + "cn.MaNguoi, cn.HoTen, cn.NgaySinh, cn.TenDangNhap, cn.MatKhau, "
                + "cn.GioiTinh, cn.DiaChi, cn.SoDienThoai, cn.Email, cn.TrangThai "
                + "FROM DOCGIA dg JOIN CONNGUOI cn ON dg.MaDocGia = cn.MaNguoi";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DocGiaDTO dg = new DocGiaDTO(
                        rs.getString("MaDocGia"),
                        rs.getString("NgayDangKi"),
                        rs.getString("LoaiDocGia"),
                        rs.getBoolean("IsDeleted"),
                        rs.getString("NgayXoa"),
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
                danhsach.add(dg);
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
    public DocGiaDTO getById(String maDocGia){
        DocGiaDTO dg = null;
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT dg.MaDocGia, dg.NgayDangKi, dg.LoaiDocGia, dg.IsDeleted, dg.NgayXoa, "
                + "cn.MaNguoi, cn.HoTen, cn.NgaySinh, cn.TenDangNhap, cn.MatKhau, "
                + "cn.GioiTinh, cn.DiaChi, cn.SoDienThoai, cn.Email, cn.TrangThai "
                + "FROM DOCGIA dg JOIN CONNGUOI cn ON dg.MaDocGia = cn.MaNguoi WHERE dg.MaDocGia = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maDocGia);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                dg = new DocGiaDTO(
                        rs.getString("MaDocGia"),
                        rs.getString("NgayDangKi"),
                        rs.getString("LoaiDocGia"),
                        rs.getBoolean("IsDeleted"),
                        rs.getString("NgayXoa"),
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
            return dg;
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
    public boolean add(DocGiaDTO docgia){
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO DOCGIA (MaDocGia, NgayDangKi, LoaiDocGia, IsDeleted, NgayXoa, MaNguoi) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, docgia.getMaDocGia());
            ps.setString(2, docgia.getNgayDangKi());
            ps.setString(3, docgia.getLoaiDocGia());
            ps.setBoolean(4, docgia.getIsDeleted());
            ps.setString(5, docgia.getNgayXoa());
            ps.setString(6, docgia.getMaNguoi());
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
    public boolean update(DocGiaDTO docgia){
        Connection conn = DatabaseConnection.getConnection();
        String sql = "UPDATE DOCGIA SET NgayDangKi = ?, LoaiDocGia = ?, IsDeleted = ?, NgayXoa = ?, MaNguoi = ? WHERE MaDocGia = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, docgia.getNgayDangKi());
            ps.setString(2, docgia.getLoaiDocGia());
            ps.setBoolean(3, docgia.getIsDeleted());
            ps.setString(4, docgia.getNgayXoa());
            ps.setString(5, docgia.getMaNguoi());
            ps.setString(6, docgia.getMaDocGia());
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
    public boolean delete(String maDocGia){
        Connection conn = DatabaseConnection.getConnection();
        String sql = "DELETE FROM DOCGIA WHERE MaDocGia = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maDocGia);
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
    public boolean softdelete(String maDocGia){
        Connection conn = DatabaseConnection.getConnection();
        String sql = "UPDATE DOCGIA SET IsDeleted = ?, NgayXoa = ? WHERE MaDocGia = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, true);
            ps.setString(2, new java.util.Date().toString());
            ps.setString(3, maDocGia);
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
