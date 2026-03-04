/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.ThongKeDAO;
import java.util.Map;

/**
 *
 * @author uly28
 */
public class ThongKeBUS {

    private final ThongKeDAO thongKeDAO = new ThongKeDAO();

    // 1. Thống kê số lượng sách theo thể loại
    public Map<String, Integer> thongKeSachTheoTheLoai() {
        return thongKeDAO.thongKeSachTheoTheLoai();
    }

    // 2. Thống kê top 5 độc giả mượn nhiều sách nhất
    public Map<String, Integer> thongKeTop5DocGiaMuonNhieu() {
        return thongKeDAO.thongKeTop5DocGiaMuonNhieu();
    }

    // 3. Thống kê sách được mượn nhiều nhất
    public Map<String, Integer> thongKeSachMuonNhieuNhat() {
        return thongKeDAO.thongKeSachMuonNhieuNhat();
    }
}
