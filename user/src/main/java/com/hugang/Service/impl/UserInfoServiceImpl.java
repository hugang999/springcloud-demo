package com.hugang.Service.impl;

import com.hugang.Service.UserInfoService;
import com.hugang.beans.UserInfo;
import com.hugang.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: hg
 * @date: 2019/9/25 16:12
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo findByOpenId(String openId) {
        return userInfoMapper.selectOne(new UserInfo().setOpenId(openId));
    }
}
