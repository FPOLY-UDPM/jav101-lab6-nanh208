package utils;

import java.sql.*;

public class DB {
    static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static String dburl = "jdbc:sqlserver://localhost:1433;databaseName=EmployeeDB;encrypt=true;trustServerCertificate=true;";
    static String username = "sa";
    static String password = "676767";

    static {
        try { // nạp driver
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    /**Mở kết nối*/
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dburl, username, password);
    }
    /**Thao tác dữ liệu*/
    public static int executeUpdate(String sql, Object... values) throws SQLException {
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall(sql)) {
            for (int i = 0; i < values.length; i++) {
                statement.setObject(i + 1, values[i]);
            }
            return statement.executeUpdate();
        }
    }

    /**Truy vấn dữ liệu*/
    public static ResultSet executeQuery(String sql, Object... values) throws SQLException {
        Connection connection = getConnection();
        CallableStatement statement = connection.prepareCall(sql);
        for (int i = 0; i < values.length; i++) {
            statement.setObject(i + 1, values[i]);
        }
        try (ResultSet rs = statement.executeQuery()) {
            // Sử dụng CachedRowSet để có thể đóng connection và statement ngay lập tức
            javax.sql.rowset.CachedRowSet crs = javax.sql.rowset.RowSetProvider.newFactory().createCachedRowSet();
            crs.populate(rs);
            return crs;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }
}
