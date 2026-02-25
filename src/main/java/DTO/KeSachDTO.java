/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Admin
 */
public class KeSachDTO {
    private String MaKeSach;
    private String TenKe;
    private String MaTheLoai;

    public KeSachDTO(String MaKeSach, String TenKe, String MaTheLoai) {
        this.MaKeSach = MaKeSach;
        this.TenKe = TenKe;
        this.MaTheLoai = MaTheLoai;
    }

    public String getMaKeSach() {
        return MaKeSach;
    }

    public void setMaKeSach(String MaKeSach) {
        this.MaKeSach = MaKeSach;
    }

    public String getTenKe() {
        return TenKe;
    }

    public void setTenKe(String TenKe) {
        this.TenKe = TenKe;
    }

    public String getMaTheLoai() {
        return MaTheLoai;
    }

    public void setMaTheLoai(String MaTheLoai) {
        this.MaTheLoai = MaTheLoai;
    }
    
}
