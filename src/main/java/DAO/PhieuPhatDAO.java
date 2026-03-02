package DAO;

import DTO.PhieuPhatDTO;
import java.sql.*;
import java.util.ArrayList;

public class PhieuPhatDAO {

    // 1. Thêm phiếu phạt (Đã thêm TrangThai = 1 mặc định)
    public boolean add(PhieuPhatDTO pp) {
        String sql = "INSERT INTO PHIEUPHAT (MaPP, MaPM, MaNQL, NgayLap, TongTien, TrangThai) VALUES (?, ?, ?, ?, ?, 1)";

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

    // 2. Lấy tất cả phiếu phạt CHƯA BỊ XÓA (TrangThai = 1)
    public ArrayList<PhieuPhatDTO> getAll() {
        ArrayList<PhieuPhatDTO> list = new ArrayList<>();
        // Đã sửa SQL ở đây
        String sql = "SELECT * FROM PHIEUPHAT WHERE TrangThai = 1";

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

    // 3. Tìm theo mã (Cũng phải kiểm tra xem mã đó chưa bị xóa)
    public PhieuPhatDTO getById(String maPP) {
        // Đã sửa SQL ở đây
        String sql = "SELECT * FROM PHIEUPHAT WHERE MaPP = ? AND TrangThai = 1";

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

    // 5. Xoá Mềm (Cập nhật TrangThai = 0)
    public boolean delete(String maPP) {
        String sql = "UPDATE PHIEUPHAT SET TrangThai = 0 WHERE MaPP=?";

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