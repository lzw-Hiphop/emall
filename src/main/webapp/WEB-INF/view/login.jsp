<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>EMall</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/bootstrap.min.js"></script>
    <style>
        .container {
            width: 300px;
            margin-top: 50px
        }

        .form-control {
            margin-bottom: 10px;
        }

        body {
            background: #eee;
        }
    </style>
    <script language="JavaScript">
        function getQueryVariable(variable) {
            var query = window.location.search.substring(1);
            var vars = query.split("&");
            for (var i = 0; i < vars.length; i++) {
                var pair = vars[i].split("=");
                if (pair[0] == variable) {
                    return pair[1];
                }
            }
            return (false);
        }
    </script>

</head>

<body>
<script>
    var state = getQueryVariable("state");
    if (state == "error") {
        alert("登陆失败，用户名或密码错误！");
    }
</script>
<div class="container">
    <form class="form-signin" action="${pageContext.request.contextPath}/login" method="post">
        <h2 class="form-signin-heading" >请登录</h2>
        <input type="text" id="username" name="username" class="input-block-level" placeholder="用户名" required autofocus>
        <input type="password" id="password" name="password" class="input-block-level" placeholder="密码" required>
        <button class="btn btn-large btn-primary" type="submit">登录</button>
    </form>
    <a href="/toreg" >未注册？点这里</a>
</div>
</body>
</html>