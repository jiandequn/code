/**
 * 列表
 */
var pageCurr;
$(function () {

    layui.use('table', function () {
        var table = layui.table
            , form = layui.form;
        tableIns = table.render({
            elem: '#tableList'
            , toolbar: '#tableListToolbar'
            , defaultToolbar: []
            , url: 'list'
            , method: 'post' //默认：get请求
            , cellMinWidth: 80
            , page: true,
            limit:30,
            request: {
                pageName: 'page' //页码的参数名称，默认：page
                , limitName: 'limit' //每页数据量的参数名，默认：limit
            }, response: {
                statusName: 'status' //数据状态的字段名称，默认：code
                , statusCode: 200 //成功的状态码，默认：0
                , countName: 'total' //数据总数的字段名称，默认：count
                , dataName: 'rows' //数据列表的字段名称，默认：data
            }
            , cols: [[
                {checkbox: true}
                ,{type: 'numbers'}
                , {field: 'id', title: 'ID', width: 80, unresize: true, sort: true}
                , {field: 'name', title: '导出文件名称'}
                , {field: 'fileFormat', title: '文件类型'}
                , {field: 'isEffective', title: '是否启用', width: 95, align: 'center', templet: '#effectiveTpl'}
                , {field: 'createTime', title: '创建时间'}
                , {fixed: 'right', title: '操作', width: 220, align: 'center', toolbar: '#optBar'}
            ]]
            , done: function (res, curr, count) {
                pageCurr = curr;
            }
        });

        //监听在职操作
        form.on('switch(isEffectiveTpl)', function (obj) {
            var data = obj.data;
            setIsEffective(obj, this.value, this.name, obj.elem.checked);
        });
        //监听工具条
        table.on('tool(tableFilter)', function (obj) {
            var data = obj.data;
            if (obj.event === 'del') {
                del(data, data.id, data.name);
            } else if (obj.event === 'edit') {
                //编辑
                getExportTableInfo(data, data.id);
            }else if(obj.event =='download'){
                customDownload([data])
               // downLoadFile("download/file/"+data.id,data.name,{});
            }

        });

    });
    //搜索框
    layui.use(['form'], function () {
        form = layui.form , layer = layui.layer;
        //TODO 数据校验
        //监听搜索框
        form.on('submit(searchSubmit)', function (data) {
            //重新加载table
            load(data);
            return false;
        });
    });
    initFieldTable();
    initTableInfoSelect();

});
//设置用户是否离职
function setIsEffective(obj, id, name, checked) {
    var flag = checked ? "启动" : "禁用";
    //是否离职
    layer.confirm('您确定要把：' + name + '设置为' + flag + '状态吗？', {
        btn: ['确认', '返回'] //按钮
    }, function () {
        $.ajax({
            type: "POST",
            data: {"id": id, "isEffective": checked ? 1 : 0},
            url: "setIsEffective",
            success: function (data) {
                ajaxSucFunc(data, function () {
                    layer.closeAll();
                    load();
                });
            },
            error: function (data) {
                ajaxErrorFunc(data);
                load();
            }
        });
    }, function () {
        layer.closeAll();
        //加载load方法
        load();
    });
}

function cleanInfo() {
    $('#addForm')[0].reset();
    form.render();
    table.reload('fieldTableId', {
        data: []
    });
}

//开通用户
function add() {
    openInfoPanel(null, "新增");
    initTableInfoSelect();
    layui.form.render('checkbox');
}
function openInfoPanel(id, title) {
    if (id == null || id == "") {
        $("#id").val("");
    }
    layer.open({
        type: 1,
        title: title,
        fixed: false,
        resize: false,
        shadeClose: true,
        area: ['700px', '800px'],
        content: $('#setExportTableInfo'),
        btn: ['确认', '取消'],
        yes: function (index, layero) {
            if(saveInfo()){
                layer.close(index);
            }
        }
        , btn2: function (index, layero) {
            cleanInfo();
            layer.close(index);
        }
        , end: function () {
            cleanInfo();
        }
    });
}

function getExportTableInfo(obj, id) {
    if (obj.isEffective == 0) {
        layer.alert("该记录已经禁用，不可进行编辑；</br>  如需编辑，请设置为<font style='font-weight:bold;' color='green'>启用</font>状态。");
    } else {
        //回显数据
        $.get("get", {"id": id}, function (data) {
            if (isLogin(data)) {
                if (data.code == "1" && data.result != null) {
                    var p = data.result;
                    form.val("addFormFilter", {
                        "tableId": p.tableId
                        , "name": p.name
                        , "fileFormat": p.fileFormat
                        , "isEffective": p.isEffective
                        , "querySql": p.querySql
                        , "id": p.id
                    });
                    $.get("../../report/table-column-info/select", {"exportTableId": id}, function (data) {
                        table.reload('fieldTableId', {
                            data: data
                        });
                    });
                    openInfoPanel(id, "修改");
                    //重新渲染下form表单中的复选框 否则复选框选中效果无效
                    layui.form.render();
                    //layui.form.render('checkbox');
                } else {
                    //弹出错误提示
                    layer.alert(data.msg, function () {
                        layer.closeAll();
                    });
                }
            }
        });
    }
}
function del(obj, id, name) {
    if (null != id) {
        layer.confirm('您确定要删除' + name + '用户吗？', {
            btn: ['确认', '返回'] //按钮
        }, function () {
            $.ajax({
                type: "POST",
                data: {"id": id},
                url: "del",
                success: function (data) {
                    ajaxSucFunc(data, function () {
                        layer.closeAll();
                        //加载load方法
                        load();//自定义
                    });
                },
                error: ajaxErrorFunc
            });
        }, function () {
            layer.closeAll();
        });
    }
}

