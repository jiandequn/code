var $search = $("#contentToolbar");
var $globalUser_content_table = $("#globalUser_content_table");
var initDataGrid = function () {
    var parameter=createQueryParameter($search);
    //parameter.columnId = $search.find("select[name='columnId'] option:selected").val();
    $globalUser_content_table.datagrid({
        nowrap: true,
        autoRowHeight: true,
        url: '/report/global/user/list',
        striped: true,
        collapsible: true,
        pagination: true,
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
            {field: 'dateTime', title: '日期', width: 100},
            {field: 'year', title: '年份', width: 100,hidden:true},
            {field: 'month', title: '月份', width: 100,hidden:true},
            {field: 'day', title: '天', width: 100,hidden:true},
            {field: 'week', title: '周', width: 100,hidden:true},
            {field: 'parentColumnId', title: '一级栏目', width: 100},
            {field: 'userType', title: '用户类型', width: 100},
            //{field: 'contentType', title: '内容类型', width: 100, formatter: function (value, row, index) {
            //    return value==1?"专辑":"专题";
            //    }
            //},
            //{field: 'contentName', title: '内容名称', width: 100, formatter: function (value, row, index) {
            //        return value;
            //    }
            //},
            //{field: 'sn', title: 'SN', width: 100},
            //{field: 'mac', title: 'Mac', width: 100},
            //{field: 'userId', title: '用户', width: 100},
            {field: 'userCount',title: '用户数',width: 120}
        ]]
    });
}
var createQueryParameter = function ($search) {
    var parameter = {};
    parameter.dateType=$("#dateType").combobox('getValue');
    parameter.parentColumnId=$search.find("input[name='parentColumnId']").val();
    parameter.userType=$search.find("select[name='userType'] option:selected").val();
    return parameter;
}

$(function () {
    //$.ajax({
    //    url: "/column/management/getParentColumnId.json",
    //    type: 'POST',
    //    contentType: "application/json",
    //    success: function (data) {
    //        if(data.codeV>0){
    //            $.each(data.result,function (i,v) {
    //                $search.find("select[name='columnId']").append("<option value='"+v.columnId+"'>"+v.columnName+"</option>");
    //            });
    //            initDataGrid();
    //        }
    //    }
    //});
    initDataGrid();
    $("#queryContent").unbind().click(function () {
        $globalUser_content_table.datagrid('options').queryParams = createQueryParameter($search);
        $globalUser_content_table.datagrid('load');
    });
    $("#removeAllBookMark").unbind().on("click", function () {
        $.messager.confirm(titleInfo, "确认要进行ES收藏记录清空操作?<br>", function (r) {
            if (r) {
                $.ajax({
                    url: "/user-center/bookmark/removeAllBookMark.json",
                    type: 'POST',
                    data:{"columnId":$search.find("select[name='columnId']").val()},
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
                        $.messager.show({
                            title: titleInfo,
                            msg: data.message,
                            timeout: timeoutValue,
                            showType: 'slide'
                        });
                        $globalUser_content_table.datagrid('load');
                    }
                });
            }
        });
    });
    $('#dateType').combobox({
        onSelect: function(record){
            dateTypeSelect(record,$globalUser_content_table);
            $globalUser_content_table.datagrid('options').queryParams = createQueryParameter($search);
            $globalUser_content_table.datagrid('load');

        }
    });

});

