var $search = $("#contentToolbar");
var $table = $("#content_table");
var $selectTable = $("#selectColumnTable");
var myInterval;
var initDataGrid = function () {
    var parameter=createQueryParameter($search);

    $table.datagrid({
        nowrap: true,
        autoRowHeight: true,
        url: '/export/table/list',
        striped: true,
        collapsible: true,
        pagination: true,
        toolbar: "#contentToolbar",
        pageSize: 30,
        queryParams: parameter,
        pageList: pageList,
        rownumbers: true,
        remoteSort: false,
        singleSelect: false,
        checkOnSelect: true,
        selectOnCheck: true,
        showFooter:false,
        border: false,
        idField:'id',
        columns: [[
            {field: 'id', title: 'ID', width: 60,checkbox:true},
            {field: 'name', title: '导出文件名', width: 180},
            {field: 'fileFormat', title: '文件格式', width: 200},
            {field: 'isEffective', title: '状态', width: 80,formatter:function(value,row,index){
                if(value==0) return "禁用"
                return "启用"
            }},
            {field: 'createTime', title: '创建时间', width: 120},
            {field: 'op',title: '操作',width: 160,formatter: function (value, row, index) {
                    var vt = "<a onclick='editTableInfo("+row.id+")'>编辑</a>&nbsp;&nbsp;&nbsp;";
                    if(row.isEffective == 0){
                        vt+="<a onclick='deleteTableInfo("+row.id+")'>删除</a>";
                    }else{
                        //vt +=  "<a href='/download/file/"+row.id+"'>下载CSV文件</a>";
                        vt +=  "<a onclick='downloadFile("+index+")'>下载CSV文件</a>";
                    }
                    return vt;
               }
            }
        ]]
    });
    $selectTable.datagrid({
        nowrap: true,
        autoRowHeight: true,
        toolbar: "#columnToolbar",
        striped: true,
        collapsible: true,
        pagination: false,
        rowumbers: true,
        remoteSort: false,
        singleSelect: true,
        checkOnSelect: false,
        selectOnCheck: false,
        border: false,
        //idField:"columnId",
        columns: [[
            {field: 'columnId', title: '字段ID', width: 100,hidden:true},
            {field: 'columnName', title: '字段英文名称', width: 200,editor:{type:'text',options:{required:true}}},
            {field: 'columnComment', title: '导出字段名称', width: 200,editor:{type:'text',options:{required:true}}},
            {field: 'seq', title: '顺序', width: 80,editor:{type : 'numberbox',options:{required:true}}},
            {field: 'op',title: '操作',width: 160,formatter: function (value, row, index) {
                return "<a onclick='deleteColumnInfo("+index+")'>删除</a>";
            }}
        ]],
        onDblClickCell: function(index,field,value){
            $(this).datagrid('beginEdit', index);
            var ed = $(this).datagrid('getEditor', {index:index,field:field});
            $(ed.target).focus();
        }
    });

    $("#showInitColumn").datagrid({
        nowrap: true,
        autoRowHeight: true,
        striped: true,
        collapsible: true,
        pagination: false,
        rownumbers: true,
        remoteSort: false,
        singleSelect: false,
        checkOnSelect: true,
        selectOnCheck: true,
        border: false,
        columns: [[
            {field: 'id', title: 'ID', width: 60,checkbox:true},
            {field: 'columnName', title: '字段名称', width: 180},
            {field: 'columnComment', title: '字段描述', width: 200}
        ]]
    });
}
var downloadFileStatus = function(status){
    $.ajax({
        url: "/download/file/status",
        data:{status:status==undefined?"":1},
        contentType: "application/json",
        async:true,
        success: function (data) {
          if(data.code==0){
              $.messager.progress('close');	// 如果提交成功则隐藏进度条
              window.clearInterval(myInterval);
              myInterval = null;
          }
        }
    });
}
var downloadFile = function(rowIndex){
    if(rowIndex){
        $table.datagrid('clearChecked');
        $table.datagrid('checkRow',rowIndex);
    }
    var checkRows = $table.datagrid('getChecked');
    if(checkRows == null || checkRows.length <= 0) return;
    var names = "";
    var ids=[];
    $.each(checkRows,function(index,item){
         names += item.name;
         ids.push(item.id)
         if(index != checkRows.length-1){
             names+=",";
         }
    });
    $.messager.confirm('确认','您确认想要下载第[<font color="red">'+names+'</font>]的记录吗？',function(r){
        if (r){
            downloadFileStatus(1);
            $.each(ids,function(index,item){
                $('#downLoadFileForm').form('submit', {
                    url: "/download/file/"+item,
                    success:function(data){
                        alert("下载完成");
                        //$.messag;=er.progress('close');	// 如果提交成功则隐藏进度条
                    }
                });
                if(myInterval == null || myInterval == undefined){
                    myInterval =window.setInterval("downloadFileStatus()",5000);
                    $.messager.progress({title:"文件下载",msg:"下载中......"});	// 显示进度条
                }
            });
        }
    });

}
var downloadFileZip = function(rowIndex){
    if(rowIndex){
        $table.datagrid('clearChecked');
        $table.datagrid('checkRow',rowIndex);
    }
    var checkRows = $table.datagrid('getChecked');
    if(checkRows == null || checkRows.length <= 0) return;
    var names = "";
    var ids=[];
    $.each(checkRows,function(index,item){
        names =names+ item.name;
        ids.push(item.id)
        if(index != checkRows.length-1){
            names=names+",";
        }
    });
    $.messager.confirm('确认','您确认想要下载第[<font color="red">'+names+'</font>]的记录吗？',function(r){
        if (r){
            downloadFileStatus(1);
            $('#downLoadFileForm').form('submit', {
                url: "/download/file/zip/"+ids,
                success:function(data){

                   // $.messager.progress('close');	// 如果提交成功则隐藏进度条
                }
            });
            if(myInterval == null || myInterval == undefined){
                myInterval =window.setInterval("downloadFileStatus()",5000);
                $.messager.progress({title:"文件下载",msg:"下载中......"});	// 显示进度条
            }
        }
    });
}
var selectTableName = function (tableName) {
    var id = $("#addForm #update_id").val();
    if(id != null && id !=''){
        $.messager.show({
            title:'提示',
            msg:'只能新增时进行选择',
            timeout:5000,
            showType:'slide'
        });

        return;
    }
    $selectTable.datagrid('clearSelections');
    $selectTable.datagrid('selectRecord',tableName);
    $.messager.confirm('确认','您确认想要选择['+tableName+']记录吗？',function(r){
        if (r){
            $("#addForm #tableName").val(tableName);
            var data = $selectTable.datagrid('getSelected');
            $("#addForm #tableComment").val(data.tableComment);
            $('#selectTableDialog').dialog('close');
        }
    });
}
var editTableInfo = function(tableId){
    openDialog(tableId);

}
var selectTableBtn = function(){
    var tableId = $('#addForm #tableId option:selected').val();
    if(tableId>0){
        $("#showInitColumn").datagrid('options').queryParams ={tableId:tableId};
        $("#showInitColumn").datagrid('options').url='/table/info/field/list';
        $("#showInitColumn").datagrid('load');
    }else{
        $.messager.show({
            title:'提示',
            msg:'请选择<导出表>',
            timeout:5000,
            showType:'slide'
        });
        $('#addForm #tableId').focus();
        return;
    }
    $('#selectTableDialog').dialog('open').dialog("setTitle","选择字段");
}
var deleteColumnInfo = function(index){
    $.messager.confirm('确认','您确认想要删除第['+index+']条记录吗？',function(r){
        if (r){
            $selectTable.datagrid('deleteRow',index);
        }
    });

}

