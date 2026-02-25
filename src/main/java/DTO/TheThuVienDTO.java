/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Admin
 */
public class TheThuVienDTO {
    private String MaThe;
    private String TenThe;
    private String MaDocGia;
    private String NgayCap;
    private String NgayHetHan;
    private String MaNQL;

    public TheThuVienDTO(String MaThe, String TenThe, String MaDocGia, String NgayCap, String NgayHetHan, String MaNQL) {
        this.MaThe = MaThe;
        this.TenThe = TenThe;
        this.MaDocGia = MaDocGia;
        this.NgayCap = NgayCap;
        this.NgayHetHan = NgayHetHan;
        this.MaNQL = MaNQL;
    }

    public String getMaThe() {
        return MaThe;
    }

    public void setMaThe(String MaThe) {
        this.MaThe = MaThe;
    }

    public String getTenThe() {
        return TenThe;
    }

    public void setTenThe(String TenThe) {
        this.TenThe = TenThe;
    }

    public String getMaDocGia() {
        return MaDocGia;
    }

    public void setMaDocGia(String MaDocGia) {
        this.MaDocGia = MaDocGia;
    }

    public String getNgayCap() {
        return NgayCap;
    }

    public void setNgayCap(String NgayCap) {
        this.NgayCap = NgayCap;
    }

    public String getNgayHetHan() {
        return NgayHetHan;
    }

    public void setNgayHetHan(String NgayHetHan) {
        this.NgayHetHan = NgayHetHan;
    }

    public String getMaNQL() {
        return MaNQL;
    }

    public void setMaNQL(String MaNQL) {
        this.MaNQL = MaNQL;
    }
    
}
