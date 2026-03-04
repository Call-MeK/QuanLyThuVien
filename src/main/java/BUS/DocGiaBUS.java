package BUS;

import DAO.DocGiaDAO;
import DTO.DocGiaDTO;
import java.util.ArrayList;

public class DocGiaBUS {
    private DocGiaDAO docGiaDAO;
    private ArrayList<DocGiaDTO> listdocGia;

    public DocGiaBUS() {
        docGiaDAO = new DocGiaDAO();
        listdocGia = docGiaDAO.getAll();
    }

    public ArrayList<DocGiaDTO> getAll() {
        return docGiaDAO.getAll();
    }

    public ArrayList<DocGiaDTO> getListDocGia() {
        return listdocGia;
    }

    // Tải lại danh sách từ DB để đồng bộ bộ nhớ với DB thật
    public void reloadFromDB() {
        listdocGia = docGiaDAO.getAll();
    }

    public boolean hasMaDocGia(String maDocGia) {
        for (DocGiaDTO dg : listdocGia) {
            if (dg.getMaDocGia().equals(maDocGia)) {
                return true;
            }
        }
        return false;
    }

    public boolean isDocGiaLocked(String maDocGia) {
        for (DocGiaDTO dg : listdocGia) {
            if (dg.getMaDocGia().equals(maDocGia)) {
                return dg.getIsDeleted() != null && dg.getIsDeleted();
            }
        }
        return false;
    }

    public String addDocGia(DocGiaDTO dg) {
        if (hasMaDocGia(dg.getMaDocGia())) {
            return "Mã độc giả đã tồn tại";
        }
        if (docGiaDAO.add(dg)) {
            listdocGia.add(dg);
            return "Thêm thành công";
        }
        return "Thêm thất bại";
    }

    public String updateDocGia(DocGiaDTO dg) {
        if (!hasMaDocGia(dg.getMaDocGia())) {
            return "Mã độc giả không tồn tại";
        }
        if (isDocGiaLocked(dg.getMaDocGia())) {
            return "Không thể sửa độc giả đang bị khóa";
        }
        if (docGiaDAO.update(dg)) {
            for (int i = 0; i < listdocGia.size(); i++) {
                if (listdocGia.get(i).getMaDocGia().equals(dg.getMaDocGia())) {
                    listdocGia.set(i, dg);
                    break;
                }
            }
            return "Cập nhật thành công";
        }
        return "Cập nhật thất bại";
    }

    // ĐÃ SỬA: DAO.delete() giờ trả về String, xử lý tương ứng
    public String deleteDocGia(String maDocGia) {
        if (!hasMaDocGia(maDocGia)) {
            return "Mã độc giả không tồn tại";
        }
        String result = docGiaDAO.delete(maDocGia);
        if (result.equals("OK")) {
            listdocGia.removeIf(dg -> dg.getMaDocGia().equals(maDocGia));
            return "Xóa thành công";
        }
        return "Xóa thất bại: " + result;
    }

    public String softDeleteDocGia(String maDocGia) {
        if (!hasMaDocGia(maDocGia)) {
            return "Mã độc giả không tồn tại";
        }
        if (isDocGiaLocked(maDocGia)) {
            return "Độc giả đã bị khóa rồi";
        }
        if (docGiaDAO.softdelete(maDocGia)) {
            for (DocGiaDTO dg : listdocGia) {
                if (dg.getMaDocGia().equals(maDocGia)) {
                    dg.setIsDeleted(true);
                    dg.setNgayXoa(java.time.LocalDate.now().toString());
                    dg.setTrangThai("Đã khóa");
                    break;
                }
            }
            return "Khóa thẻ thành công";
        }
        return "Khóa thẻ thất bại";
    }

