package DTO;

public class ChiTietPhieuPhatDTO {
    private String MaCTPP;
    private String MaPP;
    private String MaCuonSach;
    private String LyDo;
    private String SoTien;
    private int TrangThai; // Thêm dòng này

    // Constructor 6 tham số
    public ChiTietPhieuPhatDTO(String MaCTPP, String MaPP, String MaCuonSach, String LyDo, String SoTien, int TrangThai) {
        this.MaCTPP = MaCTPP;
        this.MaPP = MaPP;
        this.MaCuonSach = MaCuonSach;
        this.LyDo = LyDo;
        this.SoTien = SoTien;
        this.TrangThai = TrangThai;
    }

    // Constructor 5 tham số (giữ lại để code cũ không lỗi)
    public ChiTietPhieuPhatDTO(String MaCTPP, String MaPP, String MaCuonSach, String LyDo, String SoTien) {
        this.MaCTPP = MaCTPP;
        this.MaPP = MaPP;
        this.MaCuonSach = MaCuonSach;
        this.LyDo = LyDo;
        this.SoTien = SoTien;
        this.TrangThai = 1; // Mặc định là 1
    }

    // --- Giữ nguyên các hàm Getter/Setter cũ, chỉ thêm 2 hàm mới dưới đây ---
    public int getTrangThai() { return TrangThai; }
    public void setTrangThai(int TrangThai) { this.TrangThai = TrangThai; }
    
 
    public String getMaCTPP() {
        return MaCTPP;
    }

    public void setMaCTPP(String MaCTPP) {
        this.MaCTPP = MaCTPP;
    }

    public String getMaPP() {
        return MaPP;
    }

    public void setMaPP(String MaPP) {
        this.MaPP = MaPP;
    }

    public String getMaCuonSach() {
        return MaCuonSach;
    }

    public void setMaCuonSach(String MaCuonSach) {
        this.MaCuonSach = MaCuonSach;
    }

    public String getLyDo() {
        return LyDo;
    }

    public void setLyDo(String LyDo) {
        this.LyDo = LyDo;
    }

    public String getSoTien() {
        return SoTien;
    }

    public void setSoTien(String SoTien) {
        this.SoTien = SoTien;
    }
}