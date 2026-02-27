public class phieumuonDAO {

    public boolean insert(PhieuMuon pm) {
        String sql = "INSERT INTO PhieuMuon VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pm.getMaPM());
            ps.setString(2, pm.getMaDocGia());
            ps.setDate(3, new java.sql.Date(pm.getNgayMuon().getTime()));
            ps.setDate(4, new java.sql.Date(pm.getHanTra().getTime()));
            ps.setString(5, pm.getTrangThai());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 2️⃣ Cập nhật trạng thái
    public boolean updateTrangThai(String maPM, String trangThai) {
        String sql = "UPDATE PhieuMuon SET trangThai=? WHERE maPM=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, trangThai);
            ps.setString(2, maPM);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 3️⃣ Xóa phiếu mượn
    public boolean delete(String maPM) {
        String sql = "DELETE FROM PhieuMuon WHERE maPM=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maPM);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4️⃣ Tìm theo mã
    public PhieuMuon findById(String maPM) {
        String sql = "SELECT * FROM PhieuMuon WHERE maPM=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maPM);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new PhieuMuon(
                        rs.getString("maPM"),
                        rs.getString("maDocGia"),
                        rs.getDate("ngayMuon"),
                        rs.getDate("hanTra"),
                        rs.getString("trangThai")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 5️⃣ Lấy tất cả
    public List<PhieuMuon> findAll() {
        List<PhieuMuon> list = new ArrayList<>();
        String sql = "SELECT * FROM PhieuMuon";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new PhieuMuon(
                        rs.getString("maPM"),
                        rs.getString("maDocGia"),
                        rs.getDate("ngayMuon"),
                        rs.getDate("hanTra"),
                        rs.getString("trangThai")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}