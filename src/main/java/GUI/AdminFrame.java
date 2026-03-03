package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminFrame extends JFrame {

    // Khai báo các thành phần chính
    private JPanel panelMenu;      // Cột bên trái chứa các nút
    private JPanel panelContent;   // Vùng bên phải hiển thị nội dung
    private CardLayout cardLayout; // Layout để chuyển đổi giữa các màn hình

    public AdminFrame() {
        // 1. CẤU HÌNH FRAME CƠ BẢN
        setTitle("Quản Lý Thư Viện - Admin");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Hiển thị ở giữa màn hình
        setLayout(new BorderLayout()); // Chia frame thành các vùng (Đông, Tây, Nam, Bắc, Trung Tâm)

        // 2. TẠO CỘT MENU (BÊN TRÁI - WEST)
        panelMenu = new JPanel();
        // Sử dụng GridLayout để chia đều các nút từ trên xuống dưới một cách đơn giản nhất
        panelMenu.setLayout(new GridLayout(8, 1, 5, 5)); 
        panelMenu.setPreferredSize(new Dimension(200, 600));
        panelMenu.setBackground(Color.LIGHT_GRAY);

        // Khai báo các nút chức năng theo cấu trúc thư mục đồ án
        JButton btnQuanLySach = new JButton("Quản Lý Sách");
        JButton btnQuanLyDocGia = new JButton("Quản Lý Độc Giả");
        JButton btnQuanLyMuonTra = new JButton("Quản Lý Mượn Trả");
        JButton btnQuanLyNhapSach = new JButton("Quản Lý Nhập Sách");
        JButton btnQuanLyPhiPhat = new JButton("Quản Lý Phí Phạt");
        JButton btnDangXuat = new JButton("Đăng Xuất");

        // Thêm các thành phần vào Menu
        panelMenu.add(new JLabel("=== MENU ADMIN ===", SwingConstants.CENTER));
        panelMenu.add(btnQuanLySach);
        panelMenu.add(btnQuanLyDocGia);
        panelMenu.add(btnQuanLyMuonTra);
        panelMenu.add(btnQuanLyNhapSach);
        panelMenu.add(btnQuanLyPhiPhat);
        panelMenu.add(new JLabel("")); // Khoảng trống cho đẹp mắt
        panelMenu.add(btnDangXuat);

        add(panelMenu, BorderLayout.WEST); // Gắn Menu vào phía Tây của Frame

        // 3. TẠO VÙNG NỘI DUNG (Ở GIỮA - CENTER) DÙNG CARDLAYOUT
        cardLayout = new CardLayout();
        panelContent = new JPanel(cardLayout);

        // Tạo các Panel đại diện cho từng chức năng (sau này bạn sẽ thay bằng các Panel thật)
        JPanel pnlSach = createSimplePanel("Giao Diện Quản Lý Sách", Color.PINK);
        JPanel pnlDocGia = createSimplePanel("Giao Diện Quản Lý Độc Giả", Color.CYAN);
        JPanel pnlMuonTra = createSimplePanel("Giao Diện Quản Lý Mượn Trả", Color.ORANGE);
        JPanel pnlNhapSach = createSimplePanel("Giao Diện Quản Lý Nhập Sách", Color.YELLOW);
        JPanel pnlPhiPhat = createSimplePanel("Giao Diện Quản Lý Phí Phạt", Color.GREEN);

        // Đưa các Panel vào CardLayout kèm theo một "Từ khóa" (Tên thẻ)
        panelContent.add(pnlSach, "CardSach");
        panelContent.add(pnlDocGia, "CardDocGia");
        panelContent.add(pnlMuonTra, "CardMuonTra");
        panelContent.add(pnlNhapSach, "CardNhapSach");
        panelContent.add(pnlPhiPhat, "CardPhiPhat");

        add(panelContent, BorderLayout.CENTER); // Gắn vùng nội dung vào Trung Tâm

        // 4. BẮT SỰ KIỆN CHUYỂN TRANG BẰNG ACTION LISTENER TRUYỀN THỐNG
        btnQuanLySach.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelContent, "CardSach");
            }
        });

        btnQuanLyDocGia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelContent, "CardDocGia");
            }
        });

        btnQuanLyMuonTra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelContent, "CardMuonTra");
            }
        });

        btnQuanLyNhapSach.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelContent, "CardNhapSach");
            }
        });

        btnQuanLyPhiPhat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelContent, "CardPhiPhat");
            }
        });

        btnDangXuat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(AdminFrame.this, 
                        "Bạn có chắc chắn muốn đăng xuất?", "Đăng xuất", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    dispose(); // Đóng cửa sổ hiện tại
                    // Gọi màn hình đăng nhập lên lại ở đây:
                    // new LoginFrame().setVisible(true);
                }
            }
        });

        // Đặt màn hình mặc định khi vừa mở form lên
        cardLayout.show(panelContent, "CardSach");
    }

    // --- HÀM HỖ TRỢ: TẠO NHANH MỘT PANEL ĐỂ TEST ---
    // (Sau này bạn tạo các class như QuanLySachPanel.java thì thay thế hàm này)
    private JPanel createSimplePanel(String title, Color bgColor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(bgColor);
        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(lblTitle, BorderLayout.CENTER);
        return panel;
    }

    // --- HÀM MAIN ĐỂ CHẠY CHƯƠNG TRÌNH ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AdminFrame frame = new AdminFrame();
                frame.setVisible(true);
            }
        });
    }
}