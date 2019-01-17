$(function() {

});
function selectLoginType(rec){
   if(rec.id==0){ //密码登录
       $("#selectLoginType").text("userPwd");
       $("#descriptionValue").text("用户密码");
       $("#loginTypeVal").attr("name","userPwd");
   }else if(rec.id ==1){     //验证码登录
       $("#selectLoginType").text("captcha");
       $("#descriptionValue").text("验证码");
       $("#loginTypeVal").attr("name","captcha");
   }
}
