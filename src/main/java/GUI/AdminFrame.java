package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminFrame extends JFrame {

    private JPanel panelMenu;
    private JPanel panelContent;
    private CardLayout cardLayout;

    private String tenFont = "Arial";
    private Color colorMenuBg = new Color(40, 44, 52);
    private Color colorMenuHover = new Color(60, 64, 72);
    private Color colorBackground = new Color(248, 250, 252);

    public AdminFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Quản Lý Thư Viện - Admin");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ==========================================
        // 1. TẠO CỘT MENU (BÊN TRÁI) - DÙNG BOXLAYOUT ĐỂ KHÔNG BỊ LỖI VỊ TRÍ
        // ==========================================
        panelMenu = new JPanel();
        panelMenu.setBackground(colorMenuBg);
        panelMenu.setPreferredSize(new Dimension(220, 700));
        // Sử dụng BoxLayout xếp theo chiều dọc (Y_AXIS)
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));

        // Tiêu đề Menu
        JLabel lblTitle = new JLabel("ADMIN", SwingConstants.CENTER);
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 22));
        lblTitle.setForeground(new Color(14, 165, 233));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn giữa cho BoxLayout
        lblTitle.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0)); // Margin trên dưới
        panelMenu.add(lblTitle);

        // Khai báo các nút
        JButton btnTrangChu = createMenuButton("🏠 Trang Chủ");
        JButton btnQuanLySach = createMenuButton("📚 Quản Lý Sách");
        JButton btnQuanLyDocGia = createMenuButton("👥 Quản Lý Độc Giả");
        JButton btnQuanLyMuonTra = createMenuButton("🔄 Quản Lý Mượn Trả");
        JButton btnQuanLyNhapSach = createMenuButton("📥 Quản Lý Nhập Sách");
        JButton btnQuanLyPhiPhat = createMenuButton("💰 Quản Lý Phí Phạt");
        
        // Thêm các nút vào panel (có khoảng trống ở giữa cho thoáng)
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
        
        // Đẩy nút đăng xuất xuống cuối cùng
        panelMenu.add(Box.createVerticalGlue()); 

        JButton btnDangXuat = createMenuButton("🚪 Đăng Xuất");
        btnDangXuat.setForeground(new Color(248, 113, 113));
        panelMenu.add(btnDangXuat);
        panelMenu.add(Box.createVerticalStrut(20)); // Margin đáy

        add(panelMenu, BorderLayout.WEST);

        // ==========================================
        // 2. TẠO VÙNG NỘI DUNG CHÍNH (CARDLAYOUT)
        // ==========================================
        cardLayout = new CardLayout();
        panelContent = new JPanel(cardLayout);
        panelContent.setBackground(colorBackground);

        // Thêm các Panel vào CardLayout
        panelContent.add(createTrangChuPanel(), "CardTrangChu");
        panelContent.add(createQuanLySachPanel(), "CardSach");
        panelContent.add(createSimplePanel("Quản Lý Độc Giả"), "CardDocGia");
        panelContent.add(createSimplePanel("Quản Lý Mượn Trả"), "CardMuonTra");
        panelContent.add(createSimplePanel("Quản Lý Nhập Sách"), "CardNhapSach");
        panelContent.add(createSimplePanel("Quản Lý Phí Phạt"), "CardPhiPhat");

        add(panelContent, BorderLayout.CENTER);

        // ==========================================
        // 3. SỰ KIỆN CLICK MENU (CHUYỂN TRANG)
        // ==========================================
        btnTrangChu.addActionListener(e -> cardLayout.show(panelContent, "CardTrangChu"));
        btnQuanLySach.addActionListener(e -> cardLayout.show(panelContent, "CardSach"));
        btnQuanLyDocGia.addActionListener(e -> cardLayout.show(panelContent, "CardDocGia"));
        btnQuanLyMuonTra.addActionListener(e -> cardLayout.show(panelContent, "CardMuonTra"));
        btnQuanLyNhapSach.addActionListener(e -> cardLayout.show(panelContent, "CardNhapSach"));
        btnQuanLyPhiPhat.addActionListener(e -> cardLayout.show(panelContent, "CardPhiPhat"));

        btnDangXuat.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn đăng xuất?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
            }
        });

        // Mở app lên thì hiện Trang Chủ đầu tiên
        cardLayout.show(panelContent, "CardTrangChu");
    }

    // =====================================================================
    // HÀM TẠO PANEL TRANG CHỦ (MỚI THÊM)
    // =====================================================================
    private JPanel createTrangChuPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(colorBackground);
        
        JLabel lblWelcome = new JLabel("CHÀO MỪNG ĐẾN VỚI HỆ THỐNG QUẢN LÝ THƯ VIỆN", SwingConstants.CENTER);
        lblWelcome.setFont(new Font(tenFont, Font.BOLD, 28));
        lblWelcome.setForeground(new Color(15, 23, 42));
        
        JLabel lblSub = new JLabel("Vui lòng chọn chức năng ở menu bên trái để bắt đầu làm việc", SwingConstants.CENTER);
        lblSub.setFont(new Font(tenFont, Font.ITALIC, 16));
        lblSub.setForeground(Color.GRAY);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        centerPanel.setBackground(colorBackground);
        centerPanel.add(lblWelcome);
        centerPanel.add(lblSub);
        
        panel.add(centerPanel, BorderLayout.CENTER);
        return panel;
    }

    // =====================================================================
    // HÀM TẠO PANEL QUẢN LÝ SÁCH 
    // =====================================================================
    private JPanel createQuanLySachPanel() {
        JPanel pnlMain = new JPanel(new BorderLayout(10, 10));
        pnlMain.setBackground(colorBackground);
        pnlMain.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("QUẢN LÝ THÔNG TIN SÁCH");
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 24));
        lblTitle.setForeground(new Color(15, 23, 42));
        pnlMain.add(lblTitle, BorderLayout.NORTH);

        JPanel pnlCenter = new JPanel(new BorderLayout(0, 20));
        pnlCenter.setBackground(colorBackground);

        JPanel pnlInput = new JPanel(new GridLayout(3, 4, 15, 15));
        pnlInput.setBackground(Color.WHITE);
        pnlInput.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JTextField txtMaSach = new JTextField();
        JTextField txtTenSach = new JTextField();
        JTextField txtTacGia = new JTextField();
        JComboBox<String> cbTheLoai = new JComboBox<>(new String[]{"Giáo trình", "Tham khảo", "Tiểu thuyết", "Công nghệ"});
        JTextField txtSoLuong = new JTextField();
        JTextField txtNhaXuatBan = new JTextField();

        pnlInput.add(new JLabel("Mã sách:"));     pnlInput.add(txtMaSach);
        pnlInput.add(new JLabel("Tên sách:"));    pnlInput.add(txtTenSach);
        pnlInput.add(new JLabel("Tác giả:"));     pnlInput.add(txtTacGia);
        pnlInput.add(new JLabel("Thể loại:"));    pnlInput.add(cbTheLoai);
        pnlInput.add(new JLabel("Số lượng:"));    pnlInput.add(txtSoLuong);
        pnlInput.add(new JLabel("Nhà XB:"));      pnlInput.add(txtNhaXuatBan);

        pnlCenter.add(pnlInput, BorderLayout.NORTH);

        String[] columns = {"Mã Sách", "Tên Sách", "Tác Giả", "Thể Loại", "Số Lượng", "Nhà XB"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font(tenFont, Font.BOLD, 14));
        
        model.addRow(new Object[]{"S001", "Lập Trình Java Cơ Bản", "John Doe", "Giáo trình", "15", "NXB KHKT"});
        model.addRow(new Object[]{"S002", "Cấu Trúc Dữ Liệu", "Jane Smith", "Tham khảo", "20", "NXB Giáo Dục"});

        JScrollPane scrollPane = new JScrollPane(table);
        pnlCenter.add(scrollPane, BorderLayout.CENTER);

        pnlMain.add(pnlCenter, BorderLayout.CENTER);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        pnlButtons.setBackground(colorBackground);

        JButton btnThem = createActionButton("Thêm Sách", new Color(34, 197, 94));
        JButton btnSua = createActionButton("Sửa Thông Tin", new Color(245, 158, 11));
        JButton btnXoa = createActionButton("Xóa Sách", new Color(239, 68, 68));
        JButton btnLamMoi = createActionButton("Làm Mới", Color.GRAY);

        pnlButtons.add(btnThem);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnLamMoi);

        pnlMain.add(pnlButtons, BorderLayout.SOUTH);

        return pnlMain;
    }

    // =====================================================================
    // CÁC HÀM TIỆN ÍCH LÀM ĐẸP UI
    // =====================================================================
    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        // Thiết lập kích thước cố định cho nút khi dùng BoxLayout
        btn.setMaximumSize(new Dimension(220, 45)); 
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 10)); // Căn lề chữ bên trong nút
        
        btn.setFont(new Font(tenFont, Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
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

    private JButton createActionButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font(tenFont, Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(130, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JPanel createSimplePanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(colorBackground);
        JLabel lblTitle = new JLabel(title + " - Đang xây dựng...", SwingConstants.CENTER);
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 24));
        lblTitle.setForeground(Color.GRAY);
        panel.add(lblTitle, BorderLayout.CENTER);
        return panel;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {}

        SwingUtilities.invokeLater(() -> {
            new AdminFrame().setVisible(true);
        });
    }
}