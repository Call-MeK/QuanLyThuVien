USE QuanLyThuVien;
GO

-- 1. B?NG CONNGUOI (C?n t?o 20 ng??i ?? chia ??u 10 ??c gi? và 10 Ng??i qu?n lý)
INSERT INTO CONNGUOI (MaNguoi, HoTen, NgaySinh, TenDangNhap, MatKhau, GioiTinh, DiaChi, SoDienThoai, Email, TrangThai) VALUES
('CN001', N'Nguy?n H?i ??ng', '2004-05-12', 'haidang', '123456', N'Nam', N'TP.HCM', '0901234501', 'dang@gmail.com', N'Ho?t ??ng'),
('CN002', N'Tr?n Th? Mai', '2005-08-22', 'maitran', '123456', N'N?', N'Hà N?i', '0901234502', 'mai@gmail.com', N'Ho?t ??ng'),
('CN003', N'Lê Hoàng Nam', '2003-11-05', 'namle', '123456', N'Nam', N'?à N?ng', '0901234503', 'nam@gmail.com', N'Ho?t ??ng'),
('CN004', N'Ph?m Thu Th?o', '2004-02-14', 'thaopham', '123456', N'N?', N'H?i Phòng', '0901234504', 'thao@gmail.com', N'Ho?t ??ng'),
('CN005', N'V? ??c Anh', '2002-07-30', 'anhvu', '123456', N'Nam', N'C?n Th?', '0901234505', 'anh@gmail.com', N'Ho?t ??ng'),
('CN006', N'Hoàng Thanh Trúc', '2005-09-11', 'truchoang', '123456', N'N?', N'Hu?', '0901234506', 'truc@gmail.com', N'Ho?t ??ng'),
('CN007', N'Bùi Quang Huy', '2003-12-25', 'huybui', '123456', N'Nam', N'Ngh? An', '0901234507', 'huy@gmail.com', N'Ho?t ??ng'),
('CN008', N'??ng Th? Y?n', '2004-04-08', 'yendang', '123456', N'N?', N'Bình D??ng', '0901234508', 'yen@gmail.com', N'Ho?t ??ng'),
('CN009', N'?? Tr?ng Hi?u', '2002-10-19', 'hieudo', '123456', N'Nam', N'??ng Nai', '0901234509', 'hieu@gmail.com', N'Ho?t ??ng'),
('CN010', N'Ngô T? Uyên', '2005-01-03', 'uyenngo', '123456', N'N?', N'V?ng Tàu', '0901234510', 'uyen@gmail.com', N'Ho?t ??ng'),
('CN011', N'Lý Công U?n', '1995-05-19', 'admin01', 'admin123', N'Nam', N'TP.HCM', '0987654301', 'ql1@lib.com', N'Ho?t ??ng'),
('CN012', N'H? Xuân H??ng', '1998-08-15', 'admin02', 'admin123', N'N?', N'Hà N?i', '0987654302', 'ql2@lib.com', N'Ho?t ??ng'),
('CN013', N'Nguy?n Du', '1990-11-20', 'admin03', 'admin123', N'Nam', N'?à N?ng', '0987654303', 'ql3@lib.com', N'Ho?t ??ng'),
('CN014', N'?oàn Th? ?i?m', '1992-02-28', 'admin04', 'admin123', N'N?', N'H?i Phòng', '0987654304', 'ql4@lib.com', N'Ho?t ??ng'),
('CN015', N'Nguy?n Trãi', '1985-07-07', 'admin05', 'admin123', N'Nam', N'C?n Th?', '0987654305', 'ql5@lib.com', N'Ho?t ??ng'),
('CN016', N'Tr??ng Qu?nh Anh', '1996-09-09', 'admin06', 'admin123', N'N?', N'Hu?', '0987654306', 'ql6@lib.com', N'Ho?t ??ng'),
('CN017', N'Phan B?i Châu', '1988-12-12', 'admin07', 'admin123', N'Nam', N'Ngh? An', '0987654307', 'ql7@lib.com', N'Ho?t ??ng'),
('CN018', N'Xuân Qu?nh', '1997-04-04', 'admin08', 'admin123', N'N?', N'Bình D??ng', '0987654308', 'ql8@lib.com', N'Ho?t ??ng'),
('CN019', N'Hàn M?c T?', '1993-10-10', 'admin09', 'admin123', N'Nam', N'??ng Nai', '0987654309', 'ql9@lib.com', N'Ho?t ??ng'),
('CN020', N'Tô Hoài', '1980-01-01', 'admin10', 'admin123', N'Nam', N'V?ng Tàu', '0987654310', 'ql10@lib.com', N'Ho?t ??ng');

