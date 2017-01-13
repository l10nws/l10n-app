<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="no-items">
  <h4>You have no Message Bundles</h4>
  Firstly you have to <a href="${contextPath}/bundle/create">create</a> a new Message Bundle
  <em class="fa fa-code"></em>
</div>