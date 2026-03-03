package BUS;

import DAO.NguoiQuanLyDAO;
import DTO.NguoiQuanLyDTO;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class NguoiQuanLyBUS {
    private NguoiQuanLyDAO nqlDAO;
    private ArrayList<NguoiQuanLyDTO> listNQL;

    public NguoiQuanLyBUS() {
        nqlDAO = new NguoiQuanLyDAO();
        listNQL = new ArrayList<>();
    }

    // Lấy tất cả danh sách Người Quản Lý
    public ArrayList<NguoiQuanLyDTO> getAll() {
        listNQL = nqlDAO.getAll();
        return listNQL;
    }

    // Lấy danh sách Người Quản Lý Đang Hoạt Động (Chưa bị xóa mềm)
    public ArrayList<NguoiQuanLyDTO> getAllActive() {
        if (listNQL == null || listNQL.isEmpty()) {
            getAll();
        }
        ArrayList<NguoiQuanLyDTO> activeList = new ArrayList<>();
        for (NguoiQuanLyDTO nql : listNQL) {
            // Chỉ lấy những người có IsDeleted = false
            if (nql.getIsDeleted() != null && !nql.getIsDeleted()) {
                activeList.add(nql);
            }
        }
        return activeList;
    }

    // Tìm kiếm Người Quản Lý theo mã
    public NguoiQuanLyDTO getById(String MaNQL) {
        if (listNQL != null && !listNQL.isEmpty()) {
            for (NguoiQuanLyDTO nql : listNQL) {
                if (nql.getMaNQL().equals(MaNQL)) {
                    return nql;
                }
            }
        }
        // Nếu không có trong cache thì gọi DAO
        return nqlDAO.getById(MaNQL);
    }

    // Thêm Người Quản Lý
    public String add(NguoiQuanLyDTO nql) {
        // 1. Kiểm tra tính hợp lệ (Validation cơ bản)
        if (nql.getMaNQL().trim().isEmpty() || nql.getHoTen().trim().isEmpty()) {
            return "Mã người quản lý và Họ tên không được để trống!";
        }
        if (nql.getTenDangNhap().trim().isEmpty() || nql.getMatKhau().trim().isEmpty()) {
            return "Tên đăng nhập và Mật khẩu không được để trống!";
        }

        // 2. Kiểm tra trùng mã
        if (getById(nql.getMaNQL()) != null) {
            return "Mã Người Quản Lý (hoặc Mã Người) đã tồn tại!";
        }

        // 3. Mặc định khi thêm mới thì IsDeleted = false (chưa bị xóa)
        nql.setIsDeleted(false);

        // 4. Gọi DAO để thêm
        if (nqlDAO.add(nql)) {
            if (listNQL != null) {
                listNQL.add(nql);
            }
            return "Thêm người quản lý thành công!";
        }
        return "Thêm thất bại do lỗi CSDL!";
    }

    // Cập nhật thông tin Người Quản Lý
    public String update(NguoiQuanLyDTO nql) {
        // 1. Kiểm tra dữ liệu trống
        if (nql.getHoTen().trim().isEmpty()) {
            return "Họ tên không được để trống!";
        }

        // 2. Kiểm tra tồn tại
        if (getById(nql.getMaNQL()) == null) {
            return "Không tìm thấy Người quản lý cần cập nhật!";
        }

        // 3. Gọi DAO cập nhật
        if (nqlDAO.update(nql)) {
            // Cập nhật lại list cache
            if (listNQL != null) {
                for (int i = 0; i < listNQL.size(); i++) {
                    if (listNQL.get(i).getMaNQL().equals(nql.getMaNQL())) {
                        listNQL.set(i, nql);
                        break;
                    }
                }
            }
            return "Cập nhật thành công!";
        }
        return "Cập nhật thất bại!";
    }

    // Xóa mềm (Soft Delete) Người Quản Lý
    public String softDelete(String MaNQL) {
        NguoiQuanLyDTO nql = getById(MaNQL);
        
        // 1. Kiểm tra tồn tại
        if (nql == null) {
            return "Mã Người Quản Lý không tồn tại!";
        }
        
        // 2. Kiểm tra xem đã bị xóa trước đó chưa
        if (nql.getIsDeleted() != null && nql.getIsDeleted()) {
            return "Người quản lý này đã bị xóa trước đó!";
        }

        // 3. Gọi DAO để xóa mềm
        if (nqlDAO.softdelete(MaNQL)) {
            // Cập nhật lại trạng thái trong cache thay vì remove khỏi List
            if (listNQL != null) {
                for (NguoiQuanLyDTO item : listNQL) {
                    if (item.getMaNQL().equals(MaNQL)) {
                        item.setIsDeleted(true);
                        break;
                    }
                }
            }
            return "Xóa thành công (Xóa mềm)!";
        }
        return "Xóa thất bại!";
    }

    // (Tùy chọn) Tìm kiếm theo Tên hoặc Tài khoản
    public ArrayList<NguoiQuanLyDTO> search(String keyword) {
        ArrayList<NguoiQuanLyDTO> result = new ArrayList<>();
        if (listNQL == null || listNQL.isEmpty()) {
            getAll();
        }
        
        String lowerKeyword = keyword.toLowerCase();
        for (NguoiQuanLyDTO nql : listNQL) {
            // Bỏ qua những người đã bị xóa mềm nếu bạn chỉ muốn tìm người đang hoạt động
            if (nql.getIsDeleted() != null && nql.getIsDeleted()) {
                continue; 
            }
            
            if (nql.getHoTen().toLowerCase().contains(lowerKeyword) || 
                nql.getTenDangNhap().toLowerCase().contains(lowerKeyword) ||
                nql.getSoDienThoai().contains(keyword)) {
                result.add(nql);
            }
        }
        return result;
    }
}