<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tld/custom.tld" prefix="functions" %>

<sec:authorize access="@permissionChecker.canWriteVersion(#versionId)" var="canWrite"/>

<div class="content-heading">

    <c:if test="${canWrite}">

        <div class="pull-right" style="margin-left: 10px;">
            <!-- Button trigger modal -->
            <button id="upload" type="button" class="btn btn-green upload-btn">
                <span class="fa icon-cloud-upload " aria-hidden="true"></span>
                Upload
            </button>
        </div>

    </c:if>

    <div class="pull-right">
        <!-- Button trigger modal -->
        <a href="${contextPath}/message/download?localeId=${selectedLocaleId}&versionId=${versionId}" type="button"
           class="btn btn-green download-btn">
            <span class="fa icon-cloud-download " aria-hidden="true"></span>
            Download
        </a>
    </div>

    Messages
</div>

<ol class="breadcrumb">
    <li><a href="${contextPath}/project/all">Projects</a></li>
    <li class="active">
        <a href="${contextPath}/bundle?p=${breadcrumbs.project_id}" class="bc-project-name">${breadcrumbs.project_name}</a>
    </li>
    <li class="active">
        <a href="${contextPath}/version?b=${breadcrumbs.bundle_id}" class="bc-bundle-name">${breadcrumbs.bundle_label}</a>
    </li>
</ol>

<!-- Nav tabs -->
<ul id="messagesTabs" class="nav nav-tabs message-tabs" role="tablist" data-tabs="messagesTabs">

    <c:forEach var="locale" items="${createdLocales}" varStatus="index">
        <li role="presentation" class="${locale.id eq selectedLocaleId ? 'active' : ''} locale-item">
            <a href="${contextPath}/message?v=${versionId}&l=${locale.id}" aria-controls="${locale.id}" class="locale-label">
                    ${locale.id}
                <c:if test="${defaultLocale.id eq locale.id}">
                    <i class="fa fa-map-pin default-locale" title="Default Locale"></i>
                </c:if>
            </a>
        </li>
    </c:forEach>

    <c:if test="${canWrite}">
        <li role="presentation">
            <a id="addLocale" href="javascript: function f(){}" class="fa fa-plus"></a>
        </li>
    </c:if>

</ul>

