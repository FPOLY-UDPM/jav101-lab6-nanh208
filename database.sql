-- Script khởi tạo cơ sở dữ liệu HRM cho SQL Server
USE master;
GO

-- Xóa DB nếu đã tồn tại và tạo mới
IF EXISTS (SELECT name FROM sys.databases WHERE name = N'HRM')
BEGIN
    ALTER DATABASE HRM SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE HRM;
END
GO

CREATE DATABASE HRM;
GO

USE HRM;
GO

-- 1. Tạo bảng Departments
CREATE TABLE Departments (
    Id VARCHAR(20) NOT NULL,
    Name NVARCHAR(50) NOT NULL,
    Description NVARCHAR(100) NULL,
    CONSTRAINT PK_Departments PRIMARY KEY (Id)
);
GO

-- 2. Tạo bảng Employees
CREATE TABLE Employees (
    Id VARCHAR(20) NOT NULL,
    [Password] VARCHAR(50) NOT NULL,
    Fullname NVARCHAR(250) NOT NULL,
    Photo VARCHAR(250) NOT NULL,
    Gender BIT NOT NULL,
    Birthday DATE NOT NULL,
    Salary FLOAT NOT NULL,
    DepartmentId VARCHAR(20) NOT NULL,
    CONSTRAINT PK_Employees PRIMARY KEY (Id),
    CONSTRAINT FK_Employees_Departments FOREIGN KEY (DepartmentId) REFERENCES Departments(Id) ON UPDATE CASCADE ON DELETE CASCADE
);
GO

-- 3. Chèn dữ liệu mẫu cho Departments
INSERT INTO Departments (Id, Name, Description) VALUES
('BGD', N'Ban giám đốc', N'Quản trị hệ thống'),
('IT', N'Phòng IT', N'Phỗ trợ kỹ thuật'),
('KD', N'Phòng Kinh doanh', N'Bán hàng'),
('KT', N'Phòng Kế toán', N'Tài chính'),
('NS', N'Phòng Nhân sự', N'Tuyển dụng');
GO

-- 4. Chèn dữ liệu mẫu cho Employees
INSERT INTO Employees (Id, [Password], Fullname, Photo, Gender, Birthday, Salary, DepartmentId) VALUES
('NV01', '123', N'Nguyễn Văn A', 'photo1.jpg', 1, '2000-01-01', 1000, 'BGD'),
('NV02', '123', N'Trần Thị B', 'photo2.jpg', 0, '2000-10-10', 1100, 'KT'),
('NV03', '123', N'Phạm Anh C', 'photo3.jpg', 1, '2001-09-09', 1000, 'KD'),
('NV04', '123', N'Hoàng Minh D', 'photo4.jpg', 1, '2001-05-05', 1050, 'IT');
GO

-- 5. Tạo Stored Procedures cho Departments (Bài 4)

-- Thêm mới
CREATE PROCEDURE spInsert(
    @Id VARCHAR(20),
    @Name NVARCHAR(50),
    @Description NVARCHAR(100)
) AS BEGIN
    INSERT INTO Departments(Id, Name, Description) VALUES(@Id, @Name, @Description)
END
GO

-- Cập nhật
CREATE PROCEDURE spUpdate(
    @Id VARCHAR(20),
    @Name NVARCHAR(50),
    @Description NVARCHAR(100)
) AS BEGIN
    UPDATE Departments SET Name=@Name, Description=@Description WHERE Id=@Id
END
GO

-- Xóa theo khóa chính
CREATE PROCEDURE spDeleteById(@Id VARCHAR(20)) AS BEGIN
    DELETE FROM Departments WHERE Id=@Id
END
GO

-- Truy vấn tất cả
CREATE PROCEDURE spSelectAll AS BEGIN
    SELECT * FROM Departments
END
GO

-- Truy vấn theo khóa chính
CREATE PROCEDURE spSelectById(@Id VARCHAR(20)) AS BEGIN
    SELECT * FROM Departments WHERE Id=@Id
END
GO
