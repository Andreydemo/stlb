<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/stlb/css/style.css">
</head>
<body>
<H1><a href="/stlb">Admin</a>:<a href="/stlb/config">Configs</a>:edit config [${configName}]</H1>

<h3>${configName} - ${configType}</h3>

<form:form action="editConfig" method="post" commandName="configChangingBean" >
    <c:choose>
        <c:when test="${configType eq 'java.lang.Boolean'}">
            <c:choose>
                <c:when test="${configValue}">
                    <c:set var="trueChecked" value="checked" scope="request"/>
                </c:when>
                <c:otherwise>
                    <c:set var="falseChecked" value="checked" scope="request"/>
                </c:otherwise>
            </c:choose>
            <label for="true">true</label>
            <input type="radio" name="value" value="true" id="true" ${trueChecked}>
            <label for="false">false</label>
            <input type="radio" name="value" value="false" id="false" ${falseChecked}>
            <input type="hidden" name="type" value="${configType}">
            <input type="hidden" name="name" value="${configName}">
        </c:when>
    </c:choose>
    <br>
    <input type="submit" value="Change">
</form:form>
</body>
</html>