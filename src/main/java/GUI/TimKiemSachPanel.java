package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;
import BUS.SachBUS;
import BUS.TheLoaiBUS;
import BUS.NhaXuatBanBUS;
import DTO.SachDTO;
import DTO.TheLoaiDTO;
import DTO.NhaXuatBanDTO;

public class TimKiemSachPanel extends JPanel {

    private String tenFont = "Segoe UI";
    private JTable table;
    private JButton btnXemChiTiet;

    public TimKiemSachPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 20));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // ==========================================
        // PHẦN TRÊN: TIÊU ĐỀ & THANH TÌM KIẾM
        // ==========================================
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

        JTextField txtSearch = new JTextField(40);
        txtSearch.setPreferredSize(new Dimension(400, 35));
        txtSearch.setFont(new Font(tenFont, Font.PLAIN, 14));
        String placeholderText = " Nhập tiêu đề, tác giả, thể loại";
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

        JButton btnSearch = new JButton("Tìm kiếm");
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

        JComboBox<String> cbType = new JComboBox<>(new String[] {
                "Tất cả", "Văn học - Tiểu thuyết", "Khoa học - Công nghệ", "Lịch sử - Địa lý"
        });
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

        // ==========================================
        // PHẦN DƯỚI: KẾT QUẢ TÌM KIẾM
        // ==========================================
        String[] cols = { "Mã Sách", "Tên Sách", "Tác Giả", "Thể Loại", "NXB", "Tình Trạng" };
        DefaultTableModel model = new DefaultTableModel(cols, 0);

        // Load dữ liệu sách từ DB
        try {
            List<SachDTO> listSach = new SachBUS().getAll();
            TheLoaiBUS theLoaiBUS = new TheLoaiBUS();
            NhaXuatBanBUS nxbBUS = new NhaXuatBanBUS();

            for (SachDTO s : listSach) {
                // Lấy tên thể loại
                String tenTheLoai = s.getTheLoai(); // mặc định dùng mã
                if (tenTheLoai != null) {
                    TheLoaiDTO tl = theLoaiBUS.getById(tenTheLoai);
                    if (tl != null && tl.getTenTheLoai() != null) {
                        tenTheLoai = tl.getTenTheLoai();
                    }
                }

                // Lấy chuỗi tên tác giả
                String tenTacGia = "Không rõ";
                if (s.getDanhSachTacGia() != null && !s.getDanhSachTacGia().isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < s.getDanhSachTacGia().size(); i++) {
                        sb.append(s.getDanhSachTacGia().get(i).getTenTacGia());
                        if (i < s.getDanhSachTacGia().size() - 1) {
                            sb.append(", ");
                        }
                    }
                    tenTacGia = sb.toString();
                }

                // Lấy tên nhà xuất bản
                String tenNxb = s.getMaNXB(); // mặc định dùng mã
                if (tenNxb != null) {
                    NhaXuatBanDTO nxb = nxbBUS.getById(tenNxb);
                    if (nxb != null && nxb.getTenNXB() != null) {
                        tenNxb = nxb.getTenNXB();
                    }
                }

                model.addRow(new Object[] { s.getMaSach(), s.getTenSach(), tenTacGia, tenTheLoai, tenNxb,
                        "Sẵn sàng" });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        table = new JTable(model);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font(tenFont, Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(241, 245, 249));
        table.setFont(new Font(tenFont, Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.WHITE);

        btnXemChiTiet = new JButton("Xem Chi Tiết / Mượn");
        btnXemChiTiet.setBackground(new Color(34, 197, 94));
        btnXemChiTiet.setForeground(Color.WHITE);
        btnXemChiTiet.setFont(new Font(tenFont, Font.BOLD, 13));
        btnXemChiTiet.setFocusPainted(false);
        bottomPanel.add(btnXemChiTiet);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // GETTER ĐỂ BẮT SỰ KIỆN TỪ USER HOME FRAME
    public JButton getBtnXemChiTiet() {
        return btnXemChiTiet;
    }

    public JTable getTable() {
        return table;
    }
}