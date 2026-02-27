public class chitietphieumuonDAO {

    // Thêm chi tiết
    public boolean insert(ChiTietPhieuMuon ct) {
        String sql = "INSERT INTO ChiTietPhieuMuon VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ct.getMaPM());
            ps.setString(2, ct.getMaSach());
            ps.setInt(3, ct.getSoLuong());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy danh sách chi tiết theo mã phiếu
    public List<ChiTietPhieuMuon> findByMaPM(String maPM) {
        List<ChiTietPhieuMuon> list = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietPhieuMuon WHERE maPM=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maPM);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new ChiTietPhieuMuon(
                        rs.getString("maPM"),
                        rs.getString("maSach"),
                        rs.getInt("soLuong")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}