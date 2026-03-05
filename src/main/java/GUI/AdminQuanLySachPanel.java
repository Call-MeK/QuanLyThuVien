package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import BUS.*;
import DTO.*;

public class AdminQuanLySachPanel extends JPanel {

    private String tenFont = "Segoe UI";
    private Color colorBackground = new Color(248, 249, 250);

    private JTextField txtMaSach, txtTenSach, txtTacGia, txtSoLuong, txtNhaXuatBan;
    private JComboBox<String> cbTheLoai;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi;
    // Thêm các nút mới
    private JButton btnRefresh, btnImport, btnExport, btnSave;

    public AdminQuanLySachPanel() {
        initComponents();
        initEvents(); // Tự thêm để gọi sự kiện
    }

    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBackground(colorBackground);
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // --- HEADER BÊN TRÊN (Chứa Tiêu đề + Nút Làm Mới Icon) ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(colorBackground);

        JLabel lblTitle = new JLabel("Quản Lý Thông Tin Sách");
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

        JPanel pnlCenter = new JPanel(new BorderLayout(0, 20));
        pnlCenter.setBackground(colorBackground);

        JPanel pnlInput = new JPanel(new GridLayout(3, 4, 15, 15));
        pnlInput.setBackground(Color.WHITE);
        pnlInput.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        Font fontLabel = new Font(tenFont, Font.BOLD, 14);
        Font fontInput = new Font(tenFont, Font.PLAIN, 14);

        txtMaSach = new JTextField();
        txtMaSach.setFont(fontInput);
        txtTenSach = new JTextField();
        txtTenSach.setFont(fontInput);
        txtTacGia = new JTextField();
        txtTacGia.setFont(fontInput);
        cbTheLoai = new JComboBox<>(new String[]{"Giáo trình", "Tham khảo", "Tiểu thuyết", "Công nghệ"});
        cbTheLoai.setFont(fontInput);
        txtSoLuong = new JTextField();
        txtSoLuong.setFont(fontInput);
        txtNhaXuatBan = new JTextField();
        txtNhaXuatBan.setFont(fontInput);

        pnlInput.add(createLabel("Mã sách:", fontLabel));
        pnlInput.add(txtMaSach);
        pnlInput.add(createLabel("Tên sách:", fontLabel));
        pnlInput.add(txtTenSach);
        pnlInput.add(createLabel("Tác giả:", fontLabel));
        pnlInput.add(txtTacGia);
        pnlInput.add(createLabel("Thể loại:", fontLabel));
        pnlInput.add(cbTheLoai);
        pnlInput.add(createLabel("Số lượng:", fontLabel));
        pnlInput.add(txtSoLuong);
        pnlInput.add(createLabel("Nhà xuất bản:", fontLabel));
        pnlInput.add(txtNhaXuatBan);

        pnlCenter.add(pnlInput, BorderLayout.NORTH);

        String[] columns = {"Mã Sách", "Tên Sách", "Tác Giả", "Thể Loại", "Số Lượng", "Nhà Xuất Bản"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        setupTable(table);

        try {
            List<SachDTO> listSach = new SachBUS().getAll();
            for (SachDTO s : listSach) {
                model.addRow(new Object[]{s.getMaSach(), s.getTenSach(), "", s.getTheLoai(), s.getNamXB(), s.getMaNXB()});
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

        btnThem = createActionButton("Thêm Mới", new Color(25, 135, 84));
        btnSua = createActionButton("Cập Nhật", new Color(255, 193, 7));
        btnSua.setForeground(Color.BLACK);
        btnXoa = createActionButton("Xóa Dữ Liệu", new Color(220, 53, 69));
        // Đã bỏ btnLamMoi ở dưới để dùng icon ở trên
        
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
    
    // ==========================================================
    // KHU VỰC XỬ LÝ SỰ KIỆN CHO CÁC NÚT MỚI
    // ==========================================================
    private void initEvents() {
        
        // Nút LÀM MỚI (Icon góc trên)
        btnRefresh.addActionListener(e -> { 
            // Xóa rỗng các ô nhập liệu
            txtMaSach.setText("");
            txtTenSach.setText("");
            txtTacGia.setText("");
            cbTheLoai.setSelectedIndex(0);
            txtSoLuong.setText("");
            txtNhaXuatBan.setText("");
            
            // Load lại bảng
            model.setRowCount(0);
            try {
                List<SachDTO> listSach = new SachBUS().getAll();
                for (SachDTO s : listSach) {
                    model.addRow(new Object[]{s.getMaSach(), s.getTenSach(), "", s.getTheLoai(), s.getNamXB(), s.getMaNXB()});
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
            Utils.ExcelExporter.exportTableToExcel(table, "DanhSach_Sach");
        });
        
        // Nút SAVE (Dành cho Import Excel)
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
                
                SachBUS sachBUS = new SachBUS();

                for (int i = 0; i < rowCount; i++) {
                    try {
                        SachDTO sach = new SachDTO();
                        // Cột 0: Mã Sách, 1: Tên Sách, 2: Tác Giả, 3: Thể Loại, 4: Số Lượng, 5: Nhà Xuất Bản
                        sach.setMaSach(model.getValueAt(i, 0).toString().trim());
                        sach.setTenSach(model.getValueAt(i, 1).toString().trim());
                        sach.setTheLoai(model.getValueAt(i, 3).toString().trim());
                        
                        // Lấy Số Lượng (Tạm gắn vào NamXB vì DTO không có cột Số lượng)
                        String soLuongStr = model.getValueAt(i, 4).toString().trim();
                        sach.setNamXB(Integer.parseInt(soLuongStr.isEmpty() ? "0" : soLuongStr));
                        
                        sach.setMaNXB(model.getValueAt(i, 5).toString().trim());
                        
                        // Các trường mặc định
                        sach.setGiaBia(Float.valueOf(0f));   // Float (Object)
                        sach.setIsDeleted(Boolean.FALSE);    // Boolean (Object)
                        sach.setNgonNgu("Tiếng Việt");

                        // Lưu vào DB
                        String result = sachBUS.insert(sach);
                        if (result.equals("Thêm sách thành công!")) {
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
                        "Lưu hoàn tất!\n- Thành công: " + successCount + " cuốn\n- Bỏ qua (Trùng mã/Lỗi): " + failCount + " cuốn", 
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                
                // Tải lại bảng sau khi Save
                btnRefresh.doClick();
            }
        });
    }

    // ===== GETTER =====
    public JTextField getTxtMaSach() { return txtMaSach; }
    public JTextField getTxtTenSach() { return txtTenSach; }
    public JTextField getTxtTacGia() { return txtTacGia; }
    public JComboBox<String> getCbTheLoai() { return cbTheLoai; }
    public JTextField getTxtSoLuong() { return txtSoLuong; }
    public JTextField getTxtNhaXuatBan() { return txtNhaXuatBan; }
    public JTable getTable() { return table; }
    public DefaultTableModel getModel() { return model; }
    public JButton getBtnThem() { return btnThem; }
    public JButton getBtnSua() { return btnSua; }
    public JButton getBtnXoa() { return btnXoa; }

    // ===== TIỆN ÍCH =====
    private JLabel createLabel(String text, Font font) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(font);
        lbl.setForeground(new Color(73, 80, 87));
        return lbl;
    }

    private void setupTable(JTable table) {
        table.setRowHeight(32);
        table.setFont(new Font(tenFont, Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font(tenFont, Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(233, 236, 239));
        table.getTableHeader().setOpaque(false);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(222, 226, 230));
    }

    private JButton createActionButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font(tenFont, Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(150, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}