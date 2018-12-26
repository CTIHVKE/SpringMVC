<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Insert title here</title>
    <link rel="icon" href="images/ico16x16.ico" type="image/x-icon" />
    <link rel="shortcut icon" href="images/ico16x16.ico" type="image/x-icon" />
    <link rel="bookmark" href="images/ico16x16.ico" type="image/x-icon" />

     <script type="text/javascript" src="js/jquery-2.2.3.js"></script>
</head>

<body>
<h2>Hello World!</h2>
<p>src:http://localhost:8080/springmvc/index.jsp</p>
<form id="reportPast">
    logUser:<input type="text" id="logUser" name="logUser"><br>
    logTitle:<input type="text" id="logTitle" name="logTitle"><br>
    <input id="button" type="button" value="AJAX:POST查找">
    <br /><br />
    String:<input type="text" id="string" name="string"><br>
    <input id="getstring" type="button" value="AJAX:POST获取字符串">
</form>
<div style="color: red;" id="mydiv"></div>
<br />
<div style="color: red;" id="newstring"></div>
</body>
<script type="text/javascript">

    $(function(){
        $("#button").click(function(){
            var logUser = $("#logUser").val();
            var logTitle = $("#logTitle").val();

            var url="/springmvc/log";
            $.post(
                url,
                {
                    logUser:logUser,
                    logTitle:logTitle
                },
                function(data){
                    $("#mydiv").html("<p>"+data.logGuid+"***"+data.logUser+"***"+data.logTitle+"***"+data.logTime+"***"+data.logContent+"</p>");
                }
            );
        });

        $("#getstring").click(function(){
            var string = $("#string").val();

            var url="/springmvc/getstring";
            $.post(
                url,
                {
                    string:string
                },
                function(data){
                    $("#newstring").html("<p>"+data+"</p>");
                }
            );
        });

    });

</script>
</html>
