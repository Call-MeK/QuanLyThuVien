package BUS;

import DAO.NhaXuatBanDAO;
import DTO.NhaXuatBanDTO;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class NhaXuatBanBUS {
    private NhaXuatBanDAO nxbDAO;
    private ArrayList<NhaXuatBanDTO> listNXB;

    public NhaXuatBanBUS() {
        nxbDAO = new NhaXuatBanDAO();
        listNXB = new ArrayList<>();
    }

    // Lấy tất cả danh sách Nhà xuất bản
    public ArrayList<NhaXuatBanDTO> getAll() {
        listNXB = nxbDAO.getAll();
        return listNXB;
    }

    // Tìm kiếm NXB theo mã
    public NhaXuatBanDTO getById(String MaNXB) {
        // Có thể tìm trong listNXB trước để đỡ phải gọi database
        if (listNXB != null) {
            for (NhaXuatBanDTO nxb : listNXB) {
                if (nxb.getMaNXB().equals(MaNXB)) {
                    return nxb;
                }
            }
        }
        // Nếu không có trong list thì gọi DAO
        return nxbDAO.getById(MaNXB);
    }

    // Thêm Nhà xuất bản mới
    public String add(NhaXuatBanDTO nxb) {
        // 1. Kiểm tra dữ liệu đầu vào (Validation)
        if (nxb.getMaNXB().trim().isEmpty() || nxb.getTenNXB().trim().isEmpty()) {
            return "Mã và Tên Nhà Xuất Bản không được để trống!";
        }
        
        // 2. Kiểm tra trùng mã NXB
        if (getById(nxb.getMaNXB()) != null) {
            return "Mã Nhà Xuất Bản đã tồn tại!";
        }

        // 3. Gọi DAO để thêm vào DB
        if (nxbDAO.add(nxb)) {
            // Nếu dùng cache (listNXB), nhớ thêm vào list sau khi add DB thành công
            if (listNXB != null) {
                listNXB.add(nxb);
            }
            return "Thêm thành công!";
        }
        return "Thêm thất bại do lỗi cơ sở dữ liệu!";
    }

    // Cập nhật thông tin Nhà xuất bản
    public String update(NhaXuatBanDTO nxb) {
        // 1. Kiểm tra dữ liệu
        if (nxb.getMaNXB().trim().isEmpty() || nxb.getTenNXB().trim().isEmpty()) {
            return "Mã và Tên Nhà Xuất Bản không được để trống!";
        }

        // 2. Gọi DAO để update DB
        if (nxbDAO.update(nxb)) {
            // Cập nhật lại listNXB nếu đang dùng cache
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

    // Xóa Nhà xuất bản
    public String delete(String MaNXB) {
        // 1. Kiểm tra xem mã NXB có tồn tại không
        if (getById(MaNXB) == null) {
            return "Mã Nhà Xuất Bản không tồn tại!";
        }

        // 2. Gọi DAO để xóa
        if (nxbDAO.delete(MaNXB)) {
            // Xóa khỏi listNXB
            if (listNXB != null) {
                listNXB.removeIf(nxb -> nxb.getMaNXB().equals(MaNXB));
            }
            return "Xóa thành công!";
        }
        return "Xóa thất bại!";
    }
    
    // (Tùy chọn) Tìm kiếm NXB theo tên
    public ArrayList<NhaXuatBanDTO> searchByName(String keyword) {
        ArrayList<NhaXuatBanDTO> result = new ArrayList<>();
        if (listNXB == null) {
            getAll(); // Load data nếu list chưa có
        }
        for (NhaXuatBanDTO nxb : listNXB) {
            if (nxb.getTenNXB().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(nxb);
            }
        }
        return result;
    }
}