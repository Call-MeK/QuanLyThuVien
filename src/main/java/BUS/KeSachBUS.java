package BUS;

import DAO.KeSachDAO;
import DTO.KeSachDTO;
import java.util.ArrayList;

public class KeSachBUS {
    private KeSachDAO keSachDAO;
    private ArrayList<KeSachDTO> listKeSach;

    public KeSachBUS() {
        keSachDAO = new KeSachDAO();
        listKeSach = keSachDAO.getAll();
    }

    public ArrayList<KeSachDTO> getAll() {
        return keSachDAO.getAll();
    }

    public boolean add(KeSachDTO ks) {
        if (keSachDAO.add(ks)) {
            listKeSach.add(ks);
            return true;
        }
        return false;
    }
}