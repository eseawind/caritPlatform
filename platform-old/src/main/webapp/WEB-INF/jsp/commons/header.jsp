<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/commons/taglibs.jsp"%>
<!-- 页眉 -->
<header>
	<div id="loginBefore" <c:if test="${not empty portalAccount}">style="display: none;"</c:if>>
		<ul>
			<li><span id="reg">注册</span></li>
			<li><span class="gbts"></span></li>
			<li><span id="login">登陆</span></li>
		</ul>
	</div>
	<div id="lolginAfter" <c:if test="${empty portalAccount}">style="display: none;"</c:if>>
		<ul>
			<li><span id="logout">退出</span></li>
			<li><span class="gbts"></span></li>
			<li><span id="gbi5"></span></li>
			<li><span class="gbts"></span></li>
			<li id="thumb_photo" style="<c:if test="${not empty portalAccount.thumbPhoto}">background-image: url(${portalAccount.thumbPhoto})</c:if><c:if test="${empty portalAccount.thumbPhoto}">background-image: url(${ctx}/resources/public/images/h_bedf916a.png);background-position:-98px -172px</c:if>"></li>
			<li><span class="gbts"></span></li>
			<li><span id="welcome">${portalAccount.nickName}</span></li>
		</ul>
	</div>
</header>