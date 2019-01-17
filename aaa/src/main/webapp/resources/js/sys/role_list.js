var parameter = {};
var titleInfo="信息提示";
var timeoutValue=2000;
var toolbar = ([{
	text:'Add',
	iconCls:'icon-add',
	handler:function(){
		$('#role_detail_dialog').dialog('open')
		cleanDialog();
		}
}]);
$(function(){
	initDataGrid();
	$('#role_detail_dialog').dialog({
		buttons:[{
			text:'确 定',
			handler:function(){
				submit_from();
			}
		},{
			text:'取消',
			handler:function(){
				$('#role_detail_dialog').dialog('close');
			}
		}]
	});
	$('#role_auth_dialog').dialog({
		buttons:[{
			text:'确 定',
			handler:function(){
				submit_tree();
			}
		},{
			text:'取消',
			handler:function(){
				$('#role_auth_dialog').dialog('close');
			}
		}]
	});
});
function initDataGrid(){
	$('#detail_table').datagrid({
		iconCls:'icon-save',
		nowrap: true,
		autoRowHeight: true,
		striped: true,
		toolbar: toolbar,
		fitColumns:true,
		collapsible:true,
		url:'admin/role/getAllRole.json',
		queryParams:parameter,
		remoteSort: false,
		singleSelect:true,
		border:false,
		idField:'roleId',
		columns : [[
		   {
			field : 'roleName',
			title : '角色名称',
			width : 100,
			formatter : function(value, row, index) {
				return value;
			}
		},{field:'isEffective',title:'状态',width:70,
			formatter:function(value){
				if(1==value){
					return '有效';
				}else{
					return '<span style="color:red;">无效</span>';
				}
			}
		},{
			field : 'roleId',
			title : '操作',
			width : 170,
			formatter : function(value) {
				return '<a href="javascript:editRole('+value+')">修改</a> <a href="javascript:addAuth('+value+')">赋权限</a>';
			}
		} 
		] ],
		pagination:true,
		pageSize:10,
		rownumbers:true,
		onClickRow:function(rowIndex){
        }
	});
	
}
//初始化角色
function initAuthTreeList(roleId){
	$("#authTree").tree({
		url:"/admin/role/getRoleTree.json?roleId="+roleId,
		animate:true,
		cascadeCheck:true
	});
}
////弹框
function editRole(roleId){
	$('#detail_table').datagrid('selectRecord',roleId);
	var rowInfo =  $('#detail_table').datagrid('getSelected');
	if(rowInfo){
		//设置弹出框信息
		generateDialog(rowInfo);
		$('#role_detail_dialog').dialog('open');
	}
}
////弹框
function addAuth(roleId){
	//初始化树
	initAuthTreeList(roleId);
	$("#roleAuthId").val(roleId);
	$('#role_auth_dialog').dialog('open');
}

function submit_tree(){
	var roleId=$("#roleAuthId").val();
	var nodes = $('#authTree').tree('getChecked',['checked','indeterminate']);
	var roleAuthArr=[];
	$.each(nodes,function(index,value){
		roleAuthArr[index]=value.id;
	});
	
	$.ajax({
		  type: "POST",
		  url: "/admin/role/updateRoleAuth.json",
		  data: {"authIds":roleAuthArr.toString(),"roleId":roleId}
		})
		  .done(function( data ) {
			  if(data.codeV==1){
					$('#role_auth_dialog').dialog('close');
					$.messager.show({title:titleInfo,msg:'修改成功！',timeout:timeoutValue,showType:'slide'});
				}else{
					$.messager.alert(titleInfo,'修改失败！');
					$('#role_auth_dialog').dialog('close');
				}
		  });
	
	
}

//保存
function submit_from(){
	var roleId = $("#hiddenAroleId").val();
	var role = {
			"roleName":$("#roleName").val(),
			"isEffective":$("#isEffective2").val()
	};
	//判断页面填写是否为空
	if($("#roleName").val() == null || $("#roleName").val()== "" ){
		$.messager.show({title:titleInfo,msg:'角色名称不能为空！',timeout:timeoutValue,showType:'slide'});
		return;
	}
	if(roleId != null && roleId != ""){
	//	alert("修改："+roleId);
		role['roleId']=roleId;
		$.post("/admin/role/update.json",role,function(data){
			if(data.codeV==1){
				$('#role_detail_dialog').dialog('close');
				$.messager.show({title:titleInfo,msg:'修改成功！',timeout:timeoutValue,showType:'slide'});
				$('#detail_table').datagrid('load',parameter);
			}else{
				$.messager.alert(titleInfo,'修改失败！');
			}
		},"json");
	}else{
		$.post("/admin/role/add.json",role,function(data){
			if(data.codeV==1){
				$('#role_detail_dialog').dialog('close');
				$.messager.show({title:titleInfo,msg:'添加成功！',timeout:timeoutValue,showType:'slide'});
				$('#detail_table').datagrid('load',parameter);
			}else{
				$.messager.alert(titleInfo,'添加失败！');
			}
		},"json");
	}
}
//设置弹出框信息 
function generateDialog(rowInfo){
	$('#hiddenAroleId').val(rowInfo.roleId);
	$('#roleName').val(rowInfo.roleName);
	$('#isEffective2').val(rowInfo.isEffective);
}
//清空对话框
function cleanDialog(){
	$('#hiddenAroleId').val('');
	$('#roleName').val('');
}


