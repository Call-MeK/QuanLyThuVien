/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

/**
 *
 * @author LePhuc
 */
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileOutputStream;
public class PDFExporter {

    public static void exportTableToPDF(JTable table, String title) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file PDF");
        fileChooser.setSelectedFile(new File("BaoCaoThuVien.pdf"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }

            try {
                Document document = new Document(PageSize.A4);
                PdfWriter.getInstance(document, new FileOutputStream(filePath));
                document.open();

                BaseFont bf = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font fontTitle = new Font(bf, 18, Font.BOLD, BaseColor.BLUE);
                Font fontHeader = new Font(bf, 12, Font.BOLD, BaseColor.BLACK);
                Font fontCell = new Font(bf, 12, Font.NORMAL, BaseColor.BLACK);

                // Thêm tiêu đề
                Paragraph pdfTitle = new Paragraph(title, fontTitle);
                pdfTitle.setAlignment(Element.ALIGN_CENTER);
                pdfTitle.setSpacingAfter(20); 
                document.add(pdfTitle);

                // Tạo bảng
                int columnCount = table.getColumnCount();
                PdfPTable pdfTable = new PdfPTable(columnCount);
                pdfTable.setWidthPercentage(100);

                // In cột Header
                for (int i = 0; i < columnCount; i++) {
                    PdfPCell cell = new PdfPCell(new Phrase(table.getColumnName(i), fontHeader));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    cell.setPadding(8);
                    pdfTable.addCell(cell);
                }

                // In dữ liệu
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                for (int rows = 0; rows < model.getRowCount(); rows++) {
                    for (int cols = 0; cols < columnCount; cols++) {
                        Object val = model.getValueAt(rows, cols);
                        String strVal = (val != null) ? val.toString() : "";
                        PdfPCell cell = new PdfPCell(new Phrase(strVal, fontCell));
                        cell.setPadding(5);
                        pdfTable.addCell(cell);
                    }
                }

                document.add(pdfTable);
                document.close();

                JOptionPane.showMessageDialog(null, "Xuất file PDF thành công!\nĐã lưu tại: " + filePath);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất PDF: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}
