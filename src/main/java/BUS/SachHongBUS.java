package BUS;

import DAO.SachHongDAO;
import DTO.SachHongDTO;

public class SachHongBUS {
    private SachHongDAO sachHongDAO;

    public SachHongBUS() {
        sachHongDAO = new SachHongDAO();
    }

    public boolean insert(SachHongDTO sachHong) {
        return sachHongDAO.insert(sachHong);
    }
}
