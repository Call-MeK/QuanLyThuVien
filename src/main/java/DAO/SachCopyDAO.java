package DAO;

import DTO.SachCopyDTO;

import java.sql.*;
import java.util.*;

public class SachCopyDAO {

    public List<SachCopyDTO> getAll() {
        List<SachCopyDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM SACHCOPY WHERE IsDeleted=0";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SachCopyDTO sc = new SachCopyDTO();
                sc.setMaVach(rs.getString("MaVach"));
                sc.setMaSach(rs.getString("MaSach"));
                sc.setTenSachBanSao(rs.getString("TenSachBanSao"));
                sc.setTinhTrang(rs.getString("TinhTrang"));
                sc.setGhiChuTinhTrang(rs.getString("GhiChuTinhTrang"));
                sc.setNgayNhap(rs.getString("NgayNhap"));
                sc.setIsDeleted(rs.getBoolean("IsDeleted"));
                sc.setMaKeSach(rs.getString("MaKeSach"));
                list.add(sc);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // THÊM
    public boolean insert(SachCopyDTO sc) {
        String sql = "INSERT INTO SACHCOPY (MaVach, MaSach, TenSachBanSao, TinhTrang, GhiChuTinhTrang, NgayNhap, IsDeleted, MaKeSach) VALUES (?, ?, ?, ?, ?, ?, 0, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sc.getMaVach());
            ps.setString(2, sc.getMaSach());
            ps.setString(3, sc.getTenSachBanSao());
            ps.setString(4, sc.getTinhTrang());
            ps.setString(5, sc.getGhiChuTinhTrang());
            ps.setString(6, sc.getNgayNhap());
            ps.setString(7, sc.getMaKeSach());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // XÓA
    public boolean delete(String maVach) {
        String sql = "UPDATE SACHCOPY SET IsDeleted=1 WHERE MaVach=?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maVach);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // SỬA
    public boolean update(SachCopyDTO sc) {
        String sql = "UPDATE SACHCOPY SET MaSach=?, TenSachBanSao=?, TinhTrang=?, GhiChuTinhTrang=?, NgayNhap=?, MaKeSach=? WHERE MaVach=?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sc.getMaSach());
            ps.setString(2, sc.getTenSachBanSao());
            ps.setString(3, sc.getTinhTrang());
            ps.setString(4, sc.getGhiChuTinhTrang());
            ps.setString(5, sc.getNgayNhap());
            ps.setString(6, sc.getMaKeSach());
            ps.setString(7, sc.getMaVach());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // TÌM THEO ID
    public SachCopyDTO findById(String maVach) {
        String sql = "SELECT * FROM SACHCOPY WHERE MaVach=? AND IsDeleted=0";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maVach);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                SachCopyDTO sc = new SachCopyDTO();
                sc.setMaVach(rs.getString("MaVach"));
                sc.setMaSach(rs.getString("MaSach"));
                sc.setTenSachBanSao(rs.getString("TenSachBanSao"));
                sc.setTinhTrang(rs.getString("TinhTrang"));
                sc.setGhiChuTinhTrang(rs.getString("GhiChuTinhTrang"));
                sc.setNgayNhap(rs.getString("NgayNhap"));
                sc.setIsDeleted(rs.getBoolean("IsDeleted"));
                sc.setMaKeSach(rs.getString("MaKeSach"));
                return sc;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // TÌM THEO TÊN
    public List<SachCopyDTO> search(String keyword) {
        List<SachCopyDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM SACHCOPY WHERE TenSachBanSao LIKE ? AND IsDeleted=0";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SachCopyDTO sc = new SachCopyDTO();
                sc.setMaVach(rs.getString("MaVach"));
                sc.setMaSach(rs.getString("MaSach"));
                sc.setTenSachBanSao(rs.getString("TenSachBanSao"));
                sc.setTinhTrang(rs.getString("TinhTrang"));
                sc.setGhiChuTinhTrang(rs.getString("GhiChuTinhTrang"));
                sc.setNgayNhap(rs.getString("NgayNhap"));
                sc.setIsDeleted(rs.getBoolean("IsDeleted"));
                sc.setMaKeSach(rs.getString("MaKeSach"));
                list.add(sc);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
