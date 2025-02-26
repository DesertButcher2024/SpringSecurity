package com.bjpowernode.service.impl;

import com.bjpowernode.entity.TUser;
import com.bjpowernode.mapper.TUserMapper;
import com.bjpowernode.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    //逆向工程、反向工程（根据数据库表，生成mapper接口、mapper.xml、实体类）

    @Resource
    private TUserMapper tUserMapper;

    /**
     * 该方法在spring security框架登录的时候被调用
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询数据库，查询页面上传过来的这个用户名是否在数据库中存在，也就是根据该username查询用户对象
        TUser tUser = tUserMapper.selectByLoginAct(username); //cat
        if (tUser == null) {
            throw new UsernameNotFoundException("登录账号不存在");
        }

        //构建一个Spring Security框架里面的User对象来返回
        UserDetails userDetails = User.builder()
                .username(tUser.getLoginAct())
                .password(tUser.getLoginPwd())
                .authorities(AuthorityUtils.NO_AUTHORITIES) //权限是空的
                //.accountExpired(true) //true表示账号过期了
                .build();
        return userDetails; //把UserDetails（User）返回给框架之后 ，框架会采用密码加密器进行密码的比较
    }
}
