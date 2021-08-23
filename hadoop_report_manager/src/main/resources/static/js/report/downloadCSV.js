var $search = $("#contentToolbar");
var $table = $("#content_table");
var initDataGrid = function () {
    var parameter=createQueryParameter($search);
    //parameter.columnId = $search.find("select[name='columnId'] option:selected").val();
    $table.datagrid({
        nowrap: true,
        autoRowHeight: true,
        url: '/table/info/download/list',
        striped: true,
        collapsible: true,
        pagination: false,
        toolbar: "#contentToolbar",
        pageSize: 30,
        queryParams: parameter,
        pageList: pageList,
        rownumbers: false,
        remoteSort: false,
        singleSelect: true,
        checkOnSelect: false,
        selectOnCheck: false,
        showFooter:true,
        border: false,
        columns: [[
            {field: 'tableName', title: '表', width: 250},
            {field: 'tableComment', title: '表名称', width: 150},
            {field: 'createTime', title: '创建时间', width: 150},
            {field: 'op',title: '操作',width: 300,formatter: function (value, row, index) {
                var vt = "<a href='/download/export/file/"+row.tableName+"'>下载CSV文件</a>";
                if(row.updateSql != null && row.updateSql != ''){
                    vt+="&nbsp;&nbsp;&nbsp;<a onclick='syncData(\""+row.tableName+"\")'>同步数据</a>";
                }
                vt+="&nbsp;&nbsp;&nbsp;<a onclick='deleteTable(\""+row.tableName+"\")'>删除</a>";
                return vt;
               }
            }
        ]]
    });
}
var syncData = function(tableName){
    $.messager.confirm('确认','您确认想要同步['+tableName+']的表数据吗？',function(r) {
        if (r) {
            $.ajax({
                url: "/api/update/"+tableName,
                type: 'POST',
                data:{tableName:tableName},
                beforeSend: function () {
                    $.messager.progress({
                        title: titleInfo,
                        msg: '正在执行操作,请稍后'
                    });
                },
                complete: function () {
                    $.messager.progress('close');
                },
                success: function (data) {
                    if(data == "success"){
                        $.messager.show({title: titleInfo, msg:"同步更新成功" , timeout: timeoutValue, showType: 'slide'});
                    }else{
                        $.messager.show({title: titleInfo, msg:"同步更新失败" , timeout: timeoutValue, showType: 'slide'});
                    }

                    $table.datagrid('load');
                }
            });
        }
    });

}
var deleteTable = function(tableName){
    $.messager.confirm('确认','您确认想要删除['+tableName+']的表吗？',function(r){
        if (r){
            $.ajax({
                url: "/table/info/download/drop/"+tableName,
                type: 'GET',
                success: function (data) {
                    if(data == 1){
                        $.messager.show({title: titleInfo, msg:data.data , timeout: timeoutValue, showType: 'slide'});
                    }else{
                        $.messager.show({title: titleInfo, msg:"删除失败" , timeout: timeoutValue, showType: 'slide'});
                    }

                    $table.datagrid('load');
                }
            });
        }
    });

}
var createQueryParameter = function ($search) {
    var parameter = {};
    return parameter;
}

$(function () {
    initDataGrid();
    $("#queryContent").unbind().click(function () {
        $table.datagrid('options').queryParams = createQueryParameter($search);
        $table.datagrid('load');
    });

});

