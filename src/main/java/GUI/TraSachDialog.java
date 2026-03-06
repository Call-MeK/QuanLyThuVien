package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import DAO.*;
import DTO.*;

public class TraSachDialog extends JDialog {

    private static final String[] TINH_TRANG = {"Tốt", "Bình thường", "Cũ", "Hỏng"};
    private static final String   tenFont    = "Segoe UI";

    private JTable            table;
    private DefaultTableModel model;
    private JLabel            lblEmptyHint;
    private JButton           btnXacNhan;
    private boolean           confirmed = false;

    private final List<String[]>      ketQua  = new ArrayList<>();
    private final String              maPM;
    private final ChiTietPhieuMuonDAO ctpmDAO = new ChiTietPhieuMuonDAO();
    private final SachCopyDAO         copyDAO = new SachCopyDAO();
    private final SachHongDAO         hongDAO = new SachHongDAO();

    public TraSachDialog(Frame parent, String maPM) {
        super(parent, "Xác Nhận Trả Sách — Phiếu: " + maPM, true);
        this.maPM = maPM;
        buildUI();
        loadData();
        pack();
        setMinimumSize(new Dimension(700, 380));
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
        JLabel lblSub = new JLabel("Hệ thống tự động cập nhật — cuốn nào Hỏng sẽ được ghi nhận ngay.");
        lblSub.setFont(new Font(tenFont, Font.ITALIC, 13));
        lblSub.setForeground(new Color(108, 117, 125));
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

        // ComboBox cho cột tình trạng trả về
        table.getColumnModel().getColumn(2).setCellEditor(
                new DefaultCellEditor(new JComboBox<>(TINH_TRANG)));
        table.getColumnModel().getColumn(2).setCellRenderer(new TinhTrangRenderer());

        // Cột T.Trạng lúc mượn: xám, readonly
        table.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            { setForeground(new Color(108, 117, 125)); }
        });

        table.getColumnModel().getColumn(0).setPreferredWidth(230);
        table.getColumnModel().getColumn(1).setPreferredWidth(130);
        table.getColumnModel().getColumn(2).setPreferredWidth(130);
        table.getColumnModel().getColumn(3).setPreferredWidth(190);

        // Wrapper để có thể hiện thông báo khi trống
        JPanel pnlTable = new JPanel(new BorderLayout());
        pnlTable.add(table.getTableHeader(), BorderLayout.NORTH);
        pnlTable.add(table, BorderLayout.CENTER);

        lblEmptyHint = new JLabel(
                "⚠ Không tìm thấy sách trong phiếu này. Có thể phiếu chưa có chi tiết mượn.",
                SwingConstants.CENTER);
        lblEmptyHint.setFont(new Font(tenFont, Font.ITALIC, 13));
        lblEmptyHint.setForeground(new Color(255, 153, 0));
        lblEmptyHint.setVisible(false);

        JPanel pnlCenter = new JPanel(new BorderLayout(0, 5));
        pnlCenter.add(new JScrollPane(table), BorderLayout.CENTER);
        pnlCenter.add(lblEmptyHint, BorderLayout.SOUTH);
        add(pnlCenter, BorderLayout.CENTER);

        // Footer
        JLabel lblHint = new JLabel("* Cuốn Hỏng sẽ tự động được ghi nhận vào hệ thống");
        lblHint.setFont(new Font(tenFont, Font.ITALIC, 12));
        lblHint.setForeground(new Color(220, 53, 69));

        btnXacNhan = btn("✔  Xác Nhận Trả", new Color(25, 135, 84));
        JButton btnHuy = btn("✖  Hủy", new Color(108, 117, 125));
        btnXacNhan.addActionListener(e -> xacNhan());
        btnHuy.addActionListener(e -> dispose());

        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlBtn.setOpaque(false);
        pnlBtn.add(btnHuy);
        pnlBtn.add(btnXacNhan);

        JPanel south = new JPanel(new BorderLayout());
        south.setOpaque(false);
        south.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        south.add(lblHint, BorderLayout.WEST);
        south.add(pnlBtn,  BorderLayout.EAST);
        add(south, BorderLayout.SOUTH);
    }

    private void loadData() {
        model.setRowCount(0);
        ketQua.clear();

        List<ChiTietPhieuMuonDTO> list = ctpmDAO.getByMaPM(maPM);

        System.out.println("[TraSachDialog] MaPM=" + maPM + " → " + list.size() + " cuốn");

        if (list.isEmpty()) {
            // Không có chi tiết → cho phép xác nhận trả luôn (chỉ cập nhật PHIEUMUON)
            lblEmptyHint.setVisible(true);
            btnXacNhan.setText("✔  Xác Nhận Trả (Không có chi tiết)");
            return;
        }

        for (ChiTietPhieuMuonDTO ct : list) {
            SachCopyDTO sc = copyDAO.findById(ct.getMaCuonSach());
            String ten = sc != null ? sc.getTenSachBanSao() : ct.getMaCuonSach();
            String tt  = sc != null && sc.getTinhTrang() != null ? sc.getTinhTrang() : "Tốt";
            model.addRow(new Object[]{ ten, tt, tt, "" });
            ketQua.add(new String[]{ ct.getMaCuonSach(), ten, tt, "" });
            System.out.println("[TraSachDialog]   cuốn: " + ct.getMaCuonSach() + " → " + ten + " / " + tt);
        }
    }

    private void xacNhan() {
        if (table.isEditing()) table.getCellEditor().stopCellEditing();

        boolean coHong = false;
        for (int i = 0; i < model.getRowCount(); i++) {
            String ttTra  = model.getValueAt(i, 2).toString();
            String ghiChu = model.getValueAt(i, 3) != null ? model.getValueAt(i, 3).toString() : "";
            ketQua.get(i)[2] = ttTra;
            ketQua.get(i)[3] = ghiChu;
            copyDAO.updateTinhTrang(ketQua.get(i)[0], ttTra, ghiChu);
            if (ttTra.equals("Hỏng")) coHong = true;
        }

        confirmed = true;

        if (coHong) {
            String ngayNay = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
            StringBuilder ds = new StringBuilder("⚠ Sách hỏng đã được ghi nhận:\n");
            for (String[] r : ketQua) {
                if (r[2].equals("Hỏng")) {
                    SachHongDTO sh = new SachHongDTO();
                    sh.setTenSachHong(r[1]);
                    sh.setMaVach(r[0]);
                    sh.setSoLuong(1);
                    sh.setNgayGhiNhan(ngayNay);
                    sh.setLyDo(r[3].isEmpty()
                            ? "Phát hiện khi trả sách — Phiếu: " + maPM
                            : r[3]);
                    hongDAO.insert(sh);
                    ds.append("  • ").append(r[1]).append("\n");
                }
            }
            ds.append("\nVào Quản Lý Phí Phạt để lập phiếu phạt nếu cần.");
            JOptionPane.showMessageDialog(this, ds.toString(),
                    "Ghi Nhận Sách Hỏng", JOptionPane.WARNING_MESSAGE);
        }

        dispose();
    }

    public boolean isConfirmed() { return confirmed; }
    public boolean hasHong()     { return ketQua.stream().anyMatch(r -> r[2].equals("Hỏng")); }

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