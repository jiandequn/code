var $search = $("#contentToolbar");
var $table = $("#content_table");
var $selectTable = $("#selectTable");
var initDataGrid = function () {
    var parameter=createQueryParameter($search);

    $table.datagrid({
        nowrap: true,
        autoRowHeight: true,
        url: '/table/info/list',
        striped: true,
        collapsible: true,
        pagination: true,
        toolbar: "#contentToolbar",
        pageSize: 30,
        queryParams: parameter,
        pageList: pageList,
        rownumbers: true,
        remoteSort: false,
        singleSelect: true,
        checkOnSelect: false,
        selectOnCheck: false,
        showFooter:true,
        border: false,
        columns: [[
            {field: 'id', title: 'ID', width: 60},
            {field: 'tableName', title: '表英文名称', width: 180},
            {field: 'tableComment', title: '表中文名称', width: 200},
            {field: 'isEffective', title: '状态', width: 80,formatter:function(value,row,index){
                if(value==0) return "禁用"
                return "启用"
            }},
            {field: 'createTime', title: '创建时间', width: 120},
            {field: 'op',title: '操作',width: 160,formatter: function (value, row, index) {
                var vt = "<a onclick='editTableInfo("+row.id+")'>编辑</a>&nbsp;&nbsp;&nbsp;";
                    vt+="<a onclick='deleteTableInfo("+row.id+")'>删除</a>";
                return vt;
               }
            }
        ]]
    });
    $selectTable.datagrid({
        nowrap: true,
        autoRowHeight: true,
        url: '/table/info/showList',
        striped: true,
        collapsible: true,
        pagination: false,
        pageSize: 30,
        pageList: pageList,
        rowumbers: false,
        remoteSort: false,
        singleSelect: true,
        checkOnSelect: false,
        selectOnCheck: false,
        border: false,
        idField:"tableName",
        columns: [[
            {field: 'tableName', title: '表英文名称', width: 150,formatter: function (value, row, index) {
                var vt = "<a onclick='selectTableName(\""+value+"\")'>"+value+"</a>";
                return vt;
            }},
            {field: 'tableComment', title: '表中文名称', width: 200},
            {field: 'createTime', title: '创建时间', width: 150}
        ]]
    });
}
var selectTableName = function (tableName) {
    var id = $("#addTableInfoForm #update_id").val();
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
            $("#addTableInfoForm #tableName").val(tableName);
            var data = $selectTable.datagrid('getSelected');
            $("#addTableInfoForm #tableComment").val(data.tableComment);
            $('#selectTableDialog').dialog('close');
        }
    });
}
var editTableInfo = function(tableId){
    openDialog(tableId);

}
var selectTableBtn = function(){
    $('#selectTableDialog').dialog('open').dialog("setTitle","选择表");

}
var deleteTableInfo = function(tableId){
    $.messager.confirm('确认','您确认想要删除第['+tableId+']的记录吗？',function(r) {
        if (r) {
            $.ajax({
                url: "/table/info?tableId="+tableId,
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
    //parameter.dateType=$("#dateType").combobox('getValue');
    //parameter.parentColumnId=$search.find("input[name='parentColumnId']").val();
    //parameter.userType=$search.find("select[name='userType'] option:selected").val();
    return parameter;
}
var initDialog = function(){
    $('#addTableInfoFormDialog').dialog({
        title: '新增',
        width: 1000,
        height: 700,
        closed: true,
        closable:true,
        cache: false,
        modal: true,
        resizable:true,
        buttons:[{
            text:'保存',
            handler:function(){
                saveOrUpdate();

            }
        },{
            text:'关闭',
            handler:function(){
                $('#addTableInfoFormDialog').dialog('close');
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
        cache: false,
        modal: true,
        buttons:[{
            text:'关闭',
            handler:function(){
                $('#selectTableDialog').dialog('close');
            }
        }]
    });
}
var saveOrUpdate= function(){
    if(!$("#addTableInfoForm").form("validate")){
        return;
    }
    $('#addTableInfoForm').form('submit', {
        url:"/table/info",
        success:function(data){
            var result = eval("("+data+")");
            if(result.code=="1"){
                $.messager.show({title:'提示',msg:result.msg,timeout:5000,showType:'slide'});
                $table.datagrid('reload');
                $('#addTableInfoFormDialog').dialog('close');
            } else{
                alert(result.msg);
            }
        }
    })
}
var openDialog=function(tableId){
    $("#addTableInfoForm").form("reset");
    var title = "新增";
    if(tableId != undefined && tableId != null && tableId != ''){
        title ="修改";
        $.ajax({
            url: "/table/info?tableId="+tableId,
            type: 'GET',
            contentType: "application/json",
            success: function (data) {
                $("#addTableInfoForm").form('load',data);
            }
        });
    }
    $('#addTableInfoFormDialog').dialog('open').dialog("setTitle",title);;
}
$(function () {
    initDataGrid();
    $("#queryContent").unbind().click(function () {
        $table.datagrid('options').queryParams = createQueryParameter($search);
        $table.datagrid('load');
    });
    $("#add").unbind().on("click", function () {
        ;
    });
    initDialog();
});

