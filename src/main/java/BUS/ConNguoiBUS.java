package BUS;

import DAO.ConNguoiDAO;
import DTO.ConNguoiDTO;
import java.util.ArrayList;

public class ConNguoiBUS {
    private ConNguoiDAO conNguoiDAO;
    private ArrayList<ConNguoiDTO> listConNguoi;

    public ConNguoiBUS() {
        conNguoiDAO = new ConNguoiDAO();
        listConNguoi = conNguoiDAO.getAll();
    }

    public ArrayList<ConNguoiDTO> getAll() {
        return conNguoiDAO.getAll();
    }

    public ArrayList<ConNguoiDTO> getListConNguoi() {
        return listConNguoi;
    }

    public ConNguoiDTO getByID(String MaNguoi) {
        return conNguoiDAO.getByID(MaNguoi);
    }

    public boolean hasMaNguoi(String maNguoi) {
        for (ConNguoiDTO cn : listConNguoi) {
            if (cn.getMaNguoi().equals(maNguoi)) {
                return true;
            }
        }
        return false;
    }

    public String addConNguoi(ConNguoiDTO connguoi) {
        if (hasMaNguoi(connguoi.getMaNguoi())) {
            return "Mã người đã tồn tại";
        }
        if (conNguoiDAO.add(connguoi)) {
            listConNguoi.add(connguoi);
            return "Thêm thành công";
        }
        return "Thêm thất bại";
    }

    public String updateConNguoi(ConNguoiDTO connguoi) {
        if (!hasMaNguoi(connguoi.getMaNguoi())) {
            return "Mã người không tồn tại";
        }
        if (conNguoiDAO.update(connguoi)) {
            for (int i = 0; i < listConNguoi.size(); i++) {
                if (listConNguoi.get(i).getMaNguoi().equals(connguoi.getMaNguoi())) {
                    listConNguoi.set(i, connguoi);
                    break;
                }
            }
            return "Cập nhật thành công";
        }
        return "Cập nhật thất bại";
    }

    public String deleteConNguoi(String maNguoi) {
        if (!hasMaNguoi(maNguoi)) {
            return "Mã người không tồn tại";
        }
        if (conNguoiDAO.delete(maNguoi)) {
            listConNguoi.removeIf(cn -> cn.getMaNguoi().equals(maNguoi));
            return "Xóa thành công";
        }
        return "Xóa thất bại";
    }
}
