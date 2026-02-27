package DAO;

import DTO.SachHongDTO;

import java.sql.*;
import java.util.*;

public class SachHongDAO {

    // 🔹 Lấy tất cả
    public List<SachHongDTO> getAll() {
        List<SachHongDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM SACHHONG";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SachHongDTO sh = new SachHongDTO(
                        rs.getInt("MaSachHong"),
                        rs.getString("TenSachHong"),
                        rs.getString("MaVach"),
                        rs.getInt("SoLuong"),
                        rs.getString("NgayGhiNhan"),
                        rs.getString("LyDo"));
                list.add(sh);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 🔹 Thêm
    public boolean insert(SachHongDTO sh) {
        String sql = "INSERT INTO SACHHONG (MaSachHong, TenSachHong, MaVach, SoLuong, NgayGhiNhan, LyDo) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sh.getMaSachHong());
            ps.setString(2, sh.getTenSachHong());
            ps.setString(3, sh.getMaVach());
            ps.setInt(4, sh.getSoLuong());
            ps.setString(5, sh.getNgayGhiNhan());
            ps.setString(6, sh.getLyDo());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 🔹 Cập nhật
    public boolean update(SachHongDTO sh) {
        String sql = "UPDATE SACHHONG SET TenSachHong=?, MaVach=?, SoLuong=?, NgayGhiNhan=?, LyDo=? WHERE MaSachHong=?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sh.getTenSachHong());
            ps.setString(2, sh.getMaVach());
            ps.setInt(3, sh.getSoLuong());
            ps.setString(4, sh.getNgayGhiNhan());
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

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maSachHong);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 🔹 Tìm theo ID
    public SachHongDTO findById(int maSachHong) {
        String sql = "SELECT * FROM SACHHONG WHERE MaSachHong=?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maSachHong);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                SachHongDTO sh = new SachHongDTO(
                        rs.getInt("MaSachHong"),
                        rs.getString("TenSachHong"),
                        rs.getString("MaVach"),
                        rs.getInt("SoLuong"),
                        rs.getString("NgayGhiNhan"),
                        rs.getString("LyDo"));
                return sh;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<SachHongDTO> searchByName(String keyword) {
        List<SachHongDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM SACHHONG WHERE TenSachHong LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SachHongDTO sh = new SachHongDTO(
                        rs.getInt("MaSachHong"),
                        rs.getString("TenSachHong"),
                        rs.getString("MaVach"),
                        rs.getInt("SoLuong"),
                        rs.getString("NgayGhiNhan"),
                        rs.getString("LyDo"));
                list.add(sh);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
