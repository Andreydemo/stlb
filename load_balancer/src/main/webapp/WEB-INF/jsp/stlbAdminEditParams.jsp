<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Admin | Edit config</title>
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
            <div class="ui large breadcrumb">
                <a href="/stlb">Admin</a>
                <i class="right chevron icon divider"></i>
                <a href="/stlb/config">Configs</a>
                <i class="right chevron icon divider"></i>
                <div class="active section">edit ${configName}</div>
            </div>
            <section id="content" class="ui segment">
                <h3>${configName} - ${configType}</h3>
                <form:form action="editConfig" method="post" commandName="configChangingBean" class="ui form">
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
                            <div class="grouped fields">
                                <label>${configName} : ${configValue}</label>
                                <div class="field">
                                    <div class="ui radio checkbox">
                                        <input type="radio" name="value" value="true" id="true" ${trueChecked}>
                                        <label for="true">true</label>
                                    </div>
                                </div>
                                <div class="field">
                                    <div class="ui radio checkbox">
                                        <input type="radio" name="value" value="false" id="false" ${falseChecked}>
                                        <label for="false">false</label>
                                    </div>
                                </div>
                                <input type="hidden" name="type" value="${configType}">
                                <input type="hidden" name="name" value="${configName}">
                            </div>
                        </c:when>
                    </c:choose>

                    <input type="submit" value="Change"  class="teal ui button">
                </form:form>
            </section>
    </main>
</body>
</html>