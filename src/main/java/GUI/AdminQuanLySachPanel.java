package GUI;

import javax.swing.*;
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

    private JTextField txtMaSach, txtTenSach, txtTacGia, txtSoLuong, txtNhaXuatBan;
    private JComboBox<String> cbTheLoai;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi;

    public AdminQuanLySachPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBackground(colorBackground);
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel lblTitle = new JLabel("Quản Lý Thông Tin Sách");
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 24));
        lblTitle.setForeground(new Color(33, 37, 41));
        add(lblTitle, BorderLayout.NORTH);

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
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        setupTable(table);

       try {
            List<SachDTO> listSach = new SachBUS().getAll();
            for (SachDTO s : listSach) {
                // Đã sắp xếp lại đúng thứ tự: Mã | Tên | Tác Giả | Thể Loại | Số Lượng | NXB
                model.addRow(new Object[]{
                    s.getMaSach(), 
                    s.getTenSach(), 
                    "Nhiều tác giả", // Tạm để String vì DanhSachTacGia cần xử lý vòng lặp riêng
                    s.getTheLoai(), 
                    s.getSoLuong(),  // Đã sửa thành getSoLuong()
                    s.getMaNXB()
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);

        add(pnlCenter, BorderLayout.CENTER);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        pnlButtons.setBackground(colorBackground);

        btnThem = createActionButton("Thêm Mới", new Color(25, 135, 84));
        btnSua = createActionButton("Cập Nhật", new Color(255, 193, 7));
        btnSua.setForeground(Color.BLACK);
        btnXoa = createActionButton("Xóa Dữ Liệu", new Color(220, 53, 69));
        btnLamMoi = createActionButton("Làm Mới", new Color(108, 117, 125));

        pnlButtons.add(btnThem);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnLamMoi);

        add(pnlButtons, BorderLayout.SOUTH);
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
    public JButton getBtnLamMoi() { return btnLamMoi; }

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