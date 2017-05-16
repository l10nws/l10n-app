<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="content-heading">
    <div class="pull-right">
        <a id="createVersionBtn" href="${contextPath}/version/create?bundleId=${bundleId}" class="btn btn-green" role="button">
            <span class="fa fa-plus " aria-hidden="true"></span>
            Create
        </a>
    </div>
    Versions
</div>
<ol class="breadcrumb">
    <li><a href="${contextPath}/project/all">Projects</a></li>
    <li><a href="${contextPath}/bundle?p=${bundle.projectId}" class="project-name">${projectName}</a></li>
    <li class="active bundle-label">${messageBundleLabel}</li>
</ol>
<!-- START panel-->
<div class="panel panel-default">
    <div class="panel-heading">
        <h4>Versions</h4>
    </div>
    <div class="panel-body">
        <!-- START table-responsive-->
        <div class="table-responsive">
            <table class="table table-hover">
                <c:set var="versionCount" value="${fn:length(versions)}" scope="request"/>
                <thead>
                <tr>
                    <th class="wd-sm">
                        Version
                    </th>
                    <th class="wd-sm">
                        Default Locale
                    </th>
                    <th colspan="2">
                        Created on
                    </th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="version" items="${versions}">
                        <tr class="clickable-row version-item" data-href='${contextPath}/message?v=${version.id}'>
                            <td class="version-name">${version.version}</td>
                            <td class="version-locale">
                                  ${version.defaultLocaleId}
                            </td>
                            <td class="version-date">
                                <fmt:formatDate value="${version.creationDate}" dateStyle="long"/>
                            </td>

                            <td class="wd-sm">
                                <sec:authorize access="@permissionChecker.canWriteVersion(#version.id)" var="canWrite"/>
                                <c:if test="${canWrite}">
                                    <div class="pull-right action-group">
                                        <a href="${contextPath}/version/edit/${version.id}"
                                           class="btn btn-default pull-left fa fa-pencil version-edit-btn" role="button">
                                        </a>
                                        <c:if test="${versionCount > 1}">
                                            <a data-version-id="${version.id}" data-version="${version.version}" href="javascript:function f(){}"
                                               class="btn btn-default pull-left fa fa-trash delete-version" role="button">
                                            </a>
                                        </c:if>
                                    </div>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <!-- END table-responsive-->
        <a class="mb-sm btn btn-primary app-btn" href="${contextPath}/bundle?p=${bundle.projectId}" style="margin-top: 20px">Back</a>
    </div>
</div>

<div id="deleteVersionModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">Delete Version</h4>
            </div>
            <div class="modal-body">
                <p>Are you sure that you want to delete <strong class="version"></strong>?</p>
            </div>
            <div class="modal-footer">
                <a href="javascript:function f(){}" type="button" class="ok btn btn-primary delete-yes">Yes</a>
                <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    jQuery(document).ready(function($) {

        $(".clickable-row").click(function() {
            window.document.location = $(this).data("href");
        });

        $("#deleteVersionModal").find(".ok").on('click', function() {
            var versionId = $(this).data("version-id");
            $.post('${contextPath}/version/delete/' + versionId,
                    {
                        "${_csrf.parameterName}": "${_csrf.token}"
                    },
                    function (data) {
                        window.location.replace("${contextPath}/version?b=${bundle.id}");
                    }
            );
        });
        $(".delete-version").click(function (e) {
            e.stopPropagation();
            var versionId = $(this).data("version-id");
            var versionLabel = $(this).data("version");
            $("#deleteVersionModal").modal();
            $("#deleteVersionModal").insertAfter(".modal-backdrop");
            $("#deleteVersionModal").find(".version").html(versionLabel);
            $("#deleteVersionModal").find(".ok").data("version-id", versionId);
        });

    });
</script>
