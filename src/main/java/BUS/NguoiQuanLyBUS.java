package BUS;

import DAO.NguoiQuanLyDAO;
import DTO.NguoiQuanLyDTO;
import java.util.ArrayList;

public class NguoiQuanLyBUS {
    private NguoiQuanLyDAO nqlDAO;
    private ArrayList<NguoiQuanLyDTO> listNQL;

    public NguoiQuanLyBUS() {
        nqlDAO = new NguoiQuanLyDAO();
        listNQL = new ArrayList<>();
    }

    public ArrayList<NguoiQuanLyDTO> getAll() {
        listNQL = nqlDAO.getAll();
        return listNQL;
    }

    public ArrayList<NguoiQuanLyDTO> getAllActive() {
        if (listNQL == null || listNQL.isEmpty()) {
            getAll();
        }
        ArrayList<NguoiQuanLyDTO> activeList = new ArrayList<>();
        for (NguoiQuanLyDTO nql : listNQL) {
            if (nql.getIsDeleted() != null && !nql.getIsDeleted()) {
                activeList.add(nql);
            }
        }
        return activeList;
    }

    public NguoiQuanLyDTO getById(String MaNQL) {
        if (listNQL != null && !listNQL.isEmpty()) {
            for (NguoiQuanLyDTO nql : listNQL) {
                if (nql.getMaNQL().equals(MaNQL)) {
                    return nql;
                }
            }
        }
        return nqlDAO.getById(MaNQL);
    }

    public String add(NguoiQuanLyDTO nql) {
        if (nql.getMaNQL().trim().isEmpty() || nql.getHoTen().trim().isEmpty()) {
            return "Mã người quản lý và Họ tên không được để trống!";
        }
        if (nql.getTenDangNhap().trim().isEmpty() || nql.getMatKhau().trim().isEmpty()) {
            return "Tên đăng nhập và Mật khẩu không được để trống!";
        }

        if (getById(nql.getMaNQL()) != null) {
            return "Mã Người Quản Lý (hoặc Mã Người) đã tồn tại!";
        }

        nql.setIsDeleted(false);

        if (nqlDAO.add(nql)) {
            if (listNQL != null) {
                listNQL.add(nql);
            }
            return "Thêm người quản lý thành công!";
        }
        return "Thêm thất bại do lỗi CSDL!";
    }

    public String update(NguoiQuanLyDTO nql) {
        if (nql.getHoTen().trim().isEmpty()) {
            return "Họ tên không được để trống!";
        }

        if (getById(nql.getMaNQL()) == null) {
            return "Không tìm thấy Người quản lý cần cập nhật!";
        }

        if (nqlDAO.update(nql)) {
            if (listNQL != null) {
                for (int i = 0; i < listNQL.size(); i++) {
                    if (listNQL.get(i).getMaNQL().equals(nql.getMaNQL())) {
                        listNQL.set(i, nql);
                        break;
                    }
                }
            }
            return "Cập nhật thành công!";
        }
        return "Cập nhật thất bại!";
    }

    public String softDelete(String MaNQL) {
        NguoiQuanLyDTO nql = getById(MaNQL);

        if (nql == null) {
            return "Mã Người Quản Lý không tồn tại!";
        }

        if (nql.getIsDeleted() != null && nql.getIsDeleted()) {
            return "Người quản lý này đã bị xóa trước đó!";
        }

        if (nqlDAO.softdelete(MaNQL)) {
            if (listNQL != null) {
                for (NguoiQuanLyDTO item : listNQL) {
                    if (item.getMaNQL().equals(MaNQL)) {
                        item.setIsDeleted(true);
                        break;
                    }
                }
            }
            return "Xóa thành công (Xóa mềm)!";
        }
        return "Xóa thất bại!";
    }

    public ArrayList<NguoiQuanLyDTO> search(String keyword) {
        ArrayList<NguoiQuanLyDTO> result = new ArrayList<>();
        if (listNQL == null || listNQL.isEmpty()) {
            getAll();
        }

        String lowerKeyword = keyword.toLowerCase();
        for (NguoiQuanLyDTO nql : listNQL) {
            if (nql.getIsDeleted() != null && nql.getIsDeleted()) {
                continue;
            }

            if (nql.getHoTen().toLowerCase().contains(lowerKeyword) ||
                    nql.getTenDangNhap().toLowerCase().contains(lowerKeyword) ||
                    nql.getSoDienThoai().contains(keyword)) {
                result.add(nql);
            }
        }
        return result;
    }
}