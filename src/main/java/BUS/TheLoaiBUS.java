package BUS;

import DAO.TheLoaiDAO;
import DTO.TheLoaiDTO;
import java.util.ArrayList;

public class TheLoaiBUS {
    private TheLoaiDAO theLoaiDAO;
    private ArrayList<TheLoaiDTO> listTheLoai;

    public TheLoaiBUS() {
        theLoaiDAO = new TheLoaiDAO();
        listTheLoai = theLoaiDAO.getAll();
    }

    public ArrayList<TheLoaiDTO> getAll() {
        return theLoaiDAO.getAll();
    }

    public boolean add(TheLoaiDTO tl) {
        if (theLoaiDAO.add(tl)) {
            listTheLoai.add(tl);
            return true;
        }
        return false;
    }

    public boolean update(TheLoaiDTO tl) {
        if (theLoaiDAO.update(tl)) {
            for (int i = 0; i < listTheLoai.size(); i++) {
                if (listTheLoai.get(i).getMaTheLoai().equals(tl.getMaTheLoai())) {
                    listTheLoai.set(i, tl);
                    break;
                }
            }
            return true;
        }
        return false;
    }

    public boolean delete(String maTL) {
        if (theLoaiDAO.delete(maTL)) {
            listTheLoai.removeIf(tl -> tl.getMaTheLoai().equals(maTL));
            return true;
        }
        return false;
    }
}