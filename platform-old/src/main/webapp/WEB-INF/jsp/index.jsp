<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/commons/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
    <head>
       <%@ include file="/WEB-INF/jsp/commons/meta.jsp"%>
       <script type="text/javascript">
       $(function(){
    	   <c:forEach items="${allNavigation}" var="nav" varStatus="stat">
		       	var obj={id:'${nav.id}',name:'${nav.name}',checked:false};
		   		<c:forEach items="${favoriteNavigation}" var="fav" varStatus="stat2">
		   			<c:if test="${nav.id eq fav.id}">obj.checked=true;</c:if>
		   		</c:forEach>
		   		allNavigation.push(obj);
		   	</c:forEach>
    	   $('.wrapper_home').each(function(){
			  $(this).masonry({
				  itemSelector : '.item',
				  columnWidth : 10
			  });
    	   });
    	   <c:forEach items="${allCatalogList}" var="catalog" varStatus="stat">
    	   if($('#${catalog.id}_container:has("div")').length==0){
    		   $('#${catalog.id}_title').remove();
    		   $('#${catalog.id}_container').remove();
    	   }
    	   </c:forEach>
		   $('ul.nav_left').toggle('right');
		   $('ul.nav_right').toggle('left');
    	   $('#img_left').click(function () {
    		   $('ul.nav_left').toggle('right');
           });
           $('.item').click(function(){
        	   open($(this).attr('url'));
           });
           // logo 图片加载出错则删除图片标签
           $('.item img').error(function(){
        	   $(this).parent().html($(this).parent().attr('name'));
        	   //$(this).remove();
           });
		   if('${portalAccount}'){
				account.id='${portalAccount.id}';
				account.nickName='${portalAccount.nickName}';
				account.email='${portalAccount.email}';
				account.photo='${portalAccount.photo}';
				account.gender='${portalAccount.gender}';
				account.realName='${portalAccount.realName}';
				account.idCard='${portalAccount.idCard}';
				account.mobile='${portalAccount.mobile}';
				account.officePhone='${portalAccount.officePhone}';
				account.balance='${portalAccount.balance}';
		   }
		});
       </script>
    </head>
    <body>
    	<jsp:include page="/WEB-INF/jsp/commons/header.jsp" flush="true">
    		<jsp:param value="1" name="menuIndex"/>
    	</jsp:include>

		<div class="navbox">
		<img id="img_left" src="${ctx}/resources/public/images/menu_button.png" width="32" height="184" border="0" class="menu_class">
		<ul class="nav_left">
		<c:forEach items="${allCatalogList}" var="catalog" varStatus="stat">
		<li><a href="#${catalog.id}" >${catalog.name}</a></li>
		</c:forEach>
		</ul>
		</div>
        <div id="main">
        <!-- 没登录 -->
        <c:if test="${empty portalAccount}">
	        <c:forEach items="${allCatalogList}" var="catalog" varStatus="stat">
				<h2 id="${catalog.id}_title"><a name="${catalog.id}">${catalog.name}</a></h2>
				<div id="${catalog.id}_container" class="wrapper_home">
				<c:forEach items="${allNavigation}" var="nav" varStatus="stat2">
					<c:if test="${catalog.id eq nav.catalogId}">
					<div name="${nav.name}" url="${nav.url}" class="item ${nav.cssClass}"><c:if test="${empty nav.logo}">${nav.name}</c:if><c:if test="${not empty nav.logo}"><img src="${nav.logo}"/></c:if></div>
					</c:if>
				</c:forEach>
				</div>
			</c:forEach>
        </c:if>
        <!-- 登录了 -->
        <c:if test="${not empty portalAccount}">
        	<c:if test="${empty favoriteNavigation}">
			<c:forEach items="${allCatalogList}" var="catalog" varStatus="stat">
				<h2 id="${catalog.id}_title"><a name="${catalog.id}">${catalog.name}</a></h2>
				<div id="${catalog.id}_container" class="wrapper_home">
				<c:forEach items="${allNavigation}" var="nav" varStatus="stat2">
					<c:if test="${catalog.id eq nav.catalogId && nav.status eq 2}">
					<div name="${nav.name}" url="${nav.url}" class="item ${nav.cssClass}"><c:if test="${empty nav.logo}">${nav.name}</c:if><c:if test="${not empty nav.logo}"><img src="${nav.logo}"/></c:if></div>
					</c:if>
				</c:forEach>
				</div>
			</c:forEach>
        	</c:if>
        	<c:if test="${not empty favoriteNavigation}">
			<c:forEach items="${allCatalogList}" var="catalog" varStatus="stat">
				<h2 id="${catalog.id}_title"><a name="${catalog.id}">${catalog.name}</a></h2>
				<div id="${catalog.id}_container" class="wrapper_home">
				<c:forEach items="${favoriteNavigation}" var="nav" varStatus="stat2">
					<c:if test="${catalog.id eq nav.catalogId}">
					<div name="${nav.name}" url="${nav.url}" class="item ${nav.cssClass}"><c:if test="${empty nav.logo}">${nav.name}</c:if><c:if test="${not empty nav.logo}"><img src="${nav.logo}"/></c:if></div>
					</c:if>
				</c:forEach>
				</div>
			</c:forEach>
        	</c:if>
        </c:if>
		</div>
    <%@ include file="/WEB-INF/jsp/commons/footer.jsp"%>
    </body>
</html>