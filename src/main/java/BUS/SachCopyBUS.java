package BUS;

import DAO.SachCopyDAO;
import DTO.SachCopyDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class SachCopyBUS {
    private SachCopyDAO scDAO;
    private List<SachCopyDTO> listSachCopy;

    public SachCopyBUS() {
        scDAO = new SachCopyDAO();
        listSachCopy = new ArrayList<>();
    }

    // Lấy tất cả danh sách Sách Copy (chỉ lấy những cuốn chưa bị xóa)
    public List<SachCopyDTO> getAll() {
        // Hàm getAll() ở DAO đã có sẵn điều kiện IsDeleted=0
        listSachCopy = scDAO.getAll();
        return listSachCopy;
    }

    // Tìm kiếm Sách Copy theo Mã Vạch
    public SachCopyDTO findById(String maVach) {
        // Ưu tiên tìm trong cache trước
        if (listSachCopy != null && !listSachCopy.isEmpty()) {
            for (SachCopyDTO sc : listSachCopy) {
                if (sc.getMaVach() != null && sc.getMaVach().equals(maVach)) {
                    return sc;
                }
            }
        }
        // Nếu không có trong cache thì gọi DAO
        return scDAO.findById(maVach);
    }

    // Thêm Bản sao Sách mới
    public String insert(SachCopyDTO sc) {
        // 1. Kiểm tra dữ liệu đầu vào (Validation)
        if (sc.getMaVach() == null || sc.getMaVach().trim().isEmpty()) {
            return "Mã vạch không được để trống!";
        }
        if (sc.getMaSach() == null || sc.getMaSach().trim().isEmpty()) {
            return "Mã sách gốc không được để trống!";
        }
        if (sc.getTenSachBanSao() == null || sc.getTenSachBanSao().trim().isEmpty()) {
            return "Tên sách bản sao không được để trống!";
        }

        // 2. Kiểm tra trùng mã vạch
        if (findById(sc.getMaVach()) != null) {
            return "Mã vạch này đã tồn tại trong hệ thống!";
        }

        // 3. Đặt mặc định IsDeleted = false khi thêm mới
        sc.setIsDeleted(false);

        // 4. Gọi DAO để thêm
        if (scDAO.insert(sc)) {
            if (listSachCopy != null) {
                listSachCopy.add(sc);
            }
            return "Thêm bản sao sách thành công!";
        }
        return "Thêm thất bại do lỗi cơ sở dữ liệu!";
    }

    // Cập nhật thông tin Bản sao Sách
    public String update(SachCopyDTO sc) {
        // 1. Kiểm tra dữ liệu
        if (sc.getMaVach() == null || sc.getMaVach().trim().isEmpty()) {
            return "Mã vạch không hợp lệ!";
        }
        if (sc.getTenSachBanSao() == null || sc.getTenSachBanSao().trim().isEmpty()) {
            return "Tên sách bản sao không được để trống!";
        }

        // 2. Kiểm tra tồn tại
        if (findById(sc.getMaVach()) == null) {
            return "Không tìm thấy bản sao sách cần cập nhật!";
        }

        // 3. Gọi DAO để update
        if (scDAO.update(sc)) {
            // Cập nhật lại list cache
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

    // Xóa Bản sao Sách (Soft Delete)
    public String delete(String maVach) {
        // 1. Kiểm tra tồn tại
        if (findById(maVach) == null) {
            return "Mã vạch không tồn tại hoặc sách đã bị xóa!";
        }

        // 2. Gọi DAO để cập nhật IsDeleted = 1
        if (scDAO.delete(maVach)) {
            // Xóa luôn khỏi danh sách hiển thị tạm (vì listSachCopy chỉ hiển thị sách chưa xóa)
            if (listSachCopy != null) {
                listSachCopy.removeIf(sc -> sc.getMaVach().equals(maVach));
            }
            return "Xóa bản sao sách thành công!";
        }
        return "Xóa thất bại!";
    }
    
    // Tìm kiếm Sách Copy (Tìm trong Cache thay vì gọi DB để tăng tốc)
    public List<SachCopyDTO> search(String keyword) {
        List<SachCopyDTO> result = new ArrayList<>();
        if (listSachCopy == null || listSachCopy.isEmpty()) {
            getAll(); // Load data nếu list trống
        }
        
        String lowerKeyword = keyword.toLowerCase().trim();
        for (SachCopyDTO sc : listSachCopy) {
            String maVach = sc.getMaVach() != null ? sc.getMaVach().toLowerCase() : "";
            String tenSach = sc.getTenSachBanSao() != null ? sc.getTenSachBanSao().toLowerCase() : "";
            String tinhTrang = sc.getTinhTrang() != null ? sc.getTinhTrang().toLowerCase() : "";
            
            // Tìm theo Mã Vạch, Tên Sách hoặc Tình Trạng
            if (maVach.contains(lowerKeyword) || 
                tenSach.contains(lowerKeyword) || 
                tinhTrang.contains(lowerKeyword)) {
                result.add(sc);
            }
        }
        return result;
    }
}