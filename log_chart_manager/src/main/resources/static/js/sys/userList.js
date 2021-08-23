/**
 * 用户列表
 */
var pageCurr;
$(function() {
    layui.use('table', function(){
        var table = layui.table
            ,form = layui.form;

        tableIns=table.render({
            elem: '#uesrList'
            ,url:'list'
        	,method: 'post' //默认：get请求
            ,cellMinWidth: 80
            ,page: true,
            request: {
                pageName: 'page' //页码的参数名称，默认：page
                ,limitName: 'limit' //每页数据量的参数名，默认：limit
            },response:{
                statusName: 'status' //数据状态的字段名称，默认：code
                ,statusCode: 200 //成功的状态码，默认：0
                ,countName: 'total' //数据总数的字段名称，默认：count
                ,dataName: 'rows' //数据列表的字段名称，默认：data
            }
            ,cols: [[
                {type:'numbers'}
                ,{field:'id', title:'ID', width:80, unresize: true, sort: true}
                ,{field:'userName', title:'用户名',width:140}
                ,{field:'realName', title:'真实名',width:140}
                ,{field:'telephone', title:'手机号',width:140}
                ,{field:'email', title: '邮箱',width:140}
                //,{field:'roleNames', title: '角色名称', }
                ,{field:'createTime', title: '创建时间',align:'center',width:120}
                ,{field:'job', title:'是否在职',width:95,align:'center',templet:'#jobTpl'}
                ,{field:'del', title:'状态',width:95,align:'left',templet:function(r){
                    return r.del?"<font color='red'>已删除</font>":"<font color='green'>正常</font>";
                }}
                ,{fixed:'right', title:'操作', width:250,align:'center', toolbar:'#optBar'}
            ]]
            ,  done: function(res, curr, count){
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                //console.log(res);
                //得到当前页码
                //console.log(curr);
                //得到数据总量
                //console.log(count);
                pageCurr=curr;
            }
        });

        //监听在职操作
        form.on('switch(isJobTpl)', function(obj){
            //console.log(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
        	var data = obj.data;
            setJobUser(obj,this.value,this.name,obj.elem.checked);
        });
        //监听工具条
        table.on('tool(userTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'del'){
                delUser(data,data.id,data.userName);
            } else if(obj.event === 'edit'){
                //编辑
                getUserAndRoles(data,data.id);
            } else if(obj.event === 'recover'){
                //恢复
                recoverUser(data,data.id);
            }else if(obj.event == 'userRoleSet'){
                openUserRolePanel(data.id);
            }else if(obj.event == 'resetPwd'){
                openResetPwd(data);
            }

        });
        //监听提交
        form.on('submit(userSubmit)', function(data){
            // TODO 校验
            formSubmit(data);
            return false;
        });

    });
    //搜索框
    layui.use(['form','laydate'], function(){
        form = layui.form ,layer = layui.layer
            ,laydate = layui.laydate;
        //日期
        laydate.render({
            elem: '#insertTimeStart'
        });
        laydate.render({
            elem: '#insertTimeEnd'
        });
        form.verify({
            username: function(value, item){ //value：表单的值、item：表单的DOM对象
                if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
                    return '用户名不能有特殊字符';
                }
                if(/(^\_)|(\__)|(\_+$)/.test(value)){
                    return '用户名首尾不能出现下划线\'_\'';
                }
                if(/^\d+$/.test(value)){
                    return '用户名不能全为数字';
                }
            }

            //我们既支持上述函数式的方式，也支持下述数组的形式
            //数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
            ,pass: [
                /^[\S]{6,12}$/
                ,'密码必须6到12位，且不能出现空格'
            ]
        });
        //TODO 数据校验
        //监听搜索框
        form.on('submit(searchSubmit)', function(data){
            //重新加载table
            load(data);
            return false;
        });

    });
});
//设置用户是否离职
function setJobUser(obj,id,nameVersion,checked){
//	var version = obj.data.version;
	var name=nameVersion.substring(0,nameVersion.indexOf("_"));
	var version=nameVersion.substring(nameVersion.indexOf("_")+1);
	//console.log("name:"+name);
	//console.log("version:"+version);
    var isJob=checked ? 0 : 1;
    var userIsJob=checked ? "在职":"离职";
    //是否离职
    layer.confirm('您确定要把用户：'+name+'设置为'+userIsJob+'状态吗？', {
        btn: ['确认','返回'] //按钮
    }, function(){
        $.ajax({
            type: "POST",
            data: {"id":id,"job":isJob,"version":version},
            url: "setIsJob",
            success: function (data) {
                ajaxSucFunc(data, function () {
                    layer.closeAll();
                    load();
                });
            },
            error: function(data){
                ajaxErrorFunc(data);
                load();
            }
        });
    }, function(){
        layer.closeAll();
        //加载load方法
        load();
    });
}
//提交表单
function formSubmit(obj){
	var currentUser=$("#currentUser").html();
    if($("#id").val()==currentUser){
        layer.confirm('更新自己的信息后，需要您重新登录才能生效；您确定要更新么？', {
            btn: ['返回','确认'] //按钮
        },function(){
            layer.closeAll();
        },function() {
            layer.closeAll();//关闭所有弹框
            submitAjax(obj,currentUser);
        });
    }else{
        submitAjax(obj,currentUser);
    }
}
function submitAjax(obj,currentUser){
    $.ajax({
        type: "POST",
        data: $("#userForm").serialize(),
        url: "setUser",
        success: function (data) {
            ajaxSucFunc(data, function () {
                if($("#id").val()==currentUser){
                    //如果是自己，直接重新登录
                    parent.location.reload();
                }else{
                    layer.closeAll();
                    cleanUser();
                    load();
                }
            });
        },
        error: ajaxErrorFunc
    });
}

