<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tld/custom.tld" prefix="functions" %>
<c:set var="contextPath" scope="application" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

<meta name="keywords" content="">
<meta name="description" content="">

<title>
</title>
<link rel="shortcut icon" href="${contextPath}/resources/img/favicon_32x32.png">
<link rel="apple-touch-icon image_src" href="${contextPath}/resources/img/favicon_158x158.png">
<!-- =============== VENDOR STYLES ===============-->
<!-- FONT AWESOME-->
<link rel="stylesheet" href="${contextPath}/resources/vendor/fontawesome/css/font-awesome.min.css">
<!-- SIMPLE LINE ICONS-->
<link rel="stylesheet" href="${contextPath}/resources/vendor/simple-line-icons/css/simple-line-icons.css">
<!-- =============== BOOTSTRAP STYLES ===============-->
<link rel="stylesheet" href="${contextPath}/resources/css/bootstrap.css" id="bscss">
<!-- =============== APP STYLES ===============-->
<link rel="stylesheet" href="${contextPath}/resources/css/app.css" id="maincss">
<link rel="stylesheet" href="${contextPath}/resources/css/app-ex.css">


<body>

<div class="wrapper">

    <div class="block-center mt-xl wd-xl">

        <!-- START panel-->

        <div class="text-center mb-xl">
            <div class="text-lg mb-lg">404</div>
            <p class="lead m0">We couldn't find this page.</p>

            <p>The page you are looking for does not exists.</p>
        </div>

        <ul class="list-inline text-center text-sm mb-xl">
            <li><a href="${contextPath}/" class="text-muted">Home</a>
            </li>
        </ul>
        <!-- END panel-->
        <div class="pv text-center">
            <span>&copy;${functions:year()} - L10n.ws</span>
        </div>

    </div>

</div>

</body>

</html>
