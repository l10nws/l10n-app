<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authentication var="currentUser" property="principal"/>

<div class="content-heading">
    <div class="pull-right">
        <a id="createBundleBtn" href="${contextPath}/user/create" class="btn btn-green" role="button">
            <span class="fa fa-plus " aria-hidden="true"></span>
            Create
        </a>
    </div>
    Users
</div>

<div class="panel panel-default">
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${users}" var="user" varStatus="status">
                    <tr class="${currentUser.role == 'ROLE_SUPERUSER' || user.role != 'ROLE_SUPERUSER' ? 'clickable-row' : ''} user-item" data-user-id="${user.id}">
                        <td>${status.index + 1}</td>
                        <td class="user-label">${user.email}</td>
                        <td>
                            <c:choose>
                                <c:when test="${user.role == 'ROLE_SUPERUSER'}">
                                    SUPERUSER
                                </c:when>
                                <c:when test="${user.role == 'ROLE_ADMIN'}">
                                    ADMIN
                                </c:when>
                                <c:when test="${user.role == 'ROLE_USER'}">
                                    USER
                                </c:when>
                            </c:choose>

                        </td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>
                            <c:if test="${user.role != 'ROLE_SUPERUSER' && currentUser.id != user.id}">
                                <button type="button" class="btn btn-default btn-xs pull-right delete-user-btn" data-user-id="${user.id}" data-user-email="${user.email}">Delete</button>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<div id="deleteUserModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">Delete User</h4>
            </div>
            <div class="modal-body">
                <p>Are you sure that you want to delete user <strong class="user-email"></strong>?</p>
            </div>
            <div class="modal-footer">
                <a href="javascript:function f(){}" type="button" class="ok btn btn-primary yes-btn">Yes</a>
                <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
            </div>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {

        $(".clickable-row").click(function () {
            var id = $(this).data("user-id");
            window.location.replace("${contextPath}/user/edit/" + id);
        });

        $("#deleteUserModal").find(".ok").on('click', function() {
            var userId = $(this).data("user-id");
            $.post('${contextPath}/user/delete',
                    {
                        "${_csrf.parameterName}": "${_csrf.token}",
                        "userId": userId
                    },
                    function (data) {
                        window.location.replace("${contextPath}/user/all");
                    }
            );
        });

        $(".delete-user-btn").click(function (e) {
            e.stopPropagation();
            var userId = $(this).data("user-id");
            var userEmail = $(this).data("user-email");
            $("#deleteUserModal").modal();
            $("#deleteUserModal").insertAfter(".modal-backdrop");
            $("#deleteUserModal").find(".user-email").html(userEmail);
            $("#deleteUserModal").find(".ok").data("user-id", userId);
        });

    });
</script>