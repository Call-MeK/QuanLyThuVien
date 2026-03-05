package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import BUS.*;
import DTO.*;

public class AdminQuanLyNhapSachPanel extends JPanel {

    private String tenFont = "Segoe UI";
    private Color colorBackground = new Color(248, 249, 250);

    private JTextField txtMaPN, txtNguoiNhap, txtMaSach, txtSoLuong, txtDonGia, txtNhaCungCap, txtNgayNhap, txtTongTien;
    private JComboBox<String> cbSearchCriteria;
    private JTextField txtSearch;
    private JTable table;
    private DefaultTableModel model;
    
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnSearch, btnResetSearch;
    // Thêm các nút mới
    private JButton btnRefresh, btnImport, btnExport, btnSave; 

    public AdminQuanLyNhapSachPanel() {
        initComponents();
        initEvents(); // Tui tự thêm gọi hàm sự kiện vô constructor cho ní nha
    }

    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBackground(colorBackground);
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // --- HEADER BÊN TRÊN (Chứa Tiêu đề + Nút Làm Mới Icon) ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(colorBackground);

        JLabel lblTitle = new JLabel("Quản Lý Phiếu Nhập Sách");
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
        JPanel pnlInput = new JPanel(new GridLayout(4, 4, 15, 15));
        pnlInput.setBackground(Color.WHITE);
        pnlInput.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        Font fontLabel = new Font(tenFont, Font.BOLD, 14);
        Font fontInput = new Font(tenFont, Font.PLAIN, 14);

        txtMaPN = new JTextField(); txtMaPN.setFont(fontInput);
        txtNguoiNhap = new JTextField(); txtNguoiNhap.setFont(fontInput);
        txtMaSach = new JTextField(); txtMaSach.setFont(fontInput);
        txtSoLuong = new JTextField("0"); txtSoLuong.setFont(fontInput);
        txtDonGia = new JTextField("0"); txtDonGia.setFont(fontInput);
        txtNhaCungCap = new JTextField(); txtNhaCungCap.setFont(fontInput);
        txtNgayNhap = new JTextField("dd/MM/yyyy"); txtNgayNhap.setFont(fontInput); txtNgayNhap.setForeground(Color.GRAY);
        txtTongTien = new JTextField("0"); txtTongTien.setFont(fontInput); txtTongTien.setEditable(false);

        pnlInput.add(createLabel("Mã phiếu nhập:", fontLabel)); pnlInput.add(txtMaPN);
        pnlInput.add(createLabel("Mã Quản lý:", fontLabel)); pnlInput.add(txtNguoiNhap);
        pnlInput.add(createLabel("Mã sách:", fontLabel)); pnlInput.add(txtMaSach);
        pnlInput.add(createLabel("Số lượng:", fontLabel)); pnlInput.add(txtSoLuong);
        pnlInput.add(createLabel("Đơn giá:", fontLabel)); pnlInput.add(txtDonGia);
        pnlInput.add(createLabel("Nhà cung cấp:", fontLabel)); pnlInput.add(txtNhaCungCap);
        pnlInput.add(createLabel("Ngày nhập:", fontLabel)); pnlInput.add(txtNgayNhap);
        pnlInput.add(createLabel("Tổng tiền (VNĐ):", fontLabel)); pnlInput.add(txtTongTien);

        // --- THANH TÌM KIẾM ---
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlSearch.setBackground(colorBackground);
        pnlSearch.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        txtSearch = new JTextField(20); txtSearch.setFont(fontInput); txtSearch.setPreferredSize(new Dimension(0, 35));
        cbSearchCriteria = new JComboBox<>(new String[]{"Tất cả", "Mã Phiếu", "Mã Sách", "Nhà Cung Cấp"});
        cbSearchCriteria.setFont(fontInput); cbSearchCriteria.setPreferredSize(new Dimension(140, 35));
        btnSearch = createActionButton("Tìm Kiếm", new Color(13, 110, 253)); btnSearch.setPreferredSize(new Dimension(110, 35));
        btnResetSearch = createActionButton("Hủy Lọc", new Color(108, 117, 125)); btnResetSearch.setPreferredSize(new Dimension(100, 35));

