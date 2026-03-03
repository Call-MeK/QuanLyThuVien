package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ThongTinCaNhanPanel extends JPanel {

    private String tenFont = "Segoe UI";

    // Các trường dữ liệu
    private JTextField txtMaDocGia, txtHoTen, txtNgaySinh, txtSoDienThoai, txtEmail, txtNgayDangKy, txtHanThe;
    private JButton btnCapNhat, btnDoiMatKhau;

    public ThongTinCaNhanPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 20));
        setBackground(Color.WHITE); // Nền trắng chuẩn thiết kế phẳng
        setBorder(new EmptyBorder(25, 30, 25, 30));

        // ==========================================
        // PHẦN TRÊN: TIÊU ĐỀ
        // ==========================================
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Thông Tin Hồ Sơ Độc Giả");
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 22));
        lblTitle.setForeground(new Color(33, 37, 41));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSubtitle = new JLabel("Quản lý thông tin cá nhân và bảo mật tài khoản của bạn");
        lblSubtitle.setFont(new Font(tenFont, Font.ITALIC, 14));
        lblSubtitle.setForeground(new Color(108, 117, 125)); 
        lblSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        topPanel.add(lblTitle);
        topPanel.add(Box.createVerticalStrut(5));
        topPanel.add(lblSubtitle);
        topPanel.add(Box.createVerticalStrut(10));

        // ==========================================
        // PHẦN GIỮA: FORM ĐIỀN THÔNG TIN
        // ==========================================
        // Sử dụng GridBagLayout để căn chỉnh form đẹp mắt
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
            new EmptyBorder(30, 40, 30, 40)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 15, 10, 15); // Khoảng cách giữa các ô

        // Khởi tạo các ô nhập liệu
        txtMaDocGia = createTextField("DG00102", false);
        txtHoTen = createTextField("Nguyễn Văn A", false);
        txtNgaySinh = createTextField("15/08/2004", false);
        txtSoDienThoai = createTextField("0901234567", true); // Cho phép sửa
        txtEmail = createTextField("nguyenvana@gmail.com", true); // Cho phép sửa
        txtNgayDangKy = createTextField("01/01/2026", false);
        txtHanThe = createTextField("31/12/2026", false);

        // Cột 1: Labels
        int row = 0;
        addFormRow(formPanel, gbc, "Mã độc giả (Số thẻ):", txtMaDocGia, row++);
        addFormRow(formPanel, gbc, "Họ và tên:", txtHoTen, row++);
        addFormRow(formPanel, gbc, "Ngày sinh:", txtNgaySinh, row++);
        addFormRow(formPanel, gbc, "Số điện thoại:", txtSoDienThoai, row++);
        addFormRow(formPanel, gbc, "Địa chỉ Email:", txtEmail, row++);
        addFormRow(formPanel, gbc, "Ngày đăng ký thẻ:", txtNgayDangKy, row++);
        addFormRow(formPanel, gbc, "Hạn sử dụng thẻ:", txtHanThe, row++);

        // Bọc formPanel trong một JPanel khác để nó không bị giãn hết màn hình
        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        centerWrapper.setBackground(Color.WHITE);
        centerWrapper.add(formPanel);

        // ==========================================
        // PHẦN DƯỚI: CÁC NÚT CHỨC NĂNG
        // ==========================================
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        btnCapNhat = new JButton("Lưu Thay Đổi");
        btnCapNhat.setBackground(new Color(13, 110, 253)); // Xanh dương
        btnCapNhat.setForeground(Color.WHITE);
        btnCapNhat.setFont(new Font(tenFont, Font.BOLD, 14));
        btnCapNhat.setPreferredSize(new Dimension(150, 40));
        btnCapNhat.setFocusPainted(false);

        btnDoiMatKhau = new JButton("Đổi Mật Khẩu");
        btnDoiMatKhau.setBackground(new Color(100, 116, 139)); // Xám
        btnDoiMatKhau.setForeground(Color.WHITE);
        btnDoiMatKhau.setFont(new Font(tenFont, Font.BOLD, 14));
        btnDoiMatKhau.setPreferredSize(new Dimension(150, 40));
        btnDoiMatKhau.setFocusPainted(false);

        bottomPanel.add(btnCapNhat);
        bottomPanel.add(btnDoiMatKhau);

        // Lắp ráp vào Panel chính
        add(topPanel, BorderLayout.NORTH);
        add(centerWrapper, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Hàm tiện ích tạo JTextField
    private JTextField createTextField(String text, boolean isEditable) {
        JTextField txt = new JTextField(text, 25);
        txt.setFont(new Font(tenFont, Font.PLAIN, 14));
        txt.setPreferredSize(new Dimension(250, 35));
        txt.setEditable(isEditable);
        if (!isEditable) {
            txt.setBackground(new Color(248, 250, 252)); // Nền xám nhạt cho ô không sửa được
            txt.setForeground(Color.DARK_GRAY);
        }
        return txt;
    }

    // Hàm tiện ích thêm 1 dòng vào Form
    private void addFormRow(JPanel panel, GridBagConstraints gbc, String labelText, JTextField txtField, int row) {
        gbc.gridy = row;
        
        // Cột Label
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font(tenFont, Font.BOLD, 14));
        label.setForeground(new Color(71, 85, 105));
        panel.add(label, gbc);

        // Cột TextField
        gbc.gridx = 1;
        panel.add(txtField, gbc);
    }

    // --- CÁC HÀM GETTER BẮT SỰ KIỆN ---
    public JButton getBtnCapNhat() { return btnCapNhat; }
    public JButton getBtnDoiMatKhau() { return btnDoiMatKhau; }
    public String getSoDienThoai() { return txtSoDienThoai.getText(); }
    public String getEmail() { return txtEmail.getText(); }
}