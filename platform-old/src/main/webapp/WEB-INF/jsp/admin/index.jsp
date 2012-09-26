<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/taglibs.jsp"%>

<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<title>中通福瑞——车载Android设备从这里开始</title>
		<%@ include file="/WEB-INF/jsp/commons/easyui.jsp"%>
		<script type="text/javascript" src="${ctx}/resources/public/scripts/default.js" ></script>
		<script>
		var fieldList;
		$(function(){
			var p = $('body').layout('panel','west').panel({
				onCollapse:function(){
					//alert('collapse');
				}
			});
			setTimeout(function(){
				$('body').layout('collapse','east');
			},0);
			$('#logout').click(function(){
				location.href='${ctx}/back/logout';
			});
		});
		</script>
	</head>

	<body class="easyui-layout">
		<div region="west" split="true" title="导航菜单" style="width: 180px;overflow:hidden;" icon="icon-redo">
	        <div id="menu" class="easyui-accordion" fit="true" border="false">
	            <div title="系统管理" style="overflow:auto;" icon="icon-edit">
                    <ul>
                        <!-- 
                        <li><div><a target="mainFrame_0" index="0" href="${ctx}/admin/language">语言管理</a></div></li>
                        <li><div><a target="mainFrame_1" index="1" href="${ctx}/admin/menu">菜单管理</a></div></li>
                         -->
						<li><div><a target="mainFrame_2" index="2" href="${ctx}/admin/user">后台账号管理</a></div></li>
						<li><div><a target="mainFrame_3" index="3" href="${ctx}/admin/catalog">分类管理</a></div></li>
						<li><div><a target="mainFrame_4" index="4" href="${ctx}/admin/navigation">地址管理</a></div></li>
						<li><div><a target="mainFrame_5" index="5" href="${ctx}/admin/account">注册用户管理</a></div></li>
						<!-- 
						<li><div><a target="mainFrame_6" index="6" href="${ctx}/admin/product">产品管理</a></div></li>
						 -->
						<li><div><a href="${ctx}/back/logout">退出</a></div></li>
                    </ul>
	            </div>
	        </div>
	    </div>
	    <!-- 
		<div region="east" split="true" title="East" style="width:180px;padding:10px;">east region</div>
	     -->
		<div region="center" id="mainPanle" style="background: #eee;overflow:hidden;">
	        <div id="tabs" class="easyui-tabs" fit="true" border="false">
	            <div title="我的主页" style="padding: 20px;" id="home">
	            </div>
	        </div>
    	</div>
		<noscript>
	        <div style="position: absolute; z-index: 100000; height: 2046px; top: 0px; left: 0px;
	            width: 100%; background: white; text-align: center;">
	            <img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
	        </div>
	    </noscript>
	</body>
</html>