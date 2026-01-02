package com.sf.Maping;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import javax.annotation.PostConstruct;
import jakarta.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sf.entity.*;
import com.sf.service.Audioservice;
import com.sf.service.Videoservice;
import com.sf.service.impl.*;
import ws.schild.jave.info.MultimediaInfo;
import ws.schild.jave.MultimediaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.sf.chaxun.Callingmethod;
import com.sf.chaxun.TestMain;
import com.sf.jingtai.JspToHtml;
import com.sf.lanjieqi.Auth;
import com.sf.service.MessageService;
import com.sf.service.UserListService;
import com.sf.tool.GetDataTime;
import com.sf.tool.GetUUID;
import com.sf.entity.AudioExtractionProgress;

import com.sf.tool.GetUUID;

@Controller
public class T {
    
    // 静态变量用于跟踪批量提取音频进度
    private static AudioExtractionProgress progress = new AudioExtractionProgress();
    private static boolean isProcessing = false;

    @Autowired
    Addserviceimpl addserviceimpl;

    @Autowired
    UserListService userListService;

    @Autowired
    MessageServiceImpl messageServiceImpl;
    @Autowired
    DanmuServiceimpl danmuServiceimpl;
    	
    @Autowired
    Audioservice audioservice;

    @PostConstruct
    public void init(){
        System.out.println("我只会被调用一次");
        // new JspToHtml().main(null);//这个是静态页面
        TestMain sf = new TestMain();
        sf.main(null);
        //int num = Callingmethod.count;

    }


    @RequestMapping("index.sf")
    public String test() {
        return "forward:/logoone.sf";

        //return "redirect:/abc/default.html";  //跳转
        //return "forward:/abc/default.html";   //前进
    }