function load(obj) {
    //重新加载table
    if (obj) {
        tableIns.reload({
            where: obj.field
            , page: {
                curr: pageCurr //从当前页码开始
            }
        });
    } else {
        tableIns.reload({});
    }

}
function initFieldTable() {
    layui.use('table', function () {
        table = layui.table
        tableIns2 = table.init('fieldTableFilter', {
            elem: '#fieldTableList'
            , id: 'fieldTableId'
            , toolbar: '#fieldTableToolbar'
            , defaultToolbar: []
            , cellMinWidth: 80
            , page: false
            ,limit:100
            , cols: [[
                {type: 'numbers'}
                , {field: 'columnId', title: '字段ID'}
                , {field: 'columnName', title: '字段英文名称', edit: true}
                , {field: 'columnComment', title: '导出字段名称', edit: true}
                , {field: 'seq', title: '顺序',type: 'numbers', edit: true}
                , {fixed: 'right', title: '操作', width: 80, align: 'center', toolbar: '#opt2Bar'}
            ]]
        });
        table.on('tool(fieldTableFilter)', function (obj) {
            if (obj.event === 'del') {
                layer.confirm('您确定要删除' + obj.data.columnName + '用户吗？', {
                    btn: ['确认', '返回'] //按钮
                }, function (index) {
                    obj.del();
                    layer.close(index);
                }, function (index) {
                    layer.close(index);
                });
            }

        });
        table.on('toolbar(fieldTableFilter)', function (obj) {
            if (obj.event == 'add') {
                var oldData = table.cache['fieldTableId'];
                oldData.push({columnId: "", columnName: "", columnComment: "", seq: "0"});
                table.reload('fieldTableId', {
                    data: oldData
                });
            } else {
                console.info('可选');
                openSelectFieldPanel()
                table.reload('fieldTableId', {});
            }

            return false;
        });
        table.init('selectTableFieldFilter', {
            elem: '#selectTableField'
            , id: 'selectTableFieldId'
            , cellMinWidth: 100
            , page: false
            ,limit:100
            , cols: [[
                {checkbox: true}
                ,{type: 'numbers'}
                ,{field: 'columnName', title: '字段英文名称', edit: true}
                ,{field: 'columnComment', title: '导出字段名称', edit: true}
            ]]
        });
        table.on('rowDouble(selectTableFieldFilter)', function(obj){
            console.log(obj.data) //得到当前行数据
            $("#tableName").val(obj.data.tableName); //插入
            $("#tableComment").val(obj.data.tableComment);
            var oldData = table.cache['selectTableFieldId'];
            oldData.push(obj.data);
            table.reload('fieldTableId', {
                data: oldData
            });
        });
    });
}
function initTableInfoSelect() {
    $.ajax({
        async: true,
        url: "../../report/table-info/select?isEffective=1",
        contentType: "application/json",
        success: function (data) {
            $("#tableId").empty();
            $("#tableId").append('<option value="">' + '--全部--' + '</option>');
            $.each(data, function (index, item) {
                $("#tableId").append('<option value="' + item.id + '">' + item.tableName + '<' + item.tableComment + '></option>');
            });
            form.render('select');
        }
    });
}
function saveInfo() {
    var paramArray = $('#addForm').serializeArray();
    var jsonObj={};
    $(paramArray).each(function(){
        jsonObj[this.name]=this.value;
    });
    if(jsonObj['tableId'] =='' || jsonObj['tableId'] == null){
        layer.msg("请选择表")
        return false;
    }
    if(jsonObj['name']=='' || jsonObj['name']==null){
        layer.msg("请填写名称");
        return false;
    }
    var columns = table.cache['fieldTableId'];
    if(columns == null ||columns.length==0){
        layer.msg("请填写字段")
        return false;
    }
    var columnInfos=[];
    $.each(table.cache['fieldTableId'],function(index,item){
        columnInfos.push({columnId:item.columnId,columnName:item.columnName,columnComment:item.columnComment,seq:item.seq})
    })
    jsonObj['columnInfos']=JSON.stringify(columnInfos);
    var flag = false;
    $.ajax({
        type: "POST",
        data:jsonObj,
        url: "save",
        async:false,
        success: function (data) {
            ajaxSucFunc(data, function () {
                layer.closeAll();
                cleanInfo();
                load();
                flag=true;
            });
        },
        error: ajaxErrorFunc
    });
    return flag;
}
function openSelectFieldPanel(){
    var tableId=$("#tableId").find("option:selected").val();
    if(tableId =='' || tableId == null){
        layer.msg('请选择表');
        $('#tableId').focus();
        return;
    }
    $.ajax({
        type: "post",
        data: {tableId:tableId},
        url: "../../report/table-column-info/getSelectTableField",
        success: function (data) {
            table.reload('selectTableFieldId', {
                data: data
            });
        },
        error: ajaxErrorFunc
    });

    layer.open({
        type:1,
        title: "选择表列表(双击选择)",
        fixed:false,
        resize :false,
        shadeClose: true,
        offset: 'rt',
        area: ['600px','600px'],
        content:$('#selectTableFieldPanel'),
        btn: ['确认', '取消'],
        yes: function (index, layero) {
            var checkStatus = table.checkStatus('selectTableFieldId'); //idTest 即为基础参数 id 对应的值
            console.log(checkStatus.data) //获取选中行的数据
            console.log(checkStatus.data.length) //获取选中行数量，可作为是否有选中行的条件
            console.log(checkStatus.isAll ) //表格是否全选
            var oldData = table.cache['fieldTableId'];
            table.reload('fieldTableId', {
                data: oldData.concat(checkStatus.data)
            });
            layer.close(index);
        },btn2: function (index, layero) {
            layer.close(index);
        },
        end:function(){
            table.reload('selectTableFieldId', {
                data: []
            });
            layer.close();
        }
    });
}
function customDownload(data,iszip){
    var names = "";
    var ids=[];
    $.each(data,function(index,item){
        names += item.name;
        ids.push(item.id)
        if(index != data.length-1){
            names+=",";
        }
    });
    layui.layer.confirm('您确认想要下载第[<font color="red">'+names+'</font>]的记录吗？', {
        btn: ['默认下载','excel下载','csv下载','返回']
        ,btn1:function(index, layero){
            if(iszip){
                downLoadFile("download/file/zip/"+ids,names,{},true);
            }else{
                $.each(data,function(index,item){
                    downLoadFile("download/file/"+item.id,item.name,{},true);
                });
            }
            layui.layer.closeAll();
        }
        ,btn2:function(index, layero){
            var downloadType="excel"
            if(iszip){
                downLoadFile("download/file/zip/"+ids,names,{downloadType:downloadType},true);
            }else{
                $.each(data,function(index,item){
                    downLoadFile("download/file/"+item.id,item.name,{downloadType:downloadType},true);
                });
            }
            layui.layer.closeAll();
        }
        ,btn3:function(){
            var downloadType="csv"
            if(iszip){
                downLoadFile("download/file/zip/"+ids,names,{downloadType:downloadType},true);
            }else{
                $.each(data,function(index,item){
                    downLoadFile("download/file/"+item.id,item.name,{downloadType:downloadType},true);
                });
            }
            layui.layer.closeAll();
        }
        ,btn4:function(){
            layui.layer.closeAll();
        }
    });
}
function batchDownlad(iszip){
    var checkStatus = table.checkStatus('tableList'); //idTest 即为基础参数 id 对应的值
    if(checkStatus == null || checkStatus.data.length<=0){
        layer.msg("请选择需要下载的行")
        return;
    }
    // var names = "";
    // var ids=[];
    // $.each(checkStatus.data,function(index,item){
    //     names += item.name;
    //     ids.push(item.id)
    //     if(index != checkStatus.data.length-1){
    //         names+=",";
    //     }
    // });
    customDownload(checkStatus.data,iszip)
    // layui.layer.confirm('您确认想要下载第[<font color="red">'+names+'</font>]的记录吗？', {
    //     btn: ['默认下载','excel下载','csv下载','返回']
    // }, function(){
    //     if(iszip){
    //         downLoadFile("download/file/zip/"+ids,names,{},true);
    //     }else{
    //         $.each(checkStatus.data,function(index,item){
    //             downLoadFile("download/file/"+item.id,item.name,{},true);
    //         });
    //     }
    //     layui.layer.closeAll();
    // }, function(){
    //     var downloadType="excel"
    //     if(iszip){
    //         downLoadFile("download/file/zip/"+ids,names,{downloadType:downloadType},true);
    //     }else{
    //         $.each(checkStatus.data,function(index,item){
    //             downLoadFile("download/file/"+item.id,item.name,{downloadType:downloadType},true);
    //         });
    //     }
    //     layui.layer.closeAll();
    // }, function(){
    //     var downloadType="csv"
    //     if(iszip){
    //         downLoadFile("download/file/zip/"+ids,names,{downloadType:downloadType},true);
    //     }else{
    //         $.each(checkStatus.data,function(index,item){
    //             downLoadFile("download/file/"+item.id,item.name,{downloadType:downloadType},true);
    //         });
    //     }
    //     layui.layer.closeAll();
    // }, function(){
    //     layui.layer.closeAll();
    // });


}
