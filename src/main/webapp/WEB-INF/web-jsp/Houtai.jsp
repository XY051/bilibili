<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">






<link rel="stylesheet" href="<%=request.getContextPath()%>/static/Houtai/css/style.css" type="text/css" />
	<script
	src="<%=request.getContextPath()%>/static/Homepage/js/sHover.min.js"></script>
	<script
	src="<%=request.getContextPath()%>/static/User_Data_Update/js/jquery-1.8.3.min.js"></script>

	
		<!-- 这里还是用ajax提交 -->
		 <!-- 这里是自定义的js -->
<script
src="<%=request.getContextPath()%>/static/adminjs/chaxunjs.js"></script>


<title>哔哩哔哩-管理员后台</title>
</head>
<body>	
	<script type="text/JavaScript"> 
var $=function(id) {
   return document.getElementById(id);
}

// 确保jQuery加载完成后再执行noConflict
var j = jQuery.noConflict();

function show_menu(num){
for(i=0;i<100;i++){
	if($('li0'+i)){
	$('li0'+i).style.display='none';
	$('f0'+i).className='';
	 }
}
	  $('li0'+num).style.display='block';//触发以后信息块
	  $('f0'+num).className='left02down01_xia_li';//触发以后TAG样式
}

function show_menuB(numB){
	for(j=0;j<100;j++){
		 if(j!=numB){
			if($('Bli0'+j)){
		  $('Bli0'+j).style.display='none';
		  $('Bf0'+j).style.background='url(<%=request.getContextPath()%>/static/Houtai/images/01.gif)';
		}
		 }
	}
	if($('Bli0'+numB)){   
		if($('Bli0'+numB).style.display=='block'){
		  $('Bli0'+numB).style.display='none';
		 $('Bf0'+numB).style.background='url(<%=request.getContextPath()%>/static/Houtai/images/01.gif)';
		}else {
		  $('Bli0'+numB).style.display='block';
		  $('Bf0'+numB).style.background='url(<%=request.getContextPath()%>/static/Houtai/images/02.gif)';
		}
	}
}


var temp=0;
function show_menuC(){
		if (temp==0){
		 document.getElementById('LeftBox').style.display='none';
	  	 document.getElementById('RightBox').style.marginLeft='0';
		 document.getElementById('Mobile').style.background='url(<%=request.getContextPath()%>/static/Houtai/images/center.gif)';

		 temp=1;
		}else{
		document.getElementById('RightBox').style.marginLeft='222px';
	   	document.getElementById('LeftBox').style.display='block';
		document.getElementById('Mobile').style.background='url(<%=request.getContextPath()%>/static/Houtai/images/center0.gif)';

	   temp=0;
			}
	 }
</script>
</head>

<body>
<div class="header">
	<div class="header03"></div>
	<div class="header01"></div>
	<div class="header02">注册用户信息管理系统</div> 
	
