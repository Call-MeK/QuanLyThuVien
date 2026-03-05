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
    private JComboBox<String> cbGioiTinh, cbTrangThai, cbSearchCriteria;
    private JTextField txtSearch;
    private JComboBox<String> cbSearchTrangThai;
    private JPanel pnlSearchInput;
    private CardLayout searchInputLayout;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnThem, btnSua, btnKhoa, btnMoKhoa, btnXoa, btnLamMoi, btnSearch, btnResetSearch;

    private DocGiaBUS docGiaBUS = new DocGiaBUS();

    public AdminQuanLyDocGiaPanel() {
        initComponents();
        initEvents();
        loadDataToTable();
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
        txtNgaySinh = new JTextField("2000-01-01"); txtNgaySinh.setFont(fontInput);
        txtDienThoai = new JTextField(); txtDienThoai.setFont(fontInput);
        txtEmail = new JTextField(); txtEmail.setFont(fontInput);
        txtNgayDK = new JTextField(java.time.LocalDate.now().toString()); txtNgayDK.setFont(fontInput);
        cbTrangThai = new JComboBox<>(new String[]{"Đang hoạt động", "Hết hạn"});
        cbTrangThai.setFont(fontInput);

        pnlInput.add(createLabel("Mã độc giả:", fontLabel)); pnlInput.add(txtMaDG);
        pnlInput.add(createLabel("Họ và tên:", fontLabel)); pnlInput.add(txtHoTen);
        pnlInput.add(createLabel("Giới tính:", fontLabel)); pnlInput.add(cbGioiTinh);
        pnlInput.add(createLabel("Ngày sinh (YYYY-MM-DD):", fontLabel)); pnlInput.add(txtNgaySinh);
        pnlInput.add(createLabel("Điện thoại:", fontLabel)); pnlInput.add(txtDienThoai);
        pnlInput.add(createLabel("Email:", fontLabel)); pnlInput.add(txtEmail);
        pnlInput.add(createLabel("Ngày đăng ký:", fontLabel)); pnlInput.add(txtNgayDK);
        pnlInput.add(createLabel("Trạng thái:", fontLabel)); pnlInput.add(cbTrangThai);

        // --- THANH TÌM KIẾM ---
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlSearch.setBackground(colorBackground);
        pnlSearch.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        txtSearch = new JTextField(20); txtSearch.setFont(fontInput); txtSearch.setPreferredSize(new Dimension(0, 35));

        cbSearchTrangThai = new JComboBox<>(new String[]{"Đang hoạt động", "Hết hạn", "Đã khóa"});
        cbSearchTrangThai.setFont(fontInput);
        cbSearchTrangThai.setPreferredSize(new Dimension(200, 35));

        searchInputLayout = new CardLayout();
        pnlSearchInput = new JPanel(searchInputLayout);
        pnlSearchInput.setPreferredSize(new Dimension(200, 35));
        pnlSearchInput.add(txtSearch, "TEXT");
        pnlSearchInput.add(cbSearchTrangThai, "COMBO");
        searchInputLayout.show(pnlSearchInput, "TEXT");

        cbSearchCriteria = new JComboBox<>(new String[]{"Tất cả", "Mã ĐG", "Họ Tên", "Số Điện Thoại", "Trạng Thái"});
        cbSearchCriteria.setFont(fontInput); cbSearchCriteria.setPreferredSize(new Dimension(140, 35));
        btnSearch = createActionButton("Lọc", new Color(13, 110, 253)); btnSearch.setPreferredSize(new Dimension(100, 35));
        btnResetSearch = createActionButton("Hủy Lọc", new Color(108, 117, 125)); btnResetSearch.setPreferredSize(new Dimension(100, 35));

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

        // --- BẢNG ---
        String[] columns = {"Mã ĐG", "Họ Tên", "Giới Tính", "Ngày Sinh", "Điện Thoại", "Trạng Thái"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        setupTable(table);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    String trangThai = model.getValueAt(row, 5) != null ? model.getValueAt(row, 5).toString() : "";
                    if (trangThai.equals("Đã khóa")) {
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

        // --- NÚT CHỨC NĂNG ---
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnlButtons.setBackground(colorBackground);

        btnThem = createActionButton("Thêm", new Color(25, 135, 84));
        btnSua = createActionButton("Cập Nhật", new Color(255, 193, 7)); btnSua.setForeground(Color.BLACK);
        btnKhoa = createActionButton("Khóa Thẻ", new Color(220, 53, 69));
        btnMoKhoa = createActionButton("Mở Khóa", new Color(13, 110, 253));
        btnXoa = createActionButton("Xóa Hẳn", new Color(134, 19, 19));
        btnLamMoi = createActionButton("Làm Mới", new Color(108, 117, 125));

        pnlButtons.add(btnThem); pnlButtons.add(btnSua); pnlButtons.add(btnKhoa);
        pnlButtons.add(btnMoKhoa); pnlButtons.add(btnXoa); pnlButtons.add(btnLamMoi);
        add(pnlButtons, BorderLayout.SOUTH);
    }

    // ==========================================================
    // KHU VỰC XỬ LÝ SỰ KIỆN
    // ==========================================================
    private void initEvents() {

        // 1. Click bảng -> đổ dữ liệu lên form
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

                    String tt = model.getValueAt(row, 5) != null ? model.getValueAt(row, 5).toString() : "";
                    if (!tt.equals("Đã khóa")) {
                        cbTrangThai.setSelectedItem(tt);
                    } else {
                        cbTrangThai.setSelectedIndex(0);
                    }

                    String maDG = txtMaDG.getText();
                    for (DocGiaDTO dg : docGiaBUS.getListDocGia()) {
                        if (dg.getMaDocGia().equals(maDG)) {
                            txtEmail.setText(dg.getEmail() != null ? dg.getEmail() : "");
                            txtNgayDK.setText(dg.getNgayDangKi() != null ? dg.getNgayDangKi() : "");
                            break;
                        }
                    }
                }
            }
        });

        // 2. Nút LÀM MỚI
        btnLamMoi.addActionListener(e -> {
            lamMoiForm();
            docGiaBUS.reloadFromDB(); // Đồng bộ lại với DB
            loadDataToTable();
        });

        // 3. Nút THÊM
        btnThem.addActionListener(e -> {
            if (txtMaDG.getText().trim().isEmpty() || txtHoTen.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập ít nhất Mã độc giả và Họ tên!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            DocGiaDTO dg = createDocGiaFromForm();
            String result = docGiaBUS.addDocGia(dg);
            if (result.equals("Thêm thành công")) {
                JOptionPane.showMessageDialog(this, "Thêm độc giả thành công!\nTài khoản đăng nhập mặc định là Mã ĐG.\nMật khẩu: 12345");
                loadDataToTable();
                lamMoiForm();
            } else {
                JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 4. Nút CẬP NHẬT
        btnSua.addActionListener(e -> {
            if (txtMaDG.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 độc giả trong bảng để sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            DocGiaDTO dg = createDocGiaFromForm();
            String result = docGiaBUS.updateDocGia(dg);
            if (result.equals("Cập nhật thành công")) {
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin thành công!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 5. Nút KHÓA THẺ
        btnKhoa.addActionListener(e -> {
            if (txtMaDG.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 độc giả trong bảng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String maDG = txtMaDG.getText().trim();
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn khóa thẻ độc giả " + maDG + "?",
                    "Xác nhận khóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String result = docGiaBUS.softDeleteDocGia(maDG);
                if (result.equals("Khóa thẻ thành công")) {
                    JOptionPane.showMessageDialog(this, "Đã khóa thẻ độc giả!");
                    loadDataToTable();
                    lamMoiForm();
                } else {
                    JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 6. Nút MỞ KHÓA
        btnMoKhoa.addActionListener(e -> {
            if (txtMaDG.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 độc giả đã khóa trong bảng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String maDG = txtMaDG.getText().trim();
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có muốn mở khóa thẻ độc giả " + maDG + "?",
                    "Xác nhận mở khóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String result = docGiaBUS.restoreDocGia(maDG);
                if (result.equals("Mở khóa thành công")) {
                    JOptionPane.showMessageDialog(this, "Đã mở khóa thẻ độc giả!");
                    loadDataToTable();
                    lamMoiForm();
                } else {
                    JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 7. Nút XÓA HẲN - ĐÃ SỬA: reload từ DB sau khi xóa để chắc chắn đồng bộ
        btnXoa.addActionListener(e -> {
            if (txtMaDG.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 độc giả trong bảng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String maDG = txtMaDG.getText().trim();
            int confirm = JOptionPane.showConfirmDialog(this,
                    "⚠ XÓA HẲN độc giả " + maDG + " khỏi hệ thống?\n" +
                    "Tất cả dữ liệu liên quan (phiếu mượn, thẻ thư viện, phí phạt) cũng sẽ bị xóa!\n" +
                    "Hành động này KHÔNG THỂ hoàn tác!",
                    "Xác nhận xóa vĩnh viễn", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                String result = docGiaBUS.deleteDocGia(maDG);
                if (result.equals("Xóa thành công")) {
                    JOptionPane.showMessageDialog(this, "Đã xóa vĩnh viễn độc giả " + maDG);
                    docGiaBUS.reloadFromDB(); // Đồng bộ lại từ DB cho chắc chắn
                    loadDataToTable();
                    lamMoiForm();
                } else {
                    JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 8. Đổi tiêu chí tìm kiếm -> chuyển giữa TextField và ComboBox
        cbSearchCriteria.addActionListener(e -> {
            String criteria = (String) cbSearchCriteria.getSelectedItem();
            if ("Trạng Thái".equals(criteria)) {
                searchInputLayout.show(pnlSearchInput, "COMBO");
            } else {
                searchInputLayout.show(pnlSearchInput, "TEXT");
            }
        });

        // 9. Nút LỌC
        btnSearch.addActionListener(e -> {
            String criteria = (String) cbSearchCriteria.getSelectedItem();
            String keyword;
            if ("Trạng Thái".equals(criteria)) {
                keyword = (String) cbSearchTrangThai.getSelectedItem();
            } else {
                keyword = txtSearch.getText();
            }
            ArrayList<DocGiaDTO> ketQua = docGiaBUS.search(keyword, criteria);
            hienThiKetQua(ketQua);
        });

        // 10. Nút HỦY LỌC
        btnResetSearch.addActionListener(e -> {
            txtSearch.setText("");
            cbSearchCriteria.setSelectedIndex(0);
            cbSearchTrangThai.setSelectedIndex(0);
            searchInputLayout.show(pnlSearchInput, "TEXT");
            loadDataToTable();
        });
    }

    // ==========================================================
    // CÁC HÀM HỖ TRỢ
    // ==========================================================

    private void loadDataToTable() {
        ArrayList<DocGiaDTO> listDG = docGiaBUS.getListDocGia();
        if (listDG != null) {
            hienThiKetQua(listDG);
        }
    }

    private void hienThiKetQua(ArrayList<DocGiaDTO> danhSach) {
        model.setRowCount(0);
        for (DocGiaDTO dg : danhSach) {
            String trangThai;
            if (dg.getIsDeleted() != null && dg.getIsDeleted()) {
                trangThai = "Đã khóa";
            } else {
                trangThai = dg.getTrangThai();
            }
            model.addRow(new Object[]{
                dg.getMaDocGia(),
                dg.getHoTen(),
                dg.getGioiTinh(),
                dg.getNgaySinh(),
                dg.getSoDienThoai(),
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
        dg.setTrangThai(cbTrangThai.getSelectedItem().toString());

        dg.setTenDangNhap(txtMaDG.getText().trim());
        dg.setMatKhau("12345");
        dg.setIsDeleted(false);
        dg.setLoaiDocGia("Thường");
        dg.setNgayXoa("");
        dg.setDiaChi("");

        return dg;
    }

    public void lamMoiForm() {
        txtMaDG.setText("");
        txtMaDG.setEditable(true);
        txtHoTen.setText("");
        txtNgaySinh.setText("2000-01-01");
        txtDienThoai.setText("");
        txtEmail.setText("");
        txtNgayDK.setText(java.time.LocalDate.now().toString());
        cbGioiTinh.setSelectedIndex(0);
        cbTrangThai.setSelectedIndex(0);
        txtSearch.setText("");
        cbSearchTrangThai.setSelectedIndex(0);
        table.clearSelection();
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
        btn.setPreferredSize(new Dimension(130, 40)); btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        return btn;
    }
}