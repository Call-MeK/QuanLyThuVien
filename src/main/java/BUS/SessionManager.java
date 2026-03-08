package BUS;

public class SessionManager {
    private static SessionManager instance;

    private String maNguoi;
    private String hoTen;
    private String vaiTro;

    private SessionManager() {
    }

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

    public String getMaNguoi() {
        return maNguoi;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getVaiTro() {
        return vaiTro;
    }
}