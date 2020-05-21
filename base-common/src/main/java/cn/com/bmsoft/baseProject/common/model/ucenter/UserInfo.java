package cn.com.bmsoft.baseProject.common.model.ucenter;

import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * 保存登录用户详细信息(可拓展)
 *
 * @author huangdiwen
 */
@Data
@ToString
public class UserInfo extends User {
    //教职工
    public static final String USERTYPE_TEACHER = "Teacher";
    //学生
    public static final String USERTYPE_STUDENT = "Student";

    private static final long serialVersionUID = -6869488151836596942L;
    //用户ID
    private Integer id;
    //编号(例如工号)
    private String no;
    //姓名
    private String name;
    //登录时间
    private Date loginTime;
    //学校机构名称
    private String department;
    //联系电话
    private String phone;
    //电子邮箱
    private String email;
    //用户类型
    private String type;
    //学校机构码
    private String orgcode;
    //昵称
    private String petname;
    //登录账号
    private String loginName;
    
    //用户角色级别：值越小，级别越高
    private Integer roleLevel = 99;

    public UserInfo(String username, String password) {
        super(username, password, Collections.EMPTY_LIST);
    }

    public UserInfo(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public UserInfo(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
