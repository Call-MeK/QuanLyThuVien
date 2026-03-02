package GUI;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class UserMainFrame extends JFrame {

    private JPanel panelContent;
    private CardLayout cardLayout;

    private JButton btnTrangChu;
    private JButton btnTimSach;
    private JButton btnSachDangMuon;
    private JButton btnPhiPhat;
    private JButton btnThongTinCaNhan;
    private JButton btnDangXuat;

    public UserMainFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Quản Lý Thư Viện - User");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== PANEL TRÁI - MENU =====
        JPanel panelMenu = new JPanel();
        panelMenu.setBackground(new Color(50, 50, 50));
        panelMenu.setPreferredSize(new Dimension(200, 600));
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));

        JLabel lblTitle = new JLabel("Thư Viện SGU", SwingConstants.CENTER);
        lblTitle.setForeground(new Color(0, 200, 255));
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        panelMenu.add(lblTitle);

        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(180, 2));
        separator.setForeground(new Color(100, 100, 100));
        panelMenu.add(separator);
        panelMenu.add(Box.createVerticalStrut(10));

        btnTrangChu = createMenuButton("Trang Chủ");
        btnTimSach = createMenuButton("Tìm Sách");
        btnSachDangMuon = createMenuButton("Sách Đang Mượn");
        btnPhiPhat = createMenuButton("Phí Phạt");
        btnThongTinCaNhan = createMenuButton("Thông Tin Cá Nhân");

        panelMenu.add(btnTrangChu);
        panelMenu.add(Box.createVerticalStrut(5));
        panelMenu.add(btnTimSach);
        panelMenu.add(Box.createVerticalStrut(5));
        panelMenu.add(btnSachDangMuon);
        panelMenu.add(Box.createVerticalStrut(5));
        panelMenu.add(btnPhiPhat);
        panelMenu.add(Box.createVerticalStrut(5));
        panelMenu.add(btnThongTinCaNhan);

        panelMenu.add(Box.createVerticalGlue());

        JSeparator separator2 = new JSeparator();
        separator2.setMaximumSize(new Dimension(180, 2));
        separator2.setForeground(new Color(100, 100, 100));
        panelMenu.add(separator2);
        panelMenu.add(Box.createVerticalStrut(5));

        btnDangXuat = createMenuButton("Đăng Xuất");
        btnDangXuat.setForeground(new Color(255, 100, 100));
        panelMenu.add(btnDangXuat);
        panelMenu.add(Box.createVerticalStrut(15));

        // ===== PANEL PHẢI - NỘI DUNG =====
        cardLayout = new CardLayout();
        panelContent = new JPanel(cardLayout);
        panelContent.setBackground(new Color(230, 230, 230));

        panelContent.add(createTrangChuPanel(), "TrangChu");
        panelContent.add(new TimKiemSachPanel(), "TimSach");
        panelContent.add(new SachDangMuonPanel(), "SachDangMuon");
        panelContent.add(new PhiPhatPanel(), "PhiPhat");
        panelContent.add(new ThongTinCaNhanPanel(), "ThongTinCaNhan");

        cardLayout.show(panelContent, "TrangChu");

        // ===== SỰ KIỆN CHUYỂN TRANG =====
        btnTrangChu.addActionListener(e -> cardLayout.show(panelContent, "TrangChu"));
        btnTimSach.addActionListener(e -> cardLayout.show(panelContent, "TimSach"));
        btnSachDangMuon.addActionListener(e -> cardLayout.show(panelContent, "SachDangMuon"));
        btnPhiPhat.addActionListener(e -> cardLayout.show(panelContent, "PhiPhat"));
        btnThongTinCaNhan.addActionListener(e -> cardLayout.show(panelContent, "ThongTinCaNhan"));

        // ===== THANH TIÊU ĐỀ TRÊN =====
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(new Color(40, 40, 40));
        panelHeader.setPreferredSize(new Dimension(1000, 40));

        JLabel lblHeader = new JLabel("   Quản Lý Thư Viện", SwingConstants.LEFT);
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 16));
        panelHeader.add(lblHeader, BorderLayout.WEST);

        add(panelHeader, BorderLayout.NORTH);
        add(panelMenu, BorderLayout.WEST);
        add(panelContent, BorderLayout.CENTER);
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(200, 45));
        btn.setPreferredSize(new Dimension(200, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(50, 50, 50));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JPanel createTrangChuPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 240, 240));

        JLabel lbl = new JLabel("Chào mừng đến Thư Viện SGU!", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 24));
        lbl.setForeground(new Color(50, 50, 50));
        panel.add(lbl, BorderLayout.CENTER);

        return panel;
    }

    // ===== GETTER =====
    public JButton getBtnTrangChu() { return btnTrangChu; }
    public JButton getBtnTimSach() { return btnTimSach; }
    public JButton getBtnSachDangMuon() { return btnSachDangMuon; }
    public JButton getBtnPhiPhat() { return btnPhiPhat; }
    public JButton getBtnThongTinCaNhan() { return btnThongTinCaNhan; }
    public JButton getBtnDangXuat() { return btnDangXuat; }
    public JPanel getPanelContent() { return panelContent; }
    public CardLayout getCardLayout() { return cardLayout; }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserMainFrame frame = new UserMainFrame();
            frame.setVisible(true);
        });
    }
}