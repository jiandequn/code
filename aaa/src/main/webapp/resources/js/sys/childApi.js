$(function(){
//    sumbit('getPersonalSet','child/getPersonalSet.json')
    initPersonalSet();
});
function initPersonalSet(){
    $("#setPersonal_test_btn").bind("click", function() {
        var myform = $("#setPersonal");
        if(!myform.form('validate')){
            return;
        }
        var params = {};
        myform.find('input').each(function() {
            params[$(this).attr('name')] = $(this).val();
        });
        var type = $("#setPersonal #type").combobox('getValue');
        if(type == 0){ //个性化设计明细要有值
            var rows = $('#PersonalSetDtl').datagrid('getRows');
            params.PersonalSetDtls = JSON.stringify(rows);
        }else{   //type =1
            var tt = $("#weekSelect").combobox('getValues');
            if(tt == null || tt=='' || tt.length <= 0)
                var weekStr = "";
            $.each(tt,function(index,item){
                weekStr += item ;
                weekStr += ',';
            }) ;
            params.week = weekStr.substring(0,weekStr.length-1);
        }
        conn_server(params, 'child/setPersonal.json', "setPersonal_return_area");
    });
    $(".setPersonal_reset_btn").click(function() {
        $('#setPersonal').form('reset');
        $('#addPersonSet').form('clear');
        $("#PersonalSetDtl").datagrid('loadData',{
            total : 0,
            rows : []
        })
        $('#PersonalSetDtlDialog').dialog('close');
        $("#personalPanel").panel("close");
        $(".setPersonal_return_area").html("");
    });
    $('#PersonalSetDtlDialog').dialog('close');
    $("#personalPanel").panel("close");
    $("#addPersonSet #days").combobox({ required:true,
        multiple:true,editable:false });
    $("#weekSelect").combobox({editable:false,
        multiple:true });
    $('#PersonalSetDtl').datagrid({
        width:400,
        height:250,
        singleSelect:true,
        columns:[[
            {field:'lessonId',title:'课程(lessonId)',width:80},
            {field:'days',title:'播放天(days)',width:300}
        ]],
        toolbar: [{
            text : "新增",
            handler: function(){
                $('#PersonalSetDtlDialog').dialog({
                    title: '新增',
                    width: 400,
                    height: 200,
                    closed: false,
                    buttons:[{
                        text:'保存',
                        handler:function(){
                            var formObj = $("#addPersonSet");
                            if(!formObj.form('validate')){
                                return;
                            }
                            var tt = $("#addPersonSet #days").combobox('getValues');
                            var dayStr = "";
                            $.each(tt,function(index,item){
                                dayStr += item ;
                                dayStr += ',';
                            }) ;

                            $('#PersonalSetDtl').datagrid('appendRow',{
                                lessonId: $("#addPersonSet #lessonId").val(),
                                days: dayStr.substring(0,dayStr.length-1)
                            });
                            $('#PersonalSetDtlDialog').dialog('close');
                        }
                    },{
                        text:'关闭',
                        handler:function(){
                            $('#PersonalSetDtlDialog').dialog('close');
                        }
                    }]
                });
            }
        },'-',{
            text : "删除",
            handler: function(){
                var selectRow = $('#PersonalSetDtl').datagrid('getSelected');
                if(selectRow == null){
                    alert('选择行');
                    return;
                }
                var index = $('#PersonalSetDtl').datagrid('getRowIndex',selectRow);
                $('#PersonalSetDtl').datagrid('deleteRow',index);
            }
        }]
    });
}

function personSetType(record){
   if(record.label == 0){
       //部分
       $("#personalPanel").panel("open");
   }else{
       $("#personalPanel").panel("close");
   }
}
function sumbit(id,url){
    $("#"+id+"_test_btn").bind("click", function() {
        var myform = $("#"+id);
        if(!myform.form('validate')){
            return;
        }
        var params = {};
        myform.find('input').each(function() {
            params[$(this).attr('name')] = $(this).val();
        });
        conn_server(params, url, id+"_return_area");
    });
    $("."+id+"_reset_btn").click(function() {
        $("."+id+"_return_area").html("");
    });
}

