/**
 * 菜单
 * */
//获取路径uri
var pathUri = window.location.href;
var leftPermArr;
console.log("pathUrl:" + pathUri);
$(function () {
    layui.use(['element'], function () {
        element = layui.element;
        //左侧导航区域（可配合layui已有的垂直导航）
        $.get("/auth/getUserPerms", function (data) {
            if (data != null) {
                leftPermArr = data;
                getMenus(data);
                element.render('nav');
            } else {
                layer.alert("权限不足，请联系管理员", function () {
                    //退出
                    window.location.href = "/logout";
                });
            }
        });
        element.on('nav(leftPermTreeFilter)', function (elem) {
            var tabtitle = $(".layui-tab-title li");
            var ids = new Array();
            $.each(tabtitle, function (i) {
                ids[i] = $(this).attr("lay-id");
            });
            $.each(leftPermArr, function (index,item) {
                if(item.id == elem.context.id && item.path.length>1){
                    for(var i=0;i<ids.length;i++){
                        if(ids[i] == item.id){
                            active.tabDelete(item.id);
                            break;
                        }
                    }
                    active.tabAdd(item.path, item.id, item.name);
                    active.tabChange(item.id);
                    //添加鼠标监控
                    initRightMouseMenu()
                    return;
                }
            });
        });
        //触发事件
        initTabNav(); //自动添加tab页签
        //当点击有site-demo-active属性的标签时，即左侧菜单栏中内容 ，触发点击事件
        $('.site-topTableFilter-active').on('click', function () {
            var dataid = $(this);
            //这时会判断右侧.layui-tab-title属性下的有lay-id属性的li的数目，即已经打开的tab项数目
            if ($(".layui-tab-title li[lay-id]").length <= 0) {
                //如果比零小，则直接打开新的tab项
                active.tabAdd(dataid.attr("data-url"), dataid.attr("data-id"), dataid.attr("data-title"));
            } else {
                //否则判断该tab项是否以及存在

                var isData = false; //初始化一个标志，为false说明未打开该tab项 为true则说明已有
                $.each($(".layui-tab-title li[lay-id]"), function () {
                    //如果点击左侧菜单栏所传入的id 在右侧tab项中的lay-id属性可以找到，则说明该tab项已经打开
                    if ($(this).attr("lay-id") == dataid.attr("data-id")) {
                        isData = true;
                    }
                })
                if (isData == false) {
                    //标志为false 新增一个tab项
                    active.tabAdd(dataid.attr("data-url"), dataid.attr("data-id"), dataid.attr("data-title"));
                }
            }
            //最后不管是否新增tab，最后都转到要打开的选项页面上
            active.tabChange(dataid.attr("data-id"));
        });
        $(".rightmenu li").click(function () {
            //右键菜单中的选项被点击之后，判断type的类型，决定关闭所有还是关闭当前。
            if ($(this).attr("data-type") == "closethis") {
                //如果关闭当前，即根据显示右键菜单时所绑定的id，执行tabDelete
                active.tabDelete($(this).attr("data-id"))
            } else if ($(this).attr("data-type") == "closeall") {
                var tabtitle = $(".layui-tab-title li");
                var ids = new Array();
                $.each(tabtitle, function (i) {
                    ids[i] = $(this).attr("lay-id");
                })
                //如果关闭所有 ，即将所有的lay-id放进数组，执行tabDeleteAll
                active.tabDeleteAll(ids);
            } else if ($(this).attr("data-type") == "closeothe") {
                var id = $(this).attr("data-id");
                var tabtitle = $(".layui-tab-title li");
                var ids = new Array();
                $.each(tabtitle, function (i) {
                    if (id != $(this).attr("lay-id")) {
                        ids[i] = $(this).attr("lay-id");
                    }
                });
                active.tabDeleteAll(ids);
            }

            $('.rightmenu').hide(); //最后再隐藏右键菜单
        })

    });
    initRightMouseMenu();
    initBgColorAction();
})
var initTabNav = function(){
    active = {
        //在这里给active绑定几项事件，后面可通过active调用这些事件
        tabAdd: function (url, id, name) {
            //新增一个Tab项 传入三个参数，分别对应其标题，tab页面的地址，还有一个规定的id，是标签中data-id的属性值
            //关于tabAdd的方法所传入的参数可看layui的开发文档中基础方法部分
            element.tabAdd('topTableFilter', {
                title: name,
                content: '<iframe data-frameid="' + id + '" scrolling="auto" frameborder="0" src="' + url + '" style="width:100%;height:99%;"></iframe>',
                id: id //规定好的id
            })
            CustomRightClick(id); //给tab绑定右击事件
            FrameWH();  //计算ifram层的大小
        },
        tabChange: function (id) {
            //切换到指定Tab项
            element.tabChange('topTableFilter', id); //根据传入的id传入到指定的tab项
        },
        tabDelete: function (id) {
            element.tabDelete("topTableFilter", id);//删除

        }
        , tabDeleteAll: function (ids) {//删除所有
            $.each(ids, function (i, item) {
                element.tabDelete("topTableFilter", item); //ids是一个数组，里面存放了多个id，调用tabDelete方法分别删除
            })
        }
    };
    function CustomRightClick(id) {
        //取消右键  rightmenu属性开始是隐藏的 ，当右击的时候显示，左击的时候隐藏
        $('.layui-tab-title li').on('contextmenu', function () {
            return false;
        })
        $('.layui-tab-title,.layui-tab-title li').click(function () {
            $('.rightmenu').hide();
        });
        //桌面点击右击
        $('.layui-tab-title li').on('contextmenu', function (e) {
            var popupmenu = $(".rightmenu");
            popupmenu.find("li").attr("data-id", id); //在右键菜单中的标签绑定id属性
            //判断右侧菜单的位置
            l = ($(document).width() - e.clientX) < popupmenu.width() ? (e.clientX - popupmenu.width()) : e.clientX;
            t = ($(document).height() - e.clientY) < popupmenu.height() ? (e.clientY - popupmenu.height()) : e.clientY;
            popupmenu.css({left: l, top: t}).show(); //进行绝对定位
            //alert("右键菜单")
            return false;
        });
    }
    function FrameWH() {
        var h = $(window).height() - 41 - 10 - 60 - 10 - 44 - 10;
        $("iframe").css("height", h + "px");
    }

    $(window).resize(function () {
        FrameWH();
    });
}
var initRightMouseMenu = function () {
    layui.config({
        base: '/layui/extend/',
    }).use(['tabrightmenu'], function () {
        var rightmenu_ = layui.tabrightmenu;
        // 默认方式渲染全部：关闭当前（closethis）、关闭所有（closeall）、关闭其它（closeothers）、关闭左侧所有（closeleft）、关闭右侧所有（closeright）、刷新当前页（refresh）
        rightmenu_.render({
            container: '#tabNavId',
            filter: 'topTableFilter',
        });
    });
}
var getMenus = function (data) {
    //回显选中
    var ul = $("<ul id='sysNavTree' class='layui-nav layui-nav-tree' lay-filter='leftPermTreeFilter'></ul>");
    for (var i = 0; i < data.length; i++) {
        var node = data[i];
        if (node.type == 0) {
            if (node.parentId == 0) {
                var li = $("<li class='layui-nav-item' flag='" + node.id + "'></li>");
                //父级无page
                var a = $("<a class='' href='javascript:;'>" + node.name + "</a>");
                li.append(a);
                //获取子节点
                var childArry = getParentArry(node.id, data);
                if (childArry.length > 0) {
                    a.append("<span class='layui-nav-more'></span>");
                    var dl = $("<dl class='layui-nav-child'></dl>");
                    for (var y in childArry) {
                        //var dd=$("<dd><a href='"+childArry[y].path+"'>"+childArry[y].name+"</a></dd>");
                        var dd = $("<dd><a id='" + childArry[y].id + "' href='javascript:;'>" + childArry[y].name + "</a></dd>");
                        //判断选中状态
                        if (pathUri.indexOf(childArry[y].path) > 0) {
                            li.addClass("layui-nav-itemed");
                            dd.addClass("layui-this")
                        }
                        //TODO 由于layui菜单不是规范统一的，多级菜单需要手动更改样式实现；
                        dl.append(dd);
                    }
                    li.append(dl);
                }
                ul.append(li);
            }
        }
    }
    $(".layui-side-scroll").append(ul);
}
//根据菜单主键id获取下级菜单
//id：菜单主键id
//arry：菜单数组信息
function getParentArry(id, arry) {
    var newArry = new Array();
    for (var x in arry) {
        if (arry[x].parentId == id)
            newArry.push(arry[x]);
    }
    return newArry;
}
function updateUsePwd() {
    layer.open({
        type: 1,
        title: "修改密码",
        fixed: false,
        resize: false,
        shadeClose: true,
        area: ['450px'],
        content: $('#pwdDiv')
    });
}
function initBgColorAction(){
    layui.use('colorpicker', function() {
        colorpicker = layui.colorpicker;
        colorpicker.render({
            elem: '#resetBgColor'
            ,color: 'rgba(7, 155, 140, 1)'
            ,format: 'rgb'
            ,predefine: true
            ,alpha: true
            ,size:'xs'
            ,done: function(color){
                $('#resetBgColor-input').val(color); //向隐藏域赋值
                color || this.change(color); //清空时执行 change
            }
            ,change: function(color){
                //给当前页面头部和左侧设置主题色
                $('.header-demo,.layui-side .layui-nav').css('background-color', color);
                $('#bgContent').css('background-color', color);
                $('.layui-header').css('background-color', color);
                $('.layui-nav').css('background-color', color);
                $('#sysNavTree').css('background-color', color);
                $('.layui-side-scroll').css('background-color', color);

            }
        });
    });
}
function resetBgColor(){
    $("#resetBgColor").trigger("onclick");
    $("#resetBgColor").trigger("click");
}
function updateUseByMe() {
    layer.open({
        type: 1,
        title: "用户信息",
        fixed: false,
        resize: false,
        shadeClose: true,
        area: ['450px'],
        content: $('#useDetail'),
        success: function (layero, index) {
            getCurrentUser();
        }
    });
}