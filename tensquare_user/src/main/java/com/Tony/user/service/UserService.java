package com.Tony.user.service;

import com.Tony.user.dao.UserDao;
import com.Tony.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author AntonTony
 * @version 1.0
 * @GitHub https://github.com/AntonTony
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    //根据用户id查询
    public User findById(String userId){
        return userDao.selectById(userId);

    }
}
