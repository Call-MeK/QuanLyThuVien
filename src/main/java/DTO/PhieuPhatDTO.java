package DTO;

public class PhieuPhatDTO {
    private String MaPP;
    private String MaPM;
    private String MaNQL;
    private String NgayLap;
    private String TongTien;
    private int TrangThai; // Thêm biến trạng thái để quản lý xóa mềm (1: Hiện, 0: Ẩn)

    // 1. Constructor đầy đủ 6 tham số
    public PhieuPhatDTO(String MaPP, String MaPM, String MaNQL, String NgayLap, String TongTien, int TrangThai) {
        this.MaPP = MaPP;
        this.MaPM = MaPM;
        this.MaNQL = MaNQL;
        this.NgayLap = NgayLap;
        this.TongTien = TongTien;
        this.TrangThai = TrangThai;
    }

    // 2. Constructor 5 tham số (Giữ lại để không bị lỗi code bên DAO đã viết)
    public PhieuPhatDTO(String MaPP, String MaPM, String MaNQL, String NgayLap, String TongTien) {
        this.MaPP = MaPP;
        this.MaPM = MaPM;
        this.MaNQL = MaNQL;
        this.NgayLap = NgayLap;
        this.TongTien = TongTien;
        this.TrangThai = 1; // Mặc định khi tạo đối tượng mới là 1 (Tồn tại)
    }

    // --- Các hàm Getter & Setter ---

    public String getMaPP() {
        return MaPP;
    }

    public void setMaPP(String MaPP) {
        this.MaPP = MaPP;
    }

    public String getMaPM() {
        return MaPM;
    }

    public void setMaPM(String MaPM) {
        this.MaPM = MaPM;
    }

    public String getMaNQL() {
        return MaNQL;
    }

    public void setMaNQL(String MaNQL) {
        this.MaNQL = MaNQL;
    }

    public String getNgayLap() {
        return NgayLap;
    }

    public void setNgayLap(String NgayLap) {
        this.NgayLap = NgayLap;
    }

    public String getTongTien() {
        return TongTien;
    }

    public void setTongTien(String TongTien) {
        this.TongTien = TongTien;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }
}