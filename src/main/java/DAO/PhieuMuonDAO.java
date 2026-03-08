package DAO;

import DTO.PhieuMuonDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PhieuMuonDAO {

    public ArrayList<PhieuMuonDTO> getAll() {
        ArrayList<PhieuMuonDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM PHIEUMUON";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new PhieuMuonDTO(
                        rs.getString("MaPM"),
                        rs.getString("NgayMuon"),
                        rs.getString("NgayTra"),
                        rs.getString("HenTra"),
                        rs.getString("TinhTrang"),
                        rs.getString("MaNQL"),
                        rs.getString("MaThe")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public PhieuMuonDTO getById(String MaPM) {
        String sql = "SELECT * FROM PHIEUMUON WHERE MaPM = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, MaPM);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new PhieuMuonDTO(
                            rs.getString("MaPM"),
                            rs.getString("NgayMuon"),
                            rs.getString("NgayTra"),
                            rs.getString("HenTra"),
                            rs.getString("TinhTrang"),
                            rs.getString("MaNQL"),
                            rs.getString("MaThe"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int insert(PhieuMuonDTO pm) {
        String sql = "INSERT INTO PHIEUMUON (MaPM, NgayMuon, NgayTra, HenTra, TinhTrang, MaNQL, MaThe) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pm.getMaPM());
            ps.setString(2, pm.getNgayMuon());
            ps.setString(3, pm.getNgayTra());
            ps.setString(4, pm.getHenTra());
            ps.setString(5, pm.getTinhTrang());
            ps.setString(6, pm.getMaNQL());
            ps.setString(7, pm.getMaThe());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int update(PhieuMuonDTO pm) {
        String sql = "UPDATE PHIEUMUON SET NgayMuon = ?, NgayTra = ?, HenTra = ?, TinhTrang = ?, MaNQL = ?, MaThe = ? WHERE MaPM = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pm.getNgayMuon());
            ps.setString(2, pm.getNgayTra());
            ps.setString(3, pm.getHenTra());
            ps.setString(4, pm.getTinhTrang());
            ps.setString(5, pm.getMaNQL());
            ps.setString(6, pm.getMaThe());
            ps.setString(7, pm.getMaPM());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int delete(String MaPM) {
        String sql = "DELETE FROM PHIEUMUON WHERE MaPM = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, MaPM);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int count() {
        String sql = "SELECT COUNT(*) FROM PHIEUMUON";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Dùng cho admin: lấy tất cả (không lọc theo user)
    public ArrayList<Object[]> getDanhSachSachDangMuon() {
        ArrayList<Object[]> list = new ArrayList<>();
        String sql = "SELECT pm.MaPM, s.TenSach, pm.NgayMuon, pm.HenTra, pm.TinhTrang " +
                     "FROM PHIEUMUON pm " +
                     "JOIN CHITIETPHIEUMUON ct ON pm.MaPM = ct.MaPM " +
                     "JOIN SACHCOPY sc ON ct.MaCuonSach = sc.MaVach " +
                     "JOIN SACH s ON sc.MaSach = s.MaSach";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getString("MaPM"),
                    rs.getString("TenSach"),
                    rs.getString("NgayMuon"),
                    rs.getString("HenTra"),
                    rs.getString("TinhTrang")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Dùng cho user: chỉ lấy phiếu của thẻ đang đăng nhập
    public ArrayList<Object[]> getDanhSachSachDangMuonByMaThe(String maThe) {
        ArrayList<Object[]> list = new ArrayList<>();
        String sql = "SELECT pm.MaPM, s.TenSach, pm.NgayMuon, pm.HenTra, pm.NgayTra, pm.TinhTrang " +
                     "FROM PHIEUMUON pm " +
                     "JOIN CHITIETPHIEUMUON ct ON pm.MaPM = ct.MaPM " +
                     "JOIN SACHCOPY sc ON ct.MaCuonSach = sc.MaVach " +
                     "JOIN SACH s ON sc.MaSach = s.MaSach " +
                     "WHERE pm.MaThe = ? AND pm.TinhTrang != 'Đã xóa' " +
                     "ORDER BY pm.NgayMuon DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maThe);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String tinhTrang = rs.getString("TinhTrang");
                    String henTra    = rs.getString("HenTra");
                    String ngayTra   = rs.getString("NgayTra");

                    // Tự tính "Trễ hạn" nếu đang mượn quá ngày
                    if ("Đang mượn".equals(tinhTrang) && henTra != null) {
                        try {
                            java.time.LocalDate han = java.time.LocalDate.parse(henTra);
                            if (java.time.LocalDate.now().isAfter(han)) tinhTrang = "Trễ hạn";
                        } catch (Exception ignored) {}
                    }

                    list.add(new Object[]{
                        rs.getString("MaPM"),
                        rs.getString("TenSach"),
                        rs.getString("NgayMuon"),
                        henTra,
                        (ngayTra == null || ngayTra.isEmpty()) ? "Chưa trả" : ngayTra,
                        tinhTrang
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String generateMaPM() {
        String sql = "SELECT MaPM FROM PHIEUMUON ORDER BY MaPM DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                String lastMa = rs.getString("MaPM");
                int num = Integer.parseInt(lastMa.substring(2)) + 1;
                return String.format("PM%02d", num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "PM01";
    }
}