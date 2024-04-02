package com.example.demo.service;

import org.hibernate.validator.constraints.URL;

import com.example.demo.pojo.User;

import jakarta.validation.constraints.Pattern;

public interface UserService {

	User findByUserName(String username);

	void regist( String username, String password);

	void update(User user);

	void updateAvatarUrl(@URL String avatarUrl);

	void updatePwd(String newPwd);

//	void updateAvatarUrl(String avatarUrl, String token);
//
//	void updatePwd(String newPwd, String username);



}
