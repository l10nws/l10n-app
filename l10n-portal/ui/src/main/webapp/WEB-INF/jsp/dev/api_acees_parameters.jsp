<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<sec:authentication var="user" property="principal"/>

<form class="form-horizontal" action="#">

  <div class="form-group">
    <label class="col-sm-2 control-label">
      Message Bundle
    </label>

    <div class="col-sm-10">
      <select id="message-bundle" class="form-control">
        <c:forEach var="project" items="${projects}">
          <optgroup label="${project.name}">
            <c:forEach var="bundle" items="${project.messageBundles}">
              <option value="${bundle.uid}">${bundle.label}</option>
            </c:forEach>
          </optgroup>
        </c:forEach>
      </select>
            <span class="help-block">
                Choose message bundle in which you need access parameters
            </span>
    </div>
  </div>

  <div class="form-group">
    <label class="col-sm-2 control-label">
      Bundle Version
    </label>

    <div class="col-sm-10">
      <c:forEach var="project" items="${projects}">
        <c:forEach var="bundle" items="${project.messageBundles}">
          <select id="version-${bundle.uid}" class="version form-control" style="display: none">
            <c:forEach var="version" items="${bundle.versions}">
              <option value="${version.version}">${version.version}</option>
            </c:forEach>
          </select>
        </c:forEach>
      </c:forEach>

    </div>
  </div>

</form>

<h3>API Parameters</h3>

<form class="form-horizontal" action="#">

  <div class="form-group">
    <label class="col-sm-2 control-label">
      Access Token
    </label>

    <div class="col-sm-10">
      <input class="form-control" readonly="readonly" value="${user.accessToken}"/>
    </div>
  </div>

  <div class="form-group">
    <label class="col-sm-2 control-label">
      Bundle Key
    </label>

    <div class="col-sm-10">
      <input id="bundle-key" class="form-control" readonly="readonly" value="${user.accessToken}"/>
    </div>
  </div>

  <div class="form-group">
    <label class="col-sm-2 control-label">
      Bundle Version
    </label>

    <div class="col-sm-10">
      <input id="bundle-version" class="form-control" readonly="readonly" value=""/>
    </div>
  </div>

</form>

<script>
  function applyMessageBundle(bundleKey) {
    $("#bundle-key").val(bundleKey);
    $(".version").hide();
    $("#version-" + bundleKey).show();
    applyBundleVersion($("#version-" + bundleKey).val())
  }

  function applyBundleVersion(bundleVersion) {
    $("#bundle-version").val(bundleVersion);
  }

  $(document).ready(function () {
    var bundleKey = $("#message-bundle").val();
    applyMessageBundle(bundleKey);
    $("#message-bundle").change(function () {
      applyMessageBundle($(this).val());
    });
    $(".version").change(function () {
      applyBundleVersion($(this).val());
    });
  });
</script>