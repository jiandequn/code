<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="/resources/css.inc"></jsp:include>
    <jsp:include page="/resources/js.inc"></jsp:include>
<link rel="stylesheet" type="text/css" href="/resources/css/doc.css">
<script type="text/javascript" src="/resources/js/sys/format.js"></script>
    <script type="text/javascript" src="/resources/js/sys/common.js"></script>
<link rel="stylesheet" type="text/css" href= "/resources/css/ajaxfileupload/ajaxfileupload.css"/>
<script type="text/javascript" src="/resources/js/ajaxfileupload/ajaxfileupload.js"></script>
    <script type="text/javascript" src="/resources/js/sys/commonApi.js"></script>
<title>AAA API DOC</title>
</head>
<body>
	<div>
		<table align="center" class="versiontable"
			style="border: 1px solid #EEE; width: 60%; margin: 0px auto;">
			<tr style="background-color: #effeef; border: 1px;">
				<td colspan="3" align="center">接口更新说明</td>
			</tr>
			<tr align="left">
				<td>日期</td>
				<td>接口</td>
				<td>内容</td>
			</tr>
		</table>
	</div>
	<br />
	<div>
		<ol id="menu">
            <li style="color:blue"><a href="#/api/aaa/device/check" title="设备鉴权">/api/aaa/device/check</a>设备鉴权</li>
            <li style="color:blue"><a href="#/api/aaa/content/check" title="业务鉴权">/api/aaa/content/check</a>业务鉴权</li>
		</ol>
	</div>

	<div>
        <a name="/api/aaa/device/check" id="device_check"></a>
        <div class="api_block">
            <div class="api_name">
                接口名称： <span class="api_name_ch">/api/aaa/device/check</span><span
                    style="float: right; font-size: 12px;">[<a href="#"
                                                               title="TOP" style="color: #FFF; text-decoration: none;">↑</a>]
				</span>
            </div>

            <div class="api_method">
                请求方式： <span class="api_method_ch">GET</span>
            </div>
            <div class="api_url">接口地址：http://{Server}/api/aaa/device/check.json</div>
            <div class="api_desc">接口说明：设备鉴权</div>
            <div class="api_result">
                返回值说明：<a href="#/RespCode" title="设备鉴权">"code":"N000000"</a>

            </div>
            <div class="api_params">
                参数列表：
                <div class="params_list">
                    <form>
                        <ul>
                            <li>mac：<input name="mac" value="CCD3E23BFE51"  class="easyui-validatebox" data-options="required:true" ><span
                                    class="notes">MAC地址</span></li>
                            <li>sn：<input name="sn" value="3032000017020390000" class="easyui-validatebox" data-options="required:true" ><span
                                    class="notes">SN</span></li>
                            <li>ca：<input name="ca" class="easyui-validatebox" ><span
                                    class="notes">CA</span></li>
                            <li>hsienNo：<input name="hsienNo"><span
                                    class="notes">区域ID;例子：02202</span></li>
                            <li>hsienName：<input name="hsienName" value=""><span
                                    class="notes">区域名称</span></li>
                        </ul>
                    </form>
                </div>
                <input type="button" class="test_btn" id="test_btn"
                       name="test_btn" value="测试接口" onclick="testInterface('device_check')"  />
                <input type="button" class="reset_btn" value="重置结果"  onclick="resetResult('device_check')"/>
            </div>
            <div class="return_area"></div>
        </div>

        <!-- -->
        <a name="/api/aaa/content/check" id="centent_check"></a>
        <div class="api_block">
            <div class="api_name">
                接口名称： <span class="api_name_ch">/api/aaa/content/check</span><span
                    style="float: right; font-size: 12px;">[<a href="#"
                                                               title="TOP" style="color: #FFF; text-decoration: none;">↑</a>]
				</span>
            </div>

            <div class="api_method">
                请求方式： <span class="api_method_ch">GET</span>
            </div>
            <div class="api_url">接口地址：http://{Server}/api/aaa/content/check.json</div>
            <div class="api_desc">接口说明：业务鉴权</div>
            <div class="api_result">
                返回值说明：<a href="#/RespCode" title="业务鉴权">"code":"N000000"</a>

            </div>
            <div class="api_params">
                参数列表：
                <div class="params_list">
                    <form>
                        <ul class="newul">
                            <li>userId：<input name="userId" value="1" class="easyui-validatebox" data-options="required:true"><span
                                    class="notes" >用户ID</span></li>
                            <li>mac：<input name="mac" value="CCD3E23BFE51"  class="easyui-validatebox" data-options="required:true" ><span
                                    class="notes">MAC地址</span></li>
                            <li>sn：<input name="sn" value="3032000017020390000" class="easyui-validatebox" data-options="required:true" ><span
                                    class="notes">SN</span></li>
                            <li>ca：<input name="ca" value="8731204033766236" class="easyui-validatebox" ><span
                                    class="notes">CA</span></li>
                            <li>token：<input name="token" value="INGJfswx8khG7qTwpkMK1A==" class="easyui-validatebox" ><span
                                    class="notes" >token</span></li>
                            <li>albumId：<input name="albumId" value="23" class="easyui-validatebox" data-options="required:true"><span
                                    class="notes">专辑ID</span></li>
                            <li>platform：<input name="platform" value="8" class="easyui-validatebox"><span
                                    class="notes"  >平台</span></li>
                            <li>spid：<input name="spid" value="HN60" class="easyui-validatebox"><span
                                    class="notes" >平台</span></li>
                            <li>cpId：<input name="cpId" value="1" class="easyui-validatebox"><span
                                    class="notes" >栏目ID</span></li>
                        </ul>
                    </form>
                </div>
                <input type="button" class="test_btn"
                       name="test_btn" value="测试接口" onclick="testInterface('centent_check')"  />
                <input type="button" class="reset_btn" value="重置结果"  onclick="resetResult('centent_check')"/>
            </div>
            <div class="return_area"></div>
        </div>

    </div>
</body>
</html>