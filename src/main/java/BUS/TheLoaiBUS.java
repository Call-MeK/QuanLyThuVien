package BUS;

import DAO.TheLoaiDAO;
import DTO.TheLoaiDTO;
import java.util.ArrayList;

public class TheLoaiBUS {
    private TheLoaiDAO theLoaiDAO;
    private ArrayList<TheLoaiDTO> listTheLoai;

    public TheLoaiBUS() {
        theLoaiDAO = new TheLoaiDAO();
        listTheLoai = new ArrayList<>();
    }

    public ArrayList<TheLoaiDTO> getAll() {
        listTheLoai = theLoaiDAO.getAll();
        return listTheLoai;
    }

    public TheLoaiDTO getById(String maTheLoai) {
        if (listTheLoai != null && !listTheLoai.isEmpty()) {
            for (TheLoaiDTO tl : listTheLoai) {
                if (tl.getMaTheLoai().equals(maTheLoai)) {
                    return tl;
                }
            }
        }
        return theLoaiDAO.getById(maTheLoai);
    }

    public String add(TheLoaiDTO theLoai) {
        if (theLoai.getMaTheLoai() == null || theLoai.getMaTheLoai().trim().isEmpty()) {
            return "Mã Thể Loại không được để trống!";
        }
        if (theLoai.getTenTheLoai() == null || theLoai.getTenTheLoai().trim().isEmpty()) {
            return "Tên Thể Loại không được để trống!";
        }

        if (getById(theLoai.getMaTheLoai()) != null) {
            return "Mã Thể Loại đã tồn tại!";
        }

        if (theLoaiDAO.add(theLoai)) {
            if (listTheLoai != null) {
                listTheLoai.add(theLoai);
            }
            return "Thêm thể loại thành công!";
        }
        return "Thêm thất bại do lỗi cơ sở dữ liệu!";
    }

    public String update(TheLoaiDTO theLoai) {
        if (theLoai.getTenTheLoai() == null || theLoai.getTenTheLoai().trim().isEmpty()) {
            return "Tên Thể Loại không được để trống!";
        }

        if (getById(theLoai.getMaTheLoai()) == null) {
            return "Không tìm thấy Thể Loại cần cập nhật!";
        }

        if (theLoaiDAO.update(theLoai)) {
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

    public String delete(String maTheLoai) {
        if (getById(maTheLoai) == null) {
            return "Mã Thể Loại không tồn tại!";
        }

        if (theLoaiDAO.delete(maTheLoai)) {
            if (listTheLoai != null) {
                listTheLoai.removeIf(tl -> tl.getMaTheLoai().equals(maTheLoai));
            }
            return "Xóa thể loại thành công!";
        }
        return "Xóa thất bại! Có thể thể loại này đang được sử dụng cho một số sách, vui lòng kiểm tra lại.";
    }

    public ArrayList<TheLoaiDTO> search(String keyword) {
        ArrayList<TheLoaiDTO> result = new ArrayList<>();
        if (listTheLoai == null || listTheLoai.isEmpty()) {
            getAll();
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