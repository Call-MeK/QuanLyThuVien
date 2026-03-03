package BUS;

import DAO.TacGiaDAO;
import DTO.TacGiaDTO;
import java.util.ArrayList;

public class TacGiaBUS {
    private TacGiaDAO tacGiaDAO;
    private ArrayList<TacGiaDTO> listTacGia;

    public TacGiaBUS() {
        tacGiaDAO = new TacGiaDAO();
        listTacGia = tacGiaDAO.getAll();
    }

    public ArrayList<TacGiaDTO> getAll() {
        return tacGiaDAO.getAll();
    }

    public TacGiaDTO getById(String maTG) {
        return tacGiaDAO.getById(maTG);
    }

    public boolean add(TacGiaDTO tg) {
        if (tacGiaDAO.add(tg)) {
            listTacGia.add(tg);
            return true;
        }
        return false;
    }

    public boolean update(TacGiaDTO tg) {
        if (tacGiaDAO.update(tg)) {
            for (int i = 0; i < listTacGia.size(); i++) {
                if (listTacGia.get(i).getMaTacGia().equals(tg.getMaTacGia())) {
                    listTacGia.set(i, tg);
                    break;
                }
            }
            return true;
        }
        return false;
    }

    public boolean delete(String maTG) {
        if (tacGiaDAO.delete(maTG)) {
            listTacGia.removeIf(tg -> tg.getMaTacGia().equals(maTG));
            return true;
        }
        return false;
    }
}