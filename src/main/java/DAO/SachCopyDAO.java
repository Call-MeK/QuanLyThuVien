package DAO;

import DTO.SachCopyDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SachCopyDAO {

    public String generateMaVach(Connection con, int offset) throws Exception {
        String sql = "SELECT COUNT(*) FROM SACHCOPY";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                long num = rs.getLong(1) + 1 + offset;
                return String.format("MV%018d", num);
            }
        }
        return String.format("MV%018d", 1 + offset);
    }

    public String generateMaVach(Connection con) throws Exception {
        return generateMaVach(con, 0);
    }

    /**
     * FIX: Thêm NgayNhap = GETDATE() để tránh lỗi NOT NULL trên cột NgayNhap
     */
    public int insertNewCopies(Connection con, String maSach, String tenSach, int soLuong) throws Exception {
        // Đếm bản sao hiện có của sách này
        int currentCount = 0;
        try (PreparedStatement ps = con.prepareStatement(
                "SELECT COUNT(*) FROM SACHCOPY WHERE RTRIM(MaSach) = ?")) {
            ps.setString(1, maSach.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) currentCount = rs.getInt(1);
            }
        }

        // Đếm tổng bản sao toàn bảng để tạo MaVach không trùng
        int totalCount = 0;
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM SACHCOPY")) {
            if (rs.next()) totalCount = rs.getInt(1);
        }

        // FIX: Thêm NgayNhap = GETDATE() vào câu INSERT
        String sql = "INSERT INTO SACHCOPY (MaVach, MaSach, TenSachBanSao, TinhTrang, IsDeleted, NgayNhap) " +
                     "VALUES (?, ?, ?, N'Tốt', 0, GETDATE())";
        int success = 0;
        for (int i = 0; i < soLuong; i++) {
            String maVach = String.format("MV%018d", totalCount + i + 1);
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, maVach);
                ps.setString(2, maSach.trim());
                ps.setString(3, tenSach + " - Bản " + (currentCount + i + 1));
                if (ps.executeUpdate() > 0) success++;
            }
        }
        return success;
    }

    public boolean insert(SachCopyDTO sc) {
        String sql = "INSERT INTO SACHCOPY (MaVach, MaSach, TenSachBanSao, TinhTrang, IsDeleted, NgayNhap) " +
                     "VALUES (?, ?, ?, ?, ?, GETDATE())";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, sc.getMaVach());
            ps.setString(2, sc.getMaSach());
            ps.setString(3, sc.getTenSachBanSao());
            ps.setString(4, sc.getTinhTrang() != null ? sc.getTinhTrang() : "Tốt");
            ps.setBoolean(5, sc.getIsDeleted() != null && sc.getIsDeleted());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean update(SachCopyDTO sc) {
        String sql = "UPDATE SACHCOPY SET MaSach=?, TenSachBanSao=?, TinhTrang=?, IsDeleted=? WHERE RTRIM(MaVach)=?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, sc.getMaSach());
            ps.setString(2, sc.getTenSachBanSao());
            ps.setString(3, sc.getTinhTrang());
            ps.setBoolean(4, sc.getIsDeleted() != null && sc.getIsDeleted());
            ps.setString(5, sc.getMaVach().trim());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean delete(String maVach) {
        String sql = "UPDATE SACHCOPY SET IsDeleted=1 WHERE RTRIM(MaVach)=?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maVach.trim());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public SachCopyDTO findById(String maVach) {
        String sql = "SELECT * FROM SACHCOPY WHERE RTRIM(MaVach)=?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maVach.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public List<SachCopyDTO> getAll() {
        List<SachCopyDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM SACHCOPY WHERE IsDeleted=0";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public boolean updateTinhTrang(String maVach, String tinhTrang, String ghiChu) {
        String sql = "UPDATE SACHCOPY SET TinhTrang=? WHERE RTRIM(MaVach)=?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tinhTrang);
            ps.setString(2, maVach.trim());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    private SachCopyDTO mapRow(ResultSet rs) throws SQLException {
        SachCopyDTO sc = new SachCopyDTO();
        sc.setMaVach(rs.getString("MaVach") != null ? rs.getString("MaVach").trim() : "");
        sc.setMaSach(rs.getString("MaSach") != null ? rs.getString("MaSach").trim() : "");
        sc.setTenSachBanSao(rs.getString("TenSachBanSao") != null ? rs.getString("TenSachBanSao") : "");
        sc.setTinhTrang(rs.getString("TinhTrang") != null ? rs.getString("TinhTrang") : "Tốt");
        sc.setIsDeleted(rs.getBoolean("IsDeleted"));
        return sc;
    }
}