    // 将数据库里面所有的视频查询出来 发送到首页面
    @RequestMapping("logoone.sf")
    public ModelAndView logoone(HttpServletRequest request,HttpServletResponse response) {
        int countJava=videoserviceimpl.videotypecount(1);
        int countCpp=videoserviceimpl.videotypecount(2);
        int countPython=videoserviceimpl.videotypecount(3);
        List<videoEntity> videoEntityList = userListServiceImpl.videolist("1");// 1 为Java
        Map model = new HashMap();
        List<videoPushEntity> listJava=new ArrayList<>();
        for(videoEntity video:videoEntityList){
            userEntity user=userListServiceImpl.userseachByUserid(video.getUser_id());
            videoPushEntity videoPush=new videoPushEntity(video,user.getUserMingzi());
            listJava.add(videoPush);
        }

        model.put("list", listJava);

        List<videoEntity> videoEntityList2 = userListServiceImpl.videolist("2");// 2 为C++
        List<videoPushEntity> listCpp=new ArrayList<>();
        for(videoEntity video:videoEntityList2){
            userEntity user=userListServiceImpl.userseachByUserid(video.getUser_id());
            videoPushEntity videoPush=new videoPushEntity(video,user.getUserMingzi());
            listCpp.add(videoPush);
        }

        model.put("list2", listCpp);
        List<videoEntity> videoEntityList3 = userListServiceImpl.videolist("3");// 3 为Python
        List<videoPushEntity> listPython=new ArrayList<>();
        for(videoEntity video:videoEntityList3){
            userEntity user=userListServiceImpl.userseachByUserid(video.getUser_id());
            videoPushEntity videoPush=new videoPushEntity(video,user.getUserMingzi());
            listPython.add(videoPush);
        }

        model.put("list3", listPython);
        // 随机查6条数据出来
        List<videoEntity> fuck=videoserviceimpl.searchVidetoptj(0,6);

        model.put("fuck", fuck);//		List<videoEntity> tjvideo1=videoserviceimpl.searchVideotj("1",7);
//		List<videoEntity> tjvideo2=videoserviceimpl.searchVideotj("2",7);
//		List<videoEntity> tjvideo3=videoserviceimpl.searchVideotj("3",7);
//
        //model.put("fuck",fuck);
//		model.put("tjlist1", tjvideo1);
//		model.put("tjlist2", tjvideo2);
//		model.put("tjlist3", tjvideo3);
        /*
         * List<videoEntity> test = userListServiceImpl.videolistimit5();
         * for(videoEntity ts:test){ System.out.println("测试是否查询出来");
         * System.out.println(ts.getVideoName());
         * System.out.println(ts.getVideoTime()); } 可以查询出这么多条记录
         */
        /* System.out.println("测试"); */
/*
		try {
			request.getRequestDispatcher("/static/adminjs/index.html").forward(request,response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        model.put("countdh", countJava);
        model.put("countmad", countCpp);
        model.put("countdm", countPython);

        return new ModelAndView("index", model);

    }

    @RequestMapping(value = "searchResult.sf",method = RequestMethod.POST)
    public ModelAndView searchResult(HttpServletRequest request,HttpServletResponse response,String keyword)throws IOException {
        // request.setCharacterEncoding("UTF-8");
        // String keyword=request.getParameter("keyword");
        // keyword= new String (keyword.getBytes("iso-8859-1"), "UTF-8") ;
        System.out.println("keyword 是"+keyword);

        List<videoEntity> listsearch=videoserviceimpl.searchVideoByName(keyword);
        // List<videoEntity> list = userListServiceImpl.videolist("1");// 1 为动画mad
        Map model = new HashMap();
        model.put("count",listsearch.size());

        model.put("list", listsearch);
        List<videoEntity> list2 = userListServiceImpl.videolist("2");// 2
        model.put("list2", list2);
        List<videoEntity> list3 = userListServiceImpl.videolist("3");// 3
        model.put("list3", list3);
        // 随机查6条数据出来
        // List<videoEntity> fuck = userListServiceImpl.videolistimit6MAD();
        List<videoEntity> videoEntityList=videoserviceimpl.searchVidetoptj(0,6);

        model.put("fuck", videoEntityList);
        /*
         * List<videoEntity> test = userListServiceImpl.videolistimit5();
         * for(videoEntity ts:test){ System.out.println("测试是否查询出来");
         * System.out.println(ts.getVideoName());
         * System.out.println(ts.getVideoTime()); } 可以查询出这么多条记录
         */
        /* System.out.println("测试"); */
/*
		try {
			request.getRequestDispatcher("/static/adminjs/index.html").forward(request,response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

        return new ModelAndView("searchResult", model);

    }



    // AJAX 提交
    @RequestMapping(value = "ajaxTuiJian", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody void ajaxTuiJian(HttpServletRequest request, HttpServletResponse response,int newnextvideo) throws IOException {
        //List<videoEntity> list = userListServiceImpl.videolistimit7();
        List<videoEntity> list=videoserviceimpl.searchVideonew("1",newnextvideo,7);
        // 设置编码
        if(list==null || list.size()==0){
            list=videoserviceimpl.searchVideonew("1",newnextvideo,-1);

        }
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String sbb = gson.toJson(list);
        out.write(sbb);
    }

    // AJAX 提交
    @RequestMapping(value = "ajaxTuiJian2", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody void webajax2(HttpServletRequest request, HttpServletResponse response,int newnextvideo) throws IOException {
        //List<videoEntity> list = userListServiceImpl.videolistimit5MAD();
        List<videoEntity> list=videoserviceimpl.searchVideonew("2",newnextvideo,7);
        if(list==null || list.size()==0){
            list=videoserviceimpl.searchVideonew("2",newnextvideo,-1);

        }
        // 设置编码
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String sbb = gson.toJson(list);
        out.write(sbb);
    }

    // AJAX 提交
    @RequestMapping(value = "ajaxTuiJian3", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody void webajax3(HttpServletRequest request, HttpServletResponse response,int newnextvideo) throws IOException {
        //List<videoEntity> list = userListServiceImpl.videolistimit5MAD();
        List<videoEntity> list=videoserviceimpl.searchVideonew("3",newnextvideo,7);
        if(list==null || list.size()==0){
            list=videoserviceimpl.searchVideonew("3",newnextvideo,-1);

        }
        // 设置编码
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String sbb = gson.toJson(list);
        out.write(sbb);
    }


    @RequestMapping(value = "shuaxin", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody void shuaxin(HttpServletRequest request, HttpServletResponse response,int videotype) throws IOException {
        List<videoEntity> videoEntityList = userListServiceImpl.videolistimi8MAD(videotype);
        //List<videoEntity> list=videoserviceimpl.searchVideonew("1",videotype,7);
        List<videoPushEntity> list=new ArrayList<>();
        for(videoEntity video:videoEntityList){
            userEntity user=userListServiceImpl.userseachByUserid(video.getUser_id());
            videoPushEntity videoPush=new videoPushEntity(video,user.getUserMingzi());
            list.add(videoPush);
        }
        // 设置编码
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String sbb = gson.toJson(list);
        out.write(sbb);
    }

    @RequestMapping(value = "tj", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody void tj(HttpServletRequest request, HttpServletResponse response,int tjnum) throws IOException {
        //List<videoEntity> list = userListServiceImpl.videolistimi8MAD();
        //List<videoEntity> list=videoserviceimpl.searchVideonew("1",videotype,7);
        List<videoEntity> videoEntityList=videoserviceimpl.searchVidetoptj(tjnum,6);
        if(videoEntityList==null || videoEntityList.size()==0){
            videoEntityList=videoserviceimpl.searchVidetoptj(tjnum-6,-1);

        }
        List<videoPushEntity> list=new ArrayList<>();
        for(videoEntity video:videoEntityList){
            userEntity user=userListServiceImpl.userseachByUserid(video.getUser_id());
            videoPushEntity videoPush=new videoPushEntity(video,user.getUserMingzi());
            list.add(videoPush);
        }
        // 设置编码
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String sbb = gson.toJson(list);
        out.write(sbb);
    }



    @Autowired
    private  SupportServiceimpl supportServiceimpl;
    @RequestMapping(value = "dianzhan", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody void dianzhan(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //List<videoEntity> list = userListServiceImpl.videolistimit7();
        // 设置编码
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String userName = (String) request.getSession().getAttribute("userName");
        userEntity user = userListServiceImpl.userlist(userName);
        String user_id = user.getUserID();
        String video_id = request.getParameter("video_id");
        StringBuffer sb = new StringBuffer("");
        if(userName==null || userName.equals("")) {
            sb.append("{");
            sb.append("\"praiseflag\":2");
            sb.append("}");
        }
        if(user_id != null&&!user_id.equals("")&&video_id!=null&&!video_id.equals("")){
            boolean result=	supportServiceimpl.isSupported(user_id,Integer.parseInt(video_id));
            if(result){
                supportServiceimpl.undosupport(user_id,Integer.parseInt(video_id));
                videoserviceimpl.supportSubOne(Integer.parseInt(video_id));
                sb.append("{");
                sb.append("\"praiseflag\":1");
                sb.append("}");
            }else {
                SupportEntity supportEntity=new SupportEntity();
                supportEntity.setVideo_id(Integer.parseInt(video_id));
                supportEntity.setUser_id(user_id);

                System.out.println("点赞插入"+supportServiceimpl.InsertSupport(supportEntity));
                videoserviceimpl.supportPlusOne(Integer.parseInt(video_id));
                sb.append("{");
                sb.append("\"praiseflag\":0");
                sb.append("}");
            }
        }
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        //System.out.println();
        out.println(sb.toString());
        out.flush();
        out.close();
//		Gson gson = new Gson();
//		String sbb = gson.toJson(list);
//		out.write(sbb);
    }
    @Autowired
    private  CollectVideoServiceimpl collectVideoServiceimpl;

    @RequestMapping(value = "shouchang", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody void shouchang(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //List<videoEntity> list = userListServiceImpl.videolistimit7();
        // 设置编码
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String userName = (String) request.getSession().getAttribute("userName");
        StringBuffer sb = new StringBuffer("");
        if(userName==null || userName.equals("")) {
            sb.append("{");
            sb.append("\"praiseflag\":2");
            sb.append("}");
            PrintWriter out = response.getWriter();
            //System.out.println();
            out.println(sb.toString());
            out.flush();
            out.close();
        }

        userEntity user = userListServiceImpl.userlist(userName);
        String user_id = user.getUserID();
        String video_id = request.getParameter("video_id");


        if(user_id != null&&!user_id.equals("")&&video_id!=null&&!video_id.equals("")){
            boolean result=	collectVideoServiceimpl.isCollected(user_id,Integer.parseInt(video_id));
            if(result){
                collectVideoServiceimpl.undoCollect(user_id,Integer.parseInt(video_id));
                //collectVideoServiceimpl(Integer.parseInt(video_id));
                sb.append("{");
                sb.append("\"praiseflag\":1");
                sb.append("}");
            }else {
                CollectVideoEnity collectVideoEnity=new CollectVideoEnity();
                collectVideoEnity.setVideo_id(Integer.parseInt(video_id));
                collectVideoEnity.setUser_id(user_id);
                collectVideoServiceimpl.InsertCollected(collectVideoEnity);

                System.out.println("收藏点赞插入");
                //videoserviceimpl.supportPlusOne(Integer.parseInt(video_id));
                sb.append("{");
                sb.append("\"praiseflag\":0");
                sb.append("}");
            }
        }
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        //System.out.println();
        out.println(sb.toString());
        out.flush();
        out.close();
//		Gson gson = new Gson();
//		String sbb = gson.toJson(list);
//		out.write(sbb);
    }

















    @Autowired
    FollowServiceimpl followServiceimpl;
    @RequestMapping(value = "follow", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody void follow(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //List<videoEntity> list = userListServiceImpl.videolistimit7();
        // 设置编码
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String userName = (String) request.getSession().getAttribute("userName");
        userEntity user = userListServiceImpl.userlist(userName);
        String user_id = user.getUserID();
        String auth_id = request.getParameter("auth_id");
        StringBuffer sb = new StringBuffer("");
        if(userName==null || userName.equals("")) {
            sb.append("{");
            sb.append("\"praiseflag\":2");
            sb.append("}");
        }
        if(user_id != null&&!user_id.equals("")&&auth_id!=null&&!auth_id.equals("")){
            boolean result=	followServiceimpl.isFollowed(user_id,auth_id);
            if(result){
                followServiceimpl.undoFollow(user_id,auth_id);
                sb.append("{");
                sb.append("\"praiseflag\":1");
                sb.append("}");
            }else {
                //SupportEntity supportEntity=new SupportEntity();
                //supportEntity.setVideo_id(Integer.parseInt(auth_id));
                //supportEntity.setUser_id(user_id);
                FollowEntity followEntity=new FollowEntity();
                followEntity.setFollower(user_id);
                followEntity.setFollowee(auth_id);
                followServiceimpl.InsertFollow(followEntity);
                //System.out.println("点赞插入"+supportServiceimpl.InsertSupport(supportEntity));
                //videoserviceimpl.supportPlusOne(Integer.parseInt(video_id));

                sb.append("{");
                sb.append("\"praiseflag\":0");
                sb.append("}");
            }
        }
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        //System.out.println();
        out.println(sb.toString());
        out.flush();
        out.close();
//		Gson gson = new Gson();
//		String sbb = gson.toJson(list);
//		out.write(sbb);
    }



    // querendingdan.sf

    @RequestMapping(value = "querendingdan1.sf", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody void querendingdan(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String userName = (String) request.getSession().getAttribute("userName");
        List<userEntity> user = userListServiceImpl.userlistUserName(userName);
        // user.getUserID();
        // 设置编码
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String sbb = gson.toJson(user);
        out.write(sbb);
    }

    @RequestMapping(value = "testsf.sf", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public String testsf(HttpServletResponse res) {
        boolean bl = addserviceimpl.login();
        if (bl) {
            System.out.println("成功");
        } else {
            System.out.println("失败");
        }

        return "shouye";

    }

    @RequestMapping("zhuce.sf")
    public String zhuce(HttpServletRequest request) {
        // 随机生成一个4位数验证码
        int num = GetUUID.yanzma();
        // 将int 转换为 String
        String yanzheng = "";
        yanzheng = String.valueOf(num);
        // 将验证码放入到session里面....暂时想不到其他办法
        request.getSession().setAttribute("yanzhengma", yanzheng);
        return "zhuce";

    }

    @Auth(validate = false)
    @RequestMapping("login.sf")
    public String login() {
        return "loginnew";

    }

    // 修改支付密码界面
    @RequestMapping("Tiaozhuanshouye.sf")
    public String Tiaozhuanshouye() {

        return "shouye";

    }

    // 信息修改界面
    @RequestMapping("User_full_information.sf")
    public ModelAndView User_full_information(HttpServletRequest request) {

        // 得到登录用户的名字
        String userName = (String) request.getSession().getAttribute("userName");
        userEntity user = userListServiceImpl.userlist(userName);
        Map model = new HashMap();
        String user_id = user.getUserID();
        List<videoEntity> videoEntityList=videoserviceimpl.selectVideo_by_user_id(user_id);
        model.put("user", user);// userlist是个Arraylist之类的
        model.put("videoList", videoEntityList);
        // System.out.println(user.getUserRMB());

        // return "User_full_information";
        return new ModelAndView("User_full_information", model);

    }


    @RequestMapping("mycollect.sf")
    public ModelAndView mycollect(HttpServletRequest request) {

        // 得到登录用户的名字
        String userName = (String) request.getSession().getAttribute("userName");
        userEntity user = userListServiceImpl.userlist(userName);
        Map model = new HashMap();
        String user_id = user.getUserID();
        List<CollectVideoEnity> collectVideoEnities=collectVideoServiceimpl.selectFollow_byUser_id(user_id);
        List<videoEntity> videoEntityList=new ArrayList<>();
        for(CollectVideoEnity collectVideoEnity:collectVideoEnities){
            videoEntity myvideo=videoserviceimpl.readVideoByVid(collectVideoEnity.getVideo_id()).get(0);
            videoEntityList.add(myvideo);

        }


        model.put("user", user);// userlist是个Arraylist之类的
        model.put("videoList", videoEntityList);
        // System.out.println(user.getUserRMB());

        // return "User_full_information";
        return new ModelAndView("mycollect", model);

    }














    @RequestMapping("user_info")
    public ModelAndView user_info(HttpServletRequest request) {

        // 得到登录用户的名字
        String user_id = (String) request.getParameter("user_id");
        userEntity user = userListServiceImpl.userseachByUserid(user_id);
        Map model = new HashMap();
        List<videoEntity> videoEntityList=videoserviceimpl.selectVideo_by_user_id(user_id);
        model.put("user", user);// userlist是个Arraylist之类的
        model.put("videoList", videoEntityList);
        // System.out.println(user.getUserRMB());

        // return "User_full_information";
        return new ModelAndView("user_info", model);

    }





    @RequestMapping("userfollow.sf")
    public ModelAndView userfollow(HttpServletRequest request)throws IOException  {

        //得到登录用户的名字
        request.setCharacterEncoding("UTF-8");
        String user_id = (String) request.getParameter("user_id");
        userEntity user=userListServiceImpl.userseachByUserid(user_id);

        List<FollowEntity> followEntityList=followServiceimpl.selectFollow_byFollower(user_id);
        List<userEntity> followlist=new  ArrayList<userEntity>();
        for(FollowEntity followEntity:followEntityList){
            userEntity followee=userListServiceImpl.userseachByUserid(followEntity.getFollowee());
            followlist.add(followee);
        }
        Map model = new HashMap();

        //List<videoEntity> videoEntityList=videoserviceimpl.selectVideo_by_user_id(user_id);
        model.put("user", user);// userlist是个Arraylist之类的
        model.put("follows",followlist);
        // System.out.println(user.getUserRMB());

        // return "User_full_information";
        return new ModelAndView("followList", model);

    }





    @RequestMapping("userfollow2.sf")
    public ModelAndView userfollow2(HttpServletRequest request)throws IOException  {

        // 得到登录用户的名字
        request.setCharacterEncoding("UTF-8");
        String user_id = (String) request.getParameter("user_id");
        userEntity user=userListServiceImpl.userseachByUserid(user_id);

        List<FollowEntity> followEntityList=followServiceimpl.selectFollow_byFollower(user_id);
        List<userEntity> followlist=new  ArrayList<userEntity>();
        for(FollowEntity followEntity:followEntityList){
            userEntity followee=userListServiceImpl.userseachByUserid(followEntity.getFollowee());
            followlist.add(followee);
        }
        Map model = new HashMap();

        //List<videoEntity> videoEntityList=videoserviceimpl.selectVideo_by_user_id(user_id);
        model.put("user", user);// userlist是个Arraylist之类的
        model.put("follows",followlist);
        // System.out.println(user.getUserRMB());

        // return "User_full_information";
        return new ModelAndView("followList2", model);

    }










    @Autowired
    Update_login_password_Service_Impl update_login_password_Service_Impl;

    // 修改登录密码界面
    @RequestMapping("Update_login_password.sf")
    public String Update_login_password(String passWord, String newpassWord, String newpassWord2,
                                        HttpServletRequest request) {
        String userName = (String) request.getSession().getAttribute("userName");
        if (passWord != null || newpassWord != null || newpassWord2 != null) {
            if (newpassWord.equals(newpassWord2)) {
                boolean bl = update_login_password_Service_Impl.Update_login_password(userName, passWord, newpassWord);
                if (bl) {
                    return "User_full_information";
                } else {
                    request.setAttribute("PassWordErro", "对不起,旧密码输入有误!");
                }
            } else {
                request.setAttribute("PassWordErro", "两次密码输入有误!");
            }
        }
        return "Update_login_password";

    }


    @RequestMapping("updateinfo.sf")
    public String updateinfo(HttpServletRequest request,String userMingzi,String sex,String showme){
        String userName = (String) request.getSession().getAttribute("userName");
        userEntity user = userListServiceImpl.userlist(userName);


        update_login_password_Service_Impl.Update_info(userMingzi,sex,showme,user.getUserID());
        return "redirect:/User_full_information.sf";
    }

    // 修改邮箱界面
    @RequestMapping("Update_email.sf")
    public String Update_email(HttpServletRequest request, String emial, String newemial) {
        String userName = (String) request.getSession().getAttribute("userName");
        if (userName != null || emial != null || newemial != null) {
            boolean bl = update_login_password_Service_Impl.Update_login_Emial(userName, emial, newemial);
            if (bl) {
                return "User_full_information";
            } else {
                request.setAttribute("PassWordErro2", "对不起,原邮箱输入错误!");
            }
        } else {
            request.setAttribute("PassWordErro", "输入不能为空!");
        }

        return "Update_email";

    }

    // 修改手机界面
    @RequestMapping("Update_Phone.sf")
    public String Update_Phone(HttpServletRequest request, String userPhone, String newuserPhone) {
        // 得到session里面的用户名
        String userName = (String) request.getSession().getAttribute("userName");
        if (userName != null || userPhone != null || newuserPhone != null) {
            boolean bl = update_login_password_Service_Impl.Update_login_Phone(userName, userPhone, newuserPhone);
            if (bl) {
                return "User_full_information";
            } else {
                request.setAttribute("PassWordErro3", "对不起,原手机号码错误!");
            }
        } else {
            request.setAttribute("PassWordErro3", "输入不能为空!");
        }
        return "Update_Phone";// User_full_information.sf

    }

    // 修改支付密码界面
    @RequestMapping("Update_PayPassword.sf")
    public String Update_PayPassword(String paypassword, String newpaypassword, String newpaypassword2,
                                     HttpServletRequest request) {
        String userName = (String) request.getSession().getAttribute("userName");
        if (paypassword != null || newpaypassword != null || newpaypassword2 != null) {
            if (newpaypassword.equals(newpaypassword2)) {
                boolean bl = update_login_password_Service_Impl.Update_login_payPassword(userName, paypassword,
                        newpaypassword);
                if (bl) {
                    return "User_full_information";
                } else {
                    request.setAttribute("PassWordErro5", "对不起,旧密码输入有误!");
                }
            } else {
                request.setAttribute("PassWordErro5", "两次密码输入有误!");
            }
        }
        return "Update_PayPassword";

    }

    @Autowired
    UserListServiceImpl userListServiceImpl;

    // 用户查看所有信息界面
    @RequestMapping("Information.sf")
    public ModelAndView Information(HttpServletRequest request) {
        // 得到登录用户的名字
        String userName = (String) request.getSession().getAttribute("userName");
        userEntity user = userListServiceImpl.userlist(userName);
        Map model = new HashMap();

        model.put("user", user);// userlist是个Arraylist之类的

        return new ModelAndView("Information", model);

    }

    // 用户银行卡界面
    @RequestMapping("Bank_Card.sf")
    public String Bank_Card() {
        // System.out.println("测试是否进入此方法");
        return "Bank_Card";

    }

    // 收货地址jsp界面
    @RequestMapping("Delivery_address.sf")
    public String Delivery_address() {
        // System.out.println("测试是否进入此方法");

        return "Delivery_address";

    }

    // String usermingzi,String userPhone,String userAddress
    // 修改收货地址
    @RequestMapping("updateDelivery_address.sf")
    public String updateDelivery_address(String usermingzi, String userPhone, String userAddress,
                                         HttpServletRequest request) {
        /*
         * System.out.println(usermingzi); System.out.println(userPhone);
         * System.out.println(userAddress);
         */
        String sessionName = (String) request.getSession().getAttribute("userName");
        boolean bl = update_login_password_Service_Impl.Update_Addred(sessionName, userAddress, usermingzi, userPhone);
        String tishi = "";
        if (bl) {
            tishi = "修改成功";
            request.setAttribute("tishi", tishi);
        } else {
            tishi = "修改失败...请检查是否输入正确";
            request.setAttribute("tishi", tishi);
        }
        return "Delivery_address";

    }

