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
<<<<<<< HEAD
        title.setFont(new Font(tenFont, Font.ITALIC | Font.BOLD, 28)); // Chỉnh font to và đậm hơn
        title.setForeground(new Color(15, 23, 42)); 
=======
        title.setFont(new Font(tenFont, Font.ITALIC, 26));
        title.setForeground(new Color(15, 23, 42));
>>>>>>> 6d4dc81f0b60e9f1c9a13f18c88ec223370a7fa8
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

        JPanel booksArea = new JPanel(new BorderLayout(0, 15)); // Tăng khoảng cách
        booksArea.setBackground(new Color(248, 250, 252));

        JLabel subtitle = new JLabel("Sách Gợi Ý Cho Bạn");
        subtitle.setFont(new Font(tenFont, Font.BOLD, 20)); // Font to hơn
        subtitle.setForeground(new Color(30, 41, 59));
        booksArea.add(subtitle, BorderLayout.NORTH);

        JPanel booksList = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10)); // Thêm margin
        booksList.setBackground(new Color(248, 250, 252));

        // ==========================================
        // LẤY DỮ LIỆU SÁCH TỪ DATABASE (SQL SERVER)
        // ==========================================
        BUS.SachBUS sachBUS = new BUS.SachBUS();
        java.util.List<DTO.SachDTO> dsSach = sachBUS.getAll();

        if (dsSach != null && !dsSach.isEmpty()) {
            int count = 0;
            for (DTO.SachDTO sach : dsSach) {
                if (count >= 5) break; // Chỉ lấy 5 cuốn đầu tiên làm gợi ý
                booksList.add(createBookCard(sach)); // Gọi hàm createBookCard mới
                count++;
            }
        } else {
            JLabel lblEmpty = new JLabel("Hiện chưa có sách nào trong thư viện (Database đang trống).");
            lblEmpty.setFont(new Font(tenFont, Font.ITALIC, 14));
            lblEmpty.setForeground(Color.RED);
            booksList.add(lblEmpty);
        }

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

    // Truyền thẳng đối tượng SachDTO vào thay vì các String rời rạc
    private JPanel createBookCard(DTO.SachDTO sach) {
        String title = sach.getTenSach() != null ? sach.getTenSach() : "Chưa cập nhật tên";
        String category = sach.getTheLoai() != null ? sach.getTheLoai() : "Khác";
        String namXB = String.valueOf(sach.getNamXB());

        JPanel card = new JPanel(new BorderLayout(0, 5));
        card.setBackground(Color.WHITE);
<<<<<<< HEAD
        card.setPreferredSize(new Dimension(180, 250)); // Cho thẻ to ra 1 chút
        // Viền thẻ sách màu xám nhạt, trông thanh lịch hơn
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        JLabel cover = new JLabel("Hình Ảnh", SwingConstants.CENTER);
        cover.setOpaque(true);
        cover.setBackground(new Color(241, 245, 249));
        cover.setForeground(new Color(148, 163, 184));
        cover.setPreferredSize(new Dimension(100, 130)); 
=======
        card.setPreferredSize(new Dimension(160, 220));
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        JLabel cover = new JLabel("Hình Ảnh", SwingConstants.CENTER);
        cover.setOpaque(true);
        cover.setBackground(new Color(230, 230, 230));
        cover.setPreferredSize(new Dimension(100, 120));
>>>>>>> 6d4dc81f0b60e9f1c9a13f18c88ec223370a7fa8

        JPanel info = new JPanel(new GridLayout(3, 1, 0, 2));
        info.setBackground(Color.WHITE);
        
        // CHỈNH FONT VÀ MÀU SẮC CHO CHỮ THẬT ĐẸP
        JLabel lblTitle = new JLabel(" " + title);
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 14)); // Tên sách in đậm
        lblTitle.setForeground(new Color(15, 23, 42)); // Màu xám đen tuyền
        
        JLabel lblCategory = new JLabel(" Thể loại: " + category);
        lblCategory.setFont(new Font(tenFont, Font.PLAIN, 12));
        lblCategory.setForeground(new Color(100, 116, 139)); // Màu xám nhạt
        
        JLabel lblYear = new JLabel(" Năm XB: " + namXB);
        lblYear.setFont(new Font(tenFont, Font.ITALIC, 12));
        lblYear.setForeground(new Color(100, 116, 139));

        info.add(lblTitle);
        info.add(lblCategory);
        info.add(lblYear);

        // ==========================================
        // LÀM ĐẸP NÚT "CHI TIẾT"
        // ==========================================
        JButton btn = new JButton("Chi Tiết");
        btn.setFont(new Font(tenFont, Font.BOLD, 13));
        btn.setBackground(new Color(99, 102, 241)); // Màu nền: Xanh Indigo cực đẹp
        btn.setForeground(Color.WHITE); // Chữ trắng
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Đổi con trỏ thành bàn tay

        // Hiệu ứng Hover: Di chuột vào thì nút đổi màu đậm hơn
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(79, 70, 229)); // Màu Indigo đậm
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(99, 102, 241)); // Trả lại màu gốc
            }
        });

        // Sự kiện khi bấm nút Chi Tiết: Đổ dữ liệu thật vào panel Chi Tiết
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelChiTietSach.setThongTinSach(
<<<<<<< HEAD
                    title, 
                    "Nhiều tác giả", // Do database Sach chưa join bảng Tác Giả
                    category, 
                    sach.getMaNXB(), 
                    namXB, 
                    "Sẵn sàng", 
                    "Mã sách: " + sach.getMaSach() + "\nGiá bìa: " + sach.getGiaBia() + " VNĐ\nNgôn ngữ: " + sach.getNgonNgu()
                );
                
=======
                        title,
                        author,
                        category,
                        "NXB Mặc Định",
                        "2023",
                        "Sẵn sàng",
                        "Mô tả chi tiết của sách " + title + " sẽ được tải từ CSDL.");

>>>>>>> 6d4dc81f0b60e9f1c9a13f18c88ec223370a7fa8
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
<<<<<<< HEAD
=======

    private JPanel createPlaceholderPanel(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font(tenFont, Font.PLAIN, 20));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }
>>>>>>> 6d4dc81f0b60e9f1c9a13f18c88ec223370a7fa8

    public static void main(String args[]) {
        // Truyền tạm 1 mã vào đây để lúc bạn test bằng cách Run File này nó không bị
        // lỗi
        java.awt.EventQueue.invokeLater(() -> new UserHomeFrame("DG00000002").setVisible(true));
    }
}