-- 2. B?NG THELOAI
INSERT INTO THELOAI (MaTheLoai, TenTheLoai) VALUES
('TL01', N'L?p trình C? b?n & Nâng cao'),
('TL02', N'Trí tu? nhân t?o (AI)'),
('TL03', N'C? s? d? li?u & SQL'),
('TL04', N'H? ?i?u hành Linux'),
('TL05', N'V?n h?c ???ng ??i'),
('TL06', N'Tâm lý h?c hành vi'),
('TL07', N'Phát tri?n b?n thân'),
('TL08', N'Nghiên c?u th? h? Z'),
('TL09', N'Ti?u thuy?t trinh thám'),
('TL10', N'Thi?t k? thu?t toán');

-- 3. B?NG NHAXUATBAN
INSERT INTO NHAXUATBAN (MaNXB, TenNXB, DiaChi, Email, SoDienThoai) VALUES
('NXB01', N'O Reilly Media', N'M?', 'contact@oreilly.com', '1800123456'),
('NXB02', N'Pearson', N'Anh', 'info@pearson.com', '1800654321'),
('NXB03', N'NXB Tr?', N'TP.HCM', 'nxbtre@tre.com.vn', '0283822222'),
('NXB04', N'Nhã Nam', N'Hà N?i', 'nhanam@nhanam.vn', '0243514687'),
('NXB05', N'Manning Publications', N'M?', 'support@manning.com', '1800999888'),
('NXB06', N'NXB Kim ??ng', N'Hà N?i', 'kimdong@kimdong.com', '0243943444'),
('NXB07', N'Addison-Wesley', N'M?', 'aw@aw.com', '1800111222'),
('NXB08', N'NXB T?ng H?p TP.HCM', N'TP.HCM', 'tonghop@hcm.vn', '0283822534'),
('NXB09', N'Packt Publishing', N'Anh', 'customercare@packt.com', '1800777666'),
('NXB10', N'NXB Ph? N?', N'Hà N?i', 'phunu@nxbphunu.vn', '0243971071');

-- 4. B?NG TACGIA
INSERT INTO TACGIA (MaTacGia, TenTacGia, QuocTich, DiaChi, SoDienThoai, Email) VALUES
('TG01', N'Robert C. Martin', N'M?', N'Chicago', '1234567890', 'unclebob@clean.com'),
('TG02', N'Kathy Sierra', N'M?', N'California', '0987654321', 'kathy@headfirst.com'),
('TG03', N'Stuart Russell', N'Anh', N'London', '1122334455', 'stuart@ai.com'),
('TG04', N'Martin Kleppmann', N'??c', N'Berlin', '5544332211', 'martin@data.com'),
('TG05', N'José Mauro de Vasconcelos', N'Brazil', N'Rio', '6677889900', 'jose@vas.com'),
('TG06', N'Yuval Noah Harari', N'Israel', N'Jerusalem', '0099887766', 'yuval@sapiens.com'),
('TG07', N'William Shotts', N'M?', N'New York', '1231231234', 'william@linux.com'),
('TG08', N'Aditya Bhargava', N'M?', N'Seattle', '3213214321', 'aditya@algo.com'),
('TG09', N'Nguy?n Nh?t Ánh', N'Vi?t Nam', N'TP.HCM', '0909090909', 'nna@tre.com'),
('TG10', N'Roberta Katz', N'M?', N'Stanford', '4564564567', 'rkatz@genz.com');

-- 5. B?NG DOCGIA (L?y 10 ng??i ??u tiên t? CONNGUOI)
INSERT INTO DOCGIA (MaDocGia, NgayDangKi, LoaiDocGia) VALUES
('CN001', '2025-01-05', N'Sinh viên'), ('CN002', '2025-01-06', N'Sinh viên'),
('CN003', '2025-01-07', N'Sinh viên'), ('CN004', '2025-02-10', N'Gi?ng viên'),
('CN005', '2025-02-15', N'Sinh viên'), ('CN006', '2025-03-01', N'Khách vãng lai'),
('CN007', '2025-03-20', N'Sinh viên'), ('CN008', '2025-04-05', N'Sinh viên'),
('CN009', '2025-04-12', N'Gi?ng viên'),('CN010', '2025-05-18', N'Sinh viên');

