package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.List;
import BUS.*;
import DAO.*;
import DTO.*;

public class AdminQuanLyNhapSachPanel extends JPanel {

    private final String tenFont       = "Segoe UI";
    private final Color  clrBackground = new Color(248, 249, 250);
    private final Color  clrWhite      = Color.WHITE;
    private final Color  clrBorder     = new Color(222, 226, 230);

    private JTextField txtMaPN, txtNgayNhap;
    private JComboBox<String> cbNXB;
    private JLabel lblTongTien;

    private JTextField        txtMaSachNhap, txtTenSach, txtSoLuong, txtDonGia;
    private JButton           btnThemDong, btnCapNhatDong, btnXoaDong;
    private JTable            tblChiTiet;
    private DefaultTableModel modelChiTiet;

    private JTextField        txtSearch;
    private JTable            tblLichSu;
    private DefaultTableModel modelLichSu;

    private JButton btnLuu, btnHuyPhieu, btnLamMoi;

    // ===== CALLBACK để notify AdminQuanLySachPanel tự refresh số cuốn =====
    private Runnable onSaveCallback;

    public void setOnSaveCallback(Runnable callback) {
        this.onSaveCallback = callback;
    }

    private final PhieuNhapBUS        pnBUS   = new PhieuNhapBUS();
    private final ChiTietPhieuNhapBUS ctBUS   = new ChiTietPhieuNhapBUS();
    private final SachDAO             sachDAO = new SachDAO();
    private final NhaXuatBanBUS       nxbBUS  = new NhaXuatBanBUS();
    private List<NhaXuatBanDTO>       listNXB;

