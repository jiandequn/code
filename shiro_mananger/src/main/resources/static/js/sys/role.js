/**
 * 更新角色
 */
//选中的复选框
var nodeIds = [];
$(function() {
    layui.use(['form' ,'tree','layer'], function(){
        var form = layui.form;
        var layer=layui.layer;
        //监听提交
        getTreeData();
        form.on('submit(roleSubmit)', function(data){
            //校验 TODO
            var array = new Array();
            //获取选中的权限id
            var checkData = nodeTree.getChecked('permId');
            for(var i=0;i<checkData.length;i++){
                     array.push(checkData[i].id)
            }
            //校验是否授权
            var permIds = array.join(",");
            // console.log("permIds"+permIds)
            if(permIds==null || permIds==''){
                layer.alert("请给该角色添加权限菜单！")
                return false;
            }
            $("#permIds").val(permIds);

            $.ajax({
                type: "POST",
                data: $("#roleForm").serialize(),
                url: "/sys/role/addRole",
                success: function (data) {
                    ajaxSucFunc(data,function(){
                        layer.closeAll();
                        load();
                    })
                },
                error: ajaxErrorFunc
            });
            return false;
        });
        form.render();
    });

});
function getTreeData() {
    $.ajax({
        type: "get",
        url: "/sys/permission/treePerms",
        success: function (data) {
            if (data !=null) {
                initTree(data);
            } else {
                layer.alert(data);
            }
        },
        error: function () {
            layer.alert("获取数据错误，请您稍后再试");
        }
    });
}

function initTree(data){
    //layui.use('tree');
    layui.use('tree', function(){
        nodeTree = layui.tree;
        //渲染
        nodeTree.render({
            elem: '#perm'  //绑定元素
            ,id: 'permId' //定义索引
            ,showCheckbox: true  //是否显示复选框
            ,isJump: true //是否允许点击节点时弹出新窗口跳转
            ,data:data
            ,click: function(obj){
                var data = obj.data;  //获取当前点击的节点数据
                layer.msg('状态：'+ obj.state + '<br>节点数据：' + JSON.stringify(data));
            }
        });
    });
}
/**
 * 获取所有子节点的id数组
 * @param obj
 * @returns {Array}
 */
function getChildNode( obj ){
    if(obj!=null){
        if( obj.children.length > 0 ){
            $.each( obj.children, function(k, v){
                //console.log( v.id );
                nodeIds.push( v.id );
                getChildNode( v );
            });
        }
    }
    return nodeIds;
}


/**
 * list转化为tree结构的json数据
 */
function listToTreeJson(data){
    //data不能为null，且是数组
    if(data!=null && (data instanceof Array)){
        //递归转化
        var getJsonTree=function(data,parentId){
            var itemArr=[];
            for(var i=0;i < data.length;i++){
                var node=data[i];
                if(node.pId==parentId && parentId!=null){
                    var newNode={name:node.name,spread:true,id:node.id,pid:node.pId,children:getJsonTree(data,node.id)};
                    itemArr.push(newNode);
                }
            }
            return itemArr;
        }
        // return JSON.stringify(getJsonTree(data,''));
        return getJsonTree(data,0);
    }
    //console.log(JSON.stringify(getJsonTree(data,'')));
}
