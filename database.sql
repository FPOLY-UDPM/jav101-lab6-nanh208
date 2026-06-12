-- Script khởi tạo cơ sở dữ liệu EmployeeDB cho SQL Server
USE master;
GO

-- Xóa DB nếu đã tồn tại và tạo mới
IF EXISTS (SELECT name FROM sys.databases WHERE name = N'EmployeeDB')
BEGIN
    ALTER DATABASE EmployeeDB SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE EmployeeDB;
END
GO

CREATE DATABASE EmployeeDB;
GO

USE EmployeeDB;
GO

SET NOCOUNT ON;

-- Create Departments table
IF OBJECT_ID(N'dbo.Departments', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.Departments (
        Id CHAR(3) NOT NULL, -- Mã phòng
        Name NVARCHAR(250) NOT NULL, -- Tên phòng
        Description NVARCHAR(250) NULL, -- Mô tả phòng
        CONSTRAINT PK_Departments PRIMARY KEY (Id)
    );
END

-- Seed Departments
INSERT INTO dbo.Departments (Id, Name, Description) VALUES
(N'BGD', N'Ban giám đốc', NULL),
(N'IT ', N'Phòng IT', N'Phòng IT'),
(N'KD ', N'Phòng Kinh doanh', NULL),
(N'KT ', N'Phòng Kế toán', NULL),
(N'NS ', N'Phòng Nhân sự', NULL);

-- Create Employees table
IF OBJECT_ID(N'dbo.Employees', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.Employees (
        Id VARCHAR(20) NOT NULL,
        [Password] VARCHAR(50) NOT NULL,
        Fullname NVARCHAR(250) NOT NULL,
        Photo VARCHAR(250) NOT NULL,
        Gender BIT NOT NULL,
        Birthday DATE NOT NULL,
        Salary FLOAT NOT NULL,
        DepartmentId CHAR(3) NOT NULL,
        CONSTRAINT PK_Employees PRIMARY KEY (Id)
    );
END

-- Seed Employees
INSERT INTO dbo.Employees (Id, [Password], Fullname, Photo, Gender, Birthday, Salary, DepartmentId) VALUES
(N'NV01', N'123', N'Nguyễn Văn A', N'photo A', 1, '2000-01-01', 100, N'BGD'),
(N'NV02', N'123', N'Trần Thị B', N'photo B', 0, '2000-10-10', 110, N'KT '),
(N'NV03', N'123', N'Phạm Anh C', N'photo C', 1, '2001-09-09', 100, N'KD '),
(N'NV04', N'123', N'Hoàng Minh D', N'photo D', 1, '2001-05-05', 105, N'IT ');

-- Add foreign key
ALTER TABLE dbo.Employees
ADD CONSTRAINT FK_Employees_Departments FOREIGN KEY (DepartmentId)
REFERENCES dbo.Departments(Id)
ON UPDATE CASCADE
ON DELETE CASCADE;
GO

-- Create Stored Procedures
CREATE PROCEDURE dbo.spSelectAll AS
BEGIN
    SELECT Id, Name, Description FROM dbo.Departments;
END
GO

CREATE PROCEDURE dbo.spSelectById @Id VARCHAR(20) AS
BEGIN
    SELECT Id, Name, Description FROM dbo.Departments WHERE Id = @Id;
END
GO

CREATE PROCEDURE dbo.spInsert
    @Id VARCHAR(20), @Name NVARCHAR(50), @Description NVARCHAR(100)
AS
BEGIN
    INSERT INTO dbo.Departments (Id, Name, Description) VALUES (@Id, @Name, @Description);
END
GO

CREATE PROCEDURE dbo.spUpdate
    @Id VARCHAR(20), @Name NVARCHAR(50), @Description NVARCHAR(100)
AS
BEGIN
    UPDATE dbo.Departments SET Name = @Name, Description = @Description WHERE Id = @Id;
END
GO

CREATE PROCEDURE dbo.spDeleteById @Id VARCHAR(20) AS
BEGIN
    DELETE FROM dbo.Departments WHERE Id = @Id;
END
GO
