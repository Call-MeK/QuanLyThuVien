package DAO;

import DTO.DocGiaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

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
            try { conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return null;
    }

    public DocGiaDTO getById(String maDocGia) {
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
            while (rs.next()) {
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
            try { conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return null;
    }

    public boolean add(DocGiaDTO docgia) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO DOCGIA (MaDocGia, NgayDangKi, LoaiDocGia, IsDeleted, NgayXoa) VALUES (?, ?, ?, ?, ?)";
        String sql2 = "INSERT INTO CONNGUOI (MaNguoi, HoTen, NgaySinh, TenDangNhap, MatKhau, GioiTinh, DiaChi, SoDienThoai, Email, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, docgia.getMaDocGia());
            ps.setString(2, docgia.getNgayDangKi());
            ps.setString(3, docgia.getLoaiDocGia());
            ps.setBoolean(4, docgia.getIsDeleted());
            ps.setString(5, docgia.getNgayXoa());

            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setString(1, docgia.getMaNguoi());
            ps2.setString(2, docgia.getHoTen());
            ps2.setString(3, docgia.getNgaySinh());
            ps2.setString(4, docgia.getTenDangNhap());
            ps2.setString(5, docgia.getMatKhau());
            ps2.setString(6, docgia.getGioiTinh());
            ps2.setString(7, docgia.getDiaChi());
            ps2.setString(8, docgia.getSoDienThoai());
            ps2.setString(9, docgia.getEmail());
            ps2.setString(10, docgia.getTrangThai());

            // CONNGUOI trước vì DOCGIA tham chiếu tới CONNGUOI
            int result2 = ps2.executeUpdate();
            int result = ps.executeUpdate();

            conn.commit();
            ps.close();
            ps2.close();
            return result > 0 && result2 > 0;
        } catch (Exception e) {
            e.printStackTrace();
            try { conn.rollback(); } catch (Exception e2) { e2.printStackTrace(); }
        } finally {
            try { conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return false;
    }

    // ĐÃ SỬA: Đúng thứ tự parameter cho bảng CONNGUOI
    public boolean update(DocGiaDTO docgia) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "UPDATE DOCGIA SET NgayDangKi = ?, LoaiDocGia = ?, IsDeleted = ?, NgayXoa = ? WHERE MaDocGia = ?";
        String sql2 = "UPDATE CONNGUOI SET HoTen = ?, NgaySinh = ?, TenDangNhap = ?, MatKhau = ?, GioiTinh = ?, DiaChi = ?, SoDienThoai = ?, Email = ?, TrangThai = ? WHERE MaNguoi = ?";
        try {
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, docgia.getNgayDangKi());
            ps.setString(2, docgia.getLoaiDocGia());
            ps.setBoolean(3, docgia.getIsDeleted());
            ps.setString(4, docgia.getNgayXoa());
            ps.setString(5, docgia.getMaDocGia());       // WHERE MaDocGia = ?

            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setString(1, docgia.getHoTen());         // HoTen
            ps2.setString(2, docgia.getNgaySinh());      // NgaySinh
            ps2.setString(3, docgia.getTenDangNhap());   // TenDangNhap
            ps2.setString(4, docgia.getMatKhau());       // MatKhau
            ps2.setString(5, docgia.getGioiTinh());      // GioiTinh
            ps2.setString(6, docgia.getDiaChi());        // DiaChi
            ps2.setString(7, docgia.getSoDienThoai());   // SoDienThoai
            ps2.setString(8, docgia.getEmail());         // Email
            ps2.setString(9, docgia.getTrangThai());     // TrangThai
            ps2.setString(10, docgia.getMaNguoi());      // WHERE MaNguoi = ?

            int result = ps.executeUpdate();
            int result2 = ps2.executeUpdate();
            conn.commit();
            ps.close();
            ps2.close();
            return result > 0 && result2 > 0;
        } catch (Exception e) {
            e.printStackTrace();
            try { conn.rollback(); } catch (Exception e2) { e2.printStackTrace(); }
        } finally {
            try { conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return false;
    }

    // ĐÃ SỬA: Xóa các bản ghi liên quan ở bảng con TRƯỚC, rồi mới xóa DOCGIA và CONNGUOI
    // Trả về String thay vì boolean để báo lỗi cụ thể
    public String delete(String maDocGia) {
        Connection conn = DatabaseConnection.getConnection();
        try {
            conn.setAutoCommit(false);

            // Bước 1: Xóa các bản ghi ở bảng con tham chiếu tới MaDocGia
            // (Thêm/bớt bảng tùy theo cấu trúc DB của bạn)
            String[] sqlXoaBangCon = {
                "DELETE FROM CHITIETPHIEUMUON WHERE MaPhieuMuon IN (SELECT MaPhieuMuon FROM PHIEUMUON WHERE MaDocGia = ?)",
                "DELETE FROM PHIEUMUON WHERE MaDocGia = ?",
                "DELETE FROM PHIPHAT WHERE MaDocGia = ?",
                "DELETE FROM THETHUVIEN WHERE MaDocGia = ?",
            };

            for (String sqlCon : sqlXoaBangCon) {
                try {
                    PreparedStatement psCon = conn.prepareStatement(sqlCon);
                    psCon.setString(1, maDocGia);
                    psCon.executeUpdate();
                    psCon.close();
                } catch (Exception ignore) {
                    // Bảng có thể không tồn tại hoặc không có FK -> bỏ qua
                }
            }

            // Bước 2: Xóa DOCGIA (con) trước CONNGUOI (cha)
            PreparedStatement ps = conn.prepareStatement("DELETE FROM DOCGIA WHERE MaDocGia = ?");
            ps.setString(1, maDocGia);
            int result = ps.executeUpdate();
            ps.close();

            PreparedStatement ps2 = conn.prepareStatement("DELETE FROM CONNGUOI WHERE MaNguoi = ?");
            ps2.setString(1, maDocGia);
            int result2 = ps2.executeUpdate();
            ps2.close();

            if (result > 0 && result2 > 0) {
                conn.commit();
                return "OK";
            } else {
                conn.rollback();
                return "Không tìm thấy bản ghi để xóa trong DB";
            }
        } catch (Exception e) {
            e.printStackTrace();
            try { conn.rollback(); } catch (Exception e2) { e2.printStackTrace(); }
            return "Lỗi DB: " + e.getMessage();
        } finally {
            try { conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }

    // ĐÃ SỬA: Khóa thẻ - cập nhật cả DOCGIA lẫn CONNGUOI.TrangThai
    public boolean softdelete(String maDocGia) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "UPDATE DOCGIA SET IsDeleted = ?, NgayXoa = ? WHERE MaDocGia = ?";
        String sql2 = "UPDATE CONNGUOI SET TrangThai = ? WHERE MaNguoi = ?";
        try {
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, true);
            ps.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            ps.setString(3, maDocGia);

            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setString(1, "Đã khóa");
            ps2.setString(2, maDocGia);

            int result = ps.executeUpdate();
            int result2 = ps2.executeUpdate();

            conn.commit();
            ps.close();
            ps2.close();
            return result > 0 && result2 > 0;
        } catch (Exception e) {
            e.printStackTrace();
            try { conn.rollback(); } catch (Exception e2) { e2.printStackTrace(); }
        } finally {
            try { conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return false;
    }

    public Object[] getThongTinCaNhan(String maDocGia) {
        String sql = "SELECT c.MaNguoi, c.HoTen, c.NgaySinh, c.SoDienThoai, c.Email, t.NgayCap, t.NgayHetHan " +
                     "FROM CONNGUOI c " +
                     "JOIN DOCGIA d ON c.MaNguoi = d.MaDocGia " +
                     "LEFT JOIN THETHUVIEN t ON d.MaDocGia = t.MaDocGia " +
                     "WHERE c.MaNguoi = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maDocGia);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Object[]{
                    rs.getString("MaNguoi"),
                    rs.getString("HoTen"),
                    rs.getDate("NgaySinh") != null ? rs.getDate("NgaySinh").toString() : "Chưa cập nhật",
                    rs.getString("SoDienThoai"),
                    rs.getString("Email"),
                    rs.getDate("NgayCap") != null ? rs.getDate("NgayCap").toString() : "Chưa có thẻ",
                    rs.getDate("NgayHetHan") != null ? rs.getDate("NgayHetHan").toString() : "Chưa có thẻ"
                };
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateThongTinLienHe(String maDocGia, String sdt, String email) {
        String sql = "UPDATE CONNGUOI SET SoDienThoai = ?, Email = ? WHERE MaNguoi = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sdt);
            ps.setString(2, email);
            ps.setString(3, maDocGia);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}