package DAO;

import DTO.SachHongDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SachHongDAO {

    public List<SachHongDTO> getAll() {
        List<SachHongDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM SACHHONG ORDER BY NgayGhiNhan DESC";
        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    /** BUS cũ gọi findById(int) */
    public SachHongDTO findById(int maSachHong) {
        String sql = "SELECT * FROM SACHHONG WHERE MaSachHong = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, maSachHong);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    /** BUS cũ gọi searchByName(String) */
    public List<SachHongDTO> searchByName(String keyword) {
        List<SachHongDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM SACHHONG WHERE TenSachHong LIKE ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    /**
     * Insert — MaSachHong là IDENTITY tự tăng trong DB.
     * Nếu DB không dùng IDENTITY thì đổi sql thành INSERT gồm cả MaSachHong.
     */
    public boolean insert(SachHongDTO sh) {
        String sql = "INSERT INTO SACHHONG (TenSachHong, MaVach, SoLuong, NgayGhiNhan, LyDo) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, sh.getTenSachHong());
            ps.setString(2, sh.getMaVach());
            ps.setInt(3, sh.getSoLuong());
            ps.setString(4, sh.getNgayGhiNhan());
            ps.setString(5, sh.getLyDo());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            // Nếu MaSachHong không phải IDENTITY, thêm cột này vào INSERT
            return insertWithId(sh);
        }
    }

    /** Fallback: insert kèm MaSachHong nếu DB không dùng IDENTITY */
    private boolean insertWithId(SachHongDTO sh) {
        String sql = "INSERT INTO SACHHONG (MaSachHong, TenSachHong, MaVach, SoLuong, NgayGhiNhan, LyDo) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, sh.getMaSachHong());
            ps.setString(2, sh.getTenSachHong());
            ps.setString(3, sh.getMaVach());
            ps.setInt(4, sh.getSoLuong());
            ps.setString(5, sh.getNgayGhiNhan());
            ps.setString(6, sh.getLyDo());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    /** BUS cũ gọi update(SachHongDTO) */
    public boolean update(SachHongDTO sh) {
        String sql = "UPDATE SACHHONG SET TenSachHong=?, MaVach=?, SoLuong=?, NgayGhiNhan=?, LyDo=? WHERE MaSachHong=?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, sh.getTenSachHong());
            ps.setString(2, sh.getMaVach());
            ps.setInt(3, sh.getSoLuong());
            ps.setString(4, sh.getNgayGhiNhan());
            ps.setString(5, sh.getLyDo());
            ps.setInt(6, sh.getMaSachHong());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean delete(int maSachHong) {
        String sql = "DELETE FROM SACHHONG WHERE MaSachHong = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, maSachHong);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    private SachHongDTO mapRow(ResultSet rs) throws SQLException {
        return new SachHongDTO(
                rs.getInt("MaSachHong"),
                rs.getString("TenSachHong"),
                rs.getString("MaVach"),
                rs.getInt("SoLuong"),
                rs.getString("NgayGhiNhan"),
                rs.getString("LyDo"));
    }
}