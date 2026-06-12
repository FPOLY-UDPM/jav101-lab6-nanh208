package dao;

import entity.Department;
import utils.JdbcV3;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {

    // Câu lệnh call stored procedure (JDBC V3)
    private String callSELECT = "{call spSelectAll}";
    private String callSELECT_byId = "{call spSelectById(?)}";
    private String callINSERT = "{call spInsert(?,?,?)}";
    private String callUPDATE = "{call spUpdate(?,?,?)}";
    private String callDELETE_byId = "{call spDeleteById(?)}";

    public List<Department> findAll() {
        List<Department> list = new ArrayList<>();
        try (ResultSet resultSet = JdbcV3.executeQuery(callSELECT)) {
            while (resultSet.next()) {
                list.add(new Department(
                        resultSet.getString("Id"),
                        resultSet.getString("Name"),
                        resultSet.getString("Description")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Department findById(String id) {
        try (ResultSet resultSet = JdbcV3.executeQuery(callSELECT_byId, id)) {
            if (resultSet.next()) {
                return new Department(
                        resultSet.getString("Id"),
                        resultSet.getString("Name"),
                        resultSet.getString("Description")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int insert(Department dept) {
        try {
            return JdbcV3.executeUpdate(callINSERT, dept.getId(), dept.getName(), dept.getDescription());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int update(Department dept) {
        try {
            return JdbcV3.executeUpdate(callUPDATE, dept.getId(), dept.getName(), dept.getDescription());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int delete(String id) {
        try {
            return JdbcV3.executeUpdate(callDELETE_byId, id);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Phương thức findByName (sử dụng Raw SQL vì không có SP trong database.sql)
    public List<Department> findByName(String name) {
        String sql = "SELECT * FROM Departments WHERE Name LIKE ?";
        List<Department> list = new ArrayList<>();
        try (ResultSet resultSet = JdbcV3.executeQuery(sql, "%" + name + "%")) {
            while (resultSet.next()) {
                list.add(new Department(
                        resultSet.getString("Id"),
                        resultSet.getString("Name"),
                        resultSet.getString("Description")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
