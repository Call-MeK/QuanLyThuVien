package DAO;

import DTO.ChiTietPhieuPhatDTO;
import java.sql.*;
import java.util.ArrayList;

public class ChiTietPhieuPhatDAO {

    // 1. Thêm chi tiết (Mặc định TrangThai = 1)
    public boolean add(ChiTietPhieuPhatDTO ct) {
        String sql = "INSERT INTO CHITIETPHIEUPHAT "
                + "(MaCTPP, MaPP, MaCuonSach, LyDo, SoTien, TrangThai) "
                + "VALUES (?, ?, ?, ?, ?, 1)";

        try (Connection conn = DatabaseConnection.getConnection();
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

    // 2. Lấy tất cả chi tiết CHƯA BỊ XÓA
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
                        rs.getString("SoTien"));
                list.add(ct);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3. Lấy theo MaPP (Chỉ lấy chi tiết chưa bị xóa)
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
                        rs.getString("SoTien"));
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

        try (Connection conn = DatabaseConnection.getConnection();
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

    // 5. Xoá mềm (Đổi TrangThai = 0)
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
}