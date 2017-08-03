<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
        <p class="pt-lg text-center">Need to Signup?</p>
        <a href="${contextPath}/register" class="btn btn-block btn-default">Register Now</a>