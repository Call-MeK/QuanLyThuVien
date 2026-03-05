package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import BUS.*;
import DTO.*;

public class AdminQuanLySachPanel extends JPanel {

    private String tenFont = "Segoe UI";
    private Color colorBackground = new Color(248, 249, 250);

    // Form fields
    private JTextField txtMaSach, txtTenSach, txtNamXB, txtNgonNgu, txtGiaBia, txtMaTacGia;
    private JComboBox<String> cbTheLoai, cbNXB;
    private JTextField txtSearch;
    private JComboBox<String> cbSearchCriteria;

    private JTable table;
    private DefaultTableModel model;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnSearch, btnResetSearch;

    private SachBUS sachBUS         = new SachBUS();
    private TheLoaiBUS theLoaiBUS   = new TheLoaiBUS();
    private NhaXuatBanBUS nxbBUS    = new NhaXuatBanBUS();

    private List<TheLoaiDTO>    listTheLoai;
    private List<NhaXuatBanDTO> listNXB;

    public AdminQuanLySachPanel() {
        initComponents();
        loadComboBoxes();
        initEvents();
        lamMoiForm();
        loadDataToTable();
    }

    // =====================================================
    // KHỞI TẠO GIAO DIỆN
    // =====================================================
    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBackground(colorBackground);
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel lblTitle = new JLabel("Quản Lý Thông Tin Sách");
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 24));
        lblTitle.setForeground(new Color(33, 37, 41));
        add(lblTitle, BorderLayout.NORTH);

        JPanel pnlCenter = new JPanel(new BorderLayout(0, 15));
        pnlCenter.setBackground(colorBackground);

        // ---- FORM NHẬP LIỆU ----
        JPanel pnlInput = new JPanel(new GridLayout(4, 4, 15, 12));
        pnlInput.setBackground(Color.WHITE);
        pnlInput.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        Font fontLabel = new Font(tenFont, Font.BOLD, 14);
        Font fontInput = new Font(tenFont, Font.PLAIN, 14);

        // Mã sách: readonly, không ghi "tự sinh" trên label nữa
        txtMaSach = new JTextField(); txtMaSach.setFont(fontInput);
        txtMaSach.setEditable(false);
        txtMaSach.setBackground(new Color(233, 236, 239));

        txtTenSach  = new JTextField(); txtTenSach.setFont(fontInput);
        txtNamXB    = new JTextField(); txtNamXB.setFont(fontInput);
        txtNgonNgu  = new JTextField(); txtNgonNgu.setFont(fontInput);
        txtGiaBia   = new JTextField("0"); txtGiaBia.setFont(fontInput);
        txtMaTacGia = new JTextField(); txtMaTacGia.setFont(fontInput);
        txtMaTacGia.setToolTipText("Nhập Mã Tác Giả (VD: TG00000001). Nhiều tác giả cách nhau bằng dấu phẩy.");

        cbTheLoai = new JComboBox<>(); cbTheLoai.setFont(fontInput);
        cbNXB     = new JComboBox<>(); cbNXB.setFont(fontInput);

        // Row 1
        pnlInput.add(lbl("Mã sách:", fontLabel));       pnlInput.add(txtMaSach);
        pnlInput.add(lbl("Tên sách:", fontLabel));       pnlInput.add(txtTenSach);
        // Row 2
        pnlInput.add(lbl("Thể loại:", fontLabel));       pnlInput.add(cbTheLoai);
        pnlInput.add(lbl("Nhà xuất bản:", fontLabel));   pnlInput.add(cbNXB);
        // Row 3
        pnlInput.add(lbl("Năm xuất bản:", fontLabel));   pnlInput.add(txtNamXB);
        pnlInput.add(lbl("Ngôn ngữ:", fontLabel));       pnlInput.add(txtNgonNgu);
        // Row 4
        pnlInput.add(lbl("Giá bìa (VNĐ):", fontLabel)); pnlInput.add(txtGiaBia);
        pnlInput.add(lbl("Mã tác giả:", fontLabel));     pnlInput.add(txtMaTacGia);

        // ---- THANH TÌM KIẾM ----
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlSearch.setBackground(colorBackground);
        pnlSearch.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        txtSearch = new JTextField(20); txtSearch.setFont(fontInput);
        txtSearch.setPreferredSize(new Dimension(0, 35));

        cbSearchCriteria = new JComboBox<>(new String[]{"Tất cả", "Mã Sách", "Tên Sách", "Thể Loại", "Nhà Xuất Bản"});
        cbSearchCriteria.setFont(fontInput);
        cbSearchCriteria.setPreferredSize(new Dimension(150, 35));

        btnSearch      = actionBtn("Tìm Kiếm", new Color(13, 110, 253));
        btnSearch.setPreferredSize(new Dimension(110, 35));
        btnResetSearch = actionBtn("Hủy Lọc", new Color(108, 117, 125));
        btnResetSearch.setPreferredSize(new Dimension(100, 35));

        pnlSearch.add(lbl("🔍 Tra cứu:", new Font(tenFont, Font.BOLD, 14)));
        pnlSearch.add(txtSearch);
        pnlSearch.add(lbl(" theo ", fontInput));
        pnlSearch.add(cbSearchCriteria);
        pnlSearch.add(btnSearch);
        pnlSearch.add(btnResetSearch);

        JPanel pnlTopCenter = new JPanel(new BorderLayout(0, 5));
        pnlTopCenter.setBackground(colorBackground);
        pnlTopCenter.add(pnlInput, BorderLayout.NORTH);
        pnlTopCenter.add(pnlSearch, BorderLayout.CENTER);
        pnlCenter.add(pnlTopCenter, BorderLayout.NORTH);

        // ---- BẢNG ----
        // Cột: MaSach | TenSach | TheLoai | NXB | NamXB | NgonNgu | GiaBia | TacGia | SoLuong
        String[] cols = {"Mã Sách", "Tên Sách", "Thể Loại", "Nhà Xuất Bản",
                         "Năm XB", "Ngôn Ngữ", "Giá Bìa", "Tác Giả", "Số Cuốn"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        setupTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);

        // ---- NÚT CHỨC NĂNG ----
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        pnlButtons.setBackground(colorBackground);

        btnThem   = actionBtn("Thêm Mới", new Color(25, 135, 84));
        btnSua    = actionBtn("Cập Nhật", new Color(255, 193, 7)); btnSua.setForeground(Color.BLACK);
        btnXoa    = actionBtn("Xóa Sách", new Color(220, 53, 69));
        btnLamMoi = actionBtn("Làm Mới",  new Color(108, 117, 125));

        pnlButtons.add(btnThem); pnlButtons.add(btnSua); pnlButtons.add(btnXoa); pnlButtons.add(btnLamMoi);
        add(pnlButtons, BorderLayout.SOUTH);
    }

    // =====================================================
    // LOAD COMBOBOX
    // =====================================================
    private void loadComboBoxes() {
        cbTheLoai.removeAllItems();
        listTheLoai = theLoaiBUS.getAll();
        if (listTheLoai != null) for (TheLoaiDTO tl : listTheLoai) cbTheLoai.addItem(tl.getTenTheLoai());

        cbNXB.removeAllItems();
        listNXB = nxbBUS.getAll();
        if (listNXB != null) for (NhaXuatBanDTO nxb : listNXB) cbNXB.addItem(nxb.getTenNXB());
    }

    // =====================================================
    // SỰ KIỆN
    // =====================================================
    private void initEvents() {

        // 1. Click bảng -> đổ lên form
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row < 0) return;

                txtMaSach.setText(val(row, 0));
                txtTenSach.setText(val(row, 1));
                txtNamXB.setText(val(row, 4));
                txtNgonNgu.setText(val(row, 5));
                // Giá bìa: bỏ định dạng "xxx.xxx đ" về số thuần
                txtGiaBia.setText(val(row, 6).replace(".", "").replace(",", "").replace(" đ", "").replace("đ", "").trim());
                txtMaTacGia.setText(""); // Mã tác giả để trống, user tự điền nếu muốn sửa

                chonComboTheoTen(cbTheLoai, val(row, 2));
                chonComboTheoTen(cbNXB,     val(row, 3));
            }
        });

        // 2. THÊM MỚI
        btnThem.addActionListener(e -> {
            SachDTO sach = layDuLieuForm();
            if (sach == null) return;

            String result = sachBUS.insert(sach);
            if (result.contains("thành công")) {
                String maTG = txtMaTacGia.getText().trim();
                if (!maTG.isEmpty()) {
                    for (String mg : maTG.split(",")) sachBUS.insertSachTacGia(sach.getMaSach(), mg.trim());
                }
                JOptionPane.showMessageDialog(this, result);
                lamMoiForm(); loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 3. CẬP NHẬT
        btnSua.addActionListener(e -> {
            String maSach = txtMaSach.getText().trim();
            if (maSach.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 sách trong bảng trước!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            SachDTO sach = layDuLieuForm();
            if (sach == null) return;

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Cập nhật thông tin sách " + maSach + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            String result = sachBUS.update(sach);
            if (result.contains("thành công")) {
                String maTG = txtMaTacGia.getText().trim();
                if (!maTG.isEmpty()) {
                    sachBUS.deleteSachTacGia(maSach);
                    for (String mg : maTG.split(",")) sachBUS.insertSachTacGia(maSach, mg.trim());
                }
                JOptionPane.showMessageDialog(this, result);
                lamMoiForm(); loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 4. XÓA (Soft Delete + kiểm tra đang mượn)
        btnXoa.addActionListener(e -> {
            String maSach  = txtMaSach.getText().trim();
            String tenSach = txtTenSach.getText().trim();
            if (maSach.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 sách trong bảng trước!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Xóa sách: " + tenSach + " (" + maSach + ")?\n"
                    + "Sách sẽ bị ẩn khỏi hệ thống.",
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm != JOptionPane.YES_OPTION) return;

            String result = sachBUS.delete(maSach);
            if (result.contains("thành công")) {
                JOptionPane.showMessageDialog(this, result);
                lamMoiForm(); loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(this, result, "Không thể xóa", JOptionPane.WARNING_MESSAGE);
            }
        });

        // 5. LÀM MỚI
        btnLamMoi.addActionListener(e -> { lamMoiForm(); loadDataToTable(); });

        // 6. TÌM KIẾM
        btnSearch.addActionListener(e -> timKiem());
        txtSearch.addActionListener(e -> timKiem());

        // 7. HỦY LỌC
        btnResetSearch.addActionListener(e -> {
            txtSearch.setText("");
            cbSearchCriteria.setSelectedIndex(0);
            loadDataToTable();
        });
    }

    // =====================================================
    // HÀM HỖ TRỢ
    // =====================================================
    private void loadDataToTable() {
        model.setRowCount(0);
        try {
            for (Object[] row : sachBUS.getDanhSachDayDu()) model.addRow(row);
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void timKiem() {
        String kw       = txtSearch.getText().trim().toLowerCase();
        String criteria = (String) cbSearchCriteria.getSelectedItem();
        model.setRowCount(0);
        try {
            for (Object[] row : sachBUS.getDanhSachDayDu()) {
                boolean match;
                switch (criteria) {
                    case "Mã Sách":      match = row[0] != null && row[0].toString().toLowerCase().contains(kw); break;
                    case "Tên Sách":     match = row[1] != null && row[1].toString().toLowerCase().contains(kw); break;
                    case "Thể Loại":     match = row[2] != null && row[2].toString().toLowerCase().contains(kw); break;
                    case "Nhà Xuất Bản": match = row[3] != null && row[3].toString().toLowerCase().contains(kw); break;
                    default:             match = true; break;
                }
                if (match) model.addRow(row);
            }
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private SachDTO layDuLieuForm() {
        String tenSach = txtTenSach.getText().trim();
        String ngonNgu = txtNgonNgu.getText().trim();
        if (tenSach.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên sách không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        int namXB = 0;
        try { namXB = Integer.parseInt(txtNamXB.getText().trim()); }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Năm xuất bản phải là số nguyên!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        float giaBia = 0;
        try { giaBia = Float.parseFloat(txtGiaBia.getText().trim()); }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Giá bìa phải là số!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        SachDTO sach = new SachDTO();
        sach.setMaSach(txtMaSach.getText().trim());
        sach.setTenSach(tenSach);
        sach.setTheLoai(getMaTheLoai((String) cbTheLoai.getSelectedItem()));
        sach.setMaNXB(getMaNXB((String) cbNXB.getSelectedItem()));
        sach.setNamXB(namXB);
        sach.setNgonNgu(ngonNgu);
        sach.setGiaBia(giaBia);
        return sach;
    }

    public void lamMoiForm() {
        txtMaSach.setText(sachBUS.generateMaSach());
        txtTenSach.setText("");
        txtNamXB.setText("");
        txtNgonNgu.setText("");
        txtGiaBia.setText("0");
        txtMaTacGia.setText("");
        if (cbTheLoai.getItemCount() > 0) cbTheLoai.setSelectedIndex(0);
        if (cbNXB.getItemCount() > 0) cbNXB.setSelectedIndex(0);
        table.clearSelection();
    }

    private String getMaTheLoai(String tenTheLoai) {
        if (listTheLoai != null) for (TheLoaiDTO tl : listTheLoai) if (tl.getTenTheLoai().equals(tenTheLoai)) return tl.getMaTheLoai();
        return "";
    }

    private String getMaNXB(String tenNXB) {
        if (listNXB != null) for (NhaXuatBanDTO nxb : listNXB) if (nxb.getTenNXB().equals(tenNXB)) return nxb.getMaNXB();
        return "";
    }

    private void chonComboTheoTen(JComboBox<String> cb, String ten) {
        for (int i = 0; i < cb.getItemCount(); i++) if (cb.getItemAt(i).equals(ten)) { cb.setSelectedIndex(i); return; }
    }

    private String val(int row, int col) {
        Object v = model.getValueAt(row, col); return v != null ? v.toString() : "";
    }

    // ===== TIỆN ÍCH UI =====
    private JLabel lbl(String text, Font font) {
        JLabel l = new JLabel(text); l.setFont(font); l.setForeground(new Color(73, 80, 87)); return l;
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

        // ✅ Màu highlight rõ ràng: nền xanh đậm, chữ trắng
        t.setSelectionBackground(new Color(37, 99, 235));
        t.setSelectionForeground(Color.WHITE);

        // Renderer để tô màu cột "Số Cuốn" (col 8): đỏ nếu = 0
        t.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                if (!isSelected) {
                    c.setBackground(Color.WHITE);
                    c.setForeground(new Color(33, 37, 41));
                    // Cột Số Cuốn (col 8): đỏ nếu = 0, xanh nếu > 0
                    if (col == 8 && value != null) {
                        try {
                            int soLuong = Integer.parseInt(value.toString());
                            if (soLuong == 0) {
                                c.setForeground(new Color(220, 53, 69));
                                ((JLabel) c).setFont(new Font(tenFont, Font.BOLD, 14));
                            } else {
                                c.setForeground(new Color(25, 135, 84));
                                ((JLabel) c).setFont(new Font(tenFont, Font.BOLD, 14));
                            }
                        } catch (Exception ignored) {}
                    }
                }
                // Căn giữa cột số
                if (col == 4 || col == 8) ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
                if (col == 6) ((JLabel) c).setHorizontalAlignment(SwingConstants.RIGHT);
                return c;
            }
        });

        // Độ rộng cột
        int[] widths = {95, 210, 130, 130, 65, 80, 100, 150, 75};
        for (int i = 0; i < widths.length; i++) {
            t.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }
    }

    private JButton actionBtn(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font(tenFont, Font.BOLD, 14));
        btn.setForeground(Color.WHITE); btn.setBackground(bg);
        btn.setBorderPainted(false); btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(140, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        return btn;
    }

    // ===== GETTER =====
    public JTable getTable()           { return table; }
    public DefaultTableModel getModel(){ return model; }
    public JButton getBtnThem()        { return btnThem; }
    public JButton getBtnSua()         { return btnSua; }
    public JButton getBtnXoa()         { return btnXoa; }
    public JButton getBtnLamMoi()      { return btnLamMoi; }
}