    @RequestMapping("userHand.sf")
    public String userHand(HttpServletRequest request) {
        String userName = (String) request.getSession().getAttribute("userName");
        String path2 = (String) request.getSession().getAttribute("fuckyou");
        userEntity user = userListServiceImpl.userlist(userName);

        // System.out.println(user.getUserHand());
        String userHand = user.getUserHand();
        String newuserHand = path2;

        // System.out.println(userName+newuserHand);

        boolean bl = update_login_password_Service_Impl.Update_login_hand(userName, userHand, newuserHand);

        if (bl) {
            System.out.println("成功");
        } else {
            System.out.println("失败");
        }
        return "User_full_information";

    }
    @Autowired
    Videoserviceimpl videoserviceimpl;
    @RequestMapping("video.sf")
    public ModelAndView video(String dizhi, HttpServletRequest request, String shipingID) {
        /*
         * System.out.println(dizhi);
         * System.out.println("===============================");
         */
        // 获得地址
        request.setAttribute("dizhi", dizhi);

        // System.out.println("===============================");
        Map model = new HashMap();

        // System.out.println(shipingID);//拿到视频地址
        request.setAttribute("shipingID", shipingID);
        videoserviceimpl.lookTimesPlusOne(Integer.parseInt(shipingID));
        // 根据视频ID查询出 此视频的所有留言
        List<messageEntity> messagelist = userListService.messagelist(shipingID);
        List<videoEntity> videolist= videoserviceimpl.readVideoByVid(Integer.parseInt(shipingID));
        userEntity auth=null;
        String videotype=videolist.get(0).getVideocAtegory();
        if(videolist.get(0)!=null){
            videoEntity v=videolist.get(0);
            //videotype=v.getVideocAtegory();
            String isadmin=request.getParameter("isadmin");
            if(isadmin!=null&&isadmin.equals("1")){
                v.setIspass(1);
                v.setIscheck(1);
            }
            model.put("videoEntity",v);
            videoEntity myvideo=videolist.get(0);
            auth=userListServiceImpl.userseachByUserid(myvideo.getUser_id());
            model.put("auth",auth);
        }
        String userName = (String) request.getSession().getAttribute("userName");
        if(userName!=null&&!userName.equals("")){
            userEntity user = userListServiceImpl.userlist(userName);
            if(supportServiceimpl.isSupported(user.getUserID(),Integer.parseInt(shipingID))){
                model.put("isdianzhan", "1");
            }else {
                model.put("isdianzhan", "0");
            }

            if(followServiceimpl.isFollowed(user.getUserID(),auth.getUserID())){
                model.put("isguanzhu", "1");
            }else {
                model.put("isguanzhu", "0");
            }
            if(collectVideoServiceimpl.isCollected(user.getUserID(),Integer.parseInt(shipingID))){
                model.put("isshouchang", "1");
            }else {
                model.put("isshouchang", "0");
            }

        }else {
            model.put("isdianzhan", "2");
            model.put("isguanzhu", "2");
            model.put("isshouchang", "2");
        }

        List<videoEntity> tjlist=videoserviceimpl.searchVideotj(videotype,9);
        List<videoPushEntity> mytjlist=new ArrayList<>();
        for(videoEntity video:tjlist){
            userEntity user=userListServiceImpl.userseachByUserid(video.getUser_id());
            videoPushEntity videoPush=new videoPushEntity(video,user.getUserMingzi());
            mytjlist.add(videoPush);
        }
        System.out.println("mytjlist size"+mytjlist.size());
        System.out.println("videotype:"+videotype);
        model.put("mytjlist", mytjlist);


        for (messageEntity message : messagelist) {
            message.getMessageuserName();// 得到用户名

            // 根据每个用户名查询出每个用户对应的头像地址
            // userEntity userent =
            // userListService.userlist(message.getMessageuserName());
            // System.out.println(userent.getUserName());
            // model.put("userent",userent);//userlist是个Arraylist之类的
            model.put("messagelist", messagelist);// userlist是个Arraylist之类的

        }

        return new ModelAndView("Video", model);

        // return "Video";

    }

