<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<jsp:include page="includes/head.jsp">
    <jsp:param name="title" value="发布商品 - 二手交易平台" />
</jsp:include>
<body>
<div class="container">
    <header>
        <h1>二手交易平台</h1>
        <nav>
            <a href="<%= request.getContextPath() %>/index.jsp">首页</a>
            <a href="<%= request.getContextPath() %>/search.jsp">搜索商品</a>
            <c:choose>
                <c:when test="${sessionScope.user != null}">
                    <a href="<%= request.getContextPath() %>/items/add">发布商品</a>
                    <span>欢迎, ${sessionScope.user.username}!</span>
                    <a href="<%= request.getContextPath() %>/auth/logout">退出</a>
                </c:when>
                <c:otherwise>
                    <a href="<%= request.getContextPath() %>/login.jsp">登录</a>
                    <a href="<%= request.getContextPath() %>/register.jsp">注册</a>
                </c:otherwise>
            </c:choose>
        </nav>
    </header>

    <main>
        <div class="form-container">
            <h2>发布新商品</h2>
            
            <c:if test="${param.error != null}">
                <div class="error-message">
                    <c:choose>
                        <c:when test="${param.error == 'missing_fields'}">
                            请填写所有字段
                        </c:when>
                        <c:when test="${param.error == 'invalid_price'}">
                            价格格式不正确
                        </c:when>
                        <c:otherwise>
                            发布失败
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:if>
            
            <form action="<%= request.getContextPath() %>/items/add" method="post">
                <div class="form-group">
                    <label for="name">商品名称:</label>
                    <input type="text" id="name" name="name" required>
                </div>
                
                <div class="form-group">
                    <label for="description">商品描述:</label>
                    <textarea id="description" name="description" rows="4" required></textarea>
                </div>
                
                <div class="form-group">
                    <label for="price">价格 (¥):</label>
                    <input type="number" id="price" name="price" step="0.01" min="0" required>
                </div>
                
                <button type="submit" class="btn">发布商品</button>
            </form>
            
            <p><a href="<%= request.getContextPath() %>/items/list">返回商品列表</a></p>
        </div>
    </main>

    <footer>
        <p>&copy; 2025 二手交易平台. 保留所有权利.</p>
    </footer>
</div>
</body>
</html>