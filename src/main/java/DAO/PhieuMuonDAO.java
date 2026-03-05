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
                PhieuMuonDTO pm = new PhieuMuonDTO(
                        rs.getString("MaPM"),
                        rs.getString("NgayMuon"),
                        rs.getString("NgayTra"),
                        rs.getString("HenTra"),
                        rs.getString("TinhTrang"),
                        rs.getString("MaNQL"),
                        rs.getString("MaThe"));
                list.add(pm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public PhieuMuonDTO getById(String MaPM) {
        PhieuMuonDTO pm = new PhieuMuonDTO();
        String sql = "SELECT * FROM PHIEUMUON WHERE MaPM = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                pm = new PhieuMuonDTO(
                        rs.getString("MaPM"),
                        rs.getString("NgayMuon"),
                        rs.getString("NgayTra"),
                        rs.getString("HenTra"),
                        rs.getString("TinhTrang"),
                        rs.getString("MaNQL"),
                        rs.getString("MaThe"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pm;
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
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public ArrayList<Object[]> getDanhSachSachDangMuon() {
        ArrayList<Object[]> list = new ArrayList<>();
        // Câu lệnh JOIN 4 bảng để lấy đầy đủ Tên Sách và Thông tin mượn
        String sql = "SELECT pm.MaPM, s.TenSach, pm.NgayMuon, pm.HenTra, pm.TinhTrang " +
                     "FROM PHIEUMUON pm " +
                     "JOIN CHITIETPHIEUMUON ct ON pm.MaPM = ct.MaPM " +
                     "JOIN SACHCOPY sc ON ct.MaCuonSach = sc.MaVach " +
                     "JOIN SACH s ON sc.MaSach = s.MaSach";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String maPM = rs.getString("MaPM");
                String tenSach = rs.getString("TenSach");
                String ngayMuon = rs.getString("NgayMuon");
                String henTra = rs.getString("HenTra");
                String trangThai = rs.getString("TinhTrang");

                list.add(new Object[]{maPM, tenSach, ngayMuon, henTra, trangThai});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
   // Tự động sinh mã Phiếu Mượn (VD: PM00000001)
    public String generateMaPM() {
        String sql = "SELECT MaPM FROM PHIEUMUON ORDER BY MaPM DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                String lastMa = rs.getString("MaPM");
                int num = Integer.parseInt(lastMa.substring(2)) + 1;
                return String.format("PM%08d", num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "PM00000001";
    }
}