    @RequestMapping("checkvideo")
    public String checkvideo(HttpServletRequest request,HttpServletResponse response){
        int video_id=Integer.parseInt(request.getParameter("video_id"));
        int s=Integer.parseInt(request.getParameter("s"));
        videoserviceimpl.UpdateVideoCheck(s,video_id);
        return "redirect:Houtai.sf";
    }


    @RequestMapping("delmessage")
    public String delMessage(HttpServletRequest request,HttpServletResponse response){
        String message_id=request.getParameter("id");
        System.out.println("删除的ID为：" +message_id);
        messageServiceImpl.messagedel(message_id);
        return "redirect:Houtai.sf";
    }


    @RequestMapping("checkmessage")
    public String checkMessage(HttpServletRequest request,HttpServletResponse response){
        String message_id=request.getParameter("id");
        //System.out.println("删除的ID为：" +message_id);
        messageServiceImpl.messagecheck(message_id);
        return "redirect:Houtai.sf";
    }

















    @Autowired
    MessageService messageService;








    // 留言- - 测试
    @RequestMapping("test.sf")
    public String Test() {

        messageEntity user = new messageEntity();
        user.setMessage("1");
        user.setMessageID("1");
        user.setMessageTime("1");
        user.setMessageuserID("1");
        user.setMessageuserName("1");
        user.setMessagevideoID("1");

        boolean bl = messageService.message(user);
        if (bl) {
            System.out.println("可以使用留言");
        } else {
            System.out.println("不可以使用留言");
        }
        return "";

    }

    // 留言- - 测试
    /*
     * @RequestMapping("test2.sf") public String Test2() { List<messageEntity>
     * list=userListService.messagelist("11"); for(messageEntity li:list){
     * System.out.println(li.getMessageuserName()); } return "";
     *
     * }
     */

    // 切换账号
    @RequestMapping("exect.sf")
    public String exect(HttpServletRequest request) {

        // System.out.println("切换账号");
        // 将session里面的用户信息全部清空
        request.getSession().removeAttribute("userName");
        return "loginnew";

    }

    @RequestMapping("Houtai.sf")
    public ModelAndView Houtai(HttpServletRequest request) {
        // 设置一个默认从第一条开始查询 只查询出15条记录
        String pageInt = "0";
        int test = Integer.valueOf(pageInt).intValue();
        //System.out.println("userListServiceImpl.userlistpage(test)");
        List<userEntity> list = userListServiceImpl.userlistpage(test);
        //System.out.println("userListServiceImpl.userlistpage(test) after"+list.size());
        /*
         * for(userEntity lis:list){ System.out.println(lis.getUserName()); }
         */

        Map model = new HashMap();
        model.put("list", list);
        List<videoEntity> videoEntityList =videoserviceimpl.selectCheckVideo();
        //System.out.println("有待审核视频数量"+videoEntityList.size());
        List<VideoCheckEntity> videoCheckEntities=new ArrayList<>();
        for(videoEntity video:videoEntityList){
            VideoCheckEntity videoCheckEntity=new VideoCheckEntity();
            videoCheckEntity.setVideoID(video.getVideoID());
            videoCheckEntity.setVideoAddress(video.getVideoAddress());
            videoCheckEntity.setIscheck(video.getIscheck());
            videoCheckEntity.setIspass(video.getIspass());
            videoCheckEntity.setUser_id(video.getUser_id());
            videoCheckEntity.setVideoName(video.getVideoName());
            videoCheckEntity.setUserName(userListServiceImpl.userseachByUserid(video.getUser_id()).getUserMingzi());
            videoCheckEntities.add(videoCheckEntity);
        }
        if(videoCheckEntities.size()!=0){
            model.put("videolist", videoCheckEntities);
            //System.out.println("有待审核视频");
        }
        String start=request.getParameter("s");
        int s=0;
        if(start==null||start=="") s=0;

        else s=Integer.parseInt(start);
        System.out.println("s: "+s);
        List<messageEntity> messageEntities=messageServiceImpl.messagelist(s);
        model.put("s", s+10);
        if(messageEntities.size()==0){
            messageEntities=messageServiceImpl.messagelist(s-10);
            model.put("s", s-10);
        }

        List<MessgaeShow> messgaeShows=new ArrayList<>();
        if(messageEntities != null && !messageEntities.isEmpty()){
            for (messageEntity mess:messageEntities){
                MessgaeShow messgaeShow=new MessgaeShow();
                messgaeShow.setMessage(mess.getMessage());
                messgaeShow.setMessageID(mess.getMessageID());
                messgaeShow.setMessageuserID(mess.getMessageuserID());
                userEntity u=userListServiceImpl.userseachByUserid(mess.getMessageuserID());
                messgaeShow.setMessageuserName(u.getUserMingzi());
                List<videoEntity> videoList = videoserviceimpl.readVideoByVid(Integer.parseInt(mess.getMessagevideoID()));
                if(videoList != null && !videoList.isEmpty()) {
                    videoEntity v1 = videoList.get(0);
                    messgaeShow.setAddress(v1.getVideoAddress());
                    //System.out.println(messgaeShow.getVideoAddress());
                    messgaeShow.setVideoName(v1.getVideoName());
                    messgaeShow.setMessagevideoID(v1.getVideoID()+"");
                    messgaeShows.add(messgaeShow);
                }
            }
        }
        model.put("messages", messgaeShows);
        return new ModelAndView("Houtai", model);

    }

