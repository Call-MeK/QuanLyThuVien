package BUS;

import DAO.NguoiQuanLyDAO;
import DTO.NguoiQuanLyDTO;
import java.util.ArrayList;

public class NguoiQuanLyBUS {
    private NguoiQuanLyDAO nqlDAO;
    private ArrayList<NguoiQuanLyDTO> listNQL;

    public NguoiQuanLyBUS() {
        nqlDAO = new NguoiQuanLyDAO();
        listNQL = nqlDAO.getAll();
    }

    public ArrayList<NguoiQuanLyDTO> getAll() {
        return nqlDAO.getAll();
    }

    public NguoiQuanLyDTO login(String username, String password) {
        // Giả sử DAO có hàm kiểm tra đăng nhập hoặc lọc từ listNQL
        for (NguoiQuanLyDTO nql : listNQL) {
            if (nql.getTenDangNhap().equals(username) && nql.getMatKhau().equals(password)) {
                return nql;
            }
        }
        return null;
    }
}