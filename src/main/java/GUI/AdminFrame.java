package GUI;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.*;
import BUS.SessionManager;

public class AdminFrame extends JFrame {

    private JPanel panelContent;
    private CardLayout cardLayout;
    private JTree menuTree;

    private String tenFont       = "Segoe UI";
    private Color colorMenuBg    = new Color(33, 37, 41);
    private Color colorMenuHover = new Color(52, 58, 64);
    private Color colorBackground = new Color(248, 249, 250);
    private Color colorPrimary   = new Color(13, 110, 253);

    private String maNVDangNhap = "";

    public AdminFrame(String maNV) {
        this.maNVDangNhap = maNV;
        initComponents();
    }

    public AdminFrame() {
        if (SessionManager.getInstance().isLoggedIn()) {
            this.maNVDangNhap = SessionManager.getInstance().getMaNguoi();
        }
        initComponents();
    }

    private void initComponents() {
        setTitle("Quản Lý Thư Viện - Hệ Thống Admin");
        setSize(1300, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // ==========================================
        // 1. CỘT MENU (JTREE)
        // ==========================================
        JPanel panelSidebar = new JPanel(new BorderLayout());
        panelSidebar.setBackground(colorMenuBg);
        panelSidebar.setPreferredSize(new Dimension(260, 0));

        JPanel panelHeader = new JPanel();
        panelHeader.setLayout(new BoxLayout(panelHeader, BoxLayout.Y_AXIS));
        panelHeader.setOpaque(false);

        JLabel lblTitle = new JLabel("ADMIN SYSTEM", SwingConstants.CENTER);
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 20));
        lblTitle.setForeground(colorPrimary);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 5, 0));

        String tenHienThi = SessionManager.getInstance().isLoggedIn()
                ? SessionManager.getInstance().getHoTen() : maNVDangNhap;
        JLabel lblAdminName = new JLabel(tenHienThi);
        lblAdminName.setFont(new Font(tenFont, Font.ITALIC, 13));
        lblAdminName.setForeground(new Color(173, 181, 189));
        lblAdminName.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblAdminName.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        panelHeader.add(lblTitle);
        panelHeader.add(lblAdminName);
        panelSidebar.add(panelHeader, BorderLayout.NORTH);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Hệ Thống");
        DefaultMutableTreeNode nodeTrangChu = new DefaultMutableTreeNode("Trang Chủ");
        DefaultMutableTreeNode nodeQuanLy   = new DefaultMutableTreeNode("Quản Lý");
        nodeQuanLy.add(new DefaultMutableTreeNode("Quản Lý Sách"));
        nodeQuanLy.add(new DefaultMutableTreeNode("Quản Lý Độc Giả"));
        nodeQuanLy.add(new DefaultMutableTreeNode("Quản Lý Mượn Trả"));
        nodeQuanLy.add(new DefaultMutableTreeNode("Quản Lý Nhập Sách"));
        nodeQuanLy.add(new DefaultMutableTreeNode("Quản Lý Phí Phạt"));
        DefaultMutableTreeNode nodeThongKe  = new DefaultMutableTreeNode("Thống Kê");
        DefaultMutableTreeNode nodeThongTin = new DefaultMutableTreeNode("Thông Tin Cá Nhân");
        DefaultMutableTreeNode nodeDangXuat = new DefaultMutableTreeNode("Đăng Xuất");

        root.add(nodeTrangChu);
        root.add(nodeQuanLy);
        root.add(nodeThongKe);
        root.add(nodeThongTin);
        root.add(nodeDangXuat);

        menuTree = new JTree(root);
        menuTree.setRootVisible(false);
        menuTree.setShowsRootHandles(true);
        menuTree.setBackground(colorMenuBg);
        menuTree.setFont(new Font(tenFont, Font.PLAIN, 15));
        menuTree.setRowHeight(40);

        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        renderer.setTextNonSelectionColor(new Color(222, 226, 230));
        renderer.setTextSelectionColor(Color.WHITE);
        renderer.setBackgroundNonSelectionColor(colorMenuBg);
        renderer.setBackgroundSelectionColor(colorMenuHover);
        renderer.setBorderSelectionColor(null);
        menuTree.setCellRenderer(renderer);

        JScrollPane scrollMenu = new JScrollPane(menuTree);
        scrollMenu.setBorder(null);
        scrollMenu.setBackground(colorMenuBg);
        scrollMenu.getViewport().setBackground(colorMenuBg);
        panelSidebar.add(scrollMenu, BorderLayout.CENTER);
        add(panelSidebar, BorderLayout.WEST);

        // ==========================================
        // 2. VÙNG NỘI DUNG (CARDLAYOUT)
        // ==========================================
        cardLayout    = new CardLayout();
        panelContent  = new JPanel(cardLayout);
        panelContent.setBackground(colorBackground);

        // ===== Lưu reference để nối callback =====
        AdminQuanLySachPanel    sachPanel     = new AdminQuanLySachPanel();
        AdminQuanLyNhapSachPanel nhapSachPanel = new AdminQuanLyNhapSachPanel();

        // ===== KEY FIX: Khi lưu phiếu nhập xong → panel sách tự reload số cuốn =====
        nhapSachPanel.setOnSaveCallback(() -> sachPanel.loadData());

        panelContent.add(new AdminTrangChuPanel(),          "CardTrangChu");
        panelContent.add(sachPanel,                         "CardSach");
        panelContent.add(new AdminQuanLyDocGiaPanel(),      "CardDocGia");
        panelContent.add(new AdminQuanLyMuonTraPanel(),     "CardMuonTra");
        panelContent.add(nhapSachPanel,                     "CardNhapSach");
        panelContent.add(new AdminQuanLyPhiPhatPanel(),     "CardPhiPhat");
        panelContent.add(new ThongKePanel(),                "CardThongKe");

        AdminThongTinCaNhanPanel panelThongTinCN = new AdminThongTinCaNhanPanel();
        panelContent.add(panelThongTinCN, "CardThongTinCN");

        if (!maNVDangNhap.isEmpty()) {
            panelThongTinCN.loadData(maNVDangNhap);
        }

        add(panelContent, BorderLayout.CENTER);

        // ==========================================
        // 3. XỬ LÝ SỰ KIỆN CHUYỂN TRANG
        // ==========================================
        menuTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode =
                        (DefaultMutableTreeNode) menuTree.getLastSelectedPathComponent();
                if (selectedNode == null) return;

                switch (selectedNode.toString()) {
                    case "Trang Chủ":
                        cardLayout.show(panelContent, "CardTrangChu");
                        break;
                    case "Quản Lý Sách":
                        // Reload lại danh sách sách mỗi khi chuyển sang tab này
                        sachPanel.loadData();
                        cardLayout.show(panelContent, "CardSach");
                        break;
                    case "Quản Lý Độc Giả":
                        cardLayout.show(panelContent, "CardDocGia");
                        break;
                    case "Quản Lý Mượn Trả":
                        cardLayout.show(panelContent, "CardMuonTra");
                        break;
                    case "Quản Lý Nhập Sách":
                        cardLayout.show(panelContent, "CardNhapSach");
                        break;
                    case "Quản Lý Phí Phạt":
                        cardLayout.show(panelContent, "CardPhiPhat");
                        break;
                    case "Thống Kê":
                        cardLayout.show(panelContent, "CardThongKe");
                        break;
                    case "Thông Tin Cá Nhân":
                        cardLayout.show(panelContent, "CardThongTinCN");
                        break;
                    case "Đăng Xuất":
                        handleLogout();
                        break;
                }
            }
        });

        cardLayout.show(panelContent, "CardTrangChu");
    }

    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn đăng xuất?",
                "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            SessionManager.getInstance().logout();
            dispose();
            new LoginFrame().setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminFrame("NV00000001").setVisible(true));
    }
}