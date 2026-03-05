package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import BUS.*;
import DTO.*;

public class AdminQuanLyMuonTraPanel extends JPanel {

    private String tenFont = "Segoe UI";
    private Color colorBackground = new Color(248, 249, 250);

    private JTextField txtMaPhieu, txtMaDG, txtMaThe, txtMaSach, txtNgayMuon, txtHanTra, txtNgayTra, txtTrangThai;
    private JComboBox<String> cbSearchCriteria;
    private JComboBox<String> cbSearchTrangThai;
    private JTextField txtSearch;
    private JPanel pnlSearchInput;
    private CardLayout searchInputLayout;
    private JTable table;
    private DefaultTableModel model;
    
    private JButton btnThem, btnTra, btnXoa, btnLamMoi, btnSearch, btnResetSearch;
    private JButton btnImport, btnExport, btnSave;

    private PhieuMuonBUS phieuMuonBUS       = new PhieuMuonBUS();
    private TheThuVienBUS theThuVienBUS     = new TheThuVienBUS();
    private DocGiaBUS docGiaBUS             = new DocGiaBUS();

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

        btnLamMoi = new JButton();
        btnLamMoi.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLamMoi.setContentAreaFilled(false); 
        btnLamMoi.setBorderPainted(false);
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setToolTipText("Làm mới dữ liệu (F5)"); 
        btnLamMoi.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/Images/refresh.png"));
            Image img = icon.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
            btnLamMoi.setIcon(new ImageIcon(img));
            btnLamMoi.setPressedIcon(new ImageIcon(img.getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
            btnLamMoi.setRolloverEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pnlHeader.add(btnLamMoi, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        JPanel pnlCenter = new JPanel(new BorderLayout(0, 15));
        pnlCenter.setBackground(colorBackground);

        // --- FORM NHẬP LIỆU (4x4 = 16 ô) ---
        JPanel pnlInput = new JPanel(new GridLayout(4, 4, 15, 15));
        pnlInput.setBackground(Color.WHITE);
        pnlInput.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        Font fontLabel = new Font(tenFont, Font.BOLD, 14);
        Font fontInput = new Font(tenFont, Font.PLAIN, 14);

        txtMaPhieu  = new JTextField(); txtMaPhieu.setFont(fontInput);  txtMaPhieu.setEditable(false);
        txtMaDG     = new JTextField(); txtMaDG.setFont(fontInput);     // Admin nhập MaDocGia
        txtMaThe    = new JTextField(); txtMaThe.setFont(fontInput);    txtMaThe.setEditable(false); // Tự tìm từ MaDG
        txtMaThe.setBackground(new Color(233, 236, 239));
        txtMaSach   = new JTextField(); txtMaSach.setFont(fontInput);
        txtNgayMuon = new JTextField(); txtNgayMuon.setFont(fontInput); txtNgayMuon.setEditable(false);
        txtHanTra   = new JTextField(); txtHanTra.setFont(fontInput);   txtHanTra.setEditable(false);
        txtNgayTra  = new JTextField(); txtNgayTra.setFont(fontInput);  txtNgayTra.setEditable(false);

        txtTrangThai = new JTextField("Đang mượn");
        txtTrangThai.setFont(fontInput);
        txtTrangThai.setEditable(false);
        txtTrangThai.setBackground(new Color(227, 242, 253));
        txtTrangThai.setForeground(new Color(13, 71, 161));

        pnlInput.add(createLabel("Mã phiếu mượn:", fontLabel));  pnlInput.add(txtMaPhieu);
        pnlInput.add(createLabel("Mã độc giả:", fontLabel));     pnlInput.add(txtMaDG);
        pnlInput.add(createLabel("Mã thẻ (tự tìm):", fontLabel));pnlInput.add(txtMaThe);
        pnlInput.add(createLabel("Mã cuốn sách:", fontLabel));   pnlInput.add(txtMaSach);
        pnlInput.add(createLabel("Ngày mượn:", fontLabel));      pnlInput.add(txtNgayMuon);
        pnlInput.add(createLabel("Hạn trả:", fontLabel));        pnlInput.add(txtHanTra);
        pnlInput.add(createLabel("Ngày trả thực:", fontLabel));  pnlInput.add(txtNgayTra);
        pnlInput.add(createLabel("Trạng thái:", fontLabel));     pnlInput.add(txtTrangThai);

        // --- THANH TÌM KIẾM ---
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlSearch.setBackground(colorBackground);
        pnlSearch.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        txtSearch = new JTextField(20);
        txtSearch.setFont(fontInput);
        txtSearch.setPreferredSize(new Dimension(0, 35));

        cbSearchTrangThai = new JComboBox<>(new String[]{"Đang mượn", "Đã trả", "Trễ hạn", "Đã xóa"});
        cbSearchTrangThai.setFont(fontInput);
        cbSearchTrangThai.setPreferredSize(new Dimension(160, 35));

        searchInputLayout = new CardLayout();
        pnlSearchInput = new JPanel(searchInputLayout);
        pnlSearchInput.setPreferredSize(new Dimension(200, 35));
        pnlSearchInput.add(txtSearch, "TEXT");
        pnlSearchInput.add(cbSearchTrangThai, "COMBO");
        searchInputLayout.show(pnlSearchInput, "TEXT");

        cbSearchCriteria = new JComboBox<>(new String[]{"Tất cả", "Mã Phiếu", "Mã Độc Giả", "Trạng Thái"});
        cbSearchCriteria.setFont(fontInput);
        cbSearchCriteria.setPreferredSize(new Dimension(140, 35));

        btnSearch      = createActionButton("Lọc", new Color(13, 110, 253));
        btnSearch.setPreferredSize(new Dimension(100, 35));
        btnResetSearch = createActionButton("Hủy Lọc", new Color(108, 117, 125));
        btnResetSearch.setPreferredSize(new Dimension(100, 35));

        pnlSearch.add(createLabel("🔍 Tra cứu:", new Font(tenFont, Font.BOLD, 14)));
        pnlSearch.add(pnlSearchInput);
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
        String[] columns = {"Mã Phiếu Mượn", "Mã Thẻ (ĐG)", "Ngày Mượn", "Hạn Trả", "Ngày Trả", "Trạng Thái"};
        model = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        setupTable(table);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    String tt = model.getValueAt(row, 5) != null ? model.getValueAt(row, 5).toString() : "";
                    switch (tt) {
                        case "Trễ hạn":
                            c.setBackground(new Color(255, 235, 238)); c.setForeground(new Color(183, 28, 28)); break;
                        case "Đã trả":
                            c.setBackground(new Color(232, 245, 233)); c.setForeground(new Color(27, 94, 32)); break;
                        case "Đã xóa":
                            c.setBackground(new Color(230, 230, 230)); c.setForeground(new Color(100, 100, 100)); break;
                        default:
                            c.setBackground(Color.WHITE); c.setForeground(Color.BLACK); break;
                    }
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);

        // --- NÚT CHỨC NĂNG ---
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        pnlButtons.setBackground(colorBackground);

        btnThem   = createActionButton("Lập Phiếu", new Color(25, 135, 84));
        btnTra    = createActionButton("Xác Nhận Trả", new Color(13, 110, 253));
        btnXoa    = createActionButton("Xóa Phiếu", new Color(220, 53, 69));
        
        btnImport = createActionButton("Import", new Color(33, 115, 70)); 
        btnSave = createActionButton("Save", new Color(111, 66, 193));
        btnExport = createActionButton("Export", new Color(33, 115, 70));

        pnlButtons.add(btnImport); 
        pnlButtons.add(btnSave);
        pnlButtons.add(btnExport);
        pnlButtons.add(btnThem); 
        pnlButtons.add(btnTra); 
        pnlButtons.add(btnXoa); 
        
        add(pnlButtons, BorderLayout.SOUTH);
    }

    // ==========================================================
    // SỰ KIỆN
    // ==========================================================
    private void initEvents() {

        // 1. Click bảng -> đổ dữ liệu lên form
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    String maPM     = val(row, 0);
                    String maThe    = val(row, 1);
                    String ngayMuon = val(row, 2);
                    String hanTra   = val(row, 3);
                    String ngayTra  = val(row, 4);
                    String tt       = val(row, 5);

                    txtMaPhieu.setText(maPM);
                    txtMaThe.setText(maThe);
                    txtNgayMuon.setText(ngayMuon);
                    txtHanTra.setText(hanTra);
                    txtNgayTra.setText(ngayTra.equals("Chưa trả") ? "" : ngayTra);
                    txtMaSach.setText("");
                    txtTrangThai.setText(tt);
                    capNhatMauTrangThai(tt);

                  
                    TheThuVienDTO the = theThuVienBUS.getById(maThe);
                    txtMaDG.setText(the != null ? the.getMaDocGia() : "");
                }
            }
        });

        // 2. Khi admin nhập xong MaDocGia -> tự động tìm MaThe
        txtMaDG.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                String maDG = txtMaDG.getText().trim();
                if (!maDG.isEmpty()) {
                    TheThuVienDTO the = theThuVienBUS.getByMaDocGia(maDG);
                    if (the != null) {
                        txtMaThe.setText(the.getMaThe());
                    } else {
                        txtMaThe.setText("");
                        JOptionPane.showMessageDialog(AdminQuanLyMuonTraPanel.this,
                                "Không tìm thấy thẻ thư viện cho độc giả: " + maDG,
                                "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        // 3. Đổi tiêu chí tìm kiếm
        cbSearchCriteria.addActionListener(e -> {
            if ("Trạng Thái".equals(cbSearchCriteria.getSelectedItem())) {
                searchInputLayout.show(pnlSearchInput, "COMBO");
            } else {
                searchInputLayout.show(pnlSearchInput, "TEXT");
            }
        });

        // 4. LÀM MỚI (Nút Icon trên cùng)
        btnLamMoi.addActionListener(e -> { lamMoiForm(); loadDataToTable(); });

        // 5. LẬP PHIẾU MỚI
        btnThem.addActionListener(e -> {
            String maDG   = txtMaDG.getText().trim();
            String maThe  = txtMaThe.getText().trim();
            String maSach = txtMaSach.getText().trim();

            if (maDG.isEmpty() || maSach.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập Mã độc giả và Mã cuốn sách!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (maThe.isEmpty()) {
                TheThuVienDTO the = theThuVienBUS.getByMaDocGia(maDG);
                if (the == null) {
                    JOptionPane.showMessageDialog(this,
                            "Không tìm thấy thẻ thư viện cho độc giả: " + maDG + "\nKhông thể lập phiếu mượn!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                maThe = the.getMaThe();
                txtMaThe.setText(maThe);
            }

            if (docGiaBUS.isDocGiaLocked(maDG)) {
                JOptionPane.showMessageDialog(this,
                        "Độc giả " + maDG + " đang bị khóa thẻ!\n"
                        + "Không thể lập phiếu mượn.\n"
                        + "Vui lòng mở khóa thẻ trước trong 'Quản Lý Độc Giả'.",
                        "Từ chối lập phiếu", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String maNQL = SessionManager.getInstance().getMaNguoi();
            if (maNQL == null || maNQL.isEmpty()) maNQL = "NV00000001";

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
                    ChiTietPhieuMuonDTO ctpm = new ChiTietPhieuMuonDTO();
                    ctpm.setMaPM(pm.getMaPM());
                    ctpm.setMaCuonSach(maSach);
                    ctpm.setTinhTrangSach("Bình thường");
                    new ChiTietPhieuMuonBUS().add(ctpm);
                } catch (Exception ex) { ex.printStackTrace(); }

                JOptionPane.showMessageDialog(this,
                        "Lập phiếu mượn thành công!\n"
                        + "Mã phiếu: " + pm.getMaPM() + "\n"
                        + "Mã thẻ: " + pm.getMaThe() + "\n"
                        + "Hạn trả: " + pm.getHenTra());
                lamMoiForm();
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 6. XÁC NHẬN TRẢ
        btnTra.addActionListener(e -> {
            String maPM = txtMaPhieu.getText().trim();
            if (maPM.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 phiếu mượn trong bảng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String tt = txtTrangThai.getText();
            if ("Đã trả".equals(tt)) {
                JOptionPane.showMessageDialog(this, "Phiếu này đã được trả rồi!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if ("Đã xóa".equals(tt)) {
                JOptionPane.showMessageDialog(this, "Phiếu này đã bị xóa, không thể cập nhật!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Xác nhận độc giả đã trả sách cho phiếu " + maPM + "?",
                    "Xác nhận trả sách", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                PhieuMuonDTO pm = phieuMuonBUS.getById(maPM);
                if (pm == null) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu mượn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                pm.setNgayTra(LocalDate.now().toString());
                pm.setTinhTrang("Đã trả");
                String result = phieuMuonBUS.update(pm);
                if (result.contains("thành công")) {
                    JOptionPane.showMessageDialog(this, "Đã xác nhận trả sách thành công!");
                    lamMoiForm(); loadDataToTable();
                } else {
                    JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 7. XÓA PHIẾU (soft delete)
        btnXoa.addActionListener(e -> {
            String maPM = txtMaPhieu.getText().trim();
            if (maPM.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 phiếu mượn trong bảng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String tt = txtTrangThai.getText();
            if ("Đã xóa".equals(tt)) {
                JOptionPane.showMessageDialog(this, "Phiếu này đã bị xóa rồi!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if ("Đang mượn".equals(tt) || "Trễ hạn".equals(tt)) {
                JOptionPane.showMessageDialog(this,
                        "Không thể xóa phiếu đang mượn!\nVui lòng xác nhận trả sách trước.",
                        "Từ chối xóa", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Xóa phiếu mượn " + maPM + "?\n"
                    + "Phiếu sẽ bị ẩn, admin có thể xem lại bằng cách lọc 'Trạng Thái → Đã xóa'.",
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                PhieuMuonDTO pm = phieuMuonBUS.getById(maPM);
                if (pm == null) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu mượn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                pm.setTinhTrang("Đã xóa");
                String result = phieuMuonBUS.update(pm);
                if (result.contains("thành công")) {
                    JOptionPane.showMessageDialog(this, "Đã ẩn phiếu mượn " + maPM);
                    lamMoiForm(); loadDataToTable();
                } else {
                    JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 8. LỌC
        btnSearch.addActionListener(e -> {
            String criteria = (String) cbSearchCriteria.getSelectedItem();
            String keyword  = "Trạng Thái".equals(criteria)
                    ? (String) cbSearchTrangThai.getSelectedItem()
                    : txtSearch.getText().trim();
            boolean hienDaXoa = "Trạng Thái".equals(criteria) && "Đã xóa".equals(keyword);
            hienThiKetQua(phieuMuonBUS.search(keyword), criteria, keyword, hienDaXoa);
        });

        // 9. HỦY LỌC
        btnResetSearch.addActionListener(e -> {
            txtSearch.setText("");
            cbSearchCriteria.setSelectedIndex(0);
            cbSearchTrangThai.setSelectedIndex(0);
            searchInputLayout.show(pnlSearchInput, "TEXT");
            loadDataToTable();
        });
        
        // 10. Nút IMPORT EXCEL
        btnImport.addActionListener(e -> {
            Utils.ExcelImporter.importExcelToTable(table, model);
        });

        // 11. Nút EXPORT EXCEL
        btnExport.addActionListener(e -> {
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Utils.ExcelExporter.exportTableToExcel(table, "DanhSach_PhieuMuon");
        });
        
        // 12. Nút SAVE 
        btnSave.addActionListener(e -> {
            int rowCount = table.getRowCount();
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(this, "Bảng đang trống, không có dữ liệu để lưu!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, 
                    "Lưu toàn bộ " + rowCount + " dòng trên bảng vào Database?", 
                    "Xác nhận", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                int successCount = 0;
                int failCount = 0;
                
                String maNQL = BUS.SessionManager.getInstance().getMaNguoi();
                if (maNQL == null || maNQL.isEmpty()) maNQL = "NV00000001";

                for (int i = 0; i < rowCount; i++) {
                    PhieuMuonDTO pm = new PhieuMuonDTO();
                    
                    pm.setMaPM(model.getValueAt(i, 0).toString().trim());
                    pm.setMaThe(model.getValueAt(i, 1).toString().trim());
                    pm.setMaNQL(maNQL); 
                    pm.setNgayMuon(model.getValueAt(i, 2).toString().trim());
                    pm.setHenTra(model.getValueAt(i, 3).toString().trim());
                    
                    String ngayTra = model.getValueAt(i, 4).toString().trim();
                    pm.setNgayTra(ngayTra.equals("Chưa trả") ? "" : ngayTra);
                    
                    pm.setTinhTrang(model.getValueAt(i, 5).toString().trim());

                    String result = phieuMuonBUS.insert(pm);
                    if (result.contains("thành công")) {
                        successCount++;
                    } else {
                        failCount++; 
                    }
                }

                JOptionPane.showMessageDialog(this, 
                        "Lưu hoàn tất!\n- Thành công: " + successCount + " phiếu\n- Bỏ qua (Trùng mã/Lỗi): " + failCount + " phiếu", 
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                
                phieuMuonBUS.getAll(); 
                loadDataToTable();
            }
        });
    }

    // ==========================================================
    // HÀM HỖ TRỢ
    // ==========================================================

    private void loadDataToTable() {
        model.setRowCount(0);
        LocalDate today = LocalDate.now();
        try {
            for (PhieuMuonDTO pm : phieuMuonBUS.getAll()) {
                String tt = tinhTrangHienThi(pm, today);
                if ("Đã xóa".equals(tt)) continue;
                model.addRow(toRow(pm, tt));
            }
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void hienThiKetQua(ArrayList<PhieuMuonDTO> danhSach, String criteria, String keyword, boolean hienDaXoa) {
        model.setRowCount(0);
        LocalDate today = LocalDate.now();
        String kw = keyword.toLowerCase().trim();

        for (PhieuMuonDTO pm : danhSach) {
            String tt = tinhTrangHienThi(pm, today);
            if ("Đã xóa".equals(tt) && !hienDaXoa) continue;

            boolean match;
            switch (criteria) {
                case "Mã Phiếu":   match = pm.getMaPM() != null && pm.getMaPM().toLowerCase().contains(kw); break;
                case "Mã Độc Giả": match = pm.getMaThe() != null && pm.getMaThe().toLowerCase().contains(kw); break;
                case "Trạng Thái": match = tt.toLowerCase().contains(kw); break;
                default:           match = true; break;
            }
            if (match) model.addRow(toRow(pm, tt));
        }
    }

    private Object[] toRow(PhieuMuonDTO pm, String tt) {
        return new Object[]{
            pm.getMaPM(), pm.getMaThe(), pm.getNgayMuon(), pm.getHenTra(),
            (pm.getNgayTra() == null || pm.getNgayTra().isEmpty()) ? "Chưa trả" : pm.getNgayTra(),
            tt
        };
    }

    private String tinhTrangHienThi(PhieuMuonDTO pm, LocalDate today) {
        String tt = pm.getTinhTrang() != null ? pm.getTinhTrang() : "Đang mượn";
        if ("Đang mượn".equals(tt) && pm.getHenTra() != null && !pm.getHenTra().isEmpty()) {
            try { if (today.isAfter(LocalDate.parse(pm.getHenTra()))) return "Trễ hạn"; }
            catch (Exception ignored) {}
        }
        return tt;
    }

    private void capNhatMauTrangThai(String tt) {
        switch (tt) {
            case "Trễ hạn":
                txtTrangThai.setBackground(new Color(255, 235, 238)); txtTrangThai.setForeground(new Color(183, 28, 28)); break;
            case "Đã trả":
                txtTrangThai.setBackground(new Color(232, 245, 233)); txtTrangThai.setForeground(new Color(27, 94, 32)); break;
            case "Đã xóa":
                txtTrangThai.setBackground(new Color(230, 230, 230)); txtTrangThai.setForeground(new Color(100, 100, 100)); break;
            default:
                txtTrangThai.setBackground(new Color(227, 242, 253)); txtTrangThai.setForeground(new Color(13, 71, 161)); break;
        }
    }

    private String val(int row, int col) {
        return model.getValueAt(row, col) != null ? model.getValueAt(row, col).toString() : "";
    }

    public void lamMoiForm() {
        txtMaPhieu.setText(phieuMuonBUS.generateMaPM());
        txtMaDG.setText("");
        txtMaThe.setText("");
        txtMaSach.setText("");
        LocalDate today = LocalDate.now();
        txtNgayMuon.setText(today.toString());
        txtHanTra.setText(today.plusDays(14).toString());
        txtNgayTra.setText("");
        txtTrangThai.setText("Đang mượn");
        capNhatMauTrangThai("Đang mượn");
        table.clearSelection();
        theThuVienBUS.getAll(); 
    }

    // ===== TIỆN ÍCH UI =====
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
        btn.setPreferredSize(new Dimension(160, 40)); btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true); return btn;
    }
}