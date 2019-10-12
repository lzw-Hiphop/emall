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
        <h2>热销</h2>
        <hr>
        <div class="row">
            <c:forEach var="p" items="${pageUtil.list}" begin="0" end="7">
                <div class="span3">
                    <a href="${pageContext.request.contextPath}/product_detail?productid=${p.productId}">
                        <img alt="" src="images/${p.productImage}" height="250" width="250" id="images"
                             onerror="this.src='images/default.jpg'" class="img-polaroid">
                    </a>
                    <h5>
                        <a href="${pageContext.request.contextPath}/product_detail?productid=${p.productId}">
                            <span style="color: black">${p.productName}</span></a>
                    </h5>
                    <h5><span style="color: red">¥${p.productPrice}</span></h5>
                </div>
            </c:forEach>
        </div>
        <!--分页-->
        <div class="pagination">
            <ul>
                <!--第一页-->
                <li>
                    <c:choose>
                    <c:when test="${pageUtil.pageIndex - 1 > 0}">
                    <a href="product_by_category?pageIndex=1&categoryid=${pageUtil.list.get(0).categoryId}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                    </c:when>
                    <c:otherwise>
                <li class="disabled">
                    <a href="#" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                </c:otherwise>
                </c:choose>
                </li>
                <!--页数-->
                <c:forEach var = "i" begin="1" end="${pageUtil.pageCount}" step="1">
                    <c:choose>
                        <c:when test="${pageUtil.pageIndex==i}">
                            <li class="active">
                                <a href="product_by_category?pageIndex=${i}&categoryid=${pageUtil.list.get(0).categoryId}">${i}</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="product_by_category?pageIndex=${i}&categoryid=${pageUtil.list.get(0).categoryId}">${i}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <!--尾页-->
                <li>
                    <c:choose>
                    <c:when test="${pageUtil.pageIndex  < pageUtil.pageCount}">
                    <a href="product_by_category?pageIndex=${pageUtil.pageCount}&categoryid=${pageUtil.list.get(0).categoryId}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                    </c:when>
                    <c:otherwise>
                <li class="disabled">
                    <a href="#" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
                </c:otherwise>
                </c:choose>
                </li>
            </ul>
    </div>
</div>
<!-- 引入footer.jsp -->
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>