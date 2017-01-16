<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="content-heading">
    <c:choose>
        <c:when test="${version eq null}">
            Create Version
        </c:when>
        <c:otherwise>
            Edit Version
        </c:otherwise>
    </c:choose>
</div>

<div class="panel panel-default">
    <div class="panel-heading"><h4>Version</h4></div>
    <div class="panel-body">
        <form class="form-horizontal" method="post"
              action="${contextPath}/version/${version eq null ? 'create' : 'update'}"
              data-parsley-validate="" novalidate="">

            <input name="id" value="${version.id}" type="hidden"/>
            <input name="bundleId" value="${bundleId}" type="hidden"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="form-group required">
                <label class="col-lg-2 control-label" for="version">Label</label>

                <div class="col-lg-10">
                    <input id="version" name="version" value="${version.version}" type="text" class="form-control"
                           required data-parsley-maxlength="50" data-parsley-trigger="change"
                           data-parsley-remote-options='{"type": "POST", "dataType": "jsonp", "data": { "${_csrf.parameterName}": "${_csrf.token}", "bundleId" : "${bundleId}", "versionId" : "${version eq null ? '' : version.id}" }}'
                           data-parsley-remote-message="This version already exists."
                           data-parsley-remote="${contextPath}/version/exist" data-parsley-remote-validator="exist"/>
                </div>
            </div>
            <c:choose>
                <c:when test="${version eq null}">
                    <div class="form-group">
                        <label class="col-lg-2 control-label" for="create-from-version">Create from</label>

                        <div class="col-lg-10">
                            <select id="create-from-version" name="fromVersionId" class="form-control">
                                <c:forEach var="item" items="${versions}">
                                    <option value="${item.id}" ${item.id eq param.versionId ? "selected" : ""}>${item.version}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="form-group">
                        <label class="col-lg-2 control-label" for="default-locale">Default Locale</label>

                        <div class="col-lg-10">
                            <select id="default-locale" name="defaultLocaleId" class="form-control">
                                <c:forEach var="locale" items="${locales}">
                                    <option value="${locale.id}" ${locale.id eq version.defaultLocaleId ? "selected" : ""}>${locale.label}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>

            <div class="form-group">
                <div class="col-lg-offset-2 col-lg-10">
                    <button id="saveBtn" type="submit" class="btn btn-primary app-btn">${version eq null ? 'Create' : 'Save'}</button>
                    <a type="button" class="btn btn-primary app-btn" href="${contextPath}/version?b=${bundleId}">Back</a>
                </div>
            </div>
        </form>
    </div>
</div>
