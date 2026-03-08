package BUS;

import DAO.ChiTietPhieuMuonDAO;
import DTO.ChiTietPhieuMuonDTO;
import java.util.List;

public class ChiTietPhieuMuonBUS {
    private ChiTietPhieuMuonDAO ctpmDAO;

    public ChiTietPhieuMuonBUS() {
        ctpmDAO = new ChiTietPhieuMuonDAO();
    }

    public boolean add(ChiTietPhieuMuonDTO ctpm) {
        if (ctpm.getMaPM() == null || ctpm.getMaCuonSach() == null) {
            return false;
        }
        return ctpmDAO.insert(ctpm);
    }

    public List<ChiTietPhieuMuonDTO> getByMaPM(String maPM) {
        return ctpmDAO.getByMaPM(maPM);
    }
}