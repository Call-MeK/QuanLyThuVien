/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Admin
 */
public class NguoiQuanLyDTO extends ConNguoiDTO{
    private String MaNQL;
    private String VaiTro;
    private Boolean IsDeleted;

    public NguoiQuanLyDTO(String MaNQL, String VaiTro, Boolean IsDeleted, String MaNguoi, String HoTen, String NgaySinh, String TenDangNhap, String MatKhau, String GioiTinh, String DiaChi, String SoDienThoai, String Email, String TrangThai) {
        super(MaNguoi, HoTen, NgaySinh, TenDangNhap, MatKhau, GioiTinh, DiaChi, SoDienThoai, Email, TrangThai);
        this.MaNQL = MaNQL;
        this.VaiTro = VaiTro;
        this.IsDeleted = IsDeleted;
    }

    public String getMaNQL() {
        return MaNQL;
    }

    public void setMaNQL(String MaNQL) {
        this.MaNQL = MaNQL;
    }

    public String getVaiTro() {
        return VaiTro;
    }

    public void setVaiTro(String VaiTro) {
        this.VaiTro = VaiTro;
    }

    public Boolean getIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(Boolean IsDeleted) {
        this.IsDeleted = IsDeleted;
    }
    
}
