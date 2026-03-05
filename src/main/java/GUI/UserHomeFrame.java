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
    private String maDocGiaDangNhap = ""; // Biến hứng mã Độc giả

    // HÀM KHỞI TẠO ĐỂ NHẬN MÃ ĐỘC GIẢ TỪ LOGIN
    public UserHomeFrame(String maDocGia) {
        this.maDocGiaDangNhap = maDocGia;

        setTitle("Hệ thống Quản lý Thư viện - Dành cho Độc giả");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initComponents();

        // TRUYỀN MÃ CHO PANEL THÔNG TIN SAU KHI NÓ ĐÃ ĐƯỢC KHỞI TẠO
        if (panelThongTin != null && !this.maDocGiaDangNhap.isEmpty()) {
            panelThongTin.loadData(this.maDocGiaDangNhap);
        }
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
                JOptionPane.showMessageDialog(UserHomeFrame.this,
                        "Sách hiện đang có sẵn. Vui lòng mang thẻ độc giả đến quầy thư viện để làm thủ tục mượn!",
                        "Hướng dẫn mượn sách",
                        JOptionPane.INFORMATION_MESSAGE);
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
            int confirm = JOptionPane.showConfirmDialog(UserHomeFrame.this, "Bạn có muốn đăng xuất không?", "Xác nhận",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(UserHomeFrame.this, "Đăng xuất thành công!");

                // Mở lại form đăng nhập khi đăng xuất
                new LoginFrame().setVisible(true);
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

                if (row == -1) {
                    JOptionPane.showMessageDialog(UserHomeFrame.this, "Vui lòng chọn 1 sách trong bảng!");
                } else {
                    String tenSach = table.getValueAt(row, 1).toString();
                    String tacGia = table.getValueAt(row, 2).toString();
                    String theLoai = table.getValueAt(row, 3).toString();
                    String nhaXb = table.getValueAt(row, 4).toString();
                    String tinhTrang = table.getValueAt(row, 5).toString();

                    panelChiTietSach.setThongTinSach(
                            tenSach,
                            tacGia,
                            theLoai,
                            nhaXb,
                            "2023", // Tạm thời để năm cố định
                            tinhTrang,
                            "Mô tả chi tiết cho sách: " + tenSach);

                    previousCard = "TimSach";
                    cardLayout.show(mainContentPanel, "ChiTietSach");
                }
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
        return new SachDangMuonPanel();
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

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelChiTietSach.setThongTinSach(
                        title,
                        author,
                        category,
                        "NXB Mặc Định",
                        "2023",
                        "Sẵn sàng",
                        "Mô tả chi tiết của sách " + title + " sẽ được tải từ CSDL.");

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

    private JPanel createPlaceholderPanel(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font(tenFont, Font.PLAIN, 20));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    public static void main(String args[]) {
        // Truyền tạm 1 mã vào đây để lúc bạn test bằng cách Run File này nó không bị
        // lỗi
        java.awt.EventQueue.invokeLater(() -> new UserHomeFrame("DG00000002").setVisible(true));
    }
}