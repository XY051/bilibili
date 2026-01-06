package com.sf.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sf.dao.RegisterDao;
import com.sf.db.Data_jdbcTemplate;
import com.sf.entity.userEntity;

@Component
public class RegisterImpl implements RegisterDao {

	@Autowired
	Data_jdbcTemplate jdbcTemplate;
	
	
	
	public int Register(userEntity user) {
		//默认为0
		int register=0;
		// 检查用户名是否已存在
		String checkUserNameSql = "SELECT count(*) FROM user WHERE userName = ?";  
		int userNameCount = jdbcTemplate.getJdbcTemplate().queryForObject(checkUserNameSql, new Object[] {user.getUserName()},Integer.class);
		
		// 检查手机号是否已存在
		String checkPhoneSql = "SELECT count(*) FROM user WHERE userPhone = ?";  
		int phoneCount = jdbcTemplate.getJdbcTemplate().queryForObject(checkPhoneSql, new Object[] {user.getUserPhone()},Integer.class);
		
		// 检查邮箱是否已存在
		String checkEmailSql = "SELECT count(*) FROM user WHERE userEmial = ?";  
		int emailCount = jdbcTemplate.getJdbcTemplate().queryForObject(checkEmailSql, new Object[] {user.getUserEmial()},Integer.class);
		
		if(userNameCount > 0){
			System.out.println("此用户名已被注册");
		} else if(phoneCount > 0){
			System.out.println("此手机号已被注册");
		} else if(emailCount > 0){
			System.out.println("此邮箱已被注册");
		} else {
			//开始注册
			String chaxunSql="insert into user(userID,userName,passWord,userPhone,userState,userEmial,userHand,userPaypassword,userMingzi,usersex,showme) values(?,?,?,?,?,?,?,?,?,?,?)";
			register=jdbcTemplate.getJdbcTemplate().update(chaxunSql,new Object[]{user.getUserID(),user.getUserName(),user.getPassWord(),user.getUserPhone(),user.getUserState(),user.getUserEmial(),user.getUserHand(),user.getUserPaypassword(),user.getUserMingzi(),user.getUsersex(),user.getShowme()});
			
		}
		return register;
	}

}