package DAO;

import model.SachHong;
import utils.DBConnection;

import java.sql.*;
import java.util.*;

public class SachHongDAO {

    // 🔹 Lấy tất cả
    public List<SachHong> getAll() {
        List<SachHong> list = new ArrayList<>();
        String sql = "SELECT * FROM SACHHONG";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SachHong sh = new SachHong();
                sh.setMaSachHong(rs.getInt("MaSachHong"));
                sh.setTenSachHong(rs.getString("TenSachHong"));
                sh.setMaVach(rs.getString("MaVach"));
                sh.setSoLuong(rs.getInt("SoLuong"));
                sh.setNgayGhiNhan(rs.getDate("NgayGhiNhan"));
                sh.setLyDo(rs.getString("LyDo"));
                list.add(sh);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 🔹 Thêm
    public boolean insert(SachHong sh) {
        String sql = "INSERT INTO SACHHONG VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sh.getMaSachHong());
            ps.setString(2, sh.getTenSachHong());
            ps.setString(3, sh.getMaVach());
            ps.setInt(4, sh.getSoLuong());
            ps.setDate(5, new java.sql.Date(sh.getNgayGhiNhan().getTime()));
            ps.setString(6, sh.getLyDo());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 🔹 Cập nhật
    public boolean update(SachHong sh) {
        String sql = "UPDATE SACHHONG SET TenSachHong=?, MaVach=?, SoLuong=?, NgayGhiNhan=?, LyDo=? WHERE MaSachHong=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sh.getTenSachHong());
            ps.setString(2, sh.getMaVach());
            ps.setInt(3, sh.getSoLuong());
            ps.setDate(4, new java.sql.Date(sh.getNgayGhiNhan().getTime()));
            ps.setString(5, sh.getLyDo());
            ps.setInt(6, sh.getMaSachHong());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 🔹 Xóa
    public boolean delete(int maSachHong) {
        String sql = "DELETE FROM SACHHONG WHERE MaSachHong=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maSachHong);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 🔹 Tìm theo ID
    public SachHong findById(int maSachHong) {
        String sql = "SELECT * FROM SACHHONG WHERE MaSachHong=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maSachHong);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                SachHong sh = new SachHong();
                sh.setMaSachHong(rs.getInt("MaSachHong"));
                sh.setTenSachHong(rs.getString("TenSachHong"));
                sh.setMaVach(rs.getString("MaVach"));
                sh.setSoLuong(rs.getInt("SoLuong"));
                sh.setNgayGhiNhan(rs.getDate("NgayGhiNhan"));
                sh.setLyDo(rs.getString("LyDo"));
                return sh;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public List<SachHong> searchByName(String keyword) {
    List<SachHong> list = new ArrayList<>();
    String sql = "SELECT * FROM SACHHONG WHERE TenSachHong LIKE ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, "%" + keyword + "%");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            SachHong sh = new SachHong();
            sh.setMaSachHong(rs.getInt("MaSachHong"));
            sh.setTenSachHong(rs.getString("TenSachHong"));
            sh.setMaVach(rs.getString("MaVach"));
            sh.setSoLuong(rs.getInt("SoLuong"));
            sh.setNgayGhiNhan(rs.getDate("NgayGhiNhan"));
            sh.setLyDo(rs.getString("LyDo"));
            list.add(sh);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

}
