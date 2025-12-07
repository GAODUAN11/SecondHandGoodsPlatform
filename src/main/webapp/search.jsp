<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<jsp:include page="includes/head.jsp">
    <jsp:param name="title" value="搜索商品 - 二手交易平台" />
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
        <h2>搜索商品</h2>
        
        <form action="<%= request.getContextPath() %>/items/search" method="get" class="search-form">
            <input type="text" name="keyword" placeholder="输入商品名称关键词" 
                   value="${param.keyword != null ? param.keyword : ''}">
            <button type="submit" class="btn">搜索</button>
        </form>
        
        <c:if test="${items != null}">
            <div class="search-results">
                <h3>搜索结果</h3>
                <c:choose>
                    <c:when test="${empty items}">
                        <p>没有找到匹配的商品</p>
                    </c:when>
                    <c:otherwise>
                        <div class="items-grid">
                            <c:forEach var="item" items="${items}">
                                <div class="item-card">
                                    <h4><a href="<%= request.getContextPath() %>/items/view?id=${item.id}">${item.name}</a></h4>
                                    <p class="price">¥${item.price}</p>
                                    <p class="description">${item.description}</p>
                                    <c:if test="${item.ownerName != null}">
                                        <p class="owner">发布者: ${item.ownerName}</p>
                                    </c:if>
                                </div>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>
        
        <!-- 当没有搜索关键词且没有items列表时，默认显示所有商品 -->
        <c:if test="${items == null}">
            <jsp:forward page="/items/list" />
        </c:if>
    </main>

    <footer>
        <p>&copy; 2025 二手交易平台. 保留所有权利.</p>
    </footer>
</div>
</body>
</html>