    // AJAX 提交 根据用户名查询
    @RequestMapping(value = "userchaxunmessage1", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody void userchaxunmessage1(HttpServletRequest request, HttpServletResponse response,
                                                 String message) throws IOException {
        String shuaige = message;
        // 得到内容 ajax提交进来
        List<userEntity> list = userListServiceImpl.listmohu("%" + shuaige + "%");

        // 设置编码
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String sbb = gson.toJson(list);
        if (list.size() == 0) {
            sbb = "0";
        }
        out.write(sbb);
    }

    // AJAX 提交 根据用户名查询
    @RequestMapping(value = "userchaxunmessage2", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody void userchaxunmessage2(HttpServletRequest request, HttpServletResponse response,
                                                 String message) throws IOException {
        String shuaige = message;
        // 得到内容 ajax提交进来
        List<userEntity> list = userListServiceImpl.userPhone("%" + shuaige + "%");

        // 设置编码
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String sbb = gson.toJson(list);
        if (list.size() == 0) {
            sbb = "0";
        }
        out.write(sbb);
    }

    // AJAX 提交 根据ID查询
    @RequestMapping(value = "userID.sf", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody void userID(HttpServletRequest request, HttpServletResponse response, String userID)
            throws IOException {
        // 得到内容 ajax提交进来
        List<userEntity> list = userListServiceImpl.userID(userID);

        // 设置编码
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String sbb = gson.toJson(list);
        if (list.size() == 0) {
            sbb = "0";
        }
        out.write(sbb);
    }
    // AJAX 提交 修改用户数据

    @RequestMapping("updateuser.sf")
    public String updateuser(String userID, String userName, String userzhenshiName, String userSex, String passWord,
                             String addr, String userPhone, String userQQ, String userEmial, HttpServletRequest request,
                             HttpServletResponse response) throws IOException {

        String echo = "";
        // 得到内容 ajax提交进来
        /*
         * System.out.println(userID); System.out.println(userName);
         * System.out.println(userzhenshiName); System.out.println(userSex);
         * System.out.println(passWord); System.out.println(addr);
         * System.out.println(userPhone); System.out.println(userQQ);
         */

        // update user set userName = "admin" ,
        // userMingzi = "佘峰T" ,usersex = "男" ,
        // passWord = "123321aaa" , userAddress = "四川省遂宁" ,
        // userPhone = "17583108191" , userQQ = "794799102" ,
        // userEmial = "794799102@qq.com" where
        // userID = "004bcfc8d4bd407bb1a114785539006f"

        userEntity user = new userEntity();
        // 设置编码
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        user.setUserName(userName);
        user.setUserMingzi(userzhenshiName);
        user.setUsersex(userSex);
        user.setPassWord(passWord);
        user.setUserAddress(addr);
        user.setUserPhone(userPhone);
        user.setUserQQ(userQQ);
        user.setUserEmial(userEmial);
        user.setUserID(userID);
        boolean bl = update_login_password_Service_Impl.Update_user(user);
        if (bl) {
            echo = "修改成功";
            System.out.println("成功");
        } else {
            echo = "修改失败,请联系管理员";
        }
        request.setAttribute("echo", echo);
        return "forward:/Houtai.sf";

    }

    // 跳转到小黑屋界面.... 测试
    @RequestMapping("xiaoheiwu.sf")
    public ModelAndView xiaoheiwu(HttpServletRequest request) {
        // 将用户表里面被拉黑的用户全部查询出来
        List<userEntity> user = userListServiceImpl.xiaoheiwu("异常");
        Map model = new HashMap();
        model.put("user", user);
        return new ModelAndView("xiaoheiwu", model);

    }

    // 将数据库里面所有的视频查询出来 发送到首页面
    @RequestMapping("testshabi.sf")
    public String testshabi(HttpServletRequest request) {

        /*
         * dangqianye= "0"; int dangqianye2 = Integer.parseInt(dangqianye);
         * List<videoEntity> list=userListServiceImpl.Pagevideolist("1",
         * dangqianye2); Map model =new HashMap(); model.put("list", list);
         */
        // request.setAttribute("test", "测试");
        int tag1 = userListServiceImpl.videocoun("1");
        // System.out.println("视频一共有"+tag1);
        request.setAttribute("tag1", tag1);

        int tag4 = 12;
        // 每页显示多少
        request.setAttribute("tag4", tag4);

        return "DisplayVideo";

    }

    // AJAX 提交
    @RequestMapping(value = "ajaxtijiao1.sf", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody void ajaxtijiao(HttpServletRequest request, HttpServletResponse response, String State,
                                         String dangqianye, int meiyexianshiduoshaoge) throws IOException {
        /* List<videoEntity> list = userListServiceImpl.videolistimit5(); */
        int dangqianye2 = Integer.parseInt(dangqianye);
        List<videoEntity> list = userListServiceImpl.Pagevideolist(State, dangqianye2, meiyexianshiduoshaoge);// State
        // 标记

        // 设置编码
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String sbb = gson.toJson(list);
        out.write(sbb);
    }

    // AJAX 提交
    @RequestMapping(value = "gouwuchet.sf", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody void gouwuche(HttpServletRequest request, HttpServletResponse response, String userName,
                                       String girdsName, String girdsimg, String girdsjiage, String girdsID) throws IOException {
        /*
         * System.out.println(girdsID);System.out.println(userName);System.out.
         * println(girdsName);System.out.println(girdsimg);System.out.println(
         * girdsjiage);
         */
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCartID(GetUUID.getUUID());
        shoppingCart.setShoopingID(girdsID);
        shoppingCart.setShoopingImg(girdsimg);
        shoppingCart.setShoopingjiage(girdsjiage);
        shoppingCart.setShoopingName(girdsName);
        shoppingCart.setUserName(userName);
        boolean bl = messageServiceImpl.Shoppingcart(shoppingCart);

        String list = "";
        if (bl) {
            list = "添加成功";
        } else {
            list = "添加失败";
        }

        // 设置编码
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String sbb = gson.toJson(list);
        out.write(sbb);
    }

    // 商品详情页面 请求
    @RequestMapping("gridspay.sf")
    public String gridspay(HttpServletRequest request, String girdsID) {
        gridsEntity gridslist = userListServiceImpl.gridsIDlist(girdsID);
        request.setAttribute("gridslist", gridslist);

        return "gridspay";

    }

    // 确认订单界面
    // Confirmorder.jsp
    @RequestMapping("Confirmorder.sf")
    public String Confirmorder(HttpServletRequest request, String girdsID, String gridsName, int gridskucun,
                               String gridsjiage, String gridsimg) {
        // 乱码问题
        try {
            gridsName = new String(gridsName.getBytes("ISO-8859-1"), "utf-8");

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            System.out.println("Confirmorder.sf" + "这里的乱码解决失败");
        }

        gridsEntity grids = new gridsEntity();
        grids.setGridsID(girdsID);
        grids.setGirdsimg(gridsimg);
        grids.setGirdsjiage(gridsjiage);
        grids.setGirdsName(gridsName);
        grids.setGirdskucun(gridskucun);
        // System.out.println(grids.getGirdsName());

        request.setAttribute("grids", grids);

        return "Confirmorder";
    }

    @RequestMapping("gouwuche.sf")
    public String gouwuche(HttpServletRequest request) {
        String userName = (String) request.getSession().getAttribute("userName");
        List<ShoppingCart> user = userListServiceImpl.shoppingcart(userName);

        request.setAttribute("user", user);
        return "gouwuche";

    }

    // AJAX 提交
    @RequestMapping(value = "delectgouwuche.sf", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public String delectgouwuche(HttpServletRequest request, HttpServletResponse response, String cartID)
            throws IOException {
        // System.out.println(cartID);
        boolean bl = update_login_password_Service_Impl.delectcartID(cartID);
        String tishi = "";
        if (bl) {
            // System.out.println("删除成功");
            tishi = "删除成功";
            request.setAttribute("tishi", tishi);
        } else {
            // System.out.println("删除失败");
            tishi = "已经删除,查找不到此商品";
            request.setAttribute("tishi", tishi);
        }
        return "forward:/gouwuche.sf";

    }

    @RequestMapping("Shopping.sf")
    public ModelAndView Shopping(HttpServletRequest request) {
        List<gridsEntity> gridslist = userListServiceImpl.gridslist();

        Map model = new HashMap();
        model.put("gridslist", gridslist);

        return new ModelAndView("Shoppingnew", model);

    }

    @RequestMapping("Order.sf")
    public ModelAndView Order(HttpServletRequest request) {
        String userName = (String) request.getSession().getAttribute("userName");
        // 根据名字查询出所有的订单
        List<ordertableEntity> ordertable = userListServiceImpl.ordertable(userName);

        Map model = new HashMap();
        model.put("ordertable", ordertable);

        return new ModelAndView("Order", model);

    }

    // 全部订单
    @RequestMapping("Adminbackgroundshipment")
    public ModelAndView admin(HttpServletRequest request) {
        List<ordertableEntity> ordertable = userListServiceImpl.ordertablelist();
        Map model = new HashMap();
        model.put("ordertable", ordertable);
        return new ModelAndView("Adminbackgroundshipment", model);
    }

    // 待发货
    @RequestMapping("Shipmentpending")
    public ModelAndView Shipmentpending(HttpServletRequest request) {
        List<ordertableEntity> ordertable = userListServiceImpl.orderStat("1");
        Map model = new HashMap();
        model.put("ordertable", ordertable);
        return new ModelAndView("Shipmentpending", model);
    }

    // 已完成订单
    @RequestMapping("Completedorder")
    public ModelAndView Completedorder(HttpServletRequest request) {
        List<ordertableEntity> ordertable = userListServiceImpl.orderStat("4");
        Map model = new HashMap();
        model.put("ordertable", ordertable);
        return new ModelAndView("Completedorder", model);
    }

    // 待会退的
    @RequestMapping("Returngoods")
    public ModelAndView Returngoods(HttpServletRequest request) {
        List<ordertableEntity> ordertable = userListServiceImpl.orderStat("3");
        Map model = new HashMap();
        model.put("ordertable", ordertable);
        return new ModelAndView("Returngoods", model);
    }

    @RequestMapping("DeletOrder")
    public ModelAndView DeletOrder(HttpServletRequest request) {
        List<ordertableEntity> ordertable = userListServiceImpl.orderStat("5");
        Map model = new HashMap();
        model.put("ordertable", ordertable);
        return new ModelAndView("DeletOrder", model);
    }

    // 跳转到查询界面
    @RequestMapping("Inquiryorder")
    public String Inquiryorder(HttpServletRequest request) {

        return "Inquiryorder";
    }

    // AJAX 查询用户订单
    @RequestMapping(value = "AJAXinquiryorder", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody void AJAXinquiryorder(HttpServletRequest request, HttpServletResponse response, String val)
            throws IOException {
        // 根据用户名查询出此用户下单 String val//这个为用户名
        List<ordertableEntity> ordertable = userListServiceImpl.ordertable(val);
        // user.getUserID();
        // 设置编码
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String sbb = gson.toJson(ordertable);
        out.write(sbb);
    }

    public static int a = 0;

    // 查询出订单表一共多少条记录
    @RequestMapping(value = "countordertable", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody void countordertable(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String tishi = "";
        // 根据用户名查询出此用户下单 String val//这个为用户名
        int ordertable = userListServiceImpl.countordertable();

        if (a == ordertable) {
            tishi = "无新订单";
        } else {
            a = ordertable;
            tishi = "新订单";
        }
        // 设置编码
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String sbb = gson.toJson(tishi);
        out.write(sbb);
    }

    @RequestMapping("AdminBL")
    public String AdminBL(HttpServletRequest request) {

        return "AdminBL";

    }

    /************************ 文件上传 ****************************************/
    @RequestMapping("videoFileTop")
    public String videoFileTop(HttpServletRequest request) {

        return "videoFileTop";

    }

    /***
     * 保存文件
     *
     * @param file
     * @return
     */
    private boolean saveFile(MultipartFile file) {

        return false;
    }

    @RequestMapping("videoFileTop.sf")
    public String videoFileTop(@RequestParam("files") MultipartFile[] files, HttpServletRequest request, String biaoti,
                               String Fruit,String miaoshu)  {
        System.out.println("用户输入的标题为:" + biaoti + Fruit);
        videoEntity myvideo=new videoEntity();
        myvideo.setVideoName(biaoti);
        myvideo.setVideocAtegory(Fruit);
        myvideo.setVideodetail(miaoshu);

        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String userName = (String) request.getSession().getAttribute("userName");
        userEntity user = userListServiceImpl.userlist(userName);
        String user_id = user.getUserID();
        myvideo.setUser_id(user_id);
        myvideo.setIscheck(0);
        myvideo.setIspass(0);
        if (files != null && files.length > 0) {
            // 循环获取file数组中得文件
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                Long timelable = System.currentTimeMillis();
                String lable_time=timelable+"";

                // 保存文件
                System.out.println("正在调用保存方法");
                // 判断文件是否为空
                if (!file.isEmpty()) {
                    try {
                        // new 出一个实体
                        videoTopEntity video = new videoTopEntity();
                        // 放入session中
                        request.getSession().setAttribute("video", video);// 放入到session中

                        System.out.println("文件总大小" + file.getSize());
                        // 文件保存路径 - 修改为直接保存到src目录
                        String srcDir = request.getServletContext().getRealPath("/").replace("target\\bilibili\\", "src\\main\\webapp\\");
                        if (srcDir.contains("target\\")) {
                            srcDir = srcDir.substring(0, srcDir.indexOf("target\\"));
                            srcDir += "src\\main\\webapp\\";
                        }
                        String srcVideoDir = srcDir + "static\\videolook\\";
                        
                        String filePath = srcVideoDir + lable_time + "_" + file.getOriginalFilename();
                        // 如果不是MP4视频文件，则保存到封面图片目录
                        if (!file.getOriginalFilename().contains(".mp4")) {
                            filePath = srcVideoDir + "videolookimg\\" + lable_time + "_" + file.getOriginalFilename();
                        }

                        System.out.println("文件保存路径: " + filePath);

                        // 创建文件对象并确保父目录存在
                        File storeFile = new File(filePath);
                        storeFile.getParentFile().mkdirs(); // 确保目录存在

                        // 获取输入输出流进行文件传输
                        InputStream in = file.getInputStream();
                        OutputStream out = new FileOutputStream(storeFile);

                        // 设置文件信息用于进度跟踪
                        long max = file.getSize(); // 文件总大小
                        video.setFileSize(max);
                        video.setFilename(file.getOriginalFilename());

                        // 剩余大小
                        long other = max;
                        int len = 0;// 读取写入长度
                        // 读写缓冲
                        byte[] b = new byte[1024]; // 增加缓冲区大小以提高性能

                        // 循环从输入流写入到输出流,结束循环是len==-1
                        while ((len = in.read(b)) != -1) {
                            out.write(b, 0, len);
                            other -= len;
                            video.setFileSY(other);

                            // 计算上传进度百分比
                            float zong = (float) max;
                            int shengxia = (int) other;
                            float ii3 = zong - shengxia;// 传了多少
                            if (shengxia != 0) {
                                int baifenbi = (int) ((ii3 / zong) * 100);
                                video.setBaifenbi(baifenbi);
                            }
                        }
                        out.flush();// 刷新
                        out.close();// 关闭
                        in.close();// 关闭
                        video.setTag(1);// 标记为1的时候表示上传成功
                        System.out.println("上传成功");

                        // 根据文件类型设置视频实体属性
                        if(file.getOriginalFilename().contains(".mp4")) {
                            // 设置视频文件路径 - 使用相对于webapp的路径
                            myvideo.setVideoAddress("/static/videolook/" + lable_time + "_" + file.getOriginalFilename());

                            // 使用JAVE库获取视频时长
                            File source = new File(filePath);
                            String videolength = "0:00";

                            try {
                                // 获取多媒体信息
                                MultimediaObject multimediaObject = new MultimediaObject(source);
                                MultimediaInfo m = multimediaObject.getInfo();

                                long ls = m.getDuration();
                                int minute = (int) (ls/60000);
                                int second = (int) ((ls-minute*60000)/1000);
                                videolength = minute+":"+String.format("%02d", second);
                                System.out.println("视频时长: " + videolength);
                                myvideo.setVideoTime(videolength);
                            } catch (Exception e) {
                                System.err.println("提取视频时长失败: " + e.getMessage());
                                // 即使失败也继续处理，设置默认时长
                                myvideo.setVideoTime("0:00");
                            }
                        } else {
                            // 设置封面图片路径
                            myvideo.setVideoImage("/static/videolook/videolookimg/" + lable_time + "_" + file.getOriginalFilename());
                        }

                        // 同时将文件复制到target目录，确保运行时可以访问
                        String targetDir = request.getServletContext().getRealPath("/static/videolook/");
                        String targetFilePath = targetDir + lable_time + "_" + file.getOriginalFilename();
                        if (!file.getOriginalFilename().contains(".mp4")) {
                            targetFilePath = targetDir + "videolookimg/" + lable_time + "_" + file.getOriginalFilename();
                        }

                        File targetFile = new File(targetFilePath);
                        targetFile.getParentFile().mkdirs(); // 确保目录存在

                        // 复制文件到target目录
                        try {
                            java.nio.file.Files.copy(storeFile.toPath(), targetFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                            System.out.println("文件已复制到target目录: " + targetFilePath);
                        } catch (Exception e) {
                            System.err.println("复制文件到target目录失败: " + e.getMessage());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // 设置视频描述并保存到数据库
            myvideo.setVideodetail(miaoshu);
            videoserviceimpl.insertVideo(myvideo);
            
            // 视频上传成功后，提取音频
            if(myvideo.getVideoAddress() != null && myvideo.getVideoAddress().contains(".mp4")) {
                System.out.println("开始自动提取音频...");
                System.out.println("  视频地址: " + myvideo.getVideoAddress());
                
                // 直接使用刚保存的视频信息进行音频提取，使用刚插入的视频ID
                // 由于videoserviceimpl.insertVideo已经更新了myvideo的ID，我们直接使用它
                List<videoEntity> allVideos = videoserviceimpl.selectAllVideos();
                if(allVideos != null && !allVideos.isEmpty()) {
                    // 获取最新上传的视频（ID最大的）
                    videoEntity latestVideo = allVideos.get(0); // 假设selectAllVideos按ID降序排列
                    for(videoEntity video : allVideos) {
                        if(video.getVideoID() > latestVideo.getVideoID()) {
                            latestVideo = video;
                        }
                    }
                    
                    // 检查最新视频是否与刚上传的视频匹配（通过视频地址匹配）
                    if(latestVideo.getVideoAddress() != null && myvideo.getVideoAddress() != null &&
                       latestVideo.getVideoAddress().equals(myvideo.getVideoAddress())) {
                        System.out.println("  开始提取音频，视频ID: " + latestVideo.getVideoID());
                        System.out.println("  视频地址: " + latestVideo.getVideoAddress());
                        
                        // 构建完整的视频文件路径 - 优先使用src目录以符合用户偏好
                        String projectPath = System.getProperty("user.dir");
                        String fullVideoPath = projectPath + "/src/main/webapp" + latestVideo.getVideoAddress();
                        
                        // 检查src目录中是否存在视频文件
                        File videoFile = new File(fullVideoPath);
                        if (!videoFile.exists()) {
                            // 如果src目录中不存在，尝试使用target路径
                            String basePath = request.getServletContext().getRealPath("/");
                            fullVideoPath = basePath + latestVideo.getVideoAddress().substring(1); // 移除开头的"/"
                            
                            // 如果仍然不存在，尝试Tomcat部署路径
                            if (!new File(fullVideoPath).exists()) {
                                String catalinaBase = System.getProperty("catalina.base");
                                if (catalinaBase != null && catalinaBase.contains("tomcat")) {
                                    String correctedCatalinaBase = catalinaBase;
                                    if (catalinaBase.endsWith("bin")) {
                                        correctedCatalinaBase = catalinaBase.substring(0, catalinaBase.lastIndexOf("bin"));
                                    }
                                    fullVideoPath = correctedCatalinaBase + "webapps" + File.separator + "bilibili" + latestVideo.getVideoAddress();
                                }
                            }
                        }
                        
                        System.out.println("上传后-构建的视频路径: " + fullVideoPath);
                        
                        // 调用音频提取服务，使用完整的文件路径
                        if (audioservice != null) {
                            audioservice.extractAudio((int)latestVideo.getVideoID(), fullVideoPath);
                        } else {
                            System.out.println("  音频服务未初始化，跳过音频提取");
                        }
                    } else {
                        System.out.println("  无法匹配到刚上传的视频，跳过音频提取");
                    }
                } else {
                    System.out.println("  无法查询到视频列表，跳过音频提取");
                }
            }
        }
        System.out.println("上传结束");

        return "videoFileTop";

    }

    /************************* 这里写个百分比的AJAX *******************************/
    @RequestMapping(value = "baifenbiAJAX", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody void baifenbiAJAX(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if (request.getSession().getAttribute("video") == null) {
            // new 出一个实体
            videoTopEntity video = new videoTopEntity();
            video.setBaifenbi(0);
            video.setFilename("请稍后..");
            video.setFileSize(0);
            video.setFileSY(0);
            video.setTag(0);
            // 放入session中
            request.getSession().setAttribute("video", video);// 放入到session中

            // - -想不到办法....就先让线程休息3秒在往下跑吧
            /*
             * try { Thread.sleep(3000); } catch (InterruptedException e) {
             * System.out.println("线程休息出错"); e.printStackTrace(); }
             */
        } else {
            videoTopEntity video = (videoTopEntity) request.getSession().getAttribute("video");
            // 设置编码
            response.setCharacterEncoding("UTF-8");
            request.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            Gson gson = new Gson();
            String sbb = gson.toJson(video);
            out.write(sbb);
        }

    }



/*	// 全部订单
	@RequestMapping("Adminbackgroundshipment")
	public ModelAndView admin(HttpServletRequest request) {
		List<ordertableEntity> ordertable = userListServiceImpl.ordertablelist();
		Map model = new HashMap();
		model.put("ordertable", ordertable);
		return new ModelAndView("Adminbackgroundshipment", model);
	}*/


    //论坛首页
    @RequestMapping("forum.sf")
    public String forum(HttpServletRequest request) {
        //	System.out.println("论坛"+Callingmethod.count);//得到注册用户的总数量

        //将所有的论坛全部查询出来
        String a = (String) request.getServletContext().getAttribute("getUserName");
        request.setAttribute("userNamemessage", a);//最新注册的用户
        request.setAttribute("usercount", Callingmethod.count);//用户总数
        request.setAttribute("messacount", Callingmethod.messacount);//帖子总数
        request.setAttribute("onedaycount", Callingmethod.onedaycount);//今日总帖子数量
        request.setAttribute("yessdaycount", Callingmethod.yessdaycount);//昨日总帖子数量
        request.setAttribute("servedatcount", Callingmethod.servedatcount);//7天总帖子数量
        return "Forumindex";
    }

    @RequestMapping("Forum")
    public ModelAndView Forum(HttpServletRequest request) {
        //将所有的论坛全部查询出来
        List<forumEntity> list =  userListServiceImpl.forumEnt("1");
        Map model = new HashMap();
        model.put("list", list);
        return new ModelAndView("Forum", model);
    }
    //
    //论坛帖子详细
    @RequestMapping("forumReply.sf")
    public ModelAndView forumReply(HttpServletRequest request,String forumID) {
        //将所有的论坛全部查询出来
        forumEntity user = userListServiceImpl.forumentitymmp(forumID);
        //在根据此ID查询出所有的回复
        List<forumreplyEntity> forumre = userListServiceImpl.forumreply(forumID);

        Map model = new HashMap();
        model.put("user", user);
        model.put("forumre", forumre);
        return new ModelAndView("ForumReply", model);
    }


    //发布文章AJAX
    @RequestMapping(value = "forummessage.sf", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public  @ResponseBody void forummessage(HttpServletRequest request,HttpServletResponse response,String biaoti , String neirong) throws IOException {
        forumEntity  forument = new forumEntity();
        //给实体设值
        forument.setForumID(GetUUID.getUUID());
        forument.setForumBT(biaoti);
        forument.setForummessage(neirong);
        String userName=(String) request.getSession().getAttribute("userName");
        userEntity user=userListServiceImpl.userlistUserName(userName).get(0);
        forument.setForumuserName(user.getUserMingzi());
        forument.setForumTime(GetDataTime.DQtime());
        forument.setForumliebie("1");//类别也默认为1吧
        forument.setForumAmount("0");
        forument.setFirumhand((String) request.getSession().getAttribute("userHand"));
        String list="";

        //调用保存方法
        boolean bl = messageService.forumfuck(forument);
        if(bl){
            list="发表成功";
        }else {
            list="发表失败";
        }


        // 设置编码
        response.setCharacterEncoding("UTF-8");
        try {
            request.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            Gson gson = new Gson();
            String sbb = gson.toJson(list);
            out.write(sbb);
        } catch (UnsupportedEncodingException e) {
            System.out.println("发布论坛文章这里出错了");
        }

    }

    /****************************************************************/

    //发布文章AJAX
    @RequestMapping(value = "forumreply.sf", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public  @ResponseBody void forumreply(HttpServletRequest request,HttpServletResponse response,String neirong , String id) throws IOException {

        // 设置编码
        response.setCharacterEncoding("UTF-8");
        try {
            String listecho = "";
            forumreplyEntity forumreply = new forumreplyEntity();
            forumreply.setReplyhand((String) request.getSession().getAttribute("userHand"));
            forumreply.setReplyid(GetUUID.getUUID());
            forumreply.setReplyneirong(neirong);
            forumreply.setReplytieziid(id);
            forumreply.setReplytime(GetDataTime.DQtime());
            forumreply.setReplyname((String) request.getSession().getAttribute("userName"));
            //调用方法
            boolean  bl = messageServiceImpl.forumreply(forumreply);
            if(bl){
                listecho="回复成功";
            }else{
                listecho="回复失败,请联系管理员";
            }
            request.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            Gson gson = new Gson();
            String sbb = gson.toJson(listecho);
            out.write(sbb);
        } catch (UnsupportedEncodingException e) {
            System.out.println("回复论坛帖子这里出错了");
        }

    }



    /*
     *
     * //所有测试页面都进这
     *
     * @RequestMapping("testshabi.sf") public String
     * testshabi(HttpServletRequest request) {
     *
     * return "DisplayVideo";
     *
     * }
     */
    // 将数据库里面所有的视频查询出来 发送到首页面
    @RequestMapping(value="insertdanmu/v3",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> insertDanmu(@RequestBody JsonDanmu jsonDanmu) {
        System.out.println("接收到请求");

        System.out.println(jsonDanmu.getText());
        System.out.println(jsonDanmu.getColor());
        System.out.println(jsonDanmu.getTime());
        Danmu danmu=new Danmu();
        danmu.setDsize(0);
        danmu.setPosition(jsonDanmu.getType());
        danmu.setDtime(jsonDanmu.getTime());
        danmu.setColor(jsonDanmu.getColor());
        danmu.setVid(Integer.parseInt(jsonDanmu.getId()));
        danmu.setContent(jsonDanmu.getText());
        Map<String, Object> map = new HashMap<>();

        if(danmuServiceimpl.sendDanmu(danmu)){

            map.put("code",0);
            return  map;
        }

//		List<videoEntity> list = userListServiceImpl.videolist("1");// 1 为动画mad
//		Map model = new HashMap();
//		model.put("list", list);
//		List<videoEntity> list2 = userListServiceImpl.videolist("2");// 2
//		model.put("list2", list2);
//		List<videoEntity> list3 = userListServiceImpl.videolist("3");// 3
//		model.put("list3", list3);
//		// 随机查6条数据出来
//		List<videoEntity> fuck = userListServiceImpl.videolistimit6MAD();
//		model.put("fuck", fuck);
        /*
         * List<videoEntity> test = userListServiceImpl.videolistimit5();
         * for(videoEntity ts:test){ System.out.println("测试是否查询出来");
         * System.out.println(ts.getVideoName());
         * System.out.println(ts.getVideoTime()); } 可以查询出这么多条记录
         */
        /* System.out.println("测试"); */
/*
		try {
			request.getRequestDispatcher("/static/adminjs/index.html").forward(request,response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

        //		return new ModelAndView("index", model);
        map.put("code",1);
        return  map;

    }


    @RequestMapping(value={"insertdanmu/v3","insertdanmu"},method=RequestMethod.GET,produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String getDanmu(HttpServletRequest request,HttpServletResponse response){
        String id=request.getParameter("id");
        if(id==null){
            return "{\"code\":0 ,\"data\":[] }";
        }
        int vid=Integer.parseInt(request.getParameter("id"));
        List<Danmu> danmuList=danmuServiceimpl.readDanmuByVid(vid);
        Map<String, Object> map = new HashMap<>();
        map.put("code",0);
        String pre="{\"code\":0 ,\"data\":";
        String  s="{\"data\": [[0, 0, 16777215, \"用户\", \"ddd\"], [7, 0, 16777215, \"用户\", \"ewwqweew\"]], \"code\": 0}";

        //List<>
        String last="[";

        int i=0;
        do{
            if(i==0) {
                last = last + "[" + "" + danmuList.get(i).getDtime() + ", " + danmuList.get(i).getPosition() + ", " + danmuList.get(i).getColor() + ", \"用户\", \"" + danmuList.get(i).getContent() + "\"]";
            }else {
                last = last + ", [" + "" + danmuList.get(i).getDtime() + ", " + danmuList.get(i).getPosition() + ", " + danmuList.get(i).getColor() + ", \"用户\", \"" + danmuList.get(i).getContent() + "\"]";

            }

            i++;
        }while (i<danmuList.size());

        last=last+"]}";
        System.out.println(pre+last);

        return  pre+last;
    }

    // 批量提取音频功能
    @RequestMapping(value = "batchExtractAudio", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> batchExtractAudio(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 获取所有视频列表
            List<videoEntity> allVideos = videoserviceimpl.selectAllVideos();
            
            int successCount = 0;
            int totalCount = 0;
            
            for(videoEntity video : allVideos) {
                // 检查视频是否已经有音频文件
                if (audioservice != null && !audioservice.hasAudioForVideo((int)video.getVideoID())) {
                    // 检查视频文件是否存在
                    String videoPath = video.getVideoAddress();
                    System.out.println("检查视频ID: " + video.getVideoID() + ", 路径: " + videoPath);
                    if(videoPath != null && videoPath.contains(".mp4")) {
                        totalCount++;
                        // 提取音频
                        boolean success = false;
                        if (audioservice != null) {
                            success = audioservice.extractAudio((int)video.getVideoID(), videoPath);
                        } else {
                            System.out.println("音频服务未初始化，跳过视频ID: " + video.getVideoID());
                        }
                        if(success) {
                            System.out.println("音频提取成功，视频ID: " + video.getVideoID());
                            successCount++;
                        } else {
                            System.out.println("音频提取失败，视频ID: " + video.getVideoID());
                        }
                    } else {
                        System.out.println("视频不是MP4格式或路径为空，跳过，ID: " + video.getVideoID());
                    }
                } else {
                    System.out.println("视频已有音频，跳过，ID: " + video.getVideoID());
                }
            }
            
            result.put("success", true);
            result.put("message", "批量提取音频完成");
            result.put("successCount", successCount);
            result.put("totalCount", totalCount);
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "批量提取音频失败: " + e.getMessage());
        }
        
        return result;
    }
    
    // 启动批量提取音频
    @RequestMapping(value = "startBatchExtractAudio", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> startBatchExtractAudio(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        
        if (isProcessing) {
            result.put("success", false);
            result.put("message", "批量提取音频已在进行中");
            return result;
        }
        
        try {
            isProcessing = true;
            
            // 获取所有视频列表
            List<videoEntity> allVideos = videoserviceimpl.selectAllVideos();
            
            // 初始化进度
            int totalCount = 0;
            for(videoEntity video : allVideos) {
                // 检查视频是否已经有音频文件
                if (audioservice != null && !audioservice.hasAudioForVideo((int)video.getVideoID())) {
                    // 检查视频文件是否存在（支持常见视频格式）
                    String videoPath = video.getVideoAddress();
                    if(videoPath != null && isVideoFile(videoPath)) {
                        totalCount++;
                    }
                }
            }
            
            progress.setTotal(totalCount);
            progress.setProcessed(0);
            progress.setSuccess(0);
            progress.setStatus("开始处理");
            
            // 获取基础路径
            String basePath = request.getServletContext().getRealPath("/");
            System.out.println("批量提取音频 - 基础路径: " + basePath);
            
            // 在新线程中执行批量提取
            Thread extractThread = new Thread(() -> {
                int successCount = 0;
                try {
                    for(videoEntity video : allVideos) {
                        if (!isProcessing) break; // 如果处理被中断则退出
                        
                        // 检查视频是否已经有音频文件
                        if (audioservice != null && !audioservice.hasAudioForVideo((int)video.getVideoID())) {
                            // 检查视频文件是否存在（支持常见视频格式）
                            String videoPath = video.getVideoAddress();
                            if(videoPath != null && isVideoFile(videoPath)) {
                                progress.setCurrentVideoName(video.getVideoName());
                                progress.setStatus("正在处理: " + video.getVideoName());
                                
                                // 构建完整路径 - 优先使用src目录以符合用户偏好
                                String projectPath = System.getProperty("user.dir");
                                String fullVideoPath = projectPath + "/src/main/webapp" + videoPath;
                                
                                // 检查src目录中是否存在视频文件
                                File videoFile = new File(fullVideoPath);
                                if (!videoFile.exists()) {
                                    // 如果src目录中不存在，尝试使用ServletContext路径
                                    String realPath = request.getServletContext().getRealPath(videoPath);
                                    if (realPath != null && new File(realPath).exists()) {
                                        fullVideoPath = realPath;
                                        System.out.println("批量处理 - 使用ServletContext路径: " + fullVideoPath);
                                    } else {
                                        // 如果ServletContext路径也不存在，尝试Tomcat部署路径
                                        String catalinaBase = System.getProperty("catalina.base");
                                        if (catalinaBase != null && catalinaBase.contains("tomcat")) {
                                            String correctedCatalinaBase = catalinaBase;
                                            if (catalinaBase.endsWith("bin")) {
                                                correctedCatalinaBase = catalinaBase.substring(0, catalinaBase.lastIndexOf("bin"));
                                            }
                                            fullVideoPath = correctedCatalinaBase + "webapps" + File.separator + "bilibili" + videoPath;
                                            System.out.println("批量处理 - 使用Tomcat路径: " + fullVideoPath);
                                        }
                                    }
                                } else {
                                    System.out.println("批量处理 - 使用src路径: " + fullVideoPath);
                                }
                                
                                System.out.println("批量处理 - 视频ID: " + video.getVideoID() + 
                                    ", 视频名称: " + video.getVideoName() + 
                                    ", 相对路径: " + videoPath + 
                                    ", 完整路径: " + fullVideoPath);
                                
                                // 检查文件是否存在
                                if (!new File(fullVideoPath).exists()) {
                                    System.out.println("视频文件不存在: " + fullVideoPath);
                                    progress.setProcessed(progress.getProcessed() + 1);
                                    continue; // 跳过这个视频
                                }
                                
                                // 提取音频
                                boolean success = false;
                                if (audioservice != null) {
                                    success = audioservice.extractAudio((int)video.getVideoID(), fullVideoPath);
                                } else {
                                    System.out.println("音频服务未初始化，跳过视频ID: " + video.getVideoID());
                                }
                                if(success) {
                                    successCount++;
                                    progress.setSuccess(successCount);
                                }
                                
                                int processed = progress.getProcessed() + 1;
                                progress.setProcessed(processed);
                            }
                        }
                    }
                    progress.setStatus("完成");
                } catch (Exception e) {
                    progress.setStatus("错误: " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    isProcessing = false;
                }
            });
            
            extractThread.start();
            
            result.put("success", true);
            result.put("message", "批量提取音频已启动，共找到 " + totalCount + " 个待处理视频");
            
        } catch (Exception e) {
            isProcessing = false;
            result.put("success", false);
            result.put("message", "启动批量提取音频失败: " + e.getMessage());
        }
        
        return result;
    }
    
    // 检查是否为视频文件
    private boolean isVideoFile(String videoPath) {
        String lowerPath = videoPath.toLowerCase();
        return lowerPath.endsWith(".mp4") || 
               lowerPath.endsWith(".avi") || 
               lowerPath.endsWith(".mov") || 
               lowerPath.endsWith(".wmv") || 
               lowerPath.endsWith(".flv") || 
               lowerPath.endsWith(".mkv") || 
               lowerPath.endsWith(".webm") || 
               lowerPath.endsWith(".m4v") || 
               lowerPath.endsWith(".3gp");
    }
    
    // 获取批量提取音频进度
    @RequestMapping(value = "getBatchExtractProgress", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public AudioExtractionProgress getBatchExtractProgress(HttpServletRequest request) {
        return progress;
    }
}