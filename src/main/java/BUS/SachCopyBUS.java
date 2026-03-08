package BUS;

import DAO.SachCopyDAO;
import DTO.SachCopyDTO;
import java.util.ArrayList;
import java.util.List;

public class SachCopyBUS {
    private SachCopyDAO scDAO;
    private List<SachCopyDTO> listSachCopy;

    public SachCopyBUS() {
        scDAO = new SachCopyDAO();
        listSachCopy = new ArrayList<>();
    }

    public List<SachCopyDTO> getAll() {
        listSachCopy = scDAO.getAll();
        return listSachCopy;
    }

    public SachCopyDTO findById(String maVach) {
        if (listSachCopy != null && !listSachCopy.isEmpty()) {
            for (SachCopyDTO sc : listSachCopy) {
                if (sc.getMaVach() != null && sc.getMaVach().equals(maVach)) {
                    return sc;
                }
            }
        }
        return scDAO.findById(maVach);
    }

    public String insert(SachCopyDTO sc) {
        if (sc.getMaVach() == null || sc.getMaVach().trim().isEmpty()) {
            return "Mã vạch không được để trống!";
        }
        if (sc.getMaSach() == null || sc.getMaSach().trim().isEmpty()) {
            return "Mã sách gốc không được để trống!";
        }
        if (sc.getTenSachBanSao() == null || sc.getTenSachBanSao().trim().isEmpty()) {
            return "Tên sách bản sao không được để trống!";
        }

        if (findById(sc.getMaVach()) != null) {
            return "Mã vạch này đã tồn tại trong hệ thống!";
        }

        sc.setIsDeleted(false);

        if (scDAO.insert(sc)) {
            if (listSachCopy != null) {
                listSachCopy.add(sc);
            }
            return "Thêm bản sao sách thành công!";
        }
        return "Thêm thất bại do lỗi cơ sở dữ liệu!";
    }

    public String update(SachCopyDTO sc) {
        if (sc.getMaVach() == null || sc.getMaVach().trim().isEmpty()) {
            return "Mã vạch không hợp lệ!";
        }
        if (sc.getTenSachBanSao() == null || sc.getTenSachBanSao().trim().isEmpty()) {
            return "Tên sách bản sao không được để trống!";
        }

        if (findById(sc.getMaVach()) == null) {
            return "Không tìm thấy bản sao sách cần cập nhật!";
        }

        if (scDAO.update(sc)) {
            if (listSachCopy != null) {
                for (int i = 0; i < listSachCopy.size(); i++) {
                    if (listSachCopy.get(i).getMaVach().equals(sc.getMaVach())) {
                        listSachCopy.set(i, sc);
                        break;
                    }
                }
            }
            return "Cập nhật bản sao sách thành công!";
        }
        return "Cập nhật thất bại!";
    }

    public String delete(String maVach) {
        if (findById(maVach) == null) {
            return "Mã vạch không tồn tại hoặc sách đã bị xóa!";
        }

        if (scDAO.delete(maVach)) {
            if (listSachCopy != null) {
                listSachCopy.removeIf(sc -> sc.getMaVach().equals(maVach));
            }
            return "Xóa bản sao sách thành công!";
        }
        return "Xóa thất bại!";
    }

    public List<SachCopyDTO> search(String keyword) {
        List<SachCopyDTO> result = new ArrayList<>();
        if (listSachCopy == null || listSachCopy.isEmpty()) {
            getAll();
        }

        String lowerKeyword = keyword.toLowerCase().trim();
        for (SachCopyDTO sc : listSachCopy) {
            String maVach = sc.getMaVach() != null ? sc.getMaVach().toLowerCase() : "";
            String tenSach = sc.getTenSachBanSao() != null ? sc.getTenSachBanSao().toLowerCase() : "";
            String tinhTrang = sc.getTinhTrang() != null ? sc.getTinhTrang().toLowerCase() : "";

            if (maVach.contains(lowerKeyword) ||
                    tenSach.contains(lowerKeyword) ||
                    tinhTrang.contains(lowerKeyword)) {
                result.add(sc);
            }
        }
        return result;
    }
}