package BUS;

import DAO.TheThuVienDAO;
import DTO.TheThuVienDTO;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class TheThuVienBUS {
    private TheThuVienDAO theThuVienDAO;
    private ArrayList<TheThuVienDTO> listTheThuVien;

    public TheThuVienBUS() {
        theThuVienDAO = new TheThuVienDAO();
        listTheThuVien = new ArrayList<>();
    }

    // Lấy tất cả danh sách Thẻ Thư Viện
    public ArrayList<TheThuVienDTO> getAll() {
        listTheThuVien = theThuVienDAO.getAll();
        return listTheThuVien;
    }

    // Tìm kiếm Thẻ Thư Viện theo mã
    public TheThuVienDTO getById(String maThe) {
        // Ưu tiên tìm trong danh sách tạm (cache) trước để tăng tốc độ
        if (listTheThuVien != null && !listTheThuVien.isEmpty()) {
            for (TheThuVienDTO the : listTheThuVien) {
                if (the.getMaThe() != null && the.getMaThe().equals(maThe)) {
                    return the;
                }
            }
        }
        // Nếu không có trong cache thì gọi DAO xuống CSDL
        return theThuVienDAO.getById(maThe);
    }

    // Thêm Thẻ Thư Viện mới
    public String add(TheThuVienDTO the) {
        // 1. Kiểm tra dữ liệu đầu vào (Validation)
        if (the.getMaThe() == null || the.getMaThe().trim().isEmpty()) {
            return "Mã thẻ không được để trống!";
        }
        if (the.getMaDocGia() == null || the.getMaDocGia().trim().isEmpty()) {
            return "Mã độc giả không được để trống!";
        }
        if (the.getMaNQL() == null || the.getMaNQL().trim().isEmpty()) {
            return "Mã người quản lý cấp thẻ không được để trống!";
        }

        // 2. Kiểm tra trùng mã thẻ
        if (getById(the.getMaThe()) != null) {
            return "Mã thẻ này đã tồn tại trong hệ thống!";
        }

        // 3. Gọi DAO để thêm
        if (theThuVienDAO.add(the)) {
            // Thêm thành công thì cập nhật luôn vào cache
            if (listTheThuVien != null) {
                listTheThuVien.add(the);
            }
            return "Cấp thẻ thư viện thành công!";
        }
        return "Cấp thẻ thất bại do lỗi cơ sở dữ liệu!";
    }

    // Cập nhật thông tin Thẻ Thư Viện
    public String update(TheThuVienDTO the) {
        // 1. Kiểm tra dữ liệu
        if (the.getMaThe() == null || the.getMaThe().trim().isEmpty()) {
            return "Mã thẻ không hợp lệ!";
        }
        if (the.getMaDocGia() == null || the.getMaDocGia().trim().isEmpty()) {
            return "Mã độc giả không được để trống!";
        }

        // 2. Kiểm tra tồn tại
        if (getById(the.getMaThe()) == null) {
            return "Không tìm thấy thẻ thư viện cần cập nhật!";
        }

        // 3. Gọi DAO để update
        if (theThuVienDAO.update(the)) {
            // Cập nhật lại list cache
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

    // Xóa Thẻ Thư Viện (Hard Delete)
    public String delete(String maThe) {
        // 1. Kiểm tra tồn tại
        if (getById(maThe) == null) {
            return "Mã thẻ không tồn tại!";
        }

        // 2. Gọi DAO để xóa
        if (theThuVienDAO.delete(maThe)) {
            // Bỏ khỏi list cache
            if (listTheThuVien != null) {
                listTheThuVien.removeIf(the -> the.getMaThe().equals(maThe));
            }
            return "Thu hồi/Xóa thẻ thư viện thành công!";
        }
        return "Xóa thất bại! Có thể thẻ này đang được sử dụng trong các Phiếu Mượn.";
    }
    
    // Tìm kiếm Thẻ Thư Viện (theo Mã thẻ, Tên thẻ hoặc Mã Độc giả)
    public ArrayList<TheThuVienDTO> search(String keyword) {
        ArrayList<TheThuVienDTO> result = new ArrayList<>();
        if (listTheThuVien == null || listTheThuVien.isEmpty()) {
            getAll(); // Load data nếu list trống
        }
        
        String lowerKeyword = keyword.toLowerCase().trim();
        for (TheThuVienDTO the : listTheThuVien) {
            String ma = the.getMaThe() != null ? the.getMaThe().toLowerCase() : "";
            String ten = the.getTenThe() != null ? the.getTenThe().toLowerCase() : "";
            String docGia = the.getMaDocGia() != null ? the.getMaDocGia().toLowerCase() : "";
            
            if (ma.contains(lowerKeyword) || ten.contains(lowerKeyword) || docGia.contains(lowerKeyword)) {
                result.add(the);
            }
        }
        return result;
    }
}