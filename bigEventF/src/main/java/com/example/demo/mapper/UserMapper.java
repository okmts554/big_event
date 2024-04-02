package com.example.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.pojo.User;

@Mapper
public interface UserMapper {

	@Select("select * from user where username = #{username}")
	User findByUserName(String username);

	@Insert("insert into user(username,password,create_time,update_time) value(#{username},#{password},now(),now())")
	void add(String username, String password);
	
	@Update("update user set nickname=#{nickname},email=#{email},update_time=#{updateTime} where id=#{id}")
	void update(User user);

//	@Update("update user set user_pic=#{avatarUrl} where username=#{username}")
//	void updateAvatarUrl(String username, String avatarUrl);
//
//	@Update("update user set password=#{newPwd} where username=#{username}")
//	void updatePwd(String newPwd, String username);
	
	@Update("update user set user_pic=#{avatarUrl} where id=#{id}")
	void updateAvatarUrl(String avatarUrl,Integer id);

	@Update("update user set password=#{newPwd} where id=#{id}")
	void updatePwd(String newPwd, Integer id);

}
