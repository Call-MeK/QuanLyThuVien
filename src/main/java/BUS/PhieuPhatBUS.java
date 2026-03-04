package BUS;

import DAO.ChiTietPhieuPhatDAO;
import DAO.PhieuPhatDAO;
import DTO.ChiTietPhieuPhatDTO;
import DTO.PhieuPhatDTO;
import java.util.ArrayList;

public class PhieuPhatBUS {
    private PhieuPhatDAO phieuPhatDAO;
    private ChiTietPhieuPhatDAO chiTietDAO;
    private ArrayList<PhieuPhatDTO> listPP;

    // TrangThai: 0 = Chưa thanh toán, 1 = Đã thanh toán, 2 = Đã hủy

    public PhieuPhatBUS() {
        phieuPhatDAO = new PhieuPhatDAO();
        chiTietDAO = new ChiTietPhieuPhatDAO();
        listPP = phieuPhatDAO.getAll();
    }

    public ArrayList<PhieuPhatDTO> getAll() {
        return phieuPhatDAO.getAll();
    }

    public ArrayList<PhieuPhatDTO> getListPP() {
        return listPP;
    }

    public void reloadFromDB() {
        listPP = phieuPhatDAO.getAll();
    }

    public PhieuPhatDTO getById(String maPP) {
        return phieuPhatDAO.getById(maPP);
    }

    public ArrayList<ChiTietPhieuPhatDTO> getChiTiet(String maPP) {
        return chiTietDAO.getByMaPP(maPP);
    }

    public String updatePhieuPhat(PhieuPhatDTO pp) {
        if (phieuPhatDAO.update(pp)) {
            reloadFromDB();
            return "Cập nhật phiếu phạt thành công";
        }
        return "Cập nhật phiếu phạt thất bại";
    }

    // Xác nhận thanh toán (0 -> 1)
    public String thanhToan(String maPP) {
        for (PhieuPhatDTO pp : listPP) {
            if (pp.getMaPP().equals(maPP)) {
                if (pp.getTrangThai() == 1) {
                    return "Phiếu phạt này đã được thanh toán rồi";
                }
                break;
            }
        }
        if (phieuPhatDAO.thanhToan(maPP)) {
            for (PhieuPhatDTO pp : listPP) {
                if (pp.getMaPP().equals(maPP)) {
                    pp.setTrangThai(1);
                    break;
                }
            }
            return "Xác nhận thanh toán thành công";
        }
        return "Xác nhận thanh toán thất bại";
    }

    // Hủy phiếu (0 -> 2), chỉ hủy được phiếu chưa thanh toán
    public String deletePhieuPhat(String maPP) {
        for (PhieuPhatDTO pp : listPP) {
            if (pp.getMaPP().equals(maPP)) {
                if (pp.getTrangThai() == 1) {
                    return "Không thể hủy phiếu đã thanh toán";
                }
                break;
            }
        }
        if (phieuPhatDAO.delete(maPP)) {
            listPP.removeIf(pp -> pp.getMaPP().equals(maPP));
            return "Hủy phiếu phạt thành công";
        }
        return "Hủy phiếu phạt thất bại";
    }

    // Tạo phiếu phạt (header + chi tiết)
    public String taoPhieuPhat(PhieuPhatDTO phieuPhat, ArrayList<ChiTietPhieuPhatDTO> dsChiTiet) {
        double tongTien = 0;
        for (ChiTietPhieuPhatDTO ct : dsChiTiet) {
            tongTien += Double.parseDouble(ct.getSoTien());
        }
        phieuPhat.setTongTien(String.valueOf(tongTien));

        if (phieuPhatDAO.add(phieuPhat)) {
            for (ChiTietPhieuPhatDTO ct : dsChiTiet) {
                ct.setMaPP(phieuPhat.getMaPP());
                chiTietDAO.add(ct);
            }
            listPP.add(phieuPhat);
            return "Tạo phiếu phạt thành công";
        }
        return "Tạo phiếu phạt thất bại";
    }

    public ArrayList<Object[]> getDanhSachHienThiGUI() {
        return phieuPhatDAO.getDanhSachHienThiGUI();
    }

    public String generateMaPP() {
        return phieuPhatDAO.generateMaPP();
    }

    // Tìm kiếm
    public ArrayList<Object[]> search(String keyword, String criteria) {
        ArrayList<Object[]> all = getDanhSachHienThiGUI();
        if (keyword == null || keyword.trim().isEmpty()) return all;

        ArrayList<Object[]> result = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase().trim();

        for (Object[] row : all) {
            // row: [0]MaPP, [1]MaPM, [2]NgayLap, [3]LyDo, [4]SoTien, [5]TrangThai
            String maPP = row[0] != null ? row[0].toString().toLowerCase() : "";
            String maPM = row[1] != null ? row[1].toString().toLowerCase() : "";
            String lyDo = row[3] != null ? row[3].toString().toLowerCase() : "";
            String trangThai = row[5] != null ? row[5].toString().toLowerCase() : "";

            boolean match = false;
            switch (criteria) {
                case "Tất cả":
                    match = maPP.contains(lowerKeyword) || maPM.contains(lowerKeyword) ||
                            lyDo.contains(lowerKeyword) || trangThai.contains(lowerKeyword);
                    break;
                case "Mã Phạt":
                    match = maPP.contains(lowerKeyword);
                    break;
                case "Mã Phiếu Mượn":
                    match = maPM.contains(lowerKeyword);
                    break;
                case "Trạng Thái":
                    match = trangThai.contains(lowerKeyword);
                    break;
            }
            if (match) result.add(row);
        }
        return result;
    }
}