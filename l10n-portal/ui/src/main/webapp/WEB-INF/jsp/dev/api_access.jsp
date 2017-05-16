<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<div class="content-heading">
    API Access
</div>

<c:choose>
    <c:when test="${not empty projects}">
        <jsp:include page="api_acees_parameters.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:include page="api_access_empty.jsp"/>
    </c:otherwise>
</c:choose>


