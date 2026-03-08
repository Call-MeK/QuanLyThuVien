package DTO;

public class PhieuNhapDTO {
    private String MaPN;
    private String NgayNhap;
    private Float TongTien;
    private String MaNXB;
    private String MaNQL;

    public PhieuNhapDTO(String MaPN, String NgayNhap, Float TongTien, String MaNXB, String MaNQL) {
        this.MaPN = MaPN;
        this.NgayNhap = NgayNhap;
        this.TongTien = TongTien;
        this.MaNXB = MaNXB;
        this.MaNQL = MaNQL;
    }

    public String getMaPN() {
        return MaPN;
    }

    public PhieuNhapDTO() {
    }

    public void setMaPN(String MaPN) {
        this.MaPN = MaPN;
    }

    public String getNgayNhap() {
        return NgayNhap;
    }

    public void setNgayNhap(String NgayNhap) {
        this.NgayNhap = NgayNhap;
    }

    public Float getTongTien() {
        return TongTien;
    }

    public void setTongTien(Float TongTien) {
        this.TongTien = TongTien;
    }

    public String getMaNXB() {
        return MaNXB;
    }

    public void setMaNXB(String MaNXB) {
        this.MaNXB = MaNXB;
    }

    public String getMaNQL() {
        return MaNQL;
    }

    public void setMaNQL(String MaNQL) {
        this.MaNQL = MaNQL;
    }

}
