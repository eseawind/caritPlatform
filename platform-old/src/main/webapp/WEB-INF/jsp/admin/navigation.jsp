<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ include file="/WEB-INF/jsp/commons/easyui.jsp"%>
		<script type="text/javascript" src="${ctx}/resources/public/scripts/utils.js?v1.1" ></script>
		<script type="text/javascript" src="${ctx}/resources/public/scripts/common.js?v1.1" ></script>
		<script type="text/javascript">
		$(function(){
			$('#catalogId').combobox({
				data:catalogs,
				editable:false,
				valueField:'id',
				textField:'name'
			});
			$('#catalogId_edit').combobox({
				data:catalogs,
				editable:false,
				valueField:'id',
				textField:'name'
			});
			$('#status').combobox({
				data:navStatus,
				editable:false,
				valueField:'code',
				textField:'value'
			});
			$('#status_edit').combobox({
				data:navStatus,
				editable:false,
				valueField:'code',
				textField:'value'
			});
			$('#cssClass').combobox({
				data:cssClassList,
				editable:false,
				valueField:'code',
				textField:'value'
			});
			$('#cssClass_edit').combobox({
				data:cssClassList,
				editable:false,
				valueField:'code',
				textField:'value'
			});
			$('.combobox-f').each(function(){
				$(this).combobox('clear');
			});
			
			$('#edit_submit_save').click(function(){
				$('#editForm').form({
					onSubmit:function(){
						// 避免 form validate bug
						$('.combobox-f').each(function(){
							$(this).val($(this).combobox('getText'));
						});
						var b=true;
						var fileText=$('#fileText').val();
						if(fileText==''||fileText==null||fileText==undefined){
							b=false;
							$.messager.alert('错误', "请选取文件", 'error');
						}else{
							if(!chkFileType(fileText,'jpg|jpeg|png|gif')){
								b=false;
								$.messager.alert('提示', "请选择 jpg|jpeg|png|gif 类型的文件", 'info');
							}
						}
						if(!b){return b;}
						b=$(this).form('validate');
						if(b){
							$.messager.progress({title:'请稍后',msg:'提交中...'});
						}
						return b;
				    }, 
			    	success:function(data){
			    		$.messager.progress('close');
			    		if(data==-1){
							$.messager.alert('错误', "编辑失败", 'error');
			    		} else if(data>0){
							$.messager.alert('成功', "编辑成功", 'info');
				        	$('#editWin').window('close');
				        	// update rows
				        	$('#tt').datagrid('reload');
						}else{
			    			$.messager.alert('异常', "后台系统异常", 'error');
						}
				    }
				}).submit();
			});
		});
		function edit(index) {
			if(index>-1){//双击
				// clear selected
				$('#tt').datagrid('clearSelections');
				$('#tt').datagrid('selectRow',index); //让双击行选定
			}
			var m = $('#tt').datagrid('getSelected');
			if (m) {
				$('#editForm input').each(function(){
					$(this).removeClass('validatebox-invalid');
				});
				$('#editWin').window({title:'修改'+winTitle,iconCls:'icon-edit'});
				$('#editWin').window('open');
				// init data
				$('#name_edit').val(m.name);
				$('#catalogId_edit').combobox('setValue',m.catalogId);
				$('#url_edit').val(m.url);
				$('#displayIndex_edit').numberspinner('setValue',m.displayIndex);
				$('#status_edit').combobox('setValue',m.status);
				$('#bgColor_edit').val(m.bgColor);
				//$('#cssClass_edit').val(m.cssClass);
				$('#width_edit').val(m.width);
				$('#height_edit').val(m.height);
				$('#fileText').val(m.logo);
				$('#id').val(m.id);
				$('#editWin').show();
			} else {
				$.messager.show({
					title : '警告',
					msg : '请先选择要修改的记录。'
				});
			}
		}
		function navStatusFormatter(v){
			var result='-';
			$.each(navStatus, function(key,val) {
				if(v==val.code){
					result=val.value;
					return false;
				}
			});
			return result;
		}
		function cssClassFormatter(v){
			var result='-';
			$.each(cssClassList, function(key,val) {
				if(v==val.code){
					result=val.value;
					return false;
				}
			});
			return result;
		}
		</script>
		<style>
		#editWin label {width: 100px;text-align: right;}
		#editWin input {width: 250px;}
		</style>
	</head>
	<body>
		<div style="width: 100%;">
		<form:form modelAttribute="navigation"
			action="${ctx}/admin/navigation/query"
			method="get" id="searchForm">
			<table>
				<tr>
					<td>
						<form:label for="catalogId" path="catalogId">分类：</form:label>
					</td>
					<td>
						<form:input path="catalogId" cssStyle="width:156px"/>
					</td>
					<td>
						<form:label for="name" path="name">名称：</form:label>
					</td>
					<td>
						<form:input path="name"/>
					</td>
					<td>
						<form:label for="url" path="url">地址：</form:label>
					</td>
					<td>
						<form:input path="url"/>
					</td>
				</tr>
				<tr>
					<td>
						<form:label for="displayIndex" path="displayIndex">顺序：</form:label>
					</td>
					<td>
						<form:input path="displayIndex" cssClass="easyui-numberspinner" min="1" max="1000"/>
					</td>
					<td>
						<form:label for="cssClass" path="cssClass">样式：</form:label>
					</td>
					<td>
						<form:input path="cssClass" cssStyle="width:156px"/>
					</td>
					<td>
						<form:label for="status" path="status">状态：</form:label>
					</td>
					<td>
						<form:input path="status" cssStyle="width:156px"/>
					</td>
				</tr>
			</table>
		</form:form>
		<div style="text-align: center; padding: 5px;">
				<a href="javascript:void(0);" class="easyui-linkbutton" id="submit"
					iconCls="icon-search">查 询</a>
				<a href="javascript:void();" class="easyui-linkbutton" id="reset"
					iconCls="icon-undo">重 置</a>
			</div>
			<table id="tt" style="height: auto;" iconCls="icon-blank" title="地址导航列表" align="left"  
			idField="id" url="${ctx}/admin/navigation/query" pagination="true" rownumbers="true"
			fitColumns="true" pageList="[ 5, 10]" sortName="displayIndex" sortOrder="asc">
				<thead>
					<tr>
						<th field="catalogId" width="100" align="center" formatter="catalogFormatter">分类</th>
						<th field="name" width="100" align="center">名称</th>
						<th field="url" width="150" align="center">地址</th>
						<th field="displayIndex" width="60" align="center">顺序</th>
						<th field="cssClass" width="80" align="center" formatter="cssClassFormatter">样式</th>
						<th field="logo" width="80" align="center">Logo</th>
						<th field="width" width="80" align="center">宽度</th>
						<th field="height" width="80" align="center">高度</th>
						<th field="status" width="60" align="center" formatter="navStatusFormatter">状态</th>
						<th field="updateTime" width="90" align="center">更新时间</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="editWin" class="easyui-window" title="地址导航" closed="true" style="width:500px;height:400px;padding:5px;" modal="true">
			<form:form modelAttribute="navigation" id="editForm" action="${ctx}/admin/navigation/save" method="post" cssStyle="padding:10px 20px;" enctype="multipart/form-data">
				<table>
					<tr>
						<td><form:label	for="catalogId" path="catalogId" cssClass="mustInput">分类：</form:label></td>
						<td><form:input path="catalogId" id="catalogId_edit" required="true" cssStyle="width:255px;"/></td>
					</tr>
					<tr>
						<td><form:label	for="name" path="name" cssClass="mustInput">名称：</form:label></td>
						<td><form:input path="name" id="name_edit" required="true" validType="length[2,100]" class="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td><form:label	for="url" path="url" cssClass="mustInput">地址：</form:label></td>
						<td><form:input path="url" id="url_edit" required="true" validType="url" class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<td><form:label	for="cssClass" path="cssClass" cssClass="mustInput">样式：</form:label></td>
						<td><form:input path="cssClass" id="cssClass_edit" required="true" validType="length[3,100]" class="easyui-validatebox" cssStyle="width:255px;"/></td>
					</tr>
					<tr>
						<td><form:label	for="displayIndex" path="displayIndex" cssClass="mustInput">顺序：</form:label></td>
						<td>
							<form:input path="displayIndex" id="displayIndex_edit"  required="true" cssClass="easyui-numberspinner" min="1" max="1000" cssStyle="width:255px;"/>
						</td>
					</tr>
					<tr>
						<td><form:label	for="logo" path="logo" cssClass="mustInput">Logo：</form:label></td>
						<td>
						<div class="fileinputs">  
							<input type="file" class="file" name="file" id="file" onchange="$('#fileText').val(this.value);"/>  
							<div class="fakefile">  
								<input id="fileText" style="width:205px;"/><button>浏览</button>
							</div>  
						</div>
						</td>
					</tr>
					<tr>
						<td><form:label	for="status" path="status" cssClass="mustInput">状态：</form:label></td>
						<td><form:input path="status" id="status_edit" required="true" class="easyui-validatebox" cssStyle="width:255px;"/></td>
					</tr>
				</table>
				<form:hidden path="id"/>
				<div style="text-align: center; padding: 5px;">
					<a href="javascript:void(0)" class="easyui-linkbutton" id="edit_submit_save" iconCls="icon-save">保 存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" id="edit_reset" iconCls="icon-undo">重 置</a>
				</div>
			</form:form>
		</div>
	</body>
</html>