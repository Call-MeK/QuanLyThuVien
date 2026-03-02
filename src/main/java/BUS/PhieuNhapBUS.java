package BUS;

import DAO.PhieuNhapDAO;
import DTO.PhieuNhapDTO;
import java.util.List;

public class PhieuNhapBUS {
    private PhieuNhapDAO phieuNhapDAO;
    private List<PhieuNhapDTO> listPhieuNhap;

    public PhieuNhapBUS() {
        phieuNhapDAO = new PhieuNhapDAO();
        // Lấy tất cả dữ liệu từ database khi khởi tạo
        listPhieuNhap = phieuNhapDAO.findAll(); 
    }

    public List<PhieuNhapDTO> findAll() {
        return phieuNhapDAO.findAll();
    }

    public List<PhieuNhapDTO> getListPhieuNhap() {
        return listPhieuNhap;
    }

    public PhieuNhapDTO findById(String maPN) {
        return phieuNhapDAO.findById(maPN);
    }

    public boolean insert(PhieuNhapDTO pn) {
        boolean success = phieuNhapDAO.insert(pn);
        if (success) {
            listPhieuNhap.add(pn);
        }
        return success;
    }

    public boolean update(PhieuNhapDTO pn) {
        boolean success = phieuNhapDAO.update(pn);
        if (success) {
            for (int i = 0; i < listPhieuNhap.size(); i++) {
                if (listPhieuNhap.get(i).getMaPN().equals(pn.getMaPN())) {
                    listPhieuNhap.set(i, pn);
                    break;
                }
            }
        }
        return success;
    }

    public boolean delete(String maPN) {
        boolean success = phieuNhapDAO.delete(maPN);
        if (success) {
            listPhieuNhap.removeIf(pn -> pn.getMaPN().equals(maPN));
        }
        return success;
    }
}