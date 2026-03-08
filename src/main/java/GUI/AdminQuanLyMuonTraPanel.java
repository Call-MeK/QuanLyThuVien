package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import BUS.*;
import DTO.*;

public class AdminQuanLyMuonTraPanel extends JPanel {

    private String tenFont = "Segoe UI";
    private Color colorBackground = new Color(248, 249, 250);

    private JTextField txtMaPhieu, txtMaDG, txtMaThe, txtNgayMuon, txtHanTra, txtNgayTra, txtTrangThai;
    private JTextField txtMaNQL, txtTenSach;
    private JTextField txtMaSachLookup;
    private JComboBox<String> cbBanSao;
    private JComboBox<String> cbSearchCriteria, cbSearchTrangThai;
    private JTextField txtSearch;
    private JPanel pnlSearchInput;
    private CardLayout searchInputLayout;
    private JTable table;
    private DefaultTableModel model;

    private JButton btnThem, btnTra, btnLamMoi, btnSearch, btnResetSearch, btnLookupSach;
    private JButton btnExport, btnSave;

    private PhieuMuonBUS phieuMuonBUS = new PhieuMuonBUS();
    private TheThuVienBUS theThuVienBUS = new TheThuVienBUS();
    private DocGiaBUS docGiaBUS = new DocGiaBUS();
    private SachCopyBUS copyBUS = new SachCopyBUS();

