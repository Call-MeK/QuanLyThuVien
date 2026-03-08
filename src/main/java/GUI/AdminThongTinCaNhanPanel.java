package GUI;

import BUS.NguoiQuanLyBUS;
import DTO.NguoiQuanLyDTO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdminThongTinCaNhanPanel extends JPanel {

    private String tenFont = "Segoe UI";
    private NguoiQuanLyBUS nqlBUS = new NguoiQuanLyBUS();

    private JTextField txtMaNV, txtHoTen, txtNgaySinh, txtGioiTinh, txtSoDienThoai, txtEmail, txtDiaChi, txtVaiTro;
    private JButton btnCapNhat, btnDoiMatKhau;

    public AdminThongTinCaNhanPanel() {
        initComponents();
        
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 20));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(25, 30, 25, 30));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Thông Tin Cá Nhân - Quản Trị Viên");
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

        txtMaNV = createTextField("", false);
        txtHoTen = createTextField("", false);
        txtNgaySinh = createTextField("", false);
        txtGioiTinh = createTextField("", false);
        txtSoDienThoai = createTextField("", true);  
        txtEmail = createTextField("", true);         
        txtDiaChi = createTextField("", true);        
        txtVaiTro = createTextField("", false);

        int row = 0;
        addFormRow(formPanel, gbc, "Mã nhân viên:", txtMaNV, row++);
        addFormRow(formPanel, gbc, "Họ và tên:", txtHoTen, row++);
        addFormRow(formPanel, gbc, "Ngày sinh:", txtNgaySinh, row++);
        addFormRow(formPanel, gbc, "Giới tính:", txtGioiTinh, row++);
        addFormRow(formPanel, gbc, "Số điện thoại:", txtSoDienThoai, row++);
        addFormRow(formPanel, gbc, "Email:", txtEmail, row++);
        addFormRow(formPanel, gbc, "Địa chỉ:", txtDiaChi, row++);
        addFormRow(formPanel, gbc, "Vai trò:", txtVaiTro, row++);

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
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có muốn lưu thay đổi không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String maNV = txtMaNV.getText().trim();
                NguoiQuanLyDTO nql = nqlBUS.getById(maNV);
                if (nql != null) {
                    nql.setSoDienThoai(txtSoDienThoai.getText().trim());
                    nql.setEmail(txtEmail.getText().trim());
                    nql.setDiaChi(txtDiaChi.getText().trim());

                    String msg = nqlBUS.update(nql);
                    JOptionPane.showMessageDialog(this, msg);
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin nhân viên!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
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
            String maNV = txtMaNV.getText().trim();
            NguoiQuanLyDTO nql = nqlBUS.getById(maNV);
            if (nql == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin nhân viên!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JPasswordField txtPassCu = new JPasswordField();
            JPasswordField txtPassMoi = new JPasswordField();
            JPasswordField txtXacNhan = new JPasswordField();

            Object[] message = {
                "Mật khẩu hiện tại:", txtPassCu,
                "Mật khẩu mới:", txtPassMoi,
                "Xác nhận mật khẩu mới:", txtXacNhan
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Đổi Mật Khẩu", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                String passCu = new String(txtPassCu.getPassword());
                String passMoi = new String(txtPassMoi.getPassword());
                String xacNhan = new String(txtXacNhan.getPassword());

                if (passCu.isEmpty() || passMoi.isEmpty() || xacNhan.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else if (!passCu.equals(nql.getMatKhau())) {
                    JOptionPane.showMessageDialog(this, "Mật khẩu hiện tại không đúng!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else if (!passMoi.equals(xacNhan)) {
                    JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else if (passMoi.length() < 4) {
                    JOptionPane.showMessageDialog(this, "Mật khẩu mới phải có ít nhất 4 ký tự!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else {
                    nql.setMatKhau(passMoi);
                    String msg = nqlBUS.update(nql);
                    if (msg.contains("thành công")) {
                        JOptionPane.showMessageDialog(this,
                                "Đổi mật khẩu thành công!\nVui lòng sử dụng mật khẩu mới cho lần đăng nhập sau.");
                    } else {
                        JOptionPane.showMessageDialog(this, msg, "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        bottomPanel.add(btnCapNhat);
        bottomPanel.add(btnDoiMatKhau);

        add(topPanel, BorderLayout.NORTH);
        add(centerWrapper, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void loadData(String maNV) {

        nqlBUS.getAll();
        NguoiQuanLyDTO nql = nqlBUS.getById(maNV);
        if (nql != null) {
            txtMaNV.setText(nql.getMaNQL());
            txtHoTen.setText(nql.getHoTen() != null ? nql.getHoTen() : "");
            txtNgaySinh.setText(nql.getNgaySinh() != null ? nql.getNgaySinh() : "");
            txtGioiTinh.setText(nql.getGioiTinh() != null ? nql.getGioiTinh() : "");
            txtSoDienThoai.setText(nql.getSoDienThoai() != null ? nql.getSoDienThoai() : "");
            txtEmail.setText(nql.getEmail() != null ? nql.getEmail() : "");
            txtDiaChi.setText(nql.getDiaChi() != null ? nql.getDiaChi() : "");
            txtVaiTro.setText(nql.getVaiTro() != null ? nql.getVaiTro() : "");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Không tìm thấy thông tin nhân viên mã: " + maNV,
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
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
}