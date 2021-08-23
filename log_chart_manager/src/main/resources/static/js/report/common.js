var myInterval;
var loading=".";

function getProductColumns(){
    var productColumnArr=[];
    $.ajax({
        async:false,
        url: "../../report/product-column/getAll?isEffective=1",
        contentType: "application/json",
        success: function (data) {
            productColumnArr = data;
        }
    });
    return productColumnArr;
}
function initProductColumnSelect(id,flag){
    var productColumnArr=getProductColumns();
    if(flag){
        $("#"+id).append('<option value="">'+'--全部--'+'</option>');
    }
    $.each(productColumnArr,function(index,item){
        $("#"+id).append('<option value="'+item.columnId+'">'+item.columnName+'</option>');
    });
    return productColumnArr;
}
function getAlbumContentType(){
    var contentTypeArr=[];
    $.ajax({
        async:false,
        url: "../..//report/album_content_type/list",
        contentType: "application/json",
        success: function (data) {
            contentTypeArr = data.rows;
        }
    });
    return contentTypeArr;
}
function initAlbumContentTypeSelect(id,flag){
    var contentTypeArr=getAlbumContentType();
    if(flag){
        $("#"+id).append('<option value="">'+'--全部--'+'</option>');
    }
    $.each(contentTypeArr,function(index,item){
        $("#"+id).append('<option value="'+item.contentType+'">'+item.contentTypeName+'</option>');
    });
    return contentTypeArr;
}
function getAreaCodes(userType){
    if(!userType){
        userType="OTT";
    }
    var areaCodeArr=[];
    $.ajax({
        async:false,
        url: "../../report/t-area-info/getAll?isEffective=1&type="+userType,
        contentType: "application/json",
        success: function (data) {
            areaCodeArr = data;
        }
    });
    return areaCodeArr;
}
function initSxSelect(xmSelect,el,userType){
    if(!el){
        el="areaCodes"
    }
    return xmSelect.render({
        el: '#'+el,
        toolbar: {
            show: true,
            showIcon: true,
        },
        prop: {
            name: 'areaName',
            value: 'areaNo',
        },
        name:"areaCodes",
        filterable: true,
        paging: true,
        pageSize: 10,
        height: '500px',
        template({ item, sels, name, value }){
            return name  + '<span style="position: absolute; right: 10px; color: #8799a3">'+value+'</span>'
        },
        data: getAreaCodes(userType)
    });

}
function initAlbumContentTypeXmSelect(xmSelect,el){
    if(!el){
        el="contentTypes"
    }
    return xmSelect.render({
        el: '#'+el,
        toolbar: {
            show: true,
            showIcon: true,
        },
        prop: {
            name: 'contentTypeName',
            value: 'contentType',
        },
        name:"contentTypes",
        filterable: true,
        paging: true,
        pageSize: 10,
        height: '500px',
        template({ item, sels, name, value }){
            return name  + '<span style="position: absolute; right: 10px; color: #8799a3">'+value+'</span>'
        },
        data: getAlbumContentType()
    });

}
function initSxSelectParentColumnId(xmSelect,el){
    if(!el){
        el="parentColumnIds"
    }
    return xmSelect.render({
        el: '#'+el,
        toolbar: {
            show: true,
            showIcon: true,
        },
        prop: {
            name: 'columnName',
            value: 'columnId',
        },
        name:"parentColumnIds",
        filterable: true,
        paging: true,
        pageSize: 5,
        height: '500px',
        template({ item, sels, name, value }){
            return name  + '<span style="position: absolute; right: 10px; color: #8799a3">'+value+'</span>'
        },
        data: getProductColumns()
    });

}
function downLoadFile(url,fileName,param,noConfirm){
    if(!noConfirm){
        layui.layer.confirm('您确认想要下载['+fileName+']吗？', {
            btn: ['确认','返回'] //按钮
        }, function(){
            var $form = $('<form method="GET"></form>');
            if(fileName){
                var input =$('<input name="fileName" value="'+fileName+'" />');
                $form.append(input);
            }
            $.each(param,function(key,value){
                if(value != '' && value!=null){
                    var input = $('<input type="text"/>');
                    input.attr('name',key);
                    input.attr('value',value);
                    $form.append(input);
                }
            });
            $form.attr('action', url);
            $form.appendTo($('body'));
            $form.hide();
            $form.submit();
            showProgressBar();
            layui.layer.closeAll();
        }, function(){
            layui.layer.closeAll();
        });
    }else{
        var $form = $('<form method="GET"></form>');
        if(fileName){
            var input =$('<input name="fileName" value="'+fileName+'" />');
            $form.append(input);
        }
        $.each(param,function(key,value){
            if(value != '' && value!=null){
                var input = $('<input type="text"/>');
                input.attr('name',key);
                input.attr('value',value);
                $form.append(input);
            }
        });
        $form.attr('action', url);
        $form.appendTo($('body'));
        $form.hide();
        $form.submit();
        showProgressBar();
    }

}
var showProgressBar = function(){
    $("#progressBar").show();
    $("#progressBar b").text(loading);
    if(myInterval == null || myInterval == undefined){
        myInterval =window.setInterval("downloadFileStatus()",1000);
    }
}
var downloadFileStatus = function(){
    $.ajax({
        url: "download/file/status",
        contentType: "application/json",
        async:true,
        success: function (data) {
            if(data.code==0){
                window.clearInterval(myInterval);
                $("#progressBar").hide();
                //$("#progressBar").remove();
                myInterval = null;
            }
            if(loading.length<6) loading=loading+".";
            else loading="."
            $("#progressBar b").text(loading);
        }
    });
}
function initWeekSelect(id){
    w = getWeekOfYear(getLastThursday())
    for(var i=1;i<=53;i++){
        if(w==i){
            $("#"+id).append('<option value="'+i+'" selected>'+i+'</option>');
        }else{
            $("#"+id).append('<option value="'+i+'">'+i+'</option>');
        }
    }

}
function initYearAndWeekSelect(weekId){
    var lastThursday=getLastThursday();
    var w = getWeekOfYear(lastThursday)
    var year = lastThursday.getFullYear();
    $("#"+weekId).val(year+"-"+w)
}
function initYearSelect(id){
    var curr = getLastThursday().getFullYear();
    var y=2020;
    for(var i=0;i<3;i++){
        var s = y+i;
        if(s==curr){
            $("#"+id).append('<option value="'+s+'" selected>'+s+'</option>');
        }else{
            $("#"+id).append('<option value="'+s+'">'+s+'</option>');
        }
    }

}
function getWeekOfYear(date) {
    var d1 = date;
    var d2 = new Date(date.getFullYear(), 0, 1);
    var d = Math.round((d1 - d2) / 86400000);
    return Math.ceil(d / 7);
    // return Math.ceil((d + ((d2.getDay() + 1) - 1)) / 7);
};

