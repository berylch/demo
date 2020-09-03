package com.example.ssm.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.ssm.dao.UserInfoMapper;
import com.example.ssm.model.UserInfo;
import com.example.ssm.service.IUserInfoService;

@Service("userInfoService")
public class UserInfoService implements IUserInfoService {
	
	@Resource
    private UserInfoMapper userInfoDao;

	@Override
	public UserInfo getUserInfoById(Integer id) {
		// TODO Auto-generated method stub
		return userInfoDao.selectByPrimaryKey(id);
	}

}
