function ajaxSucFunc(data,fuc){
    if(isLogin(data)){
        if (data.code == "1") {
            layer.alert(data.msg);
            if(fuc != undefined){
                fuc();
            }
        } else {
            layer.alert(data.msg);
        }
    }
}
function ajaxErrorFunc(data){
    if(data !=null && data.responseJSON != undefined){
        if(data.responseJSON.status == 500){
            layer.alert("用户无权限");
            return;
        }
    }
    layer.alert("操作请求错误，请您稍后再试");
}