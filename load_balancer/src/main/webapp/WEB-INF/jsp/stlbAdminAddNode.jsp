<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Admin | Add Node</title>
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
            <a href="/stlb/viewNodes">View Nodes</a>
            <i class="right chevron icon divider"></i>
            <div class="active section">Add Node</div>
        </div>
        <section id="content" class="ui segment">
            <form action="/stlb/addNode" method="post" class="ui form">
                <div class="field">
                    <label for="url-name">Name</label>
                    <input id="url-name" name="name" type="text"/>
                </div>
                <div class="field">
                    <label for="url-value">URL</label>
                    <input id="url-value" name="url" type="text"/>
                </div>
                <input type="submit" value="Add" class="teal ui button"/>
                <div class="ui error message">
                    <ul class="list"></ul>
                </div>
            </form>
        </section>
    </main>
    <script src="/stlb/bower_components/jquery/dist/jquery.min.js"></script>
    <script src="/stlb/bower_components/semantic/dist/semantic.js"></script>
    <script src="/stlb/js/main.js"></script>
    </body>
</html>