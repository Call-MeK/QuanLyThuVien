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

    public ArrayList<Object[]> getDanhSachHienThiGUI() {
        return phieuPhatDAO.getDanhSachHienThiGUI();
    }

    public ArrayList<Object[]> getDanhSachHienThiGUIByMaDocGia(String maDocGia) {
        return phieuPhatDAO.getDanhSachHienThiGUIByMaDocGia(maDocGia);
    }

    public String generateMaPP() {
        return phieuPhatDAO.generateMaPP();
    }

    private int getTrangThai(String maPP) {
        for (PhieuPhatDTO pp : listPP) {
            if (pp.getMaPP().equals(maPP))
                return pp.getTrangThai();
        }
        return -1;
    }

    public String thanhToan(String maPP) {
        int tt = getTrangThai(maPP);
        if (tt == -1)
            return "Không tìm thấy phiếu phạt";
        if (tt == 1)
            return "Phiếu này đã thanh toán rồi";

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

    public String deletePhieuPhat(String maPP) {
        int tt = getTrangThai(maPP);
        if (tt == -1)
            return "Không tìm thấy phiếu phạt";
        if (tt == 1)
            return "Không thể hủy phiếu đã thanh toán";

        if (phieuPhatDAO.delete(maPP)) {
            listPP.removeIf(pp -> pp.getMaPP().equals(maPP));
            return "Hủy phiếu phạt thành công";
        }
        return "Hủy phiếu phạt thất bại";
    }

    public String capNhatPhieuPhat(String maPP, String lyDoMoi, double soTienMoi) {
        int tt = getTrangThai(maPP);
        if (tt == -1)
            return "Không tìm thấy phiếu phạt";
        if (tt == 1)
            return "Không thể sửa phiếu đã thanh toán";

        PhieuPhatDTO target = null;
        for (PhieuPhatDTO pp : listPP) {
            if (pp.getMaPP().equals(maPP)) {
                target = pp;
                break;
            }
        }
        target.setTongTien(String.valueOf(soTienMoi));
        if (!phieuPhatDAO.update(target))
            return "Cập nhật phiếu phạt thất bại";

        boolean ctUpdated = chiTietDAO.updateByMaPP(maPP, lyDoMoi, String.valueOf(soTienMoi));

        if (!ctUpdated) {
            String maCTPP = "CT" + System.currentTimeMillis() % 100000000;
            ChiTietPhieuPhatDTO ctMoi = new ChiTietPhieuPhatDTO();
            ctMoi.setMaCTPP(maCTPP);
            ctMoi.setMaPP(maPP);
            ctMoi.setMaCuonSach("MV000000000000000001");
            ctMoi.setLyDo(lyDoMoi);
            ctMoi.setSoTien(String.valueOf(soTienMoi));
            String addResult = chiTietDAO.addWithMessage(ctMoi);
            if (!addResult.equals("OK")) {
                return "Cập nhật số tiền OK, nhưng lưu lý do thất bại: " + addResult;
            }
        }

        return "Cập nhật phiếu phạt thành công";
    }

    public String taoPhieuPhat(PhieuPhatDTO phieuPhat, ArrayList<ChiTietPhieuPhatDTO> dsChiTiet) {
        double tongTien = 0;
        for (ChiTietPhieuPhatDTO ct : dsChiTiet) {
            tongTien += Double.parseDouble(ct.getSoTien());
        }
        phieuPhat.setTongTien(String.valueOf(tongTien));

        String result = phieuPhatDAO.add(phieuPhat);
        if (result.equals("OK")) {
            for (ChiTietPhieuPhatDTO ct : dsChiTiet) {
                ct.setMaPP(phieuPhat.getMaPP());
                chiTietDAO.add(ct);
            }
            listPP.add(phieuPhat);
            return "Tạo phiếu phạt thành công";
        }
        return "Tạo phiếu phạt thất bại: " + result;
    }

    public ArrayList<Object[]> search(String keyword, String criteria) {
        ArrayList<Object[]> all;
        if ("Trạng Thái".equals(criteria)) {
            all = phieuPhatDAO.getDanhSachBaoGomDaHuy();
        } else {
            all = getDanhSachHienThiGUI();
        }
        if (keyword == null || keyword.trim().isEmpty())
            return all;

        ArrayList<Object[]> result = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase().trim();

        for (Object[] row : all) {
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
            if (match)
                result.add(row);
        }
        return result;
    }

    public ArrayList<String> getDanhSachMaSachByMaPM(String maPM) {
        return chiTietDAO.getDanhSachMaSachByMaPM(maPM);
    }
}