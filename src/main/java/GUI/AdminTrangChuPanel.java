package GUI;

import javax.swing.*;
import java.awt.*;
import BUS.*;

public class AdminTrangChuPanel extends JPanel {

    private String tenFont = "Segoe UI";
    private Color colorBackground = new Color(248, 249, 250);

    public AdminTrangChuPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(20, 20));
        setBackground(colorBackground);
        setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JPanel pnlTop = new JPanel(new GridLayout(2, 1, 0, 5));
        pnlTop.setBackground(colorBackground);

        JLabel lblWelcome = new JLabel("Chào mừng bạn quay trở lại");
        lblWelcome.setFont(new Font(tenFont, Font.BOLD, 26));
        lblWelcome.setForeground(new Color(33, 37, 41));

        JLabel lblSub = new JLabel("Dưới đây là tổng quan tình hình thư viện hôm nay.");
        lblSub.setFont(new Font(tenFont, Font.PLAIN, 16));
        lblSub.setForeground(new Color(108, 117, 125));

        pnlTop.add(lblWelcome);
        pnlTop.add(lblSub);
        add(pnlTop, BorderLayout.NORTH);

        JPanel pnlCenter = new JPanel(new BorderLayout(30, 0));
        pnlCenter.setBackground(colorBackground);

        JPanel pnlLeft = new JPanel(new GridLayout(4, 1, 0, 20));
        pnlLeft.setBackground(colorBackground);
        pnlLeft.setPreferredSize(new Dimension(240, 0));

        int tongSach = 0, tongDocGia = 0, tongPhieuMuon = 0, tongPhieuPhat = 0;
        try {
            tongSach = new SachBUS().getAll().size();
            tongDocGia = new DocGiaBUS().getAll().size();
            tongPhieuMuon = new PhieuMuonBUS().count();
            tongPhieuPhat = new PhieuPhatBUS().getAll().size();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        pnlLeft.add(createStatCard("TỔNG SỐ SÁCH", String.valueOf(tongSach), "Cuốn", new Color(13, 110, 253)));
        pnlLeft.add(createStatCard("TỔNG ĐỘC GIẢ", String.valueOf(tongDocGia), "Người", new Color(25, 135, 84)));
        pnlLeft.add(createStatCard("PHIẾU MƯỢN", String.valueOf(tongPhieuMuon), "Phiếu", new Color(255, 193, 7)));
        pnlLeft.add(createStatCard("PHIẾU PHẠT", String.valueOf(tongPhieuPhat), "Phiếu", new Color(220, 53, 69)));

        pnlCenter.add(pnlLeft, BorderLayout.WEST);

        JPanel pnlRight = new JPanel(new BorderLayout(0, 20));
        pnlRight.setBackground(colorBackground);

        JLabel lblImage = new JLabel();
        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        lblImage.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1));
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/Images/lib.jpg"));
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
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JLabel lblRecentTitle = new JLabel("Hoạt Động Gần Đây Nhất");
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
        add(pnlCenter, BorderLayout.CENTER);

        JLabel lblFooter = new JLabel("Hệ thống cập nhật lần cuối: Vừa xong", SwingConstants.RIGHT);
        lblFooter.setFont(new Font(tenFont, Font.ITALIC, 14));
        lblFooter.setForeground(Color.GRAY);
        add(lblFooter, BorderLayout.SOUTH);
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
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 14));
        lblTitle.setForeground(new Color(108, 117, 125));

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
}