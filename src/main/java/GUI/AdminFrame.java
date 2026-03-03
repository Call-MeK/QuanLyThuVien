package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminFrame extends JFrame {

    private JPanel panelMenu;
    private JPanel panelContent;
    private CardLayout cardLayout;

    // Cấu hình UI chuyên nghiệp
    private String tenFont = "Segoe UI"; 
    private Color colorMenuBg = new Color(33, 37, 41);
    private Color colorMenuHover = new Color(52, 58, 64);
    private Color colorBackground = new Color(248, 249, 250);
    private Color colorPrimary = new Color(13, 110, 253);

    public AdminFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Quản Lý Thư Viện - Admin System");
        // Kích thước mặc định đủ rộng để không vỡ layout khi chưa Fullscreen
        setSize(1250, 750); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Mở ứng dụng toàn màn hình theo chuẩn
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // ==========================================
        // 1. TẠO CỘT MENU (BÊN TRÁI)
        // ==========================================
        panelMenu = new JPanel();
        panelMenu.setBackground(colorMenuBg);
        panelMenu.setPreferredSize(new Dimension(240, 0));
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));

        JLabel lblTitle = new JLabel("ADMIN PANEL", SwingConstants.CENTER);
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 22)); 
        lblTitle.setForeground(colorPrimary);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(35, 0, 35, 0));
        panelMenu.add(lblTitle);

        JButton btnTrangChu = createMenuButton("🏠  Trang Chủ");
        JButton btnQuanLySach = createMenuButton("📚  Quản Lý Sách");
        JButton btnQuanLyDocGia = createMenuButton("👥  Quản Lý Độc Giả");
        JButton btnQuanLyMuonTra = createMenuButton("🔄  Quản Lý Mượn Trả");
        JButton btnQuanLyNhapSach = createMenuButton("📥  Quản Lý Nhập Sách");
        JButton btnQuanLyPhiPhat = createMenuButton("💰  Quản Lý Phí Phạt");
        
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
        
        panelMenu.add(Box.createVerticalGlue()); 

        JButton btnDangXuat = createMenuButton("🚪  Đăng Xuất");
        btnDangXuat.setForeground(new Color(220, 53, 69)); 
        panelMenu.add(btnDangXuat);
        panelMenu.add(Box.createVerticalStrut(25)); 

        JScrollPane scrollMenu = new JScrollPane(panelMenu);
        scrollMenu.setBorder(null);
        scrollMenu.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollMenu, BorderLayout.WEST);

        // ==========================================
        // 2. TẠO VÙNG NỘI DUNG CHÍNH (CARDLAYOUT)
        // ==========================================
        cardLayout = new CardLayout();
        panelContent = new JPanel(cardLayout);
        panelContent.setBackground(colorBackground);

        panelContent.add(createTrangChuPanel(), "CardTrangChu");
        panelContent.add(createQuanLySachPanel(), "CardSach");
        panelContent.add(createQuanLyDocGiaPanel(), "CardDocGia"); 
        panelContent.add(createQuanLyMuonTraPanel(), "CardMuonTra"); 
        panelContent.add(createQuanLyNhapSachPanel(), "CardNhapSach"); 
        panelContent.add(createSimplePanel("Quản Lý Phí Phạt"), "CardPhiPhat");

        add(panelContent, BorderLayout.CENTER);

        // ==========================================
        // 3. SỰ KIỆN CHUYỂN TRANG
        // ==========================================
        btnTrangChu.addActionListener(e -> cardLayout.show(panelContent, "CardTrangChu"));
        btnQuanLySach.addActionListener(e -> cardLayout.show(panelContent, "CardSach"));
        btnQuanLyDocGia.addActionListener(e -> cardLayout.show(panelContent, "CardDocGia"));
        btnQuanLyMuonTra.addActionListener(e -> cardLayout.show(panelContent, "CardMuonTra"));
        btnQuanLyNhapSach.addActionListener(e -> cardLayout.show(panelContent, "CardNhapSach"));
        btnQuanLyPhiPhat.addActionListener(e -> cardLayout.show(panelContent, "CardPhiPhat"));

        btnDangXuat.addActionListener(e -> {
            UIManager.put("OptionPane.messageFont", new Font(tenFont, Font.PLAIN, 15));
            UIManager.put("OptionPane.buttonFont", new Font(tenFont, Font.BOLD, 14));
            
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn đăng xuất?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
            }
        });

        cardLayout.show(panelContent, "CardTrangChu");
    }

    // =====================================================================
    // TRANG CHỦ
    // =====================================================================
    private JPanel createTrangChuPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(colorBackground);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        JPanel pnlTop = new JPanel(new GridLayout(2, 1, 0, 5));
        pnlTop.setBackground(colorBackground);
        
        JLabel lblWelcome = new JLabel("👋 CHÀO MỪNG ADMIN TRỞ LẠI!");
        lblWelcome.setFont(new Font(tenFont, Font.BOLD, 26));
        lblWelcome.setForeground(new Color(33, 37, 41));
        
        JLabel lblSub = new JLabel("Dưới đây là tổng quan tình hình thư viện hôm nay.");
        lblSub.setFont(new Font(tenFont, Font.PLAIN, 16));
        lblSub.setForeground(new Color(108, 117, 125));
        
        pnlTop.add(lblWelcome);
        pnlTop.add(lblSub);
        panel.add(pnlTop, BorderLayout.NORTH);

        JPanel pnlCenter = new JPanel(new BorderLayout(30, 0)); 
        pnlCenter.setBackground(colorBackground);

        // CỘT TRÁI (Đã thu gọn kích thước để vừa cửa sổ)
        JPanel pnlLeft = new JPanel(new GridLayout(4, 1, 0, 20)); 
        pnlLeft.setBackground(colorBackground);
        pnlLeft.setPreferredSize(new Dimension(240, 0)); 
        
        pnlLeft.add(createStatCard("📚 TỔNG SỐ SÁCH", "1,245", "Cuốn", new Color(13, 110, 253)));
        pnlLeft.add(createStatCard("👥 TỔNG ĐỘC GIẢ", "320", "Người", new Color(25, 135, 84)));
        pnlLeft.add(createStatCard("🔄 SÁCH ĐANG MƯỢN", "85", "Phiếu", new Color(255, 193, 7)));
        pnlLeft.add(createStatCard("⚠️ TRỄ HẠN TRẢ", "12", "Trường hợp", new Color(220, 53, 69)));
        
        pnlCenter.add(pnlLeft, BorderLayout.WEST);

        // CỘT PHẢI
        JPanel pnlRight = new JPanel(new BorderLayout(0, 20));
        pnlRight.setBackground(colorBackground);

        JLabel lblImage = new JLabel();
        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        lblImage.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1)); 
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/Images/thuvien.jpg"));
            Image img = icon.getImage().getScaledInstance(650, 220, Image.SCALE_SMOOTH);
            lblImage.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            lblImage.setText("[ Hình ảnh minh họa Thư Viện ]");
            lblImage.setOpaque(true);
            lblImage.setBackground(new Color(233, 236, 239));
            lblImage.setPreferredSize(new Dimension(650, 220));
        }
        pnlRight.add(lblImage, BorderLayout.NORTH);

        JPanel pnlRecent = new JPanel(new BorderLayout(0, 15));
        pnlRecent.setBackground(Color.WHITE);
        pnlRecent.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel lblRecentTitle = new JLabel("🕒 Hoạt Động Gần Đây Nhất");
        lblRecentTitle.setFont(new Font(tenFont, Font.BOLD, 18));
        lblRecentTitle.setForeground(new Color(33, 37, 41));
        pnlRecent.add(lblRecentTitle, BorderLayout.NORTH);

        JPanel listActivities = new JPanel(new GridLayout(4, 1, 0, 10));
        listActivities.setBackground(Color.WHITE);
        
        listActivities.add(createActivityRow("Độc giả Nguyễn Văn A vừa mượn 'Lập Trình Java'", "10 phút trước"));
        listActivities.add(createActivityRow("Đã nhập thêm 50 cuốn 'Cấu trúc dữ liệu' vào kho", "1 giờ trước"));
        listActivities.add(createActivityRow("Độc giả Lê Thị B đã trả sách trễ hạn (phạt 20.000đ)", "Hôm qua"));
        listActivities.add(createActivityRow("Cập nhật thông tin thành viên Trần Văn C", "Hôm qua"));
        
        pnlRecent.add(listActivities, BorderLayout.CENTER);
        pnlRight.add(pnlRecent, BorderLayout.CENTER);
        pnlCenter.add(pnlRight, BorderLayout.CENTER);
        panel.add(pnlCenter, BorderLayout.CENTER);
        
        JLabel lblFooter = new JLabel("Hệ thống cập nhật lần cuối: Vừa xong", SwingConstants.RIGHT);
        lblFooter.setFont(new Font(tenFont, Font.ITALIC, 14));
        lblFooter.setForeground(Color.GRAY);
        panel.add(lblFooter, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createStatCard(String title, String value, String unit, Color topColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1));

        JPanel topBar = new JPanel();
        topBar.setBackground(topColor);
        topBar.setPreferredSize(new Dimension(0, 5));
        card.add(topBar, BorderLayout.NORTH);

        JPanel content = new JPanel(new GridLayout(3, 1));
        content.setBackground(Color.WHITE);
        content.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 14)); // Giảm nhẹ Font Title
        lblTitle.setForeground(new Color(108, 117, 125));

        // Đã giảm cỡ chữ giá trị từ 36 xuống 28 để không bị tràn khi thu nhỏ màn hình
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font(tenFont, Font.BOLD, 28)); 
        lblValue.setForeground(topColor);

        JLabel lblUnit = new JLabel(unit);
        lblUnit.setFont(new Font(tenFont, Font.PLAIN, 13));
        lblUnit.setForeground(Color.GRAY);

        content.add(lblTitle);
        content.add(lblValue);
        content.add(lblUnit);

        card.add(content, BorderLayout.CENTER);
        return card;
    }

    private JPanel createActivityRow(String action, String time) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(Color.WHITE);
        
        JLabel lblAction = new JLabel("▪ " + action);
        lblAction.setFont(new Font(tenFont, Font.PLAIN, 15));
        lblAction.setForeground(new Color(73, 80, 87));
        
        JLabel lblTime = new JLabel(time);
        lblTime.setFont(new Font(tenFont, Font.ITALIC, 13));
        lblTime.setForeground(new Color(173, 181, 189));
        
        row.add(lblAction, BorderLayout.WEST);
        row.add(lblTime, BorderLayout.EAST);
        row.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(241, 243, 245)));
        return row;
    }

    // =====================================================================
    // QUẢN LÝ SÁCH
    // =====================================================================
    private JPanel createQuanLySachPanel() {
        JPanel pnlMain = new JPanel(new BorderLayout(15, 15));
        pnlMain.setBackground(colorBackground);
        pnlMain.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25)); 

        JLabel lblTitle = new JLabel("Quản Lý Thông Tin Sách");
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 24)); 
        lblTitle.setForeground(new Color(33, 37, 41));
        pnlMain.add(lblTitle, BorderLayout.NORTH);

        JPanel pnlCenter = new JPanel(new BorderLayout(0, 20));
        pnlCenter.setBackground(colorBackground);

        JPanel pnlInput = new JPanel(new GridLayout(3, 4, 15, 15));
        pnlInput.setBackground(Color.WHITE);
        pnlInput.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        Font fontLabel = new Font(tenFont, Font.BOLD, 14); 
        Font fontInput = new Font(tenFont, Font.PLAIN, 14); 

        JTextField txtMaSach = new JTextField(); txtMaSach.setFont(fontInput);
        JTextField txtTenSach = new JTextField(); txtTenSach.setFont(fontInput);
        JTextField txtTacGia = new JTextField(); txtTacGia.setFont(fontInput);
        JComboBox<String> cbTheLoai = new JComboBox<>(new String[]{"Giáo trình", "Tham khảo", "Tiểu thuyết", "Công nghệ"});
        cbTheLoai.setFont(fontInput);
        JTextField txtSoLuong = new JTextField(); txtSoLuong.setFont(fontInput);
        JTextField txtNhaXuatBan = new JTextField(); txtNhaXuatBan.setFont(fontInput);

        pnlInput.add(createLabel("Mã sách:", fontLabel));     pnlInput.add(txtMaSach);
        pnlInput.add(createLabel("Tên sách:", fontLabel));    pnlInput.add(txtTenSach);
        pnlInput.add(createLabel("Tác giả:", fontLabel));     pnlInput.add(txtTacGia);
        pnlInput.add(createLabel("Thể loại:", fontLabel));    pnlInput.add(cbTheLoai);
        pnlInput.add(createLabel("Số lượng:", fontLabel));    pnlInput.add(txtSoLuong);
        pnlInput.add(createLabel("Nhà xuất bản:", fontLabel));pnlInput.add(txtNhaXuatBan);

        pnlCenter.add(pnlInput, BorderLayout.NORTH);

        String[] columns = {"Mã Sách", "Tên Sách", "Tác Giả", "Thể Loại", "Số Lượng", "Nhà Xuất Bản"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        
        table.setRowHeight(32); 
        table.setFont(new Font(tenFont, Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font(tenFont, Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(233, 236, 239));
        table.getTableHeader().setOpaque(false);
        table.setShowGrid(false); 
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setShowHorizontalLines(true); 
        table.setGridColor(new Color(222, 226, 230));

        model.addRow(new Object[]{"S001", "Lập Trình Java OOP", "James Gosling", "Giáo trình", "15", "NXB KHKT"});
        model.addRow(new Object[]{"S002", "Cấu Trúc Dữ Liệu", "Robert Sedgewick", "Tham khảo", "20", "NXB ĐHQG"});

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);

        pnlMain.add(pnlCenter, BorderLayout.CENTER);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        pnlButtons.setBackground(colorBackground);

        JButton btnThem = createActionButton("Thêm Mới", new Color(25, 135, 84)); 
        JButton btnSua = createActionButton("Cập Nhật", new Color(255, 193, 7)); 
        btnSua.setForeground(Color.BLACK); 
        JButton btnXoa = createActionButton("Xóa Dữ Liệu", new Color(220, 53, 69)); 
        JButton btnLamMoi = createActionButton("Làm Mới", new Color(108, 117, 125)); 

        pnlButtons.add(btnThem);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnLamMoi);

        pnlMain.add(pnlButtons, BorderLayout.SOUTH);

        return pnlMain;
    }

    // =====================================================================
    // QUẢN LÝ ĐỘC GIẢ
    // =====================================================================
    private JPanel createQuanLyDocGiaPanel() {
        JPanel pnlMain = new JPanel(new BorderLayout(15, 15));
        pnlMain.setBackground(colorBackground);
        pnlMain.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25)); 

        JLabel lblTitle = new JLabel("Quản Lý Thông Tin Độc Giả");
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 24)); 
        lblTitle.setForeground(new Color(33, 37, 41));
        pnlMain.add(lblTitle, BorderLayout.NORTH);

        JPanel pnlCenter = new JPanel(new BorderLayout(0, 15)); 
        pnlCenter.setBackground(colorBackground);

        JPanel pnlInput = new JPanel(new GridLayout(4, 4, 15, 15));
        pnlInput.setBackground(Color.WHITE);
        pnlInput.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        Font fontLabel = new Font(tenFont, Font.BOLD, 14); 
        Font fontInput = new Font(tenFont, Font.PLAIN, 14); 

        JTextField txtMaDG = new JTextField(); txtMaDG.setFont(fontInput);
        JTextField txtHoTen = new JTextField(); txtHoTen.setFont(fontInput);
        JComboBox<String> cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"}); cbGioiTinh.setFont(fontInput);
        JTextField txtNgaySinh = new JTextField("dd/MM/yyyy"); txtNgaySinh.setFont(fontInput); txtNgaySinh.setForeground(Color.GRAY);
        JTextField txtDienThoai = new JTextField(); txtDienThoai.setFont(fontInput);
        JTextField txtEmail = new JTextField(); txtEmail.setFont(fontInput);
        JTextField txtNgayDK = new JTextField(); txtNgayDK.setFont(fontInput);
        JComboBox<String> cbTrangThai = new JComboBox<>(new String[]{"Đang hoạt động", "Bị khóa", "Hết hạn"}); cbTrangThai.setFont(fontInput);

        pnlInput.add(createLabel("Mã độc giả:", fontLabel));  pnlInput.add(txtMaDG);
        pnlInput.add(createLabel("Họ và tên:", fontLabel));   pnlInput.add(txtHoTen);
        pnlInput.add(createLabel("Giới tính:", fontLabel));   pnlInput.add(cbGioiTinh);
        pnlInput.add(createLabel("Ngày sinh:", fontLabel));   pnlInput.add(txtNgaySinh);
        pnlInput.add(createLabel("Điện thoại:", fontLabel));  pnlInput.add(txtDienThoai);
        pnlInput.add(createLabel("Email:", fontLabel));       pnlInput.add(txtEmail);
        pnlInput.add(createLabel("Ngày đăng ký:", fontLabel));pnlInput.add(txtNgayDK);
        pnlInput.add(createLabel("Trạng thái:", fontLabel));  pnlInput.add(cbTrangThai);

        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlSearch.setBackground(colorBackground);
        pnlSearch.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); 

        JLabel lblSearch = createLabel("🔍 Tìm kiếm:", new Font(tenFont, Font.BOLD, 14));
        JTextField txtSearch = new JTextField(20); 
        txtSearch.setFont(fontInput);
        txtSearch.setPreferredSize(new Dimension(0, 35)); 

        JComboBox<String> cbSearchCriteria = new JComboBox<>(new String[]{"Tất cả", "Mã ĐG", "Họ Tên", "Số Điện Thoại", "Trạng Thái"});
        cbSearchCriteria.setFont(fontInput);
        cbSearchCriteria.setPreferredSize(new Dimension(140, 35));

        JButton btnSearch = createActionButton("Lọc", new Color(13, 110, 253)); 
        btnSearch.setPreferredSize(new Dimension(100, 35)); 
        JButton btnResetSearch = createActionButton("Hủy Lọc", new Color(108, 117, 125));
        btnResetSearch.setPreferredSize(new Dimension(100, 35));

        pnlSearch.add(lblSearch);
        pnlSearch.add(txtSearch);
        pnlSearch.add(createLabel(" theo ", fontInput));
        pnlSearch.add(cbSearchCriteria);
        pnlSearch.add(btnSearch);
        pnlSearch.add(btnResetSearch);

        JPanel pnlTopCenter = new JPanel(new BorderLayout(0, 5));
        pnlTopCenter.setBackground(colorBackground);
        pnlTopCenter.add(pnlInput, BorderLayout.NORTH);
        pnlTopCenter.add(pnlSearch, BorderLayout.CENTER);

        pnlCenter.add(pnlTopCenter, BorderLayout.NORTH);

        String[] columns = {"Mã ĐG", "Họ Tên", "Giới Tính", "Ngày Sinh", "Điện Thoại", "Trạng Thái"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        
        table.setRowHeight(32); 
        table.setFont(new Font(tenFont, Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font(tenFont, Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(233, 236, 239));
        table.getTableHeader().setOpaque(false);
        table.setShowGrid(false); 
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setShowHorizontalLines(true); 
        table.setGridColor(new Color(222, 226, 230));

        model.addRow(new Object[]{"DG001", "Nguyễn Văn A", "Nam", "15/05/2005", "0901234567", "Đang hoạt động"});
        model.addRow(new Object[]{"DG002", "Trần Thị B", "Nữ", "22/10/2004", "0987654321", "Bị khóa"});
        model.addRow(new Object[]{"DG003", "Lê Minh C", "Nam", "01/01/2006", "0911222333", "Đang hoạt động"});

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);

        pnlMain.add(pnlCenter, BorderLayout.CENTER);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        pnlButtons.setBackground(colorBackground);

        JButton btnThem = createActionButton("Thêm Độc Giả", new Color(25, 135, 84)); 
        JButton btnSua = createActionButton("Cập Nhật", new Color(255, 193, 7)); btnSua.setForeground(Color.BLACK); 
        JButton btnKhoa = createActionButton("Khóa Thẻ", new Color(220, 53, 69)); 
        JButton btnLamMoi = createActionButton("Làm Mới Form", new Color(108, 117, 125)); 

        pnlButtons.add(btnThem);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnKhoa);
        pnlButtons.add(btnLamMoi);

        pnlMain.add(pnlButtons, BorderLayout.SOUTH);

        return pnlMain;
    }

    // =====================================================================
    // QUẢN LÝ MƯỢN TRẢ
    // =====================================================================
    private JPanel createQuanLyMuonTraPanel() {
        JPanel pnlMain = new JPanel(new BorderLayout(15, 15));
        pnlMain.setBackground(colorBackground);
        pnlMain.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25)); 

        JLabel lblTitle = new JLabel("Quản Lý Mượn - Trả Sách");
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 24)); 
        lblTitle.setForeground(new Color(33, 37, 41));
        pnlMain.add(lblTitle, BorderLayout.NORTH);

        JPanel pnlCenter = new JPanel(new BorderLayout(0, 15)); 
        pnlCenter.setBackground(colorBackground);

        JPanel pnlInput = new JPanel(new GridLayout(4, 4, 15, 15));
        pnlInput.setBackground(Color.WHITE);
        pnlInput.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        Font fontLabel = new Font(tenFont, Font.BOLD, 14); 
        Font fontInput = new Font(tenFont, Font.PLAIN, 14); 

        JTextField txtMaPhieu = new JTextField(); txtMaPhieu.setFont(fontInput);
        JTextField txtMaDG = new JTextField(); txtMaDG.setFont(fontInput);
        JTextField txtMaSach = new JTextField(); txtMaSach.setFont(fontInput);
        JTextField txtNgayMuon = new JTextField("dd/MM/yyyy"); txtNgayMuon.setFont(fontInput); txtNgayMuon.setForeground(Color.GRAY);
        JTextField txtHanTra = new JTextField("dd/MM/yyyy"); txtHanTra.setFont(fontInput); txtHanTra.setForeground(Color.GRAY);
        JTextField txtNgayTra = new JTextField(); txtNgayTra.setFont(fontInput);
        JComboBox<String> cbTrangThai = new JComboBox<>(new String[]{"Đang mượn", "Đã trả", "Trễ hạn"}); cbTrangThai.setFont(fontInput);
        JTextField txtTienPhat = new JTextField("0"); txtTienPhat.setFont(fontInput);

        pnlInput.add(createLabel("Mã phiếu:", fontLabel));    pnlInput.add(txtMaPhieu);
        pnlInput.add(createLabel("Mã độc giả:", fontLabel));  pnlInput.add(txtMaDG);
        pnlInput.add(createLabel("Mã sách:", fontLabel));     pnlInput.add(txtMaSach);
        pnlInput.add(createLabel("Ngày mượn:", fontLabel));   pnlInput.add(txtNgayMuon);
        pnlInput.add(createLabel("Hạn trả:", fontLabel));     pnlInput.add(txtHanTra);
        pnlInput.add(createLabel("Ngày trả thực:", fontLabel));pnlInput.add(txtNgayTra);
        pnlInput.add(createLabel("Trạng thái:", fontLabel));  pnlInput.add(cbTrangThai);
        pnlInput.add(createLabel("Tiền phạt:", fontLabel));   pnlInput.add(txtTienPhat);

        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlSearch.setBackground(colorBackground);
        pnlSearch.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); 

        JLabel lblSearch = createLabel("🔍 Tra cứu phiếu:", new Font(tenFont, Font.BOLD, 14));
        JTextField txtSearch = new JTextField(20); 
        txtSearch.setFont(fontInput);
        txtSearch.setPreferredSize(new Dimension(0, 35)); 

        JComboBox<String> cbSearchCriteria = new JComboBox<>(new String[]{"Tất cả", "Mã Phiếu", "Mã Độc Giả", "Trạng Thái"});
        cbSearchCriteria.setFont(fontInput);
        cbSearchCriteria.setPreferredSize(new Dimension(140, 35));

        JButton btnSearch = createActionButton("Tìm Kiếm", new Color(13, 110, 253)); 
        btnSearch.setPreferredSize(new Dimension(110, 35)); 
        JButton btnResetSearch = createActionButton("Hủy Lọc", new Color(108, 117, 125));
        btnResetSearch.setPreferredSize(new Dimension(100, 35));

        pnlSearch.add(lblSearch);
        pnlSearch.add(txtSearch);
        pnlSearch.add(createLabel(" theo ", fontInput));
        pnlSearch.add(cbSearchCriteria);
        pnlSearch.add(btnSearch);
        pnlSearch.add(btnResetSearch);

        JPanel pnlTopCenter = new JPanel(new BorderLayout(0, 5));
        pnlTopCenter.setBackground(colorBackground);
        pnlTopCenter.add(pnlInput, BorderLayout.NORTH);
        pnlTopCenter.add(pnlSearch, BorderLayout.CENTER);

        pnlCenter.add(pnlTopCenter, BorderLayout.NORTH);

        String[] columns = {"Mã Phiếu", "Mã Độc Giả", "Mã Sách", "Ngày Mượn", "Hạn Trả", "Trạng Thái"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        
        table.setRowHeight(32); 
        table.setFont(new Font(tenFont, Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font(tenFont, Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(233, 236, 239));
        table.getTableHeader().setOpaque(false);
        table.setShowGrid(false); 
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setShowHorizontalLines(true); 
        table.setGridColor(new Color(222, 226, 230));

        model.addRow(new Object[]{"PM001", "DG001", "S001", "01/03/2026", "15/03/2026", "Đang mượn"});
        model.addRow(new Object[]{"PM002", "DG005", "S055", "10/02/2026", "24/02/2026", "Trễ hạn"});
        model.addRow(new Object[]{"PM003", "DG002", "S003", "20/02/2026", "05/03/2026", "Đã trả"});

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);

        pnlMain.add(pnlCenter, BorderLayout.CENTER);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        pnlButtons.setBackground(colorBackground);

        JButton btnThem = createActionButton("Lập Phiếu", new Color(34, 197, 94)); 
        JButton btnTra = createActionButton("Xác Nhận Trả", new Color(13, 110, 253)); 
        JButton btnGiaHan = createActionButton("Gia Hạn", new Color(255, 193, 7)); btnGiaHan.setForeground(Color.BLACK); 
        JButton btnLamMoi = createActionButton("Làm Mới Form", new Color(108, 117, 125)); 

        pnlButtons.add(btnThem);
        pnlButtons.add(btnTra);
        pnlButtons.add(btnGiaHan);
        pnlButtons.add(btnLamMoi);

        pnlMain.add(pnlButtons, BorderLayout.SOUTH);

        return pnlMain;
    }

    // =====================================================================
    // QUẢN LÝ NHẬP SÁCH
    // =====================================================================
    private JPanel createQuanLyNhapSachPanel() {
        JPanel pnlMain = new JPanel(new BorderLayout(15, 15));
        pnlMain.setBackground(colorBackground);
        pnlMain.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25)); 

        JLabel lblTitle = new JLabel("Quản Lý Phiếu Nhập Sách");
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 24)); 
        lblTitle.setForeground(new Color(33, 37, 41));
        pnlMain.add(lblTitle, BorderLayout.NORTH);

        JPanel pnlCenter = new JPanel(new BorderLayout(0, 15)); 
        pnlCenter.setBackground(colorBackground);

        JPanel pnlInput = new JPanel(new GridLayout(4, 4, 15, 15));
        pnlInput.setBackground(Color.WHITE);
        pnlInput.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        Font fontLabel = new Font(tenFont, Font.BOLD, 14); 
        Font fontInput = new Font(tenFont, Font.PLAIN, 14); 

        JTextField txtMaPN = new JTextField(); txtMaPN.setFont(fontInput);
        JTextField txtNguoiNhap = new JTextField(); txtNguoiNhap.setFont(fontInput);
        JTextField txtMaSach = new JTextField(); txtMaSach.setFont(fontInput);
        JTextField txtSoLuong = new JTextField("0"); txtSoLuong.setFont(fontInput);
        JTextField txtDonGia = new JTextField("0"); txtDonGia.setFont(fontInput);
        JTextField txtNhaCungCap = new JTextField(); txtNhaCungCap.setFont(fontInput);
        JTextField txtNgayNhap = new JTextField("dd/MM/yyyy"); txtNgayNhap.setFont(fontInput); txtNgayNhap.setForeground(Color.GRAY);
        JTextField txtTongTien = new JTextField("0"); txtTongTien.setFont(fontInput); txtTongTien.setEditable(false); 

        pnlInput.add(createLabel("Mã phiếu nhập:", fontLabel)); pnlInput.add(txtMaPN);
        pnlInput.add(createLabel("Mã Quản lý:", fontLabel));   pnlInput.add(txtNguoiNhap);
        pnlInput.add(createLabel("Mã sách:", fontLabel));       pnlInput.add(txtMaSach);
        pnlInput.add(createLabel("Số lượng:", fontLabel));      pnlInput.add(txtSoLuong);
        pnlInput.add(createLabel("Đơn giá:", fontLabel));       pnlInput.add(txtDonGia);
        pnlInput.add(createLabel("Nhà cung cấp:", fontLabel));  pnlInput.add(txtNhaCungCap);
        pnlInput.add(createLabel("Ngày nhập:", fontLabel));     pnlInput.add(txtNgayNhap);
        pnlInput.add(createLabel("Tổng tiền (VNĐ):", fontLabel));pnlInput.add(txtTongTien);

        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlSearch.setBackground(colorBackground);
        pnlSearch.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); 

        JLabel lblSearch = createLabel("🔍 Tra cứu phiếu nhập:", new Font(tenFont, Font.BOLD, 14));
        JTextField txtSearch = new JTextField(20); 
        txtSearch.setFont(fontInput);
        txtSearch.setPreferredSize(new Dimension(0, 35)); 

        JComboBox<String> cbSearchCriteria = new JComboBox<>(new String[]{"Tất cả", "Mã Phiếu", "Mã Sách", "Nhà Cung Cấp"});
        cbSearchCriteria.setFont(fontInput);
        cbSearchCriteria.setPreferredSize(new Dimension(140, 35));

        JButton btnSearch = createActionButton("Tìm Kiếm", new Color(13, 110, 253)); 
        btnSearch.setPreferredSize(new Dimension(110, 35)); 
        JButton btnResetSearch = createActionButton("Hủy Lọc", new Color(108, 117, 125));
        btnResetSearch.setPreferredSize(new Dimension(100, 35));

        pnlSearch.add(lblSearch);
        pnlSearch.add(txtSearch);
        pnlSearch.add(createLabel(" theo ", fontInput));
        pnlSearch.add(cbSearchCriteria);
        pnlSearch.add(btnSearch);
        pnlSearch.add(btnResetSearch);

        JPanel pnlTopCenter = new JPanel(new BorderLayout(0, 5));
        pnlTopCenter.setBackground(colorBackground);
        pnlTopCenter.add(pnlInput, BorderLayout.NORTH);
        pnlTopCenter.add(pnlSearch, BorderLayout.CENTER);

        pnlCenter.add(pnlTopCenter, BorderLayout.NORTH);

        String[] columns = {"Mã Phiếu", "Mã Sách", "Số Lượng", "Đơn Giá", "Nhà Cung Cấp", "Ngày Nhập", "Tổng Tiền"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        
        table.setRowHeight(32); 
        table.setFont(new Font(tenFont, Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font(tenFont, Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(233, 236, 239));
        table.getTableHeader().setOpaque(false);
        table.setShowGrid(false); 
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setShowHorizontalLines(true); 
        table.setGridColor(new Color(222, 226, 230));

        model.addRow(new Object[]{"PN001", "S001", "50", "120,000", "NXB Trẻ", "01/03/2026", "6,000,000"});
        model.addRow(new Object[]{"PN002", "S055", "100", "85,000", "NXB Kim Đồng", "15/02/2026", "8,500,000"});
        model.addRow(new Object[]{"PN003", "S003", "30", "150,000", "NXB Giáo Dục", "20/02/2026", "4,500,000"});

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);

        pnlMain.add(pnlCenter, BorderLayout.CENTER);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        pnlButtons.setBackground(colorBackground);

        JButton btnThem = createActionButton("Tạo Phiếu Nhập", new Color(34, 197, 94)); 
        JButton btnSua = createActionButton("Sửa Thông Tin", new Color(255, 193, 7)); btnSua.setForeground(Color.BLACK); 
        JButton btnXoa = createActionButton("Hủy Phiếu", new Color(220, 53, 69)); 
        JButton btnLamMoi = createActionButton("Làm Mới Form", new Color(108, 117, 125)); 

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
    
    private JLabel createLabel(String text, Font font) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(font);
        lbl.setForeground(new Color(73, 80, 87)); 
        return lbl;
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(240, 50)); 
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

    // Nút chức năng đã được chỉnh về kích thước an toàn 150x40
    private JButton createActionButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font(tenFont, Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(150, 40)); 
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JPanel createSimplePanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(colorBackground);
        JLabel lblTitle = new JLabel(title + " - Đang xây dựng...", SwingConstants.CENTER);
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 24));
        lblTitle.setForeground(new Color(173, 181, 189));
        panel.add(lblTitle, BorderLayout.CENTER);
        return panel;
    }

    // =====================================================================
    // HÀM MAIN
    // =====================================================================
    public static void main(String[] args) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {}

        SwingUtilities.invokeLater(() -> {
            new AdminFrame().setVisible(true);
        });
    }
}