package BUS;

import DAO.TacGiaDAO;
import DTO.TacGiaDTO;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class TacGiaBUS {
    private TacGiaDAO tgDAO;
    private ArrayList<TacGiaDTO> listTacGia;

    public TacGiaBUS() {
        tgDAO = new TacGiaDAO();
        listTacGia = new ArrayList<>();
    }

    // Lấy tất cả danh sách Tác Giả
    public ArrayList<TacGiaDTO> getAll() {
        listTacGia = tgDAO.getAll();
        return listTacGia;
    }

    // Tìm kiếm Tác Giả theo mã
    public TacGiaDTO getById(String MaTacGia) {
        // Tìm trong list cache trước
        if (listTacGia != null && !listTacGia.isEmpty()) {
            for (TacGiaDTO tg : listTacGia) {
                if (tg.getMaTacGia().equals(MaTacGia)) {
                    return tg;
                }
            }
        }
        // Nếu không có trong cache thì gọi DAO
        return tgDAO.getById(MaTacGia);
    }

    // Thêm Tác Giả mới
    public String add(TacGiaDTO tg) {
        // 1. Kiểm tra dữ liệu đầu vào (Validation)
        if (tg.getMaTacGia() == null || tg.getMaTacGia().trim().isEmpty()) {
            return "Mã Tác Giả không được để trống!";
        }
        if (tg.getTenTacGia() == null || tg.getTenTacGia().trim().isEmpty()) {
            return "Tên Tác Giả không được để trống!";
        }

        // 2. Kiểm tra trùng mã Tác Giả
        if (getById(tg.getMaTacGia()) != null) {
            return "Mã Tác Giả đã tồn tại!";
        }

        // 3. Gọi DAO để thêm vào DB
        if (tgDAO.add(tg)) {
            // Thêm vào cache
            if (listTacGia != null) {
                listTacGia.add(tg);
            }
            return "Thêm tác giả thành công!";
        }
        return "Thêm thất bại do lỗi cơ sở dữ liệu!";
    }

    // Cập nhật thông tin Tác Giả
    public String update(TacGiaDTO tg) {
        // 1. Kiểm tra dữ liệu
        if (tg.getTenTacGia() == null || tg.getTenTacGia().trim().isEmpty()) {
            return "Tên Tác Giả không được để trống!";
        }

        // 2. Kiểm tra tồn tại
        if (getById(tg.getMaTacGia()) == null) {
            return "Không tìm thấy Tác Giả cần cập nhật!";
        }

        // 3. Gọi DAO để update DB
        if (tgDAO.update(tg)) {
            // Cập nhật lại list cache
            if (listTacGia != null) {
                for (int i = 0; i < listTacGia.size(); i++) {
                    if (listTacGia.get(i).getMaTacGia().equals(tg.getMaTacGia())) {
                        listTacGia.set(i, tg);
                        break;
                    }
                }
            }
            return "Cập nhật tác giả thành công!";
        }
        return "Cập nhật thất bại!";
    }

    // Xóa Tác Giả (Xóa cứng)
    public String delete(String MaTacGia) {
        // 1. Kiểm tra mã có tồn tại không
        if (getById(MaTacGia) == null) {
            return "Mã Tác Giả không tồn tại!";
        }

        // 2. Gọi DAO để xóa
        if (tgDAO.delete(MaTacGia)) {
            // Xóa khỏi list cache
            if (listTacGia != null) {
                listTacGia.removeIf(tg -> tg.getMaTacGia().equals(MaTacGia));
            }
            return "Xóa tác giả thành công!";
        }
        return "Xóa thất bại! Có thể tác giả này đang bị ràng buộc dữ liệu (ví dụ: đang có sách do tác giả này viết).";
    }
    
    // (Tùy chọn) Tìm kiếm Tác Giả theo tên hoặc quốc tịch
    public ArrayList<TacGiaDTO> search(String keyword) {
        ArrayList<TacGiaDTO> result = new ArrayList<>();
        if (listTacGia == null || listTacGia.isEmpty()) {
            getAll();
        }
        
        String lowerKeyword = keyword.toLowerCase().trim();
        for (TacGiaDTO tg : listTacGia) {
            // Check null an toàn trước khi contains
            String ten = tg.getTenTacGia() != null ? tg.getTenTacGia().toLowerCase() : "";
            String quocTich = tg.getQuocTich() != null ? tg.getQuocTich().toLowerCase() : "";
            String sdt = tg.getSoDienThoai() != null ? tg.getSoDienThoai().toLowerCase() : "";
            
            if (ten.contains(lowerKeyword) || quocTich.contains(lowerKeyword) || sdt.contains(lowerKeyword)) {
                result.add(tg);
            }
        }
        return result;
    }
}