<!-- Tab panes -->
<div class="tab-content">
    <c:forEach var="locale" items="${createdLocales}" varStatus="index">
        <div role="tabpanel" class="tab-pane ${locale.id eq selectedLocaleId ? 'active' : ''}" id="${locale.id}">
            <c:if test="${locale.id eq selectedLocaleId}">

                <c:if test="${canWrite}">

                    <div class="pull-right">
                        <div class="dropdown dropdown-action">
                            <button class="btn btn-default dropdown-toggle action-btn" type="button" id="dropdownAction"
                                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                                <span class="material-icons md-24">more_vert</span>
                            </button>
                            <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownAction">
                                <li>
                                    <a class="set-default-locale" href="javascript:function f(){}"><i
                                            class="fa fa-map-pin"></i> Set as Default</a>
                                </li>
                                <li class="${defaultLocale.id eq selectedLocaleId ? 'disabled' : ''}">
                                    <a class="${defaultLocale.id eq selectedLocaleId ? '' : 'delete-locale'}"
                                       href="javascript:function f(){}"><i class="fa fa-trash"></i> Delete</a>
                                </li>
                            </ul>
                        </div>
                    </div>

                </c:if>

                <h4>${locale.label}</h4>

                <c:if test="${canWrite}">

                    <div class="toolbar">
                        <a id="add-new-message" role="button" class="btn btn-default">
                            <i class="fa fa-plus"></i>
                        </a>
                        <a id="addToAll" role="button" class="btn btn-default add-all">
                            <i class="fa fa-plus"></i>
                            <i class="fa fa-bars"></i>
                        </a>
                        <a id="delete-message" role="button" class="btn btn-default disabled pull-right">
                            <i class="fa fa-trash"></i>
                        </a>
                    </div>

                </c:if>

                <form action="${contextPath}/message" method="post" class="form-horizontal"
                      data-parsley-validate="" novalidate="">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="hidden" name="versionId" value="${versionId}"/>
                    <input type="hidden" name="localeId" value="${selectedLocaleId}"/>

                    <table class="table table-hover message-table">
                        <thead>
                        <tr>
                            <th class="number">#</th>
                            <th class="key">Key</th>
                            <th class="value">Value</th>
                            <c:if test="${canWrite}">
                                <th class="check">
                                    <label class="checkbox-inline c-checkbox select-all">
                                        <input type="checkbox" name="message${message.id}">
                                        <span class="fa fa-check"></span>
                                    </label>
                                </th>
                            </c:if>
                        </tr>
                        </thead>
                        <tbody id="messages-container">
                        <c:set var="maxLenght" value="70"/>
                        <c:set var="truncateOffset" value="7"/>
                        <c:if test="${empty messages}">
                            <tr>
                                <c:choose>
                                    <c:when test="${canWrite}">
                                        <td class="no-record" colspan="4">
                                            no record found
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td class="no-record" colspan="3">
                                            no record found
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                        </c:if>
                        <c:forEach items="${messages}" var="message" varStatus="status">
                            <tr class="${ canWrite ? 'clickable-row' : ''} message-table-row"
                                data-message-key="${message.key}">
                                <td class="number">
                                        ${status.index + 1}.
                                </td>
                                <td class="key">
                                    <c:choose>
                                        <c:when test="${fn:length(message.key) > maxLenght + truncateOffset}">
                                            <span class="tooltipster" title="${message.key}">
                                                ${fn:substring(message.key, 0, truncateOffset)}...${fn:substring(message.key, fn:length(message.key) - maxLenght + truncateOffset, fn:length(message.key))}
                                            </span>
                                        </c:when>
                                        <c:otherwise>
                                            ${message.key}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="value">
                                    <c:choose>
                                        <c:when test="${fn:length(message.value) > maxLenght + truncateOffset}">
                                            <span class="tooltipster" title="${message.value}">
                                                ${fn:substring(message.value, 0, truncateOffset)}...${fn:substring(message.value, fn:length(message.value) - maxLenght + truncateOffset, fn:length(message.value))}
                                            </span>
                                        </c:when>
                                        <c:otherwise>
                                            ${message.value}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <c:if test="${canWrite}">
                                    <td class="check">
                                        <label class="checkbox-inline c-checkbox check-to-delete">
                                            <input  type="checkbox" name="id" data-message-key="${message.key}">
                                            <span class="fa fa-check selectCbx"></span>
                                        </label>
                                    </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                    <c:if test="${canWrite}">
                        <button id="saveBtn" type="submit" class="btn btn-primary app-btn">Save</button>
                    </c:if>
                    <a type="button" class="btn btn-primary app-btn"
                       href="${contextPath}/version?b=${breadcrumbs.bundle_id}">Back</a>

                </form>
            </c:if>
        </div>
    </c:forEach>
</div>


