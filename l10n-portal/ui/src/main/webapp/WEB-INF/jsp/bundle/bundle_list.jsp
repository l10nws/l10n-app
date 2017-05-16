<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="content-heading">
    <div class="pull-right">
        <a id="createBundleBtn" href="${contextPath}/bundle/create" class="btn btn-green" role="button">
            <span class="fa fa-plus " aria-hidden="true"></span>
            Create
        </a>
    </div>
    Message Bundles
</div>

<c:set var="listSize" value="${fn:length(projects)}" scope="request"/>
<c:choose>
    <c:when test="${listSize == 0}">
        <jsp:include page="no_bundles.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:include page="bundle_items.jsp"/>
    </c:otherwise>
</c:choose>