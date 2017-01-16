<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/tld/custom.tld" prefix="functions" %>

<div class="content-heading">
    ${bundle eq null ? 'Create Message Bundle' : 'Edit Message Bundle'}
</div>

<ol class="breadcrumb">
    <li><a href="${contextPath}/project/all">Projects</a></li>
    <li>${project.name}</li>
</ol>

<div class="panel panel-default">
    <div class="panel-heading"><h4>Message Bundle</h4></div>
    <div class="panel-body">

        <form method="post" action="${contextPath}/bundle/${bundle eq null ? 'create': 'edit'}/${bundle.id}?projectId=${project.id}" class="form-horizontal"
              data-parsley-validate="" novalidate="">
            <input name="bundleId" value="${bundle.id}" type="hidden"/>
            <input name="projectId" value="${project.id}" type="hidden"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="form-group required">
                <label for="label" class="col-lg-1 control-label">Label</label>

                <div class="col-lg-11">
                    <input id="label" name="label" value="${bundle.label}" type="text" class="form-control"
                           required data-parsley-maxlength="50"/>
                </div>
            </div>

            <c:if test="${bundle == null}">
                <div class="form-group required">
                    <label for="version" class="col-lg-1 control-label">Version</label>

                    <div class="col-lg-11">
                        <input id="version" name="version" placeholder="1.0.0" type="text" class="form-control" required data-parsley-maxlength="50"/>
                    </div>
                </div>
            </c:if>

            <c:if test="${bundle eq null}">
                <div class="form-group">
                    <label for="select-locale" class="col-lg-1 control-label">Default Locale</label>

                    <div class="col-lg-11">
                        <select id="select-locale" name="defaultLocaleId" class="form-control">
                            <c:forEach var="item" items="${locales}">
                                <option value="${item.id}" ${(bundle ne null and bundle.defaultLocaleId eq item.id) or (bundle eq null and item.id eq 'en_US') ? 'selected' : ''}>${item.label}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </c:if>

            <div class="form-group">
                <div class="col-lg-offset-1 col-lg-11">
                    <button id="saveBtn" type="submit" class="btn btn-primary app-btn">${bundle eq null ? 'Create' : 'Save'}</button>
                    <a type="button" class="btn btn-primary app-btn" href="${contextPath}/bundle?p=${project.id}">Back</a>
                </div>
            </div>
        </form>

    </div>

</div>

<script>
    jQuery(document).ready(function () {
        $('#select-locale').select2();
    });
</script>