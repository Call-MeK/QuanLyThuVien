package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import BUS.DocGiaBUS;
import DTO.DocGiaDTO;

public class AdminQuanLyDocGiaPanel extends JPanel {

    private String tenFont = "Segoe UI";
    private Color colorBackground = new Color(248, 249, 250);

    private JTextField txtMaDG, txtHoTen, txtNgaySinh, txtDienThoai, txtEmail, txtNgayDK;
    private JTextField txtTrangThai;
    private JComboBox<String> cbGioiTinh, cbSearchCriteria, cbLoaiDocGia;
    private JTextField txtSearch;
    private JComboBox<String> cbSearchTrangThai;
    private JPanel pnlSearchInput;
    private CardLayout searchInputLayout;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnThem, btnSua, btnKhoa, btnMoKhoa, btnLamMoi, btnSearch, btnResetSearch;
    private JButton btnImport, btnExport, btnSave;
    private DocGiaBUS docGiaBUS = new DocGiaBUS();

    public AdminQuanLyDocGiaPanel() {
        initComponents();
        initEvents();
        lamMoiForm();
        loadDataToTable();
    }

    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBackground(colorBackground);
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // HEADER
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(colorBackground);
        JLabel lblTitle = new JLabel("Quản Lý Thông Tin Độc Giả");
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 24));
        lblTitle.setForeground(new Color(33, 37, 41));
        pnlHeader.add(lblTitle, BorderLayout.WEST);

        btnLamMoi = new JButton("↺");
        btnLamMoi.setFont(new Font(tenFont, Font.BOLD, 18));
        btnLamMoi.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLamMoi.setContentAreaFilled(false);
        btnLamMoi.setBorderPainted(false);
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setToolTipText("Làm mới dữ liệu");
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/Images/refresh.png"));
            Image img = icon.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
            btnLamMoi.setIcon(new ImageIcon(img));
            btnLamMoi.setText("");
        } catch (Exception e) { /* dùng text */ }
        pnlHeader.add(btnLamMoi, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        // PHẦN GIỮA
        JPanel pnlCenter = new JPanel(new BorderLayout(0, 15));
        pnlCenter.setBackground(colorBackground);

        // FORM NHẬP LIỆU (Tăng lên 5 dòng để chứa thêm Loại Độc Giả)
        JPanel pnlInput = new JPanel(new GridLayout(5, 4, 15, 15));
        pnlInput.setBackground(Color.WHITE);
        pnlInput.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        Font fontLabel = new Font(tenFont, Font.BOLD, 14);
        Font fontInput = new Font(tenFont, Font.PLAIN, 14);

        txtMaDG      = new JTextField(); txtMaDG.setFont(fontInput);
        txtHoTen     = new JTextField(); txtHoTen.setFont(fontInput);
        cbGioiTinh   = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"}); cbGioiTinh.setFont(fontInput);
        txtNgaySinh  = new JTextField("2000-01-01"); txtNgaySinh.setFont(fontInput);
        txtDienThoai = new JTextField(); txtDienThoai.setFont(fontInput);
        txtEmail     = new JTextField(); txtEmail.setFont(fontInput);
        txtNgayDK    = new JTextField(java.time.LocalDate.now().toString()); txtNgayDK.setFont(fontInput);
        
        // ComboBox Loại Độc Giả mới
        cbLoaiDocGia = new JComboBox<>(new String[]{"Sinh viên", "Giáo viên", "Khách"}); 
        cbLoaiDocGia.setFont(fontInput);

        txtTrangThai = new JTextField("Đang hoạt động");
        txtTrangThai.setFont(fontInput);
        txtTrangThai.setEditable(false);
        txtTrangThai.setForeground(new Color(25, 135, 84));
        txtTrangThai.setBackground(new Color(233, 236, 239));

        pnlInput.add(createLabel("Mã độc giả:", fontLabel));             pnlInput.add(txtMaDG);
        pnlInput.add(createLabel("Họ và tên:", fontLabel));              pnlInput.add(txtHoTen);
        pnlInput.add(createLabel("Giới tính:", fontLabel));              pnlInput.add(cbGioiTinh);
        pnlInput.add(createLabel("Ngày sinh (YYYY-MM-DD):", fontLabel)); pnlInput.add(txtNgaySinh);
        pnlInput.add(createLabel("Điện thoại:", fontLabel));             pnlInput.add(txtDienThoai);
        pnlInput.add(createLabel("Email:", fontLabel));                  pnlInput.add(txtEmail);
        pnlInput.add(createLabel("Ngày đăng ký:", fontLabel));           pnlInput.add(txtNgayDK);
        pnlInput.add(createLabel("Trạng thái:", fontLabel));             pnlInput.add(txtTrangThai);
        pnlInput.add(createLabel("Loại độc giả:", fontLabel));           pnlInput.add(cbLoaiDocGia);
        // Thêm 2 ô trống để cân đối bố cục Grid
        pnlInput.add(new JLabel()); pnlInput.add(new JLabel());

        // THANH TÌM KIẾM
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlSearch.setBackground(colorBackground);
        pnlSearch.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        txtSearch = new JTextField(20);
        txtSearch.setFont(fontInput);
        txtSearch.setPreferredSize(new Dimension(0, 35));

        cbSearchTrangThai = new JComboBox<>(new String[]{"Đang hoạt động", "Đã khóa"});
        cbSearchTrangThai.setFont(fontInput);
        cbSearchTrangThai.setPreferredSize(new Dimension(200, 35));

        searchInputLayout = new CardLayout();
        pnlSearchInput = new JPanel(searchInputLayout);
        pnlSearchInput.setPreferredSize(new Dimension(200, 35));
        pnlSearchInput.add(txtSearch, "TEXT");
        pnlSearchInput.add(cbSearchTrangThai, "COMBO");
        searchInputLayout.show(pnlSearchInput, "TEXT");

        cbSearchCriteria = new JComboBox<>(new String[]{"Tất cả", "Mã ĐG", "Họ Tên", "Số Điện Thoại", "Email", "Trạng Thái"});
        cbSearchCriteria.setFont(fontInput);
        cbSearchCriteria.setPreferredSize(new Dimension(160, 35));

        btnSearch      = createActionButton("Lọc", new Color(13, 110, 253));
        btnSearch.setPreferredSize(new Dimension(100, 35));
        btnResetSearch = createActionButton("Hủy Lọc", new Color(108, 117, 125));
        btnResetSearch.setPreferredSize(new Dimension(100, 35));

        pnlSearch.add(createLabel("Tìm kiếm:", new Font(tenFont, Font.BOLD, 14)));
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

        // BẢNG - cột 6 = Trạng Thái
        String[] columns = {"Mã ĐG", "Họ Tên", "Giới Tính", "Ngày Sinh", "Điện Thoại", "EMail", "Trạng Thái"};
        model = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        setupTable(table);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    String tt = model.getValueAt(row, 6) != null ? model.getValueAt(row, 6).toString() : "";
                    if (tt.equals("Đã khóa")) {
                        c.setBackground(new Color(255, 235, 238));
                        c.setForeground(new Color(183, 28, 28));
                    } else {
                        c.setBackground(Color.WHITE);
                        c.setForeground(Color.BLACK);
                    }
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);

        // NÚT CHỨC NĂNG
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnlButtons.setBackground(colorBackground);

        btnImport = createActionButton("Import",    new Color(33, 115, 70));
        btnSave   = createActionButton("Save",      new Color(111, 66, 193));
        btnExport = createActionButton("Export",    new Color(33, 115, 70));
        btnThem   = createActionButton("Thêm",      new Color(25, 135, 84));
        btnSua    = createActionButton("Cập Nhật",  new Color(255, 193, 7)); btnSua.setForeground(Color.BLACK);
        btnKhoa   = createActionButton("Khóa Thẻ", new Color(220, 53, 69));
        
        // Đổi tên nút thành Mở Thẻ theo yêu cầu
        btnMoKhoa = createActionButton("Mở Thẻ",  new Color(13, 110, 253));

        pnlButtons.add(btnImport);
        pnlButtons.add(btnSave);
        pnlButtons.add(btnExport);
        pnlButtons.add(btnThem);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnKhoa);
        pnlButtons.add(btnMoKhoa);
        add(pnlButtons, BorderLayout.SOUTH);
    }

    private void initEvents() {

        // Click bảng -> đổ dữ liệu lên form
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtMaDG.setText(model.getValueAt(row, 0) != null ? model.getValueAt(row, 0).toString() : "");
                    txtMaDG.setEditable(false);
                    txtHoTen.setText(model.getValueAt(row, 1) != null ? model.getValueAt(row, 1).toString() : "");
                    cbGioiTinh.setSelectedItem(model.getValueAt(row, 2) != null ? model.getValueAt(row, 2).toString() : "Khác");
                    txtNgaySinh.setText(model.getValueAt(row, 3) != null ? model.getValueAt(row, 3).toString() : "");
                    txtDienThoai.setText(model.getValueAt(row, 4) != null ? model.getValueAt(row, 4).toString() : "");
                    txtEmail.setText(model.getValueAt(row, 5) != null ? model.getValueAt(row, 5).toString() : "");

                    String trangThai = model.getValueAt(row, 6) != null ? model.getValueAt(row, 6).toString() : "";
                    txtTrangThai.setText(trangThai);

                    if (trangThai.equals("Đã khóa")) {
                        txtTrangThai.setForeground(new Color(183, 28, 28));
                        txtTrangThai.setBackground(new Color(255, 235, 238));
                    } else {
                        txtTrangThai.setForeground(new Color(25, 135, 84));
                        txtTrangThai.setBackground(new Color(233, 236, 239));
                    }

                    String maDG = txtMaDG.getText();
                    for (DocGiaDTO dg : docGiaBUS.getListDocGia()) {
                        if (dg.getMaDocGia().trim().equals(maDG.trim())) {
                            txtNgayDK.setText(dg.getNgayDangKi() != null ? dg.getNgayDangKi() : "");
                            // Cập nhật Loại độc giả từ DB lên form
                            if(dg.getLoaiDocGia() != null && !dg.getLoaiDocGia().isEmpty()){
                                cbLoaiDocGia.setSelectedItem(dg.getLoaiDocGia());
                            }
                            break;
                        }
                    }
                }
            }
        });

        btnLamMoi.addActionListener(e -> {
            lamMoiForm();
            docGiaBUS.reloadFromDB();
            loadDataToTable();
        });

        btnThem.addActionListener(e -> {
            if (!validateInput("")) return;
            String maNQL = "NV00000001";
            try {
                if (BUS.SessionManager.getInstance() != null && BUS.SessionManager.getInstance().getMaNguoi() != null)
                    maNQL = BUS.SessionManager.getInstance().getMaNguoi();
            } catch (Exception ex) { }

            DocGiaDTO dg = createDocGiaFromForm();
            String result = docGiaBUS.addDocGia(dg, maNQL);
            if (result.equals("Thêm thành công")) {
                JOptionPane.showMessageDialog(this,
                        "Thêm độc giả thành công!\n\nTài khoản đăng nhập:\n- User: "
                        + dg.getMaDocGia() + "\n- Pass: 12345");
                docGiaBUS.reloadFromDB(); loadDataToTable(); lamMoiForm();
            } else {
                JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSua.addActionListener(e -> {
            String maDG = txtMaDG.getText().trim();
            if (maDG.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 độc giả trong bảng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE); return;
            }
            if (docGiaBUS.isDocGiaLocked(maDG)) {
                JOptionPane.showMessageDialog(this, "Độc giả đang bị khóa! Mở khóa trước.", "Từ chối", JOptionPane.WARNING_MESSAGE); return;
            }
            if (!validateInput(maDG)) return;
            String result = docGiaBUS.updateDocGia(createDocGiaFromForm());
            if (result.equals("Cập nhật thành công")) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnKhoa.addActionListener(e -> {
            String maDG = txtMaDG.getText().trim();
            if (maDG.isEmpty()) { JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 độc giả!", "Cảnh báo", JOptionPane.WARNING_MESSAGE); return; }
            if (docGiaBUS.isDocGiaLocked(maDG)) { JOptionPane.showMessageDialog(this, "Độc giả đã bị khóa rồi!"); return; }
            int c = JOptionPane.showConfirmDialog(this, "Xác nhận khóa thẻ " + maDG + "?", "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (c == JOptionPane.YES_OPTION) {
                String result = docGiaBUS.softDeleteDocGia(maDG);
                if (result.equals("Khóa thẻ thành công")) {
                    JOptionPane.showMessageDialog(this, "Đã khóa thẻ " + maDG);
                    docGiaBUS.reloadFromDB(); loadDataToTable(); lamMoiForm();
                } else { JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE); }
            }
        });

        // Đổi thông báo để khớp với nút "Mở Thẻ"
        btnMoKhoa.addActionListener(e -> {
            String maDG = txtMaDG.getText().trim();
            if (maDG.isEmpty()) { JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 độc giả!", "Cảnh báo", JOptionPane.WARNING_MESSAGE); return; }
            if (!docGiaBUS.isDocGiaLocked(maDG)) { JOptionPane.showMessageDialog(this, "Thẻ của độc giả chưa bị khóa!"); return; }
            int c = JOptionPane.showConfirmDialog(this, "Xác nhận mở thẻ cho độc giả " + maDG + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (c == JOptionPane.YES_OPTION) {
                String result = docGiaBUS.restoreDocGia(maDG);
                if (result.equals("Mở khóa thành công")) {
                    JOptionPane.showMessageDialog(this, "Đã mở thẻ thành công cho " + maDG);
                    docGiaBUS.reloadFromDB(); loadDataToTable(); lamMoiForm();
                } else { JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE); }
            }
        });

        cbSearchCriteria.addActionListener(e -> {
            if ("Trạng Thái".equals(cbSearchCriteria.getSelectedItem()))
                searchInputLayout.show(pnlSearchInput, "COMBO");
            else
                searchInputLayout.show(pnlSearchInput, "TEXT");
        });

        btnSearch.addActionListener(e -> {
            String criteria = (String) cbSearchCriteria.getSelectedItem();
            String keyword = "Trạng Thái".equals(criteria)
                    ? (String) cbSearchTrangThai.getSelectedItem()
                    : txtSearch.getText();
            hienThiKetQua(docGiaBUS.search(keyword, criteria));
        });

        btnResetSearch.addActionListener(e -> {
            txtSearch.setText("");
            cbSearchCriteria.setSelectedIndex(0);
            cbSearchTrangThai.setSelectedIndex(0);
            searchInputLayout.show(pnlSearchInput, "TEXT");
            loadDataToTable();
        });

        btnImport.addActionListener(e -> Utils.ExcelImporter.importExcelToTable(table, model));

        btnExport.addActionListener(e -> {
            if (model.getRowCount() == 0) { JOptionPane.showMessageDialog(this, "Không có dữ liệu!", "Cảnh báo", JOptionPane.WARNING_MESSAGE); return; }
            Utils.ExcelExporter.exportTableToExcel(table, "DanhSach_DocGia");
        });

        btnSave.addActionListener(e -> {
            int rowCount = table.getRowCount();
            if (rowCount == 0) { JOptionPane.showMessageDialog(this, "Bảng trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE); return; }
            int c = JOptionPane.showConfirmDialog(this, "Lưu " + rowCount + " dòng vào Database?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (c == JOptionPane.YES_OPTION) {
                int ok = 0, fail = 0;
                String maNQL = BUS.SessionManager.getInstance().getMaNguoi();
                if (maNQL == null || maNQL.isEmpty()) maNQL = "NV00000001";
                for (int i = 0; i < rowCount; i++) {
                    DocGiaDTO dg = new DocGiaDTO();
                    dg.setMaDocGia(model.getValueAt(i, 0).toString().trim());
                    dg.setHoTen(model.getValueAt(i, 1).toString().trim());
                    dg.setGioiTinh(model.getValueAt(i, 2).toString().trim());
                    dg.setNgaySinh(model.getValueAt(i, 3).toString().trim());
                    dg.setSoDienThoai(model.getValueAt(i, 4).toString().trim());
                    dg.setEmail(model.getValueAt(i, 5) != null ? model.getValueAt(i, 5).toString().trim() : "");
                    dg.setMaNguoi(dg.getMaDocGia());
                    dg.setTenDangNhap(dg.getMaDocGia());
                    dg.setMatKhau("12345");
                    dg.setTrangThai("Đang hoạt động");
                    dg.setIsDeleted(false);
                    // Dữ liệu import hàng loạt sẽ có Loại mặc định là Sinh viên
                    dg.setLoaiDocGia("Sinh viên");
                    dg.setNgayDangKi(java.time.LocalDate.now().toString());
                    if (docGiaBUS.addDocGia(dg, maNQL).equals("Thêm thành công")) ok++; else fail++;
                }
                JOptionPane.showMessageDialog(this, "Lưu xong!\n- Thành công: " + ok + "\n- Bỏ qua: " + fail);
                docGiaBUS.reloadFromDB(); loadDataToTable();
            }
        });
    }

    private void loadDataToTable() {
        ArrayList<DocGiaDTO> list = docGiaBUS.getListDocGia();
        if (list != null) hienThiKetQua(list);
    }

    private void hienThiKetQua(ArrayList<DocGiaDTO> danhSach) {
        model.setRowCount(0);
        for (DocGiaDTO dg : danhSach) {
            boolean biKhoa = (dg.getIsDeleted() != null && dg.getIsDeleted())
                    || "Bị khóa".equalsIgnoreCase(dg.getTrangThai())
                    || "Đã khóa".equalsIgnoreCase(dg.getTrangThai());
            String trangThai = biKhoa ? "Đã khóa" : "Đang hoạt động";
            model.addRow(new Object[]{
                dg.getMaDocGia(), dg.getHoTen(), dg.getGioiTinh(),
                dg.getNgaySinh(), dg.getSoDienThoai(),
                dg.getEmail(),
                trangThai
            });
        }
    }

    private DocGiaDTO createDocGiaFromForm() {
        DocGiaDTO dg = new DocGiaDTO();
        dg.setMaDocGia(txtMaDG.getText().trim());
        dg.setMaNguoi(txtMaDG.getText().trim());
        dg.setHoTen(txtHoTen.getText().trim());
        dg.setGioiTinh(cbGioiTinh.getSelectedItem().toString());
        dg.setNgaySinh(txtNgaySinh.getText().trim());
        dg.setSoDienThoai(txtDienThoai.getText().trim());
        dg.setEmail(txtEmail.getText().trim());
        dg.setNgayDangKi(txtNgayDK.getText().trim());
        dg.setTrangThai("Đang hoạt động");
        dg.setTenDangNhap(txtMaDG.getText().trim());
        dg.setMatKhau("12345");
        dg.setIsDeleted(false);
        // Lấy loại độc giả thay vì để mặc định "Thường"
        dg.setLoaiDocGia(cbLoaiDocGia.getSelectedItem().toString());
        dg.setNgayXoa("");
        dg.setDiaChi("");
        return dg;
    }

    public void lamMoiForm() {
        txtMaDG.setText(docGiaBUS.generateMaDocGia());
        txtMaDG.setEditable(false);
        txtHoTen.setText("");
        txtNgaySinh.setText("2000-01-01");
        txtDienThoai.setText("");
        txtEmail.setText("");
        txtNgayDK.setText(java.time.LocalDate.now().toString());
        txtNgayDK.setEditable(false);
        cbGioiTinh.setSelectedIndex(0);
        cbLoaiDocGia.setSelectedIndex(0); // Làm mới Loại Độc Giả
        txtSearch.setText("");
        cbSearchTrangThai.setSelectedIndex(0);
        table.clearSelection();
        txtTrangThai.setText("Đang hoạt động");
        txtTrangThai.setForeground(new Color(25, 135, 84));
        txtTrangThai.setBackground(new Color(233, 236, 239));
    }

    private JLabel createLabel(String text, Font font) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(font); lbl.setForeground(new Color(73, 80, 87)); return lbl;
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

    private JButton createActionButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font(tenFont, Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.setBorderPainted(false); btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(130, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true); return btn;
    }

    private boolean validateInput(String maDG) {
        String sdt   = txtDienThoai.getText().trim();
        String email = txtEmail.getText().trim();
        String hoTen = txtHoTen.getText().trim();
        if (hoTen.isEmpty() || sdt.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ Họ tên, Số điện thoại và Email!", "Cảnh báo", JOptionPane.WARNING_MESSAGE); return false;
        }
        if (!sdt.matches("^0\\d{9}$")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ! (10 số, bắt đầu bằng 0)", "Cảnh báo", JOptionPane.WARNING_MESSAGE); return false;
        }
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE); return false;
        }
        if (docGiaBUS.checkDuplicatePhone(sdt, maDG)) {
            JOptionPane.showMessageDialog(this, "Số điện thoại đã được đăng ký cho độc giả khác!", "Cảnh báo", JOptionPane.WARNING_MESSAGE); return false;
        }
        if (docGiaBUS.checkDuplicateEmail(email, maDG)) {
            JOptionPane.showMessageDialog(this, "Email đã được đăng ký cho độc giả khác!", "Cảnh báo", JOptionPane.WARNING_MESSAGE); return false;
        }
        return true;
    }
}