    private List<SachCopyDTO> dsBanSaoHienTai = new ArrayList<>();

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

        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(colorBackground);
        JLabel lblTitle = new JLabel("Quản Lý Mượn - Trả Sách");
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 24));
        lblTitle.setForeground(new Color(33, 37, 41));
        pnlHeader.add(lblTitle, BorderLayout.WEST);

        btnLamMoi = new JButton("↺");
        btnLamMoi.setFont(new Font(tenFont, Font.BOLD, 18));
        btnLamMoi.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLamMoi.setContentAreaFilled(false);
        btnLamMoi.setBorderPainted(false);
        btnLamMoi.setFocusPainted(false);
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/Images/refresh.png"));
            Image img = icon.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
            btnLamMoi.setIcon(new ImageIcon(img));
            btnLamMoi.setText("");
        } catch (Exception e) {
        }
        pnlHeader.add(btnLamMoi, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        JPanel pnlCenter = new JPanel(new BorderLayout(0, 15));
        pnlCenter.setBackground(colorBackground);

        Font fontLabel = new Font(tenFont, Font.BOLD, 14);
        Font fontInput = new Font(tenFont, Font.PLAIN, 14);

        JPanel pnlInput = new JPanel(new GridLayout(6, 4, 15, 10));
        pnlInput.setBackground(Color.WHITE);
        pnlInput.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1, true),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)));

        txtMaPhieu = roField(fontInput);
        txtMaDG = new JTextField();
        txtMaDG.setFont(fontInput);
        txtMaThe = roField(fontInput);
        txtMaThe.setBackground(new Color(233, 236, 239));
        txtNgayMuon = roField(fontInput);
        txtHanTra = roField(fontInput);
        txtNgayTra = roField(fontInput);
        txtTrangThai = roField(fontInput);
        txtTrangThai.setBackground(new Color(227, 242, 253));
        txtTrangThai.setForeground(new Color(13, 71, 161));

        txtMaNQL = roField(fontInput);
        txtMaNQL.setBackground(new Color(233, 236, 239));
        txtTenSach = roField(fontInput);
        txtTenSach.setBackground(new Color(233, 236, 239));

        txtMaSachLookup = new JTextField();
        txtMaSachLookup.setFont(fontInput);
        txtMaSachLookup.setToolTipText("Nhập Mã Sách rồi bấm Tìm bản sao");

        btnLookupSach = new JButton("Tìm bản sao");
        btnLookupSach.setFont(new Font(tenFont, Font.BOLD, 13));
        btnLookupSach.setBackground(new Color(13, 110, 253));
        btnLookupSach.setForeground(Color.WHITE);
        btnLookupSach.setFocusPainted(false);
        btnLookupSach.setBorderPainted(false);
        btnLookupSach.setCursor(new Cursor(Cursor.HAND_CURSOR));

        cbBanSao = new JComboBox<>();
        cbBanSao.setFont(fontInput);

        pnlInput.add(lbl("Mã phiếu mượn:", fontLabel));
        pnlInput.add(txtMaPhieu);
        pnlInput.add(lbl("Mã độc giả:", fontLabel));
        pnlInput.add(txtMaDG);

        pnlInput.add(lbl("Mã thẻ (tự tìm):", fontLabel));
        pnlInput.add(txtMaThe);
        pnlInput.add(lbl("Ngày mượn:", fontLabel));
        pnlInput.add(txtNgayMuon);

        pnlInput.add(lbl("Hạn trả:", fontLabel));
        pnlInput.add(txtHanTra);
        pnlInput.add(lbl("Ngày trả thực:", fontLabel));
        pnlInput.add(txtNgayTra);

        pnlInput.add(lbl("Trạng thái:", fontLabel));
        pnlInput.add(txtTrangThai);
        pnlInput.add(lbl("Mã NV lập phiếu:", fontLabel));
        pnlInput.add(txtMaNQL);

        pnlInput.add(lbl("Tên sách đang mượn:", fontLabel));
        pnlInput.add(txtTenSach);
        pnlInput.add(lbl("Mã sách:", fontLabel));
        JPanel pnlLookup = new JPanel(new BorderLayout(5, 0));
        pnlLookup.setBackground(Color.WHITE);
        pnlLookup.add(txtMaSachLookup, BorderLayout.CENTER);
        pnlLookup.add(btnLookupSach, BorderLayout.EAST);
        pnlInput.add(pnlLookup);
        pnlInput.add(lbl("Chọn bản sao:", fontLabel));
        pnlInput.add(cbBanSao);
        pnlInput.add(new JLabel());
        pnlInput.add(new JLabel());

        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlSearch.setBackground(colorBackground);
        pnlSearch.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        txtSearch = new JTextField(20);
        txtSearch.setFont(fontInput);
        txtSearch.setPreferredSize(new Dimension(0, 35));

        cbSearchTrangThai = new JComboBox<>(new String[] { "Đang mượn", "Đã trả", "Trễ hạn", "Đã xóa" });
        cbSearchTrangThai.setFont(fontInput);
        cbSearchTrangThai.setPreferredSize(new Dimension(160, 35));

        searchInputLayout = new CardLayout();
        pnlSearchInput = new JPanel(searchInputLayout);
        pnlSearchInput.setPreferredSize(new Dimension(200, 35));
        pnlSearchInput.add(txtSearch, "TEXT");
        pnlSearchInput.add(cbSearchTrangThai, "COMBO");
        searchInputLayout.show(pnlSearchInput, "TEXT");

        cbSearchCriteria = new JComboBox<>(new String[] { "Tất cả", "Mã Phiếu", "Mã Độc Giả", "Trạng Thái" });
        cbSearchCriteria.setFont(fontInput);
        cbSearchCriteria.setPreferredSize(new Dimension(140, 35));

        btnSearch = actionBtn("Lọc", new Color(13, 110, 253));
        btnSearch.setPreferredSize(new Dimension(100, 35));
        btnResetSearch = actionBtn("Hủy Lọc", new Color(108, 117, 125));
        btnResetSearch.setPreferredSize(new Dimension(100, 35));

        pnlSearch.add(lbl("Tra cứu:", new Font(tenFont, Font.BOLD, 14)));
        pnlSearch.add(pnlSearchInput);
        pnlSearch.add(lbl(" theo ", fontInput));
        pnlSearch.add(cbSearchCriteria);
        pnlSearch.add(btnSearch);
        pnlSearch.add(btnResetSearch);

        JPanel pnlTopCenter = new JPanel(new BorderLayout(0, 5));
        pnlTopCenter.setBackground(colorBackground);
        pnlTopCenter.add(pnlInput, BorderLayout.NORTH);
        pnlTopCenter.add(pnlSearch, BorderLayout.CENTER);
        pnlCenter.add(pnlTopCenter, BorderLayout.NORTH);

        String[] cols = { "Mã Phiếu Mượn", "Mã Thẻ", "Ngày Mượn", "Hạn Trả", "Ngày Trả", "Trạng Thái" };
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        table = new JTable(model);
        setupTable(table);
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                if (!isSelected) {
                    String tt = model.getValueAt(row, 5) != null ? model.getValueAt(row, 5).toString() : "";
                    switch (tt) {
                        case "Trễ hạn":
                            c.setBackground(new Color(255, 235, 238));
                            c.setForeground(new Color(183, 28, 28));
                            break;
                        case "Đã trả":
                            c.setBackground(new Color(232, 245, 233));
                            c.setForeground(new Color(27, 94, 32));
                            break;
                        case "Đã xóa":
                            c.setBackground(new Color(230, 230, 230));
                            c.setForeground(new Color(100, 100, 100));
                            break;
                        default:
                            c.setBackground(Color.WHITE);
                            c.setForeground(Color.BLACK);
                            break;
                    }
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        pnlButtons.setBackground(colorBackground);
        btnSave = actionBtn("Save", new Color(111, 66, 193));
        btnExport = actionBtn("Export", new Color(33, 115, 70));
        btnThem = actionBtn("Lập Phiếu", new Color(25, 135, 84));
        btnTra = actionBtn("Xác Nhận Trả", new Color(13, 110, 253));
        pnlButtons.add(btnSave);
        pnlButtons.add(btnExport);
        pnlButtons.add(btnThem);
        pnlButtons.add(btnTra);
        add(pnlButtons, BorderLayout.SOUTH);
    }

    private void initEvents() {

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row < 0)
                    return;
                String maPM = val(row, 0);
                String maThe = val(row, 1);
                String ngayMuon = val(row, 2);
                String hanTra = val(row, 3);
                String ngayTra = val(row, 4);
                String tt = val(row, 5);

                txtMaPhieu.setText(maPM);
                txtMaThe.setText(maThe);
                txtNgayMuon.setText(ngayMuon);
                txtHanTra.setText(hanTra);
                txtNgayTra.setText("Chưa trả".equals(ngayTra) ? "" : ngayTra);
                txtTrangThai.setText(tt);
                capNhatMauTrangThai(tt);

                TheThuVienDTO the = theThuVienBUS.getById(maThe);
                txtMaDG.setText(the != null ? the.getMaDocGia() : "");

                loadChiTietPhieu(maPM);

                txtMaSachLookup.setText("");
                cbBanSao.removeAllItems();
                dsBanSaoHienTai.clear();
            }
        });

        txtMaDG.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                String maDG = txtMaDG.getText().trim();
                if (maDG.isEmpty())
                    return;
                TheThuVienDTO the = theThuVienBUS.getByMaDocGia(maDG);
                if (the != null) {
                    txtMaThe.setText(the.getMaThe());
                    txtMaThe.setForeground(new Color(33, 37, 41));
                } else {
                    txtMaThe.setText("Chưa có thẻ");
                    txtMaThe.setForeground(new Color(220, 53, 69));
                }
            }
        });

        btnLookupSach.addActionListener(e -> {
            String maSach = txtMaSachLookup.getText().trim();
            if (maSach.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã Sách!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            loadBanSao(maSach);
        });
        txtMaSachLookup.addActionListener(e -> btnLookupSach.doClick());

        cbSearchCriteria.addActionListener(e -> {
            if ("Trạng Thái".equals(cbSearchCriteria.getSelectedItem()))
                searchInputLayout.show(pnlSearchInput, "COMBO");
            else
                searchInputLayout.show(pnlSearchInput, "TEXT");
        });

        btnLamMoi.addActionListener(e -> {
            lamMoiForm();
            loadDataToTable();
        });

        btnThem.addActionListener(e -> {
            String maDG = txtMaDG.getText().trim();
            String maThe = txtMaThe.getText().trim();
            if (maDG.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã độc giả!", "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (maThe.isEmpty() || maThe.startsWith("Chưa")) {
                TheThuVienDTO the = theThuVienBUS.getByMaDocGia(maDG);
                if (the == null) {
                    JOptionPane.showMessageDialog(this, "Độc giả chưa có thẻ thư viện!", "Cảnh báo",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                maThe = the.getMaThe();
                txtMaThe.setText(maThe);
            }
            String maVach = getMaVachDaChon();
            if (maVach == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn bản sao sách!", "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (docGiaBUS.isDocGiaLocked(maDG)) {
                JOptionPane.showMessageDialog(this, "Độc giả đang bị khóa thẻ!", "Từ chối",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String maNQL = SessionManager.getInstance().getMaNguoi();
            if (maNQL == null || maNQL.isEmpty())
                maNQL = "NV00000001";

            PhieuMuonDTO pm = new PhieuMuonDTO();
            pm.setMaPM(txtMaPhieu.getText().trim());
            pm.setMaThe(maThe);
            pm.setMaNQL(maNQL);
            pm.setNgayMuon(txtNgayMuon.getText().trim());
            pm.setHenTra(txtHanTra.getText().trim());
            pm.setNgayTra("");
            pm.setTinhTrang("Đang mượn");

            String result = phieuMuonBUS.insert(pm);
            if (result.contains("thành công")) {
                try {
                    ChiTietPhieuMuonDTO ct = new ChiTietPhieuMuonDTO();
                    ct.setMaPM(pm.getMaPM());
                    ct.setMaCuonSach(maVach);
                    ct.setTinhTrangSach("Bình thường");
                    new ChiTietPhieuMuonBUS().add(ct);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(this,
                        "Lập phiếu thành công!\nMã phiếu: " + pm.getMaPM() + "\nHạn trả: " + pm.getHenTra());
                lamMoiForm();
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnTra.addActionListener(e -> {
            String maPM = txtMaPhieu.getText().trim();
            if (maPM.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 phiếu!");
                return;
            }
            String tt = txtTrangThai.getText().trim();
            if ("Đã trả".equals(tt)) {
                JOptionPane.showMessageDialog(this, "Phiếu này đã trả rồi!");
                return;
            }
            if ("Đã xóa".equals(tt)) {
                JOptionPane.showMessageDialog(this, "Phiếu đã bị xóa!");
                return;
            }

            Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
            TraSachDialog dialog = new TraSachDialog(parentFrame, maPM);
            dialog.setVisible(true);
            if (!dialog.isConfirmed())
                return;

            PhieuMuonDTO pm = phieuMuonBUS.getById(maPM);
            if (pm == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            pm.setNgayTra(LocalDate.now().toString());
            pm.setTinhTrang("Đã trả");
            String result = phieuMuonBUS.update(pm);
            if (result.contains("thành công")) {
                String msg = "Xác nhận trả thành công!";
                if (dialog.hasHong())
                    msg += "\nCó sách hỏng — vào Quản Lý Phí Phạt để lập phiếu phạt.";
                JOptionPane.showMessageDialog(this, msg, "Thành công", JOptionPane.INFORMATION_MESSAGE);
                lamMoiForm();
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSearch.addActionListener(e -> {
            String criteria = (String) cbSearchCriteria.getSelectedItem();
            String keyword = "Trạng Thái".equals(criteria) ? (String) cbSearchTrangThai.getSelectedItem()
                    : txtSearch.getText().trim();
            boolean hienDaXoa = "Trạng Thái".equals(criteria) && "Đã xóa".equals(keyword);
            hienThiKetQua(phieuMuonBUS.search(keyword), criteria, keyword, hienDaXoa);
        });
        txtSearch.addActionListener(e -> btnSearch.doClick());

        btnResetSearch.addActionListener(e -> {
            txtSearch.setText("");
            cbSearchCriteria.setSelectedIndex(0);
            cbSearchTrangThai.setSelectedIndex(0);
            searchInputLayout.show(pnlSearchInput, "TEXT");
            loadDataToTable();
        });

        btnExport.addActionListener(e -> {
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Utils.ExcelExporter.exportTableToExcel(table, "DanhSach_PhieuMuon");
        });
        btnSave.addActionListener(e -> {
            int rowCount = table.getRowCount();
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(this, "Bảng trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int c = JOptionPane.showConfirmDialog(this, "Lưu " + rowCount + " dòng vào Database?", "Xác nhận",
                    JOptionPane.YES_NO_OPTION);
            if (c != JOptionPane.YES_OPTION)
                return;
            int ok = 0, fail = 0;
            String maNQL = SessionManager.getInstance().getMaNguoi();
            if (maNQL == null)
                maNQL = "NV00000001";
            for (int i = 0; i < rowCount; i++) {
                try {
                    PhieuMuonDTO pm = new PhieuMuonDTO();
                    pm.setMaPM(val2(i, 0));
                    pm.setMaThe(val2(i, 1));
                    pm.setMaNQL(maNQL);
                    pm.setNgayMuon(val2(i, 2));
                    pm.setHenTra(val2(i, 3));
                    String ngayTra = val2(i, 4);
                    pm.setNgayTra("Chưa trả".equals(ngayTra) ? "" : ngayTra);
                    pm.setTinhTrang(val2(i, 5));
                    if (phieuMuonBUS.insert(pm).contains("thành công"))
                        ok++;
                    else
                        fail++;
                } catch (Exception ex) {
                    fail++;
                }
            }
            JOptionPane.showMessageDialog(this, "Lưu xong!\n- Thành công: " + ok + "\n- Bỏ qua: " + fail);
            loadDataToTable();
        });
    }

    private void loadChiTietPhieu(String maPM) {
        try {
            PhieuMuonDTO pm = phieuMuonBUS.getById(maPM);
            txtMaNQL.setText(pm != null && pm.getMaNQL() != null ? pm.getMaNQL().trim() : "");

            List<ChiTietPhieuMuonDTO> ctList = new BUS.ChiTietPhieuMuonBUS().getByMaPM(maPM);
            if (ctList != null && !ctList.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (ChiTietPhieuMuonDTO ct : ctList) {
                    SachCopyDTO sc = copyBUS.findById(ct.getMaCuonSach());
                    if (sc != null && sc.getTenSachBanSao() != null) {
                        if (sb.length() > 0)
                            sb.append("; ");
                        sb.append(sc.getTenSachBanSao());
                    }
                }
                txtTenSach.setText(sb.toString());
            } else {
                txtTenSach.setText("(Không có chi tiết)");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            txtMaNQL.setText("");
            txtTenSach.setText("");
        }
    }

    private void loadBanSao(String maSach) {
        cbBanSao.removeAllItems();
        dsBanSaoHienTai.clear();
        List<SachCopyDTO> list = copyBUS.getAvailable(maSach);
        for (SachCopyDTO sc : list) {
            dsBanSaoHienTai.add(sc);
            cbBanSao.addItem(sc.getTenSachBanSao() + "  [" + sc.getTinhTrang() + "]");
        }
        if (dsBanSaoHienTai.isEmpty())
            JOptionPane.showMessageDialog(this, "Không có bản sao còn dùng được cho sách: " + maSach,
                    "Không có bản sao", JOptionPane.WARNING_MESSAGE);
    }

    private String getMaVachDaChon() {
        int idx = cbBanSao.getSelectedIndex();
        if (idx < 0 || idx >= dsBanSaoHienTai.size())
            return null;
        return dsBanSaoHienTai.get(idx).getMaVach();
    }

    private void loadDataToTable() {
        model.setRowCount(0);
        LocalDate today = LocalDate.now();
        try {
            for (PhieuMuonDTO pm : phieuMuonBUS.getAll()) {
                String tt = tinhTrangHienThi(pm, today);
                if ("Đã xóa".equals(tt))
                    continue;
                model.addRow(toRow(pm, tt));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void hienThiKetQua(ArrayList<PhieuMuonDTO> ds, String criteria, String keyword, boolean hienDaXoa) {
        model.setRowCount(0);
        LocalDate today = LocalDate.now();
        String kw = keyword.toLowerCase().trim();
        for (PhieuMuonDTO pm : ds) {
            String tt = tinhTrangHienThi(pm, today);
            if ("Đã xóa".equals(tt) && !hienDaXoa)
                continue;
            boolean match = false;
            switch (criteria) {
                case "Mã Phiếu":
                    match = pm.getMaPM() != null && pm.getMaPM().toLowerCase().contains(kw);
                    break;
                case "Mã Độc Giả":
                    match = pm.getMaThe() != null && pm.getMaThe().toLowerCase().contains(kw);
                    break;
                case "Trạng Thái":
                    match = tt.toLowerCase().contains(kw);
                    break;
                default:
                    match = true;
            }
            if (match)
                model.addRow(toRow(pm, tt));
        }
    }

    private Object[] toRow(PhieuMuonDTO pm, String tt) {
        String ngayTra = pm.getNgayTra();
        if (ngayTra == null || ngayTra.isEmpty() || ngayTra.startsWith("1900")) {
            ngayTra = "Chưa trả";
        }
        return new Object[] { pm.getMaPM(), pm.getMaThe(), pm.getNgayMuon(), pm.getHenTra(), ngayTra, tt };
    }

    private String tinhTrangHienThi(PhieuMuonDTO pm, LocalDate today) {
        String tt = pm.getTinhTrang() != null ? pm.getTinhTrang() : "Đang mượn";
        if ("Đang mượn".equals(tt) && pm.getHenTra() != null && !pm.getHenTra().isEmpty()) {
            try {
                if (today.isAfter(LocalDate.parse(pm.getHenTra())))
                    return "Trễ hạn";
            } catch (Exception ignored) {
            }
        }
        return tt;
    }

    private void capNhatMauTrangThai(String tt) {
        switch (tt) {
            case "Trễ hạn":
                txtTrangThai.setBackground(new Color(255, 235, 238));
                txtTrangThai.setForeground(new Color(183, 28, 28));
                break;
            case "Đã trả":
                txtTrangThai.setBackground(new Color(232, 245, 233));
                txtTrangThai.setForeground(new Color(27, 94, 32));
                break;
            case "Đã xóa":
                txtTrangThai.setBackground(new Color(230, 230, 230));
                txtTrangThai.setForeground(new Color(100, 100, 100));
                break;
            default:
                txtTrangThai.setBackground(new Color(227, 242, 253));
                txtTrangThai.setForeground(new Color(13, 71, 161));
                break;
        }
    }

    public void lamMoiForm() {
        txtMaPhieu.setText(phieuMuonBUS.generateMaPM());
        txtMaDG.setText("");
        txtMaThe.setText("");
        txtMaNQL.setText("");
        txtTenSach.setText("");
        txtMaSachLookup.setText("");
        cbBanSao.removeAllItems();
        dsBanSaoHienTai.clear();
        LocalDate today = LocalDate.now();
        txtNgayMuon.setText(today.toString());
        txtHanTra.setText(today.plusDays(14).toString());
        txtNgayTra.setText("");
        txtTrangThai.setText("Đang mượn");
        capNhatMauTrangThai("Đang mượn");
        table.clearSelection();
    }

    private String val(int row, int col) {
        return model.getValueAt(row, col) != null ? model.getValueAt(row, col).toString() : "";
    }

    private String val2(int row, int col) {
        Object v = model.getValueAt(row, col);
        return v != null ? v.toString() : "";
    }

    private JTextField roField(Font f) {
        JTextField tf = new JTextField();
        tf.setFont(f);
        tf.setEditable(false);
        return tf;
    }

    private JLabel lbl(String text, Font font) {
        JLabel l = new JLabel(text);
        l.setFont(font);
        l.setForeground(new Color(73, 80, 87));
        return l;
    }

    private void setupTable(JTable t) {
        t.setRowHeight(32);
        t.setFont(new Font(tenFont, Font.PLAIN, 14));
        t.getTableHeader().setFont(new Font(tenFont, Font.BOLD, 14));
        t.getTableHeader().setBackground(new Color(233, 236, 239));
        t.getTableHeader().setOpaque(false);
        t.setShowGrid(false);
        t.setIntercellSpacing(new Dimension(0, 0));
        t.setShowHorizontalLines(true);
        t.setGridColor(new Color(222, 226, 230));
    }

    private JButton actionBtn(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font(tenFont, Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(160, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        return btn;
    }
}