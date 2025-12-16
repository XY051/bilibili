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
<title>哔哩哔哩-首页</title>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/bilibiliIndex/css/reset.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/bilibiliIndex/css/common.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/bilibiliIndex/css/index.css">

<script
	src="<%=request.getContextPath()%>/static/Homepage/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/static/Homepage/js/jquery-1.10.2.js"></script>
<script
	src="<%=request.getContextPath()%>/static/bilibiliIndex/js/script.js"></script>
</head>
<body>


<script>
    function beforeSubmit(search) {
        if (search.keyword.value == '') {
            alert('搜索关键字不能为空！');
            search.keyword.focus();
            return false;
        }
    }
</script>
	<header id="header">
	<div id="page_top">
		<div class="bg-wrap">
			<div class="bg"></div>
			<div class="mask"></div>
		</div>
		<div class="header-top">
			<div class="page-width clearfix">
				<div class="header-top__nav">
					<ul>
						<li class="item item-home"><a href="index.sf">主站</a></li>
						<!-- <li class="item"><a href="Shopping.sf">周边</a></li> -->
						<li class="item"><a href="videoFileTop">我要投稿</a>
						</li>
						<li class="item"></li>
						<li class="item"><a style="height: 0px;"><form  action="searchResult.sf" method="post"><input placeholder="输入搜索的关键字" style="margin-right:5px;line-height: 42px; height: 1.7em;border-radius:4px;border: 1px solid #fff;padding: 0 12px;color: #fff;font-size: 12px;" type="text" name="keyword" value=""><input style="border-radius:4px;border: 1px solid #fff;text-align: center;padding: 0 12px;color: #fff;font-size: 12px;" type="submit" value="搜索"></form></a></li>
					</ul>
				</div>

				<div class="header-top__user">
					<%
				if (request.getSession().getAttribute("userName") == null) {
			%>
					<div class="login-box">
						<a href="login.sf">登录</a> <span></span> <a href="zhuce.sf">注册</a>
					</div>

					<%
				} else {
					
			%>
					<div onmouseover="project()">
						<span style="color: white;">欢迎你的登录: &nbsp; &nbsp; &nbsp;<%=request.getSession().getAttribute("userName") %></span>
						&nbsp;&nbsp;&nbsp;
					</div>
					<%} %>
				</div>
				<div class="header-top__user"
					style="position: fixed; z-index: 999; width: 300px; height: 170px; background-color: #FCF6EB; margin-left: 55%; margin-top: 3%"
					id="div2">
					<div id="kawayi" onmouseout="asjdh()">
						<div
							style="width: 130px; height: 160px; background-color: red; position: fixed; margin-left: 14%">
							<img id="imgtest" alt="" style="width: 130px; height: 160px;"
								onmouseover="project()" onmouseout="about()"
								src="<%=request.getContextPath()%>/static/Homepage/img/kawayi.png">
							<div
								style="width: 90px; height: 90px; margin-top: -113px; margin-left: -110px">
								<!-- 头像放这里 -->
								<img style="width: 90px; height: 80px" alt=""
									src="<%=request.getContextPath()%><%=request.getSession().getAttribute("userHand")%>">
							</div>
						</div>

					</div>
					<div class="center_header">
						<div align="center" style="margin-left: 60%">
							<a href="#"><img width="20px" height="20px"
								src="<%=request.getContextPath()%><%=request.getSession().getAttribute("userHand")%>" /></a>

						</div>
						<div align="center" style="margin-left: -100px">
							<span style="color: black;"> <b><%=request.getSession().getAttribute("userName")%></b>
							</span>
						</div>

						<div style="background-color: black">------------------------</div>
					</div>
					<div>
						<br> <a href="User_full_information.sf" style="color: red">个人信息</a>&nbsp;&nbsp;<a
							href="Update_login_password.sf" style="color: red">修改密码</a>&nbsp;&nbsp;<a
							href="User_full_information.sf" style="color: red">修改头像</a><br>
						<%
						    String userName = (String) request.getSession().getAttribute("userName");
						    if ("admin".equals(userName)) {
						%>
						    <br><a href="Houtai.sf" style="color: blue">后台管理</a><br>
						<%
						    }
						%>
						<div style="background-color: black">------------------------</div>
						<br> <a href="exect.sf" style="margin-left: 23%">切换用户</a>
					</div>


				</div>

			</div>
			<script type="text/javascript">
            $(document).ready(function(){
            	$("#div2").hide();
            });
           		 
            function asjdh() {//移出
	          	//alert("1");
            	$("#div2").hide();
	        }
		    
            function project() {//移入
	        	//alert("2");
	        	$("#div2").show();
	        }
			
			function about() {
				// 可以添加鼠标移出时的额外操作
			}
            </script>
		</div>
	</div>
	</div>
	</div>
	<div class="header-c">
		<div class="page-width">
			<a href="#" id="header_logo"></a>
			<p id="header_title"></p>

			<div class="header-title">哔哩哔哩 (゜-゜)つロ 干杯~</div>
		</div>
	</div>
	</header>

	<nav id="nav">
	<div class="page-width clearfix">
		<ul class="nav-list">
			<li class="item item-home"><a href="index.sf" class="link">首页</a>
			</li>
			<li class="item"><a href="#" class="link">
					<div class="num">
						<i>${countdh}</i>
					</div> Java
			</a></li>
			<li class="item"><a href="#" class="link">
					<div class="num">
						<i>${countmad}</i>
					</div> C++
			</a></li>
			<li class="item"><a href="#" class="link">
					<div class="num">
						<i>${countdm}</i>
					</div> Python
			</a></li>
			<%--<li class="item item-square"><a href="testshabi.sf" class="link">广场</a>--%>
				<%--<div class="nav-square__hover">--%>
					<%--<ul>--%>
						<!-- <li><a href="#"><i class="icon-activity"></i>活动中心</a></li>
              <li><a href="#"><i class="icon-game"></i>游戏中心</a></li>
              <li><a href="#"><i class="icon-news"></i>新闻中心</a></li>
              <li><a href="#"><i class="icon-friend"></i>画友</a></li>
              <li><a href="#"><i class="icon-tv"></i>芒果TV</a></li>-->
					<%--</ul>--%>
					<!-- 
            <a href="#" class="activity"><img src="images/cont/nav-square__img.jpg" alt="#"></a> -->
				<%--</div></li>--%>

			<%--<li class="item item-room"><a href="#" class="link">小黑屋</a></li>--%>
		</ul>
		<div class="nav-gif" id="qiulingyang">
			<img
				src="<%=request.getContextPath()%>/static/bilibiliIndex/images/cont/nav_img.gif"
				alt="求领养">
		</div>
	</div>
	</nav>

	<div id="banner">
		<div class="page-width clearfix">
			<div class="slider fl">
				<div class="slider-img">
					<a href="#" slider-title="pic1"><img
						src="<%=request.getContextPath()%>/static/bilibiliIndex/images/cont/slider_img1.png"
						alt="#"></a> <a href="#" slider-title="pic2"><img
						src="<%=request.getContextPath()%>/static/bilibiliIndex/images/cont/slider_img2.png"
						alt="#"></a> <a href="#" slider-title="pic3"><img
						src="<%=request.getContextPath()%>/static/bilibiliIndex/images/cont/slider_img3.png"
						alt="#"></a> <a href="#" slider-title="pic4"><img
						src="<%=request.getContextPath()%>/static/bilibiliIndex/images/cont/slider_img4.png"
						alt="#"></a>
				</div>
				<div class="slider-title">
					<p>pic1</p>
				</div>
				<div class="slider-btn">
					<span class="cur"></span> <span></span> <span></span> <span></span>
				</div>
				<a href="#" class="slider-more">More</a>
			</div>
			<div class="banner-list fr">
				<ul id="toptj">
					<c:forEach items="${fuck}" var="fuck">
						<li><a
							href="video.sf?dizhi=${fuck.videoAddress}&shipingID=${fuck.videoID}">
								<img src="<%=request.getContextPath()%>${fuck.videoImage}"
								alt="#">
								<div class="info">
									<p class="title">${fuck.videoName }</p>
									<p class="author">up主：admin</p>
									<p class="play">播放：${fuck.videolookTime}</p>
								</div>
						</a></li>
					</c:forEach>
				</ul>
				<a  class="btn btn-prev">刷新</a> <a
					class="btn btn-next">刷新</a>
			</div>
		</div>
	</div>

	<div id="main">
		<div class="page-width">
			<!-- 推广 -->


			<!-- 舞蹈 -->
			<div class="mainCont clearfix" id="dance" js-move="true">
				<div class="pic-list fl" js-tab="true">
					<div class="pic-list__title">
						<i class="icon icon-dance"></i>
						<h2>Java</h2>
						<div class="tab-title">
							<a href="#" class="cur">有新动态</a>

						</div>

						<div class="more-wrap">
							<a  class="dynamic" id="shuaxin"><i></i>刷新</a>

						</div>
					</div>
					<ul class="list1 pic-list__wrapper clearfix tab-cont__item tab-cont__cur">
						<!-- 这里开始遍历 -->
						<c:forEach items="${list}" var="list">
							<li class="item"><a
								href="video.sf?dizhi=${list.videoAddress}&shipingID=${list.videoID}"
								class="img-link"> <img
									src="<%=request.getContextPath()%>${list.videoImage}" alt="#">
									<span class="mask"></span> <span class="time">${list.videoTime}</span>
							</a>
								<div class="img-info">
									<a
										href="video.sf?dizhi=${list.videoAddress}&shipingID=${list.videoID}">${list.videoName }</a>
									<div class="btm">
										<div class="user">
											<i></i>${list.userMingzi}
										</div>
										<div class="online">
											<i></i>${list.videolookTime}</div>
									</div>
								</div></li>
						</c:forEach>
					</ul>
				</div>
				<div class="main-side fr" js-tab="true">
					<div class="main-side__title">
						<div class="rank-t">
							<h3>最新</h3>
						</div>
						<div class="tab-title">
							<a href="#" class="cur">最新动态</a>
						</div>
						<!-- <div class="side-select">
              <span>三日</span>
              <i></i>
            </div> -->
					</div>
					<div class="main-side__cont">
						<div class="tab-cont">

							<ul class="tab-cont__item main-rank" id="did2">

								<!-- ajax -->
							</ul>
						</div>
						<button type="button" class="more" id="chakan">
							查看更多<i></i>
						</button>

						<script type="text/javascript">
									
						</script>
					</div>
				</div>
			</div>

		</div>
	</div>


	<div id="sideBar">
		<div class="sideBar-list">

			<br> <a href="#">Java</a> <a href="#">C++</a> <a href="#">Python</a> <br>
			<a href="#"><i></i>排序</a>
		</div>
		<br>
		<button id="closemusic" type="button">关闭音乐</button>
		<div class="sideBar-line"></div>
		<a href="#" class="sideBar-toTop" id="backTop"></a>
	</div>


	<!-- 背景音乐 
  		
			<EMBED  id="music" src="/static/The Truth That You Leave.mp3"  HIDDEN="true" volume="10"  controller="true" autostart="true" loop="true">
			<script type="text/javascript">
			$("#closemusic").click(function(){
				/* $("#music").playOrPause(); */
				document.embeds('music').stop();
			})
	
			</script>
-->
</body>
</html>