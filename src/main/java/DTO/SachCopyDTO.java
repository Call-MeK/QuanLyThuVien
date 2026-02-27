/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Admin
 */
public class SachCopyDTO {
    private String MaVach;
    private String MaSach;
    private String TenSachBanSao;
    private String TinhTrang;
    private String GhiChuTinhTrang;
    private String NgayNhap;
    private Boolean IsDeleted;
    private String MaKeSach;

    public SachCopyDTO(String MaVach, String MaSach, String TenSachBanSao, String TinhTrang, String GhiChuTinhTrang, String NgayNhap, Boolean IsDeleted, String MaKeSach) {
        this.MaVach = MaVach;
        this.MaSach = MaSach;
        this.TenSachBanSao = TenSachBanSao;
        this.TinhTrang = TinhTrang;
        this.GhiChuTinhTrang = GhiChuTinhTrang;
        this.NgayNhap = NgayNhap;
        this.IsDeleted = IsDeleted;
        this.MaKeSach = MaKeSach;
    }

    public SachCopyDTO() {
    }

    public String getMaVach() {
        return MaVach;
    }

    public void setMaVach(String MaVach) {
        this.MaVach = MaVach;
    }

    public String getMaSach() {
        return MaSach;
    }

    public void setMaSach(String MaSach) {
        this.MaSach = MaSach;
    }

    public String getTenSachBanSao() {
        return TenSachBanSao;
    }

    public void setTenSachBanSao(String TenSachBanSao) {
        this.TenSachBanSao = TenSachBanSao;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(String TinhTrang) {
        this.TinhTrang = TinhTrang;
    }

    public String getGhiChuTinhTrang() {
        return GhiChuTinhTrang;
    }

    public void setGhiChuTinhTrang(String GhiChuTinhTrang) {
        this.GhiChuTinhTrang = GhiChuTinhTrang;
    }

    public String getNgayNhap() {
        return NgayNhap;
    }

    public void setNgayNhap(String NgayNhap) {
        this.NgayNhap = NgayNhap;
    }

    public Boolean getIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(Boolean IsDeleted) {
        this.IsDeleted = IsDeleted;
    }

    public String getMaKeSach() {
        return MaKeSach;
    }

    public void setMaKeSach(String MaKeSach) {
        this.MaKeSach = MaKeSach;
    }
    
}
