    package GUI;

    import javax.swing.*;
    import javax.swing.table.DefaultTableModel;
    import java.awt.*;

    public class PhiPhatPanel extends JPanel {

        private JLabel lblTongPhi;
        private JTable tablePhi;
        private DefaultTableModel tableModel;
        private JButton btnLamMoi;
        private JButton btnThanhToan;

        public PhiPhatPanel() {
            initComponents();
        }

        private void initComponents() {
            setLayout(new BorderLayout(10, 10));
            setBackground(new Color(240, 240, 240));

            // ===== LABEL TỔNG PHÍ (TRÊN) =====
            JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
            panelTop.setBackground(new Color(240, 240, 240));

            lblTongPhi = new JLabel("Tổng phí chưa thanh toán: 0.0 VND");
            lblTongPhi.setFont(new Font("Arial", Font.BOLD, 14));
            panelTop.add(lblTongPhi);

            // ===== BẢNG PHÍ PHẠT =====
            String[] columns = {"Mã phí", "Phiếu mượn", "Số tiền", "Lý do", "Trạng thái"};
            tableModel = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            tablePhi = new JTable(tableModel);
            tablePhi.setFont(new Font("Arial", Font.PLAIN, 13));
            tablePhi.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
            tablePhi.setRowHeight(25);
            tablePhi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            JScrollPane scrollPane = new JScrollPane(tablePhi);

            // ===== PANEL NÚT =====
            JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
            panelButtons.setBackground(new Color(240, 240, 240));

            btnLamMoi = new JButton("Làm mới");
            btnLamMoi.setFont(new Font("Arial", Font.PLAIN, 13));

            btnThanhToan = new JButton("Thanh toán");
            btnThanhToan.setFont(new Font("Arial", Font.PLAIN, 13));

            panelButtons.add(btnLamMoi);
            panelButtons.add(btnThanhToan);

            // ===== THÊM VÀO PANEL CHÍNH =====
            add(panelTop, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);
            add(panelButtons, BorderLayout.SOUTH);
        }

        // ===== GETTER =====
        public JLabel getLblTongPhi() { return lblTongPhi; }
        public JTable getTablePhi() { return tablePhi; }
        public DefaultTableModel getTableModel() { return tableModel; }
        public JButton getBtnLamMoi() { return btnLamMoi; }
        public JButton getBtnThanhToan() { return btnThanhToan; }
    }