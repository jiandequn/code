var parameter = {};
var titleInfo="信息提示";
var timeoutValue=2000;
var toolbar = ([{
	text:'Add',
	iconCls:'icon-add',
	handler:function(){
		$('#user_detail_dialog').dialog('open')
		$('#userPwd').removeAttr("disabled");
		cleanDialog();
		}
}]);
$(function(){
	initDataGrid();
	initRoleList();
	initDeptList();
	$('#user_detail_dialog').dialog({
		buttons:[{
			text:'确 定',
			handler:function(){
				submit_from();
			}
		},{
			text:'取消',
			handler:function(){
				$('#user_detail_dialog').dialog('close');
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
		url:'admin/user/getList.json',
		queryParams:parameter,
		remoteSort: false,
		singleSelect:true,
		border:false,
		idField:'userId',
		columns : [[
		   {field:'roleId',title:'角色id',hidden:true},
		   {field:'deptId',title:'部门id',hidden:true},
		   {field:'userPwd',title:'密码',hidden:true},
		   {
			field : 'userName',
			title : '用户名',
			width : 100,
			formatter : function(value, row, index) {
				return value;
			}
		}, {
			field : 'userMail',
			title : '用户邮箱',
			width : 140,
			formatter : function(value) {
				return value;
			}
		},{
			field : 'roleName',
			title : '用户角色',
			width : 100,
			formatter : function(value) {
				return value;
			}
		},{
			field : 'deptName',
			title : '用户部门',
			width : 100,
			formatter : function(value) {
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
			field : 'userId',
			title : '操作',
			width : 170,
			formatter : function(value) {
				return '<a href="javascript:userEdit('+value+')">修改</a>';
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
function initRoleList(){
	$.post("/admin/role/getAllRole.json",{isEffective:1},function(data){
		$("#roleId").html("<option value='0'>请选择</option>");
		$.each(data.rows,function(dataIndex,role){
			$("#roleId").append('<option value='+role.roleId+'>'+role.roleName+'</option>');
		});
	},"json");
}
//初始化部门
function initDeptList(){
	$.post("/admin/dept/getAllDept.json",{isEffective:1},function(data){
		$("#deptId").html("<option value='0'>请选择</option>");
		$.each(data.rows,function(dataIndex,dept){
			$("#deptId").append('<option value='+dept.deptId+'>'+dept.deptName+'</option>');
		});
	},"json");
}
//弹框
function userEdit(userId){
	$('#detail_table').datagrid('selectRecord',userId);
	var rowInfo =  $('#detail_table').datagrid('getSelected');
	if(rowInfo){
		//设置弹出框信息
		generateDialog(rowInfo);
		$('#user_detail_dialog').dialog('open');
	}
}
//保存
function submit_from(){
	var userId = $("#userId").val();
	var oldPwd = $("#oldPwd").val();
	var user = {
			"userName":$("#userName").val(),
			"userPwd":$("#userPwd").val(),
			"userMail":$("#userMail").val(),
			"roleId":$("#roleId").val(),
			"deptId":$("#deptId").val(),
			"isEffective":$("#isEffectiveUser").val()
	};
	//判断页面填写是否为空
	if($("#userMail").val() == null ||$("#userMail").val()== "" ){
		$.messager.show({title:titleInfo,msg:'用户邮箱不能为空！',timeout:timeoutValue,showType:'slide'});
		return;
	}
	if($("#roleId").val() == 0 ||$("#roleId").val()== "" ){
		$.messager.show({title:titleInfo,msg:'请选择角色！',timeout:timeoutValue,showType:'slide'});
		return;
	}
	if($("#deptId").val() == 0 ||$("#deptId").val()== "" ){
		$.messager.show({title:titleInfo,msg:'请选择部门！',timeout:timeoutValue,showType:'slide'});
		return;
	}
	if(userId!=null&&userId!=""){
		user['userId']=userId;
		if(oldPwd ==$("#userPwd").val()){
			delete user["userPwd"];
		}
		$.post("/admin/user/update.json",user,function(data){
			if(data.codeV==1){
				$('#user_detail_dialog').dialog('close');
				$.messager.show({title:titleInfo,msg:'修改成功！',timeout:timeoutValue,showType:'slide'});
				$('#detail_table').datagrid('load',parameter);
			}else{
				$.messager.alert(titleInfo,'修改失败！');
			}
		},"json");
	}else{
		$.post("/admin/user/add.json",user,function(data){
			if(data.codeV==1){
				$('#user_detail_dialog').dialog('close');
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
	$('#userId').val(rowInfo.userId);
	$('#userPwd').val(rowInfo.userPwd);
	$('#userName').val(rowInfo.userName);
	$('#userMail').val(rowInfo.userMail);
	$('#roleId').val(rowInfo.roleId);
	$('#deptId').val(rowInfo.deptId);
	$('#isEffectiveUser').val(rowInfo.isEffective);
	$('#oldPwd').val(rowInfo.userPwd);
}
//清空对话框
function cleanDialog(){
	$('#userId').val('');
	$('#userPwd').val('');
	$('#userName').val('');
	$('#userMail').val('');
}