function getLastThursday(){//获取上周四，用户计算周年
    var curr = new Date();
    d=curr.getDay();
    curr.setDate(curr.getDate()+4-d)
    curr.setDate(curr.getDate()-7);
    return curr;
}
function getYesterday(){//获取昨天日期
    var curr = new Date();
    return DateUtils.formatterDate(curr.setDate(curr.getDate()-1));
}
/**
 *返回上个月范围值
 * @returns 2021-06 - 2021-07
 */
function getLastMonthRange(){//获取昨天日期
    var date = new Date();
    var c_year = date.getFullYear();
    var last_year = c_year;
    var c_month = date.getMonth()+1;
    var last_month = c_month-1;
    if(c_month == 1){
        last_year = c_year -1;
        last_month = 12;
    }
    return  last_year+"-"+(last_month<10?"0" + last_month:last_month)+" - "+c_year+"-"+(c_month<10?"0" + c_month:c_month);
}

/**
 * 获取上一个月的值
 */
function getLastMonth(){
    var date = new Date();
    var c_year = date.getFullYear();
    var last_year = c_year;
    var c_month = date.getMonth()+1;
    var last_month = c_month-1;
    if(c_month == 1){
        last_year = c_year -1;
        last_month = 12;
    }
    return  last_year+"-"+(last_month<10?"0" + last_month:last_month);
}

/**
 * 获取上周一日期
 * @param value
 * @returns 2021-06-14
 */
function getLastWeekForMonday(){
    var curr =new Date();
    var deltaDay=7+curr.getDay()-1;
    var monday =new Date(curr.getTime()-deltaDay*24*60*60*1000);
    return DateUtils.formatterDate(monday);
}
function getLastWeekForSunday(){
    var curr =new Date();
    var deltaDay=curr.getDay();
    var sunday =new Date(curr.getTime()-deltaDay*24*60*60*1000);
    return DateUtils.formatterDate(sunday);
}
function getWeekOfYear2(value){
    var curr =new Date(value);
    d=curr.getDay();
    curr.setDate(curr.getDate()+4-d)
    return curr.getFullYear()+"-"+getWeekOfYear(curr);
}


