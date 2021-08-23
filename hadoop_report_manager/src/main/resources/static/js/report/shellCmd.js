var $table = $("#content_table");
var runTypeArrs=[{'id':1,"text":"按年"},{'id':2,"text":"按季度"},
    {'id':3,"text":"按月"},{'id':4,"text":"按周"},{'id':5,"text":"按天"},{'id':6,"text":"总统计"}];
var initDataGrid = function () {
    $table.datagrid({
        nowrap: true,
        autoRowHeight: true,
        striped: true,
        collapsible: true,
        pagination: true,
        toolbar: "#contentToolbar",
        pageSize: 30,
        url: '/run/class/list',
        queryParams:{isEffective:1},
        pageList: pageList,
        rownumbers: true,
        remoteSort: false,
        singleSelect: true,
        checkOnSelect: false,
        selectOnCheck: false,
        border: false,
        columns: [[
            {field: 'id', title: 'ID', width: 60,checkbox:true},
            {field: 'name', title: 'JAVA类路径', width: 400},
            {field: 'title', title: 'JAVA类描述', width: 250},
            {field: 'runType', title: '类型', width: 80,formatter:function(value,row,index){
                return runTypeArrs[value-1].text;
            }},
            //{field: 'op',title: '操作',width: 160,formatter: function (value, row, index) {
            //        var vt = "<a class='shellId' onclick='shellExec(\""+row.id+"\")'>执行</a>&nbsp;&nbsp;&nbsp;";
            //        return vt;
            //   }
            //}
        ]]
    });

}
function execShell(){
    var checkRows = $("#content_table").datagrid('getChecked');
    if(checkRows == null || checkRows.length < 1){
        $.messager.show({
            title:'提示',
            msg:'未勾选执行任务',
            timeout:5000,
            showType:'slide'
        }); return;
    }
    var ids="",texts="";
    var len = checkRows.length;
    $.each(checkRows,function(index,item){
        ids+=item.id;
        texts+=item.text;
        if(len > index+1){
            texts+=",";
            ids+=",";
        }
    });
    //验证时间
    var startDate = $("#startDate").datebox('getValue');
    var endDate = $("#endDate").datebox('getValue');
    if((startDate == null||startDate ==''|| startDate == undefined)||
        (startDate == null||startDate ==''|| startDate == undefined)){
        $.messager.show({
            title:'提示',
            msg:'请选择时间',
            timeout:5000,
            showType:'slide'
        }); return;
    }
    if(startDate >= endDate){
        $.messager.show({
            title:'提示',
            msg:'时间范围不对',
            timeout:5000,
            showType:'slide'
        }); return;
    }
    var msg = '您确认想要执行勾选的[<font style="background:yellow; color:red">'+texts+'</font>]按时间[<font style="background:yellow; color:red">'+startDate+'~'+endDate+'</font>]进行统计吗？'
    $.messager.confirm('确认',msg,function(r){
        if (r){
            shellExec(ids);
        }
    });
}
function shellExec(id){
    var startDate = $("#startDate").datebox('getValue');
    var endDate = $("#endDate").datebox('getValue');
    $.ajax({
        url: "/shell/cmd/exec?threadName=tableInfo&runClazzIds="+id+"&timePattern="+startDate+"~"+endDate,
        type: 'POST',
        data:{runClazzIds:id,threadName:"tableInfo",timePettern:startDate+"~"+endDate},
        contentType: "application/json",
        success: function (data) {
            $("#execContent").linkbutton('disable');
            $.messager.progress();
            $.messager.show({
                title:'提示',
                msg:data.msg,
                timeout:5000,
                showType:'slide'
            });
        }
    });
}
function shellStatus(){
    $.ajax({
        url: "/shell/cmd/status?threadName=tableInfo",
        type: 'GET',
        contentType: "application/json",
        success: function (data) {
            if(data.code == 1){
               var t = $.messager.progress('close');
                t= $.messager.progress();
                $("#execContent").linkbutton('disable');

            }else{
                $("#execContent").linkbutton('enable');
                $.messager.progress('close');
            }

        }
    });
}

$(function () {
    initDataGrid();
    //$.ajax({
    //    url: "/shell/cmd/combox",
    //    type: 'GET',
    //    contentType: "application/json",
    //    success: function (data) {
    //        //$("#tableId").append('<option value="0" >-全部-</option>');
    //        //$.each(data,function(index,item){
    //        //     $("#tableId").append('<option value='+item.id+'>'+item.text+'</option>');
    //        //});
    //        $table.datagrid({
    //            data:data
    //        });
    //
    //    }
    //});
    shellStatus();
    window.setInterval("shellStatus()",5000);
    var curr = new Date(); // get current date
    var first = curr.getDate() - curr.getDay() - 6;
    var last = first + 7; // last day is the first day + 6
    var startDate = new Date(curr.setDate(first));
    var endDate = new Date(curr.setDate(last));
    $("#startDate").datebox('setValue', formatDate(startDate));
    $("#endDate").datebox('setValue', formatDate(endDate));
});
function formatDate(now) {
    var year=now.getFullYear();
    var month=now.getMonth()+1;
    var date=now.getDate();
    return year+"-"+month+"-"+date;
}

