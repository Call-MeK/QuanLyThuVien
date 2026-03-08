package BUS;

import DAO.ChiTietPhieuPhatDAO;
import java.util.ArrayList;

public class ChiTietPhieuPhatBUS {
    private ChiTietPhieuPhatDAO ctppDAO;

    public ChiTietPhieuPhatBUS() {
        ctppDAO = new ChiTietPhieuPhatDAO();
    }

    public ArrayList<Object[]> getChiTietCoTenSachByMaPP(String maPP) {
        return ctppDAO.getChiTietCoTenSachByMaPP(maPP);
    }
}