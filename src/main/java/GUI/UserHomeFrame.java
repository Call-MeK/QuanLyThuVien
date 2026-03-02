package library.ui;

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
        
        // Tạo các Panel cho từng chức năng
        JPanel panelTrangChu = createHomePanel(); 
        JPanel panelTimSach = createPlaceholderPanel("Giao diện Tìm kiếm sách");
        JPanel panelSachDangMuon = createPlaceholderPanel("Giao diện Sách đang mượn");
        JPanel panelThongTin = createPlaceholderPanel("Giao diện Thông tin cá nhân");
        
        // Thêm các Panel vào CardLayout và đặt tên định danh cho chúng
        mainContentPanel.add(panelTrangChu, "TrangChu");
        mainContentPanel.add(panelTimSach, "TimSach");
        mainContentPanel.add(panelSachDangMuon, "SachDangMuon");
        mainContentPanel.add(panelThongTin, "ThongTin");
        
        // ==========================================
        // 3. BẮT SỰ KIỆN KHI CLICK VÀO CÁC NÚT (Sử dụng cách viết truyền thống)
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
                // Hiển thị hộp thoại xác nhận
                int confirm = JOptionPane.showConfirmDialog(UserHomeFrame.this, "Bạn có chắc chắn muốn đăng xuất?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(UserHomeFrame.this, "Đăng xuất thành công!");
                    dispose(); // Đóng cửa sổ hiện tại
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

        // Panel chứa danh sách các thẻ sách (Sử dụng FlowLayout để sách không bị co giãn)
        JPanel booksListPanel = new JPanel();
        booksListPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
        booksListPanel.setBackground(new Color(248, 250, 252));

        // Thêm các thẻ sách vào panel
        booksListPanel.add(createBookCard("Đắc Nhân Tâm", "Dale Carnegie", "Kỹ năng sống"));
        booksListPanel.add(createBookCard("Nhà Giả Kim", "Paulo Coelho", "Tiểu thuyết"));
        booksListPanel.add(createBookCard("Sapiens", "Yuval Noah Harari", "Lịch sử"));
        booksListPanel.add(createBookCard("Tuổi Trẻ Đáng Giá...", "Rosie Nguyễn", "Tự lực"));
        booksListPanel.add(createBookCard("Harry Potter", "J.K. Rowling", "Fantasy"));

        // Tạo thanh cuộn cho danh sách sách
        JScrollPane scrollPane = new JScrollPane(booksListPanel);
        scrollPane.setBackground(new Color(248, 250, 252));
        scrollPane.getViewport().setBackground(new Color(248, 250, 252)); 
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Xóa viền của scrollpane
        // Chỉ cho phép cuộn ngang, tắt cuộn dọc
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER); 
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16); // Tốc độ cuộn chuột

        hotBooksContainer.add(scrollPane, BorderLayout.CENTER);
        
        // Lắp ráp vào mainAreaPanel
        mainAreaPanel.add(hotBooksContainer, BorderLayout.CENTER);
        
        // Lắp ráp vào homePanel
        homePanel.add(mainAreaPanel, BorderLayout.CENTER);

        return homePanel;
    }

    // Hàm tạo ra 1 Thẻ sách (Gồm Ảnh, Tên, Tác giả, Nút bấm)
    private JPanel createBookCard(String title, String author, String category) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(0, 10));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(180, 260)); 
        
        // Kẻ viền cho thẻ sách
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        // Nhãn chứa ảnh bìa sách
        JLabel lblCover = new JLabel("[Ảnh]", SwingConstants.CENTER);
        lblCover.setOpaque(true);
        lblCover.setBackground(new Color(241, 245, 249));
        lblCover.setForeground(new Color(148, 163, 184));
        lblCover.setPreferredSize(new Dimension(100, 140)); 

        // Panel chứa thông tin chữ
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

        // Nút bấm Xem Chi Tiết
        JButton btnView = new JButton("Xem Ngay");
        btnView.setFont(new Font(tenFont, Font.BOLD, 12));
        btnView.setBackground(new Color(14, 165, 233)); 
        btnView.setForeground(Color.WHITE);
        btnView.setFocusPainted(false);
        btnView.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
        btnView.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Thêm vào thẻ chính
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
        
        // Sự kiện khi dùng chuột trỏ vào nút thì đổi màu (Hover)
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
    
    // Hàm tạo 1 Panel trống để giữ chỗ cho các chức năng chưa code
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
        // Áp dụng giao diện của Hệ điều hành đang chạy
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Chạy giao diện
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UserHomeFrame().setVisible(true);
            }
        });
    }
}