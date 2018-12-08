<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/12/7
  Time: 1:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
String path = request.getContextPath();
// 获得本项目的地址(例如: http://localhost:8080/MyApp/)赋值给basePath变量
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
+ path + "/";
// 将 "项目路径basePath" 放入pageContext中，待以后用EL表达式读出。
pageContext.setAttribute("basePath", basePath);
%>

<html>
  <head>
    <title>12315468</title>
    <link rel="icon" href="${pageContext.request.contextPath}/statics/images/favicon.ico" type="image/x-icon">
    <link rel="Shortcut Icon" href="${pageContext.request.contextPath}/statics/images/favicon.ico" type="image/x-icon">
    <link rel="Bookmark" href="${pageContext.request.contextPath}/statics/images/favicon.ico" type="image/x-icon">

  </head>
  <body>
    <c:redirect url="${pageScope.basePath}views/login"></c:redirect>
  </body>
</html>
