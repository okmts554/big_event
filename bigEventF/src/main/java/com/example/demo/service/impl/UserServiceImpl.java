package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.Map;

import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;
import com.example.demo.util.Md5Util;
import com.example.demo.util.ThreadLocalUtil;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public User findByUserName(String username) {
		User u = userMapper.findByUserName(username);
		return u;
	}

	@Override
	public void regist(String username, String password) {
		String md5String = Md5Util.getMD5String(password);
		password = md5String;
		userMapper.add(username, password);
	}

	@Override
	public void update(User user) {
		user.setUpdateTime(LocalDateTime.now());
		userMapper.update(user);
	}

	@Override
	public void updateAvatarUrl(@URL String avatarUrl) {
		Map<String,Object>map = ThreadLocalUtil.get();
		Integer id = (Integer)map.get("id");
		userMapper.updateAvatarUrl(avatarUrl, id);
	}

	@Override
	public void updatePwd(String newPwd) {
		Map<String,Object>map = ThreadLocalUtil.get();
		Integer id = (Integer)map.get("id");
		newPwd = Md5Util.getMD5String(newPwd);
		userMapper.updateAvatarUrl(newPwd, id );
	}

//	@Override
//	public void updateAvatarUrl(String avatarUrl, String token) {
//		Map<String, Object> map = JwtUtil.parseToken(token);
//		String username = (String)map.get("username");
//		userMapper.updateAvatarUrl(username, avatarUrl);
//	}
//
//	@Override
//	public void updatePwd(String newPwd, String username) {
//		newPwd = Md5Util.getMD5String(newPwd);
//		userMapper.updatePwd(newPwd, username);
//	}



}
