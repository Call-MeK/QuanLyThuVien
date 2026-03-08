package DAO;

import DTO.ChiTietPhieuPhatDTO;
import java.sql.*;
import java.util.ArrayList;

public class ChiTietPhieuPhatDAO {

    public String addWithMessage(ChiTietPhieuPhatDTO ct) {
        String sql = "INSERT INTO CHITIETPHIEUPHAT "
                + "(MaCTPP, MaPP, MaCuonSach, LyDo, SoTien, TrangThai) "
                + "VALUES (?, ?, ?, ?, ?, 1)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ct.getMaCTPP());
            ps.setString(2, ct.getMaPP());
            ps.setString(3, ct.getMaCuonSach() != null ? ct.getMaCuonSach() : "");
            ps.setString(4, ct.getLyDo());
            ps.setString(5, ct.getSoTien());
            int rows = ps.executeUpdate();
            return rows > 0 ? "OK" : "0 dòng được thêm";
        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi DB: " + e.getMessage();
        }
    }

    public boolean add(ChiTietPhieuPhatDTO ct) {
        return addWithMessage(ct).equals("OK");
    }

    public ArrayList<ChiTietPhieuPhatDTO> getAll() {
        ArrayList<ChiTietPhieuPhatDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM CHITIETPHIEUPHAT WHERE TrangThai = 1";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                ChiTietPhieuPhatDTO ct = new ChiTietPhieuPhatDTO(
                        rs.getString("MaCTPP"),
                        rs.getString("MaPP"),
                        rs.getString("MaCuonSach"),
                        rs.getString("LyDo"),
                        rs.getString("SoTien"),
                        rs.getInt("TrangThai"));
                list.add(ct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<ChiTietPhieuPhatDTO> getByMaPP(String maPP) {
        ArrayList<ChiTietPhieuPhatDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM CHITIETPHIEUPHAT WHERE MaPP = ? AND TrangThai = 1";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPP);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietPhieuPhatDTO ct = new ChiTietPhieuPhatDTO(
                        rs.getString("MaCTPP"),
                        rs.getString("MaPP"),
                        rs.getString("MaCuonSach"),
                        rs.getString("LyDo"),
                        rs.getString("SoTien"),
                        rs.getInt("TrangThai"));
                list.add(ct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean update(ChiTietPhieuPhatDTO ct) {
        String sql = "UPDATE CHITIETPHIEUPHAT "
                + "SET LyDo=?, SoTien=?, MaCuonSach=? "
                + "WHERE MaCTPP=? AND TrangThai = 1";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ct.getLyDo());
            ps.setString(2, ct.getSoTien());
            ps.setString(3, ct.getMaCuonSach() != null ? ct.getMaCuonSach() : "");
            ps.setString(4, ct.getMaCTPP());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateByMaPP(String maPP, String lyDo, String soTien) {
        String sql = "UPDATE CHITIETPHIEUPHAT SET LyDo=?, SoTien=? WHERE MaPP=? AND TrangThai = 1";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, lyDo);
            ps.setString(2, soTien);
            ps.setString(3, maPP);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String maCTPP) {
        String sql = "UPDATE CHITIETPHIEUPHAT SET TrangThai = 0 WHERE MaCTPP=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maCTPP);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<String> getDanhSachMaSachByMaPM(String maPM) {
        ArrayList<String> dsMaSach = new ArrayList<>();
        String sql = "SELECT MaCuonSach FROM CHITIETPHIEUMUON WHERE MaPM = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, maPM);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                dsMaSach.add(rs.getString("MaCuonSach"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsMaSach;
    }

    public ArrayList<Object[]> getChiTietCoTenSachByMaPP(String maPP) {
        ArrayList<Object[]> list = new ArrayList<>();
        String sql = "SELECT c.MaCuonSach, s.TenSach, c.LyDo, c.SoTien "
                + "FROM CHITIETPHIEUPHAT c "
                + "JOIN SACHCOPY sc ON c.MaCuonSach = sc.MaVach "
                + "JOIN SACH s ON sc.MaSach = s.MaSach "
                + "WHERE c.MaPP = ? AND c.TrangThai = 1";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maPP);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String maSach = rs.getString("MaCuonSach");
                String tenSach = rs.getString("TenSach");
                String lyDo = rs.getString("LyDo");
                double tien = rs.getDouble("SoTien");
                String soTien = String.format("%,.0f", tien);

                list.add(new Object[] { maSach, tenSach, lyDo, soTien });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}