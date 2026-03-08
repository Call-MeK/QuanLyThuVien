package GUI;

import BUS.ThongKeBUS;
import Utils.PDFExporter;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ThongKePanel extends JPanel {

    private ThongKeBUS thongKeBUS = new ThongKeBUS();

    // Cấu hình UI (giữ đồng bộ với AdminFrame)
    private String tenFont = "Segoe UI";
    private Color colorBackground = new Color(248, 249, 250);
    private Color colorPrimary = new Color(13, 110, 253);

    // Bảng dữ liệu
    private DefaultTableModel modelTheLoai;
    private DefaultTableModel modelDocGia;
    private DefaultTableModel modelSach;
    
    private JTable tableTheLoai;
    private JTable tableDocGia;
    private JTable tableSach;
    private JTabbedPane tabbedPane;

    // Biểu đồ cột
    private BarChartPanel chartTheLoai;
    private BarChartPanel chartDocGia;
    private BarChartPanel chartSach;

    public ThongKePanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBackground(colorBackground);
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(colorBackground);

        JLabel lblTitle = new JLabel("Thống Kê Thư Viện");
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 24));
        lblTitle.setForeground(new Color(33, 37, 41));
        pnlHeader.add(lblTitle, BorderLayout.WEST);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlButtons.setBackground(colorBackground);

        JButton btnXuatExcel = new JButton("Xuất Excel");
        btnXuatExcel.setFont(new Font(tenFont, Font.BOLD, 14));
        btnXuatExcel.setForeground(Color.WHITE);
        btnXuatExcel.setBackground(new Color(25, 135, 84));
        btnXuatExcel.setBorderPainted(false);
        btnXuatExcel.setFocusPainted(false);
        btnXuatExcel.setPreferredSize(new Dimension(130, 40));
        btnXuatExcel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton btnXuatPDF = new JButton("Xuất PDF");
        btnXuatPDF.setFont(new Font(tenFont, Font.BOLD, 14));
        btnXuatPDF.setForeground(Color.WHITE);
        btnXuatPDF.setBackground(new Color(220, 53, 69)); 
        btnXuatPDF.setBorderPainted(false);
        btnXuatPDF.setFocusPainted(false);
        btnXuatPDF.setPreferredSize(new Dimension(130, 40));
        btnXuatPDF.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton btnLoad = new JButton("Tải Thống Kê");
        btnLoad.setFont(new Font(tenFont, Font.BOLD, 14));
        btnLoad.setForeground(Color.WHITE);
        btnLoad.setBackground(colorPrimary);
        btnLoad.setBorderPainted(false);
        btnLoad.setFocusPainted(false);
        btnLoad.setPreferredSize(new Dimension(160, 40));
        btnLoad.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnLoad.addActionListener(e -> loadThongKe());
        
        btnXuatPDF.addActionListener(e -> exportToPDF());

        btnXuatExcel.addActionListener(e -> exportToExcel());

        pnlButtons.add(btnXuatExcel);
        pnlButtons.add(btnXuatPDF);
        pnlButtons.add(btnLoad);

        pnlHeader.add(pnlButtons, BorderLayout.EAST);

        add(pnlHeader, BorderLayout.NORTH);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font(tenFont, Font.BOLD, 14));
        tabbedPane.setBackground(colorBackground);

        tabbedPane.addTab("Sách theo Thể Loại", createTabTheLoai());
        tabbedPane.addTab("Top 5 Độc Giả Mượn Nhiều", createTabDocGia());
        tabbedPane.addTab("Top 10 Sách Mượn Nhiều", createTabSach());

        add(tabbedPane, BorderLayout.CENTER);
    }
    private void exportToPDF() {
        if (modelTheLoai.getRowCount() == 0 && modelDocGia.getRowCount() == 0 && modelSach.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Chưa có dữ liệu thống kê! Vui lòng nhấn 'Tải Thống Kê' trước.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int selectedIndex = tabbedPane.getSelectedIndex();
        if (selectedIndex == 0) {
            PDFExporter.exportTableToPDF(tableTheLoai, "BAO CAO THONG KE SACH THEO THE LOAI");
        } else if (selectedIndex == 1) {
            PDFExporter.exportTableToPDF(tableDocGia, "BAO CAO TOP 5 DOC GIA MUON NHIEU NHAT");
        } else if (selectedIndex == 2) {
            PDFExporter.exportTableToPDF(tableSach, "BAO CAO TOP 10 SACH DUOC MUON NHIEU NHAT");
        }
    }
    private void exportToExcel() {
        if (modelTheLoai.getRowCount() == 0 && modelDocGia.getRowCount() == 0 && modelSach.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Chưa có dữ liệu thống kê! Vui lòng nhấn 'Tải Thống Kê' trước.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int selectedIndex = tabbedPane.getSelectedIndex();
        if (selectedIndex == 0) {
            Utils.ExcelExporter.exportTableToExcel(tableTheLoai, "ThongKe_SachTheoTheLoai");
        } else if (selectedIndex == 1) {
            Utils.ExcelExporter.exportTableToExcel(tableDocGia, "ThongKe_Top5DocGia");
        } else if (selectedIndex == 2) {
            Utils.ExcelExporter.exportTableToExcel(tableSach, "ThongKe_Top10Sach");
        }
    }

    private JPanel createTabTheLoai() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 15, 0));
        panel.setBackground(colorBackground);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        modelTheLoai = new DefaultTableModel(new String[] { "Thể Loại", "Số Lượng" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableTheLoai = (JTable) createStyledTable(modelTheLoai); 
        JScrollPane scrollPane = new JScrollPane(tableTheLoai); 
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1));

        JPanel pnlTable = createSectionPanel("Bảng Dữ Liệu");
        pnlTable.add(scrollPane, BorderLayout.CENTER);

        chartTheLoai = new BarChartPanel("Biểu Đồ Sách Theo Thể Loại",
                new Color(54, 162, 235), new Color(30, 100, 180));

        JPanel pnlChart = createSectionPanel("Biểu Đồ Cột");
        pnlChart.add(chartTheLoai, BorderLayout.CENTER);

        panel.add(pnlTable);
        panel.add(pnlChart);
        return panel;
    }

    private JPanel createTabDocGia() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 15, 0));
        panel.setBackground(colorBackground);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));

        modelDocGia = new DefaultTableModel(new String[] { "Độc Giả", "Số Lượng Mượn" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableDocGia = (JTable) createStyledTable(modelDocGia);
        JScrollPane scrollPane = new JScrollPane(tableDocGia);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1));

        JPanel pnlTable = createSectionPanel("Bảng Dữ Liệu");
        pnlTable.add(scrollPane, BorderLayout.CENTER);

        chartDocGia = new BarChartPanel("Biểu Đồ Top 5 Độc Giả Mượn Nhiều",
                new Color(75, 192, 120), new Color(40, 130, 70));

        JPanel pnlChart = createSectionPanel("Biểu Đồ Cột");
        pnlChart.add(chartDocGia, BorderLayout.CENTER);

        panel.add(pnlTable);
        panel.add(pnlChart);
        return panel;
    }

    private JPanel createTabSach() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 15, 0));
        panel.setBackground(colorBackground);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));

        modelSach = new DefaultTableModel(new String[] { "Sách", "Số Lần Mượn" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableSach = (JTable) createStyledTable(modelSach);
        JScrollPane scrollPane = new JScrollPane(tableSach);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1));

        JPanel pnlTable = createSectionPanel("Bảng Dữ Liệu");
        pnlTable.add(scrollPane, BorderLayout.CENTER);

        chartSach = new BarChartPanel("Biểu Đồ Top 10 Sách Mượn Nhiều",
                new Color(255, 159, 64), new Color(200, 100, 30));

        JPanel pnlChart = createSectionPanel("Biểu Đồ Cột");
        pnlChart.add(chartSach, BorderLayout.CENTER);

        panel.add(pnlTable);
        panel.add(pnlChart);
        return panel;
    }

    private void loadThongKe() {
        try {
            Map<String, Integer> dataTheLoai = thongKeBUS.thongKeSachTheoTheLoai();
            modelTheLoai.setRowCount(0);
            for (Map.Entry<String, Integer> entry : dataTheLoai.entrySet()) {
                modelTheLoai.addRow(new Object[] { entry.getKey(), entry.getValue() });
            }
            chartTheLoai.setData(dataTheLoai);

            Map<String, Integer> dataDocGia = thongKeBUS.thongKeTop5DocGiaMuonNhieu();
            modelDocGia.setRowCount(0);
            for (Map.Entry<String, Integer> entry : dataDocGia.entrySet()) {
                modelDocGia.addRow(new Object[] { entry.getKey(), entry.getValue() });
            }
            chartDocGia.setData(dataDocGia);

            Map<String, Integer> dataSach = thongKeBUS.thongKeSachMuonNhieuNhat();
            modelSach.setRowCount(0);
            for (Map.Entry<String, Integer> entry : dataSach.entrySet()) {
                modelSach.addRow(new Object[] { entry.getKey(), entry.getValue() });
            }
            chartSach.setData(dataSach);

            JOptionPane.showMessageDialog(this,
                    "Tải dữ liệu thành công!\n"
                            + "- Thể loại: " + dataTheLoai.size() + " dòng\n"
                            + "- Độc giả: " + dataDocGia.size() + " dòng\n"
                            + "- Sách: " + dataSach.size() + " dòng",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải dữ liệu: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private JPanel createSectionPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font(tenFont, Font.BOLD, 15));
        lblTitle.setForeground(new Color(73, 80, 87));
        panel.add(lblTitle, BorderLayout.NORTH);

        return panel;
    }

    private JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setRowHeight(32);
        table.setFont(new Font(tenFont, Font.PLAIN, 14));
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(222, 226, 230));
        table.setSelectionBackground(new Color(13, 110, 253, 30));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font(tenFont, Font.BOLD, 14));
        header.setBackground(new Color(233, 236, 239));
        header.setOpaque(false);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        table.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);

        return table;
    }
