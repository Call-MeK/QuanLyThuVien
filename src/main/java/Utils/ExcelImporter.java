package Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class ExcelImporter {
    public static void importExcelToTable(JTable table, DefaultTableModel model) {
        JFileChooser f = new JFileChooser();
        f.setDialogTitle("Chọn file Excel (.csv) để nhập dữ liệu");
        
        if (f.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = f.getSelectedFile();
            
            try (BufferedReader br = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
                String line;
                boolean isFirstLine = true;
                model.setRowCount(0);

                while ((line = br.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue; 
                    }
                    String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                    for (int i = 0; i < data.length; i++) {
                        data[i] = data[i].replaceAll("^\"|\"$", "").replace("\"\"", "\"");
                    }
                    model.addRow(data);
                }
                
                JOptionPane.showMessageDialog(null, 
                        "Đọc file Excel thành công! Vui lòng kiểm tra dữ liệu và bấm 'Save' để lưu vào Database.", 
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, 
                        "Lỗi khi đọc file Excel:\n" + e.getMessage(), 
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}