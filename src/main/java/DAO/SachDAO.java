package DAO;

import DTO.SachDTO;
import DTO.TacGiaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
                list.add(new SachDTO(
                        rs.getString("MaSach"),
                        rs.getString("tenSach"),
                        rs.getString("TheLoai"),
                        rs.getString("MaNXB"),
                        rs.getInt("NamXB"),
                        rs.getString("NgonNgu"),
                        rs.getFloat("GiaBia"),
                        rs.getBoolean("isdeleted"),
                        new ArrayList<>()));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public SachDTO getById(String maSach) {
        String sql = "SELECT * FROM SACH WHERE MaSach = ? AND isdeleted = 0";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maSach);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new SachDTO(
                            rs.getString("MaSach"),
                            rs.getString("tenSach"),
                            rs.getString("TheLoai"),
                            rs.getString("MaNXB"),
                            rs.getInt("NamXB"),
                            rs.getString("NgonNgu"),
                            rs.getFloat("GiaBia"),
                            rs.getBoolean("isdeleted"),
                            new ArrayList<>());
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    /**
     * Lấy danh sách đầy đủ để hiển thị bảng admin.
     * Dùng STUFF/FOR XML PATH thay STRING_AGG — tương thích SQL Server 2012+
     */
    public List<Object[]> getDanhSachDayDu() {
        List<Object[]> list = new ArrayList<>();
        String sql =
            "SELECT s.MaSach, s.tenSach, " +
            "       ISNULL(tl.TenTheLoai, s.TheLoai)  AS TenTheLoai, " +
            "       ISNULL(n.TenNXB,      s.MaNXB)    AS TenNXB, " +
            "       s.NamXB, s.NgonNgu, s.GiaBia, " +
            // Ghép tên tác giả bằng STUFF + FOR XML PATH (tương thích mọi version)
            "       ISNULL(STUFF((" +
            "           SELECT ', ' + tg.TenTacGia " +
            "           FROM TACGIA tg " +
            "           JOIN SACH_TACGIA st ON tg.MaTacGia = st.MaTacGia " +
            "           WHERE st.MaSach = s.MaSach " +
            "           FOR XML PATH(''), TYPE).value('.','NVARCHAR(MAX)'), 1, 2, ''), " +
            "       N'Chưa cập nhật') AS TenTacGia, " +
            // Đếm bản sao còn dùng được
            "       (SELECT COUNT(*) FROM SACHCOPY sc2 " +
            "        WHERE sc2.MaSach = s.MaSach " +
            "          AND sc2.IsDeleted = 0 " +
            "          AND sc2.TinhTrang != N'Hỏng') AS SoLuong " +
            "FROM SACH s " +
            "LEFT JOIN THELOAI     tl ON s.TheLoai = tl.MaTheLoai " +
            "LEFT JOIN NHAXUATBAN  n  ON s.MaNXB   = n.MaNXB " +
            "WHERE s.isdeleted = 0 " +
            "ORDER BY s.MaSach";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getString("MaSach"),
                    rs.getString("tenSach"),
                    rs.getString("TenTheLoai"),
                    rs.getString("TenNXB"),
                    rs.getInt("NamXB"),
                    rs.getString("NgonNgu"),
                    String.format("%,.0f đ", rs.getFloat("GiaBia")),
                    rs.getString("TenTacGia"),
                    rs.getInt("SoLuong")
                });
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public boolean isDangMuon(String maSach) {
        String sql =
            "SELECT COUNT(*) FROM PHIEUMUON pm " +
            "JOIN CHITIETPHIEUMUON ct ON pm.MaPM = ct.MaPM " +
            "JOIN SACHCOPY sc ON ct.MaCuonSach = sc.MaVach " +
            "WHERE sc.MaSach = ? AND pm.TinhTrang = N'Đang mượn'";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maSach);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public List<TacGiaDTO> getTacGiaCuaSach(String maSach) {
        List<TacGiaDTO> list = new ArrayList<>();
        String sql = "SELECT tg.* FROM TACGIA tg JOIN SACH_TACGIA st ON tg.MaTacGia = st.MaTacGia WHERE st.MaSach = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maSach);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new TacGiaDTO(
                            rs.getString("MaTacGia"), rs.getString("TenTacGia"),
                            rs.getString("QuocTich"), rs.getString("DiaChi"),
                            rs.getString("SoDienThoai"), rs.getString("Email")));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
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
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean update(SachDTO sach) {
        String sql = "UPDATE SACH SET tenSach=?, TheLoai=?, MaNXB=?, NamXB=?, NgonNgu=?, GiaBia=? WHERE MaSach=? AND isdeleted=0";
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
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean delete(String maSach) {
        String sql = "UPDATE SACH SET isdeleted=1 WHERE MaSach=?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maSach);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean insertSachTacGia(String maSach, String maTacGia) {
        String sql = "INSERT INTO SACH_TACGIA (MaTacGia, MaSach) VALUES (?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maTacGia);
            ps.setString(2, maSach);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean deleteSachTacGia(String maSach) {
        String sql = "DELETE FROM SACH_TACGIA WHERE MaSach = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maSach);
            ps.executeUpdate();
            return true;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public String generateMaSach() {
        String sql = "SELECT MAX(MaSach) FROM SACH";
        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next() && rs.getString(1) != null) {
                int num = Integer.parseInt(rs.getString(1).trim().substring(1)) + 1;
                return String.format("S%09d", num);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return "S000000001";
    }
}