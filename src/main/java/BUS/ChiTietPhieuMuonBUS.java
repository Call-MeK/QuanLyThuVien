package BUS;

import DAO.ChiTietPhieuMuonDAO;
import DTO.ChiTietPhieuMuonDTO;
import java.util.List;

public class ChiTietPhieuMuonBUS {
    private ChiTietPhieuMuonDAO ctpmDAO;

    public ChiTietPhieuMuonBUS() {
        ctpmDAO = new ChiTietPhieuMuonDAO();
    }

    /** Thêm chi tiết phiếu mượn (1 cuốn sách) */
    public boolean add(ChiTietPhieuMuonDTO ctpm) {
        if (ctpm.getMaPM() == null || ctpm.getMaCuonSach() == null) return false;
        return ctpmDAO.insert(ctpm);
    }

    /** Lấy danh sách cuốn sách trong 1 phiếu mượn — dùng khi xác nhận trả */
    public List<ChiTietPhieuMuonDTO> getByMaPM(String maPM) {
        return ctpmDAO.getByMaPM(maPM);
    }

    /** Cập nhật tình trạng sách trong phiếu (ghi lại khi trả) */
    public boolean updateTinhTrang(String maPM, String maCuonSach, String tinhTrang) {
        return ctpmDAO.updateTinhTrang(maPM, maCuonSach, tinhTrang);
    }
}