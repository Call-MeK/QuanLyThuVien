package BUS;

/**
 * Lưu thông tin người đăng nhập hiện tại (Singleton).
 * Dùng được ở mọi nơi: SessionManager.getInstance().getMaNguoi()
 */
public class SessionManager {
    private static SessionManager instance;

    private String maNguoi;   // VD: NV00000001 hoặc DG00000001
    private String hoTen;     // VD: Nguyễn Văn A
    private String vaiTro;    // "Admin" hoặc "DocGia"

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void login(String maNguoi, String hoTen, String vaiTro) {
        this.maNguoi = maNguoi;
        this.hoTen = hoTen;
        this.vaiTro = vaiTro;
    }

    public void logout() {
        this.maNguoi = null;
        this.hoTen = null;
        this.vaiTro = null;
    }

    public boolean isLoggedIn() {
        return maNguoi != null && !maNguoi.isEmpty();
    }

    public String getMaNguoi() { return maNguoi; }
    public String getHoTen()   { return hoTen; }
    public String getVaiTro()  { return vaiTro; }
}