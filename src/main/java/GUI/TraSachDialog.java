package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import BUS.*;
import DAO.*;
import DTO.*;

public class TraSachDialog extends JDialog {

    // ===================== HẰNG SỐ PHÍ PHẠT =====================
    private static final double PHI_TRE_HAN_MOI_NGAY = 5_000;   // 5,000đ/ngày trễ
    private static final double PHI_SACH_HONG         = 50_000;  // 50,000đ/cuốn hỏng

    private static final String[] TINH_TRANG = {"Tốt", "Bình thường", "Cũ", "Hỏng"};
    private static final String   tenFont    = "Segoe UI";

    private JTable            table;
    private DefaultTableModel model;
    private JLabel            lblEmptyHint;
    private JLabel            lblTongPhat;   // Hiển thị tổng tiền phạt preview
    private JButton           btnXacNhan;
    private boolean           confirmed = false;

    private final List<String[]>      ketQua  = new ArrayList<>();
    private final String              maPM;
    private final String              hanTra;  // Lấy từ PhieuMuon để tính trễ hạn
    private final ChiTietPhieuMuonDAO ctpmDAO = new ChiTietPhieuMuonDAO();
    private final SachCopyDAO         copyDAO = new SachCopyDAO();
    private final SachHongDAO         hongDAO = new SachHongDAO();
    private final PhieuPhatBUS        phatBUS = new PhieuPhatBUS();

