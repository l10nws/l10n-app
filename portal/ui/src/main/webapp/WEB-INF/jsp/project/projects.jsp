<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="content-heading">
    <div class="pull-right">
        <a id="projectCreateBtn" href="${contextPath}/project/create" class="btn btn-green" role="button">
            <span class="fa fa-plus " aria-hidden="true"></span>
            Create
        </a>
    </div>
    Projects
</div>

<c:forEach var="item" items="${projects}">
    <c:set var="project" value="${item}" scope="request"/>
    <jsp:include page="project_item.jsp"/>
</c:forEach>

<c:set var="listSize" value="${fn:length(projects)}" scope="request"/>
<c:if test="${listSize == 0}">
    <jsp:include page="no_projects.jsp"/>
</c:if>

<div id="deleteProjectModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">Delete Project</h4>
            </div>
            <div class="modal-body">
                <p>Are you sure that you want to delete <strong class="project-name"></strong>?</p>
            </div>
            <div class="modal-footer">
                <a href="javascript:function f(){}" type="button" class="ok btn btn-primary delete-project-ok-btn">Yes</a>
                <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $("#deleteProjectModal").find(".ok").on('click', function() {
            var projectId = $(this).data("project-id");
            $.post('${contextPath}/project/delete/' + projectId,
                    {
                        "${_csrf.parameterName}": "${_csrf.token}"
                    },
                    function (data) {
                        window.location.replace("${contextPath}/project/all");
                    }
            );
        });
        $(".delete-project").click(function () {
            var projectId = $(this).data("project-id");
            var projectName = $(this).data("project-name");
            $("#deleteProjectModal").modal();
            $("#deleteProjectModal").insertAfter(".modal-backdrop");
            $("#deleteProjectModal").find(".project-name").html(projectName);
            $("#deleteProjectModal").find(".ok").data("project-id", projectId);
        });
    });
</script>
