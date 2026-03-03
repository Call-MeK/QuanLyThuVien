package BUS;

import DAO.KeSachDAO;
import DTO.KeSachDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class KeSachBUS {
    private KeSachDAO keSachDAO;
    private List<KeSachDTO> listKeSach;

    public KeSachBUS() {
        keSachDAO = new KeSachDAO();
        listKeSach = new ArrayList<>();
    }

    // Lấy danh sách Kệ Sách (chỉ lấy những kệ chưa bị xóa)
    public List<KeSachDTO> getAll() {
        // Hàm getAll() ở DAO đã lọc isdeleted = 0 rồi
        listKeSach = keSachDAO.getAll();
        return listKeSach;
    }

    // Tìm kiếm Kệ Sách theo Mã
    public KeSachDTO getById(String maKeSach) {
        if (listKeSach == null || listKeSach.isEmpty()) {
            getAll(); // Load data nếu list trống
        }
        
        for (KeSachDTO ke : listKeSach) {
            if (ke.getMaKeSach().equals(maKeSach)) {
                return ke;
            }
        }
        return null;
    }

    // Thêm Kệ Sách mới
    public String insert(KeSachDTO ke) {
        // 1. Kiểm tra dữ liệu đầu vào (Validation)
        if (ke.getMaKeSach() == null || ke.getMaKeSach().trim().isEmpty()) {
            return "Mã Kệ Sách không được để trống!";
        }
        if (ke.getTenKe() == null || ke.getTenKe().trim().isEmpty()) {
            return "Tên Kệ Sách không được để trống!";
        }
        if (ke.getMaTheLoai() == null || ke.getMaTheLoai().trim().isEmpty()) {
            return "Mã Thể Loại không được để trống!";
        }

        // 2. Kiểm tra trùng mã Kệ Sách
        if (getById(ke.getMaKeSach()) != null) {
            return "Mã Kệ Sách đã tồn tại!";
        }

        // 3. Gọi DAO để thêm vào DB
        if (keSachDAO.insert(ke)) {
            // Thêm vào danh sách tạm (cache)
            if (listKeSach != null) {
                listKeSach.add(ke);
            }
            return "Thêm kệ sách thành công!";
        }
        return "Thêm thất bại do lỗi cơ sở dữ liệu!";
    }

    // Cập nhật thông tin Kệ Sách
    public String update(KeSachDTO ke) {
        // 1. Kiểm tra dữ liệu đầu vào
        if (ke.getTenKe() == null || ke.getTenKe().trim().isEmpty()) {
            return "Tên Kệ Sách không được để trống!";
        }
        if (ke.getMaTheLoai() == null || ke.getMaTheLoai().trim().isEmpty()) {
            return "Mã Thể Loại không được để trống!";
        }

        // 2. Kiểm tra kệ sách có tồn tại không
        if (getById(ke.getMaKeSach()) == null) {
            return "Không tìm thấy kệ sách để cập nhật!";
        }

        // 3. Gọi DAO để update DB
        if (keSachDAO.update(ke)) {
            // Cập nhật lại list cache
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

    // Xóa Kệ Sách (Soft Delete)
    public String delete(String maKeSach) {
        // 1. Kiểm tra kệ sách có tồn tại không
        if (getById(maKeSach) == null) {
            return "Mã Kệ Sách không tồn tại hoặc đã bị xóa!";
        }

        // 2. Gọi DAO để cập nhật isdeleted = 1
        if (keSachDAO.delete(maKeSach)) {
            // Xóa luôn khỏi danh sách hiển thị tạm (vì listKeSach chỉ chứa isdeleted = 0)
            if (listKeSach != null) {
                listKeSach.removeIf(ke -> ke.getMaKeSach().equals(maKeSach));
            }
            return "Xóa kệ sách thành công!";
        }
        return "Xóa thất bại!";
    }
    
    // (Tùy chọn) Tìm kiếm Kệ Sách theo tên hoặc mã
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