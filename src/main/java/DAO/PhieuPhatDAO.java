package DAO;

import DTO.PhieuPhatDTO;
import java.sql.*;
import java.util.ArrayList;

public class PhieuPhatDAO {

    // 1. Thêm phiếu phạt
    public boolean add(PhieuPhatDTO pp) {
        String sql = "INSERT INTO PHIEUPHAT (MaPP, MaPM, MaNQL, NgayLap, TongTien) VALUES (?, ?, ?, ?, ?)";

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

    // 2. Lấy tất cả phiếu phạt
    public ArrayList<PhieuPhatDTO> getAll() {
        ArrayList<PhieuPhatDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM PHIEUPHAT";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                PhieuPhatDTO pp = new PhieuPhatDTO(
                        rs.getString("MaPP"),
                        rs.getString("MaPM"),
                        rs.getString("MaNQL"),
                        rs.getString("NgayLap"),
                        rs.getString("TongTien"));
                list.add(pp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3. Tìm theo mã
    public PhieuPhatDTO getById(String maPP) {
        String sql = "SELECT * FROM PHIEUPHAT WHERE MaPP = ?";

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
                        rs.getString("TongTien"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 4. Cập nhật
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

    // 5. Xoá
    public boolean delete(String maPP) {
        String sql = "DELETE FROM PHIEUPHAT WHERE MaPP=?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maPP);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}