<script>

    function first(obj) {
        for (var i in obj) return i;
    }

    function deleteRow(index) {
        $(".template-row" + index).remove();
        var size = $("#messages-container tr.clickable-row").size() + 1;
        $("#messages-container tr.template").each(function () {
            $(this).find(".row-index").text(size);
            size++;
        });
    }

    jQuery(document).ready(function () {

        window.Parsley.addValidator('uniquekey', {
            validateString: function(value) {
                var array = $(".template-key").toArray();
                var index = 0;
                var result = true;
                for (var i = 0; i < array.length; i++) {
                    var obj = array[i];
                    if($(obj).val() == value && ++index > 1) {
                        result = false;
                        break;
                    }
                }
                return result;
            },
            messages: {
                en: 'Duplicate value.'
            }
        });

        //--------------------------------------------------------------------------------------------------------------

        $('#select-locale').select2();

        $("#addToAll").click(function () {
            $("#addToAllModal").modal();
            $("#addToAllModal").insertAfter(".modal-backdrop");
        });

        $("#addLocale").click(function () {
            $("#addLocaleModal").modal();
            $("#addLocaleModal").insertAfter(".modal-backdrop");
        });

        $("#upload").click(function () {
            $("#uploadModal").modal();
            $("#uploadModal").insertAfter(".modal-backdrop");
        });

        $(".set-default-locale").click(function () {
            $.post('${contextPath}/version/locale',
                    {
                        "${_csrf.parameterName}": "${_csrf.token}",
                        "defaultLocaleId": "${selectedLocaleId}",
                        "versionId": "${versionId}"
                    },
                    function (data) {
                        window.location.replace("${contextPath}/message?v=${versionId}&l=${selectedLocaleId}");
                    }
            );
        });

        $(".delete-locale").click(function () {
            $("#deleteLocaleModal").modal();
            $("#deleteLocaleModal").insertAfter(".modal-backdrop");
        });

        $(".clickable-row").click(function () {
            $("#editMessageModal").modal();
            $("#editMessageModal").insertAfter(".modal-backdrop");
            var key = $(this).data("message-key");
            $.getJSON('${contextPath}/message/find?versionId=${versionId}&key=' + key, function (json) {
                var keyValue = json[first(json)].key;
                var parsleyRemoteOptions = $("#editMessageModal").find("input.key").data("parsley-remote-options");
                parsleyRemoteOptions.data.oldKey = keyValue;
                $("#editMessageModal").find("input.key").attr("data-parsley-remote-options", JSON.stringify(parsleyRemoteOptions));
                $("#editMessageModal").find("input.key").val(keyValue);

                $("#editMessageModal").find("textarea.value").each(function () {
                    var localeId = $(this).data("locale-id");
                    if (json[localeId]) {
                        $(this).val(json[localeId].value);
                        $("#editMessageModal").find("#mid_" + localeId).val(json[localeId].id);
                    }
                });
            });
        });

        $(".check-to-delete").click(function (e) {
            e.stopPropagation();
        });

        $(".select-all input").click(function () {
            var checked = $(this).prop("checked");
            $("#messages-container input[type='checkbox']").prop("checked", checked);
            if (checked) {
                $("#delete-message").removeClass("disabled");
            } else {
                $("#delete-message").addClass("disabled");
            }
        });

        $("#messages-container input[type='checkbox']").click(function () {
            if ($("#messages-container input[type='checkbox']:checked").length > 0) {
                $("#delete-message").removeClass("disabled");
            } else {
                $("#delete-message").addClass("disabled");
                $(".select-all input").prop("checked", false);
            }
        });
        // -------------------------------------------------------------------------------------------------------------
        var messageListTemplate = Handlebars.compile($("#message-list-template").html());

        $("#delete-message").click(function () {
            $("#addDeleteMessageModal").modal();
            $("#addDeleteMessageModal").insertAfter(".modal-backdrop");

            var keys = [];
            $("#messages-container input[type='checkbox']:checked").each(function () {
                keys.push($(this).data("message-key"));
            });

            var html = messageListTemplate({keys: keys});

            $("#addDeleteMessageModal").find("#message-list").html(html);

            $("#deleteMessageBtn").click(function () {
                $.post('${contextPath}/message/delete',
                        {
                            "${_csrf.parameterName}": "${_csrf.token}",
                            "keys": keys,
                            "localeId": "${selectedLocaleId}",
                            "versionId": "${versionId}",
                            "deleteFromAllLocales": $(".delete-from-all").prop("checked")
                        },
                        function (data) {
                            window.location.replace("${contextPath}/message?v=${versionId}&l=${selectedLocaleId}");
                        }
                );
            });
        });

        // -------------------------------------------------------------------------------------------------------------
        var newMessageTemplate = Handlebars.compile($("#message-template").html());

        $('#add-new-message').click(function () {

            $(".no-record").remove();

            var index = $("#messages-container .message-table-row").size() + 1;
            var html = newMessageTemplate({index: index});

            $("#messages-container").append(html);

            $(".template-key" + index).focus();

            $('.message-area').focusin(function () {
                $(this).addClass('expanded');
            });

            $(".message-area").focusout(function () {
                $(this).removeClass('expanded');
            });

            $(".message-area").keydown(function (e) {
                if (e.keyCode == 9 && $(this).get(0) == $(".message-area:last").get(0)) {
                    $('#add-new-message').click();
                    $(this).focus();
                }
            });

        });


    });
</script>

<script id="message-template" type="text/x-handlebars-template">
    <tr class="template template-row{{index}} message-table-row">
        <td class="number">
            <span class="row-index">{{index}}</span>.
        </td>
        <td class="key">
            <input name="key" type="text" class="form-control template-key template-key{{index}}" placeholder="Key"
                   required
                   data-parsley-uniquekey
                   data-parsley-trigger="change"
                   data-parsley-remote-options='{"type": "POST", "dataType": "jsonp", "data": { "${_csrf.parameterName}": "${_csrf.token}", "versionId" : "${versionId}"}}'
                   data-parsley-remote-message="This key already exists."
                   data-parsley-remote="${contextPath}/message/create/exist"
                   data-parsley-remote-validator="exist"/>
        </td>
        <td class="value">
            <textarea name="value" class="form-control message-area" placeholder="Value"></textarea>
        </td>
        <c:if test="${canWrite}">
            <td class="check">
                <a href="javascript:function f(){}" tabindex="-1" onclick="deleteRow({{index}})"><i
                        class="fa fa-times-circle-o remove-row-icon"></i></a>
            </td>
        </c:if>
    </tr>
