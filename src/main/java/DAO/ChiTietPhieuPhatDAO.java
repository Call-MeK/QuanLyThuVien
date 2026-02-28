package DAO;

import DTO.ChiTietPhieuPhatDTO;
import java.sql.*;
import java.util.ArrayList;

public class ChiTietPhieuPhatDAO {

    String url = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyThuVien";
    String user = "sa";
    String pass = "12345"; // đổi nếu khác

    // 1. Thêm chi tiết phiếu phạt
    public boolean add(ChiTietPhieuPhatDTO ct) {
        String sql = "INSERT INTO CHITIETPHIEUPHAT "
                + "(MaCTPP, MaPP, MaCuonSach, LyDo, SoTien) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ct.getMaCTPP());
            ps.setString(2, ct.getMaPP());
            ps.setString(3, ct.getMaCuonSach());
            ps.setString(4, ct.getLyDo());
            ps.setString(5, ct.getSoTien());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 2. Lấy tất cả chi tiết
    public ArrayList<ChiTietPhieuPhatDTO> getAll() {
        ArrayList<ChiTietPhieuPhatDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM CHITIETPHIEUPHAT";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                ChiTietPhieuPhatDTO ct = new ChiTietPhieuPhatDTO(
                        rs.getString("MaCTPP"),
                        rs.getString("MaPP"),
                        rs.getString("MaCuonSach"),
                        rs.getString("LyDo"),
                        rs.getString("SoTien")
                );
                list.add(ct);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3. Lấy theo MaPP
    public ArrayList<ChiTietPhieuPhatDTO> getByMaPP(String maPP) {
        ArrayList<ChiTietPhieuPhatDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM CHITIETPHIEUPHAT WHERE MaPP = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maPP);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ChiTietPhieuPhatDTO ct = new ChiTietPhieuPhatDTO(
                        rs.getString("MaCTPP"),
                        rs.getString("MaPP"),
                        rs.getString("MaCuonSach"),
                        rs.getString("LyDo"),
                        rs.getString("SoTien")
                );
                list.add(ct);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 4. Cập nhật
    public boolean update(ChiTietPhieuPhatDTO ct) {
        String sql = "UPDATE CHITIETPHIEUPHAT "
                + "SET MaPP=?, MaCuonSach=?, LyDo=?, SoTien=? "
                + "WHERE MaCTPP=?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ct.getMaPP());
            ps.setString(2, ct.getMaCuonSach());
            ps.setString(3, ct.getLyDo());
            ps.setString(4, ct.getSoTien());
            ps.setString(5, ct.getMaCTPP());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5. Xoá
    public boolean delete(String maCTPP) {
        String sql = "DELETE FROM CHITIETPHIEUPHAT WHERE MaCTPP=?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maCTPP);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}