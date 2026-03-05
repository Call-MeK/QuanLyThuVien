package BUS;

import DAO.TheThuVienDAO;
import DTO.TheThuVienDTO;
import java.util.ArrayList;

public class TheThuVienBUS {
    private TheThuVienDAO theThuVienDAO;
    private ArrayList<TheThuVienDTO> listTheThuVien;

    public TheThuVienBUS() {
        theThuVienDAO = new TheThuVienDAO();
        listTheThuVien = new ArrayList<>();
    }

    public ArrayList<TheThuVienDTO> getAll() {
        listTheThuVien = theThuVienDAO.getAll();
        return listTheThuVien;
    }

    public TheThuVienDTO getById(String maThe) {
        if (listTheThuVien != null && !listTheThuVien.isEmpty()) {
            for (TheThuVienDTO the : listTheThuVien) {
                if (the.getMaThe() != null && the.getMaThe().equals(maThe)) {
                    return the;
                }
            }
        }
        return theThuVienDAO.getById(maThe);
    }

    // ✅ THÊM MỚI: Tìm thẻ theo MaDocGia
    // Dùng khi admin nhập MaDocGia để tự động lấy MaThe
    public TheThuVienDTO getByMaDocGia(String maDocGia) {
        if (listTheThuVien == null || listTheThuVien.isEmpty()) {
            getAll();
        }
        for (TheThuVienDTO the : listTheThuVien) {
            if (the.getMaDocGia() != null && the.getMaDocGia().equals(maDocGia)) {
                return the;
            }
        }
        return null;
    }

    public String add(TheThuVienDTO the) {
        if (the.getMaThe() == null || the.getMaThe().trim().isEmpty()) return "Mã thẻ không được để trống!";
        if (the.getMaDocGia() == null || the.getMaDocGia().trim().isEmpty()) return "Mã độc giả không được để trống!";
        if (the.getMaNQL() == null || the.getMaNQL().trim().isEmpty()) return "Mã người quản lý cấp thẻ không được để trống!";
        if (getById(the.getMaThe()) != null) return "Mã thẻ này đã tồn tại trong hệ thống!";

        if (theThuVienDAO.add(the)) {
            if (listTheThuVien != null) listTheThuVien.add(the);
            return "Cấp thẻ thư viện thành công!";
        }
        return "Cấp thẻ thất bại do lỗi cơ sở dữ liệu!";
    }

    public String update(TheThuVienDTO the) {
        if (the.getMaThe() == null || the.getMaThe().trim().isEmpty()) return "Mã thẻ không hợp lệ!";
        if (the.getMaDocGia() == null || the.getMaDocGia().trim().isEmpty()) return "Mã độc giả không được để trống!";
        if (getById(the.getMaThe()) == null) return "Không tìm thấy thẻ thư viện cần cập nhật!";

        if (theThuVienDAO.update(the)) {
            if (listTheThuVien != null) {
                for (int i = 0; i < listTheThuVien.size(); i++) {
                    if (listTheThuVien.get(i).getMaThe().equals(the.getMaThe())) {
                        listTheThuVien.set(i, the);
                        break;
                    }
                }
            }
            return "Cập nhật thông tin thẻ thành công!";
        }
        return "Cập nhật thất bại!";
    }

    public String delete(String maThe) {
        if (getById(maThe) == null) return "Mã thẻ không tồn tại!";
        if (theThuVienDAO.delete(maThe)) {
            if (listTheThuVien != null) listTheThuVien.removeIf(the -> the.getMaThe().equals(maThe));
            return "Thu hồi/Xóa thẻ thư viện thành công!";
        }
        return "Xóa thất bại! Có thể thẻ này đang được sử dụng trong các Phiếu Mượn.";
    }

    public ArrayList<TheThuVienDTO> search(String keyword) {
        ArrayList<TheThuVienDTO> result = new ArrayList<>();
        if (listTheThuVien == null || listTheThuVien.isEmpty()) getAll();
        String kw = keyword.toLowerCase().trim();
        for (TheThuVienDTO the : listTheThuVien) {
            String ma     = the.getMaThe() != null ? the.getMaThe().toLowerCase() : "";
            String ten    = the.getTenThe() != null ? the.getTenThe().toLowerCase() : "";
            String docGia = the.getMaDocGia() != null ? the.getMaDocGia().toLowerCase() : "";
            if (ma.contains(kw) || ten.contains(kw) || docGia.contains(kw)) result.add(the);
        }
        return result;
    }
}