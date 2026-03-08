package BUS;

import DAO.TacGiaDAO;
import DTO.TacGiaDTO;
import java.util.ArrayList;

public class TacGiaBUS {
    private TacGiaDAO tgDAO;
    private ArrayList<TacGiaDTO> listTacGia;

    public TacGiaBUS() {
        tgDAO = new TacGiaDAO();
        listTacGia = new ArrayList<>();
    }

    public ArrayList<TacGiaDTO> getAll() {
        listTacGia = tgDAO.getAll();
        return listTacGia;
    }

    public TacGiaDTO getById(String MaTacGia) {
        if (listTacGia != null && !listTacGia.isEmpty()) {
            for (TacGiaDTO tg : listTacGia) {
                if (tg.getMaTacGia().equals(MaTacGia)) {
                    return tg;
                }
            }
        }
        return tgDAO.getById(MaTacGia);
    }

    public String add(TacGiaDTO tg) {
        if (tg.getMaTacGia() == null || tg.getMaTacGia().trim().isEmpty()) {
            return "Mã Tác Giả không được để trống!";
        }
        if (tg.getTenTacGia() == null || tg.getTenTacGia().trim().isEmpty()) {
            return "Tên Tác Giả không được để trống!";
        }

        if (getById(tg.getMaTacGia()) != null) {
            return "Mã Tác Giả đã tồn tại!";
        }

        if (tgDAO.add(tg)) {
            if (listTacGia != null) {
                listTacGia.add(tg);
            }
            return "Thêm tác giả thành công!";
        }
        return "Thêm thất bại do lỗi cơ sở dữ liệu!";
    }

    public String update(TacGiaDTO tg) {
        if (tg.getTenTacGia() == null || tg.getTenTacGia().trim().isEmpty()) {
            return "Tên Tác Giả không được để trống!";
        }

        if (getById(tg.getMaTacGia()) == null) {
            return "Không tìm thấy Tác Giả cần cập nhật!";
        }

        if (tgDAO.update(tg)) {
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

    public String delete(String MaTacGia) {
        if (getById(MaTacGia) == null) {
            return "Mã Tác Giả không tồn tại!";
        }

        if (tgDAO.delete(MaTacGia)) {
            if (listTacGia != null) {
                listTacGia.removeIf(tg -> tg.getMaTacGia().equals(MaTacGia));
            }
            return "Xóa tác giả thành công!";
        }
        return "Xóa thất bại! Có thể tác giả này đang bị ràng buộc dữ liệu (ví dụ: đang có sách do tác giả này viết).";
    }

    public ArrayList<TacGiaDTO> search(String keyword) {
        ArrayList<TacGiaDTO> result = new ArrayList<>();
        if (listTacGia == null || listTacGia.isEmpty()) {
            getAll();
        }

        String lowerKeyword = keyword.toLowerCase().trim();
        for (TacGiaDTO tg : listTacGia) {
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