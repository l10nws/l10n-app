<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<sec:authentication var="user" property="principal"/>

<c:forEach items="${project.messageBundles}" var="bundle">

    <div class="list-group bundle-item">

        <a href="${contextPath}/version?b=${bundle.id}" class="list-group-item">
            <table class="wd-wide">
                <tbody>
                <tr>
                    <td class="wd-auto">
                        <div class="ph">
                            <h4 class="media-box-heading bundle-label">${bundle.label}</h4>
                            <small class="text-muted">
                                Versions:
                                <c:forEach var="version" items="${bundle.versions}">
                                    <a href="${contextPath}/message?v=${version.id}">
                                        <span class="ml bundle-version">${version.version}</span>
                                    </a>
                                </c:forEach>
                            </small>
                        </div>
                    </td>
                    <td class="wd-xl">
                        <div class="text-muted">
                            <small class="mr-sm">Created by</small>
                            <a href="mailto:${bundle.ownerEmail}" class="mr-sm bundle-owner">${bundle.ownerEmail}</a>
                            <small> on <fmt:formatDate value="${bundle.creationDate}" dateStyle="MEDIUM"/></small>
                        </div>
                    </td>
                    <td class="wd-sm">
                        <sec:authorize access="@permissionChecker.canWriteBundle(#bundle.id)" var="canWrite"/>
                        <c:if test="${canWrite}">
                            <div class="pull-right action-group">
                                <a href="${contextPath}/bundle/edit/${bundle.id}" class="btn btn-default fa fa-pencil app-item-btn version-edit-btn" role="button"></a>
                                <a href="javascript:function f(){}" class="delete-project-bundle btn btn-default fa fa-trash app-item-btn" role="button" data-bundle-id="${bundle.id}" data-bundle-label="${bundle.label}">
                                </a>
                            </div>
                        </c:if>
                    </td>
                </tr>
                </tbody>
            </table>
        </a>

    </div>

</c:forEach>
