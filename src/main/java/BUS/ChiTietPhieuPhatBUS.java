package BUS;

import DAO.ChiTietPhieuPhatDAO;
import DTO.ChiTietPhieuPhatDTO;
import java.util.ArrayList;

public class ChiTietPhieuPhatBUS {
    private ChiTietPhieuPhatDAO ctppDAO;
    private ArrayList<ChiTietPhieuPhatDTO> listCTPP;

    public ChiTietPhieuPhatBUS() {
        ctppDAO = new ChiTietPhieuPhatDAO();
        // Khởi tạo danh sách bằng cách lấy toàn bộ dữ liệu từ Database
        listCTPP = ctppDAO.getAll();
    }

    /**
     * Làm mới và trả về danh sách chi tiết phiếu phạt đang hoạt động (TrangThai = 1)
     */
    public ArrayList<ChiTietPhieuPhatDTO> getAll() {
        listCTPP = ctppDAO.getAll();
        return listCTPP;
    }

    /**
     * Lấy danh sách chi tiết phiếu phạt theo mã phiếu phạt (MaPP)
     */
    public ArrayList<ChiTietPhieuPhatDTO> getByMaPP(String maPP) {
        // Có thể gọi trực tiếp DAO hoặc lọc từ list nội bộ
        return ctppDAO.getByMaPP(maPP);
    }

    /**
     * Thêm mới một chi tiết phiếu phạt
     */
    public boolean add(ChiTietPhieuPhatDTO ct) {
        if (ctppDAO.add(ct)) {
            // Sau khi thêm vào DB thành công, cập nhật vào danh sách nội bộ
            listCTPP.add(ct);
            return true;
        }
        return false;
    }

    /**
     * Cập nhật thông tin chi tiết phiếu phạt
     */
    public boolean update(ChiTietPhieuPhatDTO ct) {
        if (ctppDAO.update(ct)) {
            // Tìm và cập nhật đối tượng trong danh sách nội bộ để đồng bộ với GUI
            for (int i = 0; i < listCTPP.size(); i++) {
                if (listCTPP.get(i).getMaCTPP().equals(ct.getMaCTPP())) {
                    listCTPP.set(i, ct);
                    break;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Xóa mềm chi tiết phiếu phạt (Chuyển TrangThai về 0)
     */
    public boolean delete(String maCTPP) {
        if (ctppDAO.delete(maCTPP)) {
            // Vì getAll chỉ lấy TrangThai = 1, nên ta xóa khỏi danh sách nội bộ
            listCTPP.removeIf(ct -> ct.getMaCTPP().equals(maCTPP));
            return true;
        }
        return false;
    }

    /**
     * Tìm kiếm chi tiết phiếu phạt theo mã chi tiết (MaCTPP) trong danh sách hiện tại
     */
    public ChiTietPhieuPhatDTO findById(String maCTPP) {
        for (ChiTietPhieuPhatDTO ct : listCTPP) {
            if (ct.getMaCTPP().equalsIgnoreCase(maCTPP)) {
                return ct;
            }
        }
        return null;
    }
}