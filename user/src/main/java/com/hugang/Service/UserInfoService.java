package com.hugang.Service;

import com.hugang.beans.UserInfo;

/**
 * @author: hg
 * @date: 2019/9/25 16:11
 */
public interface UserInfoService {

    UserInfo findByOpenId(String openId);
}