/**
 * 对排序参数进行格式化处理
 * @param tabIns
 * @param tableSort
 * @returns {null}
 */
function getOrderByParams(tabIns,tableSort){
    var orderby=null;
    if(tableSort == undefined || tableSort==null){
        var sortPar = tableIns.config.initSort;
        if(Array.isArray(sortPar)){
            tableSort=sortPar;
        }else {
            if (sortPar.type != null) {
                orderby=sortPar.field + ":" + sortPar.type
            }
        }
    }
    if(tableSort){
        for (i = 0, len = tableSort.length; i < len; i++) {
            if (orderby == null) {
                if (tableSort[i].type != null) {
                    orderby = tableSort[i].field + ":" + tableSort[i].type;
                }
            } else {
                if (tableSort[i].type != null) {
                    orderby = orderby+"," + tableSort[i].field + ":" + tableSort[i].type;
                }
            }
        }
    }
    if(orderby != null && tabIns){
        var ar=orderby.split(",");
        var colstemp = tabIns.config.cols[0];
        var sortMsg="排序规则组合:";
        for(var i=0,alen=ar.length;i<alen;i++){
            var kv=ar[i].split(":");
            for (j = 0, len = colstemp.length; j < len; j++) {
                if (kv[0] == colstemp[j].field) {
                    sortMsg=sortMsg.concat("\n", colstemp[j].title,":",kv[1] == "desc" ? "降序" : "升序")
                    break;
                }
            }
        }
        layer.msg(sortMsg);
    }
    return orderby;
}

/**
 * 分页排序查询对于layui table
 */
function queryTablePageByOrderby(tableIns,searchParam,pageCurr,tableSort,options){
    var configParam={};
    if(options){
        configParam=options;
    }

    var queryParams = {};
    if (searchParam != null && searchParam != undefined) {
        queryParams = searchParam.field;
    }
    if(pageCurr){
        configParam["page"]={curr:pageCurr};
    }
    queryParams["orderby"]=getOrderByParams(tableIns,tableSort);
    configParam["where"]=queryParams;
    if(tableSort){
        configParam["initSort"]=tableSort;
    }
    // if (searchParam) {
    //     tableIns.reload({
    //         initSort:tableSort
    //         , where: queryParams
    //         , page: {
    //             curr: pageCurr //从当前页码开始
    //         }
    //     });
    // } else {
    //     tableIns.reload({
    //         initSort:tableSort
    //         , where: queryParams
    //     });
    // }
    tableIns.reload(configParam)
}
function initContentTypeInputVerify(form){
    form.verify({
        verifyContentType: function(value, item){ //value：表单的值、item：表单的DOM对象
            if(value!=null && value!='' && value != undefined){
                if(!new RegExp("^[0-9]+$").test(value)){
                    if("null" != value || "NULL"!=value){
                        return '类型不满足需要数值或NULL，null';
                    }
                }
            }
        }

    });
}

/**
 * 按日期生成数据接口
 * @returns {boolean}
 */
function gengerateData(){
    var $gengerateDataForm = $('<form id="convertDataForm_1" class="layui-form layui-form-pane" style="width: 600px" lay-filter="convertDataFormFilter">' +
        '<div class="layui-form-item"> <label class="layui-form-label">时间</label>' +
        '<div class="layui-input-inline" style="width: 250px;">' +
        '<input name="startDate" id="convertDataStartDate" placeholder="yyyy-MM-dd" autocomplete="off"\n' +
        'class="layui-input" type="text"/></div></div></form>');
    $gengerateDataForm.appendTo($('body'));
    $gengerateDataForm.hide();
    layui.form.render();
    layui.laydate.render({
        elem: '#convertDataStartDate'
        , trigger: 'click'//添加属性
        ,range:true
    });
    layer.open({
        type: 1,
        title: "生成数据界面",
        fixed: false,
        resize: true,
        maxmin:true,
        shadeClose: true,
        area: ['600px', '700px']
        , content: $gengerateDataForm
        , btn: ['确定']
        , yes: function (index_x, layero) {
            console.log($gengerateDataForm.serialize())
            var param=$gengerateDataForm.serialize();
            var loadIndex = layer.load(2, {time: 120*1000});
            $.ajax({
                type: "POST",
                data: param,
                url: "gengerateData",
                success: function (result) {
                    layer.msg('生成成功');
                    layer.close(index_x);
                    layer.close(loadIndex);
                }
            });
        }
        , end: function () {
            $gengerateDataForm.remove();
        }
    });
    return false;
}