-- 6. B?NG NGUOIQUANLY (L?y 10 ng??i sau t? CONNGUOI)
INSERT INTO NGUOIQUANLY (MaNQL, VaiTro) VALUES
('CN011', N'Th? th?'), ('CN012', N'Th? th?'),
('CN013', N'Qu?n tr? h? th?ng'), ('CN014', N'Th? th?'),
('CN015', N'Ki?m kê sách'), ('CN016', N'Th? th?'),
('CN017', N'H? tr? k? thu?t'), ('CN018', N'Th? th?'),
('CN019', N'Th? th?'), ('CN020', N'Giám ??c th? vi?n');

-- 7. B?NG KESACH
INSERT INTO KESACH (MaKeSach, TenKe, MaTheLoai) VALUES
('KS01', N'K? Java & OOP', 'TL01'), ('KS02', N'K? AI & Thu?t toán', 'TL02'),
('KS03', N'K? SQL Database', 'TL03'), ('KS04', N'K? Linux OS', 'TL04'),
('KS05', N'K? V?n h?c m?i', 'TL05'), ('KS06', N'K? Tâm lý h?c', 'TL06'),
('KS07', N'K? K? n?ng m?m', 'TL07'), ('KS08', N'K? Sách Gen Z', 'TL08'),
('KS09', N'K? Trinh thám', 'TL09'), ('KS10', N'K? C?u trúc d? li?u', 'TL10');

-- 8. B?NG SACH (Sách hi?n ??i, th?i gian g?n ?ây)
INSERT INTO SACH (MaSach, TenSach, TheLoai, MaNXB, NamXB, NgonNgu, GiaBia) VALUES
('S01', N'Clean Code: A Handbook of Agile Software Craftsmanship', 'TL01', 'NXB01', 2021, N'Ti?ng Anh', 550000),
('S02', N'Head First Java (3rd Edition)', 'TL01', 'NXB01', 2022, N'Ti?ng Anh', 600000),
('S03', N'Artificial Intelligence: A Modern Approach (4th Ed)', 'TL02', 'NXB02', 2020, N'Ti?ng Anh', 1200000),
('S04', N'Designing Data-Intensive Applications', 'TL03', 'NXB01', 2021, N'Ti?ng Anh', 750000),
('S05', N'Cây Cam Ng?t C?a Tôi (Tái b?n)', 'TL05', 'NXB04', 2022, N'Ti?ng Vi?t', 125000),
('S06', N'Sapiens: L??c S? Loài Ng??i', 'TL06', 'NXB04', 2021, N'Ti?ng Vi?t', 250000),
('S07', N'The Linux Command Line: A Complete Introduction', 'TL04', 'NXB05', 2023, N'Ti?ng Anh', 450000),
('S08', N'Grokking Algorithms', 'TL10', 'NXB05', 2024, N'Ti?ng Anh', 480000),
('S09', N'Gen Z, Explained: The Art of Living in a Digital Age', 'TL08', 'NXB07', 2022, N'Ti?ng Anh', 350000),
('S10', N'M?t Bi?c (B?n ??c bi?t)', 'TL05', 'NXB03', 2023, N'Ti?ng Vi?t', 150000);

-- 9. B?NG SACH_TACGIA
INSERT INTO SACH_TACGIA (MaSach, MaTacGia) VALUES
('S01', 'TG01'), ('S02', 'TG02'), ('S03', 'TG03'), ('S04', 'TG04'), ('S05', 'TG05'),
('S06', 'TG06'), ('S07', 'TG07'), ('S08', 'TG08'), ('S09', 'TG10'), ('S10', 'TG09');

-- 10. B?NG SACHCOPY (T?o các b?n sao c? th? c?a sách ?? m??n)
INSERT INTO SACHCOPY (MaVach, MaSach, TenSachBanSao, TinhTrang, NgayNhap, MaKeSach) VALUES
('MV001', 'S01', N'Clean Code - B?n 1', N'M?i', '2024-01-10', 'KS01'),
('MV002', 'S02', N'Head First Java - B?n 1', N'Bình th??ng', '2024-01-12', 'KS01'),
('MV003', 'S03', N'AI Modern Approach - B?n 1', N'M?i', '2024-02-15', 'KS02'),
('MV004', 'S04', N'Data-Intensive - B?n 1', N'M?i', '2024-03-20', 'KS03'),
('MV005', 'S05', N'Cây Cam Ng?t - B?n 1', N'Bình th??ng', '2024-04-05', 'KS05'),
('MV006', 'S06', N'Sapiens - B?n 1', N'C?', '2024-05-11', 'KS06'),
('MV007', 'S07', N'Linux Command Line - B?n 1', N'M?i', '2024-06-22', 'KS04'),
('MV008', 'S08', N'Grokking Algorithms - B?n 1', N'M?i', '2024-07-30', 'KS10'),
('MV009', 'S09', N'Gen Z, Explained - B?n 1', N'Bình th??ng', '2024-08-14', 'KS08'),
('MV010', 'S10', N'M?t Bi?c - B?n 1', N'C?', '2024-09-09', 'KS05');

