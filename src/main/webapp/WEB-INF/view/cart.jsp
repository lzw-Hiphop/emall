<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>EMall</title>
    <link href="../../css/bootstrap.min.css" rel="stylesheet">
    <script src="../../js/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="../../js/jquery.min.js"><\/script>')</script>
    <script src="../../js/bootstrap.min.js"></script>
    <style>
        body {
            background: #eee
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row-fluid">
        <c:choose>
            <c:when test="${sessionScope.get('userid')==null}">
                <div class="span2 offset7"><a href="/tologin">
                    <p class="navbar-text pull-right">登陆</p></a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="span2 offset7"><a href="/userInfo">
                    <p class="navbar-text pull-right">用户：${sessionScope.get("username")}</p></a>
                </div>
            </c:otherwise>
        </c:choose>
        <div class="span1 ">
            <a href="/cart"><p class="navbar-text pull-right">购物车</p></a>
        </div>
        <div class="span1 ">
            <a href="/allOrder"><p class="navbar-text pull-right">我的订单</p></a>
        </div>
        <div class="span1 ">
            <a href="/logout"><p class="navbar-text pull-right">退出账号</p></a>
        </div>
    </div>
</div>
<div class="container">
    <div class="navbar">
        <div class="navbar-inner">
            <a class="brand" href="#">EMall</a>
            <ul class="nav">
                <li><a href="/home">首页</a></li>
                <li><a href="${pageContext.request.contextPath}/product_by_category?categoryid=1">手机</a></li>
                <li><a href="${pageContext.request.contextPath}/product_by_category?categoryid=2">电脑</a></li>
                <li><a href="${pageContext.request.contextPath}/product_by_category?categoryid=3">耳机</a></li>
            </ul>
            <form action="${pageContext.request.contextPath}/search" method="post" class="navbar-form pull-right">
                <input type="text" name="searchProduct" class="span2">
                <button type="submit" class="btn">搜索</button>
            </form>
        </div>
    </div>
</div>
<div class="container">
    <div class="jumbotron">
        <h2>购物车</h2>
        <hr>
        <div class="table-responsive">
            <form action="${pageContext.request.contextPath}/order" method="post">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>商品名称</th>
                        <th>商品图片</th>
                        <th>单价</th>
                        <th>数量</th>
                        <th>小计</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="c" items="${cart}">
                        <tr>
                            <td><label><input name="pid" type="checkbox" value="${c.pid}" checked/>${c.pname}</label>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/product_detail?productid=${c.pid}">
                                    <img alt="" src="images/${c.pimage}" height="100" width="100" id="images"
                                         onerror="this.src='images/default.jpg'" class="img-polaroid">
                                </a>
                            </td>
                            <td>¥${c.baseprice}</td>
                            <td>${c.count}</td>
                            <td>¥${c.subtotal}</td>
                            <td><a href="${pageContext.request.contextPath}/deleteById?cartid=${c.cartId}"
                                   class="btn btn-xs btn-danger">删除</a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="row-fluid">
                    <div class="span2 offset8">
                        <a href="${pageContext.request.contextPath}/clearCart"
                           class="btn btn-xs btn-danger">清空购物车</a>
                    </div>
                    <div class="span2">
                        <button class="btn btn-xs btn-primary" type="submit">提交订单</button>
                    </div>
                </div>
            </form>
        </div>

    </div>
</div>

<!-- 引入footer.jsp -->
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>