private static class BarChartPanel extends JPanel {

        private String chartTitle;
        private Color barColorTop;
        private Color barColorBottom;
        private String[] labels = {};
        private int[] values = {};
        private String tenFont = "Segoe UI";

        public BarChartPanel(String chartTitle, Color barColorTop, Color barColorBottom) {
            this.chartTitle = chartTitle;
            this.barColorTop = barColorTop;
            this.barColorBottom = barColorBottom;
            setBackground(Color.WHITE);
        }

        public void setData(Map<String, Integer> data) {
            List<String> labelList = new ArrayList<>(data.keySet());
            List<Integer> valueList = new ArrayList<>(data.values());

            labels = labelList.toArray(new String[0]);
            values = new int[valueList.size()];
            for (int i = 0; i < valueList.size(); i++) {
                values[i] = valueList.get(i);
            }

            revalidate();
            repaint();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(400, 300);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

            int w = getWidth();
            int h = getHeight();

            g2.setFont(new Font(tenFont, Font.BOLD, 14));
            g2.setColor(new Color(33, 37, 41));
            FontMetrics fmTitle = g2.getFontMetrics();
            int titleWidth = fmTitle.stringWidth(chartTitle);
            g2.drawString(chartTitle, (w - titleWidth) / 2, 22);

            if (labels.length == 0) {
                g2.setFont(new Font(tenFont, Font.ITALIC, 14));
                g2.setColor(new Color(173, 181, 189));
                String msg = "Nhan \"Tai Thong Ke\" de hien thi bieu do";
                int msgW = g2.getFontMetrics().stringWidth(msg);
                g2.drawString(msg, (w - msgW) / 2, h / 2);
                return;
            }

            int paddingLeft = 60;
            int paddingRight = 20;
            int paddingTop = 45;
            int paddingBottom = 80;

            int chartWidth = w - paddingLeft - paddingRight;
            int chartHeight = h - paddingTop - paddingBottom;

            if (chartWidth <= 0 || chartHeight <= 0)
                return;

            int maxVal = 0;
            for (int v : values) {
                if (v > maxVal)
                    maxVal = v;
            }
            if (maxVal == 0)
                maxVal = 1;

            g2.setFont(new Font(tenFont, Font.PLAIN, 11));
            int gridLines = 5;
            for (int i = 0; i <= gridLines; i++) {
                int y = paddingTop + chartHeight - (i * chartHeight / gridLines);
                g2.setColor(new Color(230, 230, 230));
                g2.drawLine(paddingLeft, y, paddingLeft + chartWidth, y);

                int val = (maxVal * i) / gridLines;
                String label = String.valueOf(val);
                g2.setColor(new Color(108, 117, 125));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(label, paddingLeft - fm.stringWidth(label) - 8, y + 4);
            }

            g2.setColor(new Color(180, 180, 180));
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawLine(paddingLeft, paddingTop, paddingLeft, paddingTop + chartHeight);
            g2.drawLine(paddingLeft, paddingTop + chartHeight,
                    paddingLeft + chartWidth, paddingTop + chartHeight);

            int numBars = labels.length;
            int totalBarArea = chartWidth / numBars;
            int barWidth = Math.max(12, Math.min(50, (int) (totalBarArea * 0.6)));
            int gap = totalBarArea - barWidth;

            for (int i = 0; i < numBars; i++) {
                int barHeight = (int) ((double) values[i] / maxVal * chartHeight);
                int x = paddingLeft + i * totalBarArea + gap / 2;
                int y = paddingTop + chartHeight - barHeight;

                GradientPaint gradient = new GradientPaint(
                        x, y, barColorTop,
                        x, y + barHeight, barColorBottom);
                g2.setPaint(gradient);
                g2.fillRoundRect(x, y, barWidth, barHeight, 4, 4);
                g2.setColor(barColorBottom.darker());
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(x, y, barWidth, barHeight, 4, 4);

                g2.setColor(new Color(33, 37, 41));
                g2.setFont(new Font(tenFont, Font.BOLD, 11));
                String valStr = String.valueOf(values[i]);
                FontMetrics fmVal = g2.getFontMetrics();
                int valX = x + (barWidth - fmVal.stringWidth(valStr)) / 2;
                g2.drawString(valStr, valX, y - 5);

                g2.setFont(new Font(tenFont, Font.PLAIN, 10));
                g2.setColor(new Color(73, 80, 87));

                String label = labels[i];
                if (label.length() > 15) {
                    label = label.substring(0, 12) + "...";
                }

                Graphics2D g2Copy = (Graphics2D) g2.create();
                int labelX = x + barWidth / 2;
                int labelY = paddingTop + chartHeight + 10;
                g2Copy.translate(labelX, labelY);
                g2Copy.rotate(Math.toRadians(-35));
                g2Copy.drawString(label, 0, 0);
                g2Copy.dispose();
            }
        }
    }
}
