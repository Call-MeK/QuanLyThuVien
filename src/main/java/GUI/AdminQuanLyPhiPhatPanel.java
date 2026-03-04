package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import BUS.*;
import DTO.*;

public class AdminQuanLyPhiPhatPanel extends JPanel {

    private String tenFont = "Segoe UI";
    private Color colorBackground = new Color(248, 249, 250);

    private JTextField txtMaPhieuPhat, txtMaPhieuMuon, txtMaDG, txtSoTien;
    private JComboBox<String> cbLyDo, cbTrangThai, cbSearchCriteria;
    private JTextField txtSearch;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnThem, btnThuTien, btnXoa, btnLamMoi, btnSearch, btnResetSearch;

    public AdminQuanLyPhiPhatPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBackground(colorBackground);
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel lblTitle = new JLabel("Quản Lý Phiếu Phạt");
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 24));
        lblTitle.setForeground(new Color(33, 37, 41));
        add(lblTitle, BorderLayout.NORTH);

        JPanel pnlCenter = new JPanel(new BorderLayout(0, 15));
        pnlCenter.setBackground(colorBackground);

        // --- FORM NHẬP LIỆU ---
        JPanel pnlInput = new JPanel(new GridLayout(3, 4, 15, 15));
        pnlInput.setBackground(Color.WHITE);
        pnlInput.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        Font fontLabel = new Font(tenFont, Font.BOLD, 14);
        Font fontInput = new Font(tenFont, Font.PLAIN, 14);

        txtMaPhieuPhat = new JTextField(); txtMaPhieuPhat.setFont(fontInput);
        txtMaPhieuMuon = new JTextField(); txtMaPhieuMuon.setFont(fontInput);
        txtMaDG = new JTextField(); txtMaDG.setFont(fontInput);
        cbLyDo = new JComboBox<>(new String[]{"Trả sách trễ hạn", "Làm rách/bẩn sách", "Làm mất sách", "Lý do khác"});
        cbLyDo.setFont(fontInput);
        txtSoTien = new JTextField("0"); txtSoTien.setFont(fontInput);
        cbTrangThai = new JComboBox<>(new String[]{"Chưa thanh toán", "Đã thanh toán"}); cbTrangThai.setFont(fontInput);

        pnlInput.add(createLabel("Mã phiếu phạt:", fontLabel)); pnlInput.add(txtMaPhieuPhat);
        pnlInput.add(createLabel("Mã phiếu mượn:", fontLabel)); pnlInput.add(txtMaPhieuMuon);
        pnlInput.add(createLabel("Mã độc giả:", fontLabel)); pnlInput.add(txtMaDG);
        pnlInput.add(createLabel("Lý do phạt:", fontLabel)); pnlInput.add(cbLyDo);
        pnlInput.add(createLabel("Số tiền (VNĐ):", fontLabel)); pnlInput.add(txtSoTien);
        pnlInput.add(createLabel("Trạng thái:", fontLabel)); pnlInput.add(cbTrangThai);

        // --- THANH TÌM KIẾM ---
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlSearch.setBackground(colorBackground);
        pnlSearch.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        txtSearch = new JTextField(20); txtSearch.setFont(fontInput); txtSearch.setPreferredSize(new Dimension(0, 35));
        cbSearchCriteria = new JComboBox<>(new String[]{"Tất cả", "Mã Phạt", "Mã Độc Giả", "Trạng Thái"});
        cbSearchCriteria.setFont(fontInput); cbSearchCriteria.setPreferredSize(new Dimension(140, 35));
        btnSearch = createActionButton("Tìm Kiếm", new Color(13, 110, 253)); btnSearch.setPreferredSize(new Dimension(110, 35));
        btnResetSearch = createActionButton("Hủy Lọc", new Color(108, 117, 125)); btnResetSearch.setPreferredSize(new Dimension(100, 35));

        pnlSearch.add(createLabel("Tra cứu phiếu phạt:", new Font(tenFont, Font.BOLD, 14)));
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
        String[] columns = {"Mã Phạt", "Mã Phiếu Mượn", "Mã Độc Giả", "Lý Do Phạt", "Số Tiền", "Trạng Thái"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        setupTable(table);

        try {
            ArrayList<PhieuPhatDTO> listPP = new PhieuPhatBUS().getAll();
            for (PhieuPhatDTO pp : listPP) {
                String trangThai = pp.getTrangThai() == 1 ? "Chưa thanh toán" : "Đã thanh toán";
                model.addRow(new Object[]{pp.getMaPP(), pp.getMaPM(), pp.getMaNQL(), pp.getNgayLap(), pp.getTongTien(), trangThai});
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

        btnThem = createActionButton("Lập Phiếu Phạt", new Color(34, 197, 94));
        btnThuTien = createActionButton("Xác Nhận Thu", new Color(13, 110, 253));
        btnXoa = createActionButton("Hủy Phiếu", new Color(220, 53, 69));
        btnLamMoi = createActionButton("Làm Mới Form", new Color(108, 117, 125));

        pnlButtons.add(btnThem); pnlButtons.add(btnThuTien); pnlButtons.add(btnXoa); pnlButtons.add(btnLamMoi);
        add(pnlButtons, BorderLayout.SOUTH);
    }

    // ===== GETTER =====
    public JTextField getTxtMaPhieuPhat() { return txtMaPhieuPhat; }
    public JTextField getTxtMaPhieuMuon() { return txtMaPhieuMuon; }
    public JTextField getTxtMaDG() { return txtMaDG; }
    public JComboBox<String> getCbLyDo() { return cbLyDo; }
    public JTextField getTxtSoTien() { return txtSoTien; }
    public JComboBox<String> getCbTrangThai() { return cbTrangThai; }
    public JTable getTable() { return table; }
    public DefaultTableModel getModel() { return model; }
    public JButton getBtnThem() { return btnThem; }
    public JButton getBtnThuTien() { return btnThuTien; }
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