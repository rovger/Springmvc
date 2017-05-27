<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <script type="text/javascript" language="javascript" src="js/jquery.js"></script>
    <title>springmvc test</title>
    <script type="text/javascript">
        $(function() {
            $("#h4").html('当前环境： '+'${model.currentEnv}');
        });
    </script>
</head>
<body>
<h2>Hello World!</h2>
<h3>SpringMVC demo by Rovger: 2017/03/27 00:00:00</h3>
<h4 id="h4">初始环境: null</h4>
</body>
</html>
