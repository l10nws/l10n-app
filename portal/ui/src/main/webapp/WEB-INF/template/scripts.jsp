<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- =============== VENDOR SCRIPTS ===============-->
<!-- MODERNIZR-->
<script src="${contextPath}/resources/vendor/modernizr/modernizr.js"></script>

<!-- BOOTSTRAP-->
<script src="${contextPath}/resources/vendor/bootstrap/dist/js/bootstrap.js"></script>
<!-- STORAGE API-->
<script src="${contextPath}/resources/vendor/jQuery-Storage-API/jquery.storageapi.js"></script>
<!-- JQUERY EASING-->
<script src="${contextPath}/resources/vendor/jquery.easing/js/jquery.easing.js"></script>
<!-- ANIMO-->
<script src="${contextPath}/resources/vendor/animo.js/animo.js"></script>
<!-- SLIMSCROLL-->
<script src="${contextPath}/resources/vendor/slimScroll/jquery.slimscroll.min.js"></script>


<%--

<!-- SCREENFULL-->
<script src="${contextPath}/resources/vendor/screenfull/dist/screenfull.js"></script>
<!-- LOCALIZE-->
<script src="${contextPath}/resources/vendor/jquery-localize-i18n/dist/jquery.localize.js"></script>
<!-- RTL demo-->
<script src="${contextPath}/resources/js/demo/demo-rtl.js"></script>

<!-- PARSLEY-->
<script type="text/javascript">

    window.ParsleyConfig = {
        validators: {
            requiredafter: {
                fn: function (value, selector) {
                    return $(selector).val() == '' || ($(selector).val() != '' && '' != value);

                },
                priority: 32
            }
        }
    };

</script>

--%>

<script src="${contextPath}/resources/vendor/parsleyjs/dist/parsley.min.js"></script>
<script>
    window.Parsley.addAsyncValidator('exist', function (xhr) {
        return 404 === xhr.status;
    });
</script>
<script src="${contextPath}/resources/vendor/select2/js/select2.min.js"></script>
<!-- ToolTipster http://iamceege.github.io/tooltipster/ -->
<script src="${contextPath}/resources/vendor/tooltipster/js/jquery.tooltipster.min.js"></script>
<!-- =============== PAGE VENDOR SCRIPTS ===============-->

<!-- =============== APP SCRIPTS ===============-->
<script src="${contextPath}/resources/js/handlebars-v3.0.3.js"></script>
<script src="${contextPath}/resources/js/app.js"></script>
<script src="${contextPath}/resources/js/fileinput.min.js"></script>

<script type="text/javascript">

    $(document).ready(function(){

        Handlebars.registerHelper('list', function (items, options) {
            var out = "<ul>";
            for (var i = 0, l = items.length; i < l; i++) {
                out = out + "<li>" + items[i] + "</li>";
            }
            return out + "</ul>";
        });

        $(".tooltipster").tooltipster({
            interactive: true,
            theme: 'tooltipster-shadow',
            contentAsHTML: true
        });

        var startWith = window.location.pathname.substring("${contextPath}".length + 1).substring(0, 3);
        $("nav.sidebar .nav").find("a[href*='" + startWith + "']").parent().addClass("active");

    });
</script>