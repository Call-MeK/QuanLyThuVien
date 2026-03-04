USE [QLSinhVien]
GO

-- Sửa thủ tục lấy dữ liệu
ALTER PROC [dbo].[loadDB]
as
begin
    -- Cột NameDep lấy từ bảng Khoa để hiển thị tên khoa thay vì mã khoa
    select Id, name, mark, Khoa.NameDep 
    from SinhVien, Khoa 
    where Khoa.IdDep = SinhVien.IdDep
end
GO

-- Sửa thủ tục xóa
ALTER PROC [dbo].[deleteDB] @Id VARCHAR(10)
as
begin
    delete from SinhVien where Id = @Id
end
GO