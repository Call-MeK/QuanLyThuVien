package GUI;

import javax.swing.*;
import java.awt.*;

public class SachDangMuonPanel extends JPanel {

    private String tenFont = "Segoe UI";
    private JTable table;
    
    // Khai báo 3 nút chức năng
    private JButton btnXemChiTiet;
    private JButton btnGiaHan;
    private JButton btnTraSach;

    public SachDangMuonPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 20));
        setBackground(Color.WHITE); // Nền trắng chuẩn thiết kế phẳng
        setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // ==========================================
        // PHẦN TRÊN: TIÊU ĐỀ & BỘ LỌC
        // ==========================================
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(Color.WHITE);

        // 1. Tiêu đề
        JLabel lblTitle = new JLabel("Sách Đang Mượn Của Bạn");
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 22));
        lblTitle.setForeground(new Color(33, 37, 41));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 2. Phụ đề
        JLabel lblSubtitle = new JLabel("Theo dõi hạn trả và quản lý các cuốn sách bạn đang mượn từ thư viện");
        lblSubtitle.setFont(new Font(tenFont, Font.ITALIC, 14));
        lblSubtitle.setForeground(new Color(108, 117, 125)); 
        lblSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 3. Bộ lọc trạng thái (Combo Box)
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblFilter = new JLabel("Trạng thái:");
        lblFilter.setFont(new Font(tenFont, Font.PLAIN, 14));
        
        JComboBox<String> cbStatus = new JComboBox<>(new String[]{
            "Tất cả", "Còn hạn", "Sắp tới hạn", "Quá hạn"
        });
        cbStatus.setPreferredSize(new Dimension(150, 30));
        cbStatus.setFont(new Font(tenFont, Font.PLAIN, 13));
        cbStatus.setBackground(Color.WHITE);

        filterPanel.add(lblFilter);
        filterPanel.add(cbStatus);

        topPanel.add(lblTitle);
        topPanel.add(Box.createVerticalStrut(5));
        topPanel.add(lblSubtitle);
        topPanel.add(Box.createVerticalStrut(20)); 
        topPanel.add(filterPanel);

        // ==========================================
        // PHẦN GIỮA: BẢNG DANH SÁCH MƯỢN
        // ==========================================
        String[] cols = {"Mã Phiếu", "Tên Sách", "Ngày Mượn", "Hạn Trả", "Trạng Thái"};
        Object[][] data = {
            {"PM001", "Dế Mèn Phiêu Lưu Ký", "10/03/2026", "24/03/2026", "Còn hạn"},
            {"PM002", "Clean Code", "01/03/2026", "15/03/2026", "Quá hạn"},
            {"PM003", "Nhà Giả Kim", "18/03/2026", "02/04/2026", "Còn hạn"}
        };
        
        table = new JTable(data, cols);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font(tenFont, Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(241, 245, 249)); // Màu xám nhạt cho Header
        table.setFont(new Font(tenFont, Font.PLAIN, 14));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));

        // ==========================================
        // PHẦN DƯỚI: CÁC NÚT CHỨC NĂNG
        // ==========================================
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        bottomPanel.setBackground(Color.WHITE);
        
        // Nút Xem Chi Tiết (Màu xanh lam nhạt / xám)
        btnXemChiTiet = new JButton("Xem Chi Tiết");
        btnXemChiTiet.setBackground(new Color(100, 116, 139)); // Xám xanh dương
        btnXemChiTiet.setForeground(Color.WHITE);
        btnXemChiTiet.setFont(new Font(tenFont, Font.BOLD, 13));
        btnXemChiTiet.setFocusPainted(false);

        // Nút Gia Hạn (Màu Cam - Cảnh báo/Chờ đợi)
        btnGiaHan = new JButton("Gia Hạn Sách");
        btnGiaHan.setBackground(new Color(245, 158, 11)); // Màu cam
        btnGiaHan.setForeground(Color.WHITE);
        btnGiaHan.setFont(new Font(tenFont, Font.BOLD, 13));
        btnGiaHan.setFocusPainted(false);

        // Nút Trả Sách (Màu Xanh lá - Hoàn thành)
        btnTraSach = new JButton("Trả Sách");
        btnTraSach.setBackground(new Color(34, 197, 94)); // Màu xanh lá
        btnTraSach.setForeground(Color.WHITE);
        btnTraSach.setFont(new Font(tenFont, Font.BOLD, 13));
        btnTraSach.setFocusPainted(false);

        bottomPanel.add(btnXemChiTiet);
        bottomPanel.add(btnGiaHan);
        bottomPanel.add(btnTraSach);

        // Lắp ráp vào Panel chính
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // --- CÁC HÀM GETTER ĐỂ BÊN NGOÀI BẮT SỰ KIỆN ---
    public JTable getTable() { return table; }
    public JButton getBtnXemChiTiet() { return btnXemChiTiet; }
    public JButton getBtnGiaHan() { return btnGiaHan; }
    public JButton getBtnTraSach() { return btnTraSach; }
}