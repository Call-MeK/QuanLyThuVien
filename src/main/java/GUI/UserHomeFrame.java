package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import BUS.DocGiaBUS;

public class UserHomeFrame extends JFrame {

    private JPanel sidebarPanel;
    private JPanel mainContentPanel;
    private CardLayout cardLayout;

    private String tenFont = "Segoe UI";

    private ChiTietSachPanel panelChiTietSach;
    private TimKiemSachPanel panelTimSach;
    private SachDangMuonPanel panelSachDangMuon;
    private ThongTinCaNhanPanel panelThongTin;
    private PhieuPhatPanel panelPhieuPhat;

    private String previousCard = "TrangChu";
    private String maDocGiaDangNhap = "";
    private boolean isBiKhoa = false;

    public UserHomeFrame(String maDocGia) {
        this.maDocGiaDangNhap = maDocGia;

        DocGiaBUS docGiaBUS = new DocGiaBUS();
        this.isBiKhoa = docGiaBUS.isDocGiaLocked(maDocGia);

        setTitle("Hệ thống Quản lý Thư viện - Dành cho Độc giả");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initComponents();

        if (panelThongTin != null && !this.maDocGiaDangNhap.isEmpty()) {
            panelThongTin.loadData(this.maDocGiaDangNhap);
        }

        if (isBiKhoa) {
            showLockBanner();
        }
    }

    private void showLockBanner() {
        JPanel banner = new JPanel(new BorderLayout());
        banner.setBackground(new Color(220, 53, 69));
        banner.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblIcon = new JLabel("🔒");
        lblIcon.setFont(new Font(tenFont, Font.PLAIN, 20));
        lblIcon.setForeground(Color.WHITE);

        JLabel lblMsg = new JLabel(
                "Thẻ thư viện của bạn đang bị khóa — Bạn không thể mượn sách mới. "
                        + "Vui lòng đến thư viện để được hỗ trợ mở khóa.");
        lblMsg.setFont(new Font(tenFont, Font.BOLD, 14));
        lblMsg.setForeground(Color.WHITE);

        banner.add(lblIcon, BorderLayout.WEST);
        banner.add(lblMsg, BorderLayout.CENTER);

        add(banner, BorderLayout.NORTH);
        revalidate();
    }

