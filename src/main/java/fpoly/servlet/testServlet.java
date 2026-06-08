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
        String action = req.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "delete":
                deleteDepartment(req, resp);
                break;
            case "edit":
                // For this simple example, we just show the list, but we could show a form
                showList(req, resp);
                break;
            case "new":
                // For this simple example, we just show the list
                showList(req, resp);
                break;
            default:
                showList(req, resp);
                break;
        }
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Department> list = dao.findAll();
        req.setAttribute("departments", list);
        req.getRequestDispatcher("/view_dept/department-list.jsp").forward(req, resp);
    }

    private void deleteDepartment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id != null) {
            dao.delete(id);
        }
        resp.sendRedirect(req.getContextPath() + "/departments");
    }
}
