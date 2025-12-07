<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<jsp:include page="includes/head.jsp">
    <jsp:param name="title" value="二手交易平台" />
</jsp:include>
<body>
<div class="container">
    <header>
        <h1>欢迎来到二手交易平台</h1>
        <nav>
            <a href="<%= request.getContextPath() %>/index.jsp">首页</a>
            <c:choose>
                <c:when test="${sessionScope.user != null}">
                    <a href="<%= request.getContextPath() %>/search.jsp">搜索商品</a>
                </c:when>
                <c:otherwise>
                    <a href="<%= request.getContextPath() %>/auth/login">搜索商品</a>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${sessionScope.user != null}">
                    <a href="<%= request.getContextPath() %>/items/add">发布商品</a>
                    <span>欢迎, ${sessionScope.user.username}!</span>
                    <a href="<%= request.getContextPath() %>/auth/logout">退出</a>
                </c:when>
                <c:otherwise>
                    <a href="<%= request.getContextPath() %>/auth/login">登录</a>
                    <a href="<%= request.getContextPath() %>/auth/register">注册</a>
                </c:otherwise>
            </c:choose>
        </nav>
    </header>
    
    <main>
        <div class="hero">
            <h2>买卖二手物品，环保又实惠</h2>
            <p>在这里您可以出售不需要的物品，也可以找到您需要的商品。</p>
            <c:choose>
                <c:when test="${sessionScope.user != null}">
                    <a href="<%= request.getContextPath() %>/search.jsp" class="btn">开始浏览</a>
                </c:when>
                <c:otherwise>
                    <a href="<%= request.getContextPath() %>/auth/login" class="btn">请先登录</a>
                </c:otherwise>
            </c:choose>
        </div>
        
        <section class="features">
            <div class="feature">
                <h3>_easy_ 发布商品</h3>
                <p>只需简单几步即可发布您的二手物品</p>
            </div>
            <div class="feature">
                <h3>快速搜索</h3>
                <p>通过关键词快速找到您需要的商品</p>
            </div>
            <div class="feature">
                <h3>安全交易</h3>
                <p>提供安全保障，让您放心交易</p>
            </div>
        </section>
    </main>
    
    <footer>
        <p>&copy; 2025 二手交易平台. 保留所有权利.</p>
    </footer>
</div>
</body>
</html>