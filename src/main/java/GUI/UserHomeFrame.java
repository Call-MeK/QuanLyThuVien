package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserHomeFrame extends JFrame {
    
    // Các thành phần chính của giao diện
    private JPanel sidebarPanel;
    private JPanel mainContentPanel;
    private CardLayout cardLayout;
    
    // Tên font chữ dùng chung cho toàn bộ giao diện
    private String tenFont = "Segoe UI"; 
    
    public UserHomeFrame() {
        // Cài đặt cơ bản cho Cửa sổ (JFrame)
        setTitle("Hệ thống Quản lý Thư viện - Dành cho Độc giả");
        setSize(1000, 700); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Hiển thị ra giữa màn hình
        setLayout(new BorderLayout()); 
        
        // Gọi hàm khởi tạo các thành phần
        initComponents();
    }
    
    private void initComponents() {
        // ==========================================
        // 1. TẠO MENU BÊN TRÁI (SIDEBAR)
        // ==========================================
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BorderLayout()); 
        sidebarPanel.setBackground(new Color(30, 41, 59)); // Màu nền menu
        sidebarPanel.setPreferredSize(new Dimension(240, 0));
        
        // Panel chứa các nút bấm menu
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(6, 1, 0, 5)); 
        menuPanel.setBackground(new Color(30, 41, 59));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0)); 
        
        // Tiêu đề Menu
        JLabel logoLabel = new JLabel(" MENU ĐỘC GIẢ", SwingConstants.CENTER);
        logoLabel.setFont(new Font(tenFont, Font.BOLD, 15)); 
        logoLabel.setForeground(new Color(148, 163, 184)); 
        logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); 
        menuPanel.add(logoLabel);
        
        // Tạo các nút bấm
        JButton btnTrangChu = createMenuButton("Trang Chủ");
        JButton btnTimSach = createMenuButton("Tìm Sách");
        JButton btnSachDangMuon = createMenuButton("Sách Đang Mượn");
        JButton btnThongTin = createMenuButton("Thông Tin Cá Nhân");
        JButton btnDangXuat = createMenuButton("Đăng Xuất");

        btnDangXuat.setForeground(new Color(248, 113, 113)); // Màu đỏ cho nút đăng xuất
        
        // Thêm nút vào menuPanel
        menuPanel.add(btnTrangChu);
        menuPanel.add(btnTimSach);
        menuPanel.add(btnSachDangMuon);
        menuPanel.add(btnThongTin);
        menuPanel.add(btnDangXuat);
        
        sidebarPanel.add(menuPanel, BorderLayout.NORTH);
        
        // ==========================================
        // 2. TẠO PHẦN NỘI DUNG CHÍNH (CARDLAYOUT)
        // ==========================================
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel();
        mainContentPanel.setLayout(cardLayout);
        mainContentPanel.setBackground(new Color(248, 250, 252)); 
        
        // GỌI CÁC HÀM TẠO GIAO DIỆN CHỨC NĂNG
        JPanel panelTrangChu = createHomePanel(); 
        JPanel panelTimSach = createSearchPanel(); 
        JPanel panelSachDangMuon = createBorrowedBooksPanel(); 
        JPanel panelThongTin = createPlaceholderPanel("Giao diện Thông tin cá nhân");
        
        // Đưa các giao diện vào CardLayout
        mainContentPanel.add(panelTrangChu, "TrangChu");
        mainContentPanel.add(panelTimSach, "TimSach");
        mainContentPanel.add(panelSachDangMuon, "SachDangMuon");
        mainContentPanel.add(panelThongTin, "ThongTin");
        
        // ==========================================
        // 3. BẮT SỰ KIỆN CHUYỂN TRANG
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
                int confirm = JOptionPane.showConfirmDialog(UserHomeFrame.this, "Bạn có muốn đăng xuất không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(UserHomeFrame.this, "Đăng xuất thành công!");
                    dispose(); 
                }
            }
        });
        
        // Lắp ráp menu và nội dung vào cửa sổ chính
        add(sidebarPanel, BorderLayout.WEST); 
        add(mainContentPanel, BorderLayout.CENTER); 
    }
    
    // ==========================================
    // CÁC HÀM TẠO GIAO DIỆN CHỨC NĂNG
    // ==========================================
    
    // 1. TẠO TRANG CHỦ
    private JPanel createHomePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(0, 20));
        panel.setBackground(new Color(248, 250, 252)); 
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Thư Viện Online", SwingConstants.LEFT);
        title.setFont(new Font(tenFont, Font.BOLD, 26));
        title.setForeground(new Color(15, 23, 42)); 
        panel.add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 20));
        centerPanel.setBackground(new Color(248, 250, 252));

        // Khung để chèn ảnh banner
        JLabel banner = new JLabel("", SwingConstants.CENTER);
        banner.setOpaque(true);
        banner.setBackground(new Color(226, 232, 240)); 
        banner.setPreferredSize(new Dimension(0, 150));
        try {
            java.net.URL imgURL = getClass().getResource("C:\\Users\\Admin\\Documents\\NetBeansProjects\\QuanLyThuVien\\src\\main\\resources\\Images\\Background_Library_fix.jpg");
    
    if (imgURL != null) {
        ImageIcon originalIcon = new ImageIcon(imgURL);
        java.awt.Image img = originalIcon.getImage();

        java.awt.Image scaledImg = img.getScaledInstance(900, 150, java.awt.Image.SCALE_SMOOTH);
        banner.setIcon(new ImageIcon(scaledImg));
    } else {
        banner.setText("Không tìm thấy ảnh!");
        banner.setForeground(Color.RED);
    }
} catch (Exception e) {
    e.printStackTrace();
}
        centerPanel.add(banner, BorderLayout.NORTH);

        // Khung sách gợi ý
        JPanel booksArea = new JPanel(new BorderLayout(0, 10));
        booksArea.setBackground(new Color(248, 250, 252));
        
        JLabel subtitle = new JLabel("Sách Gợi Ý Cho Bạn");
        subtitle.setFont(new Font(tenFont, Font.BOLD, 18));
        booksArea.add(subtitle, BorderLayout.NORTH);

        JPanel booksList = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        booksList.setBackground(new Color(248, 250, 252));

        booksList.add(createBookCard("Đắc Nhân Tâm", "Dale Carnegie", "Kỹ năng"));
        booksList.add(createBookCard("Nhà Giả Kim", "Paulo Coelho", "Tiểu thuyết"));
        booksList.add(createBookCard("Sapiens", "Yuval Noah Harari", "Lịch sử"));
        booksList.add(createBookCard("Tuổi Trẻ Đáng Giá...", "Rosie Nguyễn", "Tự lực"));

        JScrollPane scroll = new JScrollPane(booksList);
        scroll.setBorder(null); 
        booksArea.add(scroll, BorderLayout.CENTER);

        centerPanel.add(booksArea, BorderLayout.CENTER);
        panel.add(centerPanel, BorderLayout.CENTER);

        return panel;
    }

    // 2. TẠO TRANG TÌM SÁCH
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(new Color(248, 250, 252)); 
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel(new BorderLayout(0, 10));
        topPanel.setBackground(new Color(248, 250, 252));

        JLabel title = new JLabel("Tìm Kiếm Sách");
        title.setFont(new Font(tenFont, Font.BOLD, 26));
        topPanel.add(title, BorderLayout.NORTH);

        JPanel searchBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchBar.setBackground(new Color(248, 250, 252));
        
        searchBar.add(new JLabel("Từ khóa:"));
        JTextField txtSearch = new JTextField(20);
        searchBar.add(txtSearch);
        
        JComboBox<String> cbType = new JComboBox<>(new String[]{"Tên sách", "Tác giả", "Thể loại"});
        searchBar.add(cbType);
        
        JButton btnSearch = new JButton("Tìm");
        btnSearch.setBackground(new Color(14, 165, 233));
        btnSearch.setForeground(Color.WHITE);
        searchBar.add(btnSearch);

        topPanel.add(searchBar, BorderLayout.CENTER);
        panel.add(topPanel, BorderLayout.NORTH);

        // Bảng dữ liệu sách
        String[] cols = {"Mã Sách", "Tên Sách", "Tác Giả", "Thể Loại", "Số Lượng", "Trạng Thái"};
        Object[][] data = {
            {"B01", "Lập trình Java", "Nhiều tác giả", "Giáo trình", "5", "Sẵn sàng"},
            {"B02", "Cấu trúc dữ liệu", "Nguyễn Văn A", "Giáo trình", "0", "Hết sách"}
        };
        
        JTable table = new JTable(data, cols);
        table.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(table);
        panel.add(scroll, BorderLayout.CENTER);

        // Nút mượn sách
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(248, 250, 252));
        JButton btnBorrow = new JButton("Mượn Sách Đã Chọn");
        btnBorrow.setBackground(new Color(34, 197, 94));
        btnBorrow.setForeground(Color.WHITE);
        bottomPanel.add(btnBorrow);
        
        panel.add(bottomPanel, BorderLayout.SOUTH);

        // Sự kiện mượn sách cơ bản
        btnBorrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if(row == -1) {
                    JOptionPane.showMessageDialog(panel, "Vui lòng chọn 1 sách trong bảng!");
                } else {
                    JOptionPane.showMessageDialog(panel, "Đã gửi yêu cầu mượn sách thành công!");
                }
            }
        });

        return panel;
    }

    // 3. TẠO TRANG SÁCH ĐANG MƯỢN
    private JPanel createBorrowedBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(new Color(248, 250, 252)); 
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Sách Đang Mượn");
        title.setFont(new Font(tenFont, Font.BOLD, 26));
        panel.add(title, BorderLayout.NORTH);

        // Bảng hiển thị
        String[] cols = {"Mã Phiếu", "Tên Sách", "Ngày Mượn", "Hạn Trả", "Trạng Thái"};
        Object[][] data = {
            {"PM01", "Toán rời rạc", "01/03/2026", "15/03/2026", "Còn hạn"},
            {"PM02", "Clean Code", "10/01/2026", "24/01/2026", "Quá hạn"}
        };
        
        JTable table = new JTable(data, cols);
        table.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(table);
        panel.add(scroll, BorderLayout.CENTER);

        // Nút gia hạn
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(248, 250, 252));
        JButton btnRenew = new JButton("Gia Hạn Sách");
        btnRenew.setBackground(new Color(245, 158, 11));
        btnRenew.setForeground(Color.WHITE);
        bottomPanel.add(btnRenew);
        
        panel.add(bottomPanel, BorderLayout.SOUTH);

        // Sự kiện gia hạn cơ bản
        btnRenew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if(row == -1) {
                    JOptionPane.showMessageDialog(panel, "Vui lòng chọn 1 cuốn sách!");
                } else {
                    String status = table.getValueAt(row, 4).toString();
                    if(status.equals("Quá hạn")) {
                        JOptionPane.showMessageDialog(panel, "Sách đã quá hạn, không thể gia hạn!");
                    } else {
                        JOptionPane.showMessageDialog(panel, "Gia hạn thêm 7 ngày thành công!");
                    }
                }
            }
        });

        return panel;
    }

    // ==========================================
    // CÁC HÀM TIỆN ÍCH DÙNG CHUNG
    // ==========================================

    // Hàm tạo Thẻ sách nhỏ gọn
    private JPanel createBookCard(String title, String author, String category) {
        JPanel card = new JPanel(new BorderLayout(0, 5));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(160, 220)); 
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        JLabel cover = new JLabel("Hình Ảnh", SwingConstants.CENTER);
        cover.setOpaque(true);
        cover.setBackground(new Color(230, 230, 230));
        cover.setPreferredSize(new Dimension(100, 120)); 

        JPanel info = new JPanel(new GridLayout(3, 1));
        info.setBackground(Color.WHITE);
        info.add(new JLabel(" " + title));
        info.add(new JLabel(" " + author));
        info.add(new JLabel(" " + category));

        JButton btn = new JButton("Chi Tiết");
        btn.setBackground(new Color(14, 165, 233));
        btn.setForeground(Color.WHITE);

        card.add(cover, BorderLayout.NORTH);
        card.add(info, BorderLayout.CENTER);
        card.add(btn, BorderLayout.SOUTH);

        return card;
    }

    // Hàm tạo Nút Menu bên trái
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font(tenFont, Font.PLAIN, 14)); 
        button.setForeground(Color.WHITE); 
        button.setBackground(new Color(30, 41, 59)); 
        button.setFocusPainted(false); 
        button.setBorderPainted(false); 
        button.setHorizontalAlignment(SwingConstants.LEFT); 
        
        // Hiệu ứng chuột trỏ vào nút
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
    
    // Hàm tạo trang trống
    private JPanel createPlaceholderPanel(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font(tenFont, Font.PLAIN, 20)); 
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    // ==========================================
    // HÀM MAIN CHẠY CHƯƠNG TRÌNH
    // ==========================================
    public static void main(String args[]) {
        // Chạy giao diện
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UserHomeFrame().setVisible(true);
            }
        });
    }
}