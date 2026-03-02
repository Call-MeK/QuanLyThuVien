package BUS;

import DAO.ChiTietPhieuPhatDAO;
import DAO.PhieuPhatDAO;
import DTO.ChiTietPhieuPhatDTO;
import DTO.PhieuPhatDTO;
import java.util.ArrayList;

public class PhieuPhatBUS {

    private PhieuPhatDAO phieuPhatDAO;
    private ChiTietPhieuPhatDAO chiTietDAO;

    public PhieuPhatBUS() {
        phieuPhatDAO = new PhieuPhatDAO();
        chiTietDAO = new ChiTietPhieuPhatDAO();
    }

    // 1. Lấy toàn bộ danh sách phiếu phạt (DAO đã tự động chỉ lấy những phiếu chưa bị xóa mềm)
    public ArrayList<PhieuPhatDTO> getAll() {
        return phieuPhatDAO.getAll();
    }

    // 2. Tìm phiếu phạt theo Mã
    public PhieuPhatDTO getById(String maPP) {
        return phieuPhatDAO.getById(maPP);
    }

    // 3. Lấy danh sách các lỗi (chi tiết) của 1 phiếu phạt cụ thể
    public ArrayList<ChiTietPhieuPhatDTO> getChiTiet(String maPP) {
        return chiTietDAO.getByMaPP(maPP);
    }

    // 4. Sửa phiếu phạt
    public boolean update(PhieuPhatDTO pp) {
        return phieuPhatDAO.update(pp);
    }

    // 5. Xóa mềm phiếu phạt (Chỉ cần gọi DAO vì DAO đã đổi thành lệnh UPDATE TrangThai = 0)
    public boolean delete(String maPP) {
        return phieuPhatDAO.delete(maPP);
    }

    // =========================================================================
    // 6. NGHIỆP VỤ CHÍNH: TẠO PHIẾU PHẠT (DÙNG CHO NÚT "TRẢ SÁCH" Ở GIAO DIỆN)
    // =========================================================================
    public boolean taoPhieuPhat(PhieuPhatDTO phieuPhat, ArrayList<ChiTietPhieuPhatDTO> dsChiTiet) {
        
        // BƯỚC 1: Tính tổng tiền phạt từ danh sách các lỗi
        double tongTien = 0;
        for (ChiTietPhieuPhatDTO ct : dsChiTiet) {
            tongTien += Double.parseDouble(ct.getSoTien());
        }
        phieuPhat.setTongTien(String.valueOf(tongTien)); // Cập nhật tổng tiền vào Phiếu Phạt

        // BƯỚC 2: Lưu Phiếu Phạt (Bảng Cha) xuống Database trước
        boolean themChaThanhCong = phieuPhatDAO.add(phieuPhat);

        // BƯỚC 3: Nếu lưu cha thành công, thì lưu tiếp các dòng lỗi (Bảng Con)
        if (themChaThanhCong == true) {
            for (ChiTietPhieuPhatDTO ct : dsChiTiet) {
                ct.setMaPP(phieuPhat.getMaPP()); // Gắn mã phiếu phạt cha cho chi tiết để nó biết nó thuộc về phiếu nào
                chiTietDAO.add(ct);              // Lưu lỗi này xuống Database
            }
            return true; // Tạo phiếu phạt hoàn tất và thành công!
        }

        return false; // Trả về false nếu thêm bảng cha bị lỗi
    }
}