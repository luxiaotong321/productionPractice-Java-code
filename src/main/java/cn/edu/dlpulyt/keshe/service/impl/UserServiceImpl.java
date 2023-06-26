package cn.edu.dlpulyt.keshe.service.impl;

import ch.qos.logback.classic.spi.EventArgUtil;
import cn.edu.dlpulyt.keshe.mapper.UserMapper;
import cn.edu.dlpulyt.keshe.pojo.User;
import cn.edu.dlpulyt.keshe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;
    @Override
    public User login(String phoneNum, String password) {
        User login = userMapper.getUserByPhoneNumAndPassword(phoneNum, password);
        if(login != null){
            return login;
        }
        return null;
    }

    @Override
    public boolean checkRegister(User user) {
        User register = userMapper.getUserByPhoneNum(user.getPhoneNum());
        if(register != null){
            return false;
        }else{
            return true;
        }
    }


    @Override
    public int register(User user) {
        return userMapper.createUser(user);
    }

    @Override
    public List<User> list() {
        return userMapper.list();
    }
}
