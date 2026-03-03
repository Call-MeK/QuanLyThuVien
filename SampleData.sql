-- Dữ liệu mẫu cải tiến và chi tiết hơn cho Database QuanLyThuVien

-- 1. CONNGUOI (10 người: 5 Quản lý, 5 Độc giả)
INSERT INTO CONNGUOI (MaNguoi, HoTen, NgaySinh, TenDangNhap, MatKhau, GioiTinh, DiaChi, SoDienThoai, Email, TrangThai) VALUES
('NV00000001', N'Nguyễn Văn Quản Lý', '1985-05-12', 'admin', 'admin123', N'Nam', N'Hà Nội', '0901111111', 'admin@lib.com', N'Hoạt động'),
('NV00000002', N'Trần Thị Thủ Thư', '1990-08-22', 'thuthu1', '123456', N'Nữ', N'Hồ Chí Minh', '0902222222', 'tt1@lib.com', N'Hoạt động'),
('NV00000003', N'Lê Văn Thủ Thư', '1992-11-05', 'thuthu2', '123456', N'Nam', N'Đà Nẵng', '0903333333', 'tt2@lib.com', N'Hoạt động'),
('NV00000004', N'Phạm Thu Kho', '1988-02-14', 'thukho', '123456', N'Nữ', N'Hải Phòng', '0904444444', 'kho@lib.com', N'Hoạt động'),
('NV00000005', N'Hoàng Giám Đốc', '1980-01-01', 'director', '123456', N'Nam', N'Hà Nội', '0905555555', 'gd@lib.com', N'Hoạt động'),
('DG00000001', N'Nguyễn Học Sinh A', '2005-09-05', 'hsA', '123456', N'Nam', N'Hà Nội', '0911111111', 'hsa@gmail.com', N'Hoạt động'),
('DG00000002', N'Trần Sinh Viên B', '2001-10-15', 'svB', '123456', N'Nữ', N'Đà Nẵng', '0912222222', 'svb@gmail.com', N'Hoạt động'),
('DG00000003', N'Lê Giảng Viên C', '1975-04-25', 'gvC', '123456', N'Nam', N'Hồ Chí Minh', '0913333333', 'gvc@gmail.com', N'Hoạt động'),
('DG00000004', N'Phạm Nghiên Cứu D', '1995-12-30', 'ncD', '123456', N'Nữ', N'Hà Nội', '0914444444', 'ncd@gmail.com', N'Hoạt động'),
('DG00000005', N'Hoàng Khách E', '1999-07-07', 'khE', '123456', N'Nam', N'Hải Phòng', '0915555555', 'khe@gmail.com', N'Hoạt động');

-- 2. NGUOIQUANLY
INSERT INTO NGUOIQUANLY (MaNQL, VaiTro, IsDeleted) VALUES
('NV00000001', N'Quản trị viên', 0),
('NV00000002', N'Thủ thư', 0),
('NV00000003', N'Thủ thư', 0),
('NV00000004', N'Thủ kho', 0),
('NV00000005', N'Giám đốc', 0);

-- 3. DOCGIA
INSERT INTO DOCGIA (MaDocGia, NgayDangKi, LoaiDocGia, IsDeleted, NgayXoa) VALUES
('DG00000001', '2023-01-10', N'Học sinh', 0, NULL),
('DG00000002', '2023-02-15', N'Sinh viên', 0, NULL),
('DG00000003', '2023-03-20', N'Giảng viên', 0, NULL),
('DG00000004', '2023-04-25', N'Nghiên cứu sinh', 0, NULL),
('DG00000005', '2023-05-30', N'Khách vãng lai', 0, NULL);

-- 4. NHAXUATBAN
INSERT INTO NHAXUATBAN (MaNXB, TenNXB, DiaChi, Email, SoDienThoai) VALUES
('NXB0000001', N'NXB Trẻ', N'161B Lý Chính Thắng, Quận 3, TP.HCM', 'hopthu@nxbtre.com.vn', '02839316289'),
('NXB0000002', N'NXB Kim Đồng', N'55 Quang Trung, Hai Bà Trưng, Hà Nội', 'cskh@nxbkimdong.com.vn', '1900571595'),
('NXB0000003', N'NXB Giáo Dục', N'81 Trần Hưng Đạo, Hoàn Kiếm, Hà Nội', 'nxbgd@moet.edu.vn', '02438220801'),
('NXB0000004', N'NXB Hội Nhà Văn', N'65 Nguyễn Du, Hai Bà Trưng, Hà Nội', 'nxbhoinhavan@gmai.com', '02438224320'),
('NXB0000005', N'NXB Văn Học', N'18 Nguyễn Trường Tộ, Ba Đình, Hà Nội', 'nxbvanhoc@yahoo.com', '02437161518');

