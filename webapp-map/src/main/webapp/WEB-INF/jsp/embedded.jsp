<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>${viewName}</title>

    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <meta name="format-detection" content="telephone=no" />

    <!-- ############# css ################# -->
    <link
            rel="stylesheet"
            type="text/css"
            href="${clientDomain}/Oskari${path}/icons.css"/>
    <link
            rel="stylesheet"
            type="text/css"
            href="${clientDomain}/Oskari${path}/oskari.min.css"/>
    <style type="text/css">
        @media screen {
            body {
                margin : 0;
                padding : 0;
            }
            #mapdiv {
                width: 100%;
            }
            #contentMap {
                height: 100%;
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
<div id="contentMap" class="oskariui container-fluid published">
    <div class="row-fluid" style="height: 100%; background-color:white;">
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


<%-- Pre-compiled application JS, empty unless created by build job --%>
<script type="text/javascript"
    src="${clientDomain}/Oskari${path}/oskari.min.js">
</script>

<%--language files --%>
<script type="text/javascript"
    src="${clientDomain}/Oskari${path}/oskari_lang_${language}.js">
</script>

<script type="text/javascript"
        src="${clientDomain}/Oskari${path}/index.js">
</script>

</body>
</html>
