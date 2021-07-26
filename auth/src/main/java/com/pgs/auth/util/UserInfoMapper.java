package com.pgs.auth.util;

import com.pgs.auth.domain.User;
import com.pgs.auth.payload.UserInfo;
import org.mapstruct.Mapper;

@Mapper
public interface UserInfoMapper {

    User userInfoToUser(UserInfo userInfo);

    UserInfo userToUserInfo(User user);
}
