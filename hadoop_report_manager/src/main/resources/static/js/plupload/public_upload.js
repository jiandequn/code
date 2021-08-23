/**
 * Created with IntelliJ IDEA.
 * User: anna
 * Date: 17-7-14
 * Time: 下午3:48
 * To change this template use File | Settings | File Templates.
 */

    //plupload中为我们提供了mOxie对象
    //有关mOxie的介绍和说明请看：https://github.com/moxiecode/moxie/wiki/API
function previewImage(file,callback){//file为plupload事件监听函数参数中的file对象,callback为预览图片准备完成的回调函数
    if(!file || !/image\//.test(file.type)) return; //确保文件是图片
    if(file.type=='image/gif'){//gif使用FileReader进行预览,因为mOxie.Image只支持jpg和png
        var fr = new mOxie.FileReader();
        fr.onload = function(){
            callback(fr.result);
            fr.destroy();
            fr = null;
        };
        fr.readAsDataURL(file.getSource());
    }else{
        var preloader = new mOxie.Image();
        preloader.onload = function() {
            preloader.downsize( 300, 300 );//先压缩一下要预览的图片,宽300，高300
            var imgsrc = preloader.type=='image/jpeg' ? preloader.getAsDataURL('image/jpeg',80) : preloader.getAsDataURL(); //得到图片src,实质为一个base64编码的数据
            callback && callback(imgsrc); //callback传入的参数为预览图片的url
            preloader.destroy();
            preloader = null;
        };
        preloader.load( file.getSource() );
    }
}
//计算文件大小
 function formatSize(size) {
//    if (size === undefined || /\D/.test(size)) {
//        return false;
//    }

    function round(num, precision) {
        return Math.round(num * Math.pow(10, precision)) / Math.pow(10, precision);
    }

    var boundary = Math.pow(1024, 4);

    // TB
    if (size > boundary) {
        return round(size / boundary, 1) + " " +'tb';
    }

    // GB
    if (size > (boundary/=1024)) {
        return round(size / boundary, 1) + " " + 'gb';
    }

    // MB
    if (size > (boundary/=1024)) {
        return round(size / boundary, 1) + " " +'mb';
    }

    // KB
    if (size > 1024) {
        return Math.round(size / 1024) + " " +'kb';
    }

    return size + " " +'b';
}
//上传部分
//参数说明 buttonId--点击按钮  showImageId--显示图片 progressId--显示图片内容以及进度条  url--上传地址  callback--上传成功后回调函数
function uploadPackage(buttonId, showImageId, progressId, url,callback){
    var uploadLength;
    var user = 111;
    url= url=="init"?"/admin/upload/init.json?user=":url;
    var uploader = new plupload.Uploader({  //实例化一个plupload上传对象
        browse_button : buttonId,
        url :url+user,
        flash_swf_url : '/resources/js/plupload/Moxie.swf',
        silverlight_xap_url :'/resources/js/plupload/Moxie.xap',
        multi_selection:false,   //只允许单文件上传
        filters: {
            mime_types : [ //只允许上传图片文件
                { title : "图片文件", extensions : "jpg,gif,png" }
            ]
        }
    });
    uploader.init(); //初始化

    //当Plupload初始化完成后触发
    uploader.bind('Init',function(uploader){
        uploadLength=$("#"+progressId).siblings(".moxie-shim").length;
        if(uploadLength>1){
            $("#"+progressId).next().remove();
        }
    });

    //绑定文件添加进队列事件
    uploader.bind('FilesAdded',function(uploader,files){
        uploader.start();
        for(var i = 0, len = files.length; i<len; i++){
            var file_name = files[i].name; //文件名
            var file_size=formatSize(files[i].size);  //文件大小
            //构造html来更新UI
            var html = '<li class="file-' + files[i].id +'"><p class="file-name">' + file_name + ' (' +file_size + ') </p><p class="progress"></p></li>';
            $('#'+progressId).html(html);
            $("#"+progressId).show();
        }
    });

    //绑定文件上传进度事件
    uploader.bind('UploadProgress',function(uploader,file){
        $('#'+progressId+ ' .file-'+file.id+' .progress').css('width',file.percent + '%');//控制进度条
    });
    //当上传队列中所有文件都上传完成后触发
    uploader.bind('FileUploaded',function(uploader,file,responseObject){
        //显示图片
        previewImage(file,function(imgsrc){
            $("#"+showImageId).attr("src",imgsrc);
        });
        $("#"+progressId).hide();  //隐藏进度条
        var data=responseObject.response;
        callback(data);
//        uploader.destroy(); //回调之后销毁对象
    });

    //当发生错误时触发
    uploader.bind('Error',function(up, err){
        alert( err.message);
    });
}









