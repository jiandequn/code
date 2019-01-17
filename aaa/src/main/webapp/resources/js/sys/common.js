//登录输入提示
$(function() {
    $(".dlC .inputC").val('');
    $(".dlC .inputC").focus(function() {
        var $len = $(this).length;
		 var value = $(this).val();
		console.log(value);
        if ($len > 0) {
            $(this).next(".label").css("display", "none");
        } else {
            $(this).next(".label").css("display", "block");
        }

    });
    $(".dlC .inputC").blur(function() {
        var value = $(this).val();
        if (value.length == 0) {
            $(this).next(".label").css("display", "block");
        }
    });
    $(".dlC .label").click(function() {
        $(this).prev(".dlC .inputC").focus();
    });
});

//左侧菜单
$(function(){
	$(".s_nav").click(function(){
		 $(this).addClass("s_nav2").siblings().removeClass("s_nav2");
	});
});