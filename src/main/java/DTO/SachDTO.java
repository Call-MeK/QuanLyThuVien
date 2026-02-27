/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class SachDTO {
    private String MaSach;
    private String tenSach;
    private String TheLoai;
    private String MaNXB;
    private int NamXB;
    private String NgonNgu;

    public SachDTO() {
    }
    private Float GiaBia;
    private Boolean isDeleted;
    
    private ArrayList<TacGiaDTO> DanhSachTacGia;
    
    public SachDTO(String MaSach, String tenSach) {
        this.MaSach = MaSach;
        this.tenSach = tenSach;
    }

    public SachDTO(String MaSach, String tenSach, String TheLoai, String MaNXB, int NamXB, String NgonNgu, Float GiaBia, Boolean isDeleted, ArrayList<TacGiaDTO> DanhSachTacGia) {
        this.MaSach = MaSach;
        this.tenSach = tenSach;
        this.TheLoai = TheLoai;
        this.MaNXB = MaNXB;
        this.NamXB = NamXB;
        this.NgonNgu = NgonNgu;
        this.GiaBia = GiaBia;
        this.isDeleted = isDeleted;
        this.DanhSachTacGia=DanhSachTacGia;
    }

    public String getMaSach() {
        return MaSach;
    }

    public void setMaSach(String MaSach) {
        this.MaSach = MaSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTheLoai() {
        return TheLoai;
    }

    public void setTheLoai(String TheLoai) {
        this.TheLoai = TheLoai;
    }

    public String getMaNXB() {
        return MaNXB;
    }

    public void setMaNXB(String MaNXB) {
        this.MaNXB = MaNXB;
    }

    public int getNamXB() {
        return NamXB;
    }

    public void setNamXB(int NamXB) {
        this.NamXB = NamXB;
    }

    public String getNgonNgu() {
        return NgonNgu;
    }

    public void setNgonNgu(String NgonNgu) {
        this.NgonNgu = NgonNgu;
    }

    public Float getGiaBia() {
        return GiaBia;
    }

    public void setGiaBia(Float GiaBia) {
        this.GiaBia = GiaBia;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public ArrayList<TacGiaDTO> getDanhSachTacGia() {
        return DanhSachTacGia;
    }

    public void setDanhSachTacGia(ArrayList<TacGiaDTO> DanhSachTacGia) {
        this.DanhSachTacGia = DanhSachTacGia;
    }
    
}