    public AdminQuanLyNhapSachPanel() {
        buildUI();
        initEvents();
        lamMoiPhieu();
        loadLichSu();

        // Tự reload lịch sử mỗi khi panel được hiển thị (switch tab)
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                loadLichSu();
            }
        });
    }

    // ==========================================================
    // BUILD UI
    // ==========================================================
    private void buildUI() {
        setLayout(new BorderLayout(0, 10));
        setBackground(clrBackground);
        setBorder(BorderFactory.createEmptyBorder(20, 25, 15, 25));

        JLabel lbl = new JLabel("Quản Lý Nhập Sách");
        lbl.setFont(new Font(tenFont, Font.BOLD, 24));
        lbl.setForeground(new Color(33, 37, 41));
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        add(lbl, BorderLayout.NORTH);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                buildPanelSoanPhieu(), buildPanelLichSu());
        split.setDividerLocation(380);
        split.setDividerSize(6);
        split.setResizeWeight(0.5);
        split.setBorder(null);
        split.setBackground(clrBackground);
        add(split, BorderLayout.CENTER);

        // ===== THANH NÚT DƯỚI CÙNG =====
        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        pnlBtn.setBackground(clrBackground);

        btnThemDong    = btn("➕  Thêm Dòng",     new Color(13, 110, 253));
        btnCapNhatDong = btn("✏  Cập Nhật Dòng",  new Color(255, 193, 7));
        btnCapNhatDong.setForeground(Color.BLACK);
        btnXoaDong     = btn("🗑  Xóa Dòng",       new Color(220, 53, 69));
        btnHuyPhieu    = btn("✖  Hủy Phiếu",      new Color(153, 51, 204));
        btnLamMoi      = btn("↺  Làm Mới",         new Color(108, 117, 125));
        btnLuu         = btn("💾  Lưu Phiếu",      new Color(25, 135, 84));

        pnlBtn.add(btnThemDong);
        pnlBtn.add(btnCapNhatDong);
        pnlBtn.add(btnXoaDong);
        pnlBtn.add(btnHuyPhieu);
        pnlBtn.add(btnLamMoi);
        pnlBtn.add(btnLuu);

        add(pnlBtn, BorderLayout.SOUTH);
    }

    private JPanel buildPanelSoanPhieu() {
        JPanel p = new JPanel(new BorderLayout(0, 8));
        p.setBackground(clrBackground);

        JPanel pnlHeader = card();
        pnlHeader.setLayout(new GridLayout(2, 6, 12, 8));
        Font fl = new Font(tenFont, Font.BOLD, 13);
        Font fi = new Font(tenFont, Font.PLAIN, 13);

        txtMaPN     = roField(fi);
        txtNgayNhap = new JTextField(fi.getSize()); txtNgayNhap.setFont(fi);

        cbNXB = new JComboBox<>(); cbNXB.setFont(fi);
        loadComboBoxNXB();

        lblTongTien = new JLabel("0 đ");
        lblTongTien.setFont(new Font(tenFont, Font.BOLD, 15));
        lblTongTien.setForeground(new Color(25, 135, 84));

        pnlHeader.add(lbl("Mã phiếu nhập:", fl)); pnlHeader.add(txtMaPN);
        pnlHeader.add(lbl("Ngày nhập:", fl));       pnlHeader.add(txtNgayNhap);
        pnlHeader.add(lbl("Nhà cung cấp:", fl));    pnlHeader.add(cbNXB);
        pnlHeader.add(lbl("Tổng tiền:", fl));       pnlHeader.add(lblTongTien);
        pnlHeader.add(new JLabel()); pnlHeader.add(new JLabel());
        pnlHeader.add(new JLabel()); pnlHeader.add(new JLabel());

        JPanel pnlAddRow = card();
        pnlAddRow.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

        txtMaSachNhap = tf(fi, 12);
        txtMaSachNhap.setToolTipText("Nhập Mã Sách rồi Enter để tự tìm tên");
        txtTenSach = roField(fi); txtTenSach.setPreferredSize(new Dimension(200, 32));
        txtSoLuong = tf(fi, 6);  txtSoLuong.setText("1");
        txtDonGia  = tf(fi, 10); txtDonGia.setText("0");

        pnlAddRow.add(lbl("Mã sách:", fl));        pnlAddRow.add(txtMaSachNhap);
        pnlAddRow.add(lbl("Tên sách:", fl));        pnlAddRow.add(txtTenSach);
        pnlAddRow.add(lbl("Số lượng:", fl));        pnlAddRow.add(txtSoLuong);
        pnlAddRow.add(lbl("Đơn giá 1 cuốn:", fl)); pnlAddRow.add(txtDonGia);

        String[] cols = {"Mã Sách", "Tên Sách", "Số Lượng", "Đơn Giá (đ)", "Thành Tiền (đ)"};
        modelChiTiet = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblChiTiet = new JTable(modelChiTiet);
        stylizeTable(tblChiTiet);
        tblChiTiet.getColumnModel().getColumn(0).setPreferredWidth(110);
        tblChiTiet.getColumnModel().getColumn(1).setPreferredWidth(220);
        tblChiTiet.getColumnModel().getColumn(2).setPreferredWidth(80);
        tblChiTiet.getColumnModel().getColumn(3).setPreferredWidth(110);
        tblChiTiet.getColumnModel().getColumn(4).setPreferredWidth(130);

        DefaultTableCellRenderer right = new DefaultTableCellRenderer();
        right.setHorizontalAlignment(SwingConstants.RIGHT);
        tblChiTiet.getColumnModel().getColumn(2).setCellRenderer(right);
        tblChiTiet.getColumnModel().getColumn(3).setCellRenderer(right);
        tblChiTiet.getColumnModel().getColumn(4).setCellRenderer(right);

        JPanel pnlTop = new JPanel(new BorderLayout(0, 6));
        pnlTop.setBackground(clrBackground);
        pnlTop.add(pnlHeader, BorderLayout.NORTH);
        pnlTop.add(pnlAddRow, BorderLayout.CENTER);
        p.add(pnlTop, BorderLayout.NORTH);
        p.add(new JScrollPane(tblChiTiet), BorderLayout.CENTER);
        return p;
    }

    private JPanel buildPanelLichSu() {
        JPanel p = new JPanel(new BorderLayout(0, 8));
        p.setBackground(clrBackground);
        p.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));

        JPanel top = new JPanel(new BorderLayout(10, 0));
        top.setBackground(clrBackground);
        JLabel t = new JLabel("📋  Lịch Sử Phiếu Nhập");
        t.setFont(new Font(tenFont, Font.BOLD, 15));
        txtSearch = new JTextField();
        txtSearch.setFont(new Font(tenFont, Font.PLAIN, 13));
        txtSearch.setPreferredSize(new Dimension(220, 30));
        txtSearch.setToolTipText("Tìm theo Mã Phiếu hoặc Nhà Cung Cấp");
        JButton btnTim = smallBtn("🔍 Tìm", new Color(13, 110, 253));
        JPanel pRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        pRight.setBackground(clrBackground);
        pRight.add(new JLabel("Tìm kiếm: ")); pRight.add(txtSearch); pRight.add(btnTim);
        top.add(t, BorderLayout.WEST); top.add(pRight, BorderLayout.EAST);

        String[] cols = {"Mã Phiếu", "Ngày Nhập", "Nhà Cung Cấp", "Tổng Tiền (đ)", "Người Nhập"};
        modelLichSu = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblLichSu = new JTable(modelLichSu);
        stylizeTable(tblLichSu);
        DefaultTableCellRenderer right = new DefaultTableCellRenderer();
        right.setHorizontalAlignment(SwingConstants.RIGHT);
        tblLichSu.getColumnModel().getColumn(3).setCellRenderer(right);

        btnTim.addActionListener(e -> timLichSu(txtSearch.getText().trim()));
        txtSearch.addActionListener(e -> timLichSu(txtSearch.getText().trim()));

        p.add(top, BorderLayout.NORTH);
        p.add(new JScrollPane(tblLichSu), BorderLayout.CENTER);
        return p;
    }

    // ==========================================================
    // EVENTS
    // ==========================================================
    private void initEvents() {
        txtMaSachNhap.addActionListener(e -> lookupTenSach());
        btnThemDong.addActionListener(e -> themDong());
        btnCapNhatDong.addActionListener(e -> capNhatDong());

        btnXoaDong.addActionListener(e -> {
            int row = tblChiTiet.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Chọn dòng cần xóa!"); return; }
            modelChiTiet.removeRow(row);
            capNhatTongTien();
        });

        tblChiTiet.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblChiTiet.getSelectedRow();
                if (row >= 0) {
                    txtMaSachNhap.setText(modelChiTiet.getValueAt(row, 0).toString());
                    txtTenSach.setText(modelChiTiet.getValueAt(row, 1).toString());
                    txtSoLuong.setText(modelChiTiet.getValueAt(row, 2).toString());
                    String dgRaw = modelChiTiet.getValueAt(row, 3).toString().replaceAll("[,\\.]", "");
                    txtDonGia.setText(dgRaw);
                }
            }
        });

        btnLuu.addActionListener(e -> luuPhieu());
        btnHuyPhieu.addActionListener(e -> huyPhieu());
        btnLamMoi.addActionListener(e -> { lamMoiPhieu(); loadLichSu(); });

        tblLichSu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblLichSu.getSelectedRow();
                if (row < 0) return;
                xemChiTiet(modelLichSu.getValueAt(row, 0).toString());
            }
        });
    }

    // ==========================================================
    // LOGIC
    // ==========================================================
    private void lookupTenSach() {
        String maSach = txtMaSachNhap.getText().trim();
        if (maSach.isEmpty()) return;
        SachDTO s = sachDAO.getById(maSach);
        if (s != null) {
            txtTenSach.setText(s.getTenSach());
        } else {
            txtTenSach.setText("⚠ Không tìm thấy");
            JOptionPane.showMessageDialog(this,
                    "Không tìm thấy sách: " + maSach + "\nKiểm tra lại Mã Sách!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void themDong() {
        String maSach  = txtMaSachNhap.getText().trim();
        String tenSach = txtTenSach.getText().trim();
        String slStr   = txtSoLuong.getText().trim();
        String dgStr   = txtDonGia.getText().trim();

        if (maSach.isEmpty() || tenSach.isEmpty() || tenSach.startsWith("⚠")) {
            JOptionPane.showMessageDialog(this, "Nhập Mã Sách hợp lệ trước!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        for (int i = 0; i < modelChiTiet.getRowCount(); i++) {
            if (modelChiTiet.getValueAt(i, 0).toString().equals(maSach)) {
                JOptionPane.showMessageDialog(this, "Sách " + maSach + " đã có trong phiếu rồi!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        int soLuong; float donGia;
        try {
            soLuong = Integer.parseInt(slStr);
            donGia  = Float.parseFloat(dgStr.replaceAll("[,\\.]", ""));
            if (soLuong <= 0 || donGia < 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên > 0, Đơn giá phải là số hợp lệ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        modelChiTiet.addRow(new Object[]{
            maSach, tenSach, soLuong,
            String.format("%,.0f", donGia),
            String.format("%,.0f", soLuong * donGia)
        });
        capNhatTongTien();
        txtMaSachNhap.setText(""); txtTenSach.setText("");
        txtSoLuong.setText("1"); txtDonGia.setText("0");
        txtMaSachNhap.requestFocus();
    }

    private void capNhatDong() {
        int row = tblChiTiet.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 dòng trong bảng chi tiết để cập nhật!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String maSach  = txtMaSachNhap.getText().trim();
        String tenSach = txtTenSach.getText().trim();
        String slStr   = txtSoLuong.getText().trim();
        String dgStr   = txtDonGia.getText().trim();

        if (maSach.isEmpty() || tenSach.isEmpty() || tenSach.startsWith("⚠")) {
            JOptionPane.showMessageDialog(this, "Nhập Mã Sách hợp lệ trước!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int soLuong; float donGia;
        try {
            soLuong = Integer.parseInt(slStr);
            donGia  = Float.parseFloat(dgStr.replaceAll("[,\\.]", ""));
            if (soLuong <= 0 || donGia < 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên > 0, Đơn giá phải là số hợp lệ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        modelChiTiet.setValueAt(maSach,  row, 0);
        modelChiTiet.setValueAt(tenSach, row, 1);
        modelChiTiet.setValueAt(soLuong, row, 2);
        modelChiTiet.setValueAt(String.format("%,.0f", donGia),            row, 3);
        modelChiTiet.setValueAt(String.format("%,.0f", soLuong * donGia),  row, 4);
        capNhatTongTien();
        txtMaSachNhap.setText(""); txtTenSach.setText("");
        txtSoLuong.setText("1"); txtDonGia.setText("0");
        tblChiTiet.clearSelection();
        txtMaSachNhap.requestFocus();
    }

    private void capNhatTongTien() {
        double tong = 0;
        for (int i = 0; i < modelChiTiet.getRowCount(); i++) {
            String ttStr = modelChiTiet.getValueAt(i, 4).toString().replaceAll("[,\\.]", "");
            try { tong += Double.parseDouble(ttStr); } catch (Exception ignored) {}
        }
        lblTongTien.setText(String.format("%,.0f đ", tong));
    }

    private void luuPhieu() {
        String maPN   = txtMaPN.getText().trim();
        String ngay   = txtNgayNhap.getText().trim();
        String tenNXB = (String) cbNXB.getSelectedItem();
        String maNXB  = getMaNXBByTen(tenNXB);

        if (ngay.isEmpty()) { JOptionPane.showMessageDialog(this, "Vui lòng nhập Ngày nhập!"); return; }
        if (maNXB == null || maNXB.isEmpty()) { JOptionPane.showMessageDialog(this, "Vui lòng chọn Nhà cung cấp!"); return; }
        if (modelChiTiet.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                "Chưa có sách nào trong phiếu!\nThêm ít nhất 1 dòng chi tiết.",
                "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Lưu phiếu " + maPN + " với " + modelChiTiet.getRowCount() + " loại sách?\n"
                + "Hệ thống sẽ tự động tạo bản sao sách (SACHCOPY) trong kho.",
                "Xác nhận lưu", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        String maNQL = SessionManager.getInstance().getMaNguoi();
        if (maNQL == null || maNQL.isEmpty()) maNQL = "NV00000001";

        double tong = 0;
        for (int i = 0; i < modelChiTiet.getRowCount(); i++) {
            String tt = modelChiTiet.getValueAt(i, 4).toString().replaceAll("[,\\.]", "");
            try { tong += Double.parseDouble(tt); } catch (Exception ignored) {}
        }

        PhieuNhapDTO pn = new PhieuNhapDTO(maPN, ngay, (float) tong, maNXB, maNQL);
        String r = pnBUS.addPhieuNhap(pn);
        if (!r.equals("Thêm thành công")) {
            JOptionPane.showMessageDialog(this, "Lỗi lưu phiếu: " + r, "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int tongBanSao = 0;
        try (java.sql.Connection con = DAO.DatabaseConnection.getConnection()) {
            con.setAutoCommit(false);
            SachCopyDAO copyDAO = new SachCopyDAO();
            try {
                for (int i = 0; i < modelChiTiet.getRowCount(); i++) {
                    String maSach  = modelChiTiet.getValueAt(i, 0).toString().trim();
                    String tenSach = modelChiTiet.getValueAt(i, 1).toString().trim();
                    int    sl      = Integer.parseInt(modelChiTiet.getValueAt(i, 2).toString());
                    float  dg      = Float.parseFloat(
                            modelChiTiet.getValueAt(i, 3).toString().replaceAll("[,\\.]", ""));

                    String sqlCT = "INSERT INTO CHITIETPHIEUNHAP (MaPN, MaSach, SoLuongNhap, DonGia) VALUES (?, ?, ?, ?)";
                    try (java.sql.PreparedStatement ps = con.prepareStatement(sqlCT)) {
                        ps.setString(1, maPN); ps.setString(2, maSach);
                        ps.setInt(3, sl);      ps.setFloat(4, dg);
                        ps.executeUpdate();
                    }

                    tongBanSao += copyDAO.insertNewCopies(con, maSach, tenSach, sl);
                }
                con.commit();
            } catch (Exception ex) {
                con.rollback();
                pnBUS.deletePhieuNhap(maPN);
                JOptionPane.showMessageDialog(this,
                    "Lỗi khi lưu chi tiết: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
                return;
            }
        } catch (Exception ex) { ex.printStackTrace(); return; }

        JOptionPane.showMessageDialog(this,
                "✔ Lưu phiếu thành công!\n"
                + "Mã phiếu: " + maPN + "\n"
                + "Đã tạo " + tongBanSao + " bản sao sách mới trong kho.",
                "Thành công", JOptionPane.INFORMATION_MESSAGE);

        lamMoiPhieu();
        loadLichSu();

        // ===== NOTIFY Panel Quản Lý Sách tự refresh số cuốn =====
        if (onSaveCallback != null) onSaveCallback.run();
    }

    private void huyPhieu() {
        int row = tblLichSu.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Chọn 1 phiếu trong lịch sử để hủy!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String maPN = modelLichSu.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this,
                "Hủy phiếu " + maPN + "?\n⚠ Lưu ý: Bản sao sách đã tạo sẽ không bị xóa.",
                "Xác nhận hủy", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm != JOptionPane.YES_OPTION) return;

        ctBUS.deleteAllByMaPN(maPN);
        String r = pnBUS.deletePhieuNhap(maPN);
        if (r.equals("Xóa thành công")) {
            JOptionPane.showMessageDialog(this, "Đã hủy phiếu " + maPN);
            loadLichSu();
        } else {
            JOptionPane.showMessageDialog(this, r, "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xemChiTiet(String maPN) {
        List<ChiTietPhieuNhapDTO> list = ctBUS.getByMaPN(maPN);
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Phiếu " + maPN + " không có chi tiết.");
            return;
        }
        DefaultTableModel m = new DefaultTableModel(
                new String[]{"Mã Sách", "Số Lượng", "Đơn Giá (đ)", "Thành Tiền (đ)"}, 0);
        for (ChiTietPhieuNhapDTO ct : list) {
            float tt = ct.getSoLuongNhap() * ct.getDonGia();
            m.addRow(new Object[]{ct.getMaSach(), ct.getSoLuongNhap(),
                String.format("%,.0f", ct.getDonGia()),
                String.format("%,.0f", tt)});
        }
        JTable t = new JTable(m); t.setRowHeight(28);
        t.setFont(new Font(tenFont, Font.PLAIN, 13));
        JOptionPane.showMessageDialog(this, new JScrollPane(t),
                "Chi Tiết Phiếu: " + maPN, JOptionPane.PLAIN_MESSAGE);
    }

    private void loadLichSu() {
        modelLichSu.setRowCount(0);
        for (PhieuNhapDTO pn : pnBUS.findAll()) {
            modelLichSu.addRow(new Object[]{
                pn.getMaPN(), pn.getNgayNhap(), pn.getMaNXB(),
                String.format("%,.0f", pn.getTongTien()), pn.getMaNQL()
            });
        }
    }

    private void timLichSu(String kw) {
        if (kw.isEmpty()) { loadLichSu(); return; }
        modelLichSu.setRowCount(0);
        String lower = kw.toLowerCase();
        for (PhieuNhapDTO pn : pnBUS.findAll()) {
            if (pn.getMaPN().toLowerCase().contains(lower)
                    || pn.getMaNXB().toLowerCase().contains(lower)) {
                modelLichSu.addRow(new Object[]{
                    pn.getMaPN(), pn.getNgayNhap(), pn.getMaNXB(),
                    String.format("%,.0f", pn.getTongTien()), pn.getMaNQL()
                });
            }
        }
    }

    public void lamMoiPhieu() {
        txtMaPN.setText(new PhieuNhapDAO().generateMaPN());
        txtNgayNhap.setText(LocalDate.now().toString());
        if (cbNXB != null && cbNXB.getItemCount() > 0) cbNXB.setSelectedIndex(0);
        modelChiTiet.setRowCount(0);
        lblTongTien.setText("0 đ");
        txtMaSachNhap.setText(""); txtTenSach.setText("");
        txtSoLuong.setText("1"); txtDonGia.setText("0");
        tblLichSu.clearSelection();
    }

    private void loadComboBoxNXB() {
        cbNXB.removeAllItems();
        listNXB = nxbBUS.getAll();
        if (listNXB != null)
            for (NhaXuatBanDTO nxb : listNXB) cbNXB.addItem(nxb.getTenNXB());
    }

    private String getMaNXBByTen(String tenNXB) {
        if (listNXB != null)
            for (NhaXuatBanDTO nxb : listNXB)
                if (nxb.getTenNXB().equals(tenNXB)) return nxb.getMaNXB();
        return "";
    }

    // ==========================================================
    // UI HELPERS
    // ==========================================================
    private JPanel card() {
        JPanel p = new JPanel();
        p.setBackground(clrWhite);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(clrBorder, 1, true),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        return p;
    }

    private JLabel lbl(String t, Font f) {
        JLabel l = new JLabel(t); l.setFont(f);
        l.setForeground(new Color(73, 80, 87)); return l;
    }

    private JTextField roField(Font f) {
        JTextField tf = new JTextField(); tf.setFont(f); tf.setEditable(false);
        tf.setBackground(new Color(233, 236, 239)); return tf;
    }

    private JTextField tf(Font f, int cols) {
        JTextField tf = new JTextField(cols); tf.setFont(f);
        tf.setPreferredSize(new Dimension(0, 32)); return tf;
    }

    private JButton btn(String text, Color bg) {
        JButton b = new JButton(text);
        b.setFont(new Font(tenFont, Font.BOLD, 14));
        b.setBackground(bg); b.setForeground(Color.WHITE);
        b.setFocusPainted(false); b.setBorderPainted(false);
        b.setPreferredSize(new Dimension(170, 40));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR)); return b;
    }

    private JButton smallBtn(String text, Color bg) {
        JButton b = new JButton(text);
        b.setFont(new Font(tenFont, Font.BOLD, 13));
        b.setBackground(bg); b.setForeground(Color.WHITE);
        b.setFocusPainted(false); b.setBorderPainted(false);
        b.setPreferredSize(new Dimension(130, 30));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR)); return b;
    }

    private void stylizeTable(JTable t) {
        t.setRowHeight(30); t.setFont(new Font(tenFont, Font.PLAIN, 13));
        t.getTableHeader().setFont(new Font(tenFont, Font.BOLD, 13));
        t.getTableHeader().setBackground(new Color(233, 236, 239));
        t.setShowGrid(false); t.setShowHorizontalLines(true);
        t.setGridColor(clrBorder); t.setIntercellSpacing(new Dimension(0, 0));
        t.setSelectionBackground(new Color(37, 99, 235));
        t.setSelectionForeground(Color.WHITE);
    }
}