        pnlSearch.add(createLabel("Tra cứu phiếu nhập:", new Font(tenFont, Font.BOLD, 14)));
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
        String[] columns = {"Mã Phiếu", "Mã Sách", "Số Lượng", "Đơn Giá", "Nhà Cung Cấp", "Ngày Nhập", "Tổng Tiền"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        setupTable(table);

        try {
            List<PhieuNhapDTO> listPN = new PhieuNhapBUS().findAll();
            for (PhieuNhapDTO pn : listPN) {
                model.addRow(new Object[]{pn.getMaPN(), pn.getMaNXB(), pn.getMaNQL(), "", "", pn.getNgayNhap(), pn.getTongTien()});
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

        btnThem = createActionButton("Tạo Phiếu Nhập", new Color(34, 197, 94));
        btnSua = createActionButton("Sửa Thông Tin", new Color(255, 193, 7)); btnSua.setForeground(Color.BLACK);
        btnXoa = createActionButton("Hủy Phiếu", new Color(220, 53, 69));
        // Đã gỡ nút btnLamMoi ở dưới để dùng nút icon ở trên
        
        btnImport = createActionButton("Import", new Color(33, 115, 70)); 
        btnSave = createActionButton("Save", new Color(111, 66, 193));
        btnExport = createActionButton("Export", new Color(33, 115, 70));

        pnlButtons.add(btnImport); 
        pnlButtons.add(btnSave);
        pnlButtons.add(btnExport);
        pnlButtons.add(btnThem); 
        pnlButtons.add(btnSua); 
        pnlButtons.add(btnXoa); 
        
        add(pnlButtons, BorderLayout.SOUTH);
    }
    
    private void initEvents() {
        
        // Nút LÀM MỚI (Icon góc trên)
        btnRefresh.addActionListener(e -> { 
            model.setRowCount(0);
            try {
                List<PhieuNhapDTO> listPN = new PhieuNhapBUS().findAll();
                for (PhieuNhapDTO pn : listPN) {
                    model.addRow(new Object[]{pn.getMaPN(), pn.getMaNXB(), pn.getMaNQL(), "", "", pn.getNgayNhap(), pn.getTongTien()});
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Nút IMPORT EXCEL
        btnImport.addActionListener(e -> {
            Utils.ExcelImporter.importExcelToTable(table, model);
        });

        // Nút EXPORT EXCEL
        btnExport.addActionListener(e -> {
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Utils.ExcelExporter.exportTableToExcel(table, "DanhSach_PhieuNhap");
        });
        
        // Nút SAVE 
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
                
                PhieuNhapBUS pnBUS = new PhieuNhapBUS();

                for (int i = 0; i < rowCount; i++) {
                    try {
                        PhieuNhapDTO pn = new PhieuNhapDTO();
                        // Cột 0: Mã Phiếu, Cột 1: Mã Sách, Cột 2: Số Lượng, Cột 3: Đơn Giá, Cột 4: Nhà Cung Cấp, Cột 5: Ngày Nhập, Cột 6: Tổng Tiền
                        pn.setMaPN(model.getValueAt(i, 0).toString().trim());
                        pn.setMaNXB(model.getValueAt(i, 4).toString().trim());
                        
                        // Lấy mã quản lý đang đăng nhập, nếu null thì gán mặc định
                        String maNQL = BUS.SessionManager.getInstance().getMaNguoi();
                        if (maNQL == null || maNQL.isEmpty()) maNQL = "NV00000001";
                        pn.setMaNQL(maNQL);
                        
                        pn.setNgayNhap(model.getValueAt(i, 5).toString().trim());
                        
                        // Xử lý Tổng tiền (Bỏ dấu phẩy nếu có)
                        String tongTienStr = model.getValueAt(i, 6).toString().trim().replace(",", "");
                        pn.setTongTien(Float.valueOf(tongTienStr.isEmpty() ? "0" : tongTienStr));

                        // Lưu vào DB
                        String result = pnBUS.addPhieuNhap(pn);
                        if (result.equals("Thêm thành công")) {
                            successCount++;
                        } else {
                            failCount++; 
                        }
                    } catch (Exception ex) {
                        failCount++;
                        System.out.println("Lỗi dòng " + i + ": " + ex.getMessage());
                    }
                }

                JOptionPane.showMessageDialog(this, 
                        "Lưu hoàn tất!\n- Thành công: " + successCount + " phiếu\n- Bỏ qua (Trùng mã/Lỗi): " + failCount + " phiếu", 
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                
                // Load lại bảng
                model.setRowCount(0);
                for (PhieuNhapDTO pn : pnBUS.findAll()) {
                    model.addRow(new Object[]{pn.getMaPN(), "", pn.getMaNQL(), "", pn.getMaNXB(), pn.getNgayNhap(), pn.getTongTien()});
                }
            }
        });
    }

    // ===== GETTER =====
    public JTextField getTxtMaPN() { return txtMaPN; }
    public JTextField getTxtNguoiNhap() { return txtNguoiNhap; }
    public JTextField getTxtMaSach() { return txtMaSach; }
    public JTextField getTxtSoLuong() { return txtSoLuong; }
    public JTextField getTxtDonGia() { return txtDonGia; }
    public JTextField getTxtNhaCungCap() { return txtNhaCungCap; }
    public JTextField getTxtNgayNhap() { return txtNgayNhap; }
    public JTextField getTxtTongTien() { return txtTongTien; }
    public JTable getTable() { return table; }
    public DefaultTableModel getModel() { return model; }
    public JButton getBtnThem() { return btnThem; }
    public JButton getBtnSua() { return btnSua; }
    public JButton getBtnXoa() { return btnXoa; }

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