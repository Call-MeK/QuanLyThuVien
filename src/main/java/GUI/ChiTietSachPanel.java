package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class ChiTietSachPanel extends JPanel {

    private JLabel lblHinhAnh;
    private JLabel lblTenSach, lblTacGia, lblTheLoai, lblNxb, lblNamXb, lblTinhTrang;
    private JTextArea txtMoTa;
    private JButton btnMuonSach, btnQuayLai;
    private String currentMaSach = "";

    private String tenFont = "Segoe UI";

    public ChiTietSachPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(248, 250, 252));
        setBorder(new EmptyBorder(25, 30, 25, 30));

        JLabel lblTitle = new JLabel("Chi Tiết Sách");
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 28));
        lblTitle.setForeground(new Color(15, 23, 42));
        add(lblTitle, BorderLayout.NORTH);

        JPanel mainContent = new JPanel(new BorderLayout(30, 0));
        mainContent.setBackground(new Color(248, 250, 252));

        JPanel panelImage = new JPanel(new BorderLayout());
        panelImage.setBackground(new Color(248, 250, 252));
        lblHinhAnh = new JLabel("BÌA SÁCH", SwingConstants.CENTER);
        lblHinhAnh.setOpaque(true);
        lblHinhAnh.setBackground(new Color(226, 232, 240));
        lblHinhAnh.setPreferredSize(new Dimension(250, 350));
        panelImage.add(lblHinhAnh, BorderLayout.NORTH);
        mainContent.add(panelImage, BorderLayout.WEST);

        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBackground(new Color(248, 250, 252));

        lblTenSach = createLabel("Tên sách: ---", 22, Font.BOLD, new Color(15, 23, 42));
        lblTacGia = createLabel("Tác giả: ---", 16, Font.PLAIN, Color.DARK_GRAY);
        lblTheLoai = createLabel("Thể loại: ---", 16, Font.PLAIN, Color.DARK_GRAY);
        lblNxb = createLabel("Nhà xuất bản: ---", 16, Font.PLAIN, Color.DARK_GRAY);
        lblNamXb = createLabel("Năm xuất bản: ---", 16, Font.PLAIN, Color.DARK_GRAY);
        lblTinhTrang = createLabel("Tình trạng: ---", 16, Font.BOLD, new Color(34, 197, 94));

        panelInfo.add(lblTenSach);
        panelInfo.add(Box.createVerticalStrut(15));
        panelInfo.add(lblTacGia);
        panelInfo.add(Box.createVerticalStrut(10));
        panelInfo.add(lblTheLoai);
        panelInfo.add(Box.createVerticalStrut(10));
        panelInfo.add(lblNxb);
        panelInfo.add(Box.createVerticalStrut(10));
        panelInfo.add(lblNamXb);
        panelInfo.add(Box.createVerticalStrut(10));
        panelInfo.add(lblTinhTrang);
        panelInfo.add(Box.createVerticalStrut(20));

        JLabel lblMoTaTitle = createLabel("Mô tả nội dung:", 16, Font.BOLD, Color.BLACK);
        panelInfo.add(lblMoTaTitle);
        panelInfo.add(Box.createVerticalStrut(5));

        txtMoTa = new JTextArea("Mô tả chi tiết cuốn sách sẽ hiển thị ở đây...");
        txtMoTa.setFont(new Font(tenFont, Font.PLAIN, 15));
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);
        txtMoTa.setEditable(false);
        txtMoTa.setBackground(new Color(241, 245, 249));
        txtMoTa.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollMoTa = new JScrollPane(txtMoTa);
        scrollMoTa.setPreferredSize(new Dimension(400, 100));
        scrollMoTa.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        panelInfo.add(scrollMoTa);

        mainContent.add(panelInfo, BorderLayout.CENTER);
        add(mainContent, BorderLayout.CENTER);

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panelButtons.setBackground(new Color(248, 250, 252));

        btnQuayLai = new JButton("Quay Lại");
        btnQuayLai.setFont(new Font(tenFont, Font.BOLD, 14));
        btnQuayLai.setBackground(new Color(100, 116, 139));
        btnQuayLai.setForeground(Color.WHITE);
        btnQuayLai.setFocusPainted(false);
        btnQuayLai.setBorderPainted(false);
        btnQuayLai.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnQuayLai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnQuayLai.setBackground(new Color(71, 85, 105));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnQuayLai.setBackground(new Color(100, 116, 139));
            }
        });
        btnMuonSach = new JButton("Đăng Ký Mượn Sách");
        btnMuonSach.setFont(new Font(tenFont, Font.BOLD, 14));
        btnMuonSach.setBackground(new Color(99, 102, 241));
        btnMuonSach.setForeground(Color.WHITE);
        btnMuonSach.setFocusPainted(false);
        btnMuonSach.setBorderPainted(false);
        btnMuonSach.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnMuonSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMuonSach.setBackground(new Color(79, 70, 229));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMuonSach.setBackground(new Color(99, 102, 241));
            }
        });

        panelButtons.add(btnQuayLai);
        panelButtons.add(btnMuonSach);
        add(panelButtons, BorderLayout.SOUTH);
    }

    private JLabel createLabel(String text, int size, int style, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(tenFont, style, size));
        label.setForeground(color);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    public void setThongTinSach(String tenSach, String tacGia, String theLoai, String nxb, String namXb,
            String tinhTrang, String moTa) {
        setThongTinSach(tenSach, tacGia, theLoai, nxb, namXb, tinhTrang, moTa, null, null);
    }

    public void setThongTinSach(String tenSach, String tacGia, String theLoai, String nxb, String namXb,
            String tinhTrang, String moTa, String hinhAnh) {
        setThongTinSach(tenSach, tacGia, theLoai, nxb, namXb, tinhTrang, moTa, hinhAnh, null);
    }

    public void setThongTinSach(String tenSach, String tacGia, String theLoai, String nxb, String namXb,
            String tinhTrang, String moTa, String hinhAnh, String maSach) {
        this.currentMaSach = maSach != null ? maSach : "";
        lblTenSach.setText(tenSach);
        lblTacGia.setText("Tác giả: " + tacGia);
        lblTheLoai.setText("Thể loại: " + theLoai);
        lblNxb.setText("Nhà xuất bản: " + nxb);
        lblNamXb.setText("Năm xuất bản: " + namXb);
        lblTinhTrang.setText("Tình trạng: " + tinhTrang);
        txtMoTa.setText(moTa);

        if (hinhAnh != null && !hinhAnh.isEmpty()) {
            try {
                File imgFile = new File(hinhAnh);
                if (imgFile.exists()) {
                    ImageIcon icon = new ImageIcon(hinhAnh);
                    Image img = icon.getImage().getScaledInstance(240, 340, Image.SCALE_SMOOTH);
                    lblHinhAnh.setIcon(new ImageIcon(img));
                    lblHinhAnh.setText("");
                } else {
                    lblHinhAnh.setIcon(null);
                    lblHinhAnh.setText("BÌA SÁCH");
                }
            } catch (Exception e) {
                lblHinhAnh.setIcon(null);
                lblHinhAnh.setText("BÌA SÁCH");
            }
        } else {
            lblHinhAnh.setIcon(null);
            lblHinhAnh.setText("BÌA SÁCH");
        }

        if (tinhTrang.equalsIgnoreCase("Hết sách")) {
            lblTinhTrang.setForeground(Color.RED);
            btnMuonSach.setEnabled(false);
        } else {
            lblTinhTrang.setForeground(new Color(34, 197, 94));
            btnMuonSach.setEnabled(true);
        }
    }

    public JButton getBtnQuayLai() {
        return btnQuayLai;
    }

    public JButton getBtnMuonSach() {
        return btnMuonSach;
    }

    public String getCurrentMaSach() {
        return currentMaSach;
    }
}