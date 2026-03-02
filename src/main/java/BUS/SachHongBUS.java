package BUS;

import DAO.ConNguoiDAO;
import DTO.ConNguoiDTO;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class  {
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

    public boolean add(ConNguoiDTO connguoi) {
        boolean success = conNguoiDAO.add(connguoi);
        if (success) {
            listConNguoi.add(connguoi);
        }
        return success;
    }

    public boolean update(ConNguoiDTO connguoi) {
        boolean success = conNguoiDAO.update(connguoi);
        if (success) {
            for (int i = 0; i < listConNguoi.size(); i++) {
                if (listConNguoi.get(i).getMaNguoi().equals(connguoi.getMaNguoi())) {
                    listConNguoi.set(i, connguoi);
                    break;
                }
            }
        }
        return success;
    }

    public boolean delete(String MaNguoi) {
        boolean success = conNguoiDAO.delete(MaNguoi);
        if (success) {
            listConNguoi.removeIf(cn -> cn.getMaNguoi().equals(MaNguoi));
        }
        return success;
    }
}
