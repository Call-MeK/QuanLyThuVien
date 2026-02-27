package DAO;

import model.SachCopy;
import utils.DBConnection;

import java.sql.*;
import java.util.*;

public class SachCopyDAO {

    public List<SachCopy> getAll() {
        List<SachCopy> list = new ArrayList<>();
        String sql = "SELECT * FROM SACHCOPY WHERE IsDeleted=0";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SachCopy sc = new SachCopy();
                sc.setMaVach(rs.getString("MaVach"));
                sc.setMaSach(rs.getString("MaSach"));
                sc.setTenSachBanSao(rs.getString("TenSachBanSao"));
                sc.setTinhTrang(rs.getString("TinhTrang"));
                sc.setGhiChuTinhTrang(rs.getString("GhiChuTinhTrang"));
                sc.setNgayNhap(rs.getDate("NgayNhap"));
                list.add(sc);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    //THÊM
    public boolean insert(SachCopy sc) {
        String sql = "INSERT INTO SACHCOPY VALUES (?, ?, ?, ?, ?, ?, 0)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sc.getMaVach());
            ps.setString(2, sc.getMaSach());
            ps.setString(3, sc.getTenSachBanSao());
            ps.setString(4, sc.getTinhTrang());
            ps.setString(5, sc.getGhiChuTinhTrang());
            ps.setDate(6, new java.sql.Date(sc.getNgayNhap().getTime()));

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    //XÓA
    public boolean delete(String maVach) {
        String sql = "UPDATE SACHCOPY SET IsDeleted=1 WHERE MaVach=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maVach);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    //SỬA
    public boolean update(SachCopy sc) {
    String sql = "UPDATE SACHCOPY SET MaSach=?, TenSachBanSao=?, TinhTrang=?, GhiChuTinhTrang=?, NgayNhap=? WHERE MaVach=?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, sc.getMaSach());
        ps.setString(2, sc.getTenSachBanSao());
        ps.setString(3, sc.getTinhTrang());
        ps.setString(4, sc.getGhiChuTinhTrang());
        ps.setDate(5, new java.sql.Date(sc.getNgayNhap().getTime()));
        ps.setString(6, sc.getMaVach());

        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
    }
    //TÌM THEO ID
    public SachCopy findById(String maVach) {
    String sql = "SELECT * FROM SACHCOPY WHERE MaVach=? AND IsDeleted=0";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, maVach);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            SachCopy sc = new SachCopy();
            sc.setMaVach(rs.getString("MaVach"));
            sc.setMaSach(rs.getString("MaSach"));
            sc.setTenSachBanSao(rs.getString("TenSachBanSao"));
            sc.setTinhTrang(rs.getString("TinhTrang"));
            sc.setGhiChuTinhTrang(rs.getString("GhiChuTinhTrang"));
            sc.setNgayNhap(rs.getDate("NgayNhap"));
            return sc;
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return null;
    }
    //TÌM THEO TÊN
    public List<SachCopy> search(String keyword) {
    List<SachCopy> list = new ArrayList<>();
    String sql = "SELECT * FROM SACHCOPY WHERE TenSachBanSao LIKE ? AND IsDeleted=0";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, "%" + keyword + "%");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            SachCopy sc = new SachCopy();
            sc.setMaVach(rs.getString("MaVach"));
            sc.setMaSach(rs.getString("MaSach"));
            sc.setTenSachBanSao(rs.getString("TenSachBanSao"));
            sc.setTinhTrang(rs.getString("TinhTrang"));
            sc.setGhiChuTinhTrang(rs.getString("GhiChuTinhTrang"));
            sc.setNgayNhap(rs.getDate("NgayNhap"));
            list.add(sc);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}



}
