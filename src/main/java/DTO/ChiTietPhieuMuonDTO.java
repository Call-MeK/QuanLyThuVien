/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Admin
 */
public class ChiTietPhieuMuonDTO {
    private String MaPM;
    private String MaCuonSach;
    private String TinhTrangSach;
    
    public ChiTietPhieuMuonDTO(String MaPM, String MaCuonSach, String TinhTrangSach) {
        this.MaPM = MaPM;
        this.MaCuonSach = MaCuonSach;
        this.TinhTrangSach = TinhTrangSach;
    }

    public String getMaPM() {
        return MaPM;
    }

    public void setMaPM(String MaPM) {
        this.MaPM = MaPM;
    }

    public String getMaCuonSach() {
        return MaCuonSach;
    }

    public void setMaCuonSach(String MaCuonSach) {
        this.MaCuonSach = MaCuonSach;
    }

    public String getTinhTrangSach() {
        return TinhTrangSach;
    }

    public void setTinhTrangSach(String TinhTrangSach) {
        this.TinhTrangSach = TinhTrangSach;
    }
    
}
