/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Admin
 */
public class XuPhatDTO {
    private String MaXP;
    private String LyDo;
    private Float SoTien;
    private String MaPM;
    private String MaNQL;
    private String MaCuonSach;

    public XuPhatDTO(String MaXP, String LyDo, Float SoTien, String MaPM, String MaNQL, String MaCuonSach) {
        this.MaXP = MaXP;
        this.LyDo = LyDo;
        this.SoTien = SoTien;
        this.MaPM = MaPM;
        this.MaNQL = MaNQL;
        this.MaCuonSach = MaCuonSach;
    }

    public String getMaXP() {
        return MaXP;
    }

    public void setMaXP(String MaXP) {
        this.MaXP = MaXP;
    }

    public String getLyDo() {
        return LyDo;
    }

    public void setLyDo(String LyDo) {
        this.LyDo = LyDo;
    }

    public Float getSoTien() {
        return SoTien;
    }

    public void setSoTien(Float SoTien) {
        this.SoTien = SoTien;
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

    public String getMaCuonSach() {
        return MaCuonSach;
    }

    public void setMaCuonSach(String MaCuonSach) {
        this.MaCuonSach = MaCuonSach;
    }
    
}
