<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Insert title here</title>
    <link rel="icon" href="images/ico16x16.ico" type="image/x-icon" />
    <link rel="shortcut icon" href="images/ico16x16.ico" type="image/x-icon" />
    <link rel="bookmark" href="images/ico16x16.ico" type="image/x-icon" />
</head>

<body>
<h2>Hello World!</h2>
<p>src:http://localhost:8080/springmvc/index.jsp</p>
<form id="reportPast" action="/springmvc/Home" method="post">
    begin:<input type="text" name="begin"><br>
    end:<input type="text" name="end"><br>
    <input type="submit" value="POST查找">
</form>
</body>
<script>
    function Search(){
        window.open("/springmvc/detail/data=" + document.getElementById("userName").value);
    }
</script>
</html>
