package GUI;

import BUS.DocGiaBUS;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ThongTinCaNhanPanel extends JPanel {

    private String tenFont = "Segoe UI";
    private DocGiaBUS docGiaBUS = new DocGiaBUS();

    private JTextField txtMaDocGia, txtHoTen, txtNgaySinh, txtSoDienThoai, txtEmail, txtNgayDangKy, txtHanThe;
    private JButton btnCapNhat, btnDoiMatKhau;

    public ThongTinCaNhanPanel() {
        initComponents();

    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 20));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(25, 30, 25, 30));

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

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                new EmptyBorder(30, 40, 30, 40)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 15, 10, 15);

        txtMaDocGia = createTextField("", false);
        txtHoTen = createTextField("", true);
        txtNgaySinh = createTextField("", true);
        txtSoDienThoai = createTextField("", true);
        txtEmail = createTextField("", true);
        txtNgayDangKy = createTextField("", false);
        txtHanThe = createTextField("", false);

        int row = 0;
        addFormRow(formPanel, gbc, "Mã độc giả (Số thẻ):", txtMaDocGia, row++);
        addFormRow(formPanel, gbc, "Họ và tên:", txtHoTen, row++);
        addFormRow(formPanel, gbc, "Ngày sinh:", txtNgaySinh, row++);
        addFormRow(formPanel, gbc, "Số điện thoại:", txtSoDienThoai, row++);
        addFormRow(formPanel, gbc, "Địa chỉ Email:", txtEmail, row++);
        addFormRow(formPanel, gbc, "Ngày đăng ký thẻ:", txtNgayDangKy, row++);
        addFormRow(formPanel, gbc, "Hạn sử dụng thẻ:", txtHanThe, row++);

        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        centerWrapper.setBackground(Color.WHITE);
        centerWrapper.add(formPanel);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        btnCapNhat = new JButton("Lưu Thay Đổi");
        btnCapNhat.setBackground(new Color(13, 110, 253));
        btnCapNhat.setForeground(Color.WHITE);
        btnCapNhat.setFont(new Font(tenFont, Font.BOLD, 14));
        btnCapNhat.setPreferredSize(new Dimension(150, 40));
        btnCapNhat.setFocusPainted(false);
        btnCapNhat.setOpaque(true);
        btnCapNhat.setBorderPainted(false);

        btnCapNhat.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn lưu thay đổi không?", "Xác nhận",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String ma = txtMaDocGia.getText();
                String sdtMoi = txtSoDienThoai.getText();
                String emailMoi = txtEmail.getText();

                String msg = docGiaBUS.updateThongTinLienHe(ma, sdtMoi, emailMoi);
                JOptionPane.showMessageDialog(this, msg);
            }
        });

        btnDoiMatKhau = new JButton("Đổi Mật Khẩu");
        btnDoiMatKhau.setBackground(new Color(100, 116, 139));
        btnDoiMatKhau.setForeground(Color.WHITE);
        btnDoiMatKhau.setFont(new Font(tenFont, Font.BOLD, 14));
        btnDoiMatKhau.setPreferredSize(new Dimension(150, 40));
        btnDoiMatKhau.setFocusPainted(false);
        btnDoiMatKhau.setOpaque(true);
        btnDoiMatKhau.setBorderPainted(false);

        btnDoiMatKhau.addActionListener(e -> {
            JTextField txtTenDangNhapCu = new JTextField(currentUsername);
            txtTenDangNhapCu.setEditable(false);
            txtTenDangNhapCu.setBackground(new Color(240, 240, 240));

            JTextField txtTenDangNhapMoi = new JTextField();

            JPasswordField txtPassCu = new JPasswordField();
            JPasswordField txtPassMoi = new JPasswordField();
            JPasswordField txtXacNhan = new JPasswordField();

            Object[] message = { "Tên đăng nhập hiện tại:", txtTenDangNhapCu,
                    "Tên đăng nhập mới:", txtTenDangNhapMoi,
                    "Mật khẩu hiện tại:", txtPassCu,
                    "Mật khẩu mới:", txtPassMoi,
                    "Xác nhận mật khẩu mới:", txtXacNhan
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Đổi Mật Khẩu", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                String tenDangNhapCu = new String(txtTenDangNhapCu.getText());
                String tenDangNhapMoi = new String(txtTenDangNhapMoi.getText());

                String passCu = new String(txtPassCu.getPassword());
                String passMoi = new String(txtPassMoi.getPassword());
                String xacNhan = new String(txtXacNhan.getPassword());

                if (passCu.isEmpty() || passMoi.isEmpty() || xacNhan.isEmpty() || tenDangNhapCu.isEmpty()
                        || tenDangNhapMoi.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                } else if (!passMoi.equals(xacNhan)) {
                    JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                } else if (tenDangNhapCu.equals(tenDangNhapMoi)) {
                    JOptionPane.showMessageDialog(this, "Tên đăng nhập mới không được trùng với tên đăng nhập cũ!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else if (passCu.equals(passMoi)) {
                    JOptionPane.showMessageDialog(this, "Mật khẩu mới không được trùng với mật khẩu cũ!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                } else {

                    JOptionPane.showMessageDialog(this,
                            "Đổi mật khẩu thành công! Vui lòng sử dụng mật khẩu mới cho lần đăng nhập sau.");
                }
            }
        });

        bottomPanel.add(btnCapNhat);
        bottomPanel.add(btnDoiMatKhau);

        add(topPanel, BorderLayout.NORTH);
        add(centerWrapper, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private String currentUsername = "";

    public void loadData(String maDocGia) {
        Object[] data = docGiaBUS.getThongTinCaNhan(maDocGia);
        if (data != null) {
            txtMaDocGia.setText(data[0] != null ? data[0].toString() : "");
            txtHoTen.setText(data[1] != null ? data[1].toString() : "");
            txtNgaySinh.setText(data[2] != null ? data[2].toString() : "");
            txtSoDienThoai.setText(data[3] != null ? data[3].toString() : "");
            txtEmail.setText(data[4] != null ? data[4].toString() : "");
            txtNgayDangKy.setText(data[5] != null ? data[5].toString() : "");
            txtHanThe.setText(data[6] != null ? data[6].toString() : "");

            if (data.length > 7 && data[7] != null) {
                currentUsername = data[7].toString();
            }
        }
    }

    private JTextField createTextField(String text, boolean isEditable) {
        JTextField txt = new JTextField(text, 25);
        txt.setFont(new Font(tenFont, Font.PLAIN, 14));
        txt.setPreferredSize(new Dimension(250, 35));
        txt.setEditable(isEditable);
        if (!isEditable) {
            txt.setBackground(new Color(248, 250, 252));
            txt.setForeground(Color.DARK_GRAY);
        }
        return txt;
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, String labelText, JTextField txtField, int row) {
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font(tenFont, Font.BOLD, 14));
        label.setForeground(new Color(71, 85, 105));
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(txtField, gbc);
    }

    public JButton getBtnCapNhat() {
        return btnCapNhat;
    }

    public JButton getBtnDoiMatKhau() {
        return btnDoiMatKhau;
    }

    public String getSoDienThoai() {
        return txtSoDienThoai.getText();
    }

    public String getEmail() {
        return txtEmail.getText();
    }
}