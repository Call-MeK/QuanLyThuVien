package DTO;

public class TheLoaiDTO {
    private String MaTheLoai;
    private String TenTheLoai;

    public TheLoaiDTO() {
    }

    public TheLoaiDTO(String MaTheLoai, String TenTheLoai) {
        this.MaTheLoai = MaTheLoai;
        this.TenTheLoai = TenTheLoai;
    }

    public String getMaTheLoai() {
        return MaTheLoai;
    }

    public void setMaTheLoai(String MaTheLoai) {
        this.MaTheLoai = MaTheLoai;
    }

    public String getTenTheLoai() {
        return TenTheLoai;
    }

    public void setTenTheLoai(String TenTheLoai) {
        this.TenTheLoai = TenTheLoai;
    }

}