    private void initComponents() {
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

        cardLayout = new CardLayout();
        mainContentPanel = new JPanel();
        mainContentPanel.setLayout(cardLayout);
        mainContentPanel.setBackground(new Color(248, 250, 252));

        JPanel panelTrangChu = createHomePanel();
        panelTimSach = new TimKiemSachPanel();
        panelSachDangMuon = new SachDangMuonPanel();
        panelPhieuPhat = new PhieuPhatPanel();
        panelThongTin = new ThongTinCaNhanPanel();
        panelChiTietSach = new ChiTietSachPanel();

        mainContentPanel.add(panelTrangChu, "TrangChu");
        mainContentPanel.add(panelTimSach, "TimSach");
        mainContentPanel.add(panelSachDangMuon, "SachDangMuon");
        mainContentPanel.add(panelPhieuPhat, "PhieuPhat");
        mainContentPanel.add(panelThongTin, "ThongTin");
        mainContentPanel.add(panelChiTietSach, "ChiTietSach");

        panelChiTietSach.getBtnQuayLai().addActionListener(e -> cardLayout.show(mainContentPanel, previousCard));

        panelChiTietSach.getBtnMuonSach().addActionListener(e -> {
            if (isBiKhoa) {
                JOptionPane.showMessageDialog(UserHomeFrame.this,
                        "Thẻ thư viện của bạn đang bị khóa!\n"
                                + "Vui lòng đến thư viện để được hỗ trợ mở khóa trước khi mượn sách.",
                        "Không thể mượn sách", JOptionPane.WARNING_MESSAGE);
            } else {
                String maSach = panelChiTietSach.getCurrentMaSach();
                if (maSach == null || maSach.isEmpty()) {
                    JOptionPane.showMessageDialog(UserHomeFrame.this,
                            "Không xác định được mã sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                BUS.SachCopyBUS copyBUS = new BUS.SachCopyBUS();
                java.util.List<DTO.SachCopyDTO> dsAvailable = copyBUS.getAvailable(maSach);
                if (dsAvailable == null || dsAvailable.isEmpty()) {
                    JOptionPane.showMessageDialog(UserHomeFrame.this,
                            "Hiện tại sách này đã hết! Vui lòng quay lại sau.",
                            "Hết sách", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                BUS.NguoiQuanLyBUS nqlBUS = new BUS.NguoiQuanLyBUS();
                java.util.List<DTO.NguoiQuanLyDTO> dsNQL = nqlBUS.getAll();
                java.util.Vector<String> nqlOptions = new java.util.Vector<>();
                java.util.Map<String, String> nqlMap = new java.util.HashMap<>();
                if (dsNQL != null) {
                    for (DTO.NguoiQuanLyDTO nql : dsNQL) {
                        if (nql.getIsDeleted() == null || !nql.getIsDeleted()) {
                            String displayText = nql.getHoTen() + " (" + nql.getMaNQL().trim() + ")";
                            nqlOptions.add(displayText);
                            nqlMap.put(displayText, nql.getMaNQL());
                        }
                    }
                }

                if (nqlOptions.isEmpty()) {
                    JOptionPane.showMessageDialog(UserHomeFrame.this,
                            "Hệ thống chưa có người quản lý nào để tiếp nhận yêu cầu!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JComboBox<String> cbAdmins = new JComboBox<>(nqlOptions);
                cbAdmins.setFont(new Font(tenFont, Font.PLAIN, 14));
                cbAdmins.setPreferredSize(new Dimension(250, 30));

                Object[] msg = {
                        "Bạn có muốn đăng ký mượn cuốn sách này không?\nSách sẽ được giữ cho bạn tại quầy thư viện.",
                        "Vui lòng chọn thủ thư tiếp nhận:", cbAdmins
                };

                int confirm = JOptionPane.showConfirmDialog(UserHomeFrame.this, msg,
                        "Xác nhận mượn sách", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (confirm != JOptionPane.OK_OPTION)
                    return;

                String selectedNQLDisplay = (String) cbAdmins.getSelectedItem();
                String maNQL = nqlMap.get(selectedNQLDisplay);

                BUS.PhieuMuonBUS pmBUS = new BUS.PhieuMuonBUS();
                BUS.ChiTietPhieuMuonBUS ctpmBUS = new BUS.ChiTietPhieuMuonBUS();
                BUS.TheThuVienBUS theThuVienBUS = new BUS.TheThuVienBUS();

                DTO.TheThuVienDTO theThuVien = theThuVienBUS.getByMaDocGia(maDocGiaDangNhap);
                if (theThuVien == null) {
                    JOptionPane.showMessageDialog(UserHomeFrame.this,
                            "Không tìm thấy thẻ thư viện của bạn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String maThe = theThuVien.getMaThe();

                String maPM = pmBUS.generateMaPM();
                String today = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());

                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.add(java.util.Calendar.DAY_OF_MONTH, 14);
                String henTra = new java.text.SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

                DTO.PhieuMuonDTO pm = new DTO.PhieuMuonDTO(
                        maPM, today, null, henTra, "Đang mượn", maNQL, maThe);

                String result = pmBUS.insert(pm);
                if (result.contains("thành công")) {
                    DTO.SachCopyDTO cuon = dsAvailable.get(0);
                    DTO.ChiTietPhieuMuonDTO ct = new DTO.ChiTietPhieuMuonDTO(
                            maPM, cuon.getMaVach(), "Tốt");
                    ctpmBUS.add(ct);

                    cuon.setTinhTrang("Đang mượn");
                    copyBUS.update(cuon);

                    JOptionPane.showMessageDialog(UserHomeFrame.this,
                            "Đăng ký mượn sách thành công!\n"
                                    + "Mã phiếu mượn: " + maPM + "\n"
                                    + "Hạn trả: " + henTra + "\n"
                                    + "Vui lòng đến quầy thư viện để nhận sách.",
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(UserHomeFrame.this,
                            "Đăng ký mượn thất bại: " + result, "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

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
            int confirm = JOptionPane.showConfirmDialog(UserHomeFrame.this,
                    "Bạn có muốn đăng xuất không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                BUS.SessionManager.getInstance().logout();
                new LoginFrame().setVisible(true);
                dispose();
            }
        });

        add(sidebarPanel, BorderLayout.WEST);
        add(mainContentPanel, BorderLayout.CENTER);

        panelTimSach.getBtnXemChiTiet().addActionListener(e -> {
            JTable t = panelTimSach.getTable();
            int row = t.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(UserHomeFrame.this, "Vui lòng chọn 1 sách trong bảng!");
            } else {
                panelChiTietSach.setThongTinSach(
                        t.getValueAt(row, 1).toString(), // Tên sách
                        t.getValueAt(row, 2).toString(), // Tác giả
                        t.getValueAt(row, 3).toString(), // Thể loại
                        t.getValueAt(row, 4).toString(), // NXB
                        "—",
                        t.getValueAt(row, 5).toString(),
                        "Mô tả chi tiết cho sách: " + t.getValueAt(row, 1));
                previousCard = "TimSach";
                cardLayout.show(mainContentPanel, "ChiTietSach");
            }
        });
    }

    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(new Color(248, 250, 252));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Thư Viện Online", SwingConstants.LEFT);
        title.setFont(new Font(tenFont, Font.ITALIC | Font.BOLD, 28));
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
                Image img = originalIcon.getImage().getScaledInstance(900, 150, Image.SCALE_SMOOTH);
                banner.setIcon(new ImageIcon(img));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        centerPanel.add(banner, BorderLayout.NORTH);

        JPanel booksArea = new JPanel(new BorderLayout(0, 15));
        booksArea.setBackground(new Color(248, 250, 252));

        JLabel subtitle = new JLabel("Sách Gợi Ý Cho Bạn");
        subtitle.setFont(new Font(tenFont, Font.BOLD, 20));
        subtitle.setForeground(new Color(30, 41, 59));
        booksArea.add(subtitle, BorderLayout.NORTH);

        JPanel booksList = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        booksList.setBackground(new Color(248, 250, 252));

        BUS.SachBUS sachBUS = new BUS.SachBUS();
        java.util.List<DTO.SachDTO> dsSach = sachBUS.getAll();

        if (dsSach != null && !dsSach.isEmpty()) {
            int count = 0;
            for (DTO.SachDTO sach : dsSach) {
                if (count >= 5)
                    break;
                booksList.add(createBookCard(sach));
                count++;
            }
        } else {
            JLabel lblEmpty = new JLabel("Hiện chưa có sách nào trong thư viện.");
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

    private JPanel createBookCard(DTO.SachDTO sach) {
        String title = sach.getTenSach() != null ? sach.getTenSach() : "Chưa cập nhật tên";
        String category = "Khác";
        if (sach.getTheLoai() != null) {
            BUS.TheLoaiBUS theLoaiBUS = new BUS.TheLoaiBUS();
            DTO.TheLoaiDTO theLoaiDTO = theLoaiBUS.getById(sach.getTheLoai());
            if (theLoaiDTO != null && theLoaiDTO.getTenTheLoai() != null) {
                category = theLoaiDTO.getTenTheLoai();
            } else {
                category = sach.getTheLoai();
            }
        }
        String namXB = String.valueOf(sach.getNamXB());

        BUS.SachCopyBUS copyBUS = new BUS.SachCopyBUS();
        java.util.List<DTO.SachCopyDTO> dsBanSao = copyBUS.getAvailable(sach.getMaSach());
        String tinhTrang = dsBanSao.isEmpty() ? "Hết sách" : "Còn " + dsBanSao.size() + " cuốn";

        JPanel card = new JPanel(new BorderLayout(0, 5));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(180, 250));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        JLabel cover = new JLabel("Hình Ảnh", SwingConstants.CENTER);
        cover.setOpaque(true);
        cover.setBackground(new Color(241, 245, 249));
        cover.setForeground(new Color(148, 163, 184));
        cover.setPreferredSize(new Dimension(100, 130));

        String hinhAnh = sach.getHinhAnh();
        if (hinhAnh != null && !hinhAnh.isEmpty()) {
            try {
                java.io.File imgFile = new java.io.File(hinhAnh);
                if (imgFile.exists()) {
                    ImageIcon icon = new ImageIcon(hinhAnh);
                    Image img = icon.getImage().getScaledInstance(170, 130, Image.SCALE_SMOOTH);
                    cover.setIcon(new ImageIcon(img));
                    cover.setText("");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        JPanel info = new JPanel(new GridLayout(3, 1, 0, 2));
        info.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel(" " + title);
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 14));
        lblTitle.setForeground(new Color(15, 23, 42));

        JLabel lblCategory = new JLabel(" Thể loại: " + category);
        lblCategory.setFont(new Font(tenFont, Font.PLAIN, 12));
        lblCategory.setForeground(new Color(100, 116, 139));

        JLabel lblYear = new JLabel(" Năm XB: " + namXB);
        lblYear.setFont(new Font(tenFont, Font.ITALIC, 12));
        lblYear.setForeground(new Color(100, 116, 139));

        info.add(lblTitle);
        info.add(lblCategory);
        info.add(lblYear);

        JButton btn = new JButton("Chi Tiết");
        btn.setFont(new Font(tenFont, Font.BOLD, 13));
        btn.setBackground(new Color(99, 102, 241));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(79, 70, 229));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(99, 102, 241));
            }
        });

        String finalTinhTrang = tinhTrang;
        String finalTheLoai = category;
        String finalHinhAnh = sach.getHinhAnh();
        btn.addActionListener(e -> {
            panelChiTietSach.setThongTinSach(
                    title,
                    "Nhiều tác giả",
                    finalTheLoai,
                    sach.getMaNXB(),
                    namXB,
                    finalTinhTrang,
                    "Mã sách: " + sach.getMaSach()
                            + "\nGiá bìa: " + sach.getGiaBia() + " VNĐ"
                            + "\nNgôn ngữ: " + sach.getNgonNgu(),
                    finalHinhAnh,
                    sach.getMaSach());
            cardLayout.show(mainContentPanel, "ChiTietSach");
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
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(51, 65, 85));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(30, 41, 59));
            }
        });
        return button;
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new UserHomeFrame("DG02").setVisible(true));
    }
}