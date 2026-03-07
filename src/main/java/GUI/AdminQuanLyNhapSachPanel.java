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

    // Form phiếu nhập
    private JTextField    txtMaPN, txtNgayNhap;
    private JComboBox<String> cbNXB;
    private JLabel        lblTongTien;

    // Form nhân viên (readonly)
    private JTextField    txtMaNV, txtHoTenNV, txtVaiTro;

    // Form thêm dòng
    private JTextField    txtMaSachNhap, txtTenSach, txtSoLuong, txtDonGia;
    private JButton       btnThemDong, btnCapNhatDong, btnXoaDong;
    private JTable        tblChiTiet;
    private DefaultTableModel modelChiTiet;

    private JButton btnLuu, btnHuyPhieu, btnLamMoi;

    // Lịch sử phiếu nhập
    private JTextField        txtSearch;
    private JTable            tblLichSu;
    private DefaultTableModel modelLichSu;

    private Runnable onSaveCallback;
    public void setOnSaveCallback(Runnable callback) { this.onSaveCallback = callback; }

    private final PhieuNhapBUS        pnBUS   = new PhieuNhapBUS();
    private final ChiTietPhieuNhapBUS ctBUS   = new ChiTietPhieuNhapBUS();
    private final SachDAO             sachDAO = new SachDAO();
    private final NhaXuatBanBUS       nxbBUS  = new NhaXuatBanBUS();
    private List<NhaXuatBanDTO>       listNXB;

    public AdminQuanLyNhapSachPanel() {
        buildUI();
        initEvents();
        lamMoiPhieu();
        hienThiThongTinNV();
        loadLichSu();

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

        // TIÊU ĐỀ
        JLabel lbl = new JLabel("Quản Lý Nhập Sách");
        lbl.setFont(new Font(tenFont, Font.BOLD, 24));
        lbl.setForeground(new Color(33, 37, 41));
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        add(lbl, BorderLayout.NORTH);

        // NỘI DUNG CHÍNH
        JPanel pnlTop = new JPanel(new BorderLayout(0, 10));
        pnlTop.setBackground(clrBackground);
        pnlTop.add(buildPanelPhieu(),   BorderLayout.NORTH);
        pnlTop.add(buildPanelChiTiet(), BorderLayout.CENTER);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pnlTop, buildPanelLichSu());
        split.setDividerLocation(400);
        split.setDividerSize(6);
        split.setResizeWeight(0.6);
        split.setBorder(null);
        split.setBackground(clrBackground);
        add(split, BorderLayout.CENTER);

        // NÚT DƯỚI CÙNG
        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        pnlBtn.setBackground(clrBackground);

        btnThemDong    = btnS("Thêm Dòng",    new Color(13, 110, 253));
        btnCapNhatDong = btnS("Cập Nhật Dòng", new Color(255, 193, 7));
        btnCapNhatDong.setForeground(Color.BLACK);
        btnXoaDong     = btnS("Xóa Dòng",      new Color(220, 53, 69));
        btnHuyPhieu    = btnS("Hủy Phiếu",     new Color(153, 51, 204));
        btnLamMoi      = btnS("Làm Mới",        new Color(108, 117, 125));
        btnLuu         = btnS("Lưu Phiếu",     new Color(25, 135, 84));

        pnlBtn.add(btnThemDong);
        pnlBtn.add(btnCapNhatDong);
        pnlBtn.add(btnXoaDong);
        pnlBtn.add(btnHuyPhieu);
        pnlBtn.add(btnLamMoi);
        pnlBtn.add(btnLuu);
        add(pnlBtn, BorderLayout.SOUTH);
    }

    /** Panel trên: thông tin phiếu + thông tin nhân viên */
    private JPanel buildPanelPhieu() {
        JPanel p = new JPanel(new GridLayout(1, 2, 15, 0));
        p.setBackground(clrBackground);

        // === TRÁI: Thông tin phiếu nhập ===
        JPanel pnlPhieu = card();
        pnlPhieu.setLayout(new GridLayout(3, 4, 12, 10));
        Font fl = new Font(tenFont, Font.BOLD, 13);
        Font fi = new Font(tenFont, Font.PLAIN, 13);

        txtMaPN     = roField(fi);
        txtNgayNhap = new JTextField(); txtNgayNhap.setFont(fi);
        cbNXB       = new JComboBox<>(); cbNXB.setFont(fi);
        loadComboBoxNXB();

        lblTongTien = new JLabel("0 đ");
        lblTongTien.setFont(new Font(tenFont, Font.BOLD, 15));
        lblTongTien.setForeground(new Color(25, 135, 84));

        pnlPhieu.add(lbl("Mã phiếu nhập:", fl)); pnlPhieu.add(txtMaPN);
        pnlPhieu.add(lbl("Ngày nhập:",     fl)); pnlPhieu.add(txtNgayNhap);
        pnlPhieu.add(lbl("Nhà cung cấp:",  fl)); pnlPhieu.add(cbNXB);
        pnlPhieu.add(lbl("Tổng tiền:",     fl)); pnlPhieu.add(lblTongTien);
        pnlPhieu.add(new JLabel()); pnlPhieu.add(new JLabel());
        pnlPhieu.add(new JLabel()); pnlPhieu.add(new JLabel());

        // === PHẢI: Thông tin nhân viên ===
        JPanel pnlNV = card();
        pnlNV.setLayout(new BorderLayout(0, 8));

        JLabel lblTitleNV = new JLabel("Thông Tin Nhân Viên Nhập");
        lblTitleNV.setFont(new Font(tenFont, Font.BOLD, 14));
        lblTitleNV.setForeground(new Color(13, 110, 253));
        lblTitleNV.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));
        pnlNV.add(lblTitleNV, BorderLayout.NORTH);

        JPanel pnlNVFields = new JPanel(new GridLayout(3, 2, 10, 8));
        pnlNVFields.setBackground(clrWhite);

        txtMaNV   = roField(fi);
        txtHoTenNV = roField(fi);
        txtVaiTro = roField(fi);

        pnlNVFields.add(lbl("Mã nhân viên:", fl)); pnlNVFields.add(txtMaNV);
        pnlNVFields.add(lbl("Họ tên:",       fl)); pnlNVFields.add(txtHoTenNV);
        pnlNVFields.add(lbl("Vai trò:",      fl)); pnlNVFields.add(txtVaiTro);
        pnlNV.add(pnlNVFields, BorderLayout.CENTER);

        p.add(pnlPhieu);
        p.add(pnlNV);
        return p;
    }

    /** Panel giữa: form thêm dòng + bảng chi tiết */
    private JPanel buildPanelChiTiet() {
        JPanel p = new JPanel(new BorderLayout(0, 8));
        p.setBackground(clrBackground);

        Font fl = new Font(tenFont, Font.BOLD, 13);
        Font fi = new Font(tenFont, Font.PLAIN, 13);

        JPanel pnlAddRow = card();
        pnlAddRow.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

        txtMaSachNhap = tf(fi, 12);
        txtMaSachNhap.setToolTipText("Nhập Mã Sách rồi Enter để tự tìm tên");
        txtTenSach = roField(fi); txtTenSach.setPreferredSize(new Dimension(200, 32));
        txtSoLuong = tf(fi, 6);  txtSoLuong.setText("1");
        txtDonGia  = tf(fi, 10); txtDonGia.setText("0");

        pnlAddRow.add(lbl("Mã sách:",        fl)); pnlAddRow.add(txtMaSachNhap);
        pnlAddRow.add(lbl("Tên sách:",        fl)); pnlAddRow.add(txtTenSach);
        pnlAddRow.add(lbl("Số lượng:",        fl)); pnlAddRow.add(txtSoLuong);
        pnlAddRow.add(lbl("Đơn giá 1 cuốn:", fl)); pnlAddRow.add(txtDonGia);

        String[] cols = {"Mã Sách", "Tên Sách", "Số Lượng", "Đơn Giá (đ)", "Thành Tiền (đ)"};
        modelChiTiet = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblChiTiet = new JTable(modelChiTiet);
        stylizeTable(tblChiTiet);
        tblChiTiet.getColumnModel().getColumn(0).setPreferredWidth(110);
        tblChiTiet.getColumnModel().getColumn(1).setPreferredWidth(250);
        tblChiTiet.getColumnModel().getColumn(2).setPreferredWidth(80);
        tblChiTiet.getColumnModel().getColumn(3).setPreferredWidth(120);
        tblChiTiet.getColumnModel().getColumn(4).setPreferredWidth(140);

        DefaultTableCellRenderer right = new DefaultTableCellRenderer();
        right.setHorizontalAlignment(SwingConstants.RIGHT);
        tblChiTiet.getColumnModel().getColumn(2).setCellRenderer(right);
        tblChiTiet.getColumnModel().getColumn(3).setCellRenderer(right);
        tblChiTiet.getColumnModel().getColumn(4).setCellRenderer(right);

        p.add(pnlAddRow, BorderLayout.NORTH);
        p.add(new JScrollPane(tblChiTiet), BorderLayout.CENTER);
        return p;
    }

    /** Panel dưới: lịch sử phiếu nhập */
    private JPanel buildPanelLichSu() {
        JPanel p = new JPanel(new BorderLayout(0, 8));
        p.setBackground(clrBackground);
        p.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));

        // Header
        JPanel top = new JPanel(new BorderLayout(10, 0));
        top.setBackground(clrBackground);
        JLabel t = new JLabel("Lịch Sử Phiếu Nhập");
        t.setFont(new Font(tenFont, Font.BOLD, 15));
        t.setForeground(new Color(33, 37, 41));

        txtSearch = new JTextField();
        txtSearch.setFont(new Font(tenFont, Font.PLAIN, 13));
        txtSearch.setPreferredSize(new Dimension(220, 30));
        txtSearch.setToolTipText("Tìm theo Mã Phiếu hoặc Người Nhập");
        JButton btnTim = btnS("Tìm", new Color(13, 110, 253));
        btnTim.setPreferredSize(new Dimension(80, 30));
        JButton btnXoaLoc = btnS("Xóa Lọc", new Color(108, 117, 125));
        btnXoaLoc.setPreferredSize(new Dimension(90, 30));

        JPanel pRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        pRight.setBackground(clrBackground);
        pRight.add(new JLabel("Tìm: ")); pRight.add(txtSearch);
        pRight.add(btnTim); pRight.add(btnXoaLoc);
        top.add(t, BorderLayout.WEST); top.add(pRight, BorderLayout.EAST);

        // Bảng lịch sử
        String[] cols = {"Mã Phiếu", "Ngày Nhập", "Nhà Cung Cấp", "Tổng Tiền (đ)", "Người Nhập"};
        modelLichSu = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblLichSu = new JTable(modelLichSu);
        stylizeTable(tblLichSu);
        tblLichSu.getColumnModel().getColumn(0).setPreferredWidth(100);
        tblLichSu.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblLichSu.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblLichSu.getColumnModel().getColumn(3).setPreferredWidth(120);
        tblLichSu.getColumnModel().getColumn(4).setPreferredWidth(150);
        DefaultTableCellRenderer right = new DefaultTableCellRenderer();
        right.setHorizontalAlignment(SwingConstants.RIGHT);
        tblLichSu.getColumnModel().getColumn(3).setCellRenderer(right);

        // Sự kiện tìm kiếm
        btnTim.addActionListener(e -> timLichSu(txtSearch.getText().trim()));
        txtSearch.addActionListener(e -> timLichSu(txtSearch.getText().trim()));
        btnXoaLoc.addActionListener(e -> { txtSearch.setText(""); loadLichSu(); });

        // Click vào dòng → xem chi tiết
        tblLichSu.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                int row = tblLichSu.getSelectedRow();
                if (row >= 0) xemChiTiet(modelLichSu.getValueAt(row, 0).toString());
            }
        });

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
                    txtDonGia.setText(modelChiTiet.getValueAt(row, 3).toString().replaceAll("[,\\.]", ""));
                }
            }
        });

        btnLuu.addActionListener(e -> luuPhieu());
        btnHuyPhieu.addActionListener(e -> huyPhieu());
        btnLamMoi.addActionListener(e -> lamMoiPhieu());
    }

    // ==========================================================
    // LOGIC
    // ==========================================================
    private void hienThiThongTinNV() {
        SessionManager sm = SessionManager.getInstance();
        if (sm.isLoggedIn()) {
            txtMaNV.setText(sm.getMaNguoi());
            txtHoTenNV.setText(sm.getHoTen());
            txtVaiTro.setText(sm.getVaiTro() != null ? sm.getVaiTro() : "Quản Lý");
        } else {
            txtMaNV.setText("NV00000001");
            txtHoTenNV.setText("—");
            txtVaiTro.setText("—");
        }
    }

    private void lookupTenSach() {
        String maSach = txtMaSachNhap.getText().trim();
        if (maSach.isEmpty()) return;
        SachDTO s = sachDAO.getById(maSach);
        if (s != null) {
            txtTenSach.setText(s.getTenSach());
        } else {
            txtTenSach.setText("Không tìm thấy");
            JOptionPane.showMessageDialog(this,
                    "Không tìm thấy sách: " + maSach,
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void themDong() {
        String maSach  = txtMaSachNhap.getText().trim();
        String tenSach = txtTenSach.getText().trim();
        if (maSach.isEmpty() || tenSach.isEmpty() || tenSach.startsWith("⚠")) {
            JOptionPane.showMessageDialog(this, "Nhập Mã Sách hợp lệ trước!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        for (int i = 0; i < modelChiTiet.getRowCount(); i++) {
            if (modelChiTiet.getValueAt(i, 0).toString().equals(maSach)) {
                JOptionPane.showMessageDialog(this, "Sách " + maSach + " đã có trong phiếu!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        int soLuong; float donGia;
        try {
            soLuong = Integer.parseInt(txtSoLuong.getText().trim());
            donGia  = Float.parseFloat(txtDonGia.getText().trim().replaceAll("[,\\.]", ""));
            if (soLuong <= 0 || donGia < 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số lượng > 0 và đơn giá phải là số hợp lệ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
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
            JOptionPane.showMessageDialog(this, "Chọn 1 dòng trong bảng để cập nhật!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String maSach  = txtMaSachNhap.getText().trim();
        String tenSach = txtTenSach.getText().trim();
        if (maSach.isEmpty() || tenSach.isEmpty() || tenSach.startsWith("⚠")) {
            JOptionPane.showMessageDialog(this, "Nhập Mã Sách hợp lệ trước!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int soLuong; float donGia;
        try {
            soLuong = Integer.parseInt(txtSoLuong.getText().trim());
            donGia  = Float.parseFloat(txtDonGia.getText().trim().replaceAll("[,\\.]", ""));
            if (soLuong <= 0 || donGia < 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số lượng > 0 và đơn giá phải là số hợp lệ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        modelChiTiet.setValueAt(maSach,  row, 0);
        modelChiTiet.setValueAt(tenSach, row, 1);
        modelChiTiet.setValueAt(soLuong, row, 2);
        modelChiTiet.setValueAt(String.format("%,.0f", donGia),           row, 3);
        modelChiTiet.setValueAt(String.format("%,.0f", soLuong * donGia), row, 4);
        capNhatTongTien();
        txtMaSachNhap.setText(""); txtTenSach.setText("");
        txtSoLuong.setText("1"); txtDonGia.setText("0");
        tblChiTiet.clearSelection();
        txtMaSachNhap.requestFocus();
    }

    private void capNhatTongTien() {
        double tong = 0;
        for (int i = 0; i < modelChiTiet.getRowCount(); i++) {
            try { tong += Double.parseDouble(
                    modelChiTiet.getValueAt(i, 4).toString().replaceAll("[,\\.]", ""));
            } catch (Exception ignored) {}
        }
        lblTongTien.setText(String.format("%,.0f đ", tong));
    }

    private void luuPhieu() {
        String maPN  = txtMaPN.getText().trim();
        String ngay  = txtNgayNhap.getText().trim();
        String maNXB = getMaNXBByTen((String) cbNXB.getSelectedItem());

        if (ngay.isEmpty())  { JOptionPane.showMessageDialog(this, "Vui lòng nhập Ngày nhập!"); return; }
        if (maNXB.isEmpty()) { JOptionPane.showMessageDialog(this, "Vui lòng chọn Nhà cung cấp!"); return; }
        if (modelChiTiet.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Chưa có sách nào trong phiếu!\nThêm ít nhất 1 dòng.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Lưu phiếu " + maPN + " với " + modelChiTiet.getRowCount() + " loại sách?\n"
                + "Hệ thống sẽ tự động tạo bản sao sách trong kho.",
                "Xác nhận lưu", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        String maNQL = SessionManager.getInstance().getMaNguoi();
        if (maNQL == null || maNQL.isEmpty()) maNQL = "NV00000001";

        double tong = 0;
        for (int i = 0; i < modelChiTiet.getRowCount(); i++) {
            try { tong += Double.parseDouble(
                    modelChiTiet.getValueAt(i, 4).toString().replaceAll("[,\\.]", ""));
            } catch (Exception ignored) {}
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

                    try (java.sql.PreparedStatement ps = con.prepareStatement(
                            "INSERT INTO CHITIETPHIEUNHAP (MaPN, MaSach, SoLuongNhap, DonGia) VALUES (?, ?, ?, ?)")) {
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
                    "Lỗi khi lưu chi tiết:\n" + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
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

        if (onSaveCallback != null) onSaveCallback.run();
    }

    private void huyPhieu() {
        String maPN = txtMaPN.getText().trim();
        int confirm = JOptionPane.showConfirmDialog(this,
                "Làm mới phiếu nhập hiện tại?\nDữ liệu chưa lưu sẽ bị xóa.",
                "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) lamMoiPhieu();
    }

    public void lamMoiPhieu() {
        txtMaPN.setText(new PhieuNhapDAO().generateMaPN());
        txtNgayNhap.setText(LocalDate.now().toString());
        if (cbNXB != null && cbNXB.getItemCount() > 0) cbNXB.setSelectedIndex(0);
        modelChiTiet.setRowCount(0);
        lblTongTien.setText("0 đ");
        txtMaSachNhap.setText(""); txtTenSach.setText("");
        txtSoLuong.setText("1"); txtDonGia.setText("0");
        hienThiThongTinNV();
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
                    || pn.getMaNQL().toLowerCase().contains(lower)
                    || pn.getMaNXB().toLowerCase().contains(lower)) {
                modelLichSu.addRow(new Object[]{
                    pn.getMaPN(), pn.getNgayNhap(), pn.getMaNXB(),
                    String.format("%,.0f", pn.getTongTien()), pn.getMaNQL()
                });
            }
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
                BorderFactory.createEmptyBorder(12, 14, 12, 14)));
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

    private JButton btnS(String text, Color bg) {
        JButton b = new JButton(text);
        b.setFont(new Font(tenFont, Font.BOLD, 13));
        b.setBackground(bg); b.setForeground(Color.WHITE);
        b.setFocusPainted(false); b.setBorderPainted(false);
        b.setPreferredSize(new Dimension(145, 38));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR)); return b;
    }

    private JButton btn(String text, Color bg) {
        JButton b = new JButton(text);
        b.setFont(new Font(tenFont, Font.BOLD, 14));
        b.setBackground(bg); b.setForeground(Color.WHITE);
        b.setFocusPainted(false); b.setBorderPainted(false);
        b.setPreferredSize(new Dimension(170, 40));
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