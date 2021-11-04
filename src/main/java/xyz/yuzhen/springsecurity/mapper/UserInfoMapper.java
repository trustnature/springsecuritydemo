package xyz.yuzhen.springsecurity.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import xyz.yuzhen.springsecurity.entity.Menu;
import xyz.yuzhen.springsecurity.entity.Role;
import xyz.yuzhen.springsecurity.entity.Users;

import java.util.List;

@Repository
public interface UserInfoMapper {

    List<Users> selectByUserName(String username);

    /** * 根据用户Id 查询用户角色
     * * @param userId * @return
     * */
    List<Role> selectRoleByUserId(Long userId);

    /**
     * * 根据用户Id 查询菜单
     * * @param userId * @return
     * */
    List<Menu> selectMenuByUserId(Long userId);
}
