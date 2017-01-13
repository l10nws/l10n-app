<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="@permissionChecker.canWriteProject(#project.id)" var="canWrite"/>

<div class="content-heading">
    <c:if test="${canWrite}">
        <div class="pull-right">
            <a id="createBundleBtn" href="${contextPath}/bundle/create?p=${project.id}" class="btn btn-green" role="button">
                <span class="fa fa-plus " aria-hidden="true"></span>
                Create
            </a>
        </div>
    </c:if>
    Message Bundles
</div>
<ol class="breadcrumb">
    <li><a href="${contextPath}/project/all">Projects</a></li>
    <li class="project-name-breadcrumb">${project.name}</li>
</ol>


<c:set var="listSize" value="${fn:length(project.messageBundles)}" scope="request"/>
<c:choose>
    <c:when test="${listSize == 0}">
        <jsp:include page="no_project_bundles.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:include page="project_bundle_items.jsp"/>
    </c:otherwise>
</c:choose>

<div id="deleteBundleModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">Delete Message Bundle</h4>
            </div>
            <div class="modal-body">
                <p>Are you sure that you want to delete <strong class="bundle-label"></strong>?</p>
            </div>
            <div class="modal-footer">
                <a href="javascript:function f(){}" type="button" class="ok btn btn-primary yes-btn">Yes</a>
                <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $("#deleteBundleModal").find(".ok").on('click', function() {
            var bundleId = $(this).data("bundle-id");
            $.post('${contextPath}/bundle/delete',
                    {
                        "${_csrf.parameterName}": "${_csrf.token}",
                        "bundleId": bundleId
                    },
                    function (data) {
                        window.location.replace("${contextPath}/bundle?p=${project.id}");
                    }
            );
        });
        $(".delete-project-bundle").click(function () {
            var bundleId = $(this).data("bundle-id");
            var bundleLabel = $(this).data("bundle-label");
            $("#deleteBundleModal").modal();
            $("#deleteBundleModal").insertAfter(".modal-backdrop");
            $("#deleteBundleModal").find(".bundle-label").html(bundleLabel);
            $("#deleteBundleModal").find(".ok").data("bundle-id", bundleId);
        });
    });
</script>