<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="content-heading">
    User Profile
</div>

<c:if test="${param.success ne null}">
    <div class="alert alert-dismissible fade in bg-green" role="alert">
        <button class="close" aria-label="Close" data-dismiss="alert" type="button">
            <span aria-hidden="true">&times;</span>
        </button>
        User profile has been successfully updated.
    </div>
</c:if>

<div class="panel panel-default panel-flat">
    <div class="panel-body">

        <form class="form-horizontal" method="post" action="${contextPath}/profile/save" data-parsley-validate=""
              novalidate="">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <h4>General</h4>
            <hr/>
            <div class="form-group has-feedback">
                <label class="col-lg-2 control-label" for="email">Email address</label>

                <div class="col-lg-10">
                    <input id="email" name="email" type="email" class="form-control" placeholder="Email Address"
                           autocomplete="off" value="${user.email}"
                           data-parsley-maxlength="50"
                           required
                           data-parsley-trigger="change"
                           data-parsley-remote-options='{"type": "POST", "dataType": "jsonp", "data": { "${_csrf.parameterName}": "${_csrf.token}" }}'
                           data-parsley-remote-message="This email address already exists."
                           data-parsley-remote="${contextPath}/profile/edit/exist"
                           data-parsley-remote-validator="exist">
                    <span class="fa fa-envelope form-control-feedback text-muted"></span>
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-2 control-label" for="firstName">First name</label>

                <div class="col-lg-10">
                    <input id="firstName" name="firstName" type="text" class="form-control" placeholder="First Name"
                           autocomplete="off" data-parsley-maxlength="50" value="${user.firstName}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-lg-2 control-label" for="lastName">Last name</label>

                <div class="col-lg-10">
                    <input id="lastName" name="lastName" type="text" class="form-control" placeholder="Last Name"
                           autocomplete="off" data-parsley-maxlength="50" value="${user.lastName}">
                </div>
            </div>

            <h4>Change password</h4>
            <hr/>
            <div class="form-group has-feedback">
                <label class="col-lg-2 control-label" for="newPassword">New Password</label>

                <div class="col-lg-10">
                    <input id="newPassword" name="newPassword" type="password" class="form-control"
                           placeholder="New Password" autocomplete="off"
                           data-parsley-validate-if-empty
                           data-parsley-maxlength="50">
                    <span class="fa fa-lock form-control-feedback text-muted"></span>
                </div>
            </div>
            <div class="form-group has-feedback">
                <label class="col-lg-2 control-label" for="retypePassword">Retype Password</label>

                <div class="col-lg-10">
                    <input id="retypePassword" type="password" class="form-control" placeholder="Retype Password"
                           autocomplete="off"
                           data-parsley-equalto="#newPassword"
                           data-parsley-validate-if-empty
                           data-parsley-maxlength="50">
                    <span class="fa fa-lock form-control-feedback text-muted"></span>
                </div>
            </div>

            <div class="form-group">
                <div class="col-lg-offset-2 col-lg-10">
                    <button id="saveBtn" class="mb-sm btn btn-primary app-btn" type="submit">Save</button>
                    <a id="backBtn" class="mb-sm btn btn-primary app-btn">Back</a>
                </div>
            </div>
        </form>
    </div>
</div>


<script>
    jQuery(document).ready(function ($) {
        $("#backBtn").click(function () {
            window.document.location = document.referrer;
        });
    });
</script>