package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import BUS.*;
import DTO.*;

public class AdminQuanLyNhapSachPanel extends JPanel {

    private String tenFont = "Segoe UI";
    private Color colorBackground = new Color(248, 249, 250);

    private JTextField txtMaPN, txtNguoiNhap, txtMaSach, txtSoLuong, txtDonGia, txtNhaCungCap, txtNgayNhap, txtTongTien;
    private JComboBox<String> cbSearchCriteria;
    private JTextField txtSearch;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnSearch, btnResetSearch;

    public AdminQuanLyNhapSachPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBackground(colorBackground);
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel lblTitle = new JLabel("Quản Lý Phiếu Nhập Sách");
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 24));
        lblTitle.setForeground(new Color(33, 37, 41));
        add(lblTitle, BorderLayout.NORTH);

        JPanel pnlCenter = new JPanel(new BorderLayout(0, 15));
        pnlCenter.setBackground(colorBackground);

        // --- FORM NHẬP LIỆU ---
        JPanel pnlInput = new JPanel(new GridLayout(4, 4, 15, 15));
        pnlInput.setBackground(Color.WHITE);
        pnlInput.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        Font fontLabel = new Font(tenFont, Font.BOLD, 14);
        Font fontInput = new Font(tenFont, Font.PLAIN, 14);

        txtMaPN = new JTextField(); txtMaPN.setFont(fontInput);
        txtNguoiNhap = new JTextField(); txtNguoiNhap.setFont(fontInput);
        txtMaSach = new JTextField(); txtMaSach.setFont(fontInput);
        txtSoLuong = new JTextField("0"); txtSoLuong.setFont(fontInput);
        txtDonGia = new JTextField("0"); txtDonGia.setFont(fontInput);
        txtNhaCungCap = new JTextField(); txtNhaCungCap.setFont(fontInput);
        txtNgayNhap = new JTextField("dd/MM/yyyy"); txtNgayNhap.setFont(fontInput); txtNgayNhap.setForeground(Color.GRAY);
        txtTongTien = new JTextField("0"); txtTongTien.setFont(fontInput); txtTongTien.setEditable(false);

        pnlInput.add(createLabel("Mã phiếu nhập:", fontLabel)); pnlInput.add(txtMaPN);
        pnlInput.add(createLabel("Mã Quản lý:", fontLabel)); pnlInput.add(txtNguoiNhap);
        pnlInput.add(createLabel("Mã sách:", fontLabel)); pnlInput.add(txtMaSach);
        pnlInput.add(createLabel("Số lượng:", fontLabel)); pnlInput.add(txtSoLuong);
        pnlInput.add(createLabel("Đơn giá:", fontLabel)); pnlInput.add(txtDonGia);
        pnlInput.add(createLabel("Nhà cung cấp:", fontLabel)); pnlInput.add(txtNhaCungCap);
        pnlInput.add(createLabel("Ngày nhập:", fontLabel)); pnlInput.add(txtNgayNhap);
        pnlInput.add(createLabel("Tổng tiền (VNĐ):", fontLabel)); pnlInput.add(txtTongTien);

        // --- THANH TÌM KIẾM ---
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlSearch.setBackground(colorBackground);
        pnlSearch.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        txtSearch = new JTextField(20); txtSearch.setFont(fontInput); txtSearch.setPreferredSize(new Dimension(0, 35));
        cbSearchCriteria = new JComboBox<>(new String[]{"Tất cả", "Mã Phiếu", "Mã Sách", "Nhà Cung Cấp"});
        cbSearchCriteria.setFont(fontInput); cbSearchCriteria.setPreferredSize(new Dimension(140, 35));
        btnSearch = createActionButton("Tìm Kiếm", new Color(13, 110, 253)); btnSearch.setPreferredSize(new Dimension(110, 35));
        btnResetSearch = createActionButton("Hủy Lọc", new Color(108, 117, 125)); btnResetSearch.setPreferredSize(new Dimension(100, 35));

        pnlSearch.add(createLabel("Tra cứu phiếu nhập:", new Font(tenFont, Font.BOLD, 14)));
        pnlSearch.add(txtSearch);
        pnlSearch.add(createLabel(" theo ", fontInput));
        pnlSearch.add(cbSearchCriteria);
        pnlSearch.add(btnSearch);
        pnlSearch.add(btnResetSearch);

        JPanel pnlTopCenter = new JPanel(new BorderLayout(0, 5));
        pnlTopCenter.setBackground(colorBackground);
        pnlTopCenter.add(pnlInput, BorderLayout.NORTH);
        pnlTopCenter.add(pnlSearch, BorderLayout.CENTER);
        pnlCenter.add(pnlTopCenter, BorderLayout.NORTH);

        // --- BẢNG ---
        String[] columns = {"Mã Phiếu", "Mã Sách", "Số Lượng", "Đơn Giá", "Nhà Cung Cấp", "Ngày Nhập", "Tổng Tiền"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        setupTable(table);

        try {
            List<PhieuNhapDTO> listPN = new PhieuNhapBUS().findAll();
            for (PhieuNhapDTO pn : listPN) {
                model.addRow(new Object[]{pn.getMaPN(), pn.getMaNXB(), pn.getMaNQL(), "", "", pn.getNgayNhap(), pn.getTongTien()});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);

        // --- NÚT CHỨC NĂNG ---
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        pnlButtons.setBackground(colorBackground);

        btnThem = createActionButton("Tạo Phiếu Nhập", new Color(34, 197, 94));
        btnSua = createActionButton("Sửa Thông Tin", new Color(255, 193, 7)); btnSua.setForeground(Color.BLACK);
        btnXoa = createActionButton("Hủy Phiếu", new Color(220, 53, 69));
        btnLamMoi = createActionButton("Làm Mới Form", new Color(108, 117, 125));

        pnlButtons.add(btnThem); pnlButtons.add(btnSua); pnlButtons.add(btnXoa); pnlButtons.add(btnLamMoi);
        add(pnlButtons, BorderLayout.SOUTH);
    }

    // ===== GETTER =====
    public JTextField getTxtMaPN() { return txtMaPN; }
    public JTextField getTxtNguoiNhap() { return txtNguoiNhap; }
    public JTextField getTxtMaSach() { return txtMaSach; }
    public JTextField getTxtSoLuong() { return txtSoLuong; }
    public JTextField getTxtDonGia() { return txtDonGia; }
    public JTextField getTxtNhaCungCap() { return txtNhaCungCap; }
    public JTextField getTxtNgayNhap() { return txtNgayNhap; }
    public JTextField getTxtTongTien() { return txtTongTien; }
    public JTable getTable() { return table; }
    public DefaultTableModel getModel() { return model; }
    public JButton getBtnThem() { return btnThem; }
    public JButton getBtnSua() { return btnSua; }
    public JButton getBtnXoa() { return btnXoa; }
    public JButton getBtnLamMoi() { return btnLamMoi; }

    // ===== TIỆN ÍCH =====
    private JLabel createLabel(String text, Font font) {
        JLabel lbl = new JLabel(text); lbl.setFont(font); lbl.setForeground(new Color(73, 80, 87)); return lbl;
    }
    private void setupTable(JTable t) {
        t.setRowHeight(32); t.setFont(new Font(tenFont, Font.PLAIN, 14));
        t.getTableHeader().setFont(new Font(tenFont, Font.BOLD, 14));
        t.getTableHeader().setBackground(new Color(233, 236, 239)); t.getTableHeader().setOpaque(false);
        t.setShowGrid(false); t.setIntercellSpacing(new Dimension(0, 0));
        t.setShowHorizontalLines(true); t.setGridColor(new Color(222, 226, 230));
    }
    private JButton createActionButton(String text, Color bgColor) {
        JButton btn = new JButton(text); btn.setFont(new Font(tenFont, Font.BOLD, 14));
        btn.setForeground(Color.WHITE); btn.setBackground(bgColor);
        btn.setBorderPainted(false); btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(150, 40)); btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}