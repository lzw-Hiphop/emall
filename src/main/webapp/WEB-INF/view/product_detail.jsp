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
    <h2>商品详情</h2>
    <hr>
    <div class="row-fluid">
        <div class="span7"><img alt="" src="images/${product.productImage}" height="450" width="450" id="images"
                                onerror="this.src='images/default.jpg'" class="img-polaroid">
        </div>
        <div class="span5 ">
            <form action="${pageContext.request.contextPath}/addToCart" method="post">
                <h4>商品名称：${product.productName}</h4>
                <h4>价格：<span style="color: red">¥ ${product.productPrice}</span></h4>
                <h4>库存：${product.productStore}</h4>
                <h4>商品描述： ${product.productInfo}</h4>
                <h4>购买数量：<input type="text" name="buyNum" value="1"></h4>
                <input type="hidden" name="pid" value="${product.productId}">
                <button class="btn btn-xs btn-primary" type="submit">加入购物车</button>
            </form>
        </div>
    </div>
</div>
<!-- 引入footer.jsp -->
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>