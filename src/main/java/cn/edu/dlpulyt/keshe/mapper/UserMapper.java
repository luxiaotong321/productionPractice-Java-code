package cn.edu.dlpulyt.keshe.mapper;

import cn.edu.dlpulyt.keshe.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper{
    // 在数据库里查手机号和密码
    @Select("select * from user where phone_num = #{phoneNum} and password = #{password}")
    public User getUserByPhoneNumAndPassword(String phoneNum,String password);

    // 在数据库里查手机号
    @Select("select * from user where phone_num = #{phoneNum}")
    public User getUserByPhoneNum(String phoneNum);

    // 插入注册的user信息
    @Insert("INSERT INTO user (name, phone_num,password,gender,province,content) VALUES (#{name},#{phoneNum},#{password},#{gender},#{province},#{content})")
    public int createUser(User user);

    // 用户信息list
    @Select("select * from user")
    public List<User> list();

    // 筛选用户id
    @Select("select * from user where id = #{id}")
    public User getuserById(String id);
}
