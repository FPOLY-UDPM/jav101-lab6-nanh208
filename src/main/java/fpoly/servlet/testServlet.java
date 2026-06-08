package fpoly.servlet;

import dao.DepartmentDAO;
import entity.Department;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet({"/testdb", "/departments"})
public class testServlet extends HttpServlet {
    private DepartmentDAO dao = new DepartmentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "delete":
                deleteDepartment(req, resp);
                break;
            case "edit":
                showEditForm(req, resp);
                break;
            case "new":
                showNewForm(req, resp);
                break;
            default:
                showList(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        saveDepartment(req, resp);
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Department> list = dao.findAll();
        req.setAttribute("departments", list);
        req.getRequestDispatcher("/view_dept/department-list.jsp").forward(req, resp);
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("dept", new Department());
        req.getRequestDispatcher("/view_dept/department-form.jsp").forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Department existingDept = dao.findById(id);
        req.setAttribute("dept", existingDept);
        req.getRequestDispatcher("/view_dept/department-form.jsp").forward(req, resp);
    }

    private void saveDepartment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String description = req.getParameter("description");

        Department dept = new Department(id, name, description);

        // Kiểm tra xem phòng ban đã tồn tại chưa để quyết định insert hay update
        if (dao.findById(id) != null) {
            dao.update(dept);
        } else {
            dao.insert(dept);
        }

        resp.sendRedirect(req.getContextPath() + "/departments");
    }

    private void deleteDepartment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id != null) {
            dao.delete(id);
        }
        resp.sendRedirect(req.getContextPath() + "/departments");
    }
}
