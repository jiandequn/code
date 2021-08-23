/**
 * 修改用户密码
 * */
//获取路径uri
$(function(){
    layui.use(['form' ,'layer'], function() {
        var form = layui.form;
        var layer = layui.layer;
        //确认手机号
        form.on("submit(updateSysUser)",function () {
            updateSysUser();
            return false;
        });
        //确认修改密码
        form.on("submit(setPwd)",function () {
            setPwd();
            return false;
        });
    })
})
function checkData(){
//  校验
    var realName=$("#useDetailForm #sysRealName").val();
    if(ValidateUtils.checkUserName(realName) != 'ok'){
        layer.tips(ValidateUtils.checkUserName(realName), '#sysRealName', {
            tips: [2, '#78BA32'], //还可配置颜色
            tipsMore: true
        });
    }
    var mobile=$("#useDetailForm #telephone").val();
    if("ok"!=ValidateUtils.checkMobile(mobile)){
        //tips层-右
        layer.tips(ValidateUtils.checkMobile(mobile), '#telephone', {
            tips: [2, '#78BA32'], //还可配置颜色
            tipsMore: true
        });
        return false;
    }
    var email=$("#useDetailForm #sysEmail").val();
    if(ValidateUtils.checkEmail(email) != 'ok'){
        layer.tips(ValidateUtils.checkEmail(email), '#sysEmail', {
            tips: [2, '#78BA32'], //还可配置颜色
            tipsMore: true
        });
        return false;
    }
}
function validateOldPwd(){
    var oldPwd = $("#setPwdForm #oldPwd").val();
    if(oldPwd == null || oldPwd == ''){
        $("#setPwdForm #pwdFlag").val(0);
        return;
    }
    $.post("/user/validatePwd", {"oldPwd":oldPwd}, function (data) {
        console.log("data:" + data)
        if (data.code == "1000") {
            $("#setPwdForm #pwdFlag").val(1);
            $("#setPwdForm #oldPwdVidate").html('<div class="layui-icon layui-icon-ok" style="font-size: 30px;color:green;"></div>');
        } else {
            $("#setPwdForm #pwdFlag").val(0);
            $("#setPwdForm #oldPwdVidate").html('<div class="layui-icon layui-icon-close" style="font-size: 30px;color:red;"></div>');
        }
    });
}
function updateSysUser(){
    var flag=checkData();
    if(flag!=false){
        var realName = $("#useDetailForm #sysRealName").val();
        var mobile=$("#useDetailForm #telephone").val();
        var email=$("#useDetailForm #sysEmail").val();
        $.post("/user/updateUserByMe",{"mobile":mobile,"realName":realName,"email":email},function(data){
            console.log("data:"+data)
            if(data.code=="1000"){
                layer.closeAll();
                layer.alert("修改成功");
                $("#useDetailForm #sysUsername").val('');
                $("#useDetailForm #telephone").val('');
                $("#useDetailForm #sysEmail").val('');
                $("#useDetailForm #sysRealName").val('');
            }else{
                layer.alert(data.message);
            }
        });

    }
}
function getCurrentUser(){
    $("#useDetailForm #sysUsername").val();
    $("#useDetailForm #telephone").val();
    $("#useDetailForm #sysEmail").val();
    $("#useDetailForm #sysRealName").val();
    $.post("/user/getCurrentUser",{},function(data){
        if(data.code=="1000"){
            $("#useDetailForm #sysUsername").val(data.obj.username);
            $("#useDetailForm #telephone").val(data.obj.mobile);
            $("#useDetailForm #sysEmail").val(data.obj.email);
            $("#useDetailForm #sysRealName").val(data.obj.realName);
        }else{
            layer.alert(data.message);
        }
    });
}
function setPwd(){
    var pwd=$("#pwd").val();
    var isPwd=$("#isPwd").val();
    if( $("#setPwdForm #pwdFlag").val() != 1){
        layer.tips("请输入正确原始密码", '#oldPwd', {
            tips: [2, '#78BA32'], //还可配置颜色
            tipsMore: true
        });
        return;
    }
    if(pwd!=isPwd){
        //tips层-右
        $("#isPwd").val("");
        $("#isPwd").val("");
        layer.tips("两次输入的密码不一致", '#isPwd', {
            tips: [2, '#78BA32'], //还可配置颜色
            tipsMore: true
        });
        return false;
    }
    if("ok"!=ValidateUtils.checkSimplePassword(pwd) || "ok"!=ValidateUtils.checkSimplePassword(isPwd)){
        //tips层-右
        $("#pwd").val("");
        $("#pwd").val("");
        $("#isPwd").val("");
        $("#isPwd").val("");
        layer.alert("密码格式有误，请您重新输入");
        return false;
    }
    $.post("/user/setPwd",{"pwd":pwd,"isPwd":isPwd},function(data){
        console.log("data:"+data);
        if(data.code=="1000"){
            layer.alert("操作成功",function () {
                layer.closeAll();
                window.location.href="/logout";
            });
        }else{
            layer.alert(data.message,function () {
                layer.closeAll();
                //window.location.href="/index";
            });
        }
    });
}
