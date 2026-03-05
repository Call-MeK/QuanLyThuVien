package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;

public class SachDangMuonPanel extends JPanel {

    private String tenFont = "Segoe UI";
    private JTable table;
    
    // Khai báo BUS
    private BUS.PhieuMuonBUS phieuMuonBUS = new BUS.PhieuMuonBUS();
    
    // Khai báo các nút chức năng (Đã bỏ nút Gia Hạn)
    private JButton btnXemChiTiet;
    private JButton btnTraSach;

    public SachDangMuonPanel() {
        initComponents();
        loadDataToTable(); // Đổ dữ liệu ngay khi mở panel
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

        JLabel lblTitle = new JLabel("Sách Đang Mượn Của Bạn");
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 22));
        lblTitle.setForeground(new Color(33, 37, 41));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSubtitle = new JLabel("Theo dõi hạn trả và quản lý các cuốn sách bạn đang mượn từ thư viện");
        lblSubtitle.setFont(new Font(tenFont, Font.ITALIC, 14));
        lblSubtitle.setForeground(new Color(108, 117, 125)); 
        lblSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblFilter = new JLabel("Trạng thái:");
        lblFilter.setFont(new Font(tenFont, Font.PLAIN, 14));
        
        // Đã sửa lại các item cho khớp với dữ liệu thật trong Database
        JComboBox<String> cbStatus = new JComboBox<>(new String[]{
            "Tất cả", "Đang mượn", "Đã trả", "Đã trả quá hạn"
        });
        cbStatus.setPreferredSize(new Dimension(150, 30));
        cbStatus.setFont(new Font(tenFont, Font.PLAIN, 13));
        cbStatus.setBackground(Color.WHITE);

        // Sự kiện lọc dữ liệu bằng TableRowSorter
        cbStatus.addActionListener(e -> {
            String selected = cbStatus.getSelectedItem().toString();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            table.setRowSorter(sorter);

            if (selected.equals("Tất cả")) {
                sorter.setRowFilter(null); 
            } else {
                // Lọc ở cột số 4 (Cột Trạng Thái)
                sorter.setRowFilter(RowFilter.regexFilter("^" + selected + "$", 4));
            }
        });

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
        Object[][] data = {};
            
        DefaultTableModel model = new DefaultTableModel(data, cols) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
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
        
        // --- Làm đẹp nút XEM CHI TIẾT ---
        btnXemChiTiet = new JButton("Xem Chi Tiết");
        btnXemChiTiet.setFont(new Font(tenFont, Font.BOLD, 13));
        btnXemChiTiet.setBackground(new Color(99, 102, 241)); // Màu Xanh Indigo
        btnXemChiTiet.setForeground(Color.WHITE);
        btnXemChiTiet.setFocusPainted(false);
        btnXemChiTiet.setBorderPainted(false);
        btnXemChiTiet.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnXemChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnXemChiTiet.setBackground(new Color(79, 70, 229));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnXemChiTiet.setBackground(new Color(99, 102, 241));
            }
        });
        
        btnXemChiTiet.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(SachDangMuonPanel.this, "Vui lòng chọn một cuốn sách trên bảng để xem chi tiết!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                String maPhieu = table.getValueAt(row, 0).toString();
                String tenSach = table.getValueAt(row, 1).toString();
                String hanTra = table.getValueAt(row, 3).toString();
                String trangThai = table.getValueAt(row, 4).toString();
                JOptionPane.showMessageDialog(SachDangMuonPanel.this, 
                        "Mã Phiếu Mượn: " + maPhieu + "\nTên Sách: " + tenSach + "\nHạn Trả: " + hanTra + "\nTrạng Thái: " + trangThai,
                        "Chi Tiết Sách Mượn", 
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // --- Làm đẹp nút TRẢ SÁCH ---
        btnTraSach = new JButton("Trả Sách");
        btnTraSach.setFont(new Font(tenFont, Font.BOLD, 13));
        btnTraSach.setBackground(new Color(16, 185, 129)); // Màu Xanh Ngọc (Emerald)
        btnTraSach.setForeground(Color.WHITE);
        btnTraSach.setFocusPainted(false);
        btnTraSach.setBorderPainted(false);
        btnTraSach.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnTraSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTraSach.setBackground(new Color(5, 150, 105)); // Đậm hơn
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTraSach.setBackground(new Color(16, 185, 129));
            }
        });
        
        btnTraSach.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(SachDangMuonPanel.this, "Vui lòng chọn cuốn sách bạn muốn trả!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(SachDangMuonPanel.this, 
                        "Vui lòng mang sách đến quầy thủ thư để tiến hành kiểm tra tình trạng sách và làm thủ tục trả!", 
                        "Hướng dẫn trả sách", 
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        bottomPanel.add(btnXemChiTiet);
        bottomPanel.add(btnTraSach);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    // =======================================================
    // HÀM KẾT NỐI DATA VÀ ĐỔ LÊN BẢNG
    // =======================================================
    public void loadDataToTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); 

        ArrayList<Object[]> dsHienThi = phieuMuonBUS.getDanhSachSachDangMuon();
        for (Object[] row : dsHienThi) {
            model.addRow(row);
        }
    }

    public JTable getTable() { return table; }
    public JButton getBtnXemChiTiet() { return btnXemChiTiet; }
    public JButton getBtnTraSach() { return btnTraSach; }
}