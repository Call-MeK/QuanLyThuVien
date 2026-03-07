package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import BUS.ConNguoiBUS;
import BUS.SessionManager;
import DTO.ConNguoiDTO;

public class LoginFrame extends JFrame {

    private String tenFont = "Segoe UI";
    private Color colorPrimary = new Color(13, 110, 253);
    private Color colorBackground = new Color(248, 249, 250);
    private Color colorText = new Color(33, 37, 41);

    public LoginFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Đăng Nhập - Hệ Thống Quản Lý Thư Viện");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.setBackground(Color.WHITE);

        // LEFT PANEL
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(colorPrimary);

        JLabel lblBranding = new JLabel("QUẢN LÝ THƯ VIỆN", SwingConstants.CENTER);
        lblBranding.setFont(new Font(tenFont, Font.BOLD, 32));
        lblBranding.setForeground(Color.WHITE);
        lblBranding.setBorder(new EmptyBorder(40, 0, 10, 0));

        JLabel lblSubBranding = new JLabel("Hệ thống quản lý thông minh", SwingConstants.CENTER);
        lblSubBranding.setFont(new Font(tenFont, Font.PLAIN, 18));
        lblSubBranding.setForeground(new Color(233, 236, 239));

        JPanel brandingPanel = new JPanel(new GridLayout(2, 1));
        brandingPanel.setOpaque(false);
        brandingPanel.add(lblBranding);
        brandingPanel.add(lblSubBranding);

        JLabel lblImage = new JLabel();
        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/Images/thuvien.jpg"));
            Image img = icon.getImage().getScaledInstance(350, 250, Image.SCALE_SMOOTH);
            lblImage.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            lblImage.setText("[Hình Ảnh Thư Viện]");
            lblImage.setFont(new Font(tenFont, Font.ITALIC, 16));
            lblImage.setForeground(Color.WHITE);
        }

        leftPanel.add(brandingPanel, BorderLayout.NORTH);
        leftPanel.add(lblImage, BorderLayout.CENTER);

        // RIGHT PANEL
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(new EmptyBorder(50, 50, 50, 50));

        JLabel lblTitle = new JLabel("ĐĂNG NHẬP");
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 28));
        lblTitle.setForeground(colorText);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubTitle = new JLabel("Vui lòng đăng nhập để tiếp tục");
        lblSubTitle.setFont(new Font(tenFont, Font.PLAIN, 14));
        lblSubTitle.setForeground(Color.GRAY);
        lblSubTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSubTitle.setBorder(new EmptyBorder(5, 0, 30, 0));

        JPanel formPanel = new JPanel(new GridLayout(6, 1, 0, 10));
        formPanel.setBackground(Color.WHITE);
        formPanel.setMaximumSize(new Dimension(350, 400));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Font labelFont = new Font(tenFont, Font.BOLD, 14);
        Font inputFont = new Font(tenFont, Font.PLAIN, 15);

        JLabel lblUsername = new JLabel("Tên đăng nhập:");
        lblUsername.setFont(labelFont);
        JTextField txtUsername = new JTextField();
        txtUsername.setFont(inputFont);
        txtUsername.setPreferredSize(new Dimension(300, 40));
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        JLabel lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setFont(labelFont);

        JPanel passPanel = new JPanel(new BorderLayout(5, 0));
        passPanel.setBackground(Color.WHITE);
        final JPasswordField txtPassword = new JPasswordField();
        txtPassword.setFont(inputFont);
        txtPassword.setPreferredSize(new Dimension(250, 40));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        final JToggleButton btnTogglePass = new JToggleButton("Hiện");
        btnTogglePass.setBackground(Color.WHITE);
        btnTogglePass.setFocusPainted(false);
        btnTogglePass.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        btnTogglePass.setPreferredSize(new Dimension(60, 40));
        btnTogglePass.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnTogglePass.addActionListener(ev -> {
            if (btnTogglePass.isSelected()) {
                txtPassword.setEchoChar((char) 0);
                btnTogglePass.setText("Ẩn");
            } else {
                txtPassword.setEchoChar('•');
                btnTogglePass.setText("Hiện");
            }
        });

        passPanel.add(txtPassword, BorderLayout.CENTER);
        passPanel.add(btnTogglePass, BorderLayout.EAST);

        JLabel lblRole = new JLabel("Vai trò:");
        lblRole.setFont(labelFont);
        JComboBox<String> cbRole = new JComboBox<>(new String[]{"Độc giả (User)", "Quản trị viên (Admin)"});
        cbRole.setFont(inputFont);
        cbRole.setBackground(Color.WHITE);

        formPanel.add(lblUsername);
        formPanel.add(txtUsername);
        formPanel.add(lblPassword);
        formPanel.add(passPanel);
        formPanel.add(lblRole);
        formPanel.add(cbRole);

        JButton btnLogin = new JButton("ĐĂNG NHẬP");
        btnLogin.setFont(new Font(tenFont, Font.BOLD, 16));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(colorPrimary);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(350, 45));
        this.getRootPane().setDefaultButton(btnLogin);

        btnLogin.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) { btnLogin.setBackground(new Color(11, 94, 215)); }
            public void mouseExited(MouseEvent evt)  { btnLogin.setBackground(colorPrimary); }
        });

        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword());
            String role = (String) cbRole.getSelectedItem();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ConNguoiBUS cnBus = new ConNguoiBUS();
            boolean loginSuccess = false;
            String loggedInUserId = "";
            String loggedInUserName = "";
            String userRolePrefix = role.equals("Quản trị viên (Admin)") ? "NV" : "DG";

            try {
                for (ConNguoiDTO cn : cnBus.getListConNguoi()) {
                    if (cn.getTenDangNhap().equals(username) && cn.getMatKhau().equals(password)) {
                        if (cn.getMaNguoi().startsWith(userRolePrefix)) {
                            loginSuccess = true;
                            loggedInUserId = cn.getMaNguoi();
                            loggedInUserName = cn.getHoTen();
                            break;
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Lỗi kết nối cơ sở dữ liệu:\n" + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (loginSuccess) {
                if (role.equals("Quản trị viên (Admin)")) {
                    SessionManager.getInstance().login(loggedInUserId, loggedInUserName, "Admin");
                    JOptionPane.showMessageDialog(this,
                            "Đăng nhập Admin thành công!\nXin chào " + loggedInUserName,
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    try {
                        AdminFrame adminFrame = new AdminFrame(loggedInUserId);
                        adminFrame.setVisible(true);
                        this.dispose();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this,
                                "Lỗi mở giao diện Admin:\n" + ex.getMessage(),
                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    SessionManager.getInstance().login(loggedInUserId, loggedInUserName, "DocGia");
                    JOptionPane.showMessageDialog(this,
                            "Đăng nhập thành công!\nXin chào " + loggedInUserName,
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    try {
                        UserHomeFrame userFrame = new UserHomeFrame(loggedInUserId);
                        userFrame.setVisible(true);
                        this.dispose();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this,
                                "Lỗi mở giao diện Độc Giả:\n" + ex.getMessage(),
                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Sai tên đăng nhập, mật khẩu hoặc sai vai trò!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(lblTitle);
        rightPanel.add(lblSubTitle);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(formPanel);
        rightPanel.add(Box.createVerticalStrut(30));
        rightPanel.add(btnLogin);
        rightPanel.add(Box.createVerticalGlue());

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
        add(mainPanel, BorderLayout.CENTER);

        SwingUtilities.invokeLater(() -> txtUsername.requestFocusInWindow());
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) { ex.printStackTrace(); }
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}