function testInterface(id,type){
    var parent = $("#"+id).next();
    var url = parent.children(".api_name").children("#api_name").text();
    var apiMethod = parent.children(".api_method").children("#api_method").text();
    var myform = parent.children(".api_params").children("#params_list").children('form');
    if(!myform.form('validate')){
        return;
    }
    var params = {};
    if(apiMethod == "POST"){ //post请求
        url = url+".json"
        myform.find('input').each(function() {
            params[$(this).attr('name')] = $(this).val();
        });

    }else if(apiMethod == "GET"){//GET 请求
        url = url+".json?1=1"
        params = null;
        myform.find('input').each(function() {
            var value =  $(this).val();
            if(value != '' && value != null & value != undefined){
                 url = url+ "&" +$(this).attr('name') +"="+value;
            }
        });
    }else{
        alert("设置请求方式");
        return;
    }
    var renturn_area = parent.children(".api_params").next();
    if(type == "upload"){
        uploadApiFile(params, url, renturn_area,'headImg');
        return;
    }
    apiConn_server(params,url,apiMethod,renturn_area)

}
function apiConn_server(params, url,apiMethod, return_area) {
    $.ajax({
        data : params,
        dataType : "json",
        type :apiMethod,
        url : url,
        success : function(Data) {
            var json = Process(JSON.stringify(Data));
            return_area.html(json);
        },
        error : function(Data) {
            alert('请求失败!' + Data);
            //alert('请求失败,请稍后再试!');
        }
    });
}
function resetResult(id){
    var parent = $("#"+id).next();
    var myform = parent.children(".api_params").children("#params_list").children('form');
    myform.form('reset');
    parent.children(".api_params").next().html("");
}
//final function
function conn_server(params, url, return_area) {
    $.ajax({
        data : params,
        dataType : "json",
        type : "post",
        url : url,
        success : function(Data) {
            var json = Process(JSON.stringify(Data));
            $("." + return_area).html(json);
        },
        error : function(Data) {
            alert('请求失败!' + Data);
        }
    });
}
function uploadApiFile(params,url,return_area,fileElementId){
    $.ajaxFileUpload
        (
            {
                url:url,
                fileElementId:fileElementId,
                dataType: 'json',
                data:params,
                success: function(data, status)
                {
                    var imageStr = "";
//                                if(data.code == 'N000000'){
//                                    imageStr = data.result;
//                                    imageStr = '<img src="'+ imageStr +'" alt="用户头像"/>';
//                                }else{
//                                    imageStr +=  Process(JSON.stringify(data));
//                                }
                    imageStr +=  Process(JSON.stringify(data))
//                                var json =
                     return_area.html(imageStr);
                },
                error: function (data, status, e)
                {
                    var json = Process(JSON.stringify(data));
                    $("." + return_area).html(json);
                }
            }
        )
}
function uploadImage(params,url,return_area,fileElementId){
    $.ajaxFileUpload
        (
            {
                url:url,
                fileElementId:fileElementId,
                dataType: 'json',
                data:params,
                success: function(data, status)
                {
                    var imageStr = "";
//                                if(data.code == 'N000000'){
//                                    imageStr = data.result;
//                                    imageStr = '<img src="'+ imageStr +'" alt="用户头像"/>';
//                                }else{
//                                    imageStr +=  Process(JSON.stringify(data));
//                                }
                    imageStr +=  Process(JSON.stringify(data))
//                                var json =
                    $("." + return_area).html(imageStr);
                },
                error: function (data, status, e)
                {
                    var json = Process(JSON.stringify(data));
                    $("." + return_area).html(json);
                }
            }
        )
}