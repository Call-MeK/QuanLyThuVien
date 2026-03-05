package BUS;

import DAO.ChiTietPhieuPhatDAO;
import DTO.ChiTietPhieuPhatDTO;
import java.util.ArrayList;

public class ChiTietPhieuPhatBUS {
    private ChiTietPhieuPhatDAO ctppDAO;
    private ArrayList<ChiTietPhieuPhatDTO> listCTPP;

    public ChiTietPhieuPhatBUS() {
        ctppDAO = new ChiTietPhieuPhatDAO();
        listCTPP = ctppDAO.getAll();
    }

    public ArrayList<ChiTietPhieuPhatDTO> getAll() {
        listCTPP = ctppDAO.getAll();
        return listCTPP;
    }

    public ArrayList<ChiTietPhieuPhatDTO> getByMaPP(String maPP) {
        return ctppDAO.getByMaPP(maPP);
    }

    public boolean add(ChiTietPhieuPhatDTO ct) {
        if (ctppDAO.add(ct)) {
            listCTPP.add(ct);
            return true;
        }
        return false;
    }

    public boolean update(ChiTietPhieuPhatDTO ct) {
        if (ctppDAO.update(ct)) {
            for (int i = 0; i < listCTPP.size(); i++) {
                if (listCTPP.get(i).getMaCTPP().equals(ct.getMaCTPP())) {
                    listCTPP.set(i, ct);
                    break;
                }
            }
            return true;
        }
        return false;
    }

    // THÊM MỚI: Cập nhật theo MaPP (update tất cả chi tiết của 1 phiếu phạt)
    public boolean updateByMaPP(String maPP, String lyDo, String soTien) {
        if (ctppDAO.updateByMaPP(maPP, lyDo, soTien)) {
            // Cập nhật lại cache
            for (ChiTietPhieuPhatDTO ct : listCTPP) {
                if (ct.getMaPP().equals(maPP)) {
                    ct.setLyDo(lyDo);
                    ct.setSoTien(soTien);
                }
            }
            return true;
        }
        return false;
    }

    public boolean delete(String maCTPP) {
        if (ctppDAO.delete(maCTPP)) {
            listCTPP.removeIf(ct -> ct.getMaCTPP().equals(maCTPP));
            return true;
        }
        return false;
    }

    public ChiTietPhieuPhatDTO findById(String maCTPP) {
        for (ChiTietPhieuPhatDTO ct : listCTPP) {
            if (ct.getMaCTPP().equalsIgnoreCase(maCTPP)) {
                return ct;
            }
        }
        return null;
    }
    // Thêm hàm này vào class ChiTietPhieuPhatBUS
    public ArrayList<Object[]> getChiTietCoTenSachByMaPP(String maPP) {
        // Tùy theo cách bạn khai báo biến DAO trong file này (có thể là chiTietDAO hoặc chiTietPhieuPhatDAO)
        return ctppDAO.getChiTietCoTenSachByMaPP(maPP); 
    }
}