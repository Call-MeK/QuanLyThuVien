package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class ThongKeDAO {

    public Map<String, Integer> thongKeSachTheoTheLoai() {
        Map<String, Integer> result = new LinkedHashMap<>();
        String sql = "SELECT tl.TenTheLoai, COUNT(*) AS SoLuong "
                + "FROM SACH s JOIN THELOAI tl ON s.TheLoai = tl.MaTheLoai "
                + "WHERE s.isdeleted = 0 "
                + "GROUP BY tl.TenTheLoai "
                + "ORDER BY SoLuong DESC";

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result.put(rs.getString("TenTheLoai"), rs.getInt("SoLuong"));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Map<String, Integer> thongKeTop5DocGiaMuonNhieu() {
        Map<String, Integer> result = new LinkedHashMap<>();
        String sql = "SELECT TOP 5 dg.MaDocGia, cn.HoTen, COUNT(ct.MaCuonSach) AS SoLuongMuon "
                + "FROM CHITIETPHIEUMUON ct "
                + "JOIN PHIEUMUON pm ON ct.MaPM = pm.MaPM "
                + "JOIN THETHUVIEN ttv ON pm.MaThe = ttv.MaThe "
                + "JOIN DOCGIA dg ON ttv.MaDocGia = dg.MaDocGia "
                + "JOIN CONNGUOI cn ON dg.MaDocGia = cn.MaNguoi "
                + "GROUP BY dg.MaDocGia, cn.HoTen "
                + "ORDER BY SoLuongMuon DESC";

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String key = rs.getString("MaDocGia") + " - " + rs.getString("HoTen");
                result.put(key, rs.getInt("SoLuongMuon"));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Map<String, Integer> thongKeSachMuonNhieuNhat() {
        Map<String, Integer> result = new LinkedHashMap<>();
        String sql = "SELECT TOP 10 s.MaSach, s.tenSach, COUNT(ct.MaCuonSach) AS SoLanMuon "
                + "FROM CHITIETPHIEUMUON ct "
                + "JOIN SACHCOPY sc ON ct.MaCuonSach = sc.MaVach "
                + "JOIN SACH s ON sc.MaSach = s.MaSach "
                + "WHERE s.isdeleted = 0 "
                + "GROUP BY s.MaSach, s.tenSach "
                + "ORDER BY SoLanMuon DESC";

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String key = rs.getString("MaSach") + " - " + rs.getString("tenSach");
                result.put(key, rs.getInt("SoLanMuon"));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
