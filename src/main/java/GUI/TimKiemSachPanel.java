package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import BUS.SachBUS;
import BUS.TheLoaiBUS;
import BUS.NhaXuatBanBUS;
import BUS.SachCopyBUS;
import DTO.SachDTO;
import DTO.TheLoaiDTO;
import DTO.NhaXuatBanDTO;

public class TimKiemSachPanel extends JPanel {

    private String tenFont = "Segoe UI";
    private JTable table;
    private DefaultTableModel model;
    private JButton btnXemChiTiet;

    public TimKiemSachPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 20));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // PHẦN TRÊN
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Tìm cuốn sách yêu thích tiếp theo của bạn");
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 22));
        lblTitle.setForeground(new Color(33, 37, 41));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSubtitle = new JLabel("Khám phá hàng nghìn đầu sách trong thư viện số của chúng tôi");
        lblSubtitle.setFont(new Font(tenFont, Font.ITALIC, 14));
        lblSubtitle.setForeground(new Color(108, 117, 125));
        lblSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel searchBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        searchBoxPanel.setBackground(Color.WHITE);
        searchBoxPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JTextField txtSearch = new JTextField(40);
        txtSearch.setPreferredSize(new Dimension(400, 35));
        txtSearch.setFont(new Font(tenFont, Font.PLAIN, 14));
        final String placeholderText = " Nhập tiêu đề, tác giả, thể loại";
        txtSearch.setText(placeholderText);
        txtSearch.setForeground(Color.GRAY);
        txtSearch.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (txtSearch.getText().equals(placeholderText)) {
                    txtSearch.setText("");
                    txtSearch.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent evt) {
                if (txtSearch.getText().isEmpty()) {
                    txtSearch.setForeground(Color.GRAY);
                    txtSearch.setText(placeholderText);
                }
            }
        });

        final JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.setPreferredSize(new Dimension(100, 35));
        btnSearch.setFont(new Font(tenFont, Font.BOLD, 13));
        btnSearch.setBackground(new Color(13, 110, 253));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFocusPainted(false);
        btnSearch.setBorderPainted(false);
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));

        searchBoxPanel.add(txtSearch);
        searchBoxPanel.add(Box.createHorizontalStrut(10));
        searchBoxPanel.add(btnSearch);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblFilter = new JLabel("Lọc theo:");
        lblFilter.setFont(new Font(tenFont, Font.PLAIN, 14));

        TheLoaiBUS theLoaiBUS = new TheLoaiBUS();
        ArrayList<String> listTheLoai = theLoaiBUS.getTenTheLoai();
        listTheLoai.add(0, "Tất cả");
        final JComboBox<String> cbType = new JComboBox<>(listTheLoai.toArray(new String[0]));
        cbType.setPreferredSize(new Dimension(200, 30));
        cbType.setFont(new Font(tenFont, Font.PLAIN, 13));
        cbType.setBackground(Color.WHITE);

        filterPanel.add(lblFilter);
        filterPanel.add(cbType);

        topPanel.add(lblTitle);
        topPanel.add(Box.createVerticalStrut(5));
        topPanel.add(lblSubtitle);
        topPanel.add(Box.createVerticalStrut(20));
        topPanel.add(searchBoxPanel);
        topPanel.add(Box.createVerticalStrut(15));
        topPanel.add(filterPanel);

        String[] cols = { "Mã Sách", "Tên Sách", "Tác Giả", "Thể Loại", "NXB", "Còn Sẵn" };
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        loadData("", "Tất cả");

        btnSearch.addActionListener(e -> {
            String kw = txtSearch.getText().equals(placeholderText) ? "" : txtSearch.getText();
            loadData(kw, cbType.getSelectedItem().toString());
        });
        cbType.addActionListener(e -> {
            String kw = txtSearch.getText().equals(placeholderText) ? "" : txtSearch.getText();
            loadData(kw, cbType.getSelectedItem().toString());
        });
        txtSearch.addActionListener(e -> btnSearch.doClick());

        table = new JTable(model);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font(tenFont, Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(241, 245, 249));
        table.setFont(new Font(tenFont, Font.PLAIN, 14));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.getColumnModel().getColumn(5).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                if (!isSelected) {
                    String v = value != null ? value.toString() : "";
                    if (v.startsWith("0") || v.equals("Hết sách")) {
                        c.setForeground(new Color(220, 53, 69));
                    } else {
                        c.setForeground(new Color(25, 135, 84));
                    }
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.WHITE);

        btnXemChiTiet = new JButton("Xem Chi Tiết / Mượn");
        btnXemChiTiet.setFont(new Font(tenFont, Font.BOLD, 13));
        btnXemChiTiet.setBackground(new Color(99, 102, 241));
        btnXemChiTiet.setForeground(Color.WHITE);
        btnXemChiTiet.setFocusPainted(false);
        btnXemChiTiet.setBorderPainted(false);
        btnXemChiTiet.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnXemChiTiet.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btnXemChiTiet.setBackground(new Color(79, 70, 229));
            }

            public void mouseExited(MouseEvent evt) {
                btnXemChiTiet.setBackground(new Color(99, 102, 241));
            }
        });

        bottomPanel.add(btnXemChiTiet);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadData(String keyword, String theLoaiFilter) {
        model.setRowCount(0);
        try {
            List<SachDTO> listSach = (keyword == null || keyword.trim().isEmpty())
                    ? new SachBUS().getAll()
                    : new SachBUS().search(keyword);

            TheLoaiBUS theLoaiBUS = new TheLoaiBUS();
            NhaXuatBanBUS nxbBUS = new NhaXuatBanBUS();
            SachCopyBUS copyBUS = new SachCopyBUS();

            for (SachDTO s : listSach) {
                String tenTheLoai = s.getTheLoai();
                if (tenTheLoai != null) {
                    TheLoaiDTO tl = theLoaiBUS.getById(tenTheLoai.trim());
                    if (tl != null && tl.getTenTheLoai() != null)
                        tenTheLoai = tl.getTenTheLoai();
                }

                if (!theLoaiFilter.equals("Tất cả")) {
                    if (tenTheLoai == null || !tenTheLoai.equals(theLoaiFilter))
                        continue;
                }

                String tenTacGia = "Không rõ";
                java.util.List<DTO.TacGiaDTO> dsTacGia = new DAO.SachDAO().getTacGiaCuaSach(s.getMaSach());

                if (dsTacGia != null && !dsTacGia.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < dsTacGia.size(); i++) {
                        sb.append(dsTacGia.get(i).getTenTacGia());
                        if (i < dsTacGia.size() - 1)
                            sb.append(", ");
                    }
                    tenTacGia = sb.toString();
                }

                String tenNxb = s.getMaNXB();
                if (tenNxb != null) {
                    NhaXuatBanDTO nxb = nxbBUS.getById(tenNxb.trim());
                    if (nxb != null && nxb.getTenNXB() != null)
                        tenNxb = nxb.getTenNXB();
                }

                int soCuonConSan = 0;
                try {
                    var dsBanSao = copyBUS.getAll();
                    for (var sc : dsBanSao) {
                        if (s.getMaSach().trim().equalsIgnoreCase(sc.getMaSach().trim())
                                && !Boolean.TRUE.equals(sc.getIsDeleted())
                                && !"Hỏng".equals(sc.getTinhTrang())) {
                            soCuonConSan++;
                        }
                    }
                } catch (Exception ex) {
                }

                String conSan = soCuonConSan > 0 ? "Còn " + soCuonConSan + " cuốn" : "Hết sách";

                model.addRow(new Object[] {
                        s.getMaSach(), s.getTenSach(), tenTacGia, tenTheLoai, tenNxb, conSan
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public JButton getBtnXemChiTiet() {
        return btnXemChiTiet;
    }

    public JTable getTable() {
        return table;
    }
}