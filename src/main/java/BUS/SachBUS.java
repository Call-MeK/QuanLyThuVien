package BUS;

import DAO.SachDAO;
import DTO.SachDTO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Admin
 */
public class SachBUS {
    private SachDAO sachDAO;
    private List<SachDTO> listSach;

    public SachBUS() {
        sachDAO = new SachDAO();
        listSach = new ArrayList<>();
    }

    // Lấy tất cả danh sách Sách (chỉ lấy sách chưa bị xóa)
    public List<SachDTO> getAll() {
        listSach = sachDAO.getAll();
        return listSach;
    }

    // Tìm kiếm Sách theo mã
    public SachDTO getById(String maSach) {
        if (listSach == null || listSach.isEmpty()) {
            getAll(); // Load dữ liệu nếu cache trống
        }
        for (SachDTO sach : listSach) {
            if (sach.getMaSach() != null && sach.getMaSach().equals(maSach)) {
                return sach;
            }
        }
        return null;
    }

    // Thêm Sách mới
    public String insert(SachDTO sach) {
        // 1. Kiểm tra dữ liệu rỗng
        if (sach.getMaSach() == null || sach.getMaSach().trim().isEmpty()) {
            return "Mã sách không được để trống!";
        }
        if (sach.getTenSach() == null || sach.getTenSach().trim().isEmpty()) {
            return "Tên sách không được để trống!";
        }
        if (sach.getTheLoai() == null || sach.getTheLoai().trim().isEmpty()) {
            return "Thể loại không được để trống!";
        }

        // 2. Kiểm tra dữ liệu số hợp lệ
        if (sach.getGiaBia() != null && sach.getGiaBia() < 0) {
            return "Giá bìa không được là số âm!";
        }
        
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (sach.getNamXB() <= 0 || sach.getNamXB() > currentYear) {
            return "Năm xuất bản không hợp lệ! Phải lớn hơn 0 và không vượt quá năm hiện tại (" + currentYear + ").";
        }

        // 3. Kiểm tra trùng mã Sách
        if (getById(sach.getMaSach()) != null) {
            return "Mã sách này đã tồn tại trong hệ thống!";
        }

        // 4. Mặc định IsDeleted = false khi thêm mới
        sach.setIsDeleted(false);

        // 5. Gọi DAO để thêm
        if (sachDAO.insert(sach)) {
            if (listSach != null) {
                listSach.add(sach);
            }
            return "Thêm sách thành công!";
        }
        return "Thêm thất bại do lỗi cơ sở dữ liệu!";
    }

    // Cập nhật thông tin Sách
    public String update(SachDTO sach) {
        // 1. Kiểm tra mã rỗng
        if (sach.getMaSach() == null || sach.getMaSach().trim().isEmpty()) {
            return "Mã sách không hợp lệ!";
        }
        if (sach.getTenSach() == null || sach.getTenSach().trim().isEmpty()) {
            return "Tên sách không được để trống!";
        }

        // 2. Kiểm tra dữ liệu số
        if (sach.getGiaBia() != null && sach.getGiaBia() < 0) {
            return "Giá bìa không được là số âm!";
        }
        
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (sach.getNamXB() <= 0 || sach.getNamXB() > currentYear) {
            return "Năm xuất bản không hợp lệ!";
        }

        // 3. Kiểm tra tồn tại
        if (getById(sach.getMaSach()) == null) {
            return "Không tìm thấy cuốn sách cần cập nhật!";
        }

        // 4. Gọi DAO để update
        if (sachDAO.update(sach)) {
            // Cập nhật lại list cache
            if (listSach != null) {
                for (int i = 0; i < listSach.size(); i++) {
                    if (listSach.get(i).getMaSach().equals(sach.getMaSach())) {
                        listSach.set(i, sach);
                        break;
                    }
                }
            }
            return "Cập nhật thông tin sách thành công!";
        }
        return "Cập nhật thất bại!";
    }

    // Xóa Sách (Soft Delete)
    public String delete(String maSach) {
        // 1. Kiểm tra tồn tại
        if (getById(maSach) == null) {
            return "Mã sách không tồn tại hoặc đã bị xóa!";
        }

        // 2. Gọi DAO để xóa mềm (isdeleted = 1)
        if (sachDAO.delete(maSach)) {
            // Xóa khỏi danh sách hiển thị
            if (listSach != null) {
                listSach.removeIf(s -> s.getMaSach().equals(maSach));
            }
            return "Xóa sách thành công!";
        }
        return "Xóa thất bại!";
    }
    
    // (Tùy chọn) Tìm kiếm sách theo từ khóa (Mã Sách, Tên Sách, Thể Loại)
    public List<SachDTO> search(String keyword) {
        List<SachDTO> result = new ArrayList<>();
        if (listSach == null || listSach.isEmpty()) {
            getAll();
        }
        
        String lowerKeyword = keyword.toLowerCase().trim();
        for (SachDTO sach : listSach) {
            String maSach = sach.getMaSach() != null ? sach.getMaSach().toLowerCase() : "";
            String tenSach = sach.getTenSach() != null ? sach.getTenSach().toLowerCase() : "";
            String theLoai = sach.getTheLoai() != null ? sach.getTheLoai().toLowerCase() : "";
            String maNXB = sach.getMaNXB() != null ? sach.getMaNXB().toLowerCase() : "";
            
            if (maSach.contains(lowerKeyword) || tenSach.contains(lowerKeyword) || 
                theLoai.contains(lowerKeyword) || maNXB.contains(lowerKeyword)) {
                result.add(sach);
            }
        }
        return result;
    }
}