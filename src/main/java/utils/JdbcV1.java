package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcV1 {

    /**
     * Thao tác dữ liệu (INSERT, UPDATE, DELETE)
     */
    public static int executeUpdate(String sql, Object... values) throws SQLException {
        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < values.length; i++) {
                statement.setObject(i + 1, values[i]);
            }
            return statement.executeUpdate();
        }
    }

    /**
     * Truy vấn dữ liệu (SELECT)
     */
    public static ResultSet executeQuery(String sql, Object... values) throws SQLException {
        Connection connection = DB.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < values.length; i++) {
            statement.setObject(i + 1, values[i]);
        }
        return statement.executeQuery();
    }
}
