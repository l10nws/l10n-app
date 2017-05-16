<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<div class="content-heading">
    ${project eq null ? 'Create Project' : 'Edit Project'}
</div>

<ol class="breadcrumb">
    <li><a href="${contextPath}/project/all">Projects</a></li>
    <c:if test="${project ne null}">
        <li class="project-name-breadcrumb">${project.name}</li>
    </c:if>
</ol>

<div class="panel panel-default">
    <div class="panel-heading"><h4>Project</h4></div>
    <div class="panel-body">
        <form class="form-horizontal" method="post" action="${contextPath}/project/${project eq null ? 'create' : 'update'}"
              data-parsley-validate="" novalidate="">

            <input name="id" value="${project.id}" type="hidden" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="form-group required">
                <label class="col-lg-1 control-label" for="name">Name</label>
                <div class="col-lg-11">
                    <input id="name" name="name" value="${project.name}" type="text" class="form-control" required data-parsley-maxlength="50" />
                </div>
            </div>

            <div class="form-group">
                <label class="col-lg-1 control-label" for="description">Description</label>
                <div class="col-lg-11">
                    <textarea  id="description" name="description" class="form-control" rows="5">${project.description}</textarea>
                </div>
            </div>

            <div class="form-group">
                <div class="col-lg-offset-1 col-lg-11">
                    <button id="saveBtn" type="submit" class="btn btn-primary app-btn">${project eq null ? 'Create' : 'Save'}</button>
                    <a type="button" class="btn btn-primary app-btn" href="${contextPath}/project/all">Back</a>
                </div>
            </div>
        </form>
    </div>
</div>
