<%--
  Created by IntelliJ IDEA.
  User: dos009@csiro.au
  Date: 5/05/2014
  Time: 9:39 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Render test</title>
</head>

<body>
<g:set var="startPageTime" value="${System.currentTimeMillis()}"/>
<h1>Render test</h1>
<g:render template="facets" />
<g:set var="endPageTime" value="${System.currentTimeMillis()}"/>
<div style="color:red">
    page render time = ${(endPageTime - startPageTime)} ms<br>
    controller processing time = ${processingTime} ms<br>
    total processing time = ${(endPageTime - startPageTime) + processingTime} ms
</div>
</body>
</html>