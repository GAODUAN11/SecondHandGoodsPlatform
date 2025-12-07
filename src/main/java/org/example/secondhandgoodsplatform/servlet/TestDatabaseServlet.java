package org.example.secondhandgoodsplatform.servlet;

import org.example.secondhandgoodsplatform.dao.DBUtil;
import org.example.secondhandgoodsplatform.model.Item;
import org.example.secondhandgoodsplatform.service.ItemService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/test-db")
public class TestDatabaseServlet extends HttpServlet {
    private ItemService itemService = new ItemService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h2>数据库连接测试</h2>");

        // 测试数据库连接
        try {
            out.println("<h3>测试数据库连接...</h3>");
            Connection conn = DBUtil.getConnection();
            out.println("<p style='color:green;'>数据库连接成功!</p>");
            conn.close();
        } catch (SQLException e) {
            out.println("<p style='color:red;'>数据库连接失败: " + e.getMessage() + "</p>");
            e.printStackTrace();
        }

        // 测试查询商品列表
        try {
            out.println("<h3>测试查询商品列表...</h3>");
            List<Item> items = itemService.getAllItems();
            out.println("<p>查询到 " + items.size() + " 个商品</p>");
            for (Item item : items) {
                out.println("<p>ID: " + item.getId() + ", 名称: " + item.getName() + ", 价格: " + item.getPrice() + "</p>");
            }
        } catch (Exception e) {
            out.println("<p style='color:red;'>查询商品列表失败: " + e.getMessage() + "</p>");
            e.printStackTrace();
        }

        out.println("</body></html>");
    }
}