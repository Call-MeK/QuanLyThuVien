package BUS;

import DAO.PhieuMuonDAO;
import DTO.PhieuMuonDTO;
import java.util.ArrayList;

public class PhieuMuonBUS {
    private PhieuMuonDAO pmDAO;
    private ArrayList<PhieuMuonDTO> listPhieuMuon;

    public PhieuMuonBUS() {
        pmDAO = new PhieuMuonDAO();
        listPhieuMuon = new ArrayList<>();
    }

    public ArrayList<PhieuMuonDTO> getAll() {
        listPhieuMuon = pmDAO.getAll();
        return listPhieuMuon;
    }

    public int count() {
        return pmDAO.count();
    }

    public PhieuMuonDTO getById(String MaPM) {
        if (listPhieuMuon != null && !listPhieuMuon.isEmpty()) {
            for (PhieuMuonDTO pm : listPhieuMuon) {
                if (pm.getMaPM() != null && pm.getMaPM().equals(MaPM))
                    return pm;
            }
        }
        PhieuMuonDTO pm = pmDAO.getById(MaPM);
        return (pm != null && pm.getMaPM() != null) ? pm : null;
    }

    public String insert(PhieuMuonDTO pm) {
        if (pm.getMaPM() == null || pm.getMaPM().trim().isEmpty())
            return "Mã Phiếu Mượn không được để trống!";
        if (pm.getMaThe() == null || pm.getMaThe().trim().isEmpty())
            return "Mã Thẻ (Độc giả) không được để trống!";
        if (pm.getMaNQL() == null || pm.getMaNQL().trim().isEmpty())
            return "Mã Người Quản Lý không được để trống!";
        if (getById(pm.getMaPM()) != null)
            return "Mã Phiếu Mượn đã tồn tại!";

        if (pmDAO.insert(pm) > 0) {
            if (listPhieuMuon != null)
                listPhieuMuon.add(pm);
            return "Tạo phiếu mượn thành công!";
        }
        return "Tạo phiếu mượn thất bại do lỗi cơ sở dữ liệu!";
    }

    public String update(PhieuMuonDTO pm) {
        if (pm.getMaPM() == null || pm.getMaPM().trim().isEmpty())
            return "Mã Phiếu Mượn không được để trống!";
        if (getById(pm.getMaPM()) == null)
            return "Không tìm thấy Phiếu Mượn cần cập nhật!";

        if (pmDAO.update(pm) > 0) {
            if (listPhieuMuon != null) {
                for (int i = 0; i < listPhieuMuon.size(); i++) {
                    if (listPhieuMuon.get(i).getMaPM().equals(pm.getMaPM())) {
                        listPhieuMuon.set(i, pm);
                        break;
                    }
                }
            }
            return "Cập nhật phiếu mượn thành công!";
        }
        return "Cập nhật thất bại!";
    }

    public String delete(String MaPM) {
        if (getById(MaPM) == null)
            return "Mã Phiếu Mượn không tồn tại!";
        if (pmDAO.delete(MaPM) > 0) {
            if (listPhieuMuon != null)
                listPhieuMuon.removeIf(pm -> pm.getMaPM().equals(MaPM));
            return "Xóa phiếu mượn thành công!";
        }
        return "Xóa thất bại! Có thể phiếu mượn này đang chứa chi tiết. Vui lòng xóa chi tiết trước.";
    }

    public ArrayList<PhieuMuonDTO> search(String keyword) {
        ArrayList<PhieuMuonDTO> result = new ArrayList<>();
        if (listPhieuMuon == null || listPhieuMuon.isEmpty())
            getAll();

        String kw = keyword.toLowerCase().trim();
        for (PhieuMuonDTO pm : listPhieuMuon) {
            String maPM = pm.getMaPM() != null ? pm.getMaPM().toLowerCase() : "";
            String maThe = pm.getMaThe() != null ? pm.getMaThe().toLowerCase() : "";
            String maNQL = pm.getMaNQL() != null ? pm.getMaNQL().toLowerCase() : "";
            String tinhTrang = pm.getTinhTrang() != null ? pm.getTinhTrang().toLowerCase() : "";
            if (maPM.contains(kw) || maThe.contains(kw) || maNQL.contains(kw) || tinhTrang.contains(kw)) {
                result.add(pm);
            }
        }
        return result;
    }

    public ArrayList<Object[]> getDanhSachSachDangMuon() {
        return pmDAO.getDanhSachSachDangMuon();
    }

    public ArrayList<Object[]> getDanhSachSachDangMuonByMaThe(String maThe) {
        return pmDAO.getDanhSachSachDangMuonByMaThe(maThe);
    }

    public String generateMaPM() {
        return pmDAO.generateMaPM();
    }
}