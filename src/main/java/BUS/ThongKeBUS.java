package BUS;

import DAO.ThongKeDAO;
import java.util.Map;

public class ThongKeBUS {

    private final ThongKeDAO thongKeDAO = new ThongKeDAO();

    public Map<String, Integer> thongKeSachTheoTheLoai() {
        return thongKeDAO.thongKeSachTheoTheLoai();
    }

    public Map<String, Integer> thongKeTop5DocGiaMuonNhieu() {
        return thongKeDAO.thongKeTop5DocGiaMuonNhieu();
    }

    public Map<String, Integer> thongKeSachMuonNhieuNhat() {
        return thongKeDAO.thongKeSachMuonNhieuNhat();
    }
}
