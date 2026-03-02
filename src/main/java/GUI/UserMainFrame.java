package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserMainFrame extends JFrame {

    private JPanel panelContent;
    private CardLayout cardLayout;

    private JButton btnTrangChu;
    private JButton btnTimSach;
    private JButton btnSachDangMuon;
    private JButton btnPhiPhat;
    private JButton btnThongTinCaNhan;
    private JButton btnDangXuat;

    private String tenFont = "Arial";

    public UserMainFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Quản Lý Thư Viện - User");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== PANEL TRÁI - MENU =====
        JPanel panelMenu = new JPanel();
        panelMenu.setBackground(new Color(40, 44, 52)); 
        panelMenu.setPreferredSize(new Dimension(220, 600));
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));

        JLabel lblTitle = new JLabel("THƯ VIỆN SGU", SwingConstants.CENTER);
        lblTitle.setForeground(new Color(14, 165, 233)); 
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 20));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        panelMenu.add(lblTitle);

        // Tạo các nút menu
        btnTrangChu = createMenuButton("🏠 Trang Chủ");
        btnTimSach = createMenuButton("🔍 Tìm Sách");
        btnSachDangMuon = createMenuButton("📚 Sách Đang Mượn");
        btnPhiPhat = createMenuButton("💰 Phí Phạt");
        
        // Đổi tên thành Thông Tin Độc Giả theo ý bạn
        btnThongTinCaNhan = createMenuButton("👤 Thông Tin Độc Giả"); 

        panelMenu.add(btnTrangChu);
        panelMenu.add(Box.createVerticalStrut(5));
        panelMenu.add(btnTimSach);
        panelMenu.add(Box.createVerticalStrut(5));
        panelMenu.add(btnSachDangMuon);
        panelMenu.add(Box.createVerticalStrut(5));
        panelMenu.add(btnPhiPhat);
        panelMenu.add(Box.createVerticalStrut(5));
        panelMenu.add(btnThongTinCaNhan);

        panelMenu.add(Box.createVerticalGlue()); 

        btnDangXuat = createMenuButton("🚪 Đăng Xuất");
        btnDangXuat.setForeground(new Color(248, 113, 113)); 
        panelMenu.add(btnDangXuat);
        panelMenu.add(Box.createVerticalStrut(20));

        // ===== PANEL PHẢI - NỘI DUNG (CARDLAYOUT) =====
        cardLayout = new CardLayout();
        panelContent = new JPanel(cardLayout);
        panelContent.setBackground(new Color(248, 250, 252));

        // Gọi các hàm tạo Panel chức năng và thêm vào CardLayout
        panelContent.add(createTrangChuPanel(), "TrangChu");
        panelContent.add(createTimKiemSachPanel(), "TimSach");
        panelContent.add(createSachDangMuonPanel(), "SachDangMuon");
        panelContent.add(createPhiPhatPanel(), "PhiPhat");
        panelContent.add(createThongTinCaNhanPanel(), "ThongTinCaNhan");

        cardLayout.show(panelContent, "TrangChu");

        // ====================================================================
        // SỰ KIỆN CHUYỂN TRANG (Sử dụng ActionListener chuẩn để không bị lỗi)
        // ====================================================================
        
        btnTrangChu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelContent, "TrangChu");
            }
        });

        btnTimSach.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelContent, "TimSach");
            }
        });

        btnSachDangMuon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelContent, "SachDangMuon");
            }
        });

        btnPhiPhat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelContent, "PhiPhat");
            }
        });

        // Xử lý click nút Thông Tin Độc Giả
        btnThongTinCaNhan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelContent, "ThongTinCaNhan");
            }
        });

        btnDangXuat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(UserMainFrame.this, "Bạn có muốn đăng xuất?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(UserMainFrame.this, "Đăng xuất thành công!");
                    dispose(); // Đóng form
                }
            }
        });

        // Lắp ráp vào Frame
        add(panelMenu, BorderLayout.WEST);
        add(panelContent, BorderLayout.CENTER);
    }

    // =========================================================================
    // CÁC HÀM TẠO GIAO DIỆN CHỨC NĂNG BÊN TRONG PANEL NỘI DUNG
    // =========================================================================

    // --- 1. PANEL TRANG CHỦ ---
    private JPanel createTrangChuPanel() {
        JPanel homePanel = new JPanel(new BorderLayout(0, 20));
        homePanel.setBackground(new Color(248, 250, 252));
        homePanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel lblPageTitle = new JLabel("Thư Viện Online");
        lblPageTitle.setFont(new Font(tenFont, Font.BOLD, 28));
        lblPageTitle.setForeground(new Color(15, 23, 42));
        homePanel.add(lblPageTitle, BorderLayout.NORTH);

        JPanel mainArea = new JPanel(new BorderLayout(0, 25));
        mainArea.setBackground(new Color(248, 250, 252));

        JLabel lblBanner = new JLabel("[ Ảnh Banner Thư Viện ]", SwingConstants.CENTER);
        lblBanner.setOpaque(true);
        lblBanner.setBackground(new Color(226, 232, 240));
        lblBanner.setForeground(new Color(100, 116, 139));
        lblBanner.setPreferredSize(new Dimension(0, 180));
        mainArea.add(lblBanner, BorderLayout.NORTH);

        JPanel hotBooksSection = new JPanel(new BorderLayout(0, 15));
        hotBooksSection.setBackground(new Color(248, 250, 252));

        JLabel lblSectionTitle = new JLabel("🔥 Gợi ý cho bạn tuần này");
        lblSectionTitle.setFont(new Font(tenFont, Font.BOLD, 18));
        lblSectionTitle.setForeground(new Color(51, 65, 85));
        hotBooksSection.add(lblSectionTitle, BorderLayout.NORTH);

        JPanel booksListPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        booksListPanel.setBackground(new Color(248, 250, 252));

        booksListPanel.add(createBookCard("Đắc Nhân Tâm", "Dale Carnegie", "Kỹ năng sống"));
        booksListPanel.add(createBookCard("Nhà Giả Kim", "Paulo Coelho", "Tiểu thuyết"));
        booksListPanel.add(createBookCard("Sapiens", "Yuval Noah Harari", "Lịch sử"));
        booksListPanel.add(createBookCard("Tôi Thấy Hoa Vàng...", "Nguyễn Nhật Ánh", "Truyện dài"));

        JScrollPane scrollPane = new JScrollPane(booksListPanel);
        scrollPane.setBorder(null);
        scrollPane.setBackground(new Color(248, 250, 252));
        scrollPane.getViewport().setBackground(new Color(248, 250, 252));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);

        hotBooksSection.add(scrollPane, BorderLayout.CENTER);
        mainArea.add(hotBooksSection, BorderLayout.CENTER);
        homePanel.add(mainArea, BorderLayout.CENTER);

        return homePanel;
    }

    // --- 2. PANEL TÌM KIẾM SÁCH ---
    private JPanel createTimKiemSachPanel() {
        JPanel searchPanel = new JPanel(new BorderLayout(0, 20));
        searchPanel.setBackground(new Color(248, 250, 252));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JPanel topPanel = new JPanel(new BorderLayout(0, 15));
        topPanel.setBackground(new Color(248, 250, 252));

        JLabel lblPageTitle = new JLabel("Tìm Kiếm Sách");
        lblPageTitle.setFont(new Font(tenFont, Font.BOLD, 28));
        topPanel.add(lblPageTitle, BorderLayout.NORTH);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        filterPanel.setBackground(new Color(248, 250, 252));

        filterPanel.add(new JLabel("Từ khóa:"));
        JTextField txtSearch = new JTextField(20);
        filterPanel.add(txtSearch);

        JComboBox<String> cbCriteria = new JComboBox<>(new String[]{"Tên sách", "Tác giả", "Thể loại"});
        filterPanel.add(cbCriteria);

        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.setBackground(new Color(14, 165, 233));
        btnSearch.setForeground(Color.WHITE);
        filterPanel.add(btnSearch);

        topPanel.add(filterPanel, BorderLayout.CENTER);
        searchPanel.add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {"Mã Sách", "Tên Sách", "Tác Giả", "Thể Loại", "Số Lượng", "Trạng Thái"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable table = new JTable(tableModel);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font(tenFont, Font.BOLD, 14));

        tableModel.addRow(new Object[]{"B001", "Lập Trình Java", "Nhiều Tác Giả", "Giáo trình", 5, "Sẵn sàng"});
        tableModel.addRow(new Object[]{"B002", "Clean Code", "Robert C. Martin", "Công nghệ", 0, "Đã mượn hết"});

        JScrollPane scrollPane = new JScrollPane(table);
        searchPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(248, 250, 252));

        JButton btnBorrow = new JButton("Đăng Ký Mượn Sách");
        btnBorrow.setBackground(new Color(34, 197, 94));
        btnBorrow.setForeground(Color.WHITE);
        bottomPanel.add(btnBorrow);

        searchPanel.add(bottomPanel, BorderLayout.SOUTH);

        btnBorrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(searchPanel, "Vui lòng chọn sách để mượn!");
                } else {
                    JOptionPane.showMessageDialog(searchPanel, "Đã gửi yêu cầu mượn sách thành công!");
                }
            }
        });

        return searchPanel;
    }

    // --- 3. PANEL SÁCH ĐANG MƯỢN ---
    private JPanel createSachDangMuonPanel() {
        JPanel borrowedPanel = new JPanel(new BorderLayout(0, 20));
        borrowedPanel.setBackground(new Color(248, 250, 252));
        borrowedPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JPanel topPanel = new JPanel(new BorderLayout(0, 10));
        topPanel.setBackground(new Color(248, 250, 252));

        JLabel lblPageTitle = new JLabel("Sách Đang Mượn");
        lblPageTitle.setFont(new Font(tenFont, Font.BOLD, 28));
        topPanel.add(lblPageTitle, BorderLayout.NORTH);

        JLabel lblDesc = new JLabel("Danh sách các quyển sách bạn đang giữ.");
        lblDesc.setFont(new Font(tenFont, Font.ITALIC, 14));
        topPanel.add(lblDesc, BorderLayout.CENTER);

        String[] columnNames = {"Mã Phiếu", "Tên Sách", "Ngày Mượn", "Hạn Trả", "Tình Trạng"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable table = new JTable(tableModel);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font(tenFont, Font.BOLD, 14));

        tableModel.addRow(new Object[]{"PM-01", "Cấu trúc dữ liệu", "01/03/2026", "15/03/2026", "Còn hạn"});
        tableModel.addRow(new Object[]{"PM-02", "Toán rời rạc", "10/01/2026", "24/01/2026", "Quá hạn"});

        JScrollPane scrollPane = new JScrollPane(table);
        borrowedPanel.add(topPanel, BorderLayout.NORTH);
        borrowedPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(248, 250, 252));

        JButton btnRenew = new JButton("Gia Hạn Sách");
        btnRenew.setBackground(new Color(245, 158, 11));
        btnRenew.setForeground(Color.WHITE);
        bottomPanel.add(btnRenew);

        borrowedPanel.add(bottomPanel, BorderLayout.SOUTH);

        btnRenew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(borrowedPanel, "Vui lòng chọn sách muốn gia hạn!");
                } else {
                    String status = table.getValueAt(selectedRow, 4).toString();
                    if (status.equals("Quá hạn")) {
                        JOptionPane.showMessageDialog(borrowedPanel, "Sách đã quá hạn, không thể gia hạn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(borrowedPanel, "Gia hạn thêm 7 ngày thành công!");
                    }
                }
            }
        });

        return borrowedPanel;
    }

    // --- 4. PANEL PHÍ PHẠT ---
    private JPanel createPhiPhatPanel() {
        JPanel finePanel = new JPanel(new BorderLayout(0, 20));
        finePanel.setBackground(new Color(248, 250, 252));
        finePanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel lblPageTitle = new JLabel("Quản Lý Phí Phạt");
        lblPageTitle.setFont(new Font(tenFont, Font.BOLD, 28));
        finePanel.add(lblPageTitle, BorderLayout.NORTH);

        String[] columnNames = {"Mã Phí", "Mã Phiếu Mượn", "Lý Do", "Số Tiền (VND)", "Trạng Thái"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable table = new JTable(tableModel);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font(tenFont, Font.BOLD, 14));

        tableModel.addRow(new Object[]{"F-001", "PM-02", "Trả sách quá hạn 3 ngày", "30,000", "Chưa thanh toán"});

        JScrollPane scrollPane = new JScrollPane(table);
        finePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(248, 250, 252));

        JButton btnPay = new JButton("Thanh Toán");
        btnPay.setBackground(new Color(34, 197, 94));
        btnPay.setForeground(Color.WHITE);
        bottomPanel.add(btnPay);

        finePanel.add(bottomPanel, BorderLayout.SOUTH);

        btnPay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(finePanel, "Vui lòng chọn khoản phí để thanh toán!");
                } else {
                    JOptionPane.showMessageDialog(finePanel, "Đã thanh toán thành công!");
                    tableModel.setValueAt("Đã thanh toán", selectedRow, 4);
                }
            }
        });

        return finePanel;
    }

    // --- 5. PANEL THÔNG TIN ĐỘC GIẢ ---
    private JPanel createThongTinCaNhanPanel() {
        JPanel profilePanel = new JPanel(new BorderLayout(0, 20));
        profilePanel.setBackground(new Color(248, 250, 252));
        profilePanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel lblPageTitle = new JLabel("Thông Tin Độc Giả");
        lblPageTitle.setFont(new Font(tenFont, Font.BOLD, 28));
        profilePanel.add(lblPageTitle, BorderLayout.NORTH);

        JPanel contentArea = new JPanel(new BorderLayout(30, 0));
        contentArea.setBackground(new Color(248, 250, 252));

        // Bên trái: Avatar
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(248, 250, 252));

        JLabel lblAvatar = new JLabel("AVATAR", SwingConstants.CENTER);
        lblAvatar.setOpaque(true);
        lblAvatar.setBackground(new Color(226, 232, 240));
        lblAvatar.setPreferredSize(new Dimension(150, 150));
        lblAvatar.setMaximumSize(new Dimension(150, 150));
        lblAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(lblAvatar);
        leftPanel.add(Box.createVerticalStrut(15));

        JLabel lblRole = new JLabel("Độc Giả");
        lblRole.setFont(new Font(tenFont, Font.BOLD, 16));
        lblRole.setForeground(new Color(14, 165, 233));
        lblRole.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(lblRole);

        // Bên phải: Thông tin GridBagLayout
        JPanel infoGrid = new JPanel(new GridBagLayout());
        infoGrid.setBackground(Color.WHITE);
        infoGrid.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addInfoRow(infoGrid, gbc, 0, "Mã Thành Viên:", "M-2026-001");
        addInfoRow(infoGrid, gbc, 1, "Họ Và Tên:", "Nguyễn Văn A");
        addInfoRow(infoGrid, gbc, 2, "Email:", "member@example.com");
        addInfoRow(infoGrid, gbc, 3, "Số Điện Thoại:", "0123 456 789");
        addInfoRow(infoGrid, gbc, 4, "Tình Trạng:", "Đang Hoạt Động");

        contentArea.add(leftPanel, BorderLayout.WEST);
        contentArea.add(infoGrid, BorderLayout.CENTER);
        profilePanel.add(contentArea, BorderLayout.CENTER);

        return profilePanel;
    }

    // =========================================================================
    // CÁC HÀM TIỆN ÍCH DÙNG CHUNG
    // =========================================================================

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(220, 45));
        btn.setPreferredSize(new Dimension(220, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFont(new Font(tenFont, Font.PLAIN, 15));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(40, 44, 52));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect sử dụng MouseAdapter chuẩn
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(new Color(60, 64, 72));
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(new Color(40, 44, 52));
            }
        });
        return btn;
    }

    private JPanel createBookCard(String title, String author, String category) {
        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(180, 260));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel lblCover = new JLabel("BÌA SÁCH", SwingConstants.CENTER);
        lblCover.setOpaque(true);
        lblCover.setBackground(new Color(241, 245, 249));
        lblCover.setPreferredSize(new Dimension(100, 140));

        JPanel info = new JPanel(new GridLayout(3, 1));
        info.setBackground(Color.WHITE);
        JLabel lTitle = new JLabel(title);
        lTitle.setFont(new Font(tenFont, Font.BOLD, 14));
        JLabel lAuthor = new JLabel(author);
        lAuthor.setForeground(Color.GRAY);
        JLabel lCat = new JLabel(category);
        lCat.setForeground(new Color(14, 165, 233));
        
        info.add(lTitle);
        info.add(lAuthor);
        info.add(lCat);

        JButton btnView = new JButton("Xem Ngay");
        btnView.setBackground(new Color(14, 165, 233));
        btnView.setForeground(Color.WHITE);
        btnView.setFocusPainted(false);

        card.add(lblCover, BorderLayout.NORTH);
        card.add(info, BorderLayout.CENTER);
        card.add(btnView, BorderLayout.SOUTH);

        return card;
    }

    private void addInfoRow(JPanel panel, GridBagConstraints gbc, int row, String label, String value) {
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        JLabel lblHeader = new JLabel(label);
        lblHeader.setFont(new Font(tenFont, Font.BOLD, 14));
        lblHeader.setForeground(Color.GRAY);
        panel.add(lblHeader, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font(tenFont, Font.PLAIN, 15));
        panel.add(lblValue, gbc);
    }

    private JPanel createPlaceholderPanel(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font(tenFont, Font.PLAIN, 20));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    // =========================================================================
    // HÀM MAIN CHẠY CHƯƠNG TRÌNH
    // =========================================================================
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {}

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UserMainFrame frame = new UserMainFrame();
                frame.setVisible(true);
            }
        });
    }
}