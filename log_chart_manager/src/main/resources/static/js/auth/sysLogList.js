/**
 * 日志列表
 */
var pageCurr;
$(function() {
    layui.use('table', function(){
        var table = layui.table
            ,form = layui.form;
        tableIns=table.render({
            elem: '#syslogList'
            ,url:'/sys/log/getList'
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
                ,{field:'id', title:'ID', width:80, unresize: true, sort: true}
                ,{field:'userName', title:'用户名'}
                ,{field:'realName', title:'真实名'}
                ,{field:'opType', title: 'IP',align:'center', title: '操作类型',templet: function(d){
                    return $("#opType").find("option[value='"+ d.opType+"']").text();
                }}
                ,{field:'broswer', title:'浏览器'}
                ,{field:'content', title: '日志内容'}
                ,{field:'level', title: '日志级别',templet: function(d){
                    return $("#level").find("option[value='"+ d.opType+"']").text();
                }}
                ,{field:'ip', title: 'IP',align:'center', title: '来源IP'}
                ,{field:'reqUri', title: 'IP',align:'center', title: '请求URI'}
                ,{field:'opTime', title:'操作时间',width:120,align:'center'}
            ]]
            ,  done: function(res, curr, count){
                pageCurr=curr;
            }
        });

    });
    //搜索框
    layui.use(['form','laydate'], function(){
        var form = layui.form ,layer = layui.layer
            ,laydate = layui.laydate;
        //日期
        laydate.render({
            elem: '#startTime'
        });
        laydate.render({
            elem: '#endTime'
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
function load(obj){
    //重新加载table
    tableIns.reload({
        where: obj.field
        , page: {
            curr: pageCurr //从当前页码开始
        }
    });
}

