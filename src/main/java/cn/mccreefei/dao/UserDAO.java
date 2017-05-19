package cn.mccreefei.dao;

import cn.mccreefei.model.User;
import org.apache.ibatis.annotations.Param;

/**
 * 用户dao接口
 */
public interface UserDAO {
    /**
     * 以用户名和密码查询用户
     * @param name
     * @param password
     * @return 存在返回该用户对象，不存在返回null
     */
    public User queryUser(@Param("name") String name,@Param("password") String password);

    /**
     * 以用户名查询用户
     * @param name
     * @return 存在该用户名返回用户对象，否则返回null
     */
    public User queryUserByName(String name);

    /**
     * 插入一位用户
     * @param name
     * @param password
     */
    public void insertUser(@Param("name") String name,@Param("password") String password);
}