-- 5. THELOAI
INSERT INTO THELOAI (MaTheLoai, TenTheLoai) VALUES
('TL00000001', N'Văn học trong nước'),
('TL00000002', N'Văn học nước ngoài'),
('TL00000003', N'Khoa học công nghệ'),
('TL00000004', N'Lịch sử - Địa lý'),
('TL00000005', N'Giáo trình - Sách giáo khoa'),
('TL00000006', N'Kinh tế - Quản lý');

-- 6. KESACH
INSERT INTO KESACH (MaKeSach, TenKe, MaTheLoai) VALUES
('KS00000001', N'Kệ A1 - Văn học VN', 'TL00000001'),
('KS00000002', N'Kệ A2 - Văn học Nước Ngoài', 'TL00000002'),
('KS00000003', N'Kệ B1 - Khoa học CNTT', 'TL00000003'),
('KS00000004', N'Kệ B2 - Lịch sử Địa lý', 'TL00000004'),
('KS00000005', N'Kệ C1 - Giáo trình Khoa học', 'TL00000005');

-- 7. SACH
INSERT INTO SACH (MaSach, TenSach, TheLoai, MaNXB, NamXB, NgonNgu, GiaBia, IsDeleted) VALUES
('S000000001', N'Dế Mèn Phiêu Lưu Ký', 'TL00000001', 'NXB0000002', 2020, N'Tiếng Việt', 45000, 0),
('S000000002', N'Số Đỏ', 'TL00000001', 'NXB0000005', 2018, N'Tiếng Việt', 60000, 0),
('S000000003', N'Cho Tôi Xin Một Vé Đi Tuổi Thơ', 'TL00000001', 'NXB0000001', 2015, N'Tiếng Việt', 80000, 0),
('S000000004', N'Đắc Nhân Tâm', 'TL00000006', 'NXB0000001', 2021, N'Tiếng Việt', 120000, 0),
('S000000005', N'Nhà Giả Kim', 'TL00000002', 'NXB0000002', 2019, N'Tiếng Việt', 95000, 0),
('S000000006', N'Clean Code', 'TL00000003', 'NXB0000003', 2012, N'Tiếng Anh', 350000, 0),
('S000000007', N'Lịch Sử Triết Học Phương Tây', 'TL00000004', 'NXB0000003', 2016, N'Tiếng Việt', 250000, 0),
('S000000008', N'Giáo Trình C++', 'TL00000005', 'NXB0000003', 2020, N'Tiếng Việt', 110000, 0);

-- 8. TACGIA
INSERT INTO TACGIA (MaTacGia, TenTacGia, QuocTich, DiaChi, SoDienThoai, Email) VALUES
('TG00000001', N'Tô Hoài', N'Việt Nam', N'Hà Nội', '', ''),
('TG00000002', N'Vũ Trọng Phụng', N'Việt Nam', N'Hà Nội', '', ''),
('TG00000003', N'Nguyễn Nhật Ánh', N'Việt Nam', N'Quảng Nam', '', ''),
('TG00000004', N'Dale Carnegie', N'Mỹ', N'Maryville', '', ''),
('TG00000005', N'Paulo Coelho', N'Brazil', N'Rio de Janeiro', '', ''),
('TG00000006', N'Robert C. Martin', N'Mỹ', N'Illinois', '', ''),
('TG00000007', N'Bertrand Russell', N'Anh', N'Wales', '', ''),
('TG00000008', N'Phạm Văn Ất', N'Việt Nam', N'Hà Nội', '', '');

-- 9. SACH_TACGIA
INSERT INTO SACH_TACGIA (MaTacGia, MaSach) VALUES
('TG00000001', 'S000000001'),
('TG00000002', 'S000000002'),
('TG00000003', 'S000000003'),
('TG00000004', 'S000000004'),
('TG00000005', 'S000000005'),
('TG00000006', 'S000000006'),
('TG00000007', 'S000000007'),
('TG00000008', 'S000000008');