function cleanUser(){
    $("#userName").val("");
	$("#realName").val("");
	$("#telephone").val("");
	$("#email").val("");
}

//开通用户
function addUser(){
    $.get("../role/getAll",function(data){
        if(data!=null){
            //显示角色数据
            //$("#roleDiv").empty();
            //$.each(data, function (index, item) {
            //    // <input type="checkbox" name="roleId" title="发呆" lay-skin="primary"/>
            //    var roleInput=$("<input type='checkbox' name='roleId' value="+item.id+" title="+item.roleName+" lay-skin='primary'/>");
            //    //未选中
            //    /*<div class="layui-unselect layui-form-checkbox" lay-skin="primary">
            //        <span>发呆</span><i class="layui-icon">&#xe626;</i>
            //        </div>*/
            //    //选中
            //    // <div class="layui-unselect layui-form-checkbox layui-form-checked" lay-skin="primary">
            //    // <span>写作</span><i class="layui-icon">&#xe627;</i></div>
            //    var div=$("<div class='layui-unselect layui-form-checkbox' lay-skin='primary'>" +
            //        "<span>"+item.roleName+"</span><i class='layui-icon'>&#xe626;</i>" +
            //        "</div>");
            //    $("#roleDiv").append(roleInput).append(div);
            //})
            openUser(null,"开通用户");
            //重新渲染下form表单 否则复选框无效
            layui.form.render('checkbox');
        }else{
            //弹出错误提示
            layer.alert("获取角色数据有误，请您稍后再试",function () {
                layer.closeAll();
            });
        }
    });
}
function openUser(id,title){
	if(id==null || id==""){
        $("#id").val("");
    }
    layer.open({
        type:1,
        title: title,
        fixed:false,
        resize :false,
        shadeClose: true,
        area: ['550px','600px'],
        content:$('#setUser'),
        end:function(){
        	cleanUser();
        }
    });
}
/**
 * 重置密码
 * @param userId
 */
function openResetPwd(user){
    $("#resetPwdForm #userId").val(user.id);
    $("#resetPwdForm #userNamePwd").val(user.realName);
    layer.open({
        type: 1,
        title: '重置密码',
        fixed:true,
        resize :true,
        shadeClose: true,
        area: ['550px',"400px"],
        content: $("#resetPwdPanel") //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
        ,end: function(layero, index){
            $("#firstPwd").val("");
            $("#secondPwd").val("");
        }
        ,btn: ['保存', '关闭']
        ,btn1: function(index, layero){
            //按钮【按钮二】的回调
            var firstPwd = $("#firstPwd").val();
            var secondPwd = $("#secondPwd").val();
            var userId =  $("#resetPwdForm #userId").val();
            var userName = $("#resetPwdForm #userNamePwd").val();
            if (firstPwd == null || firstPwd.length < 6 || firstPwd == undefined) {
                layer.tips("密码不符合要求", '#firstPwd', {
                    tips: [2, '#78BA32'], //还可配置颜色
                    tipsMore: true
                });
                return;
            }
            if (firstPwd != secondPwd) {
                layer.tips("密码不符合要求", '#secondPwd', {
                    tips: [2, '#78BA32'], //还可配置颜色
                    tipsMore: true
                });
                return;
            }
            layer.confirm('您确定要提交设置密码[' + userName + ']的角色信息', {
                btn: ['确认', '返回'] //按钮
            }, function () {
                $.ajax({
                    type: "POST",
                    data: {"id": userId, "password": firstPwd},
                    url: "resetPwd",
                    success: function (data) {
                        ajaxSucFunc(data);
                    },
                    error: ajaxErrorFunc
                });
            }, function () {
            });
            return false //开启该代码可禁止点击该按钮关闭
        }
        ,btn2: function(index, layero){
            //按钮【按钮二】的回调
            return true  //开启该代码可禁止点击该按钮关闭
        }
    });
}
function openUserRolePanel(userId){
    layer.open({
        type: 2,
        title: '更新用户角色',
        fixed:true,
        resize :true,
        shadeClose: true,
        area: ['550px',"600px"],
        content: 'userRoleSet?userId='+userId //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
        ,btn: ['保存', '关闭']
        ,btn1: function(index, layero){
        //按钮【按钮二】的回调
            $(layero).find("iframe")[0].contentWindow.setRoleToUser();
            return false //开启该代码可禁止点击该按钮关闭
        }
        ,btn2: function(index, layero){
            //按钮【按钮二】的回调
            return true  //开启该代码可禁止点击该按钮关闭
        }
    });
}
function getUserAndRoles(obj,id) {
	//如果已经离职，提醒不可编辑和删除
	if(obj.job){
        layer.alert("该用户已经离职，不可进行编辑；</br>  如需编辑，请设置为<font style='font-weight:bold;' color='green'>在职</font>状态。");
    }else if(obj.del){
        layer.alert("该用户已经删除，不可进行编辑；</br>  如需编辑，请先<font style='font-weight:bold;' color='blue'>恢复</font>用户状态。");
    }else{
	    //回显数据
	    $.get("getUser",{"id":id},function(data){
	    	if(isLogin(data)){
		        if(data.code=="1" && data.result != null){
                    var user = data.result;
                    form.val("userFormFilter", {
                        "userName": user.userName
                        ,"sex": user.sex
                        ,"version": user.version
                        ,"realName": user.realName
                        ,"email": user.email
                        ,"telephone": user.telephone
                        ,"id":user.id
                    });
		            openUser(id,"设置用户");
		            //重新渲染下form表单中的复选框 否则复选框选中效果无效
		             layui.form.render();
		            //layui.form.render('checkbox');
		        }else{
		            //弹出错误提示
		            layer.alert(data.msg,function () {
		                layer.closeAll();
		            });
		        }
	    	}
	    });
    }
}
function delUser(obj,id,name) {
	var currentUser=$("#currentUser").html();
	var version=obj.version;
	//console.log("delUser版本:"+version);
    if(null!=id){
    	if(currentUser==id){
            layer.alert("对不起，您不能执行删除自己的操作！");
        }else{
	        layer.confirm('您确定要删除'+name+'用户吗(用户的相关权限也将删除哦)？', {
	            btn: ['确认','返回'] //按钮
	        }, function(){
                $.ajax({
                    type: "POST",
                    data: {"id":id,"version":version},
                    url: "delUser",
                    success: function (data) {
                        ajaxSucFunc(data,function(){
                            layer.closeAll();
                            //加载load方法
                            load();//自定义
                        });
                    },
                    error: ajaxErrorFunc
                });
	        }, function(){
	            layer.closeAll();
	        });
        }
    }
}
function recoverUser(obj,id) {
	var version=obj.version;
    if(null!=id){
        layer.confirm('您确定要恢复'+name+'用户吗？', {
            btn: ['确认','返回'] //按钮
        }, function(){
            $.ajax({
                type: "POST",
                data: {"id":id,"version":version},
                url: "recoverUser",
                success: function (data) {
                    ajaxSucFunc(data,function(){
                        layer.closeAll();
                        //加载load方法
                        load();//自定义
                    });
                },
                error: ajaxErrorFunc
            });
        }, function(){
            layer.closeAll();
        });
    }
}

function load(obj){
    //重新加载table
    if(obj){
        tableIns.reload({
            where: obj.field
            , page: {
                curr: pageCurr //从当前页码开始
            }
        });
    }else{
        tableIns.reload({});
    }

}

