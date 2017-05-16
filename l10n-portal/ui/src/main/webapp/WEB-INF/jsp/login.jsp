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
    Login
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
        <!-- START panel-->
        <div class="panel panel-dark panel-flat panel-login">
            <div class="panel-heading text-center">
                <a href="${contextPath}/">
                    <img src="${contextPath}/resources/img/logo.png" alt="Home" class="block-center img-rounded">
                </a>
            </div>
            <div class="panel-body">
                <p class="text-center pv">SIGN IN TO CONTINUE.</p>

                <c:if test="${param.error ne null}">
                    <span class="error">Please check that you have entered your login and password correctly.</span>
                </c:if>

                <form role="form" action="${contextPath}/login" method="post" data-parsley-validate="" novalidate=""
                      class="mb-lg">

                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                    <div class="form-group has-feedback">
                        <input id="exampleInputEmail1" name="username" type="email" placeholder="Enter email"
                               autocomplete="off"
                               required class="form-control">
                        <span class="fa fa-envelope form-control-feedback text-muted"></span>
                    </div>
                    <div class="form-group has-feedback">
                        <input id="exampleInputPassword1" name="password" type="password" placeholder="Password"
                               required
                               class="form-control">
                        <span class="fa fa-lock form-control-feedback text-muted"></span>
                    </div>
                    <div class="clearfix">
                        <div class="checkbox c-checkbox pull-left mt0">
                            <label>
                                <input type="checkbox" name="remember" checked="checked">
                                <span class="fa fa-check"></span>Remember Me</label>
                        </div>
                    </div>
                    <button id="loginBtn" type="submit" class="btn btn-block btn-primary mt-lg">Login</button>
                </form>
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