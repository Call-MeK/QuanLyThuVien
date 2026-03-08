package BUS;

import DAO.PhieuNhapDAO;
import DTO.PhieuNhapDTO;
import java.util.List;

public class PhieuNhapBUS {
    private PhieuNhapDAO phieuNhapDAO;
    private List<PhieuNhapDTO> listPhieuNhap;

    public PhieuNhapBUS() {
        phieuNhapDAO = new PhieuNhapDAO();
        listPhieuNhap = phieuNhapDAO.findAll();
    }

    public List<PhieuNhapDTO> findAll() {
        return phieuNhapDAO.findAll();
    }

    public PhieuNhapDTO findById(String maPN) {
        return phieuNhapDAO.findById(maPN);
    }

    public String generateMaPN() {
        return phieuNhapDAO.generateMaPN();
    }

    public boolean hasMaPN(String maPN) {
        for (PhieuNhapDTO pn : listPhieuNhap) {
            if (pn.getMaPN().equals(maPN)) {
                return true;
            }
        }
        return false;
    }

    public String addPhieuNhap(PhieuNhapDTO pn) {
        if (hasMaPN(pn.getMaPN())) {
            return "Mã phiếu nhập đã tồn tại";
        }
        if (phieuNhapDAO.insert(pn)) {
            listPhieuNhap.add(pn);
            return "Thêm thành công";
        }
        return "Thêm thất bại";
    }

    public String updatePhieuNhap(PhieuNhapDTO pn) {
        if (!hasMaPN(pn.getMaPN())) {
            return "Mã phiếu nhập không tồn tại";
        }
        if (phieuNhapDAO.update(pn)) {
            for (int i = 0; i < listPhieuNhap.size(); i++) {
                if (listPhieuNhap.get(i).getMaPN().equals(pn.getMaPN())) {
                    listPhieuNhap.set(i, pn);
                    break;
                }
            }
            return "Cập nhật thành công";
        }
        return "Cập nhật thất bại";
    }

    public String deletePhieuNhap(String maPN) {
        if (!hasMaPN(maPN)) {
            return "Mã phiếu nhập không tồn tại";
        }
        if (phieuNhapDAO.delete(maPN)) {
            listPhieuNhap.removeIf(pn -> pn.getMaPN().equals(maPN));
            return "Xóa thành công";
        }
        return "Xóa thất bại";
    }
}