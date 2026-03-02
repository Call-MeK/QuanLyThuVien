package GUI;import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SachDangMuonPanel extends JPanel {

    private JTable tableMuon;
    private DefaultTableModel tableModel;
    private JButton btnLamMoi;
    private JButton btnTraSach;
    private JButton btnGiaHan;

    public SachDangMuonPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 240, 240));

        // ===== BẢNG SÁCH ĐANG MƯỢN =====
        String[] columns = {"Mã phiếu", "Sách", "Ngày mượn", "Ngày trả", "Trạng thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableMuon = new JTable(tableModel);
        tableMuon.setFont(new Font("Arial", Font.PLAIN, 13));
        tableMuon.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tableMuon.setRowHeight(25);
        tableMuon.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tableMuon);

        // ===== PANEL NÚT =====
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelButtons.setBackground(new Color(240, 240, 240));

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setFont(new Font("Arial", Font.PLAIN, 13));

        btnTraSach = new JButton("Trả sách");
        btnTraSach.setFont(new Font("Arial", Font.PLAIN, 13));

        btnGiaHan = new JButton("Gia hạn");
        btnGiaHan.setFont(new Font("Arial", Font.PLAIN, 13));

        panelButtons.add(btnLamMoi);
        panelButtons.add(btnTraSach);
        panelButtons.add(btnGiaHan);

        // ===== THÊM VÀO PANEL CHÍNH =====
        add(scrollPane, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);
    }

    // ===== GETTER =====
    public JTable getTableMuon() { return tableMuon; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getBtnLamMoi() { return btnLamMoi; }
    public JButton getBtnTraSach() { return btnTraSach; }
    public JButton getBtnGiaHan() { return btnGiaHan; }
}