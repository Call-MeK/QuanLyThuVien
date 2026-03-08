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

    public ArrayList<ConNguoiDTO> getListConNguoi() {
        return listConNguoi;
    }
}
