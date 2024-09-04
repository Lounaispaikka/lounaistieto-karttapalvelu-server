<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="format-detection" content="telephone=no" />
    <title>Lounaistiedon Karttapalvelu</title>

    
    <!-- ############# css ################# -->
    <link
            rel="stylesheet"
            type="text/css"
            href="${clientDomain}/Oskari${path}/icons.css"/>

    <link
        rel="stylesheet"
        type="text/css"
        href="${clientDomain}/Oskari${path}/oskari.min.css"
    />
    <style type="text/css">
        @media screen {
            body {
                margin: 0;
                padding: 0;
                height: 100vh;
                width: 100%;
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

<!-- Matomo -->
<!-- https://github.com/Lounaispaikka/lounaistieto-karttapalvelu-server -->
<script>
    var _paq = window._paq = window._paq || [];
    /* tracker methods like "setCustomDimension" should be called before "trackPageView" */
    _paq.push(['requireCookieConsent']);
    _paq.push(['trackPageView']);
    _paq.push(['enableLinkTracking']);
    (function() {
        var u="https://mtm.utu.fi/";
        _paq.push(['setTrackerUrl', u+'matomo.php']);
        _paq.push(['setSiteId', '8']);
        var d=document, g=d.createElement('script'), s=d.getElementsByTagName('script')[0];
        g.async=true; g.src=u+'matomo.js'; s.parentNode.insertBefore(g,s);
    })();
    </script>
    <!-- End Matomo Code -->

<body>

    <nav id="maptools">
        <a href="http://www.lounaistieto.fi">
            <div class="logo ${language}">
            </div>
        </a>

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
                    <form action="${pageContext.request.contextPath}${_logout_uri}" method="POST" id="logoutform">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <a class="login-a" href="${pageContext.request.contextPath}${_logout_uri}" onClick="jQuery('#logoutform').submit();return false;"><spring:message code="logout" text="Logout" /></a>
                    </form>
                </c:when>
                <%-- Otherwise show appropriate logins --%>
                <c:otherwise>
                    <c:if test="${!empty _login_uri_saml}">
                        <a  href="${pageContext.request.contextPath}${_login_uri_saml}"><spring:message code="login.sso" text="SSO login" /></a><hr />
                    </c:if>
                    <c:if test="${!empty _login_uri && !empty _login_field_user}">
                        <p id="toggle-login" style="color: #FFFFFF;padding-bottom: 5px;"><spring:message code="admin_login" text="Kirjautuminen" /></p>
                        <form id="login-form" style="display: none;" action='${pageContext.request.contextPath}${_login_uri}' method="post" accept-charset="UTF-8">
                            <input size="16" id="username" name="${_login_field_user}" type="text" placeholder="<spring:message code="username" text="Username" />" autofocus
                                required>
                            <input size="16" id="password" name="${_login_field_pass}" type="password" placeholder="<spring:message code="password" text="Password" />" required>
                            <input type="submit" id="submit" value="<spring:message code="login" text="Log in" />">
                        </form>
                    </c:if>
                    <c:if test="${!empty _registration_uri}">
                        <a class="login-a" href="${pageContext.request.contextPath}${_registration_uri}"><spring:message code="user.registration" text="Register" /></a>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="affiliate_logos">
        </div>
    </nav>

    <!-- ############# Javascript ################# -->

    <!--  OSKARI -->
    <script type="text/javascript">
        var ajaxUrl = '${ajaxUrl}';
        var controlParams = ${controlParams};
    </script>

    <%-- Pre-compiled application JS, empty unless created by build job --%>
    <script type="text/javascript"
        src="${clientDomain}/Oskari${path}/oskari.min.js">
    </script>
    <%--language files --%>
    <script type="text/javascript"
        src="${clientDomain}/Oskari${path}/oskari_lang_${language}.js">
    </script>

    <!-- ====SCRIPTI==== -->
    <script>
        jQuery(document).ready(function(){
            jQuery("#toggle-login").click(function(){
                jQuery("#login-form").slideToggle("fast");
            });
        });
    </script>
    <!-- ====SCRIPTI==== -->
    <script type="text/javascript"
            src="${clientDomain}/Oskari${path}/index.js">
    </script>


<!-- ############# /Javascript ################# -->
</body>
</html>
