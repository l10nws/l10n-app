<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<sec:authentication var="user" property="principal"/>

<c:forEach items="${projects}" var="project">
    <div class="panel panel-default">

        <div class="panel-heading">
            ${project.name}
        </div>

        <div class="panel-body">
            <c:set var="project" value="${project}" scope="request"/>
            <jsp:include page="project_bundle_items.jsp"/>
        </div>
    </div>

</c:forEach>

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
                        window.location.replace("${contextPath}/bundle/all");
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