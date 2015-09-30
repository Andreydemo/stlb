<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="/stlb/css/style.css">
    </head>
    <body>
        <H1><a href="/stlb">Admin</a>:Configs</H1>
      <table border="1">
          <th>Name</th><th>Value</th>
            <c:forEach items="${configs}" var="config">
                <tr>
               <td><a href="/stlb/editConfig?configName=${config.key}">${config.key}</a></td><td>${config.value}</td>
                </tr>
            </c:forEach>
      </table>
    </body>
</html>