</div>
<div class="left" id="LeftBox">
	<div class="left01">
		<div class="left01_right"></div>
		<div class="left01_left"></div>
		<div class="left01_c">超级管理员：admin</div>
	</div>


	<div class="left02">
		<div class="left02down">
			<div class="left02down01"><a  onclick="show_menuB(21)" href="javascript:;"><div id="Bf021" class="left02down01_img"></div>视频审核</a></div>
			<div class="left02down01_xia noneBox" id="Bli021">
				<ul>
					<li onmousemove="show_menu(21)" id="f021"><a >&middot;视频审核</a></li>
				</ul>
			</div>

		</div>
	</div>
	
	<div class="left02">
		<div class="left02down">
			<div class="left02down01"><a  onclick="show_menuB(22)" href="javascript:;"><div id="Bf022" class="left02down01_img"></div>音频管理</a></div>
			<div class="left02down01_xia noneBox" id="Bli022">
				<ul>
					<li onmousemove="show_menu(22)" id="f022"><a >&middot;批量提取音频</a></li>
				</ul>
			</div>
		</div>
	</div>

	<div class="left02">
	  <div class="left02down">
			<div class="left02down01"><a  onclick="show_menuB(80)" href="javascript:;"><div id="Bf080" class="left02down01_img"></div>用户信息查询</a></div>
			<div class="left02down01_xia noneBox" id="Bli080">
				<ul>
					<li onmousemove="show_menu(10)" id="f010"><a href="#">&middot;精确查询</a></li>
					<li onmousemove="show_menu(11)" id="f011"><a href="#">&middot;组合条件查询</a></li>
				</ul>
			</div>
		    <div class="left02down01"><a onclick="show_menuB(81)" href="javascript:;">
		      <div id="Bf081" class="left02down01_img"></div>
		      用户密码管理</a></div>
			<div class="left02down01_xia noneBox" id="Bli081">
				<ul>
					<li onmousemove="show_menu(12)" id="f012"><a href="#">&middot;找回密码</a></li>
					<li onmousemove="show_menu(13)" id="f013"><a href="#">&middot;更改密码</a></li>
				</ul>
			</div>
		</div>
	</div>



	<div class="left02">
		<div class="left02down">
			<div class="left02down01"><a  onclick="show_menuB(85)" href="javascript:;"><div id="Bf085" class="left02down01_img"></div>留言管理</a></div>
			<div class="left02down01_xia noneBox" id="Bli085">
				<ul>
					<li onmousemove="show_menu(51)" id="f051"><a href="#">&middot;留言审核</a></li>
				</ul>
			</div>


		</div>
	</div
















	<!--
    	作者：offline
    	时间：2017-07-19
    	描述：
   
	<div class="left02">
		<div class="left02top">
			<div class="left02top_right"></div>
			<div class="left02top_left"></div>
			<div class="left02top_c">用户分析</div>
		</div>
		<div class="left02down">
			<div class="left02down01"><a  onclick="show_menuB(82)" href="javascript:;"><div id="Bf082" class="left02down01_img"></div>用户注册统计</a></div>
			<div class="left02down01"><a href="#"><div class="left02down01_img"></div>用户登录统计</a></div>
			<div class="left02down01"><a href="#"><div class="left02down01_img"></div>用户激活统计</a></div>
		</div>
	</div>
	
	<div class="left02">
		<div class="left02top">
			<div class="left02top_right"></div>
			<div class="left02top_left"></div>
			<div class="left02top_c">用户过滤</div>
		</div>
		<div class="left02down">
			<div class="left02down01"><a href="#"><div class="left02down01_img"></div>过滤IP(段)</a></div>
			<div class="left02down01"><a href="#"><div class="left02down01_img"></div>过滤用户名</a></div>
		</div>
	</div>
	 -->
	<div class="left02">
		<div class="left02top">
			<div class="left02top_right"></div>
			<div class="left02top_left"></div>
			<div class="left02top_c">系统管理</div>
		</div>
		<div class="left02down">
			<div class="left02down01"><a href="#"><div class="left02down01_img"></div>权限管理</a></div>
			<div class="left02down01"><a href="#"><div class="left02down01_img"></div>用户组管理</a></div>
			<div class="left02down01"><a href="#"><div class="left02down01_img"></div>操作日志</a></div>
		</div>
	</div>
	<div class="left01">
		<div class="left03_right"></div>
		<div class="left01_left"></div>
		<div class="left03_c"><a href="<%=request.getContextPath()%>/index.sf" style="color: white; text-decoration: none;">安全退出</a></div>
	</div>
	
	<script type="text/javascript">
		// 批量提取音频功能
		jQuery(document).ready(function() {
			jQuery("#batchExtractBtn").click(function() {
				// 确认操作
				if (!confirm("确定要批量提取所有视频的音频吗？这可能需要一些时间。")) {
					return;
				}
				
				// 显示加载动画和进度条
				jQuery("#loading").show();
				jQuery("#extractResult").hide();
				jQuery("#progressContainer").show();
				// 重置进度条
				jQuery("#progressBar").css('width', '0%').text('0%');
				jQuery("#progressText").text('开始处理...');
				
				// 启动批量提取音频
				jQuery.post('<%=request.getContextPath()%>/startBatchExtractAudio', {}, function(startResponse) {
					if (startResponse.success) {
						// 开始轮询进度
						var progressInterval = setInterval(function() {
							jQuery.get('<%=request.getContextPath()%>/getBatchExtractProgress', function(progressData) {
								if (progressData) {
									// 更新进度条
									var percentage = progressData.percentage || 0;
									jQuery("#progressBar").css('width', percentage + '%').text(percentage + '%');
									jQuery("#progressText").text(progressData.status + ' - 处理: ' + progressData.processed + '/' + progressData.total + ', 成功: ' + progressData.success);
									
									// 如果处理完成
									if (progressData.status === '完成' || progressData.processed >= progressData.total) {
										clearInterval(progressInterval);
										jQuery("#loading").hide();
										jQuery("#resultText").text('批量提取音频完成！');
										jQuery("#successCount").text('成功处理：' + progressData.success + ' 个视频');
										jQuery("#totalCount").text('总共处理：' + progressData.total + ' 个视频');
										jQuery("#extractResult").show();
										alert("批量提取音频完成！成功处理 " + progressData.success + " 个视频，共 " + progressData.total + " 个视频需要处理");
									}
								}
							});
						}, 1000); // 每秒更新一次进度
					} else {
						jQuery("#loading").hide();
						alert("启动批量提取失败：" + startResponse.message);
					}
				});
			});
		});
	</script>
