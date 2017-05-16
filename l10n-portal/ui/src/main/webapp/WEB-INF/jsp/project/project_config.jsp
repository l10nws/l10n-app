<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ws.l10n.portal.domain.Permission" %>
<div class="content-heading">
    Access management
</div>

<ol class="breadcrumb">
    <li><a href="${contextPath}/project/all">Projects</a></li>
    <li>${project.name}</li>
</ol>

<c:if test="${param.error ne null}">
    <div class="alert alert-danger alert-dismissible fade in" role="alert">
        <button class="close" aria-label="Close" data-dismiss="alert" type="button">
            <span aria-hidden="true">&times;</span>
        </button>
        No user found.
    </div>
</c:if>

<div class="panel panel-default">
    <div class="panel-heading">
        <h4>Users</h4>
    </div>
    <div class="panel-body">
        <div class="table-responsive">
            <form action="${contextPath}/project/assign/${project.id}" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <table class="table permission-table">
                    <thead>
                    <tr>
                        <th>
                            <input id="email" type="email" placeholder="User email address" name="email"
                                   class="form-control"/>
                        </th>
                        <th class="permission">
                            <select id="permission" name="permission" class="form-control">
                                <option>
                                    <%=Permission.READ%>
                                </option>
                                <option>
                                    <%=Permission.WRITE%>
                                </option>
                            </select>
                        </th>
                        <th class="add">
                            <button type="submit" class="btn btn-primary">Add</button>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>${owner.email}</td>
                        <td>owner</td>
                        <td>
                        </td>
                    </tr>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td>${user.email}</td>
                            <td>
                                <a data-user-id="${user.userId}" data-permission="<%=Permission.READ%>" href="javascript:function f(){}" class="permission-btn btn btn-square btn-default btn-xs ${user.permission == 'READ' ? 'active' : ''}" >
                                    <%=Permission.READ%>
                                </a>
                                <a data-user-id="${user.userId}" data-permission="<%=Permission.WRITE%>" href="javascript:function f(){}" class="permission-btn btn btn-square btn-default btn-xs ${user.permission == 'WRITE' ? 'active' : ''}" >
                                    <%=Permission.WRITE%>
                                </a>
                            </td>
                            <td>
                                <a class="unassign" data-user-id="${user.userId}" href="javascript:function f(){}">
                                    <i class="fa fa-times-circle"></i>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </form>
        </div>
        <a type="button" class="btn btn-primary app-btn" href="${contextPath}/project/all">Back</a>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $(".unassign").click(function () {
            var userId = $(this).data("user-id");
            $.post('${contextPath}/project/unassign/${project.id}',
                    {
                        "${_csrf.parameterName}": "${_csrf.token}",
                        "userId": userId
                    },
                    function (data) {
                        window.location.replace("${contextPath}/project/config/${project.id}");
                    });
        });
        $(".permission-btn").click(function () {
            var userId = $(this).data("user-id");
            var permission = $(this).data("permission");
            $.post('${contextPath}/project/permission/${project.id}',
                    {
                        "${_csrf.parameterName}": "${_csrf.token}",
                        "userId": userId,
                        "permission": permission
                    },
                    function (data) {
                        window.location.replace("${contextPath}/project/config/${project.id}");
                    });
        });
    });
</script>