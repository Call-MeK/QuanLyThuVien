package DAO;

import DTO.PhieuPhatDTO;
import java.sql.*;
import java.util.ArrayList;

public class PhieuPhatDAO {

    public String add(PhieuPhatDTO pp) {
        String sql = "INSERT INTO PHIEUPHAT (MaPP, MaPM, MaNQL, NgayLap, TongTien, TrangThai) VALUES (?, ?, ?, ?, ?, 0)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pp.getMaPP());
            ps.setString(2, pp.getMaPM());
            ps.setString(3, pp.getMaNQL());
            ps.setDate(4, java.sql.Date.valueOf(pp.getNgayLap()));
            ps.setDouble(5, Double.parseDouble(pp.getTongTien()));
            int rows = ps.executeUpdate();
            return rows > 0 ? "OK" : "Không có dòng nào được thêm";
        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi DB: " + e.getMessage();
        }
    }

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
                if (lyDo == null)
                    lyDo = "Chưa có chi tiết";

                double tien = rs.getDouble("TongTien");
                String soTien = String.format("%,.0f", tien);

                int tt = rs.getInt("TrangThai");
                String trangThai = (tt == 1) ? "Đã thanh toán" : "Chưa thanh toán";

                list.add(new Object[] { maPP, maPM, ngayLap, lyDo, soTien, trangThai });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Object[]> getDanhSachBaoGomDaHuy() {
        ArrayList<Object[]> list = new ArrayList<>();
        String sql = "SELECT p.MaPP, p.MaPM, p.NgayLap, "
                + "c.LyDo, p.TongTien, p.TrangThai "
                + "FROM PHIEUPHAT p "
                + "LEFT JOIN CHITIETPHIEUPHAT c ON p.MaPP = c.MaPP AND c.TrangThai = 1 "
                + "ORDER BY p.NgayLap DESC";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String maPP = rs.getString("MaPP");
                String maPM = rs.getString("MaPM");
                String ngayLap = rs.getString("NgayLap");
                String lyDo = rs.getString("LyDo");
                if (lyDo == null)
                    lyDo = "Chưa có chi tiết";

                double tien = rs.getDouble("TongTien");
                String soTien = String.format("%,.0f", tien);

                int tt = rs.getInt("TrangThai");
                String trangThai;
                if (tt == 1)
                    trangThai = "Đã thanh toán";
                else if (tt == 2)
                    trangThai = "Đã hủy";
                else
                    trangThai = "Chưa thanh toán";

                list.add(new Object[] { maPP, maPM, ngayLap, lyDo, soTien, trangThai });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String generateMaPP() {
        String sql = "SELECT MaPP FROM PHIEUPHAT ORDER BY MaPP DESC";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                String lastMa = rs.getString("MaPP");
                int num = Integer.parseInt(lastMa.substring(2)) + 1;
                return String.format("PP%02d", num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "PP01";
    }

    public ArrayList<Object[]> getDanhSachHienThiGUIByMaDocGia(String maDocGia) {
        ArrayList<Object[]> list = new ArrayList<>();
        String sql = "SELECT p.MaPP, p.MaPM, p.NgayLap, "
                + "c.LyDo, p.TongTien, p.TrangThai "
                + "FROM PHIEUPHAT p "
                + "LEFT JOIN CHITIETPHIEUPHAT c ON p.MaPP = c.MaPP AND c.TrangThai = 1 "
                + "JOIN PHIEUMUON pm ON p.MaPM = pm.MaPM "
                + "JOIN THETHUVIEN t ON pm.MaThe = t.MaThe "
                + "WHERE t.MaDocGia = ? AND p.TrangThai < 2 "
                + "ORDER BY p.NgayLap DESC";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maDocGia);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String maPP = rs.getString("MaPP");
                String maPM = rs.getString("MaPM");
                String ngayLap = rs.getString("NgayLap");
                String lyDo = rs.getString("LyDo");
                if (lyDo == null)
                    lyDo = "Chưa có chi tiết";

                double tien = rs.getDouble("TongTien");
                String soTien = String.format("%,.0f", tien);

                int tt = rs.getInt("TrangThai");
                String trangThai = (tt == 1) ? "Đã thanh toán" : "Chưa thanh toán";

                list.add(new Object[] { maPP, maPM, ngayLap, lyDo, soTien, trangThai });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}