</div>
<div class="rrcc" id="RightBox">
	<div class="center" id="Mobile" onclick="show_menuC()"></div>
	<div class="right" id="li021">

		<div class="right01"><img src="<%=request.getContextPath()%>/static/Houtai/images/04.gif" /> 视频管理 &gt; <span>视频审核</span></div>

		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="3">


			<tr>
				<td height="100%" valign="top">
					<div style="overflow:auto;height:100%; width:100%">
						<table width="100%" border="0" cellpadding="3" cellspacing="1" class="table1">
							<TR>
								<th align="center"><input type="checkbox" name="checkbox" id="checkbox"></th>
								<th align="center">视频id</th>
								<th align="center">视频名称</th>
								<th align="center">用户名称</th>
								<th align="center">审核状态</th>
								<th width="160" align="center">操作</th>
							</TR>
							<c:forEach items="${videolist}" var="video">
								<TR>
									<TD align="center"><input type="checkbox" name="checkboxc" id="checkboxc"></TD>
									<TD width="70" align="center">${video.videoID}</TD>
									<TD  width="" align="center">${video.videoName}</TD>
									<TD align="center"><a style="color: #0AB5DA" href="/bilibili/user_info?user_id=${video.user_id}">${video.userName}</a></TD>
									<TD width="70" align="center">未审核</TD>

									<TD width="160" align="center"><BUTTON style="height:21px; font-size:12px"
																		   onClick="javascript:if (confirm('查看该信息？')) location.href='/bilibili/video.sf?dizhi=${video.videoAddress}&shipingID=${video.videoID}&isadmin=1';else return;">查看</BUTTON>
										<BUTTON style="height:21px; font-size:12px" onClick="javascript:if (confirm('通过审核？')) location.href='/bilibili/checkvideo?s=1&video_id=${video.videoID}';else return;">通过
										</BUTTON> <BUTTON style="height:21px; font-size:12px" onClick="javascript:if (confirm('不通过审核？')) location.href='/bilibili/checkvideo?s=0&video_id=${video.videoID}';else return;">不通过</BUTTON></TD>
								</TR>
							</c:forEach>
						</table>
					</div>
				</td>
			</tr>
		</table>


	</div>


	<div class="right noneBox" id="li051">
		<div class="right01"><img src="<%=request.getContextPath()%>/static/Houtai/images/04.gif" /> 留言管理 &gt; <span>留言审核</span></div>
	</div>
	
	<div class="right noneBox" id="li022">
		<div class="right01"><img src="<%=request.getContextPath()%>/static/Houtai/images/04.gif" /> 音频管理 &gt; <span>批量提取音频</span></div>
		<div style="padding: 20px;">
			<h3>批量提取音频</h3>
			<p>此功能将为所有尚未提取音频的视频文件提取音频，并保存为MP3格式。</p>
			<button id="batchExtractBtn" style="background: #00a1d6; color: white; padding: 10px 20px; border: none; cursor: pointer;">
				开始批量提取音频
			</button>
			<div id="extractResult" style="margin-top: 15px; display: none;">
				<p>提取结果：<span id="resultText"></span></p>
				<p>成功处理：<span id="successCount"></span> 个视频</p>
				<p>总共处理：<span id="totalCount"></span> 个视频</p>
			</div>
			<div id="loading" style="display: none; margin-top: 15px;">
				正在处理，请稍候... <img src="<%=request.getContextPath()%>/static/Houtai/images/01.gif" alt="加载中..." width="16" height="16" />
				<div id="progressContainer" style="margin-top: 10px; display: none;">
					<div style="width: 100%; background-color: #f0f0f0; border-radius: 5px; overflow: hidden;">
						<div id="progressBar" style="width: 0%; height: 20px; background-color: #4CAF50; text-align: center; line-height: 20px; color: white;">0%</div>
					</div>
					<div id="progressText">处理中...</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="right noneBox" id="li051">
		<div class="right01"><img src="<%=request.getContextPath()%>/static/Houtai/images/04.gif" /> 留言管理 &gt; <span>留言审核</span></div>
		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="3">


			<tr>
				<td height="100%" valign="top">
					<div style="overflow:auto;height:100%; width:100%">
						<table width="100%" border="0" cellpadding="3" cellspacing="1" class="table1">
							<TR>
								<th align="center"><input type="checkbox" name="checkbox" id="checkbom"></th>
								<th align="center">留言id</th>
								<th align="center">留言内容</th>
								<th align="center">用户名称</th>
								<th align="center">视频名称</th>
								<th width="160" align="center">操作</th>
							</TR>
							<c:forEach items="${messages}" var="message">
								<TR>
									<TD align="center"><input type="checkbox" name="checkbox" id="checkboxm"></TD>
									<TD width="20" align="center">${message.messageID}</TD>
									<TD  width="" align="center">${message.message}</TD>
									<TD align="center"><a style="color: #0AB5DA" href="/bilibili/user_info?user_id=${message.messageuserID}">${message.messageuserName}</a></TD>
									<TD width="160" align="center">${message.videoName}</TD>
									<TD width="160" align="center">
										</BUTTON> <BUTTON style="height:21px; font-size:12px" onClick="javascript:if (confirm('是否通过该评论？')) location.href='/bilibili/checkmessage?id=${message.messageID}';else return;">通过</BUTTON>

										<BUTTON style="height:21px; font-size:12px"
																		   onClick="javascript:if (confirm('查看该信息？')) location.href='/bilibili/video.sf?dizhi=${message.address}&shipingID=${message.messagevideoID}&isadmin=1';else return;">查看</BUTTON>

										</BUTTON> <BUTTON style="height:21px; font-size:12px" onClick="javascript:if (confirm('是否删除该评论？')) location.href='/bilibili/delmessage?id=${message.messageID}';else return;">删除</BUTTON></TD>

								</TR>
							</c:forEach>
						</table>
					</div>
				</td>
			</tr>

		</table>
		<div align="center" style="margin-top: " ><BUTTON style="height:21px; font-size:12px" onClick="javascript:location.href='/bilibili/Houtai.sf?s=${s}';">下一页</BUTTON></div>








	</div>


























	<div class="right noneBox" id="li011">
		<div class="right01"><img src="<%=request.getContextPath()%>/static/Houtai/images/04.gif" /> 用户信息查询 &gt; <span>组合条件查询</span></div>

		 
		请输入要查询的内容:<input type="text" id="usertext" placeholder="根据用户名查询" >  <input type="button" id="ajaxbutton1"  value="查询">
		
		<br>
		
          
		<div id="chaxunqu"></div>

		请输入要查询的内容:<input type="text" id="usertext2" placeholder="根据手机查询" >  <input type="button" id="ajaxbutton2"  value="查询">
		
		
		<br>
          
		<div id="chaxunqu2"></div>

		
	</div>
	<div class="right noneBox" id="li012">
		<div class="right01"><img src="<%=request.getContextPath()%>/static/Houtai/images/04.gif" /> 用户密码管理 &gt; <span>找回密码</span></div>
	</div>
	<div class="right noneBox" id="li013">
		<div class="right01"><img src="<%=request.getContextPath()%>/static/Houtai/images/04.gif" /> 用户密码管理 &gt; <span>更改密码</span></div>
	</div>

	<div class="right noneBox" id="li010">

		<div class="right01"><img src="<%=request.getContextPath()%>/static/Houtai/images/04.gif" /> 用户信息查询 &gt; <span>精确查询</span></div>
		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="3">


			<tr>
				<td height="100%" valign="top">
					<div style="overflow:auto;height:100%; width:100%">
						<table width="100%" border="0" cellpadding="3" cellspacing="1" class="table1">
							<TR>
								<th align="center"><input type="checkbox" name="checkbox" id="checkbox12"></th>
								<th align="center">用户ID</th>
								<th align="center">用户账号</th>
								<th align="center">联系电话</th>
								<th align="center">地址</th>
								<th align="center">审核状态</th>
								<th width="160" align="center">操作</th>
							</TR>
							<c:forEach items="${list}" var="list">
								<TR>
									<TD align="center"><input type="checkbox" name="checkbox2" id="checkbox2"></TD>
									<TD align="center">${list.userID}</TD>
									<TD align="center">${list.userName}</TD>
									<TD align="center">${list.userPhone}</TD>
									<TD align="center">${list.userAddress}</TD>
									<TD align="center">${list.userState }</TD>

									<TD width="160" align="center"><BUTTON style="height:21px; font-size:12px"
																		   onClick="javascript:if (confirm('查看该信息？')) location.href='static/Xinxi.jsp?id=${list.userID}';else return;">查看</BUTTON>
										<BUTTON style="height:21px; font-size:12px" onClick="javascript:if (confirm('编辑该信息？')) location.href='static/Xinxi.jsp?id=${list.userID}';else return;">编辑
										</BUTTON> <BUTTON style="height:21px; font-size:12px" onClick="javascript:if (confirm('通过审核？')) location.href='static/Xinxi.jsp?id=${list.userID}';else return;">审核</BUTTON></TD>
								</TR>
							</c:forEach>
						</table>
					</div>
				</td>
			</tr>
		</table>
		<script type="text/javascript">
            <%--jQuery(document).ready(function(){--%>
            <%--//alert("1");--%>
            <%--/* if(request.getAttribute("echo")!=null){--%>
            <%--alert(request.getAttribute("echo"));--%>
            <%--}else{--%>
            <%--alert("欢迎进入后台用户管理");--%>
            <%--} */--%>
            <%--var echo ="<%=request.getAttribute("echo")%>";--%>
            <%--//alert(echo);--%>
            <%--if(echo=="null"){--%>
            <%--alert("欢迎进入用户后台管理");--%>
            <%--}--%>
            <%--if(echo!="null"){--%>
            <%--alert(echo);--%>
            <%--}--%>
            <%--});--%>

		</script>



	</div>




</div>
	

</body>
</html>