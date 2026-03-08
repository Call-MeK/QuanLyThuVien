package BUS;

import DAO.KeSachDAO;
import DTO.KeSachDTO;
import java.util.ArrayList;
import java.util.List;

public class KeSachBUS {
    private KeSachDAO keSachDAO;
    private List<KeSachDTO> listKeSach;

    public KeSachBUS() {
        keSachDAO = new KeSachDAO();
        listKeSach = new ArrayList<>();
    }

    public List<KeSachDTO> getAll() {
        listKeSach = keSachDAO.getAll();
        return listKeSach;
    }

    public KeSachDTO getById(String maKeSach) {
        if (listKeSach == null || listKeSach.isEmpty()) {
            getAll();
        }

        for (KeSachDTO ke : listKeSach) {
            if (ke.getMaKeSach().equals(maKeSach)) {
                return ke;
            }
        }
        return null;
    }

    public String insert(KeSachDTO ke) {
        if (ke.getMaKeSach() == null || ke.getMaKeSach().trim().isEmpty()) {
            return "Mã Kệ Sách không được để trống!";
        }
        if (ke.getTenKe() == null || ke.getTenKe().trim().isEmpty()) {
            return "Tên Kệ Sách không được để trống!";
        }
        if (ke.getMaTheLoai() == null || ke.getMaTheLoai().trim().isEmpty()) {
            return "Mã Thể Loại không được để trống!";
        }

        if (getById(ke.getMaKeSach()) != null) {
            return "Mã Kệ Sách đã tồn tại!";
        }

        if (keSachDAO.insert(ke)) {
            if (listKeSach != null) {
                listKeSach.add(ke);
            }
            return "Thêm kệ sách thành công!";
        }
        return "Thêm thất bại do lỗi cơ sở dữ liệu!";
    }

    public String update(KeSachDTO ke) {
        if (ke.getTenKe() == null || ke.getTenKe().trim().isEmpty()) {
            return "Tên Kệ Sách không được để trống!";
        }
        if (ke.getMaTheLoai() == null || ke.getMaTheLoai().trim().isEmpty()) {
            return "Mã Thể Loại không được để trống!";
        }

        if (getById(ke.getMaKeSach()) == null) {
            return "Không tìm thấy kệ sách để cập nhật!";
        }

        if (keSachDAO.update(ke)) {
            if (listKeSach != null) {
                for (int i = 0; i < listKeSach.size(); i++) {
                    if (listKeSach.get(i).getMaKeSach().equals(ke.getMaKeSach())) {
                        listKeSach.set(i, ke);
                        break;
                    }
                }
            }
            return "Cập nhật kệ sách thành công!";
        }
        return "Cập nhật thất bại!";
    }

    public String delete(String maKeSach) {
        if (getById(maKeSach) == null) {
            return "Mã Kệ Sách không tồn tại hoặc đã bị xóa!";
        }

        if (keSachDAO.delete(maKeSach)) {
            if (listKeSach != null) {
                listKeSach.removeIf(ke -> ke.getMaKeSach().equals(maKeSach));
            }
            return "Xóa kệ sách thành công!";
        }
        return "Xóa thất bại!";
    }

    public List<KeSachDTO> search(String keyword) {
        List<KeSachDTO> result = new ArrayList<>();
        if (listKeSach == null || listKeSach.isEmpty()) {
            getAll();
        }

        String keywordLower = keyword.toLowerCase().trim();
        for (KeSachDTO ke : listKeSach) {
            if (ke.getMaKeSach().toLowerCase().contains(keywordLower) ||
                    ke.getTenKe().toLowerCase().contains(keywordLower) ||
                    ke.getMaTheLoai().toLowerCase().contains(keywordLower)) {
                result.add(ke);
            }
        }
        return result;
    }
}