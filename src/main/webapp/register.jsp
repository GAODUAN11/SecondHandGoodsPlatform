<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<jsp:include page="includes/head.jsp">
    <jsp:param name="title" value="用户注册 - 二手交易平台" />
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
            <h2>用户注册</h2>
            
            <c:if test="${param.error != null}">
                <div class="error-message">
                    <c:choose>
                        <c:when test="${param.error == 'missing_fields'}">
                            请填写所有字段
                        </c:when>
                        <c:when test="${param.error == 'user_exists'}">
                            用户名已存在
                        </c:when>
                        <c:otherwise>
                            注册失败
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:if>
            
            <form action="<%= request.getContextPath() %>/auth/register" method="post">
                <div class="form-group">
                    <label for="username">用户名:</label>
                    <input type="text" id="username" name="username" required>
                </div>
                
                <div class="form-group">
                    <label for="password">密码:</label>
                    <input type="password" id="password" name="password" required>
                </div>
                
                <div class="form-group">
                    <label for="email">邮箱:</label>
                    <input type="email" id="email" name="email" required>
                </div>
                
                <button type="submit" class="btn">注册</button>
            </form>
            
            <p>已有账户? <a href="<%= request.getContextPath() %>/auth/login">立即登录</a></p>
        </div>
    </main>

    <footer>
        <p>&copy; 2025 二手交易平台. 保留所有权利.</p>
    </footer>
</div>
</body>
</html>