-- 10. SACHCOPY
INSERT INTO SACHCOPY (MaVach, MaSach, TenSachBanSao, TinhTrang, GhiChuTinhTrang, NgayNhap, IsDeleted, MaKeSach) VALUES
('MV000000000000000001', 'S000000001', N'Dế Mèn - Bản 1', N'Tốt', '', '2023-01-15', 0, 'KS00000001'),
('MV000000000000000002', 'S000000001', N'Dế Mèn - Bản 2', N'Tốt', '', '2023-01-15', 0, 'KS00000001'),
('MV000000000000000003', 'S000000002', N'Số Đỏ - Bản 1', N'Bình thường', N'Hơi sờn góc', '2023-02-10', 0, 'KS00000001'),
('MV000000000000000004', 'S000000003', N'Cho Tôi Xin - Bản 1', N'Tốt', '', '2023-03-05', 0, 'KS00000001'),
('MV000000000000000005', 'S000000004', N'Đắc Nhân Tâm - Bản 1', N'Cũ', N'Ố vàng', '2023-04-20', 0, 'KS00000004'),
('MV000000000000000006', 'S000000005', N'Nhà Giả Kim - Bản 1', N'Tốt', '', '2023-05-12', 0, 'KS00000002'),
('MV000000000000000007', 'S000000006', N'Clean Code - Bản 1', N'Tốt', '', '2023-06-18', 0, 'KS00000003'),
('MV000000000000000008', 'S000000008', N'C++ Đối Tượng - Bản 1', N'Bình thường', '', '2023-07-25', 0, 'KS00000005'),
('MV000000000000000009', 'S000000001', N'Dế Mèn - Bản 3', N'Hỏng', N'Mất trang 12-15', '2023-01-15', 1, 'KS00000001');

-- 11. SACHHONG
INSERT INTO SACHHONG (MaSachHong, TenSachHong, MaVach, SoLuong, NgayGhiNhan, LyDo) VALUES
(1, N'Dế Mèn - Bản 3 bị hỏng', 'MV000000000000000009', 1, '2023-11-05', N'Mất trang 12-15');

-- 12. THETHUVIEN
INSERT INTO THETHUVIEN (MaThe, TenThe, MaDocGia, NgayCap, NgayHetHan, MaNQL) VALUES
('TTV0000001', N'Thẻ Sinh A', 'DG00000001', '2023-01-12', '2024-01-12', 'NV00000002'),
('TTV0000002', N'Thẻ Viên B', 'DG00000002', '2023-02-16', '2024-02-16', 'NV00000002'),
('TTV0000003', N'Thẻ Giảng C', 'DG00000003', '2023-03-22', '2024-03-22', 'NV00000003');

-- 13. PHIEUNHAP
INSERT INTO PHIEUNHAP (MaPN, NgayNhap, TongTien, MaNXB, MaNQL) VALUES
('PN00000001', '2023-01-15', 90000, 'NXB0000002', 'NV00000004'),
('PN00000002', '2023-02-10', 60000, 'NXB0000005', 'NV00000004');

-- 14. CHITIETPHIEUNHAP
INSERT INTO CHITIETPHIEUNHAP (MaPN, MaSach, SoLuongNhap, DonGia) VALUES
('PN00000001', 'S000000001', 2, 45000),
('PN00000002', 'S000000002', 1, 60000);

-- 15. PHIEUMUON
INSERT INTO PHIEUMUON (MaPM, NgayMuon, NgayTra, HenTra, TinhTrang, MaNQL, MaThe) VALUES
('PM00000001', '2023-10-01', '2023-10-10', '2023-10-15', N'Đã trả', 'NV00000002', 'TTV0000001'),
('PM00000002', '2023-10-05', '2023-10-25', '2023-10-20', N'Đã trả quá hạn', 'NV00000003', 'TTV0000002'),
('PM00000003', '2023-11-01', NULL, '2023-11-15', N'Đang mượn', 'NV00000002', 'TTV0000003');

-- 16. CHITIETPHIEUMUON
INSERT INTO CHITIETPHIEUMUON (MaPM, MaCuonSach, TinhTrangSach) VALUES
('PM00000001', 'MV000000000000000001', N'Tốt'),
('PM00000001', 'MV000000000000000004', N'Tốt'),
('PM00000002', 'MV000000000000000006', N'Tốt'),
('PM00000003', 'MV000000000000000007', N'Tốt');

-- 17. XUPHAT
INSERT INTO XUPHAT (MaXP, LyDo, SoTien, MaPM, MaNQL, MaCuonSach) VALUES
('XP00000001', N'Trả trễ hạn', 50000, 'PM00000002', 'NV00000003', 'MV000000000000000006');
