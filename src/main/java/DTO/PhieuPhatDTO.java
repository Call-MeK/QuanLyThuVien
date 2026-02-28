package DTO;

public class PhieuPhatDTO {
    private String MaPP;
    private String MaPM;
    private String MaNQL;
    private String NgayLap;
    private String TongTien;

    public PhieuPhatDTO(String MaPP, String MaPM, String MaNQL, String NgayLap, String TongTien) {
        this.MaPP = MaPP;
        this.MaPM = MaPM;
        this.MaNQL = MaNQL;
        this.NgayLap = NgayLap;
        this.TongTien = TongTien;
    }

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
}