package BUS;

import DAO.ChiTietPhieuNhapDAO;
import DTO.ChiTietPhieuNhapDTO;
import java.util.List;

public class ChiTietPhieuNhapBUS {
    private ChiTietPhieuNhapDAO chiTietDAO;

    public ChiTietPhieuNhapBUS() {
        chiTietDAO = new ChiTietPhieuNhapDAO();
    }

    public List<ChiTietPhieuNhapDTO> getByMaPN(String maPN) {
        return chiTietDAO.findByMaPN(maPN);
    }

    public boolean hasChiTiet(String maPN, String maSach) {
        List<ChiTietPhieuNhapDTO> list = getByMaPN(maPN);
        for (ChiTietPhieuNhapDTO ct : list) {
            if (ct.getMaSach().equals(maSach)) {
                return true;
            }
        }
        return false;
    }

    public String addChiTiet(ChiTietPhieuNhapDTO ct) {
        if (hasChiTiet(ct.getMaPN(), ct.getMaSach())) {
            return "Chi tiết phiếu nhập biểu thị sách này đã tồn tại";
        }
        if (chiTietDAO.insert(ct)) {
            return "Thêm chi tiết thành công";
        }
        return "Thêm chi tiết thất bại";
    }

    public String updateChiTiet(ChiTietPhieuNhapDTO ct) {
        if (!hasChiTiet(ct.getMaPN(), ct.getMaSach())) {
            return "Chi tiết phiếu nhập không tồn tại";
        }
        if (chiTietDAO.update(ct)) {
            return "Cập nhật chi tiết thành công";
        }
        return "Cập nhật chi tiết thất bại";
    }

    public String deleteSpecific(String maPN, String maSach) {
        if (!hasChiTiet(maPN, maSach)) {
            return "Chi tiết phiếu nhập không tồn tại";
        }
        if (chiTietDAO.deleteSpecific(maPN, maSach)) {
            return "Xóa chi tiết thành công";
        }
        return "Xóa chi tiết thất bại";
    }

    public String deleteAllByMaPN(String maPN) {
        if (chiTietDAO.deleteAllByMaPN(maPN)) {
            return "Xóa toàn bộ chi tiết thành công";
        }
        return "Xóa toàn bộ chi tiết thất bại hoặc không có chi tiết nào";
    }
}