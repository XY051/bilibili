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

    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/static/User_Data_Update/css/jiazaitoubu.css"
          type="text/css" />
    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/static/User_Data_Update/css/css.css"
          type="text/css" />
    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/static/User_Data_Update/css/center.css"
          type="text/css" />
    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/static/User_Data_Update/city/city.css"
          type="text/css" />
    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/static/User_Data_Update/css/shangchuan.css"
          type="text/css" />
    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/static/Homepage/css/bootstrap.css"
          type="text/css" />

    <!-- <script src="<%=request.getContextPath()%>/static/User_Data_Update/"></script> -->
    <script
            src="<%=request.getContextPath()%>/static/User_Data_Update/js/jquery-1.8.3.min.js"></script>
    <script
            src="<%=request.getContextPath()%>/static/User_Data_Update/js/index2.js"></script>
    <script
            src="<%=request.getContextPath()%>/static/User_Data_Update/js/index2.js"></script>


    <script type="text/javascript">


        //显示灰色 jQuery 遮罩层
        function showBg() {
            var bh = $("body").height();
            var bw = $("body").width();
            /* 	$("#sid").click(function(){
                    //alert("123");
                     window.open("Shangchuan.jsp","_blank","toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, width=400, height=200");

                });  */

            $("#fullbg").css({
                height: bh,
                width: bw,
                display: "block"
            });
            $("#dialog").show();
        }
        //关闭灰色 jQuery 遮罩
        function closeBg() {
            $("#fullbg,#dialog").hide();
        }


    </script>


    <title>哔哩哔哩-用户信息</title>
</head>
<body>
<!-- 这里是上传头像界面 -->


<!-- 这里是上传头像界面 -->

<div class="top" id="wyqtb">
    <!--热门搜索-->

    <!--导航-->
    <div class="dao_hang" style="background-color: #6C89B2;">


        <!--结束-->
        <div class="dao_list">
            <a href="index.sf">首页</a>

        </div>
    </div>
</div>
<script>
    window.onload=function(){
        $(".aside").css({display:"none"})
        $(".important").mouseenter(function(){
            $(".aside").css({display:"block"})
        }).mouseleave(function(){
            $(".aside").css({display:"none"})
        })
    }
</script>
<!--当前位置-->
<div class="now_positionm">
    <span>当前位置：</span><a href="index.sf">首页></a><a
        href="User_full_information.sf">个人中心</a>
</div>
<!--centers-->
<div class="centers_m">
    <!--清除浮动-->
    <div class="clear_sm"></div>
    <!--left-->
    <div class="centers_ml">
        <!--头像-->
        <div class="center_header">
            <a href="User_full_information.sf"><img
                    src="<%=request.getContextPath()%>${user.userHand}" /></a> <em><font>${user.userMingzi}</font></em>
            <!-- <a href="">上传头像</a></font>	 -->
            <font style="color: red; font-size: 10px;">
        </div>
        <!--列表go-->
        <div class="centers_listm">
            <div class="centers_listm_one">
                <img
                        src="<%=request.getContextPath()%>/static/User_Data_Update/images/zshy.png" />
                <em>个人信息</em>
            </div>
            <!--一条开始-->



            <!--一条开始-->
            <div class="centers_listm_one_in">
                <img
                        src="<%=request.getContextPath()%>/static/User_Data_Update/images/ddgl.png" />
                <em>关注情况</em> <b></b>
            </div>
            <span class="gjszmdm"> <a href="userfollow2.sf?user_id=${user.userID}" class="center_in_self"><font>关注的人</font></a>
				</span>

            <%-- <!--一条开始-->
            <div class="centers_listm_one_in">
                <img
                    src="<%=request.getContextPath()%>/static/User_Data_Update/images/wdssc.png" />
                <em>收藏管理</em> <b></b>
            </div>
            <span class="gjszmdm"> <a href="#" class="center_in_self"><font>店铺收藏</font></a>
                <a href="#" class="center_in_self"><font>菜品收藏</font></a>
            </span> --%>

        </div>
        <script type="text/javascript">
            $(function(){//第一步都得写这个
                $(".centers_listm_one_in").click(function(){
                    $(this).next(".gjszmdm").slideToggle().siblings("gjszmdm").slideUp()
                });

                //弹出上传头像界面


            })
        </script>
    </div>
    <!--right-->
    <div class="centers_mr">

        <!--一条开始-->
        <div class="public_m2">个人信息</div>
        <div class="public_m4">
            <p>
                <em>昵称：</em><i style="">${user.userMingzi}</i>
                <em>性别：</em><i style="">${user.usersex}</i>
            </p>
            <p>
                <em>简介：</em><i style="">${user.showme}</i>
            </p>
            <p>
                <em>邮箱：</em><i style="">${user.userEmial}</i>
            </p>
        </div>
        <div class="public_m2">作品</div>
        <div class="public_m4">
        </div>
        <% int i=1; %>
        <c:forEach var="video" items="${videoList }" >
            <c:choose>
                <c:when test="${video.ispass==1&&video.ischeck==1 }">

                    <div  class="row">
                        <a href="/bilibili/video.sf?dizhi=${video.videoAddress}&&shipingID=${video.videoID}">
                            <div style="margin-right: 30px;margin-top: 20px;margin-bottom: 20px">
                                <div class="col-md-1" style="height: 149px;width:20px;margin-right:0px;margin-left:0px;margin-bottom: 20px;border-radius: 3px;margin-right: 12px;padding-right: 0px;font-size: 20px;font-family: Helvetica-BoldOblique,Helvetica;margin-right: 11px;color: #FA5055" ><%=i %></div>
                                <div class="col-md-4" style="margin-left:20px;height: 149px;width:265px;margin-right:0px;margin-left:0px;margin-bottom: 20px;border-radius: 3px;margin-right: 30px;padding-right: 0px" ><img src='/bilibili/${video.videoImage}' style="height: 149px;width:265px;border-radius: 5px;" /></div>
                                <div class="col-md-6" style="padding-left: 0px;height: 149px;margin-right:0px;margin-left:0px;margin-bottom: 20px;border-radius: 3px;margin-right: 12px;padding-right: 0px" ><div class="_oR7n"><p class="_18oBr uNe2s" style="line-height: 20px;font-weight: 500;font-size: 16px;font-family: -apple-system,BlinkMacSystemFont,Segoe UI,PingFang SC,Hiragino Sans GB,Microsoft YaHei,Helvetica Neue,Helvetica,Arial,sans-serif,Apple Color Emoji,Segoe UI Emoji,Segoe UI Symbol;color: #FF9000;">${video.videoName}</p><p style="font-size: 14px;color: #999;padding-top: 6px">${user.userMingzi}</p>  <p style="width:400px;height: 55px; text-overflow: ellipsis;overflow: hidden;-webkit-line-clamp: 2;font-size: 14px;color: #666;padding-top: 6px">



                                </p> <p class="_1UG9x"><p style="color: #999;font-size: 12px;">${video.supportcount}个赞</p></div></div>
                            </div>
                        </a>
                    </div>


                    <% i++; %>

                </c:when>
                <c:otherwise>
                </c:otherwise>
            </c:choose>







        </c:forEach>





    </div>
</div>
<script
        src="<%=request.getContextPath()%>/static/Homepage/js/bootstrap.min.js"></script>

</body>
</html>