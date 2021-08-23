/**
 * 权限列表
 */
$(function() {
     initTreeGrid()
    //操作
    layui.use('form', function(){
        var form = layui.form;
        //监听提交
        form.on('submit(permSubmit)', function(data){
            //校验 TODO
            $.ajax({
                type: "POST",
                data: $("#permForm").serialize(),
                url: "/sys/permission/setPerm",
                success: function (data) {
                    ajaxSucFunc(data,function(){
                        layer.closeAll();
                    });
                },
                error: ajaxErrorFunc
            });
            return false;
        });
        form.render();
    });

});
function initTreeGrid(){
    var editObj=null,ptable=null,treeGrid=null,tableId='treeTable',layer=null;
    layui.config({
        base: '/layui/extend/'
    }).extend({
        treeGrid:'treeGrid'
    }).use(['jquery','treeGrid','layer'], function(){
        var $=layui.jquery;
        treeGrid = layui.treeGrid;//很重要
        layer=layui.layer;
        ptable=treeGrid.render({
            id:"permTable"
            ,elem: '#permTable'
            ,idField:'id'
            ,url:'/sys/permission/list'
            ,cellMinWidth: 60
            //,height:'600px'
            ,treeId:'id'//树形id字段名称
            ,treeUpId:'parentId'//树形父id字段名称
            ,treeShowName:'name'//以树形式显示的字段
            ,cols: [[
                 {field:'id',width:50, title: '权限ID'}
                ,{field:'name', edit:'text',width:200, title: '权限名称'}
                //,{field:'parentIds', title: '父类权限IDS'}
                ,{field:'type',width:60,  title: '类型',templet: function(r){
                    return r.type==0?'菜单':'功能';
                }}
                ,{field:'code',width:120, title: '权限CODE'}
                ,{field:'path',width:120, title: '权限路径'}
                ,{field:'seq',width:80, title: '顺序'}
                ,{width:180,title: '操作', align:'center'/*toolbar: '#barDemo'*/
                    ,templet: function(r){
                        var addBtn ='<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="add">编辑</a>';
                        var addChildBtn='<a class="layui-btn layui-btn-xs" lay-event="addChild">添加子节点</a>';
                        var delBtn='<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>';
                        return addBtn+addChildBtn+delBtn;
                    }
                }
            ]]
            ,page:false
        });

        treeGrid.on('tool(permTable)',function (obj) {
            if(obj.event === 'del'){//删除行
                del(obj.data.id,obj.data.name);
            }else if(obj.event==="add"){//添加行
                edit(obj.data.id,0);
            }else if(obj.event==="addChild"){//添加行
                addPerm(obj.data.id,1);
            }
        });
    });

}
function edit(id,opType){
    if(null!=id){
        $("#opType").val(opType);
        $("#id").val(id);
        $.get("/sys/permission/getPerm",{"id":id},function(data) {
            if(null!=data){
                layui.use('form', function(){
                    var form = layui.form;
                    form.val("permFormValue", {
                        "id":id,
                        "parentId":data.parentId,
                        "name":data.name,
                        "code":data.code,
                        "path":data.path,
                        "seq":data.seq,
                        "descpt":data.descpt,
                        "type": data.type == 0?"菜单":"功能"
                    });
                    $("#id").val(id);
                    $("input[name=type][value='0']").attr("checked", data.type == 0 ? true : false);
                    $("input[name=type][value='1']").attr("checked", data.type  == 1 ? true : false);
                    form.render();
                });
                //$('#updatePerm').render('radio');

                openPanel("更新权限")
            }else{
                layer.alert("获取权限数据出错，请您稍后再试");
            }
        });
    }
}
//开通权限
function addPerm(parentId,flag){
    if(null!=parentId){
        //flag[0:开通权限；1：新增子节点权限]
        //type[0:编辑；1：新增]
        if(flag==0){
            $("#opType").val(1);
            $("#parentId").val(0);
        }else{
            //设置父id
            $("#opType").val(1);
            $("#parentId").val(parentId);
        }
        openPanel("添加权限");
    }
}
function openPanel(title){
    layer.open({
        type:1,
        title: title,
        fixed:false,
        resize :false,
        shadeClose: true,
        area: ['500px', '580px'],
        content:$('#updatePerm'),  //页面自定义的div，样式自定义
        end:function(){
            location.reload();
        }
    });
}
function del(id,name){
    // console.log("===删除id："+id);
    if(null!=id){
        layer.confirm('您确定要删除'+name+'权限吗？', {
            btn: ['确认','返回'] //按钮
        }, function(){
            $.ajax({
                type: "POST",
                data: {"id":id},
                url: "/sys/permission/del",
                success: function (data) {
                    ajaxSucFunc(data,function(){
                        layer.closeAll();
                    });
                },
                error: ajaxErrorFunc
            });
        }, function(){
            layer.closeAll();
        });
    }

}

//关闭弹框
function close(){
    layer.closeAll();
}