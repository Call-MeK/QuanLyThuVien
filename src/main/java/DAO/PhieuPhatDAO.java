package DAO;

import DTO.PhieuPhatDTO;
import java.sql.*;
import java.util.ArrayList;

public class PhieuPhatDAO {

    // TrangThai: 0 = Chưa thanh toán, 1 = Đã thanh toán, 2 = Đã hủy

    // 1. Thêm phiếu phạt (mặc định TrangThai = 0 = Chưa thanh toán)
    public boolean add(PhieuPhatDTO pp) {
        String sql = "INSERT INTO PHIEUPHAT (MaPP, MaPM, MaNQL, NgayLap, TongTien, TrangThai) VALUES (?, ?, ?, ?, ?, 0)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pp.getMaPP());
            ps.setString(2, pp.getMaPM());
            ps.setString(3, pp.getMaNQL());
            ps.setDate(4, java.sql.Date.valueOf(pp.getNgayLap()));
            ps.setDouble(5, Double.parseDouble(pp.getTongTien()));
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 2. Lấy tất cả phiếu phạt CHƯA BỊ HỦY (TrangThai = 0 hoặc 1)
    public ArrayList<PhieuPhatDTO> getAll() {
        ArrayList<PhieuPhatDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM PHIEUPHAT WHERE TrangThai < 2 ORDER BY NgayLap DESC";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                PhieuPhatDTO pp = new PhieuPhatDTO(
                        rs.getString("MaPP"),
                        rs.getString("MaPM"),
                        rs.getString("MaNQL"),
                        rs.getString("NgayLap"),
                        rs.getString("TongTien"),
                        rs.getInt("TrangThai"));
                list.add(pp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3. Tìm theo mã (chỉ tìm phiếu chưa hủy)
    public PhieuPhatDTO getById(String maPP) {
        String sql = "SELECT * FROM PHIEUPHAT WHERE MaPP = ? AND TrangThai < 2";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPP);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new PhieuPhatDTO(
                        rs.getString("MaPP"),
                        rs.getString("MaPM"),
                        rs.getString("MaNQL"),
                        rs.getString("NgayLap"),
                        rs.getString("TongTien"),
                        rs.getInt("TrangThai"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 4. Cập nhật thông tin phiếu phạt
    public boolean update(PhieuPhatDTO pp) {
        String sql = "UPDATE PHIEUPHAT SET MaPM=?, MaNQL=?, NgayLap=?, TongTien=? WHERE MaPP=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pp.getMaPM());
            ps.setString(2, pp.getMaNQL());
            ps.setString(3, pp.getNgayLap());
            ps.setString(4, pp.getTongTien());
            ps.setString(5, pp.getMaPP());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5. Hủy phiếu (TrangThai = 2)
    public boolean delete(String maPP) {
        String sql = "UPDATE PHIEUPHAT SET TrangThai = 2 WHERE MaPP = ? AND TrangThai = 0";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPP);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 6. Xác nhận thanh toán (TrangThai: 0 -> 1)
    public boolean thanhToan(String maPP) {
        String sql = "UPDATE PHIEUPHAT SET TrangThai = 1 WHERE MaPP = ? AND TrangThai = 0";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPP);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 7. Lấy danh sách hiển thị GUI (JOIN lấy lý do từ chi tiết)
    public ArrayList<Object[]> getDanhSachHienThiGUI() {
        ArrayList<Object[]> list = new ArrayList<>();
        String sql = "SELECT p.MaPP, p.MaPM, p.NgayLap, "
                + "c.LyDo, p.TongTien, p.TrangThai "
                + "FROM PHIEUPHAT p "
                + "LEFT JOIN CHITIETPHIEUPHAT c ON p.MaPP = c.MaPP AND c.TrangThai = 1 "
                + "WHERE p.TrangThai < 2 "
                + "ORDER BY p.NgayLap DESC";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String maPP = rs.getString("MaPP");
                String maPM = rs.getString("MaPM");
                String ngayLap = rs.getString("NgayLap");
                String lyDo = rs.getString("LyDo");
                if (lyDo == null) lyDo = "Chưa có chi tiết";

                double tien = rs.getDouble("TongTien");
                String soTien = String.format("%,.0f", tien);

                int tt = rs.getInt("TrangThai");
                String trangThai = (tt == 1) ? "Đã thanh toán" : "Chưa thanh toán";

                list.add(new Object[]{maPP, maPM, ngayLap, lyDo, soTien, trangThai});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 8. Sinh mã phiếu phạt tự động
    public String generateMaPP() {
        String sql = "SELECT MaPP FROM PHIEUPHAT ORDER BY MaPP DESC";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                String lastMa = rs.getString("MaPP"); // VD: PP00000003
                int num = Integer.parseInt(lastMa.substring(2)) + 1;
                return String.format("PP%08d", num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "PP00000001";
    }
}