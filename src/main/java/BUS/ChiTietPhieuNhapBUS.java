package BUS;

import DAO.ChiTietPhieuNhapDAO;
import DTO.ChiTietPhieuNhapDTO;
import java.util.List;

public class ChiTietPhieuNhapBUS {
    private ChiTietPhieuNhapDAO chiTietDAO;

    public ChiTietPhieuNhapBUS() {
        chiTietDAO = new ChiTietPhieuNhapDAO();
    }

    // Lấy danh sách chi tiết phiếu nhập dựa trên Mã Phiếu Nhập
    public List<ChiTietPhieuNhapDTO> findByMaPN(String maPN) {
        return chiTietDAO.findByMaPN(maPN);
    }

    // Thêm một chi tiết phiếu nhập
    public boolean insert(ChiTietPhieuNhapDTO ct) {
        return chiTietDAO.insert(ct);
    }

    // Cập nhật số lượng hoặc đơn giá
    public boolean update(ChiTietPhieuNhapDTO ct) {
        return chiTietDAO.update(ct);
    }

    // Xóa một chi tiết phiếu nhập cụ thể (dựa vào Mã PN và Mã Sách)
    public boolean deleteSpecific(String maPN, String maSach) {
        return chiTietDAO.deleteSpecific(maPN, maSach);
    }

    // Xóa tất cả các chi tiết thuộc về một Mã Phiếu Nhập
    public boolean deleteAllByMaPN(String maPN) {
        return chiTietDAO.deleteAllByMaPN(maPN);
    }
}