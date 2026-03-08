package BUS;

import DAO.SachDAO;
import DTO.SachDTO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SachBUS {
    private SachDAO sachDAO;
    private List<SachDTO> listSach;

    public SachBUS() {
        sachDAO = new SachDAO();
        listSach = new ArrayList<>();
    }

    public List<SachDTO> getAll() {
        listSach = sachDAO.getAll();
        return listSach;
    }

    public SachDTO getById(String maSach) {
        if (listSach != null && !listSach.isEmpty()) {
            for (SachDTO s : listSach) {
                if (s.getMaSach() != null && s.getMaSach().trim().equalsIgnoreCase(maSach.trim()))
                    return s;
            }
        }
        return sachDAO.getById(maSach);
    }

    public List<Object[]> getDanhSachDayDu() {
        return sachDAO.getDanhSachDayDu();
    }

    public String getHinhAnh(String maSach) {
        SachDTO sach = getById(maSach);
        if (sach != null) {
            return sach.getHinhAnh();
        }
        return null;
    }

    public boolean isDangMuon(String maSach) {
        return sachDAO.isDangMuon(maSach);
    }

    public String generateMaSach() {
        return sachDAO.generateMaSach();
    }

    public boolean insertSachTacGia(String maSach, String maTacGia) {
        return sachDAO.insertSachTacGia(maSach, maTacGia);
    }

    public boolean deleteSachTacGia(String maSach) {
        return sachDAO.deleteSachTacGia(maSach);
    }

    public String insert(SachDTO sach) {
        if (sach.getMaSach() == null || sach.getMaSach().trim().isEmpty())
            return "Mã sách không được để trống!";
        if (sach.getTenSach() == null || sach.getTenSach().trim().isEmpty())
            return "Tên sách không được để trống!";
        if (sach.getTheLoai() == null || sach.getTheLoai().trim().isEmpty())
            return "Thể loại không được để trống!";
        if (sach.getGiaBia() != null && sach.getGiaBia() < 0)
            return "Giá bìa không được là số âm!";
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (sach.getNamXB() <= 0 || sach.getNamXB() > currentYear)
            return "Năm xuất bản không hợp lệ (1 - " + currentYear + ")!";
        if (getById(sach.getMaSach()) != null)
            return "Mã sách này đã tồn tại trong hệ thống!";
        sach.setIsDeleted(false);
        if (sachDAO.insert(sach)) {
            if (listSach != null)
                listSach.add(sach);
            return "Thêm sách thành công!";
        }
        return "Thêm thất bại do lỗi cơ sở dữ liệu!";
    }

    public String update(SachDTO sach) {
        if (sach.getMaSach() == null || sach.getMaSach().trim().isEmpty())
            return "Mã sách không hợp lệ!";
        if (sach.getTenSach() == null || sach.getTenSach().trim().isEmpty())
            return "Tên sách không được để trống!";
        if (sach.getGiaBia() != null && sach.getGiaBia() < 0)
            return "Giá bìa không được là số âm!";
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (sach.getNamXB() <= 0 || sach.getNamXB() > currentYear)
            return "Năm xuất bản không hợp lệ!";
        if (getById(sach.getMaSach()) == null)
            return "Không tìm thấy cuốn sách cần cập nhật!";
        if (sachDAO.update(sach)) {
            if (listSach != null) {
                for (int i = 0; i < listSach.size(); i++) {
                    if (listSach.get(i).getMaSach().equals(sach.getMaSach())) {
                        listSach.set(i, sach);
                        break;
                    }
                }
            }
            return "Cập nhật thông tin sách thành công!";
        }
        return "Cập nhật thất bại!";
    }

    public String delete(String maSach) {
        if (getById(maSach) == null)
            return "Mã sách không tồn tại hoặc đã bị xóa!";
        if (isDangMuon(maSach))
            return "Không thể xóa! Sách này đang được mượn bởi độc giả.\nVui lòng xác nhận trả sách trước.";
        if (sachDAO.delete(maSach)) {
            if (listSach != null)
                listSach.removeIf(s -> s.getMaSach().equals(maSach));
            return "Xóa sách thành công!";
        }
        return "Xóa thất bại!";
    }

    public List<SachDTO> search(String keyword) {
        List<SachDTO> result = new ArrayList<>();
        if (listSach == null || listSach.isEmpty())
            getAll();
        String kw = keyword.toLowerCase().trim();
        for (SachDTO sach : listSach) {
            String ma = sach.getMaSach() != null ? sach.getMaSach().toLowerCase() : "";
            String ten = sach.getTenSach() != null ? sach.getTenSach().toLowerCase() : "";
            String loai = sach.getTheLoai() != null ? sach.getTheLoai().toLowerCase() : "";
            String nxb = sach.getMaNXB() != null ? sach.getMaNXB().toLowerCase() : "";
            if (ma.contains(kw) || ten.contains(kw) || loai.contains(kw) || nxb.contains(kw))
                result.add(sach);
        }
        return result;
    }
}