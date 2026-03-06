-- ==============================================================
-- THÊM CỘT SOLUONG VÀ SET GIÁ TRỊ TỪ 1 ĐẾN 20
-- ==============================================================
USE QuanLyThuVien;
GO

-- 1. Thêm cột SoLuong vào bảng SACH (nếu chưa có)
IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('SACH') AND name = 'SoLuong')
BEGIN
    ALTER TABLE SACH ADD SoLuong INT;
END
GO

-- 2. Cập nhật số lượng ngẫu nhiên từ 1 đến 20 cho tất cả các đầu sách
UPDATE SACH
SET SoLuong = (ABS(CHECKSUM(NEWID())) % 20) + 1;
GO

-- 3. Kiểm tra kết quả để thấy sự thay đổi "thực tế"
SELECT TOP 20 MaSach, TenSach, SoLuong 
FROM SACH 
ORDER BY MaSach;