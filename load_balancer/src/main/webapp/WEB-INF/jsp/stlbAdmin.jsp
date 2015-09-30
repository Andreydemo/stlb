<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="/stlb/css/style.css">
    </head>
    <body>
        <H1>Admin</H1>
        Available Nodes :
        <ul>
                <c:forEach items="${availableNodes}" var="node">
                    <li>
                            ${node.url}
                            <c:choose>
                                    <c:when test="${node.available}">
                                            <figure class="green-circle"></figure>
                                    </c:when>
                                    <c:otherwise>
                                            <figure class="red-circle"></figure>
                                    </c:otherwise>
                            </c:choose>
                            <form action="/stlb/removeNode" method="post" class="inline-form">
                                        <input type="hidden" value="${node.url}" name="deletingUrl"/>
                                        <input type="submit" value="remove">
                            </form>
                    </li>
                </c:forEach>
        </ul>
        <form action="/stlb/removeNode" method="get" >
                <input type="submit" value="Add node">
        </form>
    </body>
</html>