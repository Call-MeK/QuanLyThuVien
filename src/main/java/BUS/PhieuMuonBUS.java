package BUS;

import DAO.PhieuMuonDAO;
import DTO.PhieuMuonDTO;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class PhieuMuonBUS {
    private PhieuMuonDAO pmDAO;
    private ArrayList<PhieuMuonDTO> listPhieuMuon;

    public PhieuMuonBUS() {
        pmDAO = new PhieuMuonDAO();
        listPhieuMuon = new ArrayList<>();
    }

    // Lấy tất cả danh sách Phiếu Mượn
    public ArrayList<PhieuMuonDTO> getAll() {
        listPhieuMuon = pmDAO.getAll();
        return listPhieuMuon;
    }

    // Lấy tổng số lượng Phiếu Mượn
    public int count() {
        return pmDAO.count();
    }

    // Tìm kiếm Phiếu Mượn theo mã
    public PhieuMuonDTO getById(String MaPM) {
        // Ưu tiên tìm trong cache trước
        if (listPhieuMuon != null && !listPhieuMuon.isEmpty()) {
            for (PhieuMuonDTO pm : listPhieuMuon) {
                // Kiểm tra null an toàn cho MaPM
                if (pm.getMaPM() != null && pm.getMaPM().equals(MaPM)) {
                    return pm;
                }
            }
        }
        // Nếu cache trống hoặc không tìm thấy, gọi DAO
        PhieuMuonDTO pm = pmDAO.getById(MaPM);
        // Do DAO của bạn trả về object rỗng (new PhieuMuonDTO()) nếu không thấy, 
        // ta cần kiểm tra xem MaPM bên trong có null không để xác định tồn tại
        if (pm != null && pm.getMaPM() != null) {
            return pm;
        }
        return null; 
    }

    // Thêm Phiếu Mượn mới
    public String insert(PhieuMuonDTO pm) {
        // 1. Kiểm tra dữ liệu đầu vào (Validation)
        if (pm.getMaPM() == null || pm.getMaPM().trim().isEmpty()) {
            return "Mã Phiếu Mượn không được để trống!";
        }
        if (pm.getMaThe() == null || pm.getMaThe().trim().isEmpty()) {
            return "Mã Thẻ (Độc giả) không được để trống!";
        }
        if (pm.getMaNQL() == null || pm.getMaNQL().trim().isEmpty()) {
            return "Mã Người Quản Lý không được để trống!";
        }

        // 2. Kiểm tra trùng mã Phiếu Mượn
        if (getById(pm.getMaPM()) != null) {
            return "Mã Phiếu Mượn đã tồn tại!";
        }

        // 3. Gọi DAO để thêm (Lưu ý: DAO trả về int)
        if (pmDAO.insert(pm) > 0) {
            if (listPhieuMuon != null) {
                listPhieuMuon.add(pm);
            }
            return "Tạo phiếu mượn thành công!";
        }
        return "Tạo phiếu mượn thất bại do lỗi cơ sở dữ liệu!";
    }

    // Cập nhật thông tin Phiếu Mượn
    public String update(PhieuMuonDTO pm) {
        // 1. Kiểm tra mã phiếu rỗng
        if (pm.getMaPM() == null || pm.getMaPM().trim().isEmpty()) {
            return "Mã Phiếu Mượn không được để trống!";
        }

        // 2. Kiểm tra tồn tại
        if (getById(pm.getMaPM()) == null) {
            return "Không tìm thấy Phiếu Mượn cần cập nhật!";
        }

        // 3. Gọi DAO để update
        if (pmDAO.update(pm) > 0) {
            // Cập nhật lại cache
            if (listPhieuMuon != null) {
                for (int i = 0; i < listPhieuMuon.size(); i++) {
                    if (listPhieuMuon.get(i).getMaPM().equals(pm.getMaPM())) {
                        listPhieuMuon.set(i, pm);
                        break;
                    }
                }
            }
            return "Cập nhật phiếu mượn thành công!";
        }
        return "Cập nhật thất bại!";
    }

    // Xóa Phiếu Mượn (Hard Delete)
    public String delete(String MaPM) {
        // 1. Kiểm tra tồn tại
        if (getById(MaPM) == null) {
            return "Mã Phiếu Mượn không tồn tại!";
        }

        // 2. Gọi DAO để xóa
        if (pmDAO.delete(MaPM) > 0) {
            if (listPhieuMuon != null) {
                listPhieuMuon.removeIf(pm -> pm.getMaPM().equals(MaPM));
            }
            return "Xóa phiếu mượn thành công!";
        }
        return "Xóa thất bại! Có thể phiếu mượn này đang chứa chi tiết phiếu mượn (sách). Vui lòng xóa chi tiết trước.";
    }
    
    // (Tùy chọn) Tìm kiếm Phiếu Mượn (Theo Mã PM, Mã Thẻ, Mã NQL)
    public ArrayList<PhieuMuonDTO> search(String keyword) {
        ArrayList<PhieuMuonDTO> result = new ArrayList<>();
        if (listPhieuMuon == null || listPhieuMuon.isEmpty()) {
            getAll();
        }
        
        String lowerKeyword = keyword.toLowerCase().trim();
        for (PhieuMuonDTO pm : listPhieuMuon) {
            String maPM = pm.getMaPM() != null ? pm.getMaPM().toLowerCase() : "";
            String maThe = pm.getMaThe() != null ? pm.getMaThe().toLowerCase() : "";
            String maNQL = pm.getMaNQL() != null ? pm.getMaNQL().toLowerCase() : "";
            String tinhTrang = pm.getTinhTrang() != null ? pm.getTinhTrang().toLowerCase() : "";
            
            if (maPM.contains(lowerKeyword) || maThe.contains(lowerKeyword) || 
                maNQL.contains(lowerKeyword) || tinhTrang.contains(lowerKeyword)) {
                result.add(pm);
            }
        }
        return result;
    }
    // =======================================================
    // HÀM GỌI DỮ LIỆU ĐÃ JOIN DÀNH RIÊNG CHO GIAO DIỆN
    // =======================================================
    public ArrayList<Object[]> getDanhSachSachDangMuon() {
        return pmDAO.getDanhSachSachDangMuon();
    }
   // Tự động sinh mã
    public String generateMaPM() {
        return pmDAO.generateMaPM();
    }
}