<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<sec:authentication var="user" property="principal"/>

<div class="list-group project-item">
    <a href="${contextPath}/bundle?p=${project.id}" class="list-group-item">
        <table class="wd-wide">
            <tbody>
            <tr>
                <td class="wd-auto">
                    <div class="ph">
                        <h4 class="media-box-heading project-name" >${project.name}</h4>
                        <small class="text-muted project-description"><c:out value="${project.description}"/></small>
                    </div>
                </td>
                <td class="wd-xxs hidden-xs hidden-sm">
                    <div class="ph relative">
                        <p class="m0 text-muted">
                            <em class="icon-layers m0 fa-lg"></em>
                        </p>
                        <div class="label label-info under-label">${project.bundleCount}</div>
                    </div>
                </td>
                <td class="wd-xs hidden-xs hidden-sm">
                    <div class="ph relative">
                        <p class="m0 text-muted">
                            <em class="icon-people m0 fa-lg"></em>
                        </p>
                        <div class="label label-info under-label">${project.userCount}</div>
                    </div>
                </td>
                <td class="wd-xl">
                    <div class="text-muted">
                        <small class="mr-sm">Created by</small>
                        <a href="mailto:${project.ownerEmail}" class="mr-sm project-owner">${project.ownerEmail}</a>
                        <small> on <fmt:formatDate value="${project.creationDate}" dateStyle="MEDIUM"/></small>
                    </div>
                </td>
                <td class="wd-sd">
                    <c:if test="${project.ownerId == user.id}">
                        <div class="pull-right action-group">
                            <a href="${contextPath}/project/config/${project.id}"
                               class="btn btn-default fa fa-user-plus app-item-btn" role="button">
                            </a>
                            <a href="${contextPath}/project/edit/${project.id}"
                               class="btn btn-default fa fa-pencil app-item-btn project-edit" role="button">
                            </a>
                            <a href="javascript:function f(){}"
                               class="delete-project btn btn-default fa fa-trash app-item-btn" role="button"
                               data-project-id="${project.id}" data-project-name="${project.name}">
                            </a>
                        </div>
                    </c:if>
                </td>
            </tr>
            </tbody>
        </table>
    </a>
</div>