    public TraSachDialog(Frame parent, String maPM) {
        super(parent, "Xác Nhận Trả Sách — Phiếu: " + maPM, true);
        this.maPM    = maPM;
        // Lấy hạn trả từ DB
        PhieuMuonBUS pmBUS = new PhieuMuonBUS();
        PhieuMuonDTO pm = pmBUS.getById(maPM);
        this.hanTra = (pm != null && pm.getHenTra() != null) ? pm.getHenTra() : "";
        buildUI();
        loadData();
        pack();
        setMinimumSize(new Dimension(750, 450));
        setLocationRelativeTo(parent);
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        getRootPane().setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        // Header
        JPanel header = new JPanel(new GridLayout(2, 1, 0, 3));
        header.setOpaque(false);
        JLabel lblTitle = new JLabel("Chọn tình trạng thực tế cho từng cuốn sách");
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 16));

        // Hiển thị hạn trả và ngày hôm nay
        long soNgayTre = tinhSoNgayTre();
        String subText = "Hạn trả: " + (hanTra.isEmpty() ? "—" : hanTra);
        if (soNgayTre > 0) {
            subText += "   ⚠ Trễ " + soNgayTre + " ngày → Phí: "
                    + String.format("%,.0f đ", soNgayTre * PHI_TRE_HAN_MOI_NGAY);
        }
        JLabel lblSub = new JLabel(subText);
        lblSub.setFont(new Font(tenFont, Font.ITALIC, 13));
        lblSub.setForeground(soNgayTre > 0 ? new Color(183, 28, 28) : new Color(108, 117, 125));
        header.add(lblTitle);
        header.add(lblSub);
        add(header, BorderLayout.NORTH);

        // Bảng
        model = new DefaultTableModel(
                new String[]{"Tên Sách Bản Sao", "T.Trạng Lúc Mượn", "T.Trạng Trả Về", "Ghi Chú (nếu có)"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return c == 2 || c == 3; }
        };
        table = new JTable(model);
        table.setRowHeight(36);
        table.setFont(new Font(tenFont, Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font(tenFont, Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(233, 236, 239));
        table.setSelectionBackground(new Color(37, 99, 235));
        table.setSelectionForeground(Color.WHITE);

        // ComboBox tình trạng trả về
        JComboBox<String> cbTinhTrang = new JComboBox<>(TINH_TRANG);
        table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(cbTinhTrang) {
            @Override
            public boolean stopCellEditing() {
                boolean result = super.stopCellEditing();
                capNhatTongPhat(); // Cập nhật tổng tiền phạt ngay khi đổi tình trạng
                return result;
            }
        });
        table.getColumnModel().getColumn(2).setCellRenderer(new TinhTrangRenderer());
        table.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            { setForeground(new Color(108, 117, 125)); }
        });
        table.getColumnModel().getColumn(0).setPreferredWidth(230);
        table.getColumnModel().getColumn(1).setPreferredWidth(130);
        table.getColumnModel().getColumn(2).setPreferredWidth(130);
        table.getColumnModel().getColumn(3).setPreferredWidth(200);

        lblEmptyHint = new JLabel(
                "⚠ Không tìm thấy sách trong phiếu này.",
                SwingConstants.CENTER);
        lblEmptyHint.setFont(new Font(tenFont, Font.ITALIC, 13));
        lblEmptyHint.setForeground(new Color(255, 153, 0));
        lblEmptyHint.setVisible(false);

        JPanel pnlCenter = new JPanel(new BorderLayout(0, 5));
        pnlCenter.add(new JScrollPane(table), BorderLayout.CENTER);
        pnlCenter.add(lblEmptyHint, BorderLayout.SOUTH);
        add(pnlCenter, BorderLayout.CENTER);

        // Footer
        JPanel south = new JPanel(new BorderLayout(10, 0));
        south.setOpaque(false);
        south.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        // Tổng tiền phạt preview
        lblTongPhat = new JLabel("Tổng phí phạt dự kiến: 0 đ");
        lblTongPhat.setFont(new Font(tenFont, Font.BOLD, 14));
        lblTongPhat.setForeground(new Color(220, 53, 69));

        btnXacNhan = btn("✔  Xác Nhận Trả", new Color(25, 135, 84));
        JButton btnHuy = btn("✖  Hủy", new Color(108, 117, 125));
        btnXacNhan.addActionListener(e -> xacNhan());
        btnHuy.addActionListener(e -> dispose());

        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlBtn.setOpaque(false);
        pnlBtn.add(btnHuy);
        pnlBtn.add(btnXacNhan);

        south.add(lblTongPhat, BorderLayout.WEST);
        south.add(pnlBtn,      BorderLayout.EAST);
        add(south, BorderLayout.SOUTH);
    }

    private void loadData() {
        model.setRowCount(0);
        ketQua.clear();

        List<ChiTietPhieuMuonDTO> list = ctpmDAO.getByMaPM(maPM);
        if (list.isEmpty()) {
            lblEmptyHint.setVisible(true);
            btnXacNhan.setText("✔  Xác Nhận Trả (Không có chi tiết)");
            capNhatTongPhat();
            return;
        }

        for (ChiTietPhieuMuonDTO ct : list) {
            SachCopyDTO sc = copyDAO.findById(ct.getMaCuonSach());
            String ten = sc != null ? sc.getTenSachBanSao() : ct.getMaCuonSach();
            String tt  = sc != null && sc.getTinhTrang() != null ? sc.getTinhTrang() : "Tốt";
            model.addRow(new Object[]{ ten, tt, tt, "" });
            ketQua.add(new String[]{ ct.getMaCuonSach(), ten, tt, "" });
        }
        capNhatTongPhat();
    }

    // Tính tổng phí phạt dự kiến và hiển thị lên lblTongPhat
    private void capNhatTongPhat() {
        double tong = 0;
        long soNgayTre = tinhSoNgayTre();
        if (soNgayTre > 0) tong += soNgayTre * PHI_TRE_HAN_MOI_NGAY;

        for (int i = 0; i < model.getRowCount(); i++) {
            String tt = model.getValueAt(i, 2) != null ? model.getValueAt(i, 2).toString() : "Tốt";
            if ("Hỏng".equals(tt)) tong += PHI_SACH_HONG;
        }

        if (tong > 0) {
            lblTongPhat.setText("Tổng phí phạt dự kiến: " + String.format("%,.0f đ", tong));
            lblTongPhat.setForeground(new Color(220, 53, 69));
        } else {
            lblTongPhat.setText("Không có phí phạt");
            lblTongPhat.setForeground(new Color(25, 135, 84));
        }
    }

    private void xacNhan() {
        if (table.isEditing()) table.getCellEditor().stopCellEditing();

        // Bước 1: Cập nhật tình trạng từng cuốn và ghi SACHHONG nếu hỏng
        boolean coHong = false;
        String ngayNay = LocalDate.now().toString();
        StringBuilder dsSachHong = new StringBuilder();

        for (int i = 0; i < model.getRowCount(); i++) {
            String ttTra  = model.getValueAt(i, 2).toString();
            String ghiChu = model.getValueAt(i, 3) != null ? model.getValueAt(i, 3).toString() : "";
            ketQua.get(i)[2] = ttTra;
            ketQua.get(i)[3] = ghiChu;

            // Cập nhật TinhTrang bản sao trong SACHCOPY
            copyDAO.updateTinhTrang(ketQua.get(i)[0], ttTra, ghiChu);

            if ("Hỏng".equals(ttTra)) {
                coHong = true;
                // Ghi vào SACHHONG
                SachHongDTO sh = new SachHongDTO();
                sh.setTenSachHong(ketQua.get(i)[1]);
                sh.setMaVach(ketQua.get(i)[0]);
                sh.setSoLuong(1);
                sh.setNgayGhiNhan(ngayNay);
                sh.setLyDo(ghiChu.isEmpty()
                        ? "Phát hiện khi trả sách — Phiếu: " + maPM
                        : ghiChu);
                hongDAO.insert(sh);
                dsSachHong.append("  • ").append(ketQua.get(i)[1]).append("\n");
            }
        }

        // Bước 2: Tự động tạo phiếu phạt nếu có phí
        long soNgayTre = tinhSoNgayTre();
        double tongPhi = 0;
        ArrayList<ChiTietPhieuPhatDTO> dsChiTiet = new ArrayList<>();
        String maNQL = BUS.SessionManager.getInstance().getMaNguoi();
        if (maNQL == null || maNQL.isEmpty()) maNQL = "NV00000001";

        // Chi tiết: trễ hạn
        if (soNgayTre > 0) {
            double phiTre = soNgayTre * PHI_TRE_HAN_MOI_NGAY;
            tongPhi += phiTre;
            ChiTietPhieuPhatDTO ct = new ChiTietPhieuPhatDTO();
            ct.setMaCTPP("CT" + System.currentTimeMillis() % 100000000);
            ct.setMaPP(""); // Sẽ set sau khi có maPP
            ct.setMaCuonSach(ketQua.isEmpty() ? "MV000000000000000001" : ketQua.get(0)[0]);
            ct.setLyDo("Trả sách trễ " + soNgayTre + " ngày (hạn: " + hanTra + ")");
            ct.setSoTien(String.valueOf(phiTre));
            dsChiTiet.add(ct);
        }

        // Chi tiết: sách hỏng
        for (String[] r : ketQua) {
            if ("Hỏng".equals(r[2])) {
                tongPhi += PHI_SACH_HONG;
                ChiTietPhieuPhatDTO ct = new ChiTietPhieuPhatDTO();
                ct.setMaCTPP("CT" + System.currentTimeMillis() % 100000000 + dsChiTiet.size());
                ct.setMaPP("");
                ct.setMaCuonSach(r[0]);
                ct.setLyDo("Làm hỏng sách: " + r[1] + (r[3].isEmpty() ? "" : " — " + r[3]));
                ct.setSoTien(String.valueOf(PHI_SACH_HONG));
                dsChiTiet.add(ct);
            }
        }

        // Tạo phiếu phạt nếu có phí
        if (tongPhi > 0 && !dsChiTiet.isEmpty()) {
            String maPP = phatBUS.generateMaPP();
            PhieuPhatDTO pp = new PhieuPhatDTO();
            pp.setMaPP(maPP);
            pp.setMaPM(maPM);
            pp.setMaNQL(maNQL);
            pp.setNgayLap(ngayNay);
            pp.setTongTien(String.valueOf(tongPhi));
            pp.setTrangThai(0); // Chưa thanh toán

            // Gán maPP cho từng chi tiết
            for (ChiTietPhieuPhatDTO ct : dsChiTiet) ct.setMaPP(maPP);

            String resultPhat = phatBUS.taoPhieuPhat(pp, dsChiTiet);

            // Thông báo kết quả
            StringBuilder msg = new StringBuilder("✔ Xác nhận trả thành công!\n\n");
            if (soNgayTre > 0) {
                msg.append("⏰ Trễ ").append(soNgayTre).append(" ngày — Phí: ")
                   .append(String.format("%,.0f đ\n", soNgayTre * PHI_TRE_HAN_MOI_NGAY));
            }
            if (coHong) {
                msg.append("📕 Sách hỏng:\n").append(dsSachHong)
                   .append("Phí: ").append(String.format("%,.0f đ\n", (double) ketQua.stream()
                       .filter(r -> "Hỏng".equals(r[2])).count() * PHI_SACH_HONG));
            }
            msg.append("\n💰 Tổng phí phạt: ").append(String.format("%,.0f đ", tongPhi))
               .append("\n→ Mã phiếu phạt: ").append(maPP)
               .append("\n→ Vào Quản Lý Phí Phạt để thu tiền.");

            JOptionPane.showMessageDialog(this, msg.toString(),
                    "Tạo Phiếu Phạt Thành Công", JOptionPane.WARNING_MESSAGE);

        } else if (coHong) {
            // Hỏng nhưng không tính phí (trường hợp hiếm)
            JOptionPane.showMessageDialog(this,
                    "✔ Trả thành công!\n⚠ Sách hỏng đã ghi nhận:\n" + dsSachHong,
                    "Ghi Nhận Sách Hỏng", JOptionPane.WARNING_MESSAGE);
        }
        // Nếu không có phí thì không hiện popup, trả lặng lẽ

        confirmed = true;
        dispose();
    }

    // Tính số ngày trễ (0 nếu không trễ)
    private long tinhSoNgayTre() {
        if (hanTra == null || hanTra.isEmpty()) return 0;
        try {
            LocalDate han  = LocalDate.parse(hanTra);
            LocalDate hom  = LocalDate.now();
            long ngayTre   = ChronoUnit.DAYS.between(han, hom);
            return ngayTre > 0 ? ngayTre : 0;
        } catch (Exception e) { return 0; }
    }

    public boolean isConfirmed() { return confirmed; }
    public boolean hasHong()     { return ketQua.stream().anyMatch(r -> "Hỏng".equals(r[2])); }

    private JButton btn(String text, Color bg) {
        JButton b = new JButton(text);
        b.setFont(new Font(tenFont, Font.BOLD, 14));
        b.setBackground(bg); b.setForeground(Color.WHITE);
        b.setFocusPainted(false); b.setBorderPainted(false);
        b.setPreferredSize(new Dimension(220, 38));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    static class TinhTrangRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable t, Object val,
                boolean sel, boolean focus, int row, int col) {
            super.getTableCellRendererComponent(t, val, sel, focus, row, col);
            setFont(new Font(tenFont, Font.BOLD, 14));
            if (!sel) {
                setBackground(Color.WHITE);
                switch (val != null ? val.toString() : "") {
                    case "Tốt":         setForeground(new Color(25, 135, 84));  break;
                    case "Bình thường": setForeground(new Color(13, 110, 253)); break;
                    case "Cũ":          setForeground(new Color(255, 153, 0));  break;
                    case "Hỏng":        setForeground(new Color(220, 53, 69));  break;
                    default:            setForeground(new Color(33, 37, 41));
                }
            }
            return this;
        }
    }
}