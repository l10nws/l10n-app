<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" scope="application" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">

<tiles:insertAttribute name="head"/>

<body>
<div class="wrapper">
    <!-- top navbar-->
    <tiles:insertAttribute name="header"/>

    <!-- sidebar-->
    <tiles:insertAttribute name="left_menu"/>

    <!-- offsidebar-->
    <%--<tiles:insertAttribute name="right_menu"/>--%>

    <!-- Main section-->
    <section>
        <div class="content-wrapper">
            <tiles:insertAttribute name="content"/>
        </div>
    </section>

    <tiles:insertAttribute name="footer"/>

</div>

<tiles:insertAttribute name="scripts"/>

</body>

</html>