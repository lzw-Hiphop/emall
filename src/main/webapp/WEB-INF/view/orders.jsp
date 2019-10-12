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
        <h2>我的订单</h2>
        <hr>
        <div class="table-responsive">
            <c:forEach var="list" items="${lists}">
                <h5>订单编号：${list.get(0).orderId}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    下单时间：${list.get(0).orderTime}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <c:choose>
                        <c:when test="${list.get(0).payState==1}">
                            支付状态：已支付&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        </c:when>
                        <c:otherwise>
                            支付状态：<a href="${pageContext.request.contextPath}/toPay?orderid=${list.get(0).orderId}">
                            <span style="color: red">未支付</span></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        </c:otherwise>
                    </c:choose>
                    <a href="${pageContext.request.contextPath}/deleteOrder?orderid=${list.get(0).orderId}"
                       class="btn btn-small btn-danger">删除订单</a>
                </h5>
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>商品名称</th>
                        <th>商品图片</th>
                        <th>单价</th>
                        <th>数量</th>
                        <th>小计</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="l" items="${list}">
                        <tr>
                            <td>${l.pname}</td>
                            <td><img alt="" src="images/${l.pimage}" height="100" width="100" id="images"
                                     onerror="this.src='images/default.jpg'" class="img-polaroid">
                            </td>
                            <td>¥${l.price}</td>
                            <td>${l.count}</td>
                            <td>¥${l.subtotal}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <hr>
            </c:forEach>
        </div>
    </div>
</div>

<!-- 引入footer.jsp -->
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>