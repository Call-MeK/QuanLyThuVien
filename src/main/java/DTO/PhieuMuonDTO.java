/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Admin
 */
public class PhieuMuonDTO {
    private String MaPM;
    private String NgayMuon;
    private String NgayTra;
    private String HenTra;
    private String TinhTrang;
    private String MaNQL;
    private String MaThe;

    public PhieuMuonDTO(String MaPM, String NgayMuon, String NgayTra, String HenTra, String TinhTrang, String MaNQL, String MaThe) {
        this.MaPM = MaPM;
        this.NgayMuon = NgayMuon;
        this.NgayTra = NgayTra;
        this.HenTra = HenTra;
        this.TinhTrang = TinhTrang;
        this.MaNQL = MaNQL;
        this.MaThe = MaThe;
    }

    public String getMaPM() {
        return MaPM;
    }

    public void setMaPM(String MaPM) {
        this.MaPM = MaPM;
    }

    public String getNgayMuon() {
        return NgayMuon;
    }

    public void setNgayMuon(String NgayMuon) {
        this.NgayMuon = NgayMuon;
    }

    public String getNgayTra() {
        return NgayTra;
    }

    public void setNgayTra(String NgayTra) {
        this.NgayTra = NgayTra;
    }

    public String getHenTra() {
        return HenTra;
    }

    public void setHenTra(String HenTra) {
        this.HenTra = HenTra;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(String TinhTrang) {
        this.TinhTrang = TinhTrang;
    }

    public String getMaNQL() {
        return MaNQL;
    }

    public void setMaNQL(String MaNQL) {
        this.MaNQL = MaNQL;
    }

    public String getMaThe() {
        return MaThe;
    }

    public void setMaThe(String MaThe) {
        this.MaThe = MaThe;
    }
    
    
}
