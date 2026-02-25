/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Admin
 */
public class TacGiaDTO {
    private String MaTacGia;
    private String TenTacGia;
    private String QuocTich;
    private String DiaChi;
    private String SoDienThoai;
    private String Email;

    public TacGiaDTO(String MaTacGia, String TenTacGia) {
        this.MaTacGia = MaTacGia;
        this.TenTacGia = TenTacGia;
    }

    public TacGiaDTO(String MaTacGia, String TenTacGia, String QuocTich, String DiaChi, String SoDienThoai, String Email) {
        this.MaTacGia = MaTacGia;
        this.TenTacGia = TenTacGia;
        this.QuocTich = QuocTich;
        this.DiaChi = DiaChi;
        this.SoDienThoai = SoDienThoai;
        this.Email = Email;
    }

    public String getMaTacGia() {
        return MaTacGia;
    }

    public void setMaTacGia(String MaTacGia) {
        this.MaTacGia = MaTacGia;
    }

    public String getTenTacGia() {
        return TenTacGia;
    }

    public void setTenTacGia(String TenTacGia) {
        this.TenTacGia = TenTacGia;
    }

    public String getQuocTich() {
        return QuocTich;
    }

    public void setQuocTich(String QuocTich) {
        this.QuocTich = QuocTich;
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
    
}
