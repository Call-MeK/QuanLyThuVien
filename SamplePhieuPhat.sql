-- CHẠY SCRIPT NÀY TRONG DATABASE QUANLYTHUVIEN ĐỂ TẠO BẢNG

-- 1. TẠO BẢNG PHIEUPHAT (Phiếu phạt tổng)
CREATE TABLE PHIEUPHAT
(
    MaPP char(10) PRIMARY KEY,
    MaPM char(10) NOT NULL,
    MaNQL char(10) NOT NULL,
    NgayLap date NOT NULL,
    TongTien decimal(10, 2) NOT NULL,
    TrangThai bit DEFAULT 1,
    -- 1 là tồn tại, 0 là đã xóa mềm

    FOREIGN KEY (MaPM) REFERENCES PHIEUMUON(MaPM),
    FOREIGN KEY (MaNQL) REFERENCES NGUOIQUANLY(MaNQL)
);
GO

-- 2. TẠO BẢNG CHITIETPHIEUPHAT (Chi tiết từng lỗi phạt cho 1 phiếu)
CREATE TABLE CHITIETPHIEUPHAT
(
    MaCTPP char(10) PRIMARY KEY,
    MaPP char(10) NOT NULL,
    MaCuonSach char(20) NOT NULL,
    LyDo nvarchar(300) NOT NULL,
    SoTien decimal(10, 2) NOT NULL,
    TrangThai bit DEFAULT 1,
    -- 1 là tồn tại, 0 là đã xóa mềm

    FOREIGN KEY (MaPP) REFERENCES PHIEUPHAT(MaPP),
    FOREIGN KEY (MaCuonSach) REFERENCES SACHCOPY(MaVach)
);
GO

-- ==========================================
-- SAU KHI TẠO XONG BẢNG, CHẠY PHẦN DỮ LIỆU BÊN DƯỚI
-- ==========================================

-- 3. Thêm dữ liệu mẫu vào bảng PHIEUPHAT
-- Lưu ý: Các MaPM và MaNQL được tham chiếu từ dữ liệu mẫu đã có trong hệ thống
INSERT INTO PHIEUPHAT
    (MaPP, MaPM, MaNQL, NgayLap, TongTien, TrangThai)
VALUES
    ('PP00000001', 'PM00000002', 'NV00000003', '2023-10-25', 50000.00, 1),
    ('PP00000002', 'PM00000001', 'NV00000002', '2023-10-10', 100000.00, 1),
    ('PP00000003', 'PM00000003', 'NV00000002', '2023-11-20', 150000.00, 1);
GO

-- 4. Thêm dữ liệu mẫu vào bảng CHITIETPHIEUPHAT
-- Tham chiếu đến các cuốn sách (MaVach) tương ứng trong từng phiếu mượn
INSERT INTO CHITIETPHIEUPHAT
    (MaCTPP, MaPP, MaCuonSach, LyDo, SoTien, TrangThai)
VALUES
    -- Chi tiết cho phiếu phạt PP00000001 (Trả trễ hạn)
    ('CTPP000001', 'PP00000001', 'MV000000000000000006', N'Trễ hạn 5 ngày', 50000.00, 1),

    -- Chi tiết cho phiếu phạt PP00000002 (Làm hỏng sách)
    ('CTPP000002', 'PP00000002', 'MV000000000000000001', N'Làm rách bìa sách', 50000.00, 1),
    ('CTPP000003', 'PP00000002', 'MV000000000000000004', N'Làm bẩn nhiều trang', 50000.00, 1),

    -- Chi tiết cho phiếu phạt PP00000003 (Làm mất sách & trễ hạn)
    ('CTPP000004', 'PP00000003', 'MV000000000000000007', N'Làm mất sách', 100000.00, 1),
    ('CTPP000005', 'PP00000003', 'MV000000000000000007', N'Trễ hạn trả sách (phạt thêm)', 50000.00, 1);
GO
