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
        function submitSave() {
            var p1 = document.getElementById("password");
            var p2 = document.getElementById("password2");
            if (p1.value != p2.value) {
                alert("错误，新密码与确认新密码不相同！");
                return false;
            }
            return true;
        }

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
    if (state == "success") {
        alert("注册成功，请重新登录！");
        document.location.href = "tologin";
    }
    if (state == "error") {
        alert("注册失败，用户名已存在！");
    }
</script>
<div class="container">
    <form class="form-signin" action="${pageContext.request.contextPath}/register" method="post"
          onSubmit="return submitSave()">
        <h2 class="form-signin-heading">注册</h2>
        <input type="text" id="username" name="username" class="input-block-level" placeholder="用户名" required autofocus>
        <input type="password" id="password" name="password" class="input-block-level" placeholder="密码" required>
        <input type="password" id="password2" name="password2" class="input-block-level" placeholder="确认密码" required>
        <button class="btn btn-large btn-primary" type="submit">注册</button>
    </form>
    <a href="tologin">已注册？点这里登陆</a>
</div>
</body>
</html>