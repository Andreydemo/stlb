<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Admin | View Nodes</title>
        <link rel="stylesheet" type="text/css" href="/stlb/bower_components/normalize-css/normalize.css"/>
        <link rel="stylesheet" type="text/css" href="/stlb/bower_components/semantic/dist/semantic.css"/>
    </head>
    <body>
        <main class="ui container">
            <div class="ui stackable inverted container menu">
                <a href="/stlb" class="item">Admin</a>
                <a href="/stlb/viewNodes" class="item">View Nodes</a>
                <a href="/stlb/config" class="item">Configurations</a>
            </div>
            <section id="content" class="ui segment">
                <h3 class="ui medium header">Available Nodes :</h3>
                <div class="ui middle aligned divided list">
                    <c:forEach items="${availableNodes}" var="node">
                        <div class="item">
                            <form action="/stlb/removeNode" method="post" class="right floated content">
                                <input type="hidden" value="${node.url}" name="deletingUrl"/>
                                <button type="submit" class="ui icon button">
                                    <i class="remove icon"></i>
                                </button>
                            </form>
                            <c:choose>
                                <c:when test="${node.available}">
                                    <i class="circular plug icon inverted green"></i>
                                </c:when>
                                <c:otherwise>
                                    <i class="circular plug icon inverted red"></i>
                                </c:otherwise>
                            </c:choose>
                            <div class="content">
                                ${node.url}
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <form action="/stlb/addNode" method="get" >
                    <input type="submit" value="Add node" class="teal ui button">
                </form>
            </section>
        </main>
    </body>
    <script type="javascript" src="/stlb/bower_components/semantic/dist/semantic.js"/>
</html>