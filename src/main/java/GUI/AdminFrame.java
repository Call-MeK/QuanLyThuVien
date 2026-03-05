package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import BUS.SessionManager;

public class AdminFrame extends JFrame {

    private JPanel panelMenu;
    private JPanel panelContent;
    private CardLayout cardLayout;

    private String tenFont = "Segoe UI";
    private Color colorMenuBg = new Color(33, 37, 41);
    private Color colorMenuHover = new Color(52, 58, 64);
    private Color colorBackground = new Color(248, 249, 250);
    private Color colorPrimary = new Color(13, 110, 253);

    private String maNVDangNhap = ""; // Biến hứng mã nhân viên (giống maDocGiaDangNhap bên UserHomeFrame)

    // CONSTRUCTOR NHẬN MÃ TỪ LOGIN (giống UserHomeFrame)
    public AdminFrame(String maNV) {
        this.maNVDangNhap = maNV;
        initComponents();
    }

    // Constructor không tham số (để code cũ không bị lỗi, dùng session)
    public AdminFrame() {
        if (SessionManager.getInstance().isLoggedIn()) {
            this.maNVDangNhap = SessionManager.getInstance().getMaNguoi();
        }
        initComponents();
    }

    // Getter để các panel con lấy mã NV nếu cần
    public String getMaNVDangNhap() {
        return maNVDangNhap;
    }

    private void initComponents() {
        setTitle("Quản Lý Thư Viện - Admin System");
        setSize(1250, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // ==========================================
        // 1. CỘT MENU (BÊN TRÁI)
        // ==========================================
        panelMenu = new JPanel();
        panelMenu.setBackground(colorMenuBg);
        panelMenu.setPreferredSize(new Dimension(240, 0));
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));

