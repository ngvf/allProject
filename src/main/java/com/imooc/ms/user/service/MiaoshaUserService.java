package com.imooc.ms.user.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.imooc.ms.exception.GlobalException;
import com.imooc.ms.redis.MiaoshaUserKey;
import com.imooc.ms.redis.RedisService;
import com.imooc.ms.result.CodeMsg;
import com.imooc.ms.user.dao.MiaoshaUserDao;
import com.imooc.ms.user.entity.MiaoshaUser;
import com.imooc.ms.user.vo.LoginVo;
import com.imooc.ms.util.MD5Util;
import com.imooc.ms.util.UUIDUtil;

@Service
public class MiaoshaUserService {

	public static final String COOKI_NAME_TOKEN = "token";

	@Autowired
	MiaoshaUserDao miaoshaUserDao;

	@Autowired
	RedisService redisService;

	public MiaoshaUser getById(long id) {
		//对象缓存实现
		//取缓存
		MiaoshaUser msUser = redisService.get(MiaoshaUserKey.getById, ""+id, MiaoshaUser.class);
		if(msUser!=null) {
			return msUser;
		}
		//取数据库
		msUser=miaoshaUserDao.getById(id);
		if(msUser!=null) {
			redisService.set(MiaoshaUserKey.getById, ""+id, msUser);
		}
		return msUser;
	}
	
	
	/**
	 * @author yangdd
	 * @param id
	 * @param token 
	 * @return
	 */
	public boolean updatePassword(String formPass ,long id, String token) {
		//A.取缓存
		MiaoshaUser user = getById(id);
		if(user==null) {
			throw new GlobalException(CodeMsg.MOBILE_EMPTY);
		}
		/*
		 * 這裏爲什麽要新建一個updateUser對象？ 因爲  在更新數據庫的時候傳到dao層的參數只要更新的參數，要更新的只有password  
		 * 如果傳user就會吧所有user的參數傳到dao層，在delog的時候不好查看
		 */
		MiaoshaUser updateUser=new MiaoshaUser();
		updateUser.setId(id);
		updateUser.setPassword(MD5Util.formPassToDBPass(formPass, user.getSalt()));
		//B.更新数据库
		miaoshaUserDao.updatePassword(updateUser);
		
		//C.處理緩存
		
		 //1.更新缓存时先删除缓存
		redisService.delete(MiaoshaUserKey.getById, ""+id);
		/*
		 * 這裏爲什麽沒有重新設置呢？只有登錄的時候會用到，而登錄的時候會走 getById（）方法重新設值
		 */
		//2.再设置缓存
		user.setPassword(updateUser.getPassword());
		redisService.set(MiaoshaUserKey.token, token, user);
		
		return true;
	}
	
	
	public MiaoshaUser getByToken(HttpServletResponse response, String token) {
		if (StringUtils.isEmpty(token)) { return null; }
		MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
		// 延长有效期
		if (user != null) {
			addCookie(response, token, user);
		}
		return user;
	}

	public String login(HttpServletResponse response, LoginVo loginVo) {
		if (loginVo == null) { throw new GlobalException(CodeMsg.SERVER_ERROR); }
		String mobile = loginVo.getMobile();
		String formPass = loginVo.getPassword();
		// 判断手机号是否存在
		MiaoshaUser user = getById(Long.parseLong(mobile));
		if (user == null) { throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST); }
		// 验证密码
		String dbPass = user.getPassword();
		String saltDB = user.getSalt();
		String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
		if (!calcPass.equals(dbPass)) { throw new GlobalException(CodeMsg.PASSWORD_ERROR); }
		// 生成cookie   登录成功后生成cookie保存session中
		String token = UUIDUtil.uuid();
		addCookie(response, token, user);
		return token;
	}

	private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
		redisService.set(MiaoshaUserKey.token, token, user);//生辰token标识用户     将token和用户  保存在redis缓存中，用作session 便于下次去用
		Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
		int expireSeconds = MiaoshaUserKey.token.expireSeconds();
		cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());//设置cookie过期时间
		cookie.setPath("/");//设置网站的根目录
		response.addCookie(cookie);
	}

}
