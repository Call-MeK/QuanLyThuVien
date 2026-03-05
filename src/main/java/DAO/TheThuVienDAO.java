package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import DTO.TheThuVienDTO;

public class TheThuVienDAO {

    public ArrayList<TheThuVienDTO> getAll() {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM THETHUVIEN";
        ArrayList<TheThuVienDTO> list = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            rs.close(); stmt.close();
            return list;
        } catch (Exception e) { e.printStackTrace(); }
        finally { try { conn.close(); } catch (Exception e) { e.printStackTrace(); } }
        return null;
    }

    public TheThuVienDTO getById(String maThe) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM THETHUVIEN WHERE RTRIM(MaThe) = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maThe.trim());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return mapRow(rs);
            rs.close(); stmt.close();
        } catch (Exception e) { e.printStackTrace(); }
        finally { try { conn.close(); } catch (Exception e) { e.printStackTrace(); } }
        return null;
    }

    /**
     * ✅ Method còn thiếu — tìm thẻ theo MaDocGia.
     * Dùng RTRIM để xử lý kiểu CHAR có khoảng trắng thừa.
     */
    public TheThuVienDTO getByMaDocGia(String maDocGia) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM THETHUVIEN WHERE RTRIM(MaDocGia) = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maDocGia.trim());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return mapRow(rs);
            rs.close(); stmt.close();
        } catch (Exception e) { e.printStackTrace(); }
        finally { try { conn.close(); } catch (Exception e) { e.printStackTrace(); } }
        return null;
    }

    public boolean add(TheThuVienDTO the) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO THETHUVIEN (MaThe, TenThe, MaDocGia, NgayCap, NgayHetHan, MaNQL) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, the.getMaThe());
            stmt.setString(2, the.getTenThe());
            stmt.setString(3, the.getMaDocGia());
            stmt.setString(4, the.getNgayCap());
            stmt.setString(5, the.getNgayHetHan());
            stmt.setString(6, the.getMaNQL());
            int result = stmt.executeUpdate();
            stmt.close();
            return result > 0;
        } catch (Exception e) { e.printStackTrace(); }
        finally { try { conn.close(); } catch (Exception e) { e.printStackTrace(); } }
        return false;
    }

    public boolean update(TheThuVienDTO the) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "UPDATE THETHUVIEN SET TenThe=?, MaDocGia=?, NgayCap=?, NgayHetHan=?, MaNQL=? WHERE RTRIM(MaThe)=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, the.getTenThe());
            stmt.setString(2, the.getMaDocGia());
            stmt.setString(3, the.getNgayCap());
            stmt.setString(4, the.getNgayHetHan());
            stmt.setString(5, the.getMaNQL());
            stmt.setString(6, the.getMaThe().trim());
            int result = stmt.executeUpdate();
            stmt.close();
            return result > 0;
        } catch (Exception e) { e.printStackTrace(); }
        finally { try { conn.close(); } catch (Exception e) { e.printStackTrace(); } }
        return false;
    }

    public boolean delete(String maThe) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "DELETE FROM THETHUVIEN WHERE RTRIM(MaThe) = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maThe.trim());
            int result = stmt.executeUpdate();
            stmt.close();
            return result > 0;
        } catch (Exception e) { e.printStackTrace(); }
        finally { try { conn.close(); } catch (Exception e) { e.printStackTrace(); } }
        return false;
    }

    // ===== Helper =====
    private TheThuVienDTO mapRow(ResultSet rs) throws Exception {
        return new TheThuVienDTO(
                rs.getString("MaThe")      != null ? rs.getString("MaThe").trim()      : "",
                rs.getString("TenThe")     != null ? rs.getString("TenThe").trim()     : "",
                rs.getString("MaDocGia")   != null ? rs.getString("MaDocGia").trim()   : "",
                rs.getString("NgayCap")    != null ? rs.getString("NgayCap")           : "",
                rs.getString("NgayHetHan") != null ? rs.getString("NgayHetHan")        : "",
                rs.getString("MaNQL")      != null ? rs.getString("MaNQL").trim()      : "");
    }
}