/**
 * 用户列表
 */
var pageCurr;
$(function() {
    layui.use('table', function(){
        var table = layui.table
            ,form = layui.form;
        tableIns=table.render({
            elem: '#productColumnList'
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
                ,{field:'columnId', title:'专区ID'}
                ,{field:'columnName', title:'专区名称'}
                ,{field:'userType', title:'用户类型'}
                ,{field:'startDate', title: '启用日期',}
                ,{field:'endDate', title: '终止日期'}
                ,{field:'isEffective', title:'是否启用',width:95,align:'center',templet:'#effectiveTpl'}
                ,{field:'seq', title:'顺序',width:95,align:'center',sort:true}
                ,{fixed:'right', title:'操作', width:220,align:'center', toolbar:'#optBar'}
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
        form.on('switch(isEffectiveTpl)', function(obj){
            //console.log(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
        	var data = obj.data;
            setIsEffective(obj,this.value,this.name,obj.elem.checked);
        });
        //监听工具条
        table.on('tool(productColumnTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'del'){
                del(data,data.id,data.columnName);
            } else if(obj.event === 'edit'){
                //编辑
                getProductColumnInfo(data,data.id);
            }

        });
        //监听提交
        form.on('submit(productColumnSubmit)', function(data){
            // TODO 校验
            $.ajax({
                type: "POST",
                data: $("#productColumnForm").serialize(),
                url: "save",
                success: function (data) {
                    ajaxSucFunc(data, function () {
                        layer.closeAll();
                        cleanInfo();
                        load();
                    });
                },
                error: ajaxErrorFunc
            });
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
function setIsEffective(obj,id,name,checked){
    var flag=checked ? "启动":"禁用";
    //是否离职
    layer.confirm('您确定要把：'+name+'设置为'+flag+'状态吗？', {
        btn: ['确认','返回'] //按钮
    }, function(){
        $.ajax({
            type: "POST",
            data: {"id":id,"isEffective":checked?1:0},
            url: "setIsEffective",
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

function cleanInfo(){
    $('#productColumnForm')[0].reset();
    form.render();
}

//开通用户
function add(){
    openInfoPanel(null,"新增");
    layui.form.render('checkbox');
}
function openInfoPanel(id,title){
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
        content:$('#setProductColumn'),
        end:function(){
        	cleanInfo();
        }
    });
}

function getProductColumnInfo(obj,id) {
	if(!obj.effective){
        layer.alert("该记录已经禁用，不可进行编辑；</br>  如需编辑，请设置为<font style='font-weight:bold;' color='green'>启用</font>状态。");
    }else{
	    //回显数据
	    $.get("get",{"id":id},function(data){
	    	if(isLogin(data)){
		        if(data.code=="1" && data.result != null){
                    var p = data.result;
                    form.val("productColumnFormFilter", {
                        "columnId": p.columnId
                        ,"columnName": p.columnName
                        ,"userType": p.userType
                        ,"startDate": p.startDate
                        ,"endDate": p.endDate
                        ,"isEffective": p.isEffective
                        ,"seq": p.seq
                        ,"id":p.id
                    });
		            openInfoPanel(id,"修改");
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
function del(obj,id,name) {
    if(null!=id){
        layer.confirm('您确定要删除'+name+'专区吗？', {
            btn: ['确认','返回'] //按钮
        }, function(){
            $.ajax({
                type: "POST",
                data: {"id":id},
                url: "del",
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

