package DAO;

import DTO.ChiTietPhieuMuonDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietPhieuMuonDAO {

    /** BUS cũ gọi insert() — giữ nguyên tên */
    public boolean insert(ChiTietPhieuMuonDTO ct) {
        return add(ct);
    }

    /** add() — tên mới, dùng trong code mới */
    public boolean add(ChiTietPhieuMuonDTO ct) {
        String sql = "INSERT INTO CHITIETPHIEUMUON (MaPM, MaCuonSach, TinhTrangSach) VALUES (?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ct.getMaPM());
            ps.setString(2, ct.getMaCuonSach());
            ps.setString(3, ct.getTinhTrangSach());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    /** Lấy danh sách cuốn sách trong 1 phiếu mượn — dùng trong TraSachDialog */
    public List<ChiTietPhieuMuonDTO> getByMaPM(String maPM) {
        List<ChiTietPhieuMuonDTO> list = new ArrayList<>();
        String sql = "SELECT ct.MaPM, ct.MaCuonSach, ct.TinhTrangSach " +
                     "FROM CHITIETPHIEUMUON ct WHERE ct.MaPM = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maPM);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChiTietPhieuMuonDTO ct = new ChiTietPhieuMuonDTO();
                    ct.setMaPM(rs.getString("MaPM"));
                    ct.setMaCuonSach(rs.getString("MaCuonSach"));
                    ct.setTinhTrangSach(rs.getString("TinhTrangSach"));
                    list.add(ct);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    /** Cập nhật tình trạng sách trong phiếu mượn (ghi lại khi trả) */
    public boolean updateTinhTrang(String maPM, String maCuonSach, String tinhTrang) {
        String sql = "UPDATE CHITIETPHIEUMUON SET TinhTrangSach = ? WHERE MaPM = ? AND MaCuonSach = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tinhTrang);
            ps.setString(2, maPM);
            ps.setString(3, maCuonSach);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}