</script>

<script id="message-list-template" type="text/x-handlebars-template">
    {{#list keys}}{{key}}{{/list}}
</script>

<div id="deleteLocaleModal" tabindex="-1" role="dialog" aria-labelledby="deleteLocaleLabel" aria-hidden="true"
     class="modal fade">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" data-dismiss="modal" aria-label="Close" class="close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h5 id="deleteLocaleLabel" class="modal-title">Are you sure you want to delete
                    locale ${selectedLocaleId}?</h5>
            </div>

            <form action="${contextPath}/message/locale/delete" method="post" data-parsley-validate="" novalidate="">

                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="versionId" value="${versionId}"/>
                <input type="hidden" name="localeId" value="${selectedLocaleId}"/>

                <div class="modal-footer">
                    <input type="submit" class="btn btn-primary app-btn" value="Delete"/>
                    <button type="button" data-dismiss="modal" class="btn btn-default app-btn">Cancel</button>
                </div>

            </form>

        </div>
    </div>
</div>

<div id="editMessageModal" tabindex="-1" role="dialog" aria-labelledby="editMessageLabel" aria-hidden="true"
     class="modal fade edit-message">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" data-dismiss="modal" aria-label="Close" class="close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 id="editMessageLabel" class="modal-title">Edit Message</h4>
            </div>

            <form action="${contextPath}/message/update" method="post" data-parsley-validate="" novalidate="">

                <div class="modal-body">

                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="hidden" name="versionId" value="${versionId}"/>
                    <input type="hidden" name="selectedLocaleId" value="${selectedLocaleId}"/>

                    <c:forEach var="locale" items="${createdLocales}" varStatus="index">

                        <div class="row" style="margin-bottom: 10px">

                            <c:if test="${index.first}">
                                <div class="col-md-6">
                                    <input name="key" type="text" class="form-control key" placeholder="Key" value=""
                                           required
                                           data-parsley-trigger="change"
                                           data-parsley-remote-options='{"type": "POST", "dataType": "jsonp", "data": { "${_csrf.parameterName}": "${_csrf.token}", "versionId" : "${versionId}", "localeId" : "${selectedLocaleId}"}}'
                                           data-parsley-remote-message="This key already exists."
                                           data-parsley-remote="${contextPath}/message/edit/exist"
                                           data-parsley-remote-validator="exist">
                                </div>
                            </c:if>

                            <div class="col-md-6 ${index.first ? '' : 'col-md-offset-6'}">
                                <div class="input-group">
                                    <input type="hidden" name="localeId" value="${locale.id}">
                                    <input id="mid_${locale.id}" type="hidden" name="messageId" value="-1">
                                    <textarea name="value" class="form-control value" placeholder="Value"
                                              data-locale-id="${locale.id}" rows="5"></textarea>
                                    <div class="input-group-addon">${locale.id}</div>
                                </div>
                            </div>

                        </div>

                    </c:forEach>

                </div>

                <div class="modal-footer">
                    <input type="submit" class="btn btn-primary app-btn save-btn" value="Save"/>
                    <button type="button" data-dismiss="modal" class="btn btn-default app-btn">Cancel</button>
                </div>

            </form>


        </div>
    </div>
</div>

<div id="addToAllModal" tabindex="-1" role="dialog" aria-labelledby="addNewMessageLabel" aria-hidden="true"
     class="modal fade add-message">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" data-dismiss="modal" aria-label="Close" class="close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 id="addNewMessageLabel" class="modal-title">New Message</h4>
            </div>

            <form action="${contextPath}/message/create" method="post" data-parsley-validate="" novalidate="">

                <div class="modal-body">

                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="hidden" name="versionId" value="${versionId}"/>
                    <input type="hidden" name="selectedLocaleId" value="${selectedLocaleId}"/>

                    <c:forEach var="locale" items="${createdLocales}" varStatus="index">

                        <div class="row" style="margin-bottom: 10px">

                            <c:if test="${index.first}">
                                <div class="col-md-6">
                                    <input name="key" type="text" class="form-control" placeholder="Key" value=""
                                           required
                                           data-parsley-trigger="change"
                                           data-parsley-remote-options='{"type": "POST", "dataType": "jsonp", "data": { "${_csrf.parameterName}": "${_csrf.token}", "versionId" : "${versionId}"}}'
                                           data-parsley-remote-message="This key already exists."
                                           data-parsley-remote="${contextPath}/message/create/exist"
                                           data-parsley-remote-validator="exist">
                                </div>
                            </c:if>

                            <div class="col-md-6 ${index.first ? '' : 'col-md-offset-6'}">
                                <div class="input-group value-in-locale">
                                    <input type="hidden" name="localeId" value="${locale.id}">
                                    <textarea name="value" class="form-control value" placeholder="Value"
                                              rows="5"></textarea>
                                    <div class="input-group-addon">${locale.id}</div>
                                </div>
                            </div>

                        </div>

                    </c:forEach>

                </div>

                <div class="modal-footer">
                    <input type="submit" class="btn btn-primary app-btn add-btn" value="Add"/>
                    <button type="button" data-dismiss="modal" class="btn btn-default app-btn">Cancel</button>
                </div>

            </form>


        </div>
    </div>
</div>

<div id="addLocaleModal" role="dialog" aria-labelledby="addLocaleModalLabel" aria-hidden="true" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" data-dismiss="modal" aria-label="Close" class="close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 id="addLocaleModalLabel" class="modal-title">Add Locale</h4>
            </div>

            <form action="${contextPath}/message/locale/add" method="POST">

                <div class="modal-body">

                    <input type="hidden" name="versionId" value="${versionId}"/>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="select-locale" class="col-sm-2 control-label">Locale</label>
                                <select id="select-locale" name="localeId" class="" style="width: 75%">
                                    <c:forEach var="item" items="${locales}">
                                        <c:if test="${not functions:contains(createdLocales, item)}">
                                            <option value="${item.id}" ${item.id eq 'en_US' ? 'selected' : ''}>${item.label}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-10 col-md-offset-2">
                            <label class="checkbox-inline c-checkbox">
                                <input id="shouldCopy" name="shouldCopy" type="checkbox">
                                <span class="fa fa-check"></span>
                                Copy existing messages from default locale <b>${defaultLocale.label}</b>
                            </label>
                        </div>
                    </div>

                </div>

                <div class="modal-footer">
                    <input type="submit" class="btn btn-primary app-btn add-btn" value="Add"/>
                    <button type="button" data-dismiss="modal" class="btn btn-default app-btn">Cancel</button>
                </div>

            </form>

        </div>
    </div>
</div>


<div id="addDeleteMessageModal" role="dialog" aria-labelledby="addDeleteMessageLabel" aria-hidden="true"
     class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" data-dismiss="modal" aria-label="Close" class="close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 id="addDeleteMessageLabel" class="modal-title">Delete Message(s)</h4>
            </div>

            <div class="modal-body">
                <p>Are you sure that you want delete the selected message(s)?</p>
                <div id="message-list" class="row">
                </div>
                <label class="checkbox-inline c-checkbox">
                    <input class="delete-from-all" value="true" type="checkbox">
                    <span class="fa fa-check"></span>
                    Delete from all locales
                </label>
            </div>

            <div class="modal-footer">
                <input id="deleteMessageBtn" type="submit" class="btn btn-primary app-btn" value="Delete"/>
                <button type="button" data-dismiss="modal" class="btn btn-default app-btn">Cancel</button>
            </div>

        </div>
    </div>
</div>

<div id="uploadModal" tabindex="-1" role="dialog" aria-labelledby="uploadModalLabel" aria-hidden="true"
     class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" data-dismiss="modal" aria-label="Close" class="close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 id="uploadModalLabel" class="modal-title">Upload message properties file</h4>
            </div>

            <form action="${contextPath}/message/upload?${_csrf.parameterName}=${_csrf.token}" method="POST"
                  enctype="multipart/form-data">

                <input type="hidden" name="versionId" value="${versionId}"/>
                <input type="hidden" name="localeId" value="${selectedLocaleId}"/>

                <div class="modal-body">

                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="upload-file" class="col-sm-2 control-label">Select file</label>
                                <input id="upload-file" name="file" type="file" class="file"
                                       data-preview-file-type="text">
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-10 col-md-offset-2">
                            <label class="checkbox-inline c-checkbox">
                                <input id="replaceExisted" name="replaceExisted" type="checkbox">
                                <span class="fa fa-check"></span>
                                Replace existing messages
                            </label>
                        </div>
                    </div>

                </div>

                <div class="modal-footer">
                    <input type="submit" class="btn btn-primary app-btn" value="Upload"/>
                    <button type="button" data-dismiss="modal" class="btn btn-default app-btn">Cancel</button>
                </div>

            </form>

        </div>
    </div>
</div>