-- 11. B?NG SACHHONG
INSERT INTO SACHHONG (TenSachHong, MaVach, SoLuong, NgayGhiNhan, LyDo) VALUES
(N'Sapiens - B?n 1', 'MV006', 1, '2025-01-15', N'Rách bìa'),
(N'M?t Bi?c - B?n 1', 'MV010', 1, '2025-02-20', N'M?t trang 15'),
(N'Head First Java - B?n 1', 'MV002', 1, '2025-03-05', N'B? ??t n??c'),
(N'Cây Cam Ng?t - B?n 1', 'MV005', 1, '2025-04-10', N'Bung gáy sách'),
(N'Sapiens - B?n 1', 'MV006', 1, '2025-05-12', N'Tr? em v? b?y'),
(N'M?t Bi?c - B?n 1', 'MV010', 1, '2025-06-18', N'Nh?n nhi?u trang'),
(N'Gen Z, Explained - B?n 1', 'MV009', 1, '2025-07-25', N'M? ch? do in ?n'),
(N'Head First Java - B?n 1', 'MV002', 1, '2025-08-30', N'M?t barcode'),
(N'Cây Cam Ng?t - B?n 1', 'MV005', 1, '2025-09-05', N'B?n bùn ??t'),
(N'Gen Z, Explained - B?n 1', 'MV009', 1, '2025-10-10', N'Rách mép gi?y');

-- 12. B?NG THETHUVIEN
INSERT INTO THETHUVIEN (MaThe, TenThe, MaDocGia, NgayCap, NgayHetHan, MaNQL) VALUES
('T01', N'Th? SV ??ng', 'CN001', '2025-01-05', '2026-01-05', 'CN011'),
('T02', N'Th? SV Mai', 'CN002', '2025-01-06', '2026-01-06', 'CN011'),
('T03', N'Th? SV Nam', 'CN003', '2025-01-07', '2026-01-07', 'CN012'),
('T04', N'Th? GV Th?o', 'CN004', '2025-02-10', '2027-02-10', 'CN012'),
('T05', N'Th? SV Anh', 'CN005', '2025-02-15', '2026-02-15', 'CN014'),
('T06', N'Th? VL Trúc', 'CN006', '2025-03-01', '2025-09-01', 'CN014'),
('T07', N'Th? SV Huy', 'CN007', '2025-03-20', '2026-03-20', 'CN016'),
('T08', N'Th? SV Y?n', 'CN008', '2025-04-05', '2026-04-05', 'CN016'),
('T09', N'Th? GV Hi?u', 'CN009', '2025-04-12', '2027-04-12', 'CN018'),
('T10', N'Th? SV Uyên', 'CN010', '2025-05-18', '2026-05-18', 'CN018');

-- 13. B?NG PHIEUNHAP
INSERT INTO PHIEUNHAP (MaPN, NgayNhap, TongTien, MaNXB, MaNQL) VALUES
('PN01', '2024-01-05', 5500000, 'NXB01', 'CN011'),
('PN02', '2024-01-10', 6000000, 'NXB01', 'CN012'),
('PN03', '2024-02-10', 12000000, 'NXB02', 'CN014'),
('PN04', '2024-03-15', 7500000, 'NXB01', 'CN016'),
('PN05', '2024-04-01', 1250000, 'NXB04', 'CN018'),
('PN06', '2024-05-05', 2500000, 'NXB04', 'CN011'),
('PN07', '2024-06-20', 4500000, 'NXB05', 'CN012'),
('PN08', '2024-07-25', 4800000, 'NXB05', 'CN014'),
('PN09', '2024-08-10', 3500000, 'NXB07', 'CN016'),
('PN10', '2024-09-05', 1500000, 'NXB03', 'CN018');

-- 14. B?NG CHITIETPHIEUNHAP
INSERT INTO CHITIETPHIEUNHAP (MaPN, MaSach, SoLuongNhap, DonGia) VALUES
('PN01', 'S01', 10, 550000), ('PN02', 'S02', 10, 600000),
('PN03', 'S03', 10, 1200000), ('PN04', 'S04', 10, 750000),
('PN05', 'S05', 10, 125000), ('PN06', 'S06', 10, 250000),
('PN07', 'S07', 10, 450000), ('PN08', 'S08', 10, 480000),
('PN09', 'S09', 10, 350000), ('PN10', 'S10', 10, 150000);

