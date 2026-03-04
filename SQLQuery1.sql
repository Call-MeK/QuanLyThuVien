CREATE PROCEDURE loadDB
AS
BEGIN
    SELECT 
        SinhVien.Id, 
        SinhVien.Name, 
        SinhVien.Mark, 
        Khoa.NameDep
    FROM SinhVien
    INNER JOIN Khoa ON Khoa.IdDep = SinhVien.IdDep;
END;