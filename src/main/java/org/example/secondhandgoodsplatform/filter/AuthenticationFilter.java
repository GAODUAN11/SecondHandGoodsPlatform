package org.example.secondhandgoodsplatform.filter;

import org.example.secondhandgoodsplatform.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    
    // 不需要登录就可以访问的路径
    private static final List<String> ALLOWED_PATHS = Arrays.asList(
        "/auth/login", 
        "/auth/register", 
        "/index.jsp",
        ""
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        
        // 检查是否是允许无需登录访问的路径
        if (isAllowedPath(path)) {
            chain.doFilter(request, response);
            return;
        }
        
        // 检查用户是否已登录
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            // 未登录用户重定向到登录页面
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/auth/login?info=login_required");
            return;
        }
        
        // 已登录用户继续处理请求
        chain.doFilter(request, response);
    }
    
    private boolean isAllowedPath(String path) {
        // 静态资源文件总是允许访问
        if (path.startsWith("/assets/")) {
            return true;
        }
        
        // 检查是否在允许的路径列表中
        for (String allowedPath : ALLOWED_PATHS) {
            if (path.equals(allowedPath)) {
                return true;
            }
        }
        
        return false;
    }
}