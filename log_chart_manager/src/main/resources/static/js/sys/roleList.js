/**
 * 角色列表
 */
var pageCurr;
$(function() {
    layui.use('table', function(){
        var table = layui.table;
        tableIns=table.render({
            elem: '#roleTable'
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
                ,{field:'code', title:'角色编号'}
                ,{field:'roleName', title:'角色名称'}
                ,{field:'descpt', title:'角色描述'}
                ,{fixed:'right', title:'操作', width:140,align:'center',toolbar:'#optBar'}
            ]]
            ,  done: function(res, curr, count){
                pageCurr=curr;
            }
        });
        table.on('tool(roleTableFilter)',function (obj) {
            if(obj.event === 'del'){//删除行
                delRole(obj.data.id);
            }else if(obj.event==="add"){//添加行
                updateRole(obj.data.id);
            }
        });
    });
});

function updateRole(id) {
    $("#roleListLi").removeClass("layui-this");
    $("#setRoleLi").removeClass("layui-this");
    $("#roleListDiv").removeClass("layui-show");
    $("#setRoleDiv").removeClass("layui-show");

    $("#updateRoleLi").addClass("layui-this");
    $("#updateRoleDiv").addClass("layui-show");
    $("#updateRoleLi").css("display","inline-block");
    $("#updateRoleDiv").css("display","inline-block");
    getRole(id);
    //isNaN是数字返回false
    //if(id!=null && !isNaN(id)){
    //    window.location.href="updateRole/"+id+"?callback="+getCallback();
    //}else{
    //    layer.alert("请求参数有误，请您稍后再试");
    //}
}
function delRole(id) {
    if(null!=id){
        layer.confirm('您确定要删除'+name+'角色吗？', {
            btn: ['确认','返回'] //按钮
        }, function(){
            $.ajax({
                type: "POST",
                data: {"id":id},
                url: "delRole",
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

