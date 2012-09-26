	<title><spring:message code="title"/> - <spring:message code="subTitle"/></title>
	<meta charset="utf-8"/>
	<%@ include file="/WEB-INF/jsp/commons/nocache.jsp"%>
	<!-- Mobile viewport optimisation -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=yes"/>
	<meta name="keywords" content="<spring:message code="keywords"/>" />
	<meta name="description" content="<spring:message code="description"/>">
	<link rel="shortcut icon" href="${ctx}/resources/favicon.ico" type="image/x-icon">
	<link rel="icon" href="${ctx}/resources/favicon.ico" type="image/x-icon">
	<link href="${ctx}/resources/public/styles/MetroJs.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/resources/public/styles/XYTipsWindow.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/resources/public/styles/homepage.css?v1.1" rel="stylesheet" type="text/css" />
	<link href="${ctx}/resources/public/styles/common.css?v1.1" rel="stylesheet" type="text/css" />
	<!--[if lte IE 7]>
	<link href="${ctx }/resources/yaml/core/iehacks.css" rel="stylesheet" type="text/css" />
	<![endif]-->

	<!--[if lt IE 9]>
	<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->
	<script type="text/javascript" src="${ctx}/resources/jquery-easyui-1.3/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/jquery-easyui-1.3/plugins/jquery.form.js"></script>
	<script type="text/javascript" src="${ctx}/resources/public/scripts/jquery.masonry.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/public/scripts/MetroJs.js"></script>
	<script type="text/javascript" src="${ctx}/resources/public/scripts/utils.js?v1.1"></script>
	<script type="text/javascript" src="${ctx}/resources/public/scripts/index.js?v1.1"></script>
	<script type="text/javascript" src="${ctx}/resources/public/scripts/XYTipsWindow-3.0.js"></script>