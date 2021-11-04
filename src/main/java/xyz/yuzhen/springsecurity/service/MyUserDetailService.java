package xyz.yuzhen.springsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.yuzhen.springsecurity.entity.Menu;
import xyz.yuzhen.springsecurity.entity.Role;
import xyz.yuzhen.springsecurity.entity.Users;
import xyz.yuzhen.springsecurity.mapper.UserInfoMapper;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        QueryWrapper<Users> wrapper = new QueryWrapper<Users>();
//        wrapper.eq("username",username);
//        Users users = userMapper.selectOne(wrapper);
//        if(users == null){
//            throw new UsernameNotFoundException("用户名不存在");
//        }
        List<Users> usersList = userInfoMapper.selectByUserName(username);
        if(null == usersList){
            System.out.println("用户不存在");
            throw new UsernameNotFoundException("用户不存在");
        }
        Users users = usersList.get(0);
        List<Role> roleList = userInfoMapper.selectRoleByUserId(users.getId());
        List<Menu> menuList = userInfoMapper.selectMenuByUserId(users.getId());
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        for(Role role:roleList){
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_" + role.getName());
            grantedAuthorityList.add(simpleGrantedAuthority);
        }

        for(Menu menu:menuList){
            grantedAuthorityList.add(new SimpleGrantedAuthority(menu.getPermission()));
        }

        return new User(username, users.getPassword(),grantedAuthorityList);

        //List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_sale");
       // return new User(users.getUsername(),new BCryptPasswordEncoder().encode(users.getPassword()),auths);
    }
}
