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
    <title>投稿</title>

    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/static/bilibiliIndex/css/reset.css">
    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/static/bilibiliIndex/css/common.css">
    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/static/bilibiliIndex/css/index.css">
    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/static/Homepage/mycssAndimg/css.css">

    <script src="<%=request.getContextPath()%>/static/Homepage/js/jquery-1.10.2.js"></script>

</head>
<body >


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
                        <li class="item">
                        <li class="item"><a href="videoFileTop">我要投稿</a>
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
                        <div style="background-color: black">------------------------</div>
                        <br> <a href="exect.sf" style="margin-left: 23%">切换用户</a>
                    </div>


                </div>

            </div>
            <script type="text/javascript">
                $(document).ready(function(){
                    $("#div2").hide();

                })
                function asjdh() {//移出
                    //alert("1");
                    $("#div2").hide();
                }
                function project() {//移入
                    //alert("2");
                    $("#div2").show();
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




<div id="main" style="background-color: #5bc0de" >
    <div class="page-width" >

        <div id="none">
            <!-- style="width: 100%;height: 60px;background-color: white;" -->
        </div>

        <div>

            <div style="margin-left: 300px">

                <form id="upload_form">

                    <div>
                        <div><label for="image_file" style="color: #212121;font-size: 16px;font-weight: 700;">请选择投稿视频文件</label></div>
                        <div><input style="color: white;border: 1px solid white;" type="file" name="files" id="video" /></div>
                    </div>
                    <div>
                        <div><label for="image_file" style="color: #212121;font-size: 16px;font-weight: 700;">请选择投稿封面壁纸</label></div>
                        <div><input style="color: white;border: 1px solid white;margin-top: 5px"  type="file" name="files" id="img" /></div>
                    </div>
                    <div>
                        <div><label for="image_file" style="color: #212121;font-size: 16px;font-weight: 700;">请输入视频标题</label></div>
                        <div><input  style="margin-top: 5px;border: 1px solid white;" type="text" name="biaoti"  id="biaoti" /></div>
                    </div>

                    <div style="-webkit-border-radius: 10px;">
                        <div><label for="image_file" style="color: #212121;font-size: 16px;font-weight: 700;">请输入视频描述</label></div>
                        <div style="margin-top:5px;-webkit-border-radius: 10px;"><textarea  rows="13" name="miaoshu" id="miaoshu" cols="70" style="-webkit-border-radius: 10px;"></textarea></div>
                    </div>

                    <div >
                        <div><label for="image_file" style="color: #212121;font-size: 16px;font-weight: 700;">请选择投放区域</label></div>
                        <div style="margin-top: 5px;">
                            <label><input name="Fruit" type="radio" value="1"  checked />动漫 </label>
                            <label><input name="Fruit" type="radio" value="2" />影视 </label>
                            <label><input name="Fruit" type="radio" value="3" />音乐 </label>

                        </div>
                    </div>
                    <div>
                        <input style="background: #00a1d6;color: wheat;" type="button" value="立即投稿" id="tougao" />  <input style="background: #00a1d6;color: wheat;" type="button" id="fanhui" value="取消返回" />
                    </div>
                </form>
                <div style="display: none;" id="daxiaobuneng"><b style="color: red;">总大小不能超过1g</b></div>





            </div>
            <script type="text/javascript">
                $("#fanhui").click(function(){
                    location.href = "index.sf";
                })

            </script>

            <script>
                $("#tougao").click(function(){
                    //判断视频
                    var video = $("#video").val();
                    if(video == "") {
                        alert("请上传投稿的视频");
                        return false;
                    } else {
                        if(!/\.(mp4)$/.test(video)) {
                            alert("视频格式必须为.MP4格式")
                            return false;
                        }
                        //判断图片
                        var img = $("#img").val();
                        if(img == "") {
                            alert("请选择图片");
                            return false;
                        } else {
                            if(!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(img)) {
                                alert("图片类型必须是.gif,jpeg,jpg,png中的一种")
                                return false;
                            }
                        }
                        //开始判断输入的内容
                        var biaoti = $("#ibiaoti").val();
                        var miaoshu = $("#miaoshu").val();
                        if(biaoti == "" || miaoshu == "" || miaoshu == "null" || biaoti == "null") {
                            alert("请正确输入标题和描述");
                            return false;
                        }
                        //判断文件总大小 不能超1g
                        //用户上传的视频
                        var addImags =$("#video").prop('files');
                        var fileSize=0;
                        for(var i=0;i<addImags.length;i++){
                            fileSize=$(addImags)[i].size;
                        }
                        //用户上传的图片
                        var addImags2 =$("#img").prop('files');
                        var fileSize2=0;
                        for(var i=0;i<addImags2.length;i++){
                            fileSize2=$(addImags2)[i].size;
                        }

                        var zonggongdaxiao = fileSize+fileSize2;
                        var Maxzong = 1073741824;
                        if(zonggongdaxiao>Maxzong){
                            $("#daxiaobuneng").show();
                            alert("对不起 文件过大.无法上传");
                            return false;
                        }

                    }
                    //判断结束
                    $("#tougao").attr('disabled',true); //将button变成不可点击
                    var formData = new FormData($( "#upload_form" )[0]);
                    $.ajax({
                        url: 'videoFileTop.sf' ,
                        type: 'POST',
                        data: formData,
                        async: true,  //设置为同步
                        cache: false,
                        contentType: false,
                        processData: false,
                        success: function (returndata) {
                            alert("上传成功");
                            location.reload();

                            //$("#tougao").attr('disabled',false); //将button变成可点击
                        },
                        error: function (returndata) {
                            alert("上传失败");
                        }
                    });
                    $(function (){
                        //iCount获取setInterval句柄
                        var iCount = setInterval(GetBack, 100);
                        function GetBack() {
                            var html = "";
                            $.post("baifenbiAJAX", function(r) {
                                //把json字符串转换为json对象
                                var obj = eval("(" + r + ")");
                                //alert(obj.test);
                                var a=obj.baifenbi;
                                a += 1;
                                /* <div id="none" style="width: 100%;height: 60px;background-color: blue;">
                             <h1>啊开始的空间爱仕达空间</h1>
                             </div> */
                                //得到DIV
                                html += "<div id='none2' style='width: "+a+"%;height: 60px;background-color: #B1D632;'>";
                                html += "<h1>"+a+"%</h1>";
                                html += "</div>";
                                //alert(html);
                                $("#none").html(html);
                                html="";
                                //如果标记不为 0  表示上传完成
                                if (obj.tag != 0) {
                                    //alert("进入");
                                    //清除setInterval
                                    clearInterval(iCount);
                                }
                            });
                        }
                    });
                });


            </script>









    </div>>

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