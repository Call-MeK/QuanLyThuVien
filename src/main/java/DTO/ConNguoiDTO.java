/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Admin
 */
public class ConNguoiDTO {
    private String MaNguoi;
    private String HoTen;
    private String NgaySinh;
    private String TenDangNhap;
    private String MatKhau;
    private String GioiTinh;
    private String DiaChi;
    private String SoDienThoai;
    private String Email;
    private String TrangThai;

    public ConNguoiDTO(String MaNguoi, String HoTen, String NgaySinh, String TenDangNhap, String MatKhau, String GioiTinh, String DiaChi, String SoDienThoai, String Email, String TrangThai) {
        this.MaNguoi = MaNguoi;
        this.HoTen = HoTen;
        this.NgaySinh = NgaySinh;
        this.TenDangNhap = TenDangNhap;
        this.MatKhau = MatKhau;
        this.GioiTinh = GioiTinh;
        this.DiaChi = DiaChi;
        this.SoDienThoai = SoDienThoai;
        this.Email = Email;
        this.TrangThai = TrangThai;
    }

    public String getMaNguoi() {
        return MaNguoi;
    }

    public void setMaNguoi(String MaNguoi) {
        this.MaNguoi = MaNguoi;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public String getTenDangNhap() {
        return TenDangNhap;
    }

    public void setTenDangNhap(String TenDangNhap) {
        this.TenDangNhap = TenDangNhap;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String SoDienThoai) {
        this.SoDienThoai = SoDienThoai;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }
    
}
