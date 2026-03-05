package DAO;

import DTO.ChiTietPhieuMuonDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ChiTietPhieuMuonDAO {
    // Hàm thêm chi tiết phiếu mượn vào DB
    public boolean insert(ChiTietPhieuMuonDTO ctpm) {
        String sql = "INSERT INTO CHITIETPHIEUMUON (MaPM, MaCuonSach, TinhTrangSach) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ctpm.getMaPM());
            ps.setString(2, ctpm.getMaCuonSach());
            ps.setString(3, ctpm.getTinhTrangSach());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}