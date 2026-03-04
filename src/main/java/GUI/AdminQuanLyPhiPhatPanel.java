package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import BUS.*;
import DTO.*;

public class AdminQuanLyPhiPhatPanel extends JPanel {

    private String tenFont = "Segoe UI";
    private Color colorBackground = new Color(248, 249, 250);

    private JTextField txtMaPhieuPhat, txtMaPhieuMuon, txtSoTien;
    private JComboBox<String> cbLyDo, cbSearchCriteria;
    private JTextField txtSearch;
    private JComboBox<String> cbSearchTrangThai;
    private JPanel pnlSearchInput;
    private CardLayout searchInputLayout;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnThem, btnThuTien, btnHuy, btnLamMoi, btnSearch, btnResetSearch;

    private PhieuPhatBUS phieuPhatBUS = new PhieuPhatBUS();

    public AdminQuanLyPhiPhatPanel() {
        initComponents();
        initEvents();
        loadDataToTable();
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
        JPanel pnlInput = new JPanel(new GridLayout(2, 4, 15, 15));
        pnlInput.setBackground(Color.WHITE);
        pnlInput.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        Font fontLabel = new Font(tenFont, Font.BOLD, 14);
        Font fontInput = new Font(tenFont, Font.PLAIN, 14);

        txtMaPhieuPhat = new JTextField(); txtMaPhieuPhat.setFont(fontInput);
        txtMaPhieuPhat.setEditable(false); // Mã tự sinh
        txtMaPhieuMuon = new JTextField(); txtMaPhieuMuon.setFont(fontInput);
        cbLyDo = new JComboBox<>(new String[]{
            "Trả sách trễ hạn", "Làm rách/bẩn sách", "Làm mất sách", "Lý do khác"
        });
        cbLyDo.setFont(fontInput);
        txtSoTien = new JTextField("0"); txtSoTien.setFont(fontInput);

        pnlInput.add(createLabel("Mã phiếu phạt:", fontLabel)); pnlInput.add(txtMaPhieuPhat);
        pnlInput.add(createLabel("Mã phiếu mượn:", fontLabel)); pnlInput.add(txtMaPhieuMuon);
        pnlInput.add(createLabel("Lý do phạt:", fontLabel));     pnlInput.add(cbLyDo);
        pnlInput.add(createLabel("Số tiền (VNĐ):", fontLabel)); pnlInput.add(txtSoTien);

        // --- THANH TÌM KIẾM ---
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlSearch.setBackground(colorBackground);
        pnlSearch.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        txtSearch = new JTextField(20); txtSearch.setFont(fontInput);
        txtSearch.setPreferredSize(new Dimension(0, 35));

        cbSearchTrangThai = new JComboBox<>(new String[]{"Chưa thanh toán", "Đã thanh toán"});
        cbSearchTrangThai.setFont(fontInput);
        cbSearchTrangThai.setPreferredSize(new Dimension(200, 35));

        searchInputLayout = new CardLayout();
        pnlSearchInput = new JPanel(searchInputLayout);
        pnlSearchInput.setPreferredSize(new Dimension(200, 35));
        pnlSearchInput.add(txtSearch, "TEXT");
        pnlSearchInput.add(cbSearchTrangThai, "COMBO");
        searchInputLayout.show(pnlSearchInput, "TEXT");

        cbSearchCriteria = new JComboBox<>(new String[]{
            "Tất cả", "Mã Phạt", "Mã Phiếu Mượn", "Trạng Thái"
        });
        cbSearchCriteria.setFont(fontInput);
        cbSearchCriteria.setPreferredSize(new Dimension(150, 35));

        btnSearch = createActionButton("Tìm Kiếm", new Color(13, 110, 253));
        btnSearch.setPreferredSize(new Dimension(110, 35));
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
        String[] columns = {"Mã Phạt", "Mã Phiếu Mượn", "Ngày Lập", "Lý Do", "Tổng Tiền", "Trạng Thái"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        setupTable(table);

        // Tô màu theo trạng thái
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    String tt = model.getValueAt(row, 5) != null ? model.getValueAt(row, 5).toString() : "";
                    if (tt.equals("Đã thanh toán")) {
                        c.setBackground(new Color(232, 245, 233));
                        c.setForeground(new Color(27, 94, 32));
                    } else if (tt.equals("Chưa thanh toán")) {
                        c.setBackground(new Color(255, 243, 224));
                        c.setForeground(new Color(230, 81, 0));
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

        btnThem = createActionButton("Lập Phiếu Phạt", new Color(25, 135, 84));
        btnThuTien = createActionButton("Xác Nhận Thu", new Color(13, 110, 253));
        btnHuy = createActionButton("Hủy Phiếu", new Color(220, 53, 69));
        btnLamMoi = createActionButton("Làm Mới", new Color(108, 117, 125));

        pnlButtons.add(btnThem); pnlButtons.add(btnThuTien);
        pnlButtons.add(btnHuy); pnlButtons.add(btnLamMoi);
        add(pnlButtons, BorderLayout.SOUTH);

        // Sinh mã mặc định
        txtMaPhieuPhat.setText(phieuPhatBUS.generateMaPP());
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
                    txtMaPhieuPhat.setText(safeGet(row, 0));
                    txtMaPhieuMuon.setText(safeGet(row, 1));
                    txtMaPhieuMuon.setEditable(false);

                    // Set lý do vào combo nếu khớp
                    String lyDo = safeGet(row, 3);
                    boolean found = false;
                    for (int i = 0; i < cbLyDo.getItemCount(); i++) {
                        if (cbLyDo.getItemAt(i).equals(lyDo)) {
                            cbLyDo.setSelectedIndex(i);
                            found = true;
                            break;
                        }
                    }
                    if (!found) cbLyDo.setSelectedItem("Lý do khác");

                    txtSoTien.setText(safeGet(row, 4).replace(",", ""));
                }
            }
        });

        // 2. Nút LÀM MỚI
        btnLamMoi.addActionListener(e -> {
            lamMoiForm();
            phieuPhatBUS.reloadFromDB();
            loadDataToTable();
        });

        // 3. Nút LẬP PHIẾU PHẠT
        btnThem.addActionListener(e -> {
            if (txtMaPhieuMuon.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã phiếu mượn!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            double soTien;
            try {
                soTien = Double.parseDouble(txtSoTien.getText().trim().replace(",", ""));
                if (soTien <= 0) {
                    JOptionPane.showMessageDialog(this, "Số tiền phải lớn hơn 0!",
                            "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số tiền không hợp lệ!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String maPP = txtMaPhieuPhat.getText().trim();
            PhieuPhatDTO pp = new PhieuPhatDTO();
            pp.setMaPP(maPP);
            pp.setMaPM(txtMaPhieuMuon.getText().trim());
            pp.setMaNQL("ADMIN"); // Có thể truyền từ session đăng nhập
            pp.setNgayLap(java.time.LocalDate.now().toString());
            pp.setTongTien(String.valueOf(soTien));
            pp.setTrangThai(0); // 0 = Chưa thanh toán

            // Tạo chi tiết phiếu phạt
            String lyDo = cbLyDo.getSelectedItem().toString();
            String maCTPP = "CT" + maPP.substring(2);

            ChiTietPhieuPhatDTO ctpp = new ChiTietPhieuPhatDTO();
            ctpp.setMaCTPP(maCTPP);
            ctpp.setMaPP(maPP);
            ctpp.setMaCuonSach("");
            ctpp.setLyDo(lyDo);
            ctpp.setSoTien(String.valueOf(soTien));

            ArrayList<ChiTietPhieuPhatDTO> dsChiTiet = new ArrayList<>();
            dsChiTiet.add(ctpp);

            String result = phieuPhatBUS.taoPhieuPhat(pp, dsChiTiet);
            if (result.equals("Tạo phiếu phạt thành công")) {
                JOptionPane.showMessageDialog(this, "Lập phiếu phạt thành công!");
                loadDataToTable();
                lamMoiForm();
            } else {
                JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 4. Nút XÁC NHẬN THU TIỀN (0 -> 1)
        btnThuTien.addActionListener(e -> {
            if (table.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 phiếu phạt trong bảng!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String maPP = txtMaPhieuPhat.getText().trim();
            String trangThaiHienTai = safeGet(table.getSelectedRow(), 5);

            if (trangThaiHienTai.equals("Đã thanh toán")) {
                JOptionPane.showMessageDialog(this, "Phiếu phạt này đã được thanh toán rồi!",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Xác nhận thu tiền phiếu phạt " + maPP + "?\nSố tiền: " + safeGet(table.getSelectedRow(), 4) + " VNĐ",
                    "Xác nhận thu tiền", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String result = phieuPhatBUS.thanhToan(maPP);
                if (result.equals("Xác nhận thanh toán thành công")) {
                    JOptionPane.showMessageDialog(this, "Đã xác nhận thanh toán!");
                    loadDataToTable();
                    lamMoiForm();
                } else {
                    JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 5. Nút HỦY PHIẾU (0 -> 2, chỉ hủy được phiếu chưa thanh toán)
        btnHuy.addActionListener(e -> {
            if (table.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 phiếu phạt trong bảng!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String maPP = txtMaPhieuPhat.getText().trim();
            String trangThaiHienTai = safeGet(table.getSelectedRow(), 5);

            if (trangThaiHienTai.equals("Đã thanh toán")) {
                JOptionPane.showMessageDialog(this, "Không thể hủy phiếu đã thanh toán!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn hủy phiếu phạt " + maPP + "?\nPhiếu sẽ bị ẩn khỏi danh sách.",
                    "Xác nhận hủy", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                String result = phieuPhatBUS.deletePhieuPhat(maPP);
                if (result.equals("Hủy phiếu phạt thành công")) {
                    JOptionPane.showMessageDialog(this, "Đã hủy phiếu phạt!");
                    loadDataToTable();
                    lamMoiForm();
                } else {
                    JOptionPane.showMessageDialog(this, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 6. Đổi tiêu chí tìm kiếm -> chuyển TextField / ComboBox
        cbSearchCriteria.addActionListener(e -> {
            String criteria = (String) cbSearchCriteria.getSelectedItem();
            if ("Trạng Thái".equals(criteria)) {
                searchInputLayout.show(pnlSearchInput, "COMBO");
            } else {
                searchInputLayout.show(pnlSearchInput, "TEXT");
            }
        });

        // 7. Nút TÌM KIẾM
        btnSearch.addActionListener(e -> {
            String criteria = (String) cbSearchCriteria.getSelectedItem();
            String keyword;
            if ("Trạng Thái".equals(criteria)) {
                keyword = (String) cbSearchTrangThai.getSelectedItem();
            } else {
                keyword = txtSearch.getText();
            }
            ArrayList<Object[]> ketQua = phieuPhatBUS.search(keyword, criteria);
            hienThiKetQua(ketQua);
        });

        // 8. Nút HỦY LỌC
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
        ArrayList<Object[]> data = phieuPhatBUS.getDanhSachHienThiGUI();
        hienThiKetQua(data);
    }

    private void hienThiKetQua(ArrayList<Object[]> danhSach) {
        model.setRowCount(0);
        for (Object[] row : danhSach) {
            model.addRow(row);
        }
    }

    private void lamMoiForm() {
        txtMaPhieuPhat.setText(phieuPhatBUS.generateMaPP());
        txtMaPhieuMuon.setText("");
        txtMaPhieuMuon.setEditable(true);
        cbLyDo.setSelectedIndex(0);
        txtSoTien.setText("0");
        txtSearch.setText("");
        cbSearchTrangThai.setSelectedIndex(0);
        table.clearSelection();
    }

    // Lấy giá trị an toàn từ bảng, tránh NullPointerException
    private String safeGet(int row, int col) {
        Object val = model.getValueAt(row, col);
        return val != null ? val.toString() : "";
    }

    // ===== GETTER =====
    public JTable getTable() { return table; }
    public DefaultTableModel getModel() { return model; }

    // ===== TIỆN ÍCH UI =====
    private JLabel createLabel(String text, Font font) {
        JLabel lbl = new JLabel(text); lbl.setFont(font);
        lbl.setForeground(new Color(73, 80, 87)); return lbl;
    }
    private void setupTable(JTable t) {
        t.setRowHeight(32); t.setFont(new Font(tenFont, Font.PLAIN, 14));
        t.getTableHeader().setFont(new Font(tenFont, Font.BOLD, 14));
        t.getTableHeader().setBackground(new Color(233, 236, 239));
        t.getTableHeader().setOpaque(false);
        t.setShowGrid(false); t.setIntercellSpacing(new Dimension(0, 0));
        t.setShowHorizontalLines(true); t.setGridColor(new Color(222, 226, 230));
    }
    private JButton createActionButton(String text, Color bgColor) {
        JButton btn = new JButton(text); btn.setFont(new Font(tenFont, Font.BOLD, 14));
        btn.setForeground(Color.WHITE); btn.setBackground(bgColor);
        btn.setBorderPainted(false); btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(150, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        return btn;
    }
}