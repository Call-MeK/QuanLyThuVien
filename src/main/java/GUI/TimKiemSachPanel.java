import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TimKiemSachPanel extends JPanel {

    private JComboBox<String> cboTimTheo;
    private JTextField txtTimKiem;
    private JButton btnTimKiem;
    private JButton btnTimKiemNangCao;
    private JButton btnMuonSach;
    private JButton btnDatTruoc;
    private JTable tableSach;
    private DefaultTableModel tableModel;

    public TimKiemSachPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 240, 240));

        // ===== PANEL TÌM KIẾM (TRÊN) =====
        JPanel panelTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelTimKiem.setBackground(new Color(240, 240, 240));

        JLabel lblTimTheo = new JLabel("Tìm theo:");
        lblTimTheo.setFont(new Font("Arial", Font.PLAIN, 14));

        cboTimTheo = new JComboBox<>(new String[]{"Tiêu đề", "Tác giả", "ISBN", "Thể loại"});
        cboTimTheo.setFont(new Font("Arial", Font.PLAIN, 13));
        cboTimTheo.setPreferredSize(new Dimension(100, 28));

        txtTimKiem = new JTextField();
        txtTimKiem.setFont(new Font("Arial", Font.PLAIN, 13));
        txtTimKiem.setPreferredSize(new Dimension(300, 28));

        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setFont(new Font("Arial", Font.PLAIN, 13));

        panelTimKiem.add(lblTimTheo);
        panelTimKiem.add(cboTimTheo);
        panelTimKiem.add(txtTimKiem);
        panelTimKiem.add(btnTimKiem);

        // ===== BẢNG SÁCH (GIỮA) =====
        String[] columns = {"ISBN", "Tiêu đề", "Tác giả", "Năm XB", "Còn lại", "Vị trí"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableSach = new JTable(tableModel);
        tableSach.setFont(new Font("Arial", Font.PLAIN, 13));
        tableSach.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tableSach.setRowHeight(25);
        tableSach.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tableSach);

        // ===== PANEL NÚT (DƯỚI) =====
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelButtons.setBackground(new Color(240, 240, 240));

        btnTimKiemNangCao = new JButton("Tìm kiếm nâng cao");
        btnTimKiemNangCao.setFont(new Font("Arial", Font.PLAIN, 13));

        btnMuonSach = new JButton("Mượn sách");
        btnMuonSach.setFont(new Font("Arial", Font.PLAIN, 13));

        btnDatTruoc = new JButton("Đặt trước");
        btnDatTruoc.setFont(new Font("Arial", Font.PLAIN, 13));

        panelButtons.add(btnTimKiemNangCao);
        panelButtons.add(btnMuonSach);
        panelButtons.add(btnDatTruoc);

        // ===== THÊM VÀO PANEL CHÍNH =====
        add(panelTimKiem, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);
    }

    // ===== GETTER =====
    public JComboBox<String> getCboTimTheo() { return cboTimTheo; }
    public JTextField getTxtTimKiem() { return txtTimKiem; }
    public JButton getBtnTimKiem() { return btnTimKiem; }
    public JButton getBtnTimKiemNangCao() { return btnTimKiemNangCao; }
    public JButton getBtnMuonSach() { return btnMuonSach; }
    public JButton getBtnDatTruoc() { return btnDatTruoc; }
    public JTable getTableSach() { return tableSach; }
    public DefaultTableModel getTableModel() { return tableModel; }
}