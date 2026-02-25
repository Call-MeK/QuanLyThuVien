/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Admin
 */
public class DocGiaDTO extends ConNguoiDTO{
    private String MaDocGia;
    private String NgayDangKi;
    private String LoaiDocGia;
    private Boolean IsDeleted;
    private String NgayXoa;

    public DocGiaDTO(String MaDocGia, String NgayDangKi, String LoaiDocGia, Boolean IsDeleted, String NgayXoa, String MaNguoi, String HoTen, String NgaySinh, String TenDangNhap, String MatKhau, String GioiTinh, String DiaChi, String SoDienThoai, String Email, String TrangThai) {
        super(MaNguoi, HoTen, NgaySinh, TenDangNhap, MatKhau, GioiTinh, DiaChi, SoDienThoai, Email, TrangThai);
        this.MaDocGia = MaDocGia;
        this.NgayDangKi = NgayDangKi;
        this.LoaiDocGia = LoaiDocGia;
        this.IsDeleted = IsDeleted;
        this.NgayXoa = NgayXoa;
    }

    public String getMaDocGia() {
        return MaDocGia;
    }

    public void setMaDocGia(String MaDocGia) {
        this.MaDocGia = MaDocGia;
    }

    public String getNgayDangKi() {
        return NgayDangKi;
    }

    public void setNgayDangKi(String NgayDangKi) {
        this.NgayDangKi = NgayDangKi;
    }

    public String getLoaiDocGia() {
        return LoaiDocGia;
    }

    public void setLoaiDocGia(String LoaiDocGia) {
        this.LoaiDocGia = LoaiDocGia;
    }

    public Boolean getIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(Boolean IsDeleted) {
        this.IsDeleted = IsDeleted;
    }

    public String getNgayXoa() {
        return NgayXoa;
    }

    public void setNgayXoa(String NgayXoa) {
        this.NgayXoa = NgayXoa;
    }
    
}
