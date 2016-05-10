<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>

</head>
<body>
<h1>Site name: ${siteName}</h1>
Hello  your session id is : ${sessionId}<br>
host - ${host}
<ul>
    <li><a href="/page1">page1</a></li>
    <li><a href="/page2">page2</a></li>
</ul>

<h2><a href="/shop">Move to the shop</a></h2>
</body>
</html>