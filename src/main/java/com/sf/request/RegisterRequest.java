package com.sf.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sf.entity.userEntity;
import com.sf.service.impl.RegisterServiceImpl;
import com.sf.tool.GetUUID;

@Controller
public class RegisterRequest {
	@Autowired
	RegisterServiceImpl registerService;

	@RequestMapping("register.sf")
	public String web3(userEntity user, HttpServletRequest request,String yanzheng,String mingzhi,String sex,String showme) {
		// System.out.println(user.getUserEmial());
		// System.out.println("测试是否进入此方法");

		// 设置一个默认的用户ID UUID
		user.setUserID(GetUUID.getUUID());
		// 设置用户注册时 默认状态
		user.setUserState("正常");// 状态为0为正常用户
		// 设置注册用户的默认支付密码; (一开始忘记了)
		user.setUserPaypassword("123456");
		// 设置注册用户的默认头像
		user.setUserHand("/static/userHand_Top/upload/MyHand.png");

		// 验证码检查
		if (yanzheng == null || yanzheng.trim().isEmpty()) {
			request.setAttribute("fail", "注册失败,验证码不能为空");
			return "forward:/zhuce.sf";
		}
		
		String yan = (String) request.getSession().getAttribute("yanzhengma");
		if (!yanzheng.equals(yan)) {
			request.setAttribute("fail", "注册失败,验证码错误");
			return "forward:/zhuce.sf";
		}
		
		// 手机号码 正则表达式
		String phone = "^1[3-9]\\d{9}$";
		Pattern regex = Pattern.compile(phone);
		Matcher matcher = regex.matcher(user.getUserPhone());
		boolean pho = matcher.matches();
		
		// 邮箱 正则表达式 - 修复为通用邮箱格式
		String email = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern regexemail = Pattern.compile(email);
		Matcher matcheremial = regexemail.matcher(user.getUserEmial());
		boolean emial = matcheremial.matches();
		
		// 验证用户账号
		String userNmae = "^[a-zA-Z]\\w{5,17}$";
		Pattern regexuserNmae = Pattern.compile(userNmae);
		Matcher matcheruserName = regexuserNmae.matcher(user.getUserName());
		boolean username = matcheruserName.matches();
		
		user.setUserMingzi(mingzhi);
		user.setShowme(showme);
		user.setUsersex(sex);
		
		// 执行验证
		if (!pho) {
			request.setAttribute("fail", "注册失败,手机号码错误");
			return "forward:/zhuce.sf";
		}
		
		if (!emial) {
			request.setAttribute("fail", "注册失败,邮箱地址错误");
			return "forward:/zhuce.sf";
		}
		
		if (!username) {
			request.setAttribute("fail", "注册失败,用户名输入错误");
			return "forward:/zhuce.sf";
		}
		
		// 执行注册
		boolean bl = registerService.RegisterService(user);
		if (bl) {
			// 这里是注册成功
			request.getServletContext().setAttribute("getUserName", user.getUserName());
			return "forward:/login.sf"; // 注册成功后跳转到登录页面
		} else {
			request.setAttribute("fail", "注册失败,此用户已经被注册");
			return "forward:/zhuce.sf";
		}
	}

}