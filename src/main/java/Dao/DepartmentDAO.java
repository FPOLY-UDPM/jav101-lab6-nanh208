package dao;

import entity.Department;
import utils.JdbcV1;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {

    public List<Department> findAll() {
        String sql = "SELECT * FROM Departments";
        List<Department> list = new ArrayList<>();
        try (ResultSet rs = JdbcV1.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Department(
                        rs.getString("Id"),
                        rs.getString("Name"),
                        rs.getString("Description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Department findById(String id) {
        String sql = "SELECT * FROM Departments WHERE Id = ?";
        try (ResultSet rs = JdbcV1.executeQuery(sql, id)) {
            if (rs.next()) {
                return new Department(
                        rs.getString("Id"),
                        rs.getString("Name"),
                        rs.getString("Description")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert(Department dept) {
        String sql = "INSERT INTO Departments (Id, Name, Description) VALUES (?, ?, ?)";
        try {
            JdbcV1.executeUpdate(sql, dept.getId(), dept.getName(), dept.getDescription());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Department dept) {
        String sql = "UPDATE Departments SET Name = ?, Description = ? WHERE Id = ?";
        try {
            JdbcV1.executeUpdate(sql, dept.getName(), dept.getDescription(), dept.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM Departments WHERE Id = ?";
        try {
            JdbcV1.executeUpdate(sql, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
