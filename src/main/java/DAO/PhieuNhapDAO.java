package DAO;

import DTO.PhieuNhapDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PhieuNhapDAO {

    public boolean insert(PhieuNhapDTO pn) {
        String sql = "INSERT INTO PHIEUNHAP (MaPN, NgayNhap, TongTien, MaNXB, MaNQL) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pn.getMaPN());
            ps.setString(2, pn.getNgayNhap());
            ps.setFloat(3, pn.getTongTien());
            ps.setString(4, pn.getMaNXB());
            ps.setString(5, pn.getMaNQL());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(PhieuNhapDTO pn) {
        String sql = "UPDATE PHIEUNHAP SET NgayNhap = ?, TongTien = ?, MaNXB = ?, MaNQL = ? WHERE MaPN = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pn.getNgayNhap());
            ps.setFloat(2, pn.getTongTien());
            ps.setString(3, pn.getMaNXB());
            ps.setString(4, pn.getMaNQL());
            ps.setString(5, pn.getMaPN());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String maPN) {
        String sql = "DELETE FROM PHIEUNHAP WHERE MaPN = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maPN);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public PhieuNhapDTO findById(String maPN) {
        String sql = "SELECT * FROM PHIEUNHAP WHERE MaPN = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maPN);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new PhieuNhapDTO(
                            rs.getString("MaPN"),
                            rs.getString("NgayNhap"),
                            rs.getFloat("TongTien"),
                            rs.getString("MaNXB"),
                            rs.getString("MaNQL")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<PhieuNhapDTO> findAll() {
        List<PhieuNhapDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM PHIEUNHAP";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new PhieuNhapDTO(
                        rs.getString("MaPN"),
                        rs.getString("NgayNhap"),
                        rs.getFloat("TongTien"),
                        rs.getString("MaNXB"),
                        rs.getString("MaNQL")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String generateMaPN() {
        String newMaPN = "PN001";
        String sql = "SELECT MaPN FROM PHIEUNHAP";
        
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int maxId = 0;
            while (rs.next()) {
                String maPN = rs.getString("MaPN");
                if (maPN != null && maPN.startsWith("PN")) {
                    try {
                        int id = Integer.parseInt(maPN.substring(2));
                        if (id > maxId) {
                            maxId = id;
                        }
                    } catch (NumberFormatException e) {
                        // Ignore
                    }
                }
            }
            if (maxId > 0) {
                newMaPN = String.format("PN%03d", maxId + 1); // Đổi "%03d" thành "%04d" nếu form mã của bạn là PN0001
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newMaPN;
    }
}