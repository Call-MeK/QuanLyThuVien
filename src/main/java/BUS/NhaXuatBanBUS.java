package BUS;

import DAO.NhaXuatBanDAO;
import DTO.NhaXuatBanDTO;
import java.util.ArrayList;

public class NhaXuatBanBUS {
    private NhaXuatBanDAO nxbDAO;
    private ArrayList<NhaXuatBanDTO> listNXB;

    public NhaXuatBanBUS() {
        nxbDAO = new NhaXuatBanDAO();
        listNXB = new ArrayList<>();
    }

    public ArrayList<NhaXuatBanDTO> getAll() {
        listNXB = nxbDAO.getAll();
        return listNXB;
    }

    public NhaXuatBanDTO getById(String MaNXB) {
        if (listNXB != null) {
            for (NhaXuatBanDTO nxb : listNXB) {
                if (nxb.getMaNXB().equals(MaNXB)) {
                    return nxb;
                }
            }
        }
        return nxbDAO.getById(MaNXB);
    }

    public String add(NhaXuatBanDTO nxb) {
        if (nxb.getMaNXB().trim().isEmpty() || nxb.getTenNXB().trim().isEmpty()) {
            return "Mã và Tên Nhà Xuất Bản không được để trống!";
        }

        if (getById(nxb.getMaNXB()) != null) {
            return "Mã Nhà Xuất Bản đã tồn tại!";
        }

        if (nxbDAO.add(nxb)) {
            if (listNXB != null) {
                listNXB.add(nxb);
            }
            return "Thêm thành công!";
        }
        return "Thêm thất bại do lỗi cơ sở dữ liệu!";
    }

    public String update(NhaXuatBanDTO nxb) {
        if (nxb.getMaNXB().trim().isEmpty() || nxb.getTenNXB().trim().isEmpty()) {
            return "Mã và Tên Nhà Xuất Bản không được để trống!";
        }

        if (nxbDAO.update(nxb)) {
            if (listNXB != null) {
                for (int i = 0; i < listNXB.size(); i++) {
                    if (listNXB.get(i).getMaNXB().equals(nxb.getMaNXB())) {
                        listNXB.set(i, nxb);
                        break;
                    }
                }
            }
            return "Cập nhật thành công!";
        }
        return "Cập nhật thất bại!";
    }

    public String delete(String MaNXB) {
        if (getById(MaNXB) == null) {
            return "Mã Nhà Xuất Bản không tồn tại!";
        }

        if (nxbDAO.delete(MaNXB)) {
            if (listNXB != null) {
                listNXB.removeIf(nxb -> nxb.getMaNXB().equals(MaNXB));
            }
            return "Xóa thành công!";
        }
        return "Xóa thất bại!";
    }

    public ArrayList<NhaXuatBanDTO> searchByName(String keyword) {
        ArrayList<NhaXuatBanDTO> result = new ArrayList<>();
        if (listNXB == null) {
            getAll();
        }
        for (NhaXuatBanDTO nxb : listNXB) {
            if (nxb.getTenNXB().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(nxb);
            }
        }
        return result;
    }
}