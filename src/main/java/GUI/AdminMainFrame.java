import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMainFrame extends JFrame {

    private JPanel cardPanel;
    private CardLayout cardLayout;

    // Các nút điều hướng (Sidebar)
    private JButton btnQuanLySach;
    private JButton btnQuanLyDocGia;
    private JButton btnQuanLyMuonTra;
    private JButton btnQuanLyNhapSach;
    private JButton btnQuanLyPhat;
    private JButton btnThongKe;
    private JButton btnDangXuat;

    public AdminMainFrame() {
        setTitle("Hệ Thống Quản Lý Thư Viện - Dành cho Quản Trị Viên");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Hiển thị ở giữa màn hình
        setLayout(new BorderLayout());

        initComponents();
    }

    private void initComponents() {
        // ===== SIDEBAR (Panel điều hướng bên trái) =====
        JPanel sideBar = new JPanel();
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setBackground(new Color(44, 62, 80)); // Màu xanh đen hiện đại
        sideBar.setPreferredSize(new Dimension(200, 0));
        sideBar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // Tiêu đề Sidebar
        JLabel lblTitle = new JLabel("ADMIN PANEL");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        sideBar.add(lblTitle);
        sideBar.add(Box.createRigidArea(new Dimension(0, 30))); // Khoảng cách

        // Khởi tạo các nút
        btnQuanLySach = createSidebarButton("📚 Quản lý Sách");
        btnQuanLyDocGia = createSidebarButton("👥 Quản lý Độc giả");
        btnQuanLyMuonTra = createSidebarButton("🔄 Quản lý Mượn/Trả");
        btnQuanLyNhapSach = createSidebarButton("📥 Quản lý Nhập sách");
        btnQuanLyPhat = createSidebarButton("⚠️ Quản lý Vi phạm");
        btnThongKe = createSidebarButton("📊 Báo cáo Thống kê");
        btnDangXuat = createSidebarButton("🚪 Đăng xuất");

        // Thêm nút vào Sidebar
        sideBar.add(btnQuanLySach);
        sideBar.add(Box.createRigidArea(new Dimension(0, 10)));
        sideBar.add(btnQuanLyDocGia);
        sideBar.add(Box.createRigidArea(new Dimension(0, 10)));
        sideBar.add(btnQuanLyMuonTra);
        sideBar.add(Box.createRigidArea(new Dimension(0, 10)));
        sideBar.add(btnQuanLyNhapSach);
        sideBar.add(Box.createRigidArea(new Dimension(0, 10)));
        sideBar.add(btnQuanLyPhat);
        sideBar.add(Box.createRigidArea(new Dimension(0, 10)));
        sideBar.add(btnThongKe);
        
        // Đẩy nút đăng xuất xuống dưới cùng
        sideBar.add(Box.createVerticalGlue());
        sideBar.add(btnDangXuat);

        // ===== CARD PANEL (Khu vực hiển thị nội dung chính) =====
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(Color.WHITE);

        // Giả lập các Panel chức năng (Bạn sẽ thay thế bằng các Panel thực tế của dự án)
        JPanel pnlQuanLySach = createPlaceholderPanel("Giao diện Quản lý Sách (Thêm, Sửa, Xóa)");
        JPanel pnlQuanLyDocGia = createPlaceholderPanel("Giao diện Quản lý Độc giả & Thẻ thư viện");
        JPanel pnlQuanLyMuonTra = createPlaceholderPanel("Giao diện Duyệt Mượn/Trả sách");
        JPanel pnlQuanLyNhapSach = createPlaceholderPanel("Giao diện Phiếu Nhập Sách & NXB");
        JPanel pnlQuanLyPhat = createPlaceholderPanel("Giao diện Quản lý Phiếu Phạt");
        JPanel pnlThongKe = createPlaceholderPanel("Giao diện Báo cáo & Thống kê");

        // Thêm các Panel vào CardLayout với tên định danh
        cardPanel.add(pnlQuanLySach, "QuanLySach");
        cardPanel.add(pnlQuanLyDocGia, "QuanLyDocGia");
        cardPanel.add(pnlQuanLyMuonTra, "QuanLyMuonTra");
        cardPanel.add(pnlQuanLyNhapSach, "QuanLyNhapSach");
        cardPanel.add(pnlQuanLyPhat, "QuanLyPhat");
        cardPanel.add(pnlThongKe, "ThongKe");

        // ===== GẮN SỰ KIỆN CHO CÁC NÚT ĐỂ CHUYỂN PANEL =====
        btnQuanLySach.addActionListener(e -> cardLayout.show(cardPanel, "QuanLySach"));
        btnQuanLyDocGia.addActionListener(e -> cardLayout.show(cardPanel, "QuanLyDocGia"));
        btnQuanLyMuonTra.addActionListener(e -> cardLayout.show(cardPanel, "QuanLyMuonTra"));
        btnQuanLyNhapSach.addActionListener(e -> cardLayout.show(cardPanel, "QuanLyNhapSach"));
        btnQuanLyPhat.addActionListener(e -> cardLayout.show(cardPanel, "QuanLyPhat"));
        btnThongKe.addActionListener(e -> cardLayout.show(cardPanel, "ThongKe"));
        
        btnDangXuat.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn đăng xuất?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                this.dispose();
                // TODO: Mở lại khung Đăng nhập (LoginFrame) tại đây
            }
        });

        // Thêm Sidebar và CardPanel vào Frame chính
        add(sideBar, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);
    }

    // Hàm tiện ích tạo nút bấm cho Sidebar
    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(180, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(52, 73, 94));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hiệu ứng hover cho nút
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(41, 128, 185));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(52, 73, 94));
            }
        });
        return button;
    }

    // Hàm tạo Panel tạm thời (Placeholder)
    private JPanel createPlaceholderPanel(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.GRAY);
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    public static void main(String[] args) {
        // Đặt Look and Feel cho giống Windows/Hệ điều hành hiện tại
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new AdminMainFrame().setVisible(true);
        });
    }
}