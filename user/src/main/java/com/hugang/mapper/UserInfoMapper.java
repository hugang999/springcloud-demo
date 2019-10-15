package com.hugang.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hugang.beans.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: hg
 * @date: 2019/9/25 16:10
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}
