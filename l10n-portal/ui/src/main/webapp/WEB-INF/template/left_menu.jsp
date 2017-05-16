<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<aside class="aside">
    <!-- START Sidebar (left)-->
    <div class="aside-inner">
        <nav data-sidebar-anyclick-close="" class="sidebar">
            <!-- START sidebar nav-->
            <ul class="nav">
                <!-- START user info-->
                <%--<li class="has-user-block">--%>
                    <%--<div id="user-block" class="collapse">--%>
                        <%--<div class="item user-block">--%>
                            <%--<!-- User picture-->--%>
                            <%--<div class="user-block-picture">--%>
                                <%--<div class="user-block-status">--%>
                                    <%--<img src="resources/img/user/02.jpg" alt="Avatar" width="60" height="60" class="img-thumbnail img-circle">--%>
                                    <%--<div class="circle circle-success circle-lg"></div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<!-- Name and Job-->--%>
                            <%--<div class="user-block-info">--%>
                                <%--<span class="user-block-name">Hello, Mike</span>--%>
                                <%--<span class="user-block-role">Designer</span>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</li>--%>
                <!-- END user info-->
                <!-- Iterates over all sidebar items-->
                <li class="nav-heading">
                    <span>Main Navigation</span>
                </li>
                <li class="">
                    <a href="${contextPath}/project/all" title="Projects">
                        <em class="icon-grid"></em>
                        <span>Projects</span>
                    </a>
                </li>
                <li class="">
                    <a href="${contextPath}/bundle/all" title="Message Bundles">
                        <em class="icon-layers"></em>
                        <span>Message Bundles</span>
                    </a>
                </li>

                <sec:authorize access="hasRole('SUPERUSER') || hasRole('ADMIN')">
                    <li class="">
                        <a href="${contextPath}/user/all" title="Users" id="navUsersBtn">
                            <em class="icon-people"></em>
                            <span>Users</span>
                        </a>
                    </li>
                </sec:authorize>
                <%--<li class="">--%>
                    <%--<a href="${contextPath}/recent" title="Recently Viewed">--%>
                        <%--<em class="fa fa-history"></em>--%>
                        <%--<span>Recently Viewed</span>--%>
                    <%--</a>--%>
                <%--</li>--%>

                <li class="nav-heading">
                    <span>Developer</span>
                </li>
                <li class="">
                    <a href="${contextPath}/dev/access" title="API Access">
                        <em class="fa fa-code"></em>
                        <span>API Access</span>
                    </a>
                </li>

                <!--

                <li class=" ">
                    <a href="#documentation" title="Dashboard" data-toggle="collapse">
                        <em class="fa fa-code"></em>
                        <%--<div class="pull-right label label-info">3</div>--%>
                        <span>Documentation</span>
                    </a>
                    <ul id="documentation" class="nav sidebar-subnav collapse">
                        <li class="sidebar-subnav-header">Documentation</li>
                        <li class=" ">
                            <a href="${contextPath}/developer/doc/http" title="Dashboard v1">
                                <span>HTTP API</span>
                            </a>
                        </li>
                    </ul>
                </li>

                -->

            </ul>
            <!-- END sidebar nav-->
        </nav>
    </div>
    <!-- END Sidebar (left)-->
</aside>
