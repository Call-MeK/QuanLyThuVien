package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PhieuPhatPanel extends JPanel {

    private String tenFont = "Segoe UI";
    private JTable table;
    
    // Khai báo các nút chức năng
    private JButton btnXemChiTiet;
    private JButton btnThanhToan;

    public PhieuPhatPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 20));
        setBackground(Color.WHITE); 
        setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // ==========================================
        // PHẦN TRÊN: TIÊU ĐỀ & BỘ LỌC
        // ==========================================
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(Color.WHITE);

        // 1. Tiêu đề
        JLabel lblTitle = new JLabel("Quản Lý Phiếu Phạt & Thanh Toán");
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 22));
        lblTitle.setForeground(new Color(33, 37, 41));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 2. Phụ đề
        JLabel lblSubtitle = new JLabel("Theo dõi các khoản phạt vi phạm và thực hiện thanh toán trực tuyến");
        lblSubtitle.setFont(new Font(tenFont, Font.ITALIC, 14));
        lblSubtitle.setForeground(new Color(108, 117, 125)); 
        lblSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 3. Bộ lọc trạng thái (Combo Box)
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblFilter = new JLabel("Trạng thái thanh toán:");
        lblFilter.setFont(new Font(tenFont, Font.PLAIN, 14));
        
        JComboBox<String> cbStatus = new JComboBox<>(new String[]{
            "Tất cả", "Chưa thanh toán", "Đã thanh toán"
        });
        cbStatus.setPreferredSize(new Dimension(160, 30));
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
        // PHẦN GIỮA: BẢNG DANH SÁCH PHIẾU PHẠT
        // ==========================================
        String[] cols = {"Mã Phạt", "Mã Phiếu Mượn", "Lý Do Phạt", "Số Tiền (VNĐ)", "Trạng Thái"};
        Object[][] data = {
            {"PP001", "PM012", "Trễ hạn 5 ngày", "25,000", "Chưa thanh toán"},
            {"PP002", "PM008", "Làm rách trang sách", "50,000", "Chưa thanh toán"},
            {"PP003", "PM002", "Trễ hạn 2 ngày", "10,000", "Đã thanh toán"}
        };
        
        // SỬ DỤNG DEFAULT TABLE MODEL ĐỂ KHÓA CHỈNH SỬA (READ-ONLY)
        DefaultTableModel model = new DefaultTableModel(data, cols) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Trả về false sẽ khóa toàn bộ các ô, không cho gõ vào bảng
            }
        };
        
        table = new JTable(model);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font(tenFont, Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(241, 245, 249)); 
        table.setFont(new Font(tenFont, Font.PLAIN, 14));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));

        // ==========================================
        // PHẦN DƯỚI: CÁC NÚT CHỨC NĂNG
        // ==========================================
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        bottomPanel.setBackground(Color.WHITE);
        
        // 1. NÚT XEM CHI TIẾT
        btnXemChiTiet = new JButton("Xem Chi Tiết");
        btnXemChiTiet.setBackground(new Color(100, 116, 139)); // Xám
        btnXemChiTiet.setForeground(Color.WHITE);
        btnXemChiTiet.setFont(new Font(tenFont, Font.BOLD, 13));
        btnXemChiTiet.setFocusPainted(false);
        
        // Xử lý sự kiện Xem chi tiết
        btnXemChiTiet.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(PhieuPhatPanel.this, "Vui lòng chọn một phiếu phạt trên bảng để xem!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                String maPhat = table.getValueAt(row, 0).toString();
                String lyDo = table.getValueAt(row, 2).toString();
                String soTien = table.getValueAt(row, 3).toString();
                JOptionPane.showMessageDialog(PhieuPhatPanel.this, 
                        "Mã Phiếu Phạt: " + maPhat + "\nLý do vi phạm: " + lyDo + "\nSố tiền phạt: " + soTien + " VNĐ",
                        "Chi Tiết Phiếu Phạt", 
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 2. NÚT THANH TOÁN
        btnThanhToan = new JButton("Thanh Toán Ngay");
        btnThanhToan.setBackground(new Color(239, 68, 68)); // Màu đỏ (gây chú ý)
        btnThanhToan.setForeground(Color.WHITE);
        btnThanhToan.setFont(new Font(tenFont, Font.BOLD, 13));
        btnThanhToan.setFocusPainted(false);
        
        // Xử lý sự kiện Thanh toán
        btnThanhToan.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(PhieuPhatPanel.this, "Vui lòng chọn phiếu phạt cần thanh toán!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                String status = table.getValueAt(row, 4).toString();
                // Kiểm tra xem đã thanh toán chưa
                if (status.equalsIgnoreCase("Đã thanh toán")) {
                    JOptionPane.showMessageDialog(PhieuPhatPanel.this, "Phiếu phạt này đã được thanh toán rồi!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(PhieuPhatPanel.this, 
                        "Vui lòng đến quầy thủ thư của thư viện để nộp tiền phạt trực tiếp!",
                        "Hướng dẫn thanh toán", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        bottomPanel.add(btnXemChiTiet);
        bottomPanel.add(btnThanhToan);

        // Lắp ráp vào Panel chính
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // --- CÁC HÀM GETTER ĐỂ BÊN NGOÀI BẮT SỰ KIỆN (NẾU CẦN SAU NÀY) ---
    public JTable getTable() { return table; }
    public JButton getBtnXemChiTiet() { return btnXemChiTiet; }
    public JButton getBtnThanhToan() { return btnThanhToan; }
}