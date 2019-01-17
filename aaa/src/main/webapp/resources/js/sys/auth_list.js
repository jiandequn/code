var parameter = {};
var titleInfo="信息提示";
var timeoutValue=2000;
var toolbar = ([{
	text:'Add',
	iconCls:'icon-add',
	handler:function(){
		$('#auth_detail_dialog').dialog('open')
		cleanDialog();
		}
}]);
$(function(){
	initDataGrid();
	initAuthList();
	$('#auth_detail_dialog').dialog({
		buttons:[{
			text:'确 定',
			handler:function(){
				submit_from();
			}
		},{
			text:'取消',
			handler:function(){
				$('#auth_detail_dialog').dialog('close');
			}
		}]
	});
	$('#auth_seq_dialog').dialog({
		buttons:[{
			text:'确 定',
			handler:function(){
				submit_seq_from();
			}
		},{
			text:'取消',
			handler:function(){
				$('#auth_seq_dialog').dialog('close');
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
		url:'admin/auth/getList.json',
		queryParams:parameter,
		remoteSort: false,
		singleSelect:true,
		idField:'authId',
		border:false,
		columns : [[
		   {field:'pid',title:'父id',hidden:true},
		   {
			field : 'authName',
			title : '权限名称',
			width : 100,
			formatter : function(value, row, index) {
				return value;
			}
		}, {
			field : 'authAction',
			title : '权限路径',
			width : 270,
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
			field : 'authId',
			title : '操作',
			width : 170,
			formatter : function(value, row, index) {
				if(row.pid == 0){
					return '<span style="color:red;">主根节点</span>&nbsp;&nbsp;<a href="javascript:editSeq('+value+')">修改顺序</a>';
				}
				return '<a href="javascript:authEdit('+value+')">修改</a>';
			}
		} 
		
		
		] ],
		pagination:true,
		rownumbers:true,
		onClickRow:function(rowIndex){
        }
	});
	
}
//初始化权限
function initAuthList(){
	$.post("/admin/auth/getAllAuth.json",{isEffective:1},function(data){
		$("#pid").html("<option value='0'>请选择</option>");
		$.each(data.rows,function(dataIndex,auth){
			if(auth.pid == 0){
				$("#pid").append('<option value='+auth.authId+'>'+auth.authName+'</option>');
			}
			
		});
	},"json");
}

function editSeq(authId){
	$('#detail_table').datagrid('selectRecord',authId);
	var rowInfo =  $('#detail_table').datagrid('getSelected');
	if(rowInfo){
		//设置弹出框信息
		generateDialog(rowInfo);
		$('#auth_seq_dialog').dialog('open');
	}
}

//弹框
function authEdit(authId){
	$('#detail_table').datagrid('selectRecord',authId);
	var rowInfo =  $('#detail_table').datagrid('getSelected');
	if(rowInfo){
		//设置弹出框信息
		generateDialog(rowInfo);
		$('#auth_detail_dialog').dialog('open');
	}
}

//保存
function submit_from(){
	var re = new RegExp("^[0-9]+$");//判断是否是数字
	var authId = $("#authId").val();
	var auth = {
			"authName":$("#authName").val(),
			"authAction":$("#authAction").val(),
			"pid":$("#pid").val(),
			"authSeq":$("#seq").val(),
			"isEffective":$("#isEffective").val()
	};
	//判断页面填写是否为空
	if($("#authName").val() == null ||$("#authName").val() == "" ){
		$.messager.show({title:titleInfo,msg:'权限名称不能为空！',timeout:timeoutValue,showType:'slide'});
		return;
	}
	if($("#seq").val() != null && $("#seq").val() != "" ){
		if(!re.test( $("#seq").val())){
			$.messager.show({title:titleInfo,msg:'顺序格式不正确！',timeout:timeoutValue,showType:'slide'});
			return;
		}
	}
	if($("#authAction").val() == null ||$("#authAction").val() == "" ){
		$.messager.show({title:titleInfo,msg:'请求动作不能为空！',timeout:timeoutValue,showType:'slide'});
		return;
	}
	if(authId!=null&&authId!=""){
		auth['authId']=authId;
		$.post("/admin/auth/update.json",auth,function(data){
			if(data.codeV==1){
				$('#auth_detail_dialog').dialog('close');
				$.messager.show({title:titleInfo,msg:'修改成功！',timeout:timeoutValue,showType:'slide'});
				$('#detail_table').datagrid('load',parameter);
			}else{
				$.messager.alert(titleInfo,'修改失败！');
			}
		},"json");
	}else{
		$.post("/admin/auth/add.json",auth,function(data){
			if(data.codeV==1){
				$('#auth_detail_dialog').dialog('close');
				$.messager.show({title:titleInfo,msg:'添加成功！',timeout:timeoutValue,showType:'slide'});
				$('#detail_table').datagrid('load',parameter);
			}else{
				$.messager.alert(titleInfo,'添加失败！');
			}
		},"json");
	}
}

function submit_seq_from(){
	var re = new RegExp("^[0-9]+$");//判断是否是数字
	var authId = $("#authId").val();
	var auth = {
			"authSeq":$("#Mseq").val()
	};
	if($("#Mseq").val() != null && $("#Mseq").val() != "" ){
		if(!re.test( $("#Mseq").val())){
			$.messager.show({title:titleInfo,msg:'顺序格式不正确！',timeout:timeoutValue,showType:'slide'});
			return;
		}
	}
	
	if(authId!=null&&authId!=""){
		auth['authId']=authId;
		$.post("/admin/auth/update.json",auth,function(data){
			if(data.codeV==1){
				$('#auth_seq_dialog').dialog('close');
				$.messager.show({title:titleInfo,msg:'修改成功！',timeout:timeoutValue,showType:'slide'});
				$('#detail_table').datagrid('load',parameter);
			}else{
				$.messager.alert(titleInfo,'修改失败！');
			}
		},"json");
	}else{
		$.post("/admin/auth/add.json",auth,function(data){
			if(data.codeV==1){
				$('#auth_seq_dialog').dialog('close');
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
	$('#authId').val(rowInfo.authId);
	$('#authName').val(rowInfo.authName);
	$('#authAction').val(rowInfo.authAction);
	$('#pid').val(rowInfo.pid);
	$('#seq').val(rowInfo.authSeq);
	$('#Mseq').val(rowInfo.authSeq);
	$('#isEffective').val(rowInfo.isEffective);
	
}

//清空对话框
function cleanDialog(){
	$('#authName').val('');
	$('#authAction').val('');
	$('#authId').val('');
	$('#seq').val('');
	$('#Mseq').val('');
}


