package DAO;

import DTO.ChiTietPhieuNhapDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ChiTietPhieuNhapDAO {

    public boolean insert(ChiTietPhieuNhapDTO ct) {
        String sql = "INSERT INTO CHITIETPHIEUNHAP (MaPN, MaSach, SoLuongNhap, DonGia) VALUES (?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ct.getMaPN());
            ps.setString(2, ct.getMaSach());
            ps.setInt(3, ct.getSoLuongNhap());
            ps.setFloat(4, ct.getDonGia());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<ChiTietPhieuNhapDTO> findByMaPN(String maPN) {
        List<ChiTietPhieuNhapDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM CHITIETPHIEUNHAP WHERE MaPN = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maPN);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new ChiTietPhieuNhapDTO(
                            rs.getString("MaPN"),
                            rs.getString("MaSach"),
                            rs.getInt("SoLuongNhap"),
                            rs.getFloat("DonGia")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean update(ChiTietPhieuNhapDTO ct) {
        String sql = "UPDATE CHITIETPHIEUNHAP SET SoLuongNhap = ?, DonGia = ? WHERE MaPN = ? AND MaSach = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, ct.getSoLuongNhap());
            ps.setFloat(2, ct.getDonGia());
            ps.setString(3, ct.getMaPN());
            ps.setString(4, ct.getMaSach());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteSpecific(String maPN, String maSach) {
        String sql = "DELETE FROM CHITIETPHIEUNHAP WHERE MaPN = ? AND MaSach = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maPN);
            ps.setString(2, maSach);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAllByMaPN(String maPN) {
        String sql = "DELETE FROM CHITIETPHIEUNHAP WHERE MaPN = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maPN);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}