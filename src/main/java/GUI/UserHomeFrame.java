package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserHomeFrame extends JFrame {
    
    // Các panel chính của chương trình
    private JPanel sidebarPanel;
    private JPanel mainContentPanel;
    private CardLayout cardLayout;
    
    // Tên font chữ dùng chung cho toàn bộ giao diện
    private String tenFont = "Segoe UI"; 
    
    public UserHomeFrame() {
        // Cài đặt các thông số cơ bản cho Cửa sổ (JFrame)
        setTitle("Hệ thống Quản lý Thư viện - Dành cho Độc giả");
        setSize(1000, 700); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Hiển thị ra giữa màn hình
        setLayout(new BorderLayout()); 
        
        // Gọi hàm khởi tạo các thành phần giao diện
        initComponents();
    }
    
    private void initComponents() {
        // ==========================================
        // 1. TẠO MENU BÊN TRÁI (SIDEBAR)
        // ==========================================
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BorderLayout()); 
        sidebarPanel.setBackground(new Color(30, 41, 59)); // Màu xanh đậm
        sidebarPanel.setPreferredSize(new Dimension(240, 0));
        
        // Panel chứa các nút bấm
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(6, 1, 0, 5)); 
        menuPanel.setBackground(new Color(30, 41, 59));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0)); 
        
        // Nhãn tiêu đề Menu
        JLabel logoLabel = new JLabel(" MENU ĐỘC GIẢ", SwingConstants.CENTER);
        logoLabel.setFont(new Font(tenFont, Font.BOLD, 15)); 
        logoLabel.setForeground(new Color(148, 163, 184)); 
        logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); 
        menuPanel.add(logoLabel);
        
        // Khởi tạo các nút bấm
        JButton btnTrangChu = createMenuButton("🏠 Trang Chủ");
        JButton btnTimSach = createMenuButton("🔍 Tìm Sách");
        JButton btnSachDangMuon = createMenuButton("📚 Sách Đang Mượn");
        JButton btnThongTin = createMenuButton("👤 Thông Tin Cá Nhân");
        JButton btnDangXuat = createMenuButton("🚪 Đăng Xuất");

        btnDangXuat.setForeground(new Color(248, 113, 113)); // Đổi màu đỏ cho nút đăng xuất
        
        // Thêm nút vào menuPanel
        menuPanel.add(btnTrangChu);
        menuPanel.add(btnTimSach);
        menuPanel.add(btnSachDangMuon);
        menuPanel.add(btnThongTin);
        menuPanel.add(btnDangXuat);
        
        // Đặt menuPanel lên phía trên cùng của sidebar
        sidebarPanel.add(menuPanel, BorderLayout.NORTH);
        
        // ==========================================
        // 2. TẠO PHẦN NỘI DUNG CHÍNH (CARDLAYOUT)
        // ==========================================
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel();
        mainContentPanel.setLayout(cardLayout);
        mainContentPanel.setBackground(new Color(248, 250, 252)); 
        
        // TẠO CÁC TRANG (PANEL) CHO TỪNG CHỨC NĂNG
        JPanel panelTrangChu = createHomePanel(); 
        
        // ---> ĐÃ SỬA: Gọi hàm createSearchPanel() thay vì trang trống
        JPanel panelTimSach = createSearchPanel(); 
        
        JPanel panelSachDangMuon = createPlaceholderPanel("Giao diện Sách đang mượn");
        JPanel panelThongTin = createPlaceholderPanel("Giao diện Thông tin cá nhân");
        
        // Thêm các Panel vào CardLayout và đặt tên định danh cho chúng
        mainContentPanel.add(panelTrangChu, "TrangChu");
        mainContentPanel.add(panelTimSach, "TimSach");
        mainContentPanel.add(panelSachDangMuon, "SachDangMuon");
        mainContentPanel.add(panelThongTin, "ThongTin");
        
        // ==========================================
        // 3. BẮT SỰ KIỆN KHI CLICK VÀO CÁC NÚT
        // ==========================================
        btnTrangChu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainContentPanel, "TrangChu");
            }
        });
        
        btnTimSach.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainContentPanel, "TimSach");
            }
        });
        
        btnSachDangMuon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainContentPanel, "SachDangMuon");
            }
        });
        
        btnThongTin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainContentPanel, "ThongTin");
            }
        });
        
        btnDangXuat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(UserHomeFrame.this, "Bạn có chắc chắn muốn đăng xuất?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(UserHomeFrame.this, "Đăng xuất thành công!");
                    dispose(); 
                }
            }
        });
        
        // ==========================================
        // 4. LẮP RÁP VÀO GIAO DIỆN CHÍNH
        // ==========================================
        add(sidebarPanel, BorderLayout.WEST); 
        add(mainContentPanel, BorderLayout.CENTER); 
    }
    
    // ==========================================
    // CÁC HÀM HỖ TRỢ TẠO GIAO DIỆN
    // ==========================================
    
    // Hàm tạo Panel Trang chủ (Giao diện chính giữa)
    private JPanel createHomePanel() {
        JPanel homePanel = new JPanel();
        homePanel.setLayout(new BorderLayout(0, 20));
        homePanel.setBackground(new Color(248, 250, 252)); 
        homePanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // --- PHẦN TOP: Tiêu đề ---
        JLabel lblPageTitle = new JLabel("Thư Viện Online");
        lblPageTitle.setFont(new Font(tenFont, Font.BOLD, 28));
        lblPageTitle.setForeground(new Color(15, 23, 42)); 
        homePanel.add(lblPageTitle, BorderLayout.NORTH);

        // --- PHẦN MAIN: Chứa Banner và Sách ---
        JPanel mainAreaPanel = new JPanel();
        mainAreaPanel.setLayout(new BorderLayout(0, 25));
        mainAreaPanel.setBackground(new Color(248, 250, 252));

        // 1. Khu vực chèn ảnh Banner quảng cáo
        JLabel lblBannerImage = new JLabel("[ Vị Trí Chèn Ảnh Banner Thư Viện ]", SwingConstants.CENTER);
        lblBannerImage.setOpaque(true);
        lblBannerImage.setBackground(new Color(226, 232, 240)); 
        lblBannerImage.setForeground(new Color(100, 116, 139));
        lblBannerImage.setFont(new Font(tenFont, Font.ITALIC, 16));
        lblBannerImage.setPreferredSize(new Dimension(0, 180)); 
        mainAreaPanel.add(lblBannerImage, BorderLayout.NORTH);

        // 2. Khu vực chứa sách gợi ý
        JPanel hotBooksContainer = new JPanel();
        hotBooksContainer.setLayout(new BorderLayout(0, 15));
        hotBooksContainer.setBackground(new Color(248, 250, 252));

        JLabel lblHotBooksTitle = new JLabel("Gợi ý cho bạn");
        lblHotBooksTitle.setFont(new Font(tenFont, Font.BOLD, 18));
        lblHotBooksTitle.setForeground(new Color(51, 65, 85));
        hotBooksContainer.add(lblHotBooksTitle, BorderLayout.NORTH);

        JPanel booksListPanel = new JPanel();
        booksListPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
        booksListPanel.setBackground(new Color(248, 250, 252));

        booksListPanel.add(createBookCard("Đắc Nhân Tâm", "Dale Carnegie", "Kỹ năng sống"));
        booksListPanel.add(createBookCard("Nhà Giả Kim", "Paulo Coelho", "Tiểu thuyết"));
        booksListPanel.add(createBookCard("Sapiens", "Yuval Noah Harari", "Lịch sử"));
        booksListPanel.add(createBookCard("Tuổi Trẻ Đáng Giá...", "Rosie Nguyễn", "Tự lực"));
        booksListPanel.add(createBookCard("Harry Potter", "J.K. Rowling", "Fantasy"));

        JScrollPane scrollPane = new JScrollPane(booksListPanel);
        scrollPane.setBackground(new Color(248, 250, 252));
        scrollPane.getViewport().setBackground(new Color(248, 250, 252)); 
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); 
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER); 
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16); 

        hotBooksContainer.add(scrollPane, BorderLayout.CENTER);
        mainAreaPanel.add(hotBooksContainer, BorderLayout.CENTER);
        homePanel.add(mainAreaPanel, BorderLayout.CENTER);

        return homePanel;
    }

    // Hàm tạo giao diện Tìm Sách
    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout(0, 20));
        searchPanel.setBackground(new Color(248, 250, 252)); 
        searchPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // ==========================================
        // PHẦN TRÊN: TIÊU ĐỀ VÀ THANH TÌM KIẾM
        // ==========================================
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout(0, 15));
        topPanel.setBackground(new Color(248, 250, 252));

        JLabel lblPageTitle = new JLabel("Tìm Kiếm Sách");
        lblPageTitle.setFont(new Font(tenFont, Font.BOLD, 28));
        lblPageTitle.setForeground(new Color(15, 23, 42)); 
        topPanel.add(lblPageTitle, BorderLayout.NORTH);

        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 0));
        filterPanel.setBackground(new Color(248, 250, 252));

        JLabel lblKeyword = new JLabel("Từ khóa:");
        lblKeyword.setFont(new Font(tenFont, Font.PLAIN, 14));

        JTextField txtSearch = new JTextField(25);
        txtSearch.setFont(new Font(tenFont, Font.PLAIN, 14));
        txtSearch.setPreferredSize(new Dimension(txtSearch.getPreferredSize().width, 30));

        String[] criteria = {"Tên sách", "Tác giả", "Thể loại"};
        JComboBox<String> cbCriteria = new JComboBox<>(criteria);
        cbCriteria.setFont(new Font(tenFont, Font.PLAIN, 14));
        cbCriteria.setPreferredSize(new Dimension(120, 30));
        cbCriteria.setBackground(Color.WHITE);

        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.setFont(new Font(tenFont, Font.BOLD, 13));
        btnSearch.setBackground(new Color(14, 165, 233)); 
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFocusPainted(false);
        btnSearch.setPreferredSize(new Dimension(100, 30));

        filterPanel.add(lblKeyword);
        filterPanel.add(txtSearch);
        filterPanel.add(cbCriteria);
        filterPanel.add(btnSearch);

        topPanel.add(filterPanel, BorderLayout.CENTER);

        // ==========================================
        // PHẦN GIỮA: BẢNG KẾT QUẢ TÌM KIẾM (JTABLE)
        // ==========================================
        String[] columnNames = {"Mã Sách", "Tên Sách", "Tác Giả", "Thể Loại", "Số Lượng", "Trạng Thái"};
        
        javax.swing.table.DefaultTableModel tableModel = new javax.swing.table.DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        JTable table = new JTable(tableModel);
        table.setFont(new Font(tenFont, Font.PLAIN, 14));
        table.setRowHeight(30); 
        table.setSelectionBackground(new Color(226, 232, 240)); 
        table.setSelectionForeground(Color.BLACK);
        
        table.getTableHeader().setFont(new Font(tenFont, Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(241, 245, 249));
        table.getTableHeader().setPreferredSize(new Dimension(100, 35));

        tableModel.addRow(new Object[]{"B001", "Đắc Nhân Tâm", "Dale Carnegie", "Kỹ năng sống", 5, "Sẵn sàng"});
        tableModel.addRow(new Object[]{"B002", "Nhà Giả Kim", "Paulo Coelho", "Tiểu thuyết", 2, "Sẵn sàng"});
        tableModel.addRow(new Object[]{"B003", "Sapiens", "Yuval Noah Harari", "Lịch sử", 0, "Đã mượn hết"});
        tableModel.addRow(new Object[]{"B004", "Tuổi Trẻ Đáng Giá Bao Nhiêu", "Rosie Nguyễn", "Tự lực", 10, "Sẵn sàng"});
        tableModel.addRow(new Object[]{"B005", "Dế Mèn Phiêu Lưu Ký", "Tô Hoài", "Văn học VN", 3, "Sẵn sàng"});

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);

        // ==========================================
        // PHẦN DƯỚI: NÚT CHỨC NĂNG
        // ==========================================
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        bottomPanel.setBackground(new Color(248, 250, 252));

        JButton btnBorrow = new JButton("Đăng Ký Mượn Sách");
        btnBorrow.setFont(new Font(tenFont, Font.BOLD, 14));
        btnBorrow.setBackground(new Color(34, 197, 94)); 
        btnBorrow.setForeground(Color.WHITE);
        btnBorrow.setFocusPainted(false);
        btnBorrow.setPreferredSize(new Dimension(180, 40));

        bottomPanel.add(btnBorrow);

        searchPanel.add(topPanel, BorderLayout.NORTH);
        searchPanel.add(scrollPane, BorderLayout.CENTER);
        searchPanel.add(bottomPanel, BorderLayout.SOUTH);

        // ==========================================
        // BẮT SỰ KIỆN CHO CÁC NÚT (Trong Tìm Sách)
        // ==========================================
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = txtSearch.getText();
                String type = cbCriteria.getSelectedItem().toString();
                JOptionPane.showMessageDialog(searchPanel, "Đang tìm sách: " + keyword + "\nTheo tiêu chí: " + type);
            }
        });

        btnBorrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(searchPanel, "Vui lòng chọn một quyển sách để mượn!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                } else {
                    String bookName = tableModel.getValueAt(selectedRow, 1).toString();
                    String status = tableModel.getValueAt(selectedRow, 5).toString();
                    
                    if (status.equals("Đã mượn hết")) {
                        JOptionPane.showMessageDialog(searchPanel, "Sách này hiện đã được mượn hết, vui lòng chọn sách khác!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(searchPanel, "Bạn đã đăng ký mượn quyển: " + bookName, "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        return searchPanel;
    }

    // Hàm tạo ra 1 Thẻ sách
    private JPanel createBookCard(String title, String author, String category) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(0, 10));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(180, 260)); 
        
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblCover = new JLabel("[Ảnh]", SwingConstants.CENTER);
        lblCover.setOpaque(true);
        lblCover.setBackground(new Color(241, 245, 249));
        lblCover.setForeground(new Color(148, 163, 184));
        lblCover.setPreferredSize(new Dimension(100, 140)); 

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(3, 1, 0, 3));
        infoPanel.setBackground(Color.WHITE);
        
        JLabel lblTitle = new JLabel(title, SwingConstants.LEFT); 
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 14));
        lblTitle.setForeground(new Color(15, 23, 42));
        
        JLabel lblAuthor = new JLabel(author, SwingConstants.LEFT);
        lblAuthor.setFont(new Font(tenFont, Font.PLAIN, 12));
        lblAuthor.setForeground(new Color(100, 116, 139));

        JLabel lblCategory = new JLabel(category, SwingConstants.LEFT);
        lblCategory.setFont(new Font(tenFont, Font.ITALIC, 11));
        lblCategory.setForeground(new Color(56, 189, 248)); 

        infoPanel.add(lblTitle);
        infoPanel.add(lblAuthor);
        infoPanel.add(lblCategory);

        JButton btnView = new JButton("Xem Ngay");
        btnView.setFont(new Font(tenFont, Font.BOLD, 12));
        btnView.setBackground(new Color(14, 165, 233)); 
        btnView.setForeground(Color.WHITE);
        btnView.setFocusPainted(false);
        btnView.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
        btnView.setCursor(new Cursor(Cursor.HAND_CURSOR));

        card.add(lblCover, BorderLayout.NORTH);
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(btnView, BorderLayout.SOUTH);

        return card;
    }

    // Hàm thiết kế hình thức cho các Nút ở Menu bên trái
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font(tenFont, Font.PLAIN, 14)); 
        button.setForeground(Color.WHITE); 
        button.setBackground(new Color(30, 41, 59)); 
        button.setFocusPainted(false); 
        button.setBorderPainted(false); 
        button.setHorizontalAlignment(SwingConstants.LEFT); 
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25)); 
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(51, 65, 85)); 
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(30, 41, 59)); 
            }
        });
        return button;
    }
    
    // Hàm tạo 1 Panel trống
    private JPanel createPlaceholderPanel(String text) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(248, 250, 252)); 
        
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font(tenFont, Font.PLAIN, 20)); 
        label.setForeground(new Color(148, 163, 184)); 
        panel.add(label, BorderLayout.CENTER);
        
        return panel;
    }

    // Hàm Main để chạy thử Form
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UserHomeFrame().setVisible(true);
            }
        });
    }
}