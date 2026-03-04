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
    
    private String tenFont = "Segoe UI"; 
    
    // Khai báo Panel Chi Tiết riêng biệt
    private ChiTietSachPanel panelChiTietSach;
    private TimKiemSachPanel panelTimSach;
    private SachDangMuonPanel panelSachDangMuon;
    private ThongTinCaNhanPanel panelThongTin;
    private PhieuPhatPanel panelPhieuPhat;
    private String previousCard = "TrangChu"; // Lưu vết để biết đường quay lại
    
    public UserHomeFrame() {
        setTitle("Hệ thống Quản lý Thư viện - Dành cho Độc giả");
        setSize(1000, 700); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout()); 
        
        initComponents();
    }
    
    private void initComponents() {
        // 1. TẠO MENU BÊN TRÁI
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BorderLayout()); 
        sidebarPanel.setBackground(new Color(30, 41, 59)); 
        sidebarPanel.setPreferredSize(new Dimension(240, 0));
        
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(7, 1, 0, 5)); 
        menuPanel.setBackground(new Color(30, 41, 59));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0)); 
        
        JLabel logoLabel = new JLabel(" MENU ĐỘC GIẢ", SwingConstants.CENTER);
        logoLabel.setFont(new Font(tenFont, Font.BOLD, 15)); 
        logoLabel.setForeground(new Color(148, 163, 184)); 
        logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); 
        menuPanel.add(logoLabel);
        
        JButton btnTrangChu = createMenuButton("Trang Chủ");
        JButton btnTimSach = createMenuButton("Tìm Sách");
        JButton btnSachDangMuon = createMenuButton("Sách Đang Mượn");
        JButton btnPhieuPhat = createMenuButton("Phiếu Phạt & Thanh Toán");
        JButton btnThongTin = createMenuButton("Thông Tin Cá Nhân");
        JButton btnDangXuat = createMenuButton("Đăng Xuất");

        btnDangXuat.setForeground(new Color(248, 113, 113)); 
        
        menuPanel.add(btnTrangChu);
        menuPanel.add(btnTimSach);
        menuPanel.add(btnSachDangMuon);
        menuPanel.add(btnPhieuPhat);
        menuPanel.add(btnThongTin);
        menuPanel.add(btnDangXuat);
        
        sidebarPanel.add(menuPanel, BorderLayout.NORTH);
        
        // 2. TẠO CARDLAYOUT (NỘI DUNG CHÍNH)
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel();
        mainContentPanel.setLayout(cardLayout);
        mainContentPanel.setBackground(new Color(248, 250, 252)); 
        
        JPanel panelTrangChu = createHomePanel(); 
        panelTimSach = new TimKiemSachPanel();
        panelSachDangMuon = new SachDangMuonPanel();
        panelPhieuPhat = new PhieuPhatPanel();
        panelThongTin = new ThongTinCaNhanPanel();
        
        mainContentPanel.add(panelTrangChu, "TrangChu");
        mainContentPanel.add(panelTimSach, "TimSach");
        mainContentPanel.add(panelSachDangMuon, "SachDangMuon");
        mainContentPanel.add(panelPhieuPhat, "PhieuPhat");
        mainContentPanel.add(panelThongTin, "ThongTin");

        // KHỞI TẠO VÀ THÊM CHI TIẾT SÁCH PANEL VÀO HOME
        panelChiTietSach = new ChiTietSachPanel();
        mainContentPanel.add(panelChiTietSach, "ChiTietSach");

        // Sự kiện: Bấm nút Quay Lại bên trong Panel Chi Tiết
        panelChiTietSach.getBtnQuayLai().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainContentPanel, previousCard);
            }
        });

        // Sự kiện: Bấm nút Đăng Ký Mượn
        panelChiTietSach.getBtnMuonSach().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(UserHomeFrame.this, "Đã gửi yêu cầu mượn sách thành công!");
            }
        });
        
        // 3. SỰ KIỆN CHUYỂN TRANG MENU
        btnTrangChu.addActionListener(e -> {
            cardLayout.show(mainContentPanel, "TrangChu");
            previousCard = "TrangChu"; 
        });
        
        btnTimSach.addActionListener(e -> {
            cardLayout.show(mainContentPanel, "TimSach");
            previousCard = "TimSach"; 
        });
        
        btnSachDangMuon.addActionListener(e -> {
            cardLayout.show(mainContentPanel, "SachDangMuon");
            previousCard = "SachDangMuon"; 
        });
        
        btnPhieuPhat.addActionListener(e -> {
            cardLayout.show(mainContentPanel, "PhieuPhat");
            previousCard = "PhieuPhat"; 
        });

        btnThongTin.addActionListener(e -> {
            cardLayout.show(mainContentPanel, "ThongTin");
            previousCard = "ThongTin"; 
        });
        
        btnDangXuat.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(UserHomeFrame.this, "Bạn có muốn đăng xuất không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(UserHomeFrame.this, "Đăng xuất thành công!");
                dispose(); 
            }
        });
        
        add(sidebarPanel, BorderLayout.WEST); 
        add(mainContentPanel, BorderLayout.CENTER); 
        
        // Sự kiện: Bấm "Xem Chi Tiết / Mượn" trong trang Tìm Sách
        panelTimSach.getBtnXemChiTiet().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable table = panelTimSach.getTable();
                int row = table.getSelectedRow();

                if(row == -1) {
                JOptionPane.showMessageDialog(UserHomeFrame.this, "Vui lòng chọn 1 sách trong bảng!");
                } else {
                    // Lấy dữ liệu từ dòng được chọn trong bảng
                    String tenSach = table.getValueAt(row, 1).toString();
                    String tacGia = table.getValueAt(row, 2).toString();
                    String theLoai = table.getValueAt(row, 3).toString();
                    String tinhTrang = table.getValueAt(row, 4).toString();

                    // Truyền dữ liệu sang panel Chi Tiết Sách
                    panelChiTietSach.setThongTinSach(
                        tenSach, 
                        tacGia, 
                        theLoai, 
                        "NXB Mặc Định", 
                        "2023", 
                        tinhTrang, 
                        "Mô tả chi tiết cho sách: " + tenSach
                    );

                    // Cập nhật vết (để biết ấn nút Quay Lại sẽ về trang Tìm Kiếm)
                    previousCard = "TimSach"; 
                    cardLayout.show(mainContentPanel, "ChiTietSach");
                }   
            }
        });
        
        // ==========================================
        // SỰ KIỆN CHO PANEL SÁCH ĐANG MƯỢN
        // ==========================================
        // 1. Nút Xem Chi Tiết
        panelSachDangMuon.getBtnXemChiTiet().addActionListener(e -> {
            JTable table = panelSachDangMuon.getTable();
            int row = table.getSelectedRow();
            if(row == -1) {
                JOptionPane.showMessageDialog(UserHomeFrame.this, "Vui lòng chọn 1 phiếu mượn để xem chi tiết!");
            } else {
                String tenSach = table.getValueAt(row, 1).toString();
                // Mượn tạm giao diện ChiTietSachPanel để hiển thị, hoặc sau này bạn tạo ChiTietPhieuMuonFrame riêng
                panelChiTietSach.setThongTinSach(tenSach, "Đang cập nhật", "Đang cập nhật", "N/A", "N/A", "Đang mượn", "Chi tiết cho phiếu mượn: " + table.getValueAt(row, 0));
                previousCard = "SachDangMuon";
                cardLayout.show(mainContentPanel, "ChiTietSach");
            }
        });

        // 2. Nút Gia Hạn
        panelSachDangMuon.getBtnGiaHan().addActionListener(e -> {
            JTable table = panelSachDangMuon.getTable();
            int row = table.getSelectedRow();
            if(row == -1) {
                JOptionPane.showMessageDialog(UserHomeFrame.this, "Vui lòng chọn 1 sách để gia hạn!");
            } else {
                String status = table.getValueAt(row, 4).toString();
                if(status.equalsIgnoreCase("Quá hạn")) {
                    JOptionPane.showMessageDialog(UserHomeFrame.this, "Sách đã quá hạn, không thể gia hạn. Vui lòng mang sách đến thư viện nộp phạt!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                } else {
                    int confirm = JOptionPane.showConfirmDialog(UserHomeFrame.this, "Bạn có chắc chắn muốn gia hạn thêm 7 ngày cho sách: " + table.getValueAt(row, 1) + "?", "Xác nhận gia hạn", JOptionPane.YES_NO_OPTION);
                    if(confirm == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(UserHomeFrame.this, "Gia hạn thành công!");
                        // Code update CSDL sau này ở đây
                    }
                }
            }
        });

        // 3. Nút Trả Sách
        panelSachDangMuon.getBtnTraSach().addActionListener(e -> {
            JTable table = panelSachDangMuon.getTable();
            int row = table.getSelectedRow();
            if(row == -1) {
                JOptionPane.showMessageDialog(UserHomeFrame.this, "Vui lòng chọn 1 sách để trả!");
            } else {
                int confirm = JOptionPane.showConfirmDialog(UserHomeFrame.this, "Bạn muốn xác nhận trả cuốn sách: " + table.getValueAt(row, 1) + "?", "Xác nhận trả sách", JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(UserHomeFrame.this, "Xác nhận trả sách thành công! Cảm ơn bạn.");
                    // Code update trạng thái trong CSDL
                }
            }
        });
        
        
        
        // ==========================================
        // SỰ KIỆN CHO PANEL THÔNG TIN CÁ NHÂN
        // ==========================================
        // 1. Nút Cập nhật thông tin
        panelThongTin.getBtnCapNhat().addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(UserHomeFrame.this, "Bạn có muốn lưu các thay đổi về thông tin cá nhân không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION) {
                String sdtMoi = panelThongTin.getSoDienThoai();
                String emailMoi = panelThongTin.getEmail();
                // Gọi DAO cập nhật CSDL ở đây...

                JOptionPane.showMessageDialog(UserHomeFrame.this, "Cập nhật thông tin thành công!\nSĐT: " + sdtMoi + "\nEmail: " + emailMoi);
            }
        });

        // 2. Nút Đổi mật khẩu
        panelThongTin.getBtnDoiMatKhau().addActionListener(e -> {
            // Mở một Dialog nhỏ để nhập mật khẩu cũ, mới
            JPasswordField txtPassCu = new JPasswordField();
            JPasswordField txtPassMoi = new JPasswordField();
            JPasswordField txtXacNhan = new JPasswordField();

            Object[] message = {
                "Mật khẩu hiện tại:", txtPassCu,
                "Mật khẩu mới:", txtPassMoi,
                "Xác nhận mật khẩu mới:", txtXacNhan
            };

            int option = JOptionPane.showConfirmDialog(UserHomeFrame.this, message, "Đổi Mật Khẩu", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                // Xử lý logic so sánh mật khẩu ở đây
                JOptionPane.showMessageDialog(UserHomeFrame.this, "Đổi mật khẩu thành công! Vui lòng sử dụng mật khẩu mới cho lần đăng nhập sau.");
            }
        });
            }
    
    // --- CÁC PANEL CHỨC NĂNG CỦA HOME ---

    private JPanel createHomePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(0, 20));
        panel.setBackground(new Color(248, 250, 252)); 
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Thư Viện Online", SwingConstants.LEFT);
        title.setFont(new Font(tenFont, Font.ITALIC, 26));
        title.setForeground(new Color(15, 23, 42)); 
        panel.add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 20));
        centerPanel.setBackground(new Color(248, 250, 252));

        JLabel banner = new JLabel("", SwingConstants.CENTER);
        banner.setOpaque(true);
        banner.setBackground(new Color(226, 232, 240)); 
        banner.setPreferredSize(new Dimension(0, 150));
        try {
            java.net.URL imgURL = getClass().getResource("/Images/welcome.jpg");
            if (imgURL != null) {
                ImageIcon originalIcon = new ImageIcon(imgURL);
                java.awt.Image img = originalIcon.getImage();
                java.awt.Image scaledImg = img.getScaledInstance(900, 150, java.awt.Image.SCALE_SMOOTH);
                banner.setIcon(new ImageIcon(scaledImg));
            } else {
                banner.setText("Không tìm thấy ảnh welcome.jpg!");
                banner.setForeground(Color.RED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        centerPanel.add(banner, BorderLayout.NORTH);

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
        booksList.add(createBookCard("Tuổi Trẻ Đáng Giá", "Rosie Nguyễn", "Tự lực"));

        JScrollPane scroll = new JScrollPane(booksList);
        scroll.setBorder(null); 
        booksArea.add(scroll, BorderLayout.CENTER);

        centerPanel.add(booksArea, BorderLayout.CENTER);
        panel.add(centerPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createBorrowedBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(new Color(248, 250, 252)); 
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // (Giữ nguyên nội dung của Trang Sách Đang Mượn)
        JLabel title = new JLabel("Sách Đang Mượn");
        title.setFont(new Font(tenFont, Font.BOLD, 26));
        panel.add(title, BorderLayout.NORTH);
        
        String[] cols = {"Mã Phiếu", "Tên Sách", "Ngày Mượn", "Hạn Trả", "Trạng Thái"};
        Object[][] data = {
            {"PM01", "Toán rời rạc", "01/03/2026", "15/03/2026", "Còn hạn"},
            {"PM02", "Clean Code", "10/01/2026", "24/01/2026", "Quá hạn"}
        };
        JTable table = new JTable(data, cols);
        table.setRowHeight(25);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        return panel;
    }

    // --- CÁC HÀM TIỆN ÍCH ---

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

        // BẮT SỰ KIỆN GỌI GIAO DIỆN CHITIETSACHPANEL
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Truyền dữ liệu vào Panel Chi Tiết
                panelChiTietSach.setThongTinSach(
                    title, 
                    author, 
                    category, 
                    "NXB Mặc Định", 
                    "2023", 
                    "Sẵn sàng", 
                    "Mô tả chi tiết của sách " + title + " sẽ được tải từ CSDL."
                );
                
                // Hiển thị panel chi tiết
                cardLayout.show(mainContentPanel, "ChiTietSach");
            }
        });

        card.add(cover, BorderLayout.NORTH);
        card.add(info, BorderLayout.CENTER);
        card.add(btn, BorderLayout.SOUTH);

        return card;
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font(tenFont, Font.PLAIN, 14)); 
        button.setForeground(Color.WHITE); 
        button.setBackground(new Color(30, 41, 59)); 
        button.setFocusPainted(false); 
        button.setBorderPainted(false); 
        button.setHorizontalAlignment(SwingConstants.LEFT); 
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) { button.setBackground(new Color(51, 65, 85)); }
            @Override
            public void mouseExited(MouseEvent evt) { button.setBackground(new Color(30, 41, 59)); }
        });
        return button;
    }
    
    private JPanel createPlaceholderPanel(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font(tenFont, Font.PLAIN, 20)); 
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new UserHomeFrame().setVisible(true));
    }
}