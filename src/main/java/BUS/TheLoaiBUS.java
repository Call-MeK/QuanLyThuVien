package BUS;

import DAO.TheLoaiDAO;
import DTO.TheLoaiDTO;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class TheLoaiBUS {
    private TheLoaiDAO theLoaiDAO;
    private ArrayList<TheLoaiDTO> listTheLoai;

    public TheLoaiBUS() {
        theLoaiDAO = new TheLoaiDAO();
        listTheLoai = new ArrayList<>();
    }

    // Lấy tất cả danh sách Thể Loại
    public ArrayList<TheLoaiDTO> getAll() {
        listTheLoai = theLoaiDAO.getAll();
        return listTheLoai;
    }

    // Tìm kiếm Thể Loại theo mã
    public TheLoaiDTO getById(String maTheLoai) {
        // Tìm trong list cache trước để tối ưu hiệu suất
        if (listTheLoai != null && !listTheLoai.isEmpty()) {
            for (TheLoaiDTO tl : listTheLoai) {
                if (tl.getMaTheLoai().equals(maTheLoai)) {
                    return tl;
                }
            }
        }
        // Nếu không có trong cache thì mới gọi DAO xuống DB
        return theLoaiDAO.getById(maTheLoai);
    }

    // Thêm Thể Loại mới
    public String add(TheLoaiDTO theLoai) {
        // 1. Kiểm tra dữ liệu đầu vào (Validation)
        if (theLoai.getMaTheLoai() == null || theLoai.getMaTheLoai().trim().isEmpty()) {
            return "Mã Thể Loại không được để trống!";
        }
        if (theLoai.getTenTheLoai() == null || theLoai.getTenTheLoai().trim().isEmpty()) {
            return "Tên Thể Loại không được để trống!";
        }

        // 2. Kiểm tra trùng mã Thể Loại
        if (getById(theLoai.getMaTheLoai()) != null) {
            return "Mã Thể Loại đã tồn tại!";
        }

        // 3. Gọi DAO để thêm vào CSDL
        if (theLoaiDAO.add(theLoai)) {
            // Thêm thành công thì cập nhật luôn vào danh sách tạm
            if (listTheLoai != null) {
                listTheLoai.add(theLoai);
            }
            return "Thêm thể loại thành công!";
        }
        return "Thêm thất bại do lỗi cơ sở dữ liệu!";
    }

    // Cập nhật thông tin Thể Loại
    public String update(TheLoaiDTO theLoai) {
        // 1. Kiểm tra dữ liệu
        if (theLoai.getTenTheLoai() == null || theLoai.getTenTheLoai().trim().isEmpty()) {
            return "Tên Thể Loại không được để trống!";
        }

        // 2. Kiểm tra xem thể loại có tồn tại hay không
        if (getById(theLoai.getMaTheLoai()) == null) {
            return "Không tìm thấy Thể Loại cần cập nhật!";
        }

        // 3. Gọi DAO để update
        if (theLoaiDAO.update(theLoai)) {
            // Cập nhật lại list cache
            if (listTheLoai != null) {
                for (int i = 0; i < listTheLoai.size(); i++) {
                    if (listTheLoai.get(i).getMaTheLoai().equals(theLoai.getMaTheLoai())) {
                        listTheLoai.set(i, theLoai);
                        break;
                    }
                }
            }
            return "Cập nhật thể loại thành công!";
        }
        return "Cập nhật thất bại!";
    }

    // Xóa Thể Loại (Xóa cứng)
    public String delete(String maTheLoai) {
        // 1. Kiểm tra mã có tồn tại không
        if (getById(maTheLoai) == null) {
            return "Mã Thể Loại không tồn tại!";
        }

        // 2. Gọi DAO để xóa
        if (theLoaiDAO.delete(maTheLoai)) {
            // Bỏ khỏi list cache
            if (listTheLoai != null) {
                listTheLoai.removeIf(tl -> tl.getMaTheLoai().equals(maTheLoai));
            }
            return "Xóa thể loại thành công!";
        }
        return "Xóa thất bại! Có thể thể loại này đang được sử dụng cho một số sách, vui lòng kiểm tra lại.";
    }
    
    // (Tùy chọn) Tìm kiếm Thể Loại theo tên hoặc mã
    public ArrayList<TheLoaiDTO> search(String keyword) {
        ArrayList<TheLoaiDTO> result = new ArrayList<>();
        if (listTheLoai == null || listTheLoai.isEmpty()) {
            getAll(); // Tải dữ liệu nếu list đang trống
        }
        
        String lowerKeyword = keyword.toLowerCase().trim();
        for (TheLoaiDTO tl : listTheLoai) {
            String ma = tl.getMaTheLoai() != null ? tl.getMaTheLoai().toLowerCase() : "";
            String ten = tl.getTenTheLoai() != null ? tl.getTenTheLoai().toLowerCase() : "";
            
            if (ma.contains(lowerKeyword) || ten.contains(lowerKeyword)) {
                result.add(tl);
            }
        }
        return result;
    }
}