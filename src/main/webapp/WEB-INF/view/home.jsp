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
<%--轮播图--%>
<div class="container">
    <div id="myCarousel" class="carousel slide">
        <ol class="carousel-indicators">
            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#myCarousel" data-slide-to="1"></li>
            <li data-target="#myCarousel" data-slide-to="2"></li>
        </ol>
        <!-- Carousel items -->
        <div class="row">
            <div class="span6 offset3">
                <div class="carousel-inner">
                    <div class="active item"><img alt="" src="images/bg/Carousel1.jpg"></div>
                    <div class="item"><img alt="" src="images/bg/Carousel2.jpg"></div>
                    <div class="item"><img alt="" src="images/bg/Carousel3.jpg"></div>
                </div>
            </div>
        </div>
        <!-- Carousel nav -->
        <a class="carousel-control left" href="#myCarousel" data-slide="prev">&lsaquo;</a>
        <a class="carousel-control right" href="#myCarousel" data-slide="next">&rsaquo;</a>
    </div>
</div>
<div class="container">
    <div class="jumbotron">
        <h2>热门商品</h2>
        <hr>
        <div class="row">
            <c:forEach var="hp" items="${hotProduct}" begin="0" end="3">
                <div class="span3">
                    <a href="${pageContext.request.contextPath}/product_detail?productid=${hp.productId}">
                        <img alt="" src="images/${hp.productImage}" height="250" width="250"
                             onerror="this.src='images/default.jpg'" class="img-polaroid">
                    </a>
                    <h5>
                        <a href="${pageContext.request.contextPath}/product_detail?productid=${hp.productId}"><span style="color: black">${hp.productName}</span></a>
                    </h5>
                    <h5><span style="color: red">¥${hp.productPrice}</span></h5>
                </div>
            </c:forEach>
        </div>

        <h2>最新商品</h2>
        <hr>
        <div class="row">
            <c:forEach var="np" items="${newProduct}" begin="0" end="7">
                <div class="span3">
                    <a href="${pageContext.request.contextPath}/product_detail?productid=${np.productId}">
                        <img alt="" src="images/${np.productImage}" height="250" width="250" id="images"
                             onerror="this.src='images/default.jpg'" class="img-polaroid">
                    </a>
                    <h5>
                        <a href="${pageContext.request.contextPath}/product_detail?productid=${np.productId}">
                            <span style="color: black">${np.productName}</span></a>
                    </h5>
                    <h5><span style="color: red">¥${np.productPrice}</span></h5>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<!-- 引入footer.jsp -->
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>