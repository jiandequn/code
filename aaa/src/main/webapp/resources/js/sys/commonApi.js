
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

function testInterface(id){
    var parent = $("#"+id).next();
    var url = parent.children(".api_name").children(".api_name_ch").text();
    var apiMethod = parent.children(".api_method").children(".api_method_ch").text();
    var myform = parent.children(".api_params").children(".params_list").children('form');
    if(!myform.form('validate')){
        return;
    }
    var params = {};
    if(apiMethod == "POST"){ //post请求
        url = url+".json"
        myform.find('input').each(function() {
            var name = $(this).attr('name');
            var value = $(this).val();
            params[name] = value;
            var urlName="{"+name+"}";
            if (url.indexOf(urlName)>=0) {
                url =url.replace(urlName,value);
                delete params[name];
            }

        });

    }else if(apiMethod == "GET"){//GET 请求
        url = url+".json?1=1"
        params = null;
        myform.find('input').each(function() {
            var name = $(this).attr('name');
            var value = $(this).val();
            var urlName="{"+name+"}";
            if (url.indexOf(urlName)>=0) {
                url =url.replace("\{"+name+"\}",value);
            }else if(value != '' && value != null & value != undefined){
                 url = url+ "&" +$(this).attr('name') +"="+value;
            }
        });
    }else{
        alert("设置请求方式");
        return;
    }
    var renturn_area = parent.children(".api_params").next();
    var enctypeStr =myform.attr('enctype');
    if( enctypeStr== "multipart/form-data"){
        var fileId =myform.find('input[type=file]');
        uploadApiFile(params, url, renturn_area,fileId.attr('id'));
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
                    imageStr +=  Process(JSON.stringify(data))
                     return_area.html(imageStr);
                },
                error: function (data, status, e)
                {
                    var json = Process(JSON.stringify(data));
                    return_area.html(json);
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