-- 15. B?NG PHIEUMUON
INSERT INTO PHIEUMUON (MaPM, NgayMuon, NgayTra, HenTra, TinhTrang, MaNQL, MaThe) VALUES
('PM01', '2025-10-01', '2025-10-15', '2025-10-14', N'?ã tr?', 'CN011', 'T01'),
('PM02', '2025-10-05', NULL, '2025-10-20', N'?ang m??n', 'CN012', 'T02'),
('PM03', '2025-10-10', '2025-10-25', '2025-10-24', N'?ã tr?', 'CN014', 'T03'),
('PM04', '2025-11-01', NULL, '2025-11-15', N'?ang m??n', 'CN016', 'T04'),
('PM05', '2025-11-05', '2025-11-25', '2025-11-19', N'Tr? h?n', 'CN018', 'T05'),
('PM06', '2025-11-15', NULL, '2025-11-29', N'?ang m??n', 'CN011', 'T06'),
('PM07', '2025-12-01', '2025-12-10', '2025-12-15', N'?ã tr?', 'CN012', 'T07'),
('PM08', '2025-12-05', '2025-12-25', '2025-12-19', N'Tr? h?n', 'CN014', 'T08'),
('PM09', '2025-12-10', NULL, '2025-12-24', N'?ang m??n', 'CN016', 'T09'),
('PM10', '2025-12-15', '2025-12-20', '2025-12-29', N'?ã tr?', 'CN018', 'T10');

-- 16. B?NG CHITIETPHIEUMUON
INSERT INTO CHITIETPHIEUMUON (MaPM, MaCuonSach, TinhTrangSach) VALUES
('PM01', 'MV001', N'Bình th??ng'), ('PM02', 'MV002', N'Bình th??ng'),
('PM03', 'MV003', N'Bình th??ng'), ('PM04', 'MV004', N'Bình th??ng'),
('PM05', 'MV005', N'Rách nh?'), ('PM06', 'MV006', N'Bình th??ng'),
('PM07', 'MV007', N'Bình th??ng'), ('PM08', 'MV008', N'Làm b?n'),
('PM09', 'MV009', N'Bình th??ng'), ('PM10', 'MV010', N'Bình th??ng');

-- 17. B?NG PHIEUPHAT (D?a trên PM05 và PM08 tr? tr?/làm h?ng)
INSERT INTO PHIEUPHAT (MaPP, MaPM, MaNQL, NgayLap, TongTien, TrangThai) VALUES
('PP01', 'PM05', 'CN018', '2025-11-25', 50000, 1),
('PP02', 'PM08', 'CN014', '2025-12-25', 100000, 0),
('PP03', 'PM05', 'CN018', '2025-11-25', 20000, 1),
('PP04', 'PM08', 'CN014', '2025-12-25', 30000, 1),
('PP05', 'PM05', 'CN018', '2025-11-25', 15000, 0),
('PP06', 'PM08', 'CN014', '2025-12-25', 45000, 1),
('PP07', 'PM05', 'CN018', '2025-11-25', 60000, 0),
('PP08', 'PM08', 'CN014', '2025-12-25', 70000, 1),
('PP09', 'PM05', 'CN018', '2025-11-25', 25000, 1),
('PP10', 'PM08', 'CN014', '2025-12-25', 85000, 0);

-- 18. B?NG CHITIETPHIEUPHAT
INSERT INTO CHITIETPHIEUPHAT (MaCTPP, MaPP, MaCuonSach, LyDo, SoTien, TrangThai) VALUES
('CTPP01', 'PP01', 'MV005', N'Tr? tr? h?n 6 ngày', 30000, 1),
('CTPP02', 'PP02', 'MV008', N'Làm b?n sách nghiêm tr?ng', 100000, 0),
('CTPP03', 'PP03', 'MV005', N'Làm gãy n?p sách', 20000, 1),
('CTPP04', 'PP04', 'MV008', N'Tr? tr? h?n 6 ngày', 30000, 1),
('CTPP05', 'PP05', 'MV005', N'Ghi chú b?y lên sách', 15000, 0),
('CTPP06', 'PP06', 'MV008', N'Làm m? trang sách', 45000, 1),
('CTPP07', 'PP07', 'MV005', N'Làm ??t góc sách', 60000, 0),
('CTPP08', 'PP08', 'MV008', N'Làm rách bìa sau', 70000, 1),
('CTPP09', 'PP09', 'MV005', N'Bóc m?t mã v?ch', 25000, 1),
('CTPP10', 'PP10', 'MV008', N'Làm r?t gáy sách', 85000, 0);

GO




