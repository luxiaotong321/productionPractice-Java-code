package cn.edu.dlpulyt.keshe.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;

    private String id;

    private String phoneNum;

    private    String password;

    private   String gender;

    private    String province;

    private   String content;
}
