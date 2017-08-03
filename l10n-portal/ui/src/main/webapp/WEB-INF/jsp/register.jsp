<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <p class="text-center pv">SIGNUP TO GET INSTANT ACCESS.</p>

        <c:if test="${param.error ne null}">
            <span class="error">Email already registered!</span>
        </c:if>

        <form role="form" action="${contextPath}/register" method="post" data-parsley-validate="" novalidate=""  class="mb-lg">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="form-group has-feedback">
                <label for="email" class="text-muted">Email address</label>
                <input id="email" name="email" type="email" placeholder="Enter email" autocomplete="off"
                       required data-parsley-maxlength="50" data-parsley-trigger="change"
                       data-parsley-remote-options='{"type": "POST", "dataType": "jsonp", "data": { "${_csrf.parameterName}": "${_csrf.token}" }}'
                       data-parsley-remote-message="This email address already exists."
                       data-parsley-remote="${contextPath}/register/exist" data-parsley-remote-validator="exist"
                       class="form-control">
                <span class="fa fa-envelope form-control-feedback text-muted"></span>
            </div>
            <div class="form-group has-feedback">
                <label for="password1" class="text-muted">Password</label>
                <input id="password1" name="password" type="password" placeholder="Password" autocomplete="off" required class="form-control">
                <span class="fa fa-lock form-control-feedback text-muted"></span>
            </div>
            <div class="form-group has-feedback">
                <label for="password2" class="text-muted">Retype Password</label>
                <input id="password2" type="password" placeholder="Retype Password" autocomplete="off" required data-parsley-equalto="#password1" class="form-control">
                <span class="fa fa-lock form-control-feedback text-muted"></span>
            </div>
            <div class="clearfix">
                <div class="checkbox c-checkbox pull-left mt0">
                    <label>
                        <input type="checkbox" value="" required name="agreed">
                        <span class="fa fa-check"></span>I agree with the <a href="${contextPath}/terms">terms</a>
                    </label>
                </div>
            </div>
            <button type="submit" class="btn btn-block btn-primary mt-lg">Create account</button>
        </form>
        <p class="pt-lg text-center">Have an account?</p><a href="${contextPath}/login" class="btn btn-block btn-default">Sign in</a>
