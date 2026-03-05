package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import BUS.*;
import DTO.*;

public class AdminQuanLyMuonTraPanel extends JPanel {

    private String tenFont = "Segoe UI";
    private Color colorBackground = new Color(248, 249, 250);

    private JTextField txtMaPhieu, txtMaDG, txtMaSach, txtNgayMuon, txtHanTra, txtNgayTra, txtTienPhat;
    private JComboBox<String> cbTrangThai, cbSearchCriteria;
    private JTextField txtSearch;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnThem, btnTra, btnGiaHan, btnLamMoi, btnSearch, btnResetSearch;

    private PhieuMuonBUS phieuMuonBUS = new PhieuMuonBUS();

    public AdminQuanLyMuonTraPanel() {
        initComponents();
        initEvents();
        lamMoiForm();
        loadDataToTable();
    }

    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBackground(colorBackground);
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel lblTitle = new JLabel("Quản Lý Mượn - Trả Sách");
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

        txtMaPhieu = new JTextField(); txtMaPhieu.setFont(fontInput); txtMaPhieu.setEditable(false);
        txtMaDG = new JTextField(); txtMaDG.setFont(fontInput);
        txtMaSach = new JTextField(); txtMaSach.setFont(fontInput);
        txtNgayMuon = new JTextField(); txtNgayMuon.setFont(fontInput); txtNgayMuon.setEditable(false);
        txtHanTra = new JTextField(); txtHanTra.setFont(fontInput); txtHanTra.setEditable(false);
        txtNgayTra = new JTextField(); txtNgayTra.setFont(fontInput); txtNgayTra.setEditable(false);
        
        cbTrangThai = new JComboBox<>(new String[]{"Đang mượn", "Đã trả", "Trễ hạn"}); 
        cbTrangThai.setFont(fontInput); cbTrangThai.setEnabled(false);
        
        txtTienPhat = new JTextField("0"); txtTienPhat.setFont(fontInput); txtTienPhat.setEditable(false);

        pnlInput.add(createLabel("Mã phiếu:", fontLabel)); pnlInput.add(txtMaPhieu);
        pnlInput.add(createLabel("Mã thẻ độc giả:", fontLabel)); pnlInput.add(txtMaDG);
        pnlInput.add(createLabel("Mã cuốn sách:", fontLabel)); pnlInput.add(txtMaSach);
        pnlInput.add(createLabel("Ngày mượn:", fontLabel)); pnlInput.add(txtNgayMuon);
        pnlInput.add(createLabel("Hạn trả:", fontLabel)); pnlInput.add(txtHanTra);
        pnlInput.add(createLabel("Ngày trả thực:", fontLabel)); pnlInput.add(txtNgayTra);
        pnlInput.add(createLabel("Trạng thái:", fontLabel)); pnlInput.add(cbTrangThai);
        pnlInput.add(createLabel("Tiền phạt:", fontLabel)); pnlInput.add(txtTienPhat);

        // --- THANH TÌM KIẾM ---
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlSearch.setBackground(colorBackground);
        pnlSearch.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        txtSearch = new JTextField(20); txtSearch.setFont(fontInput); txtSearch.setPreferredSize(new Dimension(0, 35));
        cbSearchCriteria = new JComboBox<>(new String[]{"Tất cả", "Mã Phiếu", "Mã Độc Giả", "Trạng Thái"});
        cbSearchCriteria.setFont(fontInput); cbSearchCriteria.setPreferredSize(new Dimension(140, 35));
        btnSearch = createActionButton("Tìm Kiếm", new Color(13, 110, 253)); btnSearch.setPreferredSize(new Dimension(110, 35));
        btnResetSearch = createActionButton("Hủy Lọc", new Color(108, 117, 125)); btnResetSearch.setPreferredSize(new Dimension(100, 35));

        pnlSearch.add(createLabel("Tra cứu phiếu:", new Font(tenFont, Font.BOLD, 14)));
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
        String[] columns = {"Mã Phiếu", "Mã Thẻ (ĐG)", "Ngày Mượn", "Hạn Trả", "Ngày Trả", "Trạng Thái"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        setupTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);

        // --- NÚT CHỨC NĂNG ---
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        pnlButtons.setBackground(colorBackground);

        btnThem = createActionButton("Lập Phiếu", new Color(34, 197, 94));
        btnTra = createActionButton("Xác Nhận Trả", new Color(13, 110, 253));
        btnGiaHan = createActionButton("Gia Hạn", new Color(255, 193, 7)); btnGiaHan.setForeground(Color.BLACK);
        btnLamMoi = createActionButton("Làm Mới Form", new Color(108, 117, 125));

        pnlButtons.add(btnThem); pnlButtons.add(btnTra); pnlButtons.add(btnGiaHan); pnlButtons.add(btnLamMoi);
        add(pnlButtons, BorderLayout.SOUTH);
    }

    private void initEvents() {
        btnLamMoi.addActionListener(e -> {
            lamMoiForm();
            loadDataToTable();
        });

        btnThem.addActionListener(e -> {
            String maThe = txtMaDG.getText().trim();
            String maSach = txtMaSach.getText().trim();

            if (maThe.isEmpty() || maSach.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã thẻ độc giả và Mã cuốn sách!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String maNQL = SessionManager.getInstance().getMaNguoi();
            if (maNQL == null || maNQL.isEmpty()) {
                maNQL = "NQL0000001";
            }

            PhieuMuonDTO pm = new PhieuMuonDTO();
            pm.setMaPM(txtMaPhieu.getText().trim());
            pm.setMaThe(maThe);
            pm.setMaNQL(maNQL);
            pm.setNgayMuon(txtNgayMuon.getText().trim());
            pm.setHenTra(txtHanTra.getText().trim());
            pm.setNgayTra(""); 
            pm.setTinhTrang("Đang mượn");

            String resultPM = phieuMuonBUS.insert(pm);
            if (resultPM.contains("thành công")) {
                try {
                    ChiTietPhieuMuonDTO ctpm = new ChiTietPhieuMuonDTO();
                    ctpm.setMaPM(pm.getMaPM());
                    ctpm.setMaCuonSach(maSach);
                    ctpm.setTinhTrangSach("Bình thường"); 
                    
                    BUS.ChiTietPhieuMuonBUS chiTietBUS = new BUS.ChiTietPhieuMuonBUS();
                    chiTietBUS.add(ctpm); 
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                JOptionPane.showMessageDialog(this, "Lập phiếu mượn thành công!");
                lamMoiForm();
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(this, resultPM, "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void loadDataToTable() {
        model.setRowCount(0);
        try {
            ArrayList<PhieuMuonDTO> listPM = phieuMuonBUS.getAll();
            for (PhieuMuonDTO pm : listPM) {
                model.addRow(new Object[]{
                    pm.getMaPM(), 
                    pm.getMaThe(), 
                    pm.getNgayMuon(), 
                    pm.getHenTra(), 
                    pm.getNgayTra() == null || pm.getNgayTra().isEmpty() ? "Chưa trả" : pm.getNgayTra(), 
                    pm.getTinhTrang()
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void lamMoiForm() {
        txtMaPhieu.setText(phieuMuonBUS.generateMaPM());
        txtMaDG.setText("");
        txtMaSach.setText("");
        
        LocalDate today = LocalDate.now();
        txtNgayMuon.setText(today.toString()); 
        txtHanTra.setText(today.plusDays(14).toString()); 
        
        txtNgayTra.setText("");
        cbTrangThai.setSelectedIndex(0);
        table.clearSelection();
    }

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