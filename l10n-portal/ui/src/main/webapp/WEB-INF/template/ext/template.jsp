<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/tld/custom.tld" prefix="functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    L10n.ws Portal
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
        <div class="panel panel-dark panel-flat panel-login">
            <div class="panel-heading text-center">
                <a href="${contextPath}/">
                    <img src="${contextPath}/resources/img/logo.png" alt="Home" class="block-center img-rounded">
                </a>
            </div>

            <div class="panel-body">
                <tiles:insertAttribute name="content"/>
            </div>
        </div>
        <!-- END panel-->


        <!-- END panel-->
        <div class="pv text-center">
            <span>&copy;${functions:year()} - L10n.ws</span>
        </div>

    </div>

</div>

<!-- =============== VENDOR SCRIPTS ===============-->
<!-- MODERNIZR-->
<script src="${contextPath}/resources/vendor/modernizr/modernizr.js"></script>
<!-- JQUERY-->
<script src="${contextPath}/resources/vendor/jquery/dist/jquery.js"></script>
<!-- BOOTSTRAP-->
<script src="${contextPath}/resources/vendor/bootstrap/dist/js/bootstrap.js"></script>
<!-- STORAGE API-->
<script src="${contextPath}/resources/vendor/jQuery-Storage-API/jquery.storageapi.js"></script>
<!-- PARSLEY-->
<script type="text/javascript">
    window.ParsleyExtend = {
        asyncValidators: {
            exist: {
                fn: function (xhr) {
                    return 404 === xhr.status;
                }
            }
        }
    };
</script>
<script type="text/javascript" src="${contextPath}/resources/vendor/parsleyjs/dist/parsley.remote.min.js"></script>
<script src="${contextPath}/resources/vendor/parsleyjs/dist/parsley.min.js"></script>

<!-- =============== APP SCRIPTS ===============-->
<script src="${contextPath}/resources/js/app.js"></script>

</body>

</html>