var deleteTableInfo = function(id){
    $.messager.confirm('确认','您确认想要删除第['+id+']的记录吗？',function(r){
        if (r){
            $.ajax({
                url: "/export/table?id="+id,
                type: 'DELETE',
                contentType: "application/json",
                success: function (data) {
                    $.messager.show({
                        title:'提示',
                        msg:data.msg,
                        timeout:5000,
                        showType:'slide'
                    });
                    $selectTable.datagrid('reload');
                }
            });
        }
    });
}
var createQueryParameter = function ($search) {
    var parameter = {};
    return parameter;
}
var initDialog = function(){
    $('#addFormDialog').dialog({
        title: '新增',
        width: 1000,
        height: 700,
        closed: true,
        closable:true,
        cache: false,
        modal: true,
        resizable:true,
        maximizable:true,
        minimizable:true,
        buttons:[{
            text:'保存',
            handler:function(){
                saveOrUpdate();

            }
        },{
            text:'关闭',
            handler:function(){
                $('#addFormDialog').dialog('close');
            }
        }]
    });
    $('#selectTableDialog').dialog({
        title: '表选择',
        width: 800,
        height: 700,
        closed: true,
        closable:true,
        resizable:true,
        maximizable:true,
        minimizable:true,
        cache: false,
        modal: true,
        buttons:[{
            text:'确定',
            handler:function(){
                var checkRows = $("#showInitColumn").datagrid('getChecked');
                if(checkRows.length >0){  //追加到行
                    $.each(checkRows, function (index,item) {
                        $selectTable.datagrid('appendRow',{
                            columnName: item.columnName,
                            columnComment:item.columnComment,
                            seq: $selectTable.datagrid('getData').total+1,
                            isEffective:1
                        });
                    }) ;
                    $('#selectTableDialog').dialog('close');
                }
            }
        },{
            text:'关闭',
            handler:function(){
                $('#selectTableDialog').dialog('close');
            }
        }]
    });
}
var saveOrUpdate= function(){
    if(!$("#addForm").form("validate")){
        return;
    }
    var allDatas = $selectTable.datagrid('getData');
    if(allDatas.total>0){
        for(i=0;i<allDatas.total;i++){
            $selectTable.datagrid('beginEdit',i);
            if(!$selectTable.datagrid('validateRow',i)){
                $.messager.show({
                    title:'提示',
                    msg:'第'+(i+1)+'行数据不可用，请重新编辑',
                    timeout:5000,
                    showType:'slide'
                });
              return;
            }
            $selectTable.datagrid('endEdit',i);
        }
    }else{
        $.messager.show({title:'提示', msg:'请新增导出字段后保存', timeout:5000, showType:'slide'});
        return;
    }

    $('#addForm').form('submit', {
        url:"/export/table",
        onSubmit:function(params){
            params["columnInfos"]=JSON.stringify($selectTable.datagrid('getData').rows);
            return true;
        },
        success:function(data){
            var result = eval("("+data+")");
            if(result.code=="1"){
                $.messager.show({title:'提示',msg:result.msg,timeout:5000,showType:'slide'});
                $table.datagrid('reload');
                $('#addFormDialog').dialog('close');
            } else{
                alert(result.msg);
            }
        }
    })
}
var openDialog=function(id){
    $("#addForm #tableId").removeAttr('disabled');
    $("#addForm").form("reset");
    $selectTable.datagrid('loadData', []);
    $("#showInitColumn").datagrid('loadData', []);
    var title = "新增";
    if(id != undefined && id != null && id != ''){
        title ="修改";
        $.ajax({
            url: "/export/table?id="+id,
            type: 'GET',
            contentType: "application/json",
            success: function (data) {
                $("#addForm").form('load',data);
                $("#addForm #tableId").attr('disabled','disabled');
                //加载字段
                $selectTable.datagrid('options').queryParams ={exportTableId:id};
                $selectTable.datagrid('options').url='/table/column/info/select';
                $selectTable.datagrid('load');
            }
        });
    }
    $('#addFormDialog').dialog('open').dialog("setTitle",title);;
}
var addColumn = function () {
    $selectTable.datagrid('appendRow',{
        seq: $selectTable.datagrid('getData').total+1,
        isEffective:1
    });
}
$(function () {
    $.ajax({
        url: "/table/info/select",
        type: 'GET',
        contentType: "application/json",
        success: function (data) {
            $("#tableId").append('<option value="0" >-全部-</option>');
            $.each(data,function(index,item){
                 $("#tableId").append('<option value='+item.id+'>'+item.tableName+'<'+item.tableComment+'></option>');
            });
        }
    });
    initDataGrid();
    $("#batchDownload").unbind().click(function () {
        downloadFile();
    });
    $("#batchDownloadZip").unbind().click(function () {
        downloadFileZip();
    });
    $("#add").unbind().on("click", function () {
        openDialog();
    });
    initDialog();
});

