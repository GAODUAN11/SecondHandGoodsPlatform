package org.example.secondhandgoodsplatform.servlet;

import org.example.secondhandgoodsplatform.model.Item;
import org.example.secondhandgoodsplatform.model.User;
import org.example.secondhandgoodsplatform.service.ItemService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/items/*")
public class ItemServlet extends HttpServlet {
    private ItemService itemService = new ItemService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || "/list".equals(pathInfo)) {
            listItems(request, response);
        } else if ("/search".equals(pathInfo)) {
            searchItems(request, response);
        } else if ("/view".equals(pathInfo)) {
            viewItem(request, response);
        } else if ("/add".equals(pathInfo)) {
            showAddForm(request, response);
        } else if ("/edit".equals(pathInfo)) {
            showEditForm(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if ("/add".equals(pathInfo)) {
            addItem(request, response);
        } else if ("/edit".equals(pathInfo)) {
            updateItem(request, response);
        } else if ("/delete".equals(pathInfo)) {
            deleteItem(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void listItems(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ItemServlet.listItems() called");
        List<Item> items = itemService.getAllItems();
        System.out.println("Retrieved " + items.size() + " items");
        request.setAttribute("items", items);
        request.getRequestDispatcher("/search.jsp").forward(request, response);
    }

    private void searchItems(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Item> items;
        if (keyword != null && !keyword.isEmpty()) {
            items = itemService.searchItemsByName(keyword);
        } else {
            items = itemService.getAllItems();
        }
        request.setAttribute("items", items);
        request.setAttribute("keyword", keyword);
        request.getRequestDispatcher("/search.jsp").forward(request, response);
    }

    private void viewItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                Item item = itemService.getItemById(id);
                if (item != null) {
                    request.setAttribute("item", item);
                    request.getRequestDispatcher("/item_detail.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                // Handle invalid ID format
            }
        }
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/add_item.jsp").forward(request, response);
    }

    private void addItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");

        if (name == null || description == null || priceStr == null ||
            name.isEmpty() || description.isEmpty() || priceStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/items/add?error=missing_fields");
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            boolean success = itemService.addItem(name, description, price, user.getId());
            if (success) {
                response.sendRedirect(request.getContextPath() + "/items/list?success=item_added");
            } else {
                response.sendRedirect(request.getContextPath() + "/items/add?error=add_failed");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/items/add?error=invalid_price");
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                Item item = itemService.getItemById(id);
                if (item != null) {
                    request.setAttribute("item", item);
                    request.getRequestDispatcher("/edit_item.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                // Handle invalid ID format
            }
        }
        response.sendRedirect(request.getContextPath() + "/items/list?error=item_not_found");
    }

    private void updateItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        String idParam = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");

        if (idParam == null || name == null || description == null || priceStr == null || idParam.isEmpty() || name.isEmpty() || description.isEmpty() || priceStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/items/edit?id=" + idParam + "&error=missing_fields");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            double price = Double.parseDouble(priceStr);
            boolean success = itemService.updateItem(id, name, description, price);
            if (success) {
                response.sendRedirect(request.getContextPath() + "/items/list?success=item_updated");
            } else {
                response.sendRedirect(request.getContextPath() + "/items/edit?id=" + idParam + "&error=update_failed");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/items/edit?id=" + idParam + "&error=invalid_format");
        }
    }

    private void deleteItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                boolean success = itemService.deleteItem(id);
                if (success) {
                    response.sendRedirect(request.getContextPath() + "/items/list?success=item_deleted");
                } else {
                    response.sendRedirect(request.getContextPath() + "/items/list?error=delete_failed");
                }
                return;
            } catch (NumberFormatException e) {
                // Handle invalid ID format
            }
        }
        response.sendRedirect(request.getContextPath() + "/items/list?error=invalid_id");
    }
}