    public String restoreDocGia(String maDocGia) {
        if (!hasMaDocGia(maDocGia)) {
            return "Mã độc giả không tồn tại";
        }
        if (!isDocGiaLocked(maDocGia)) {
            return "Độc giả này chưa bị khóa";
        }
        DocGiaDTO target = null;
        for (DocGiaDTO dg : listdocGia) {
            if (dg.getMaDocGia().equals(maDocGia)) {
                target = dg;
                break;
            }
        }
        if (target == null) return "Không tìm thấy độc giả";

        // Lưu trạng thái cũ để rollback nếu DB lỗi
        String oldTrangThai = target.getTrangThai();
        boolean oldIsDeleted = target.getIsDeleted();
        String oldNgayXoa = target.getNgayXoa();

        target.setIsDeleted(false);
        target.setNgayXoa("");
        target.setTrangThai("Đang hoạt động");

        if (docGiaDAO.update(target)) {
            return "Mở khóa thành công";
        }
        // Rollback bộ nhớ nếu DB thất bại
        target.setIsDeleted(oldIsDeleted);
        target.setTrangThai(oldTrangThai);
        target.setNgayXoa(oldNgayXoa);
        return "Mở khóa thất bại";
    }

    public DocGiaDTO login(String tenDangNhap, String matKhau) {
        if (listdocGia == null || listdocGia.isEmpty()) {
            listdocGia = docGiaDAO.getAll();
        }
        for (DocGiaDTO dg : listdocGia) {
            if (dg.getIsDeleted() != null && dg.getIsDeleted()) {
                continue;
            }
            if (dg.getTenDangNhap() != null && dg.getMatKhau() != null &&
                    dg.getTenDangNhap().equals(tenDangNhap) && dg.getMatKhau().equals(matKhau)) {
                return dg;
            }
        }
        return null;
    }

    public Object[] getThongTinCaNhan(String maDocGia) {
        return docGiaDAO.getThongTinCaNhan(maDocGia);
    }

    public String updateThongTinLienHe(String maDocGia, String sdt, String email) {
        if (sdt.trim().isEmpty() || email.trim().isEmpty()) {
            return "Số điện thoại và Email không được để trống!";
        }
        if (docGiaDAO.updateThongTinLienHe(maDocGia, sdt, email)) {
            for (DocGiaDTO dg : listdocGia) {
                if (dg.getMaDocGia().equals(maDocGia)) {
                    dg.setSoDienThoai(sdt);
                    dg.setEmail(email);
                    break;
                }
            }
            return "Cập nhật thông tin thành công!";
        }
        return "Cập nhật thất bại. Vui lòng thử lại!";
    }

    public ArrayList<DocGiaDTO> search(String keyword, String criteria) {
        ArrayList<DocGiaDTO> result = new ArrayList<>();
        if (listdocGia == null || listdocGia.isEmpty()) {
            listdocGia = docGiaDAO.getAll();
        }

        String lowerKeyword = keyword.toLowerCase().trim();

        for (DocGiaDTO dg : listdocGia) {
            boolean match = false;
            String ma = dg.getMaDocGia() != null ? dg.getMaDocGia().toLowerCase() : "";
            String ten = dg.getHoTen() != null ? dg.getHoTen().toLowerCase() : "";
            String sdt = dg.getSoDienThoai() != null ? dg.getSoDienThoai().toLowerCase() : "";

            String trangThai;
            if (dg.getIsDeleted() != null && dg.getIsDeleted()) {
                trangThai = "đã khóa";
            } else {
                trangThai = dg.getTrangThai() != null ? dg.getTrangThai().toLowerCase() : "";
            }

            switch (criteria) {
                case "Tất cả":
                    if (ma.contains(lowerKeyword) || ten.contains(lowerKeyword) ||
                            sdt.contains(lowerKeyword) || trangThai.contains(lowerKeyword)) {
                        match = true;
                    }
                    break;
                case "Mã ĐG":
                    match = ma.contains(lowerKeyword);
                    break;
                case "Họ Tên":
                    match = ten.contains(lowerKeyword);
                    break;
                case "Số Điện Thoại":
                    match = sdt.contains(lowerKeyword);
                    break;
                case "Trạng Thái":
                    match = trangThai.contains(lowerKeyword);
                    break;
            }

            if (match) {
                result.add(dg);
            }
        }
        return result;
    }
}