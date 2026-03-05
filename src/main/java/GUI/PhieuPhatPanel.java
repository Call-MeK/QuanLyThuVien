package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.table.TableRowSorter;

public class PhieuPhatPanel extends JPanel {

    private String tenFont = "Segoe UI";
    private JTable table;
    private JButton btnXemChiTiet;
    private JButton btnThanhToan;
    private BUS.PhieuPhatBUS phieuPhatBUS = new BUS.PhieuPhatBUS();

    public PhieuPhatPanel() {
        initComponents();
        loadDataToTable();
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

        JLabel lblTitle = new JLabel("Quản Lý Phiếu Phạt & Thanh Toán");
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 22));
        lblTitle.setForeground(new Color(33, 37, 41));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSubtitle = new JLabel("Theo dõi các khoản phạt vi phạm và trạng thái");
        lblSubtitle.setFont(new Font(tenFont, Font.ITALIC, 14));
        lblSubtitle.setForeground(new Color(108, 117, 125));
        lblSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Bộ lọc trạng thái
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
        cbStatus.addActionListener(e -> {
            String selected = cbStatus.getSelectedItem().toString();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            table.setRowSorter(sorter);

            if (selected.equals("Tất cả")) {
                sorter.setRowFilter(null);
            } else {
                // ĐÃ SỬA: Cột Trạng Thái giờ là index 5 (vì thêm cột Ngày Lập)
                sorter.setRowFilter(RowFilter.regexFilter("^" + selected + "$", 5));
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
        // PHẦN GIỮA: BẢNG - ĐÃ SỬA: 6 cột khớp với DAO
        // ==========================================
        String[] cols = {"Mã Phạt", "Mã Phiếu Mượn", "Ngày Lập", "Lý Do Phạt", "Số Tiền (VNĐ)", "Trạng Thái"};
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, cols) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(model);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font(tenFont, Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(241, 245, 249));
        table.setFont(new Font(tenFont, Font.PLAIN, 14));

        // Tô màu theo trạng thái
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    // Lấy trạng thái từ model (không phải view) để tránh lỗi khi sort
                    int modelRow = table.convertRowIndexToModel(row);
                    String tt = table.getModel().getValueAt(modelRow, 5) != null
                            ? table.getModel().getValueAt(modelRow, 5).toString() : "";
                    if (tt.equals("Đã thanh toán")) {
                        c.setBackground(new Color(232, 245, 233));
                        c.setForeground(new Color(27, 94, 32));
                    } else if (tt.equals("Chưa thanh toán")) {
                        c.setBackground(new Color(255, 243, 224));
                        c.setForeground(new Color(230, 81, 0));
                    } else {
                        c.setBackground(Color.WHITE);
                        c.setForeground(Color.BLACK);
                    }
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));

        // ==========================================
        // PHẦN DƯỚI: CÁC NÚT CHỨC NĂNG
        // ==========================================
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        bottomPanel.setBackground(Color.WHITE);

        btnXemChiTiet = new JButton("Xem Chi Tiết");
        btnXemChiTiet.setBackground(new Color(100, 116, 139));
        btnXemChiTiet.setForeground(Color.WHITE);
        btnXemChiTiet.setFont(new Font(tenFont, Font.BOLD, 13));
        btnXemChiTiet.setFocusPainted(false);
        btnXemChiTiet.setOpaque(true);
        btnXemChiTiet.setBorderPainted(false);

        btnXemChiTiet.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu phạt trên bảng để xem!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                int modelRow = table.convertRowIndexToModel(row);
                String maPhat = table.getModel().getValueAt(modelRow, 0).toString();
                String ngayLap = table.getModel().getValueAt(modelRow, 2).toString();
                String trangThai = table.getModel().getValueAt(modelRow, 5).toString();

                // Lấy chi tiết CÓ TÊN SÁCH từ Database (cần import BUS.ChiTietPhieuPhatBUS ở đầu file nếu chưa có)
                BUS.ChiTietPhieuPhatBUS chiTietBUS = new BUS.ChiTietPhieuPhatBUS();
                ArrayList<Object[]> dsChiTiet = chiTietBUS.getChiTietCoTenSachByMaPP(maPhat);

                // Dựng chuỗi hiển thị chi tiết
                StringBuilder sb = new StringBuilder();
                sb.append("--- THÔNG TIN CHUNG ---\n");
                sb.append("Mã Phiếu Phạt: ").append(maPhat).append("\n");
                sb.append("Ngày lập: ").append(ngayLap).append("\n");
                sb.append("Trạng thái: ").append(trangThai).append("\n\n");
                
                sb.append("--- DANH SÁCH CUỐN SÁCH BỊ PHẠT ---\n");
                if (dsChiTiet.isEmpty()) {
                    sb.append("Chưa có dữ liệu chi tiết cuốn sách nào.\n");
                } else {
                    double tongTien = 0;
                    for (int i = 0; i < dsChiTiet.size(); i++) {
                        Object[] ct = dsChiTiet.get(i);
                        // ct[0]: Mã sách, ct[1]: Tên sách, ct[2]: Lý do, ct[3]: Số tiền (dạng chuỗi có dấu phẩy)
                        sb.append(i + 1).append(". Sách: ").append(ct[1]).append(" (Mã vạch: ").append(ct[0]).append(")\n");
                        sb.append("   - Lỗi vi phạm: ").append(ct[2]).append("\n");
                        sb.append("   - Phạt: ").append(ct[3]).append(" VNĐ\n\n");
                        
                        // Tính tổng tiền (phải xóa dấu phẩy mới parse sang số được)
                        String tienChuoi = ct[3].toString().replace(",", "");
                        try {
                            tongTien += Double.parseDouble(tienChuoi);
                        } catch(Exception ex) {}
                    }
                    sb.append("----------------------------\n");
                    sb.append("TỔNG TIỀN PHẠT: ").append(String.format("%,.0f", tongTien)).append(" VNĐ");
                }

                // Hiển thị lên màn hình
                JTextArea textArea = new JTextArea(sb.toString());
                textArea.setFont(new Font(tenFont, Font.PLAIN, 14));
                textArea.setEditable(false);
                textArea.setBackground(new Color(248, 249, 250));
                textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                
                JScrollPane scrollMsg = new JScrollPane(textArea);
                scrollMsg.setPreferredSize(new Dimension(450, 300));

                JOptionPane.showMessageDialog(this, scrollMsg,
                        "Chi Tiết Phiếu Phạt", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnThanhToan = new JButton("Thanh Toán Ngay");
        btnThanhToan.setBackground(new Color(239, 68, 68));
        btnThanhToan.setForeground(Color.WHITE);
        btnThanhToan.setFont(new Font(tenFont, Font.BOLD, 13));
        btnThanhToan.setFocusPainted(false);
        btnThanhToan.setOpaque(true);
        btnThanhToan.setBorderPainted(false);

        btnThanhToan.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu phạt cần thanh toán!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                int modelRow = table.convertRowIndexToModel(row);
                String status = table.getModel().getValueAt(modelRow, 5).toString();
                if (status.equalsIgnoreCase("Đã thanh toán")) {
                    JOptionPane.showMessageDialog(this, "Phiếu phạt này đã được thanh toán rồi!",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Vui lòng đến quầy thủ thư của thư viện để nộp tiền phạt trực tiếp!",
                            "Hướng dẫn thanh toán",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        bottomPanel.add(btnXemChiTiet);
        bottomPanel.add(btnThanhToan);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // --- GETTER ---
    public JTable getTable() { return table; }
    public JButton getBtnXemChiTiet() { return btnXemChiTiet; }
    public JButton getBtnThanhToan() { return btnThanhToan; }

    // --- LOAD DATA ---
    public void loadDataToTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        // Lấy mã Độc giả đang đăng nhập từ Session
        String maDocGiaDangNhap = BUS.SessionManager.getInstance().getMaNguoi();
        
        ArrayList<Object[]> dsHienThi;
        
        // Kiểm tra xem có người đăng nhập không
        if (maDocGiaDangNhap != null && !maDocGiaDangNhap.isEmpty()) {
            // Chỉ lấy phiếu phạt của người này
            dsHienThi = phieuPhatBUS.getDanhSachHienThiGUIByMaDocGia(maDocGiaDangNhap); 
        } else {
            // Nếu lỗi session (không có ai đăng nhập), cho mảng rỗng để không hiện gì cả
            dsHienThi = new ArrayList<>();
        }

        for (Object[] row : dsHienThi) {
            model.addRow(row);
        }
    }
}