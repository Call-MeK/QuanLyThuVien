/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Admin
 */
public class SachHongDTO {
    private int MaSachHong;
    private String TenSachHong;
    private String MaVach;
    private int SoLuong;
    private String NgayGhiNhan;
    private String LyDo;

    public SachHongDTO(int MaSachHong, String TenSachHong, String MaVach, int SoLuong, String NgayGhiNhan, String LyDo) {
        this.MaSachHong = MaSachHong;
        this.TenSachHong = TenSachHong;
        this.MaVach = MaVach;
        this.SoLuong = SoLuong;
        this.NgayGhiNhan = NgayGhiNhan;
        this.LyDo = LyDo;
    }

    public int getMaSachHong() {
        return MaSachHong;
    }

    public void setMaSachHong(int MaSachHong) {
        this.MaSachHong = MaSachHong;
    }

    public String getTenSachHong() {
        return TenSachHong;
    }

    public void setTenSachHong(String TenSachHong) {
        this.TenSachHong = TenSachHong;
    }

    public String getMaVach() {
        return MaVach;
    }

    public void setMaVach(String MaVach) {
        this.MaVach = MaVach;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public String getNgayGhiNhan() {
        return NgayGhiNhan;
    }

    public void setNgayGhiNhan(String NgayGhiNhan) {
        this.NgayGhiNhan = NgayGhiNhan;
    }

    public String getLyDo() {
        return LyDo;
    }

    public void setLyDo(String LyDo) {
        this.LyDo = LyDo;
    }
    
}
