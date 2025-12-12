package com.sf.dao;

import com.sf.entity.ShoppingCart;
import com.sf.entity.forumEntity;
import com.sf.entity.forumreplyEntity;
import com.sf.entity.messageEntity;
import com.sf.entity.ordertableEntity;

import java.util.List;

public interface MessageDao {
	/**
	 * 	直接保存用户留言信息到留言表
	 * @param message
	 * @return
	 */
	public int message(messageEntity message);
	
	
	
	public int Shoppingcart(ShoppingCart shoppingCart);
	
	
	/**
	 * 直接保存论坛帖子
	 * @param forument
	 * @return
	 */
	public int forumfuck(forumEntity forument);
	
	/**
	 * 直接保存回复的帖子内容
	 * @param forument
	 * @return
	 */
	public int forumreply(forumreplyEntity forumreply);
	public List<messageEntity> messagelist(int start);
	public boolean messagedel(String id );
	public boolean messagecheck(String id );


}
