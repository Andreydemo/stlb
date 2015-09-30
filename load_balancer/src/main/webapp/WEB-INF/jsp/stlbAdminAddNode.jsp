<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="/stlb/css/style.css">
    </head>
    <body>
        <H1>Admin:Add Node</H1>
        <form action="/stlb/addNode" method="post">
                <label for="url-value">URL</label>
                <input id="url-value" name="url" type="text"/>
                <input type="submit" value="Add"/>
        </form>
    </body>
</html>