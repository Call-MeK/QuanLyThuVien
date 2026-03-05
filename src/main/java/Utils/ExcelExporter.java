/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;
import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class ExcelExporter {
    
    public static void exportTableToExcel(JTable table, String defaultFileName) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn vị trí lưu file Excel");
        fileChooser.setSelectedFile(new File(defaultFileName + ".csv"));

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            
            // Tự động thêm đuôi .csv nếu người dùng quên gõ
            if (!file.getName().toLowerCase().endsWith(".csv")) {
                file = new File(file.getParentFile(), file.getName() + ".csv");
            }

            try (FileOutputStream fos = new FileOutputStream(file);
                 OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
                
                // LƯU Ý QUAN TRỌNG: Ghi mã BOM (Byte Order Mark) 
                // Để khi mở bằng Excel nó nhận chuẩn Tiếng Việt có dấu không bị lỗi font
                fos.write(0xEF);
                fos.write(0xBB);
                fos.write(0xBF);

                // 1. Ghi dòng tiêu đề cột
                for (int i = 0; i < table.getColumnCount(); i++) {
                    osw.write("\"" + table.getColumnName(i) + "\"");
                    if (i < table.getColumnCount() - 1) osw.write(",");
                }
                osw.write("\n");

                // 2. Ghi dữ liệu từng dòng
                for (int i = 0; i < table.getRowCount(); i++) {
                    for (int j = 0; j < table.getColumnCount(); j++) {
                        Object value = table.getValueAt(i, j);
                        // Xử lý dữ liệu rỗng và các dấu nháy kép bên trong chuỗi
                        String strVal = (value == null) ? "" : value.toString().replace("\"", "\"\"");
                        osw.write("\"" + strVal + "\"");
                        
                        if (j < table.getColumnCount() - 1) osw.write(",");
                    }
                    osw.write("\n");
                }
                
                JOptionPane.showMessageDialog(null, 
                        "Xuất Excel thành công!\nFile được lưu tại:\n" + file.getAbsolutePath(), 
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, 
                        "Lỗi khi xuất file:\n" + e.getMessage(), 
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}