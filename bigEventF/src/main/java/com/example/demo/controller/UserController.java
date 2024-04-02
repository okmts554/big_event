package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojo.Result;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;
import com.example.demo.util.Md5Util;
import com.example.demo.util.ThreadLocalUtil;

import jakarta.validation.constraints.Pattern;

@Validated
@Mapper
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public Result register(@Pattern(regexp="^\\S{5,16}$")String username, @Pattern(regexp="^\\S{5,16}")String password) {
		User u = userService.findByUserName(username);
		if(u==null) {
			userService.regist(username,password);
			return Result.success();
		}
		return Result.error("用户名已被占用");
	}
	
	@PostMapping("/login")
	public Result login(@Pattern(regexp="^\\S{5,16}$")String username, @Pattern(regexp="^\\S{5,16}$")String password) {
		User loginU = userService.findByUserName(username);
		if(loginU==null) {
			return Result.error("用户不存在");
		}
		if(loginU.getPassword().equals(Md5Util.getMD5String(password))) {
			Map<String,Object> claims = new HashMap<>();
			claims.put("id", loginU.getId());
			claims.put("username", loginU.getUsername());
			String token = JwtUtil.genToken(claims);
			return Result.success(token);
		}
		return Result.error("密码错误");
	}
	
	@GetMapping("/userInfo")
	public Result<User> userInfo(/* @RequestHeader(name="Authorization") String token */) {
//		Map<String,Object> map = JwtUtil.parseToken(token);
//		String username = (String)map.get("username");
		Map<String,Object> map = ThreadLocalUtil.get();
		String username = (String)map.get("username");
		User u = userService.findByUserName(username);
		return Result.success(u);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody User user) {
		userService.update(user);
		return Result.success();
	}
	
//	@PatchMapping("/updateAvatar")
//	public Result updateAvatarUrl(@RequestParam String avatarUrl, @RequestHeader(name="Authorization") String token) {
//		userService.updateAvatarUrl(avatarUrl, token);
//		return Result.success();
//		
//	}
//	
//	@PatchMapping("/updatePwd")
//	public Result updatePwd(@RequestBody Map<String, String> params, @RequestHeader(name="Authorization")String token) {
//		String oldPwd = params.get("old_pwd");
//		String newPwd = params.get("new_pwd");
//		String rePwd = params.get("re_pwd");
//		if(!StringUtils.hasLength(oldPwd)||!StringUtils.hasLength(newPwd)||!StringUtils.hasLength(rePwd)) {
//			return Result.error("缺少必要额参数");
//		}
//		Map<String,Object> map = JwtUtil.parseToken(token);
//		String username = (String)map.get("username");
//		User u = userService.findByUserName(username);
//		if(!Md5Util.getMD5String(oldPwd).equals(u.getPassword())) {
//			return Result.error("密码错误");
//		}
//		if(!newPwd.equals(rePwd)) {
//			return Result.error("密码不一致");
//		}
//		userService.updatePwd(newPwd, username);
//		return Result.success();
//	}
	
	@PatchMapping("/updateAvatar")
	public Result updateAvatarUrl(@RequestParam @URL String avatarUrl) {
		userService.updateAvatarUrl(avatarUrl);
		return Result.success();
	}
	
	
	public Result updatePwd(@RequestBody Map<String, String> params) {
		String oldPwd=params.get("old_pwd");
		String newPwd=params.get("new_pwd");
		String rePwd=params.get("re_pwd");
		
		if(!StringUtils.hasLength(oldPwd)||!StringUtils.hasLength(newPwd)||!StringUtils.hasLength(rePwd)) {
			return Result.error("缺少必要的参数你");
		}
		
		Map<String,Object> map = ThreadLocalUtil.get();
		String username = (String)map.get("username");
		User u = userService.findByUserName(username);
		
		if(!u.getPassword().equals(Md5Util.getMD5String(oldPwd))) {
			return Result.error("密码错误");
		}
		if(!newPwd.equals(rePwd)) {
			return Result.error("密码不一致");
		}
		userService.updatePwd(newPwd);
		return Result.success();
	}
	
	
	
	
	


}
