package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import BUS.*;
import DTO.*;

public class AdminQuanLyPhiPhatPanel extends JPanel {

    private String tenFont = "Segoe UI";
    private Color colorBackground = new Color(248, 249, 250);

    private JTextField txtMaPhieuPhat, txtMaPhieuMuon, txtSoTien;
    private JComboBox<String> cbLyDo, cbSearchCriteria;
    private JComboBox<String> cbMaCuonSach; // Đã đổi sang JComboBox
    private JTextField txtSearch;
    private JComboBox<String> cbSearchTrangThai;
    private JPanel pnlSearchInput;
    private CardLayout searchInputLayout;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnThem, btnCapNhat, btnThuTien, btnHuy, btnLamMoi, btnSearch, btnResetSearch;
    // Thêm các nút mới
    private JButton btnRefresh, btnImport, btnExport, btnSave; 

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

        // --- HEADER BÊN TRÊN (Chứa Tiêu đề + Nút Làm Mới Icon) ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(colorBackground);

        JLabel lblTitle = new JLabel("Quản Lý Phiếu Phạt");
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 24));
        lblTitle.setForeground(new Color(33, 37, 41));
        pnlHeader.add(lblTitle, BorderLayout.WEST);

        // Nút Làm Mới bằng Icon
        btnRefresh = new JButton();
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefresh.setContentAreaFilled(false); 
        btnRefresh.setBorderPainted(false);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setToolTipText("Làm mới dữ liệu (F5)"); 
        btnRefresh.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/Images/refresh.png"));
            Image img = icon.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
            btnRefresh.setIcon(new ImageIcon(img));
            btnRefresh.setPressedIcon(new ImageIcon(img.getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
            btnRefresh.setRolloverEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pnlHeader.add(btnRefresh, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

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
        txtMaPhieuPhat.setEditable(false);
        
        txtMaPhieuMuon = new JTextField(); txtMaPhieuMuon.setFont(fontInput);
        
        cbMaCuonSach = new JComboBox<>(); 
        cbMaCuonSach.setFont(fontInput);
        
        cbLyDo = new JComboBox<>(new String[]{
            "Trả sách trễ hạn", "Làm rách/bẩn sách", "Làm mất sách", "Lý do khác"
        });
        cbLyDo.setFont(fontInput);
        
        txtSoTien = new JTextField("0"); txtSoTien.setFont(fontInput);

        // Hàng 1
        pnlInput.add(createLabel("Mã phiếu phạt:", fontLabel)); pnlInput.add(txtMaPhieuPhat);
        pnlInput.add(createLabel("Mã phiếu mượn:", fontLabel)); pnlInput.add(txtMaPhieuMuon);
        
        // Hàng 2
        pnlInput.add(createLabel("Mã cuốn sách:", fontLabel));  
        pnlInput.add(cbMaCuonSach);
        pnlInput.add(createLabel("Lý do phạt:", fontLabel));     pnlInput.add(cbLyDo);
        
        // Hàng 3
        pnlInput.add(createLabel("Số tiền (VNĐ):", fontLabel)); pnlInput.add(txtSoTien);
        pnlInput.add(new JLabel("")); // Cột trống để cân bằng layout
        pnlInput.add(new JLabel("")); // Cột trống để cân bằng layout

        // --- THANH TÌM KIẾM ---
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlSearch.setBackground(colorBackground);
        pnlSearch.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        txtSearch = new JTextField(20); txtSearch.setFont(fontInput);
        txtSearch.setPreferredSize(new Dimension(0, 35));

        cbSearchTrangThai = new JComboBox<>(new String[]{"Chưa thanh toán", "Đã thanh toán", "Đã hủy"});
        cbSearchTrangThai.setFont(fontInput);
        cbSearchTrangThai.setPreferredSize(new Dimension(200, 35));

        searchInputLayout = new CardLayout();
        pnlSearchInput = new JPanel(searchInputLayout);
        pnlSearchInput.setPreferredSize(new Dimension(200, 35));
        pnlSearchInput.add(txtSearch, "TEXT");
        pnlSearchInput.add(cbSearchTrangThai, "COMBO");
        searchInputLayout.show(pnlSearchInput, "TEXT");

        cbSearchCriteria = new JComboBox<>(new String[]{"Tất cả", "Mã Phạt", "Mã Phiếu Mượn", "Trạng Thái"});
        cbSearchCriteria.setFont(fontInput);
        cbSearchCriteria.setPreferredSize(new Dimension(150, 35));

        btnSearch = createActionButton("Tìm Kiếm", new Color(13, 110, 253));
        btnSearch.setPreferredSize(new Dimension(110, 35));
        btnResetSearch = createActionButton("Hủy Lọc", new Color(108, 117, 125));
        btnResetSearch.setPreferredSize(new Dimension(100, 35));

        pnlSearch.add(createLabel("Tra cứu:", new Font(tenFont, Font.BOLD, 14)));
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
                    } else if (tt.equals("Đã hủy")) {
                        c.setBackground(new Color(238, 238, 238));
                        c.setForeground(new Color(158, 158, 158));
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
        btnCapNhat = createActionButton("Cập Nhật", new Color(255, 193, 7));
        btnCapNhat.setForeground(Color.BLACK);
        btnThuTien = createActionButton("Xác Nhận Thu", new Color(13, 110, 253));
        btnHuy = createActionButton("Hủy Phiếu", new Color(220, 53, 69));
        // Đã gỡ nút btnLamMoi ở dưới để dùng nút icon ở trên
        
        btnImport = createActionButton("Import", new Color(33, 115, 70)); 
        btnSave = createActionButton("Save", new Color(111, 66, 193));
        btnExport = createActionButton("Export", new Color(33, 115, 70));

        pnlButtons.add(btnImport); 
        pnlButtons.add(btnSave);
        pnlButtons.add(btnExport);
        pnlButtons.add(btnThem); 
        pnlButtons.add(btnCapNhat);
        pnlButtons.add(btnThuTien); 
        pnlButtons.add(btnHuy);
        
        add(pnlButtons, BorderLayout.SOUTH);

        txtMaPhieuPhat.setText(phieuPhatBUS.generateMaPP());
    }

    // ==========================================================
    // SỰ KIỆN
    // ==========================================================
    private void initEvents() {
        
        // Sự kiện: Khi gõ xong Mã Phiếu Mượn và ấn Enter -> Tải danh sách sách
        txtMaPhieuMuon.addActionListener(e -> {
            String maPM = txtMaPhieuMuon.getText().trim();
            if (!maPM.isEmpty()) {
                // Gọi BUS để lấy danh sách
                ArrayList<String> dsMaSach = phieuPhatBUS.getDanhSachMaSachByMaPM(maPM);
                cbMaCuonSach.removeAllItems(); // Xóa dữ liệu cũ
                
                if (dsMaSach.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy cuốn sách nào thuộc mã phiếu mượn này!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                } else {
                    // Đổ dữ liệu mới vào ComboBox
                    for (String ma : dsMaSach) {
                        cbMaCuonSach.addItem(ma);
                    }
                    JOptionPane.showMessageDialog(this, "Đã tải xong danh sách sách của phiếu " + maPM);
                }
            }
        });

        // 1. Click bảng -> đổ dữ liệu lên form
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtMaPhieuPhat.setText(safeGet(row, 0));
                    txtMaPhieuMuon.setText(safeGet(row, 1));
                    txtMaPhieuMuon.setEditable(false);
                    cbMaCuonSach.removeAllItems(); // Bảng chính không hiện mã sách, nên reset tạm

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

        // 2. LÀM MỚI (Icon trên cùng)
        btnRefresh.addActionListener(e -> {
            lamMoiForm();
            phieuPhatBUS.reloadFromDB();
            loadDataToTable();
        });

        // 3. LẬP PHIẾU PHẠT
        btnThem.addActionListener(e -> {
            if (txtMaPhieuMuon.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã phiếu mượn!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (cbMaCuonSach.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Chưa có mã cuốn sách! Hãy gõ Mã phiếu mượn và ấn Enter để tải sách.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            double soTien = parseSoTien();
            if (soTien <= 0) return;

            String maNQL = SessionManager.getInstance().getMaNguoi();
            if (maNQL == null || maNQL.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Không xác định được người đăng nhập!\nVui lòng đăng xuất và đăng nhập lại.",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String maPP = txtMaPhieuPhat.getText().trim();
            PhieuPhatDTO pp = new PhieuPhatDTO();
            pp.setMaPP(maPP);
            pp.setMaPM(txtMaPhieuMuon.getText().trim());
            pp.setMaNQL(maNQL);
            pp.setNgayLap(java.time.LocalDate.now().toString());
            pp.setTongTien(String.valueOf(soTien));
            pp.setTrangThai(0);

            String lyDo = cbLyDo.getSelectedItem().toString();
            ChiTietPhieuPhatDTO ctpp = new ChiTietPhieuPhatDTO();
            ctpp.setMaCTPP("CT" + maPP.substring(2));
            ctpp.setMaPP(maPP);
            
            // Lấy mã cuốn sách từ JComboBox
            ctpp.setMaCuonSach(cbMaCuonSach.getSelectedItem().toString());
            
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

        // 4. CẬP NHẬT (chỉ phiếu chưa TT)
        btnCapNhat.addActionListener(e -> {
            if (table.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 phiếu phạt trong bảng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (isDaTT()) {
                JOptionPane.showMessageDialog(this, "Phiếu đã thanh toán, không thể sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            double soTien = parseSoTien();
            if (soTien <= 0) return;

            String maPP = txtMaPhieuPhat.getText().trim();
            String lyDo = cbLyDo.getSelectedItem().toString();

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Cập nhật phiếu " + maPP + "?\nLý do: " + lyDo + "\nSố tiền: " + String.format("%,.0f", soTien) + " VNĐ",
                    "Xác nhận cập nhật", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String result = phieuPhatBUS.capNhatPhieuPhat(maPP, lyDo, soTien);
                JOptionPane.showMessageDialog(this, result);
                if (result.contains("thành công")) {
                    phieuPhatBUS.reloadFromDB();
                    loadDataToTable();
                    lamMoiForm();
                }
            }
        });

        // 5. XÁC NHẬN THU TIỀN (chỉ phiếu chưa TT)
        btnThuTien.addActionListener(e -> {
            if (table.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 phiếu phạt trong bảng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (isDaTT()) {
                JOptionPane.showMessageDialog(this, "Phiếu này đã thanh toán rồi!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String maPP = txtMaPhieuPhat.getText().trim();
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Xác nhận thu tiền phiếu " + maPP + "?\nSố tiền: " + safeGet(table.getSelectedRow(), 4) + " VNĐ",
                    "Xác nhận thu tiền", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String result = phieuPhatBUS.thanhToan(maPP);
                JOptionPane.showMessageDialog(this, result);
                if (result.contains("thành công")) {
                    loadDataToTable();
                    lamMoiForm();
                }
            }
        });

        // 6. HỦY PHIẾU (chỉ phiếu chưa TT)
        btnHuy.addActionListener(e -> {
            if (table.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 phiếu phạt trong bảng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (isDaTT()) {
                JOptionPane.showMessageDialog(this, "Phiếu đã thanh toán, không thể hủy!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String maPP = txtMaPhieuPhat.getText().trim();
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Hủy phiếu phạt " + maPP + "?\nPhiếu sẽ bị ẩn khỏi danh sách.",
                    "Xác nhận hủy", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                String result = phieuPhatBUS.deletePhieuPhat(maPP);
                JOptionPane.showMessageDialog(this, result);
                if (result.contains("thành công")) {
                    loadDataToTable();
                    lamMoiForm();
                }
            }
        });

        // 7. Đổi tiêu chí tìm kiếm
        cbSearchCriteria.addActionListener(e -> {
            if ("Trạng Thái".equals(cbSearchCriteria.getSelectedItem())) {
                searchInputLayout.show(pnlSearchInput, "COMBO");
            } else {
                searchInputLayout.show(pnlSearchInput, "TEXT");
            }
        });

        // 8. TÌM KIẾM
        btnSearch.addActionListener(e -> {
            String criteria = (String) cbSearchCriteria.getSelectedItem();
            String keyword = "Trạng Thái".equals(criteria)
                    ? (String) cbSearchTrangThai.getSelectedItem()
                    : txtSearch.getText();
            hienThiKetQua(phieuPhatBUS.search(keyword, criteria));
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
            Utils.ExcelExporter.exportTableToExcel(table, "DanhSach_PhieuPhat");
        });
        
        // 12. Nút SAVE (Dành cho Import Excel)
        // 12. Nút SAVE (Dành cho Import Excel)
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
                
                String maNQL = SessionManager.getInstance().getMaNguoi();
                if (maNQL == null || maNQL.isEmpty()) maNQL = "NV00000001";

                for (int i = 0; i < rowCount; i++) {
                    try {
                        // Cột 0: Mã Phạt, 1: Mã Phiếu Mượn, 2: Ngày Lập, 3: Lý Do, 4: Tổng Tiền, 5: Trạng Thái
                        String maPP = model.getValueAt(i, 0).toString().trim();
                        String maPM = model.getValueAt(i, 1).toString().trim();
                        String ngayLap = model.getValueAt(i, 2).toString().trim();
                        String lyDo = model.getValueAt(i, 3).toString().trim();
                        String tongTien = model.getValueAt(i, 4).toString().trim().replace(",", "");
                        String trangThaiStr = model.getValueAt(i, 5).toString().trim();
                        
                        int trangThai = trangThaiStr.equals("Đã thanh toán") ? 1 : (trangThaiStr.equals("Đã hủy") ? 2 : 0);

                        // Tạo Phiếu Phạt
                        PhieuPhatDTO pp = new PhieuPhatDTO();
                        pp.setMaPP(maPP);
                        pp.setMaPM(maPM);
                        pp.setMaNQL(maNQL);
                        pp.setNgayLap(ngayLap.isEmpty() ? java.time.LocalDate.now().toString() : ngayLap);
                        pp.setTongTien(tongTien);
                        pp.setTrangThai(trangThai);

                        // Tạo 1 dòng Chi Tiết Phiếu Phạt giả định (Vì Excel chỉ có 1 dòng tổng)
                        ChiTietPhieuPhatDTO ctpp = new ChiTietPhieuPhatDTO();
                        ctpp.setMaCTPP("CT" + maPP.substring(2));
                        ctpp.setMaPP(maPP);
                        ctpp.setMaCuonSach("MV000000000000000001"); // Mã sách mặc định do Excel ko có
                        ctpp.setLyDo(lyDo);
                        ctpp.setSoTien(tongTien);

                        ArrayList<ChiTietPhieuPhatDTO> dsChiTiet = new ArrayList<>();
                        dsChiTiet.add(ctpp);

                        // Gọi BUS lưu vào Database
                        String result = phieuPhatBUS.taoPhieuPhat(pp, dsChiTiet);
                        if (result.contains("thành công")) {
                            successCount++;
                        } else {
                            failCount++; 
                        }
                    } catch (Exception ex) {
                        failCount++;
                    }
                }

                JOptionPane.showMessageDialog(this, 
                        "Lưu hoàn tất!\n- Thành công: " + successCount + " phiếu\n- Bỏ qua (Trùng mã/Lỗi): " + failCount + " phiếu", 
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                
                // Load lại bảng
                phieuPhatBUS.reloadFromDB();
                loadDataToTable();
            }
        });
    }

    // ==========================================================
    // HÀM HỖ TRỢ
    // ==========================================================

    // Kiểm tra dòng đang chọn đã thanh toán chưa
    private boolean isDaTT() {
        return safeGet(table.getSelectedRow(), 5).equals("Đã thanh toán");
    }

    private void loadDataToTable() {
        hienThiKetQua(phieuPhatBUS.getDanhSachHienThiGUI());
    }

    private void hienThiKetQua(ArrayList<Object[]> danhSach) {
        model.setRowCount(0);
        for (Object[] row : danhSach) model.addRow(row);
    }

    private void lamMoiForm() {
        txtMaPhieuPhat.setText(phieuPhatBUS.generateMaPP());
        txtMaPhieuMuon.setText(""); txtMaPhieuMuon.setEditable(true);
        cbMaCuonSach.removeAllItems(); // Làm trống JComboBox
        cbLyDo.setSelectedIndex(0);
        txtSoTien.setText("0");
        txtSearch.setText(""); cbSearchTrangThai.setSelectedIndex(0);
        table.clearSelection();
    }

    private double parseSoTien() {
        try {
            double soTien = Double.parseDouble(txtSoTien.getText().trim().replace(",", ""));
            if (soTien <= 0) {
                JOptionPane.showMessageDialog(this, "Số tiền phải lớn hơn 0!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return -1;
            }
            return soTien;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số tiền không hợp lệ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return -1;
        }
    }

    private String safeGet(int row, int col) {
        Object val = model.getValueAt(row, col);
        return val != null ? val.toString() : "";
    }

    public JTable getTable() { return table; }
    public DefaultTableModel getModel() { return model; }

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
        btn.setPreferredSize(new Dimension(140, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        return btn;
    }
}