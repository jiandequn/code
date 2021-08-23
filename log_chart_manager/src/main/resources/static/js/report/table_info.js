/**
 * 列表
 */
var pageCurr;
$(function() {
    layui.use('table', function(){
        var table = layui.table
            ,form = layui.form;
        tableIns=table.render({
            elem: '#tableInfoList'
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
                ,{field:'tableName', title:'表英文名称'}
                ,{field:'tableComment', title:'表中文名称'}
                ,{field:'isEffective', title:'是否启用',width:95,align:'center',templet:'#effectiveTpl'}
                ,{field: 'createTime', title: '创建时间'},
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
        table.on('tool(tableInfoTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'del'){
                del(data,data.id,data.tableName);
            } else if(obj.event === 'edit'){
                //编辑
                getTableInfoInfo(data,data.id);
            }

        });
        //监听提交
        form.on('submit(tableInfoSubmit)', function(data){
            // TODO 校验
            $.ajax({
                type: "POST",
                data: $("#tableInfoForm").serialize(),
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
    layui.use(['form'], function(){
        form = layui.form ,layer = layui.layer;
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
    $('#tableInfoForm')[0].reset();
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
        content:$('#setTableInfo'),
        end:function(){
        	cleanInfo();
        }
    });
}

function getTableInfoInfo(obj,id) {
	if(obj.isEffective==0){
        layer.alert("该记录已经禁用，不可进行编辑；</br>  如需编辑，请设置为<font style='font-weight:bold;' color='green'>启用</font>状态。");
    }else{
	    //回显数据
	    $.get("get",{"id":id},function(data){
	    	if(isLogin(data)){
		        if(data.code=="1" && data.result != null){
                    var p = data.result;
                    form.val("tableInfoFormFilter", {
                        "tableName": p.tableName
                        ,"tableComment": p.tableComment
                        ,"isEffective": p.isEffective
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
        layer.confirm('您确定要删除'+name+'用户吗？', {
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
function selectTable(){
    layui.use('table', function() {
        var table = layui.table
        table.render({
            elem: '#selectTable2'
            , url: 'getAllTableInDb'
            , method: 'post' //默认：get请求
            , cellMinWidth: 80
            , page: false
           ,parseData: function(res) { //res 即为原始返回的数据
                return {
                    "code": "0", //解析接口状态
                    "msg": "", //解析提示文本
                    "count": res.length, //解析数据长度
                    "data": res //解析数据列表
                }
            }
            , cols: [[
                {type: 'numbers'}
                , {field: 'tableName', title: '表英文名称'}
                , {field: 'tableComment', title: '表中文名称'}
                , {field: 'createTime', title: '创建时间'}
            ]]
        });
        table.on('rowDouble(selectTable2Filter)', function(obj){
            console.log(obj.data) //得到当前行数据
            $("#tableName").val(obj.data.tableName);
            $("#tableComment").val(obj.data.tableComment);
        });
    });
    layer.open({
        type:1,
        title: "选择表列表(双击选择)",
        fixed:false,
        resize :false,
        shadeClose: true,
        offset: 'rt',
        area: ['600px','600px'],
        content:$('#selectTablePanel'),
        end:function(){
        }
    });
}

