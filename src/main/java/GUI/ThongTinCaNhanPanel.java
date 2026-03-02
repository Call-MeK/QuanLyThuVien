import javax.swing.*;
import java.awt.*;

public class ThongTinCaNhanPanel extends JPanel {

    private JLabel lblMaThanhVien;
    private JLabel lblHoTen;
    private JLabel lblEmail;
    private JLabel lblSoDienThoai;
    private JLabel lblDiaChi;
    private JLabel lblTrangThai;
    private JLabel lblNgayDangKy;

    public ThongTinCaNhanPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 240, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 20, 12, 20);
        gbc.anchor = GridBagConstraints.WEST;

        // Font cho label tiêu đề và giá trị
        Font fontLabel = new Font("Arial", Font.BOLD, 14);
        Font fontValue = new Font("Arial", Font.PLAIN, 14);

        // Mã thành viên
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lbl1 = new JLabel("Mã thành viên:");
        lbl1.setFont(fontLabel);
        add(lbl1, gbc);

        gbc.gridx = 1;
        lblMaThanhVien = new JLabel("---");
        lblMaThanhVien.setFont(fontValue);
        add(lblMaThanhVien, gbc);

        // Họ tên
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lbl2 = new JLabel("Họ tên:");
        lbl2.setFont(fontLabel);
        add(lbl2, gbc);

        gbc.gridx = 1;
        lblHoTen = new JLabel("---");
        lblHoTen.setFont(fontValue);
        add(lblHoTen, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lbl3 = new JLabel("Email:");
        lbl3.setFont(fontLabel);
        add(lbl3, gbc);

        gbc.gridx = 1;
        lblEmail = new JLabel("---");
        lblEmail.setFont(fontValue);
        add(lblEmail, gbc);

        // Số điện thoại
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lbl4 = new JLabel("Số điện thoại:");
        lbl4.setFont(fontLabel);
        add(lbl4, gbc);

        gbc.gridx = 1;
        lblSoDienThoai = new JLabel("---");
        lblSoDienThoai.setFont(fontValue);
        add(lblSoDienThoai, gbc);

        // Địa chỉ
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel lbl5 = new JLabel("Địa chỉ:");
        lbl5.setFont(fontLabel);
        add(lbl5, gbc);

        gbc.gridx = 1;
        lblDiaChi = new JLabel("---");
        lblDiaChi.setFont(fontValue);
        add(lblDiaChi, gbc);

        // Trạng thái
        gbc.gridx = 0; gbc.gridy = 5;
        JLabel lbl6 = new JLabel("Trạng thái:");
        lbl6.setFont(fontLabel);
        add(lbl6, gbc);

        gbc.gridx = 1;
        lblTrangThai = new JLabel("---");
        lblTrangThai.setFont(fontValue);
        add(lblTrangThai, gbc);

        // Ngày đăng ký
        gbc.gridx = 0; gbc.gridy = 6;
        JLabel lbl7 = new JLabel("Ngày đăng ký:");
        lbl7.setFont(fontLabel);
        add(lbl7, gbc);

        gbc.gridx = 1;
        lblNgayDangKy = new JLabel("---");
        lblNgayDangKy.setFont(fontValue);
        add(lblNgayDangKy, gbc);
    }

    // ===== GETTER =====
    public JLabel getLblMaThanhVien() { return lblMaThanhVien; }
    public JLabel getLblHoTen() { return lblHoTen; }
    public JLabel getLblEmail() { return lblEmail; }
    public JLabel getLblSoDienThoai() { return lblSoDienThoai; }
    public JLabel getLblDiaChi() { return lblDiaChi; }
    public JLabel getLblTrangThai() { return lblTrangThai; }
    public JLabel getLblNgayDangKy() { return lblNgayDangKy; }

    // ===== SETTER để cập nhật thông tin =====
    public void setThongTin(String maTV, String hoTen, String email,
                            String sdt, String diaChi, String trangThai, String ngayDK) {
        lblMaThanhVien.setText(maTV);
        lblHoTen.setText(hoTen);
        lblEmail.setText(email);
        lblSoDienThoai.setText(sdt);
        lblDiaChi.setText(diaChi);
        lblTrangThai.setText(trangThai);
        lblNgayDangKy.setText(ngayDK);
    }
}