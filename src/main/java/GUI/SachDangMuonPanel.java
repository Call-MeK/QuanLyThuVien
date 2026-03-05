package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import BUS.*;
import DTO.*;

public class SachDangMuonPanel extends JPanel {

    private String tenFont = "Segoe UI";
    private JTable table;
    private DefaultTableModel model;

    private PhieuMuonBUS phieuMuonBUS   = new PhieuMuonBUS();
    private TheThuVienBUS theThuVienBUS = new TheThuVienBUS();

    private JButton btnXemChiTiet;
    private JButton btnTraSach;

    // MaThe của user đang đăng nhập
    private String maTheCurrent = null;

    public SachDangMuonPanel() {
        initComponents();
        timMaThe();
        loadDataToTable();
    }

    /** Lấy MaDocGia từ session -> tìm MaThe tương ứng */
    private void timMaThe() {
        String maDG = SessionManager.getInstance().getMaNguoi();
        if (maDG == null || maDG.isEmpty()) return;

        TheThuVienDTO the = theThuVienBUS.getByMaDocGia(maDG);
        if (the != null) maTheCurrent = the.getMaThe();
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

        JComboBox<String> cbStatus = new JComboBox<>(new String[]{
            "Tất cả", "Đang mượn", "Trễ hạn", "Đã trả"
        });
        cbStatus.setPreferredSize(new Dimension(150, 30));
        cbStatus.setFont(new Font(tenFont, Font.PLAIN, 13));
        cbStatus.setBackground(Color.WHITE);

        // Lọc theo cột 5 (Trạng Thái)
        cbStatus.addActionListener(e -> {
            String selected = cbStatus.getSelectedItem().toString();
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            table.setRowSorter(sorter);
            if ("Tất cả".equals(selected)) {
                sorter.setRowFilter(null);
            } else {
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
        // PHẦN GIỮA: BẢNG
        // Cột 0: Mã Phiếu (ẩn) | 1: Tên Sách | 2: Ngày Mượn | 3: Hạn Trả | 4: Ngày Trả | 5: Trạng Thái
        // ==========================================
        String[] cols = {"Mã Phiếu", "Tên Sách", "Ngày Mượn", "Hạn Trả", "Ngày Trả", "Trạng Thái"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };

        table = new JTable(model);
        table.setRowHeight(32);
        table.getTableHeader().setFont(new Font(tenFont, Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(241, 245, 249));
        table.setFont(new Font(tenFont, Font.PLAIN, 14));
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setShowVerticalLines(false);

        // Màu sắc theo trạng thái
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                if (!isSelected) {
                    int modelRow = table.convertRowIndexToModel(row);
                    String tt = model.getValueAt(modelRow, 5) != null
                            ? model.getValueAt(modelRow, 5).toString() : "";
                    switch (tt) {
                        case "Trễ hạn":
                            c.setBackground(new Color(255, 235, 238));
                            c.setForeground(new Color(183, 28, 28));
                            break;
                        case "Đã trả":
                            c.setBackground(new Color(232, 245, 233));
                            c.setForeground(new Color(27, 94, 32));
                            break;
                        default:
                            c.setBackground(Color.WHITE);
                            c.setForeground(new Color(33, 37, 41));
                            break;
                    }
                }
                return c;
            }
        });

        // Ẩn cột Mã Phiếu (vẫn giữ trong model để dùng trong sự kiện)
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));

        // ==========================================
        // PHẦN DƯỚI: NÚT CHỨC NĂNG
        // ==========================================
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        bottomPanel.setBackground(Color.WHITE);

        btnXemChiTiet = taoNut("Xem Chi Tiết", new Color(99, 102, 241), new Color(79, 70, 229));
        btnTraSach    = taoNut("Hướng Dẫn Trả Sách", new Color(16, 185, 129), new Color(5, 150, 105));

        btnXemChiTiet.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn một dòng trong bảng để xem chi tiết!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int mr = table.convertRowIndexToModel(row);
            String maPhieu   = model.getValueAt(mr, 0).toString();
            String tenSach   = model.getValueAt(mr, 1).toString();
            String ngayMuon  = model.getValueAt(mr, 2).toString();
            String hanTra    = model.getValueAt(mr, 3).toString();
            String ngayTra   = model.getValueAt(mr, 4).toString();
            String trangThai = model.getValueAt(mr, 5).toString();

            String icon = "Trễ hạn".equals(trangThai) ? "⚠️ " : "📖 ";
            JOptionPane.showMessageDialog(this,
                    icon + "Chi Tiết Phiếu Mượn\n\n"
                    + "Mã Phiếu:   " + maPhieu  + "\n"
                    + "Tên Sách:   " + tenSach  + "\n"
                    + "Ngày Mượn:  " + ngayMuon + "\n"
                    + "Hạn Trả:    " + hanTra   + "\n"
                    + "Ngày Trả:   " + ngayTra  + "\n"
                    + "Trạng Thái: " + trangThai,
                    "Chi Tiết Sách Mượn",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        btnTraSach.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn cuốn sách bạn muốn trả!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int mr = table.convertRowIndexToModel(row);
            String trangThai = model.getValueAt(mr, 5).toString();

            if ("Đã trả".equals(trangThai)) {
                JOptionPane.showMessageDialog(this,
                        "Cuốn sách này đã được trả rồi!",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this,
                    "📌 Hướng dẫn trả sách:\n\n"
                    + "Vui lòng mang sách đến quầy thủ thư để:\n"
                    + "  • Kiểm tra tình trạng sách\n"
                    + "  • Làm thủ tục trả và cập nhật hệ thống\n\n"
                    + ("Trễ hạn".equals(trangThai)
                        ? "⚠️ Sách đã quá hạn, có thể phát sinh phí phạt!\n"
                          + "Vui lòng đến thư viện sớm nhất có thể."
                        : "Cảm ơn bạn đã sử dụng dịch vụ thư viện!"),
                    "Hướng Dẫn Trả Sách",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        bottomPanel.add(btnXemChiTiet);
        bottomPanel.add(btnTraSach);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // =======================================================
    // LOAD DỮ LIỆU — chỉ lấy phiếu của user đang đăng nhập
    // =======================================================
    public void loadDataToTable() {
        model.setRowCount(0);

        if (maTheCurrent == null || maTheCurrent.isEmpty()) {
            model.addRow(new Object[]{"", "Không tìm thấy thẻ thư viện của bạn. Vui lòng liên hệ thủ thư.", "", "", "", ""});
            return;
        }

        try {
            ArrayList<Object[]> ds = phieuMuonBUS.getDanhSachSachDangMuonByMaThe(maTheCurrent);
            if (ds == null || ds.isEmpty()) {
                model.addRow(new Object[]{"", "Bạn chưa mượn cuốn sách nào 📚", "", "", "", ""});
            } else {
                for (Object[] row : ds) {
                    model.addRow(row);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private JButton taoNut(String text, Color bgNormal, Color bgHover) {
        JButton btn = new JButton(text);
        btn.setFont(new Font(tenFont, Font.BOLD, 13));
        btn.setBackground(bgNormal);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(bgHover); }
            public void mouseExited(java.awt.event.MouseEvent e)  { btn.setBackground(bgNormal); }
        });
        return btn;
    }

    public JTable getTable() { return table; }
    public JButton getBtnXemChiTiet() { return btnXemChiTiet; }
    public JButton getBtnTraSach() { return btnTraSach; }
}