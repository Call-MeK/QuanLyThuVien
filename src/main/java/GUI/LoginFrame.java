package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import BUS.ConNguoiBUS;
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

        // Background Panel
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.setBackground(Color.WHITE);

        // ==========================================
        // 1. LEFT PANEL (Hình ảnh / Branding)
        // ==========================================
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

        // ==========================================
        // 2. RIGHT PANEL (Form Đăng Nhập)
        // ==========================================
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

        // Form fields
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
        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setFont(inputFont);
        txtPassword.setPreferredSize(new Dimension(300, 40));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        JLabel lblRole = new JLabel("Vai trò:");
        lblRole.setFont(labelFont);
        JComboBox<String> cbRole = new JComboBox<>(new String[] { "Độc giả (User)", "Quản trị viên (Admin)" });
        cbRole.setFont(inputFont);
        cbRole.setBackground(Color.WHITE);
        cbRole.setPreferredSize(new Dimension(300, 40));

        formPanel.add(lblUsername);
        formPanel.add(txtUsername);
        formPanel.add(lblPassword);
        formPanel.add(txtPassword);
        formPanel.add(lblRole);
        formPanel.add(cbRole);

        // Login Button
        JButton btnLogin = new JButton("ĐĂNG NHẬP");
        btnLogin.setFont(new Font(tenFont, Font.BOLD, 16));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(colorPrimary);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(350, 45));

        // Hover effect cho button
        btnLogin.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btnLogin.setBackground(new Color(11, 94, 215));
            }
            public void mouseExited(MouseEvent evt) {
                btnLogin.setBackground(colorPrimary);
            }
        });

        // Event Đăng nhập xử lý qua BUS
        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());
            String role = (String) cbRole.getSelectedItem();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ConNguoiBUS cnBus = new ConNguoiBUS();
            boolean loginSuccess = false;
            String userRolePrefix = role.equals("Quản trị viên (Admin)") ? "NV" : "DG";

            for (ConNguoiDTO cn : cnBus.getListConNguoi()) {
                if (cn.getTenDangNhap().equals(username) && cn.getMatKhau().equals(password)) {
                    if (cn.getMaNguoi().startsWith(userRolePrefix)) {
                        loginSuccess = true;
                        break;
                    }
                }
            }

            if (loginSuccess) {
                if (role.equals("Quản trị viên (Admin)")) {
                    JOptionPane.showMessageDialog(this, "Đăng nhập Admin thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    new AdminFrame().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Đăng nhập Độc giả thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    new UserHomeFrame().setVisible(true);
                }
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Sai tên đăng nhập, mật khẩu hoặc sai vai trò!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}