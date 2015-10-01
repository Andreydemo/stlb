<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Admin</title>
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
            <h3 class="ui dividing header">Hello there!</h3>
            <p>Choose one of the menu item.</p>
            <p>Mauris elementum sit amet velit vitae sollicitudin. Fusce in nibh et tellus placerat ornare quis vitae purus.
            Nulla blandit nunc tempus dictum consequat. Etiam nec nibh eget velit fermentum efficitur. Nam iaculis suscipit
            nulla at luctus. Duis ultricies ligula eget felis volutpat porttitor. Praesent tristique hendrerit massa, eget
            ultrices tortor rutrum quis.</p>
        </section>
    </main>
    </body>
    <script type="javascript" src="/stlb/bower_components/semantic/dist/semantic.js"/>
</html>