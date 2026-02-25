/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Admin
 */
public class ChiTietPhieuNhapDTO {
    private String MaPN;
    private String MaSach;
    private int SoLuongNhap;
    private Float DonGia;

    public ChiTietPhieuNhapDTO(String MaPN, String MaSach, int SoLuongNhap, Float DonGia) {
        this.MaPN = MaPN;
        this.MaSach = MaSach;
        this.SoLuongNhap = SoLuongNhap;
        this.DonGia = DonGia;
    }

    public String getMaPN() {
        return MaPN;
    }

    public void setMaPN(String MaPN) {
        this.MaPN = MaPN;
    }

    public String getMaSach() {
        return MaSach;
    }

    public void setMaSach(String MaSach) {
        this.MaSach = MaSach;
    }

    public int getSoLuongNhap() {
        return SoLuongNhap;
    }

    public void setSoLuongNhap(int SoLuongNhap) {
        this.SoLuongNhap = SoLuongNhap;
    }

    public Float getDonGia() {
        return DonGia;
    }

    public void setDonGia(Float DonGia) {
        this.DonGia = DonGia;
    }
    
}
