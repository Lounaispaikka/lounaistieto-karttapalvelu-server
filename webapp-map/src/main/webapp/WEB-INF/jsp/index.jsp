<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
    <jsp:useBean id="props" class="fi.nls.oskari.util.PropertyUtil"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Lounaistiedon Karttapalvelu</title>

    <script type="text/javascript" src="/Oskari/libraries/jquery/jquery-1.10.2.js">
    </script>

    <!-- ############# css ################# -->
    <link
            rel="stylesheet"
            type="text/css"
            href="/Oskari/resources/css/forms.css"/>
    <link
            rel="stylesheet"
            type="text/css"
            href="/Oskari/resources/css/portal.css"/>
    <link
            rel="stylesheet"
            type="text/css"
            href="/Oskari${path}/icons.css"/>
    <link
            rel="stylesheet"
            type="text/css"
            href="/Oskari${path}/css/overwritten.css"/>
    <style type="text/css">
        @media screen {
            body {
                margin: 0;
                padding: 0;
            }

            #mapdiv {
                width: 100%;
            }

            #maptools {
                background-color: #333438;
                height: 100%;
                position: absolute;
                top: 0;
                width: 153px;
                z-index: 2;
            }

            #contentMap {
                height: 100%;
                margin-left: 170px;
            }

            img.fullscreenDivImg {

            }
        }
    </style>
    <!-- ############# /css ################# -->
</head>

<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-68203085-5"></script>
<script>
    window.dataLayer = window.dataLayer || [];
    function gtag(){dataLayer.push(arguments);}
    gtag('js', new Date());

    gtag('config', 'UA-68203085-5');
</script>
<body>

<nav id="maptools">
    <a href="http://www.lounaistieto.fi">
        <div class="logo ${language}">
        </div>
    </a>
    <div id="lounais-text">
        <div>Paikkatietoa</div>
        <div>Lounais-Suomesta</div>
    </div>

    <div id="loginbar">
    </div>
    <div id="menubar">
    </div>
    <div id="divider">
    </div>
    <div id="toolbar">
    </div>
    <div id="login">
        <c:choose>
            <c:when test="${!empty loginState}">
                <p class="error"><spring:message code="invalid_password_or_username" text="Invalid password or username!" /></p>
            </c:when>
        </c:choose>
        <c:choose>
            <%-- If logout url is present - so logout link --%>
            <c:when test="${!empty _logout_uri}">
                <a href="${pageContext.request.contextPath}${_logout_uri}"><spring:message code="logout" text="Logout" /></a>
            </c:when>
            <%-- Otherwise show appropriate logins --%>
            <c:otherwise>
                <c:if test="${!empty _login_uri_saml}">
                    <a href="${pageContext.request.contextPath}${_login_uri_saml}"><spring:message code="login.sso" text="SSO login" /></a><hr />
                </c:if>
                <c:if test="${!empty _login_uri && !empty _login_field_user}">
                    <p style="color: #FFFFFF;padding-bottom: 5px;"><spring:message code="admin_login" text="Kirjautuminen" /></p>
                    <form action='${pageContext.request.contextPath}${_login_uri}' method="post" accept-charset="UTF-8">
                        <input size="16" id="username" name="${_login_field_user}" type="text" placeholder="<spring:message code="username" text="Username" />" autofocus
                               required>
                        <input size="16" id="password" name="${_login_field_pass}" type="password" placeholder="<spring:message code="password" text="Password" />" required>
                        <input type="submit" id="submit" value="<spring:message code="login" text="Log in" />">
                    </form>
                </c:if>
                <c:if test="${!empty _registration_uri}">
                    <a href="${pageContext.request.contextPath}${_registration_uri}"><spring:message code="user.registration" text="Register" /></a>
                </c:if>
            </c:otherwise>
        </c:choose>
    </div>
</nav>
<div id="contentMap" class="oskariui container-fluid">
    <div id="menutoolbar" class="container-fluid"></div>
    <div class="row-fluid oskariui-mode-content" style="height: 100%; background-color:white;">
        <div class="oskariui-left"></div>
        <div class="span12 oskariui-center" style="height: 100%; margin: 0;">
            <div id="mapdiv"></div>
        </div>
        <div class="oskari-closed oskariui-right">
            <div id="mapdivB"></div>
        </div>
    </div>
</div>


<!-- ############# Javascript ################# -->

<!--  OSKARI -->

<script type="text/javascript">
    var ajaxUrl = '${ajaxUrl}';
    var controlParams = ${controlParams};
</script>

<script type="text/javascript"
        src="/Oskari/bundles/bundle.js">
</script>

<c:if test="${preloaded}">
    <!-- Pre-compiled application JS, empty unless created by build job -->
    <script type="text/javascript"
            src="/Oskari${path}/oskari.min.js">
    </script>
    <!-- Minified CSS for preload -->
    <link
            rel="stylesheet"
            type="text/css"
            href="/Oskari${path}/oskari.min.css"
            />
    <%--language files --%>
    <script type="text/javascript"
            src="/Oskari${path}/oskari_lang_${language}.js">
    </script>
</c:if>

<script type="text/javascript"
        src="/Oskari${path}/index.js">
</script>


<!-- ############# /Javascript ################# -->
</body>
</html>
