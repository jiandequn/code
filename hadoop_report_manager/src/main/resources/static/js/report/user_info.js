var $search = $("#contentToolbar");
var $table = $("#content_table");
var initDataGrid = function () {
    var parameter=createQueryParameter($search);
    $table.datagrid({
        nowrap: true,
        autoRowHeight: true,
        url: '/user/info/list',
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
            {field: 'mac', title: 'MAC', width: 120},
            {field: 'sn', title: 'SN', width: 180},
            {field: 'userId', title: '用户ID', width: 100},
            {field: 'userType', title: '状态', width: 80},
            {field: 'createTime', title: '创建时间', width: 180}
        ]]
    });
}

var createQueryParameter = function ($search) {
    var parameter = {};
    var startDate = $("#startDate").datebox('getValue');
    var endDate = $("#endDate").datebox('getValue');
    if(startDate != null  && startDate != ''){
        parameter["startDate"] =startDate;
    }
    if(endDate != null && endDate != ''){
        parameter["endDate"] =endDate;
    }
    //parameter.dateType=$("#dateType").combobox('getValue');
    //parameter.parentColumnId=$search.find("input[name='parentColumnId']").val();
    //parameter.userType=$search.find("select[name='userType'] option:selected").val();
    return parameter;
}



$(function () {
    initDataGrid();
    $("#queryContent").unbind().click(function () {
        $table.datagrid('options').queryParams = createQueryParameter($search);
        $table.datagrid('load');
    });
    $("#exportContent").unbind().on("click", function () {
        $.messager.confirm('确认','您确认想要下载吗？',function(r){
            if (r){
                var $form = $('<form method="GET"></form>');
                $form.attr('action', '/user/info/exportFile');
                $form.appendTo($('body'));
                $form.submit();
            }
        });
    });
});

