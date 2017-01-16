<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/tld/custom.tld" prefix="functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authentication var="currentUser" property="principal"/>

<div class="content-heading">
    ${user eq null ? 'Create User' : 'Edit User'}
</div>

<ol class="breadcrumb">
    <li><a href="${contextPath}/user/all">Users</a></li>
</ol>

<div class="panel panel-default">
    <div class="panel-heading">
        <h4>User</h4>
    </div>
    <div class="panel-body">

        <form method="post" action="${contextPath}/user/${user eq null ? 'create': 'edit/'}${user.id}" class="form-horizontal" data-parsley-validate="" novalidate="">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="form-group required">
                <label for="email" class="col-lg-1 control-label">Email address</label>
                <div class="col-lg-11">
                    <input id="email" name="email" value="${user.email}" type="text" class="form-control"
                           required data-parsley-maxlength="50"
                           data-parsley-trigger="change"
                           data-parsley-remote-options='{"type": "POST", "dataType": "jsonp", "data": { "${_csrf.parameterName}": "${_csrf.token}", "userId" : "${user.id}"}}'
                           data-parsley-remote-message="This email address already exists."
                           data-parsley-remote="${contextPath}/user/${user eq null ? 'create': 'edit'}/exist" data-parsley-remote-validator="exist"/>
                </div>
            </div>

            <c:if test="${user eq null || user.role != 'ROLE_SUPERUSER' && currentUser.id != user.id}">
                <div class="form-group">
                    <label for="role" class="col-lg-1 control-label">Role</label>
                    <div class="col-lg-11">
                        <select id="role" name="role" class="form-control">
                            <option value="ROLE_USER" ${user eq null || user.role == 'ROLE_USER' ? 'selected' : ''}>USER</option>
                            <option value="ROLE_ADMIN" ${user.role == 'ROLE_ADMIN' ? 'selected' : ''}>ADMIN</option>
                        </select>
                    </div>
                </div>
            </c:if>

            <div class="form-group">
                <label for="firstName" class="col-lg-1 control-label">First name</label>
                <div class="col-lg-11">
                    <input id="firstName" name="firstName" value="${user.firstName}" type="text" class="form-control" data-parsley-maxlength="50"/>
                </div>
            </div>

            <div class="form-group">
                <label for="lastName" class="col-lg-1 control-label">Last name</label>
                <div class="col-lg-11">
                    <input id="lastName" name="lastName" value="${user.lastName}" type="text" class="form-control" data-parsley-maxlength="50"/>
                </div>
            </div>

            <div class="form-group ${user eq null ? 'required': ''}">
                <label for="password1" class="col-lg-1 control-label">Password</label>
                <div class="col-lg-11">
                    <input id="password1" name="password" type="password" placeholder="Password" autocomplete="off" class="form-control" ${user eq null ? 'required': ''}/>
                </div>
            </div>

            <div class="form-group ${user eq null ? 'required': ''}">
                <label for="password2" class="col-lg-1 control-label">Retype Password</label>
                <div class="col-lg-11">
                    <input id="password2" type="password" placeholder="Retype Password" autocomplete="off" class="form-control" ${user eq null ? 'required': ''} data-parsley-equalto="#password1"/>
                </div>
            </div>

            <div class="form-group">
                <div class="col-lg-offset-1 col-lg-11">
                    <button id="saveBtn" type="submit" class="btn btn-primary app-btn">${user eq null ? 'Create' : 'Save'}</button>
                    <a type="button" class="btn btn-primary app-btn" href="${contextPath}/user/all">Back</a>
                </div>
            </div>
        </form>

    </div>

</div>
