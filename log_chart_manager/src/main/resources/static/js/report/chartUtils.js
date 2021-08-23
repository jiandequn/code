var PpfunsConfig = {
    chartsTypeArr: [],
    chartType:'xy',
    chartsPlotOptionsEnable: {column: true, spline: true,bar:true,line:true,area:true},
    title: null,            //图表标题
    url: null,           //请求路径
    elm: 'container',  //统计图表ID
    requestParams: {}, //请求参数
    styleBtn: null,  //统计字段样式ID
    styleBtnAuto: null,
    countFields: []  //统计字段

}

function PpfunsChart(options) {
    var obj = this;
    obj.config = JSON.parse(JSON.stringify(PpfunsConfig));
    if (options) {
        for (var k in options) {
            obj.config[k] = options[k];
        }
    }
    if (obj.config.countFields.length == 0) {
        obj.config.chartsTypeArr.forEach(function (item, index, input) {
            obj.config.countFields.push(item.name);
        });
    }
    if (this.config.styleBtn) {
        $("#" + obj.config.styleBtn).click(function () {
            var countFields = obj.config.countFields;
            if (obj.config.styleBtnAuto != null) {
                countFields = obj.config.styleBtnAuto();//返回的数据countFields应该为数组[]
            }
            obj.openXAixsStypeStylePanel(countFields);
            return false;
        })
    }
    obj.config["tmpChartsType"] = {}


}

PpfunsChart.prototype.openXAixsStypeStylePanel = function (countFields) {
    var obj = this;
    var $formChart = $('<form id="formChartXAixsStype" class="layui-form layui-form-pane" style="width: 600px" lay-filter="formChartXAixsStypeFilter"></form>');
    var temp2 = '<div class="layui-form-item" style="width: 600px" pane><label class="layui-form-label" style="width: 120px">是否显示数据</label>' +
        '<div class="layui-input-block">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' +
        '<input type="checkbox" name="like[column]" title="柱形" ' + (obj.config.chartsPlotOptionsEnable.column ? 'checked' : '') +
        ' /><input type="checkbox" name="like[spline]" title="曲线" ' + (obj.config.chartsPlotOptionsEnable.spline ? 'checked' : '') +
        ' /><input type="checkbox" name="like[line]" title="直线" ' + (obj.config.chartsPlotOptionsEnable.line ? 'checked' : '') +
        ' /><input type="checkbox" name="like[area]" title="区域" ' + (obj.config.chartsPlotOptionsEnable.area ? 'checked' : '') +
        ' /></div></div>';
    $formChart.append(temp2);
    $formChart.append('<br>');
    // 初始化表单元素
    obj.config.chartsTypeArr.forEach(function (item, index, input) {
        var idx = 0;
        var isChecked="";
        for (idx in countFields) {
            if(countFields[idx]==item.name){
                isChecked="checked";break;
            }
        }
        var itemlayui = $('<div class="layui-form-item" style="width: 600px" pane></div>');
        var labelDiv = $('<label class="layui-form-label" style="width: 120px">'+ item.title+'</label>');
        var inputBlock = $('<div class="layui-input-block">&nbsp;&nbsp;<input type="checkbox" name="'+item.name+'_checkbox" title="显示" lay-filter="checkboxformChartFilter" '+isChecked+' /></div>');
        inputBlock.append($('<input type="radio" name="' + item.name + '" value="column" title="柱形" '+(item.value=='column'?'checked':'')+' lay-filter="radioformChartFilter" '+(isChecked==""?'disabled':'')+'/>'));
        inputBlock.append($('<input type="radio" name="' + item.name + '" value="spline" title="曲线" '+(item.value=='spline'?'checked':'')+' lay-filter="radioformChartFilter" '+(isChecked==""?'disabled':'')+'/>'));
        inputBlock.append($('<input type="radio" name="' + item.name + '" value="line" title="直线" '+(item.value=='line'?'checked':'')+' lay-filter="radioformChartFilter" '+(isChecked==""?'disabled':'')+'/>'));
        inputBlock.append($('<input type="radio" name="' + item.name + '" value="area" title="区域" '+(item.value=='area'?'checked':'')+' lay-filter="radioformChartFilter" '+(isChecked==""?'disabled':'')+'/>'));
        itemlayui.append(labelDiv);
        itemlayui.append(inputBlock);
        $formChart.append(itemlayui);
    });
    $formChart.appendTo($('body'));
    $formChart.hide();
    layui.form.render();
    layui.form.on('radio(radioformChartFilter)', function (data) {
        console.log(data.elem); //得到radio原始DOM对象
        obj.config.tmpChartsType[$(data.elem).attr("name")] = data.value;
        console.log(data.value); //被点击的radio的value值
    });
    layui.form.on('checkbox(checkboxformChartFilter)', function(data){
        var namestr=data.elem.name.replace("_checkbox","");
        if(data.elem.checked){
            $('#formChartXAixsStype').find("input[name='"+namestr+"']").removeAttr("disabled");
        }else{
            $('#formChartXAixsStype').find("input[name='"+namestr+"']").attr("disabled","disabled");
        }
        layui.form.render();
    });
    layer.open({
        type: 1,
        title: "数据样式选择",
        fixed: false,
        resize: true,
        maxmin:true,
        shadeClose: true,
        area: ['600px', '700px']
        , content: $formChart
        , btn: ['确定']
        , yes: function (index_x, layero) {
            console.log($formChart.serializeArray());
            var tmpOptions = {column: false, spline: false}
            obj.config.countFields=[];
            $formChart.serializeArray().forEach(function (item, index, input) {
                if (item.name == 'like[spline]') {
                    tmpOptions["spline"] = true;
                } else if (item.name == 'like[column]') {
                    tmpOptions["column"] = true;
                }else if(item.name.indexOf('_checkbox')>0){
                    var namestr=item.name.replace("_checkbox","");
                    obj.config.countFields.push(namestr)
                    console.log(namestr)
                }
                else if (obj.config.tmpChartsType[item.name]) {
                    obj.config.chartsTypeArr.forEach(function (item1, index1, input1) {
                        if (item1.name == item.name) {
                            item1["value"] = obj.config.tmpChartsType[item.name]
                            return false;
                        }
                    });
                }
            });
            obj.config.chartsPlotOptionsEnable["column"] = tmpOptions.column
            obj.config.chartsPlotOptionsEnable["spline"] = tmpOptions.spline
            if(obj.config.countFields.length==0){
                layer.msg("必须至少选择一个数据进行展示");
                return;
            }
            layer.close(index_x); //如果设定了yes回调，需进行手工关闭
            obj.render();
        }
        , end: function () {
            $formChart.remove();
        }
    });
}
PpfunsChart.prototype.render = function (options) {
    var obj = this;
    if (options) {
        for (var k in options) {
            obj.config[k] = options[k];
        }
    }
    var fieldTypeList = [];
    if (obj.config.countFields) {
        obj.config.countFields.forEach(function (v) {
            obj.config.chartsTypeArr.forEach(function (item) {
                if (item.name == v) {
                    fieldTypeList.push(item.value);
                    return false;
                }
            });
        });
        if(obj.config.requestParams["countFields"]== undefined|| obj.config.requestParams["countFields"]== null || obj.config.requestParams["countFields"]==''){
            obj.config.requestParams["countFields"]=obj.config.countFields.toString()
        }
    }
    obj.config.requestParams["countFieldTypes"] = fieldTypeList.toString();
    obj.config.requestParams["countFields"] = obj.config.countFields.toString();
    $.ajax({
        type: "GET",
        data: obj.config.requestParams,
        url: obj.config.url,
        success: function (result) {
            obj.custom(result);
        }
    });
}
PpfunsChart.prototype.custom = function (result) {
    if(this.config.chartType =='xy'){
        if(! result.series){
            $("#"+this.config.elm).empty();
            $("#"+this.config.elm).append("无数据可用");
            return;
        }
        Highcharts.chart(this.config.elm, {
            chart: {
                zoomType: 'xy'
            },
            title: {
                text: this.config.title
            },
            subtitle: {
                text: '数据来源: 聚视互娱日志采集'
            },
            xAxis: [{
                categories: result.categories,
                crosshair: true
            }],
            plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0,
                    dataLabels: {
                        enabled: this.config.chartsPlotOptionsEnable.column //设置显示对应y的值
                    }
                },
                spline: {
                    pointPadding: 0.2,
                    borderWidth: 0,
                    dataLabels: {
                        enabled: this.config.chartsPlotOptionsEnable.spline //设置显示对应y的值
                    }
                },
                line: {
                    pointPadding: 0.2,
                    borderWidth: 0,
                    dataLabels: {
                        enabled: this.config.chartsPlotOptionsEnable.line //设置显示对应y的值
                    }
                },
                area: {
                    pointPadding: 0.2,
                    borderWidth: 0,
                    dataLabels: {
                        enabled: this.config.chartsPlotOptionsEnable.area //设置显示对应y的值
                    }
                }
            },
            yAxis: result.yAxis,
            tooltip: {
                shared: true
            },
            // legend: {
            //     // layout: 'vertical',
            //     // align: 'left',
            //     // x: 80,
            //     // verticalAlign: 'top',
            //     // y: 55,
            //     // floating: true,
            //     backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
            // },
            series: result.series
        });
    }else if(this.config.chartType=='bar'){
        var seriesData=[]
        var tooltip_valueSuffix="";
        if(result.series){
            result.series.forEach(function(item){
                // seriesData.push({colorByPoint: true,data:item.data,name:item.name});
                seriesData.push({colorByPoint: false,data:item.data,name:item.name});
            })
            if(result.series[0].tooltip.valueSuffix){
                tooltip_valueSuffix=result.series[0].tooltip.valueSuffix;
            }
        }else {
            $("#"+this.config.elm).empty();
            $("#"+this.config.elm).append("无数据可用");
            return;
        }
        Highcharts.chart(this.config.elm, {
            chart: {
                type: 'bar'
            },
            title: {
                text: this.config.title
            },
            subtitle: {
                text: '数据来源: 聚视互娱日志采集'
            },
            xAxis: {
                categories: result.categories,
                title: {
                    text: null
                }
            },
            yAxis: {
                min: 0,
                title: {
                    text: result.yAxis.title.text,
                    align: 'high'
                },
                labels: {
                    overflow: 'justify'
                }
            },
            tooltip: {
                // head + 每个 point + footer 拼接成完整的 table
                headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:.1f}'+tooltip_valueSuffix+'</b></td></tr>',
                footerFormat: '</table>',
                shared: true,
                useHTML: true
            },
            plotOptions: {
                bar: {
                    dataLabels: {
                        enabled: this.config.chartsPlotOptionsEnable.bar,
                        allowOverlap: true // 允许数据标签重叠
                    }
                }
            },
            // legend: {
            //     layout: 'vertical',
            //     align: 'right',
            //     verticalAlign: 'top',
            //     x: -40,
            //     y: 100,
            //     floating: true,
            //     borderWidth: 1,
            //     backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),
            //     shadow: true
            // },
            series: seriesData
        });
    }else if(this.config.chartType=="pie"){
        Highcharts.chart('container', {
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                type: 'pie'
            },
            title: {
                text: this.config.title
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f} %</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                        style: {
                            color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                        }
                    }
                }
            },
            series: [{
                name: '区域',
                colorByPoint: true,
                data: result
            }]
        });
    }

}