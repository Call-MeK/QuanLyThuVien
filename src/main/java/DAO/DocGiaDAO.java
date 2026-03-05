package DAO;

import DTO.DocGiaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class DocGiaDAO {

    // ===============================================
    // TỰ ĐỘNG SINH MÃ
    // ===============================================
    // ===============================================
    // TỰ ĐỘNG SINH MÃ (ĐÃ FIX LỖI KHOẢNG TRẮNG)
    // ===============================================
    public String generateMaDocGia() {
        // Dùng MAX() để lấy mã lớn nhất chính xác hơn ORDER BY
        String sql = "SELECT MAX(MaDocGia) as MaxMa FROM DOCGIA";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                String lastMa = rs.getString("MaxMa");
                if (lastMa != null && !lastMa.trim().isEmpty()) {
                    lastMa = lastMa.trim(); // Xóa khoảng trắng thừa của kiểu char()
                    // Cắt bỏ chữ "DG" (2 ký tự đầu), lấy phần số cộng thêm 1
                    int num = Integer.parseInt(lastMa.substring(2)) + 1;
                    return String.format("DG%08d", num);
                }
            }
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return "DG00000001";
    }

    private String generateMaThe(Connection conn) {
        String sql = "SELECT MAX(MaThe) as MaxMa FROM THETHUVIEN";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                String lastMa = rs.getString("MaxMa");
                if (lastMa != null && !lastMa.trim().isEmpty()) {
                    lastMa = lastMa.trim(); // Xóa khoảng trắng thừa
                    // Cắt bỏ chữ "TTV" (3 ký tự đầu), lấy phần số cộng thêm 1
                    int num = Integer.parseInt(lastMa.substring(3)) + 1;
                    return String.format("TTV%07d", num);
                }
            }
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return "TTV0000001";
    }
    // ===============================================
    // CÁC HÀM CRUD
    // ===============================================
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
                // ĐÃ THÊM .trim() ĐỂ CẮT SẠCH KHOẢNG TRẮNG CỦA KIỂU CHAR()
                DocGiaDTO dg = new DocGiaDTO(
                        rs.getString("MaDocGia") != null ? rs.getString("MaDocGia").trim() : "",
                        rs.getString("NgayDangKi"),
                        rs.getString("LoaiDocGia"),
                        rs.getBoolean("IsDeleted"),
                        rs.getString("NgayXoa"),
                        rs.getString("MaNguoi") != null ? rs.getString("MaNguoi").trim() : "",
                        rs.getString("HoTen"),
                        rs.getString("NgaySinh"),
                        rs.getString("TenDangNhap") != null ? rs.getString("TenDangNhap").trim() : "",
                        rs.getString("MatKhau"),
                        rs.getString("GioiTinh"),
                        rs.getString("DiaChi"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email"),
                        rs.getString("TrangThai"));
                danhsach.add(dg);
            }
            rs.close(); ps.close();
            return danhsach;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return null;
    }
    

    // ĐÃ SỬA NGHIỆP VỤ TRANSACTION: Thêm vào cả 3 bảng
    public boolean add(DocGiaDTO docgia, String maNQL) {
        Connection conn = null;
        String sqlConNguoi = "INSERT INTO CONNGUOI (MaNguoi, HoTen, NgaySinh, TenDangNhap, MatKhau, GioiTinh, DiaChi, SoDienThoai, Email, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlDocGia = "INSERT INTO DOCGIA (MaDocGia, NgayDangKi, LoaiDocGia, IsDeleted, NgayXoa) VALUES (?, ?, ?, ?, ?)";
        String sqlThe = "INSERT INTO THETHUVIEN (MaThe, TenThe, MaDocGia, NgayCap, NgayHetHan, MaNQL) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Bắt đầu Transaction

            // 1. Thêm vào CONNGUOI
            PreparedStatement ps1 = conn.prepareStatement(sqlConNguoi);
            ps1.setString(1, docgia.getMaDocGia());
            ps1.setString(2, docgia.getHoTen());
            if (docgia.getNgaySinh() != null && !docgia.getNgaySinh().isEmpty()) {
                ps1.setDate(3, java.sql.Date.valueOf(docgia.getNgaySinh()));
            } else { ps1.setNull(3, java.sql.Types.DATE); }
            ps1.setString(4, docgia.getTenDangNhap());
            ps1.setString(5, docgia.getMatKhau());
            ps1.setString(6, docgia.getGioiTinh());
            ps1.setString(7, docgia.getDiaChi() != null ? docgia.getDiaChi() : "");
            ps1.setString(8, docgia.getSoDienThoai());
            ps1.setString(9, docgia.getEmail());
            ps1.setString(10, docgia.getTrangThai());
            int r1 = ps1.executeUpdate();

            // 2. Thêm vào DOCGIA
            PreparedStatement ps2 = conn.prepareStatement(sqlDocGia);
            ps2.setString(1, docgia.getMaDocGia());
            ps2.setDate(2, java.sql.Date.valueOf(LocalDate.now())); // Ngày đăng ký là hôm nay
            ps2.setString(3, docgia.getLoaiDocGia());
            ps2.setBoolean(4, false);
            ps2.setNull(5, java.sql.Types.DATE); // Chưa bị xóa
            int r2 = ps2.executeUpdate();

            // 3. Cấp THẺ THƯ VIỆN
            String maTheMoi = generateMaThe(conn);
            PreparedStatement ps3 = conn.prepareStatement(sqlThe);
            ps3.setString(1, maTheMoi);
            ps3.setString(2, "Thẻ " + docgia.getHoTen());
            ps3.setString(3, docgia.getMaDocGia());
            ps3.setDate(4, java.sql.Date.valueOf(LocalDate.now())); // Ngày cấp
            ps3.setDate(5, java.sql.Date.valueOf(LocalDate.now().plusYears(1))); // Hạn thẻ 1 năm
            ps3.setString(6, maNQL);
            int r3 = ps3.executeUpdate();

            // Nếu cả 3 đều thành công thì Commit
            if (r1 > 0 && r2 > 0 && r3 > 0) {
                conn.commit();
                ps1.close(); ps2.close(); ps3.close();
                return true;
            } else {
                conn.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (Exception e2) { e2.printStackTrace(); }
        } finally {
            try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return false;
    }

    public boolean update(DocGiaDTO docgia) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "UPDATE DOCGIA SET NgayDangKi = ?, LoaiDocGia = ?, IsDeleted = ?, NgayXoa = ? WHERE MaDocGia = ?";
        String sql2 = "UPDATE CONNGUOI SET HoTen = ?, NgaySinh = ?, TenDangNhap = ?, MatKhau = ?, GioiTinh = ?, DiaChi = ?, SoDienThoai = ?, Email = ?, TrangThai = ? WHERE MaNguoi = ?";
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, docgia.getNgayDangKi());
            ps.setString(2, docgia.getLoaiDocGia());
            ps.setBoolean(3, docgia.getIsDeleted() != null ? docgia.getIsDeleted() : false);
            ps.setString(4, docgia.getNgayXoa());
            ps.setString(5, docgia.getMaDocGia());

            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setString(1, docgia.getHoTen());
            ps2.setString(2, docgia.getNgaySinh());
            ps2.setString(3, docgia.getTenDangNhap());
            ps2.setString(4, docgia.getMatKhau());
            ps2.setString(5, docgia.getGioiTinh());
            ps2.setString(6, docgia.getDiaChi());
            ps2.setString(7, docgia.getSoDienThoai());
            ps2.setString(8, docgia.getEmail());
            ps2.setString(9, docgia.getTrangThai());
            ps2.setString(10, docgia.getMaNguoi());

            int result = ps.executeUpdate();
            int result2 = ps2.executeUpdate();
            conn.commit();
            ps.close(); ps2.close();
            return result > 0 && result2 > 0;
        } catch (Exception e) {
            e.printStackTrace();
            try { conn.rollback(); } catch (Exception e2) { e2.printStackTrace(); }
        } finally { try { conn.close(); } catch (Exception e) { e.printStackTrace(); } }
        return false;
    }

    public String delete(String maDocGia) {
        Connection conn = DatabaseConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            String[] sqlXoaBangCon = {
                "DELETE FROM CHITIETPHIEUMUON WHERE MaPM IN (SELECT MaPM FROM PHIEUMUON WHERE MaThe IN (SELECT MaThe FROM THETHUVIEN WHERE MaDocGia = ?))",
                "DELETE FROM PHIEUPHAT WHERE MaPM IN (SELECT MaPM FROM PHIEUMUON WHERE MaThe IN (SELECT MaThe FROM THETHUVIEN WHERE MaDocGia = ?))",
                "DELETE FROM PHIEUMUON WHERE MaThe IN (SELECT MaThe FROM THETHUVIEN WHERE MaDocGia = ?)",
                "DELETE FROM THETHUVIEN WHERE MaDocGia = ?"
            };
            for (String sqlCon : sqlXoaBangCon) {
                try (PreparedStatement psCon = conn.prepareStatement(sqlCon)) {
                    psCon.setString(1, maDocGia);
                    psCon.executeUpdate();
                } catch (Exception ignore) {}
            }

            PreparedStatement ps = conn.prepareStatement("DELETE FROM DOCGIA WHERE MaDocGia = ?");
            ps.setString(1, maDocGia);
            int result = ps.executeUpdate(); ps.close();

            PreparedStatement ps2 = conn.prepareStatement("DELETE FROM CONNGUOI WHERE MaNguoi = ?");
            ps2.setString(1, maDocGia);
            int result2 = ps2.executeUpdate(); ps2.close();

            if (result > 0 && result2 > 0) {
                conn.commit(); return "OK";
            } else { conn.rollback(); return "Không tìm thấy bản ghi để xóa"; }
        } catch (Exception e) {
            e.printStackTrace();
            try { conn.rollback(); } catch (Exception e2) { e2.printStackTrace(); }
            return "Lỗi DB: " + e.getMessage();
        } finally { try { conn.close(); } catch (Exception e) { e.printStackTrace(); } }
    }

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
            conn.commit(); ps.close(); ps2.close();
            return result > 0 && result2 > 0;
        } catch (Exception e) {
            e.printStackTrace();
            try { conn.rollback(); } catch (Exception e2) { e2.printStackTrace(); }
        } finally { try { conn.close(); } catch (Exception e) { e.printStackTrace(); } }
        return false;
    }

    public Object[] getThongTinCaNhan(String maDocGia) {
        String sql = "SELECT c.MaNguoi, c.HoTen, c.NgaySinh, c.SoDienThoai, c.Email, t.NgayCap, t.NgayHetHan " +
                     "FROM CONNGUOI c JOIN DOCGIA d ON c.MaNguoi = d.MaDocGia " +
                     "LEFT JOIN THETHUVIEN t ON d.MaDocGia = t.MaDocGia WHERE c.MaNguoi = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maDocGia);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Object[]{
                    rs.getString("MaNguoi"), rs.getString("HoTen"),
                    rs.getDate("NgaySinh") != null ? rs.getDate("NgaySinh").toString() : "Chưa cập nhật",
                    rs.getString("SoDienThoai"), rs.getString("Email"),
                    rs.getDate("NgayCap") != null ? rs.getDate("NgayCap").toString() : "Chưa có thẻ",
                    rs.getDate("NgayHetHan") != null ? rs.getDate("NgayHetHan").toString() : "Chưa có thẻ"
                };
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public boolean updateThongTinLienHe(String maDocGia, String sdt, String email) {
        String sql = "UPDATE CONNGUOI SET SoDienThoai = ?, Email = ? WHERE MaNguoi = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sdt); ps.setString(2, email); ps.setString(3, maDocGia);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
    // Kiểm tra các ràng buộc nghiệp vụ trước khi xóa
    public String kiemTraDieuKienXoa(String maDocGia) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // 1. Kiểm tra xem độc giả có đang mượn sách chưa trả không?
            String sql1 = "SELECT COUNT(*) as DangMuon FROM PHIEUMUON WHERE MaThe IN (SELECT MaThe FROM THETHUVIEN WHERE MaDocGia = ?) AND (NgayTra IS NULL OR NgayTra = '')";
            try (PreparedStatement ps1 = conn.prepareStatement(sql1)) {
                ps1.setString(1, maDocGia);
                ResultSet rs1 = ps1.executeQuery();
                if (rs1.next() && rs1.getInt("DangMuon") > 0) return "Độc giả đang giữ sách chưa trả. Yêu cầu trả sách trước khi xóa!";
            }
            
            // 2. Kiểm tra xem độc giả có đang nợ tiền phạt chưa đóng không?
            String sql2 = "SELECT COUNT(*) as ChuaTra FROM PHIEUPHAT WHERE MaPM IN (SELECT MaPM FROM PHIEUMUON WHERE MaThe IN (SELECT MaThe FROM THETHUVIEN WHERE MaDocGia = ?)) AND TrangThai = 0";
            try (PreparedStatement ps2 = conn.prepareStatement(sql2)) {
                ps2.setString(1, maDocGia);
                ResultSet rs2 = ps2.executeQuery();
                if (rs2.next() && rs2.getInt("ChuaTra") > 0) return "Độc giả đang nợ tiền phạt. Yêu cầu nộp phạt trước khi xóa!";
            }
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi kiểm tra CSDL";
        }
    }

    // Hàm thực hiện ẩn đi (Đổi trạng thái thành "Đã xóa")
    public boolean xoaMemThanhDaXoa(String maDocGia) {
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
            ps2.setString(1, "Đã xóa"); // Đổi trạng thái hiển thị
            ps2.setString(2, maDocGia);

            int result = ps.executeUpdate();
            int result2 = ps2.executeUpdate();
            conn.commit(); ps.close(); ps2.close();
            return result > 0 && result2 > 0;
        } catch (Exception e) {
            try { conn.rollback(); } catch(Exception ex){}
        } finally {
            try { conn.close(); } catch(Exception ex){}
        }
        return false;
    }
}