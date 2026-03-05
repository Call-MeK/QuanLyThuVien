package Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class ExcelImporter {

    public static void importExcelToTable(JTable table, DefaultTableModel model) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel (.csv) để nhập dữ liệu");
        
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            
            try (BufferedReader br = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
                String line;
                boolean isFirstLine = true;
                
                // Xóa sạch dữ liệu cũ đang hiển thị trên bảng
                model.setRowCount(0);

                while ((line = br.readLine()) != null) {
                    // Bỏ qua dòng đầu tiên (thường là dòng tiêu đề cột)
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue; 
                    }
                    
                    // Cắt dữ liệu theo dấu phẩy (chuẩn CSV của Excel)
                    // Dùng Regex này để nó không bị lỗi nếu trong nội dung có chứa dấu phẩy
                    String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                    
                    // Dọn dẹp mấy cái dấu ngoặc kép thừa do Excel tự sinh ra
                    for (int i = 0; i < data.length; i++) {
                        data[i] = data[i].replaceAll("^\"|\"$", "").replace("\"\"", "\"");
                    }
                    
                    // Thêm dòng mới vào giao diện JTable
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