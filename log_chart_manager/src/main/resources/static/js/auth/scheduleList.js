/**
 * 用户列表
 */
var tableIns;
var pageCurr;
$(function() {
    layui.use('table', function(){
        var table = layui.table
            ,form = layui.form;

        tableIns=table.render({
            elem: '#scheduleList'
            ,url:'/quartz/getList'
        	,method: 'post' //默认：get请求
            ,cellMinWidth: 80
            ,page: true,
            request: {
                pageName: 'page' //页码的参数名称，默认：page
                ,limitName: 'limit' //每页数据量的参数名，默认：limit
            },response:{
                statusName: 'code' //数据状态的字段名称，默认：code
                ,statusCode: 200 //成功的状态码，默认：0
                ,countName: 'totals' //数据总数的字段名称，默认：count
                ,dataName: 'list' //数据列表的字段名称，默认：data
            }
            ,cols: [[
                {type:'numbers'}
                ,{field:'jobId', title:'ID', width:80, unresize: true, sort: true}
                ,{field:'jobGroup', title:'分组名称'}
                ,{field:'jobName', title:'任务名称',templet:'#jobNameTpl'}
                ,{field:'cronExpression', title: '表达式',}
                ,{field:'beanClass', title: '类路径', }
                ,{field:'methodName', title: '方法名称', }
                ,{field:'jobStatus', title:'任务状态',width:95,align:'center',templet:'#jobStatusTpl'}
                ,{field:'createTime', title: '创建时间',align:'center'}
                ,{field:'updateTime', title: '修改时间',align:'center'}

                //,{field:'isJob', title:'是否在职',width:95,align:'center',templet:'#jobTpl'}
                ,{fixed:'right', title:'操作', width:180,align:'center', toolbar:'#optBar'}
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
                Layui_SetDataTableRowColor('scheduleList', 0, '#2c08b1');
            }
        });

        //监听在职操作
        form.on('switch(isJobStatusTpl)', function(obj){
            //console.log(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
        	var data = obj.data;
            setJobStatus(obj,this.value,this.name,obj.elem.checked);
        });
        //监听工具条
        table.on('tool(jobTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'del'){
                delSchduleJob(data);
            } else if(obj.event === 'edit'){
                //编辑
                getSchduleJob(data);
            }else if(obj.event == "test"){
                testSchduleJob(data);
            }
        });
        //监听提交
        form.on('submit(jobSubmit)', function(data){
            // TODO 校验
            formSubmit(data);
            return false;
        });

    });
    //搜索框
    layui.use(['form','laydate'], function(){
        var form = layui.form ,layer = layui.layer
            ,laydate = layui.laydate;
        //日期
        laydate.render({
            elem: '#insertTimeStart'
        });
        laydate.render({
            elem: '#insertTimeEnd'
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
function Layui_SetDataTableRowColor(DivId,RowIndex, Color) {
    try
    {
        var div = document.getElementById(DivId);
        if(div != null) //找到对象了
        {
            var table_main = div.getElementsByClassName('layui-table-main');   //通过class获取table_main
            if (table_main != null && table_main.length > 0)
            {
                var table = table_main[0].getElementsByClassName('layui-table');   //通过class获取table
                if (table != null && table.length > 0) {
                    var trs = table[0].querySelectorAll("tr");
                    if (trs != null && trs.length > 0) {
                        trs[RowIndex].style.color = Color;//字体颜色
                        trs[RowIndex].style.backgroundColor= Color;//背景颜色
                    }
                }
            }

        }
    }
    catch(e)
    {
        console.log(e.message);
    }
}
function getSchduleJob(data){
    openJob("修改任务");
    $("#jobForm #jobId").val(data.jobId);
    $("#jobForm #jobGroup").val(data.jobGroup);
    $("#jobForm #jobName").val(data.jobName);
    $("#jobForm #cronExpression").val(data.cronExpression);
    $("#jobForm #methodName").val(data.methodName);
    $("#jobForm #beanClass").val(data.beanClass);

}
function testSchduleJob(obj){
    layer.confirm('您确定要测试：['+obj.jobName+']的任务吗？', {
        btn: ['确认','返回'] //按钮
    }, function(){
        $.post("/quartz/test",{"jobId":obj.jobId},function(data){
            if(isLogin(data)){
                if(data=="1"){
                    //回调弹框
                    layer.alert("操作成功！",function(){
                        layer.closeAll();
                        //加载load方法
                        tableIns.reload({});
                    });
                }else{
                    layer.alert(data.msg,function(){
                        layer.closeAll();
                        //加载load方法
                        tableIns.reload({});
                    });
                }
            }
        });
    }, function(){
        layer.closeAll();
        //加载load方法
        tableIns.reload({});
    });
}
//设置用户是否离职
function setJobStatus(obj,value,name,checked){
    var isJob=checked ? 1 : 0;
    var userIsJob=checked ? "启动":"禁用";
    //是否离职
    layer.confirm('您确定要把用户：'+name+'设置为'+userIsJob+'状态吗？', {
        btn: ['确认','返回'] //按钮
    }, function(){
        $.post("/quartz/updateJobStatus",{"jobId":value,"jobStatus":isJob},function(data){
        	if(isLogin(data)){
	            if(data=="1"){
	                //回调弹框
	                layer.alert("操作成功！",function(){
	                    layer.closeAll();
	                  //加载load方法
                        tableIns.reload({});
	                });
	            }else{
	            	layer.alert("修改失败",function(){
                    	layer.closeAll();
                    	//加载load方法
                        tableIns.reload({});;//自定义
                    });
	            }
        	}
        });
    }, function(){
        layer.closeAll();
        //加载load方法
        tableIns.reload({});
    });
}
//提交表单
function formSubmit(obj){
	var currentUser=$("#currentUser").html();
    submitAjax(obj,currentUser);
}
function submitAjax(obj,currentUser){
    $.ajax({
        type: "POST",
        data: $("#jobForm").serialize(),
        url: "/quartz/save",
        success: function (data) {
            if(isLogin(data)){
                if (data.code == "1") {
                    layer.alert(data.msg,function(){
                            layer.closeAll();
                            cleanJob();
                            tableIns.reload({});
                    });
                } else {
                	layer.alert(data.msg);
                }
            }
        },
        error: function () {
            layer.alert("操作请求错误，请您稍后再试",function(){
                layer.closeAll();
                //加载load方法
                tableIns.reload({});//自定义
            });
        }
    });
}

function cleanJob(){
    document.getElementById("jobForm").reset();
//　　$('jobForm')[0].reset();
    $("#jobForm #jobId").val('');
    layui.form.render();
}
//开通用户
function addJob(){
    openJob("新增定时任务");
}
function openJob(title){
    cleanJob();
    layer.open({
        type:1,
        title: title,
        fixed:false,
        resize :false,
        shadeClose: true,
        area:'auto',
        //area: ['650px'],
        content:$('#setJob'),
        end:function(){
        	cleanJob();
        }
    });
}
function delSchduleJob(obj) {
    if(0!=obj.jobStatus){
        layer.alert("对不起，您不能执行删除自己的操作！");
    }else{
        layer.confirm('您确定要删除['+obj.jobName+']吗？', {
            btn: ['确认','返回'] //按钮
        }, function(){
            $.post("/quartz/delete",{"jobId":obj.jobId},function(data){
                if(data=="1"){
                    //回调弹框
                    layer.alert("删除成功！",function(){
                        layer.closeAll();
                        //加载load方法
                        tableIns.reload({});;//自定义
                    });
                }else{
                    layer.alert("删除失败",function(){
                        layer.closeAll();
                        //加载load方法
                        tableIns.reload({});//自定义
                    });
                }
            });
        }, function(){
            layer.closeAll();
        });
    }
}

function load(obj){
    if(obj){
        tableIns.reload({
            where: obj.field
            , page: {
                curr: 1 //从当前页码开始
            }
        });
    } else{
        tableIns.reload({});
    }
    //重新加载table

}

function openCron(){
    layer.open({
        type:1,
        title: "",
        fixed:false,
        resize :true,
        scrollbar:true,
        shadeClose: true,
        //shade: 0,
        area:['800px', '600px'],
        offset: 'r',
        //maxmin: true,
        content:$('#showCron'),
        end:function(){

        }
    });
}

