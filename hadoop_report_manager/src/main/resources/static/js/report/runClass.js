var $search = $("#contentToolbar");
var $table = $("#content_table");
var runTypeArrs=[{'id':1,"text":"按年"},{'id':2,"text":"按季度"},
    {'id':3,"text":"按月"},{'id':4,"text":"按周"},{'id':5,"text":"按天"},{'id':6,"text":"总统计"}];
var initDataGrid = function () {
    var parameter=createQueryParameter($search);

    $table.datagrid({
        nowrap: true,
        autoRowHeight: true,
        url: '/run/class/list',
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
            {field: 'name', title: 'java类路径', width: 250},
            {field: 'title', title: 'java类描述', width: 200},
            {field: 'runType', title: '类型', width: 80,formatter:function(value,row,index){
                return runTypeArrs[value-1].text;
            }},
            {field: 'isEffective', title: '状态', width: 80,formatter:function(value,row,index){
                if(value==0) return "禁用"
                return "启用"
            }},
            {field: 'createTime', title: '创建时间', width: 120},
            {field: 'op',title: '操作',width: 160,formatter: function (value, row, index) {
                var vt = "<a onclick='edit("+row.id+")'>编辑</a>&nbsp;&nbsp;&nbsp;";
                    vt+="<a onclick='del("+row.id+")'>删除</a>";
                return vt;
               }
            }
        ]]
    });
}
var edit = function(tableId){
    openDialog(tableId);

}

var del = function(id){
    $.messager.confirm('确认','您确认想要删除第['+id+']的记录吗？',function(r) {
        if (r) {
            $.ajax({
                url: "/run/class?id="+id,
                type: 'DELETE',
                contentType: "application/json",
                success: function (data) {
                    $table.datagrid('reload');
                    $.messager.show({
                        title:'提示',
                        msg:data.msg,
                        timeout:5000,
                        showType:'slide'
                    });
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
    $('#addFormDialog').dialog({
        title: '新增',
        width: 650,
        height: 500,
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
                $('#addFormDialog').dialog('close');
            }
        }]
    });
}
var saveOrUpdate= function(){
    if(!$("#addForm").form("validate")){
        return;
    }
    $('#addForm').form('submit', {
        url:"/run/class",
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
    $("#addForm").form("reset");
    var title = "新增";
    if(id != undefined && id != null && id != ''){
        title ="修改";
        $.ajax({
            url: "/run/class?id="+id,
            type: 'GET',
            contentType: "application/json",
            success: function (data) {
                $("#addForm").form('load',data);
            }
        });
    }
    $('#addFormDialog').dialog('open').dialog("setTitle",title);;
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
    $.each(runTypeArrs,function(index,item){
        $("#runType").append('<option value=' + item.id + '>' + item.text + '</option>');
    });
});

