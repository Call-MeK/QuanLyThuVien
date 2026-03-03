package BUS;

import DAO.SachHongDAO;
import DTO.SachHongDTO;
import java.util.List;

public class SachHongBUS {
    private SachHongDAO sachHongDAO;
    private List<SachHongDTO> listSachHong;

    public SachHongBUS() {
        sachHongDAO = new SachHongDAO();
        listSachHong = sachHongDAO.getAll();
    }

    public List<SachHongDTO> getAll() {
        return sachHongDAO.getAll();
    }

    public List<SachHongDTO> getListSachHong() {
        return listSachHong;
    }

    public boolean hasMaSachHong(int maSachHong) {
        for (SachHongDTO sh : listSachHong) {
            if (sh.getMaSachHong() == maSachHong) {
                return true;
            }
        }
        return false;
    }

    public String addSachHong(SachHongDTO sh) {
        if (hasMaSachHong(sh.getMaSachHong())) {
            return "Mã sách hỏng đã tồn tại";
        }
        if (sachHongDAO.insert(sh)) {
            listSachHong.add(sh);
            return "Thêm thành công";
        }
        return "Thêm thất bại";
    }

    public String updateSachHong(SachHongDTO sh) {
        if (!hasMaSachHong(sh.getMaSachHong())) {
            return "Mã sách hỏng không tồn tại";
        }
        if (sachHongDAO.update(sh)) {
            for (int i = 0; i < listSachHong.size(); i++) {
                if (listSachHong.get(i).getMaSachHong() == sh.getMaSachHong()) {
                    listSachHong.set(i, sh);
                    break;
                }
            }
            return "Cập nhật thành công";
        }
        return "Cập nhật thất bại";
    }

    public String deleteSachHong(int maSachHong) {
        if (!hasMaSachHong(maSachHong)) {
            return "Mã sách hỏng không tồn tại";
        }
        if (sachHongDAO.delete(maSachHong)) {
            listSachHong.removeIf(sh -> sh.getMaSachHong() == maSachHong);
            return "Xóa thành công";
        }
        return "Xóa thất bại";
    }

    public SachHongDTO findById(int maSachHong) {
        return sachHongDAO.findById(maSachHong);
    }

    public List<SachHongDTO> searchByName(String keyword) {
        return sachHongDAO.searchByName(keyword);
    }
}