        // Tiêu đề ADMIN
        JLabel lblTitle = new JLabel("ADMIN", SwingConstants.CENTER);
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 22));
        lblTitle.setForeground(colorPrimary);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 5, 0));

        // Hiển thị tên + mã người đăng nhập
        String tenHienThi = SessionManager.getInstance().isLoggedIn()
                ? SessionManager.getInstance().getHoTen()
                : maNVDangNhap;

        JLabel lblAdminName = new JLabel(tenHienThi, SwingConstants.CENTER);
        lblAdminName.setFont(new Font(tenFont, Font.PLAIN, 13));
        lblAdminName.setForeground(new Color(173, 181, 189));
        lblAdminName.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblAdminName.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));

        panelMenu.add(lblTitle);
        panelMenu.add(lblAdminName);

        JButton btnTrangChu = createMenuButton("Trang Chủ");
        JButton btnQuanLySach = createMenuButton("Quản Lý Sách");
        JButton btnQuanLyDocGia = createMenuButton("Quản Lý Độc Giả");
        JButton btnQuanLyMuonTra = createMenuButton("Quản Lý Mượn Trả");
        JButton btnQuanLyNhapSach = createMenuButton("Quản Lý Nhập Sách");
        JButton btnQuanLyPhiPhat = createMenuButton("Quản Lý Phí Phạt");
        JButton btnThongKe = createMenuButton("Thống Kê");
        JButton btnThongTinCN = createMenuButton("Thông Tin Cá Nhân");

        panelMenu.add(btnTrangChu);
        panelMenu.add(Box.createVerticalStrut(5));
        panelMenu.add(btnQuanLySach);
        panelMenu.add(Box.createVerticalStrut(5));
        panelMenu.add(btnQuanLyDocGia);
        panelMenu.add(Box.createVerticalStrut(5));
        panelMenu.add(btnQuanLyMuonTra);
        panelMenu.add(Box.createVerticalStrut(5));
        panelMenu.add(btnQuanLyNhapSach);
        panelMenu.add(Box.createVerticalStrut(5));
        panelMenu.add(btnQuanLyPhiPhat);
        panelMenu.add(Box.createVerticalStrut(5));
        panelMenu.add(btnThongKe);
        panelMenu.add(Box.createVerticalStrut(5));
        panelMenu.add(btnThongTinCN);

        panelMenu.add(Box.createVerticalGlue());

        JButton btnDangXuat = createMenuButton("Đăng Xuất");
        btnDangXuat.setForeground(new Color(220, 53, 69));
        panelMenu.add(btnDangXuat);
        panelMenu.add(Box.createVerticalStrut(25));

        JScrollPane scrollMenu = new JScrollPane(panelMenu);
        scrollMenu.setBorder(null);
        scrollMenu.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollMenu, BorderLayout.WEST);

        // ==========================================
        // 2. VÙNG NỘI DUNG (CARDLAYOUT)
        // ==========================================
        cardLayout = new CardLayout();
        panelContent = new JPanel(cardLayout);
        panelContent.setBackground(colorBackground);

        panelContent.add(new AdminTrangChuPanel(), "CardTrangChu");
        panelContent.add(new AdminQuanLySachPanel(), "CardSach");
        panelContent.add(new AdminQuanLyDocGiaPanel(), "CardDocGia");
        panelContent.add(new AdminQuanLyMuonTraPanel(), "CardMuonTra");
        panelContent.add(new AdminQuanLyNhapSachPanel(), "CardNhapSach");
        panelContent.add(new AdminQuanLyPhiPhatPanel(), "CardPhiPhat");
        panelContent.add(new ThongKePanel(), "CardThongKe");
        AdminThongTinCaNhanPanel panelThongTinCN = new AdminThongTinCaNhanPanel();
        panelContent.add(panelThongTinCN, "CardThongTinCN");

        // Truyền mã NV cho panel thông tin (giống cách UserHomeFrame truyền cho ThongTinCaNhanPanel)
        if (!maNVDangNhap.isEmpty()) {
            panelThongTinCN.loadData(maNVDangNhap);
        }

        add(panelContent, BorderLayout.CENTER);

        // ==========================================
        // 3. SỰ KIỆN CHUYỂN TRANG VÀ ĐĂNG XUẤT
        // ==========================================
        btnTrangChu.addActionListener(e -> cardLayout.show(panelContent, "CardTrangChu"));
        btnQuanLySach.addActionListener(e -> cardLayout.show(panelContent, "CardSach"));
        btnQuanLyDocGia.addActionListener(e -> cardLayout.show(panelContent, "CardDocGia"));
        btnQuanLyMuonTra.addActionListener(e -> cardLayout.show(panelContent, "CardMuonTra"));
        btnQuanLyNhapSach.addActionListener(e -> cardLayout.show(panelContent, "CardNhapSach"));
        btnQuanLyPhiPhat.addActionListener(e -> cardLayout.show(panelContent, "CardPhiPhat"));
        btnThongKe.addActionListener(e -> cardLayout.show(panelContent, "CardThongKe"));
        btnThongTinCN.addActionListener(e -> cardLayout.show(panelContent, "CardThongTinCN"));

        // Đăng xuất: clear session + quay lại login
        btnDangXuat.addActionListener(e -> {
            UIManager.put("OptionPane.messageFont", new Font(tenFont, Font.PLAIN, 15));
            UIManager.put("OptionPane.buttonFont", new Font(tenFont, Font.BOLD, 14));
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn đăng xuất?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                SessionManager.getInstance().logout(); // Xóa session
                dispose();
                new LoginFrame().setVisible(true);
            }
        });

        cardLayout.show(panelContent, "CardTrangChu");
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(250, 50));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 10));
        btn.setFont(new Font(tenFont, Font.BOLD, 15));
        btn.setForeground(new Color(222, 226, 230));
        btn.setBackground(colorMenuBg);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { btn.setBackground(colorMenuHover); }
            @Override
            public void mouseExited(MouseEvent e) { btn.setBackground(colorMenuBg); }
        });
        return btn;
    }

    public static void main(String[] args) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) { }
        // Test: truyền mã tạm
        SwingUtilities.invokeLater(() -> new AdminFrame("NV00000001").setVisible(true));
    }
}