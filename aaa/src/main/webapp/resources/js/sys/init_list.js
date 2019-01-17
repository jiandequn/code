/*function open1(plugin){
	if ($('#tt').tabs('exists',plugin)){
		$('#tt').tabs('select',plugin);
	} else {
		$('#tt').tabs('add',{
			title:false,
			href:plugin+'.html',
			closable:false
		});
	}
}
*/

$(function(){
	$('#tt').tabs({
		onLoad:function(panel){
			var plugin = panel.panel('options').title;
			panel.find('textarea[name="code-'+plugin+'"]').each(function(){
				var data = $(this).val();
				data = data.replace(/(\r\n|\r|\n)/g, '\n');
				if (data.indexOf('\t') == 0){
					data = data.replace(/^\t/, '');
					data = data.replace(/\n\t/g, '\n');
				}
				data = data.replace(/\t/g, '    ');
				var pre = $('<pre name="code" class="prettyprint linenums"></pre>').insertAfter(this);
				pre.text(data);
				$(this).remove();
			});
			prettyPrint();
		}
	});
});
function open1(authId,plugin,name){
	var tab = $('#tt').tabs('getSelected');
	var index = $('#tt').tabs('getTabIndex',tab);
	if(index != 0 || index > 0){
		$("#tt").tabs("close",1);
	}
	$("ul li").removeClass("selected");
	$("#click_"+authId).addClass("selected");
	if ($('#tt').tabs('exists',name)){
		$('#tt').tabs('select', name);
	} else {
		$('#tt').tabs('add',{
			title:name,
			href:plugin,
//			closable:true,
			onSelect:function(name){
			       console.log(name);
			    },
			extractor:function(data){
				data = $.fn.panel.defaults.extractor(data);
//				console.log(data);
//				var tmp = $('<div></div>').html(data);
//				data = tmp.find('#content').html();
//				tmp.remove();
				return data;
			}
		});
	}
}



function gotoJSP(authId,requestUrl) {

	$("#center").html('');
	$("ul li").removeClass("selected");
	$("#click_"+authId).addClass("selected");
	$.post(requestUrl, function(data) {
		$("#center").html(data);
	});
}
function open2(authId,plugin,name){
	var tab = $('#tt').tabs('getSelected');
	var index = $('#tt').tabs('getTabIndex',tab);
	var contentHtml = '<div id="yeeee" class="easyui-layout" data-options="fit:true,split:true,maxHeight:1000"><iframe src="'+plugin+'" frameborder="0"  border="0" marginwidth="0" marginheight="0" scrolling="yes"   style="width:100%" height="100%"></iframe></div> ';
//    if(index != 0 || index > 0){
//        $("#tt").tabs("close",1);
//    }
	$("ul li").removeClass("selected");
	$("#click_"+authId).addClass("selected");
	if ($('#tt').tabs('exists',name)){
		$('#tt').tabs('select', name);
	} else {
		$('#tt').tabs('add',{
			title:name,
			closable:true,
			content:contentHtml,
			onSelect:function(name){
				console.log(name);
			},
			extractor:function(data){
				data = $.fn.panel.defaults.extractor(data);
//				console.log(data);
//				var tmp = $('<div></div>').html(data);
//				data = tmp.find('#content').html();
//				tmp.remove();
				return data;
			}
		});
	}
}