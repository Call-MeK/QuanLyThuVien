package BUS;

import DAO.NhaXuatBanDAO;
import DTO.NhaXuatBanDTO;
import java.util.ArrayList;

public class NhaXuatBanBUS {
    private NhaXuatBanDAO nxbDAO;
    private ArrayList<NhaXuatBanDTO> listNXB;

    public NhaXuatBanBUS() {
        nxbDAO = new NhaXuatBanDAO();
        listNXB = nxbDAO.getAll();
    }

    public ArrayList<NhaXuatBanDTO> getAll() {
        return nxbDAO.getAll();
    }

    public boolean add(NhaXuatBanDTO nxb) {
        if (nxbDAO.add(nxb)) {
            listNXB.add(nxb);
            return true;
        }
        return false;
    }

    public boolean update(NhaXuatBanDTO nxb) {
        if (nxbDAO.update(nxb)) {
            for (int i = 0; i < listNXB.size(); i++) {
                if (listNXB.get(i).getMaNXB().equals(nxb.getMaNXB())) {
                    listNXB.set(i, nxb);
                    break;
                }
            }
            return true;
        }
        return false;
    }

    public boolean delete(String maNXB) {
        if (nxbDAO.delete(maNXB)) {
            listNXB.removeIf(n -> n.getMaNXB().equals(maNXB));
            return true;
        }
        return false;
    }
}