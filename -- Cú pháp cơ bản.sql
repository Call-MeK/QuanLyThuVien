-- 1. Trỏ đúng vào database QuanLyThuVien
USE QuanLyThuVien;
GO

-- 2. Thêm dữ liệu vào bảng CONNGUOI
INSERT INTO CONNGUOI (MaNguoi, HoTen, NgaySinh, TenDangNhap, MatKhau, GioiTinh, DiaChi, SoDienThoai, Email, TrangThai)
VALUES 
    -- Nhập một dòng dữ liệu mới (chú ý mã người DG00000006 để không bị trùng với dữ liệu đã có)
    ('DG00000006', N'Nguyễn Văn A', '2000-01-01', 'nva', '123456', N'Nam', N'Hà Nội', '0912345678', 'nva@gmail.com', N'Hoạt động');
GO
USE QuanLyThuVien;
GO

SELECT * FROM CONNGUOI WHERE MaNguoi = 'DG00000006';
USE QuanLyThuVien;
GO
-- =============================================
-- NẠP THÊM NHIỀU DỮ LIỆU CHO BẢNG CONNGUOI
-- =============================================
USE QuanLyThuVien;
GO

INSERT INTO CONNGUOI (MaNguoi, HoTen, NgaySinh, TenDangNhap, MatKhau, GioiTinh, DiaChi, SoDienThoai, Email, TrangThai)
VALUES 
    -- Độc giả mới
    ('DG00000007', N'Trần Thị Bích', '2001-05-15', 'tranbich', '123456', N'Nữ', N'Hồ Chí Minh', '0987654321', 'bich.tran@gmail.com', N'Hoạt động'),
    ('DG00000008', N'Lê Hoàng Tuấn', '1999-10-20', 'hoangtuan', '123456', N'Nam', N'Đà Nẵng', '0901234567', 'tuan.le@gmail.com', N'Hoạt động'),
    ('DG00000009', N'Phạm Quang Dũng', '2002-02-28', 'quangdung', '123456', N'Nam', N'Cần Thơ', '0933445566', 'dung.pham@gmail.com', N'Bị khóa'),
    ('DG00000010', N'Nguyễn Mai Anh', '2003-08-08', 'maianh', '123456', N'Nữ', N'Hải Phòng', '0911223344', 'anh.nguyen@gmail.com', N'Hoạt động'),
    
    -- Thêm 1 nhân viên thư viện mới (mã NV)
    ('NV00000006', N'Vũ Văn Trọng', '1995-12-12', 'trongvv', 'admin123', N'Nam', N'Bình Dương', '0977889900', 'trong.vu@lib.com', N'Hoạt động');
GO
