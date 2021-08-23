/**
 * 登录
 */
var picCode;
 $(function(){
     picCode=drawPic();
     layui.use(['form' ,'layer'], function() {
         var form = layui.form;
         var layer = layui.layer;
         form.on("submit(login)",function () {
             login();
             return false;
         });
         var path=window.location.href;
//    	 console.info("==请求的uri:"+path);
         if(path.indexOf("kickout")>0){
             layer.alert("您的账号已在别处登录；若不是您本人操作，请立即修改密码！",function(){
                 window.location.href="/login";
             });
         }
     })
 })

function login(){
    var flag=checkParams();
    if(flag!=false){
        $.post("/user/login",$("#useLogin").serialize(),function(data){
            console.log("data:"+data)
            if(data.code=="1000"){
                layer.alert("登录成功",function () {
                    window.location.href="/home";
                });
            }else{
                //$("#password").val("");
                picCode=drawPic();
                $("#code").val("");
                //$("#smsCode").val("");
                layer.alert(data.message,function(){
                    layer.closeAll();//关闭所有弹框
                    //关闭发送验证码按钮倒计时
                });
            }
        });
    }
}

function checkParams(){
    //  校验
    var username=$("#username").val();
    var password=$("#password").val();
    var mobile=$("#mobile").val();
    var code=$("#code").val();
    if("ok"!=ValidateUtils.checkUserName(username) || "ok"!=ValidateUtils.checkSimplePassword(password)){
        layer.alert("请您输入正确的用户名和密码");
        return false;
    }
    if("ok"!=ValidateUtils.checkPicCode(code)){
        //tips层-右
        layer.tips(ValidateUtils.checkPicCode(code), '#canvas', {
            tips: [2, '#78BA32'], //还可配置颜色
            tipsMore: true
        });
        return false;
    }
    if(picCode.toLowerCase()!=code.toLowerCase()){
        //tips层-右
        layer.tips("请您输入正确的验证码", '#canvas', {
            tips: [2, '#78BA32'], //还可配置颜色
            tipsMore: true
        });
        return false;
    }
}