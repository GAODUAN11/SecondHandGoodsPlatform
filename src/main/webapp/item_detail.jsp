<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<jsp:include page="includes/head.jsp">
    <jsp:param name="title" value="商品详情 - 二手交易平台" />
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
        <c:if test="${item != null}">
            <div class="item-detail">
                <h2>${item.name}</h2>
                <p class="price">¥${item.price}</p>
                <p class="description">${item.description}</p>
                <c:if test="${item.ownerName != null}">
                    <p class="owner">发布者: ${item.ownerName}</p>
                </c:if>
                
                <c:if test="${sessionScope.user != null && sessionScope.user.id == item.userId}">
                    <p><a href="<%= request.getContextPath() %>/items/edit?id=${item.id}" class="btn">编辑商品</a></p>
                </c:if>
            </div>
        </c:if>
        
        <c:if test="${item == null}">
            <p>未找到指定的商品</p>
        </c:if>
        
        <p><a href="<%= request.getContextPath() %>/items/list">返回商品列表</a></p>
    </main>

    <footer>
        <p>&copy; 2025 二手交易平台. 保留所有权利.</p>
    </footer>
</div>
</body>
</html>