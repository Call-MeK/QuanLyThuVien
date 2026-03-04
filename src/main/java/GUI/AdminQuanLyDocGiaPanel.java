package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import BUS.*;
import DTO.*;

public class AdminQuanLyDocGiaPanel extends JPanel {

    private String tenFont = "Segoe UI";
    private Color colorBackground = new Color(248, 249, 250);

    private JTextField txtMaDG, txtHoTen, txtNgaySinh, txtDienThoai, txtEmail, txtNgayDK;
    private JComboBox<String> cbGioiTinh, cbTrangThai, cbSearchCriteria;
    private JTextField txtSearch;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnThem, btnSua, btnKhoa, btnLamMoi, btnSearch, btnResetSearch;

    public AdminQuanLyDocGiaPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBackground(colorBackground);
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel lblTitle = new JLabel("Quản Lý Thông Tin Độc Giả");
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

        txtMaDG = new JTextField(); txtMaDG.setFont(fontInput);
        txtHoTen = new JTextField(); txtHoTen.setFont(fontInput);
        cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"}); cbGioiTinh.setFont(fontInput);
        txtNgaySinh = new JTextField("dd/MM/yyyy"); txtNgaySinh.setFont(fontInput); txtNgaySinh.setForeground(Color.GRAY);
        txtDienThoai = new JTextField(); txtDienThoai.setFont(fontInput);
        txtEmail = new JTextField(); txtEmail.setFont(fontInput);
        txtNgayDK = new JTextField(); txtNgayDK.setFont(fontInput);
        cbTrangThai = new JComboBox<>(new String[]{"Đang hoạt động", "Bị khóa", "Hết hạn"}); cbTrangThai.setFont(fontInput);

        pnlInput.add(createLabel("Mã độc giả:", fontLabel)); pnlInput.add(txtMaDG);
        pnlInput.add(createLabel("Họ và tên:", fontLabel)); pnlInput.add(txtHoTen);
        pnlInput.add(createLabel("Giới tính:", fontLabel)); pnlInput.add(cbGioiTinh);
        pnlInput.add(createLabel("Ngày sinh:", fontLabel)); pnlInput.add(txtNgaySinh);
        pnlInput.add(createLabel("Điện thoại:", fontLabel)); pnlInput.add(txtDienThoai);
        pnlInput.add(createLabel("Email:", fontLabel)); pnlInput.add(txtEmail);
        pnlInput.add(createLabel("Ngày đăng ký:", fontLabel)); pnlInput.add(txtNgayDK);
        pnlInput.add(createLabel("Trạng thái:", fontLabel)); pnlInput.add(cbTrangThai);

        // --- THANH TÌM KIẾM ---
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlSearch.setBackground(colorBackground);
        pnlSearch.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        txtSearch = new JTextField(20); txtSearch.setFont(fontInput); txtSearch.setPreferredSize(new Dimension(0, 35));
        cbSearchCriteria = new JComboBox<>(new String[]{"Tất cả", "Mã ĐG", "Họ Tên", "Số Điện Thoại", "Trạng Thái"});
        cbSearchCriteria.setFont(fontInput); cbSearchCriteria.setPreferredSize(new Dimension(140, 35));
        btnSearch = createActionButton("Lọc", new Color(13, 110, 253)); btnSearch.setPreferredSize(new Dimension(100, 35));
        btnResetSearch = createActionButton("Hủy Lọc", new Color(108, 117, 125)); btnResetSearch.setPreferredSize(new Dimension(100, 35));

        pnlSearch.add(createLabel("🔍 Tìm kiếm:", new Font(tenFont, Font.BOLD, 14)));
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
        String[] columns = {"Mã ĐG", "Họ Tên", "Giới Tính", "Ngày Sinh", "Điện Thoại", "Trạng Thái"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        setupTable(table);

        try {
            ArrayList<DocGiaDTO> listDG = new DocGiaBUS().getAll();
            if (listDG != null) {
                for (DocGiaDTO dg : listDG) {
                    model.addRow(new Object[]{dg.getMaDocGia(), dg.getHoTen(), dg.getGioiTinh(), dg.getNgaySinh(), dg.getSoDienThoai(), dg.getTrangThai()});
                }
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

        btnThem = createActionButton("Thêm Độc Giả", new Color(25, 135, 84));
        btnSua = createActionButton("Cập Nhật", new Color(255, 193, 7)); btnSua.setForeground(Color.BLACK);
        btnKhoa = createActionButton("Khóa Thẻ", new Color(220, 53, 69));
        btnLamMoi = createActionButton("Làm Mới Form", new Color(108, 117, 125));

        pnlButtons.add(btnThem); pnlButtons.add(btnSua); pnlButtons.add(btnKhoa); pnlButtons.add(btnLamMoi);
        add(pnlButtons, BorderLayout.SOUTH);
    }

    // ===== GETTER =====
    public JTextField getTxtMaDG() { return txtMaDG; }
    public JTextField getTxtHoTen() { return txtHoTen; }
    public JComboBox<String> getCbGioiTinh() { return cbGioiTinh; }
    public JTextField getTxtNgaySinh() { return txtNgaySinh; }
    public JTextField getTxtDienThoai() { return txtDienThoai; }
    public JTextField getTxtEmail() { return txtEmail; }
    public JTextField getTxtNgayDK() { return txtNgayDK; }
    public JComboBox<String> getCbTrangThai() { return cbTrangThai; }
    public JTextField getTxtSearch() { return txtSearch; }
    public JComboBox<String> getCbSearchCriteria() { return cbSearchCriteria; }
    public JTable getTable() { return table; }
    public DefaultTableModel getModel() { return model; }
    public JButton getBtnThem() { return btnThem; }
    public JButton getBtnSua() { return btnSua; }
    public JButton getBtnKhoa() { return btnKhoa; }
    public JButton getBtnLamMoi() { return btnLamMoi; }
    public JButton getBtnSearch() { return btnSearch; }
    public JButton getBtnResetSearch() { return btnResetSearch; }

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