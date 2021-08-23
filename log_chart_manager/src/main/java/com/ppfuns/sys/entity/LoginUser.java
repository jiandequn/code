package com.ppfuns.sys.entity;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2020/3/9
 * Time: 15:57
 * To change this template use File | Settings | File Templates.
 */
public class LoginUser implements Serializable {
    @NotNull(message = "用户名不能为空，请您先填写用户名")
    private String username;
    private Integer id;
    private String realName;
    @NotNull(message = "密码不能为空")
   // ^(?![A-Z]+$)(?![a-z]+$)(?!\d+$)(?![\W_]+$)[^\u4e00-\u9fa5]\S{6,16}$
//    @MatchPattern(pattern = "^[0-9_a-zA-Z]{6,20}$", message = "用户名或密码有误，请您重新填写")
    private String password;
    private String telephone;
    private String email;
    private Boolean job;
    private String version;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getJob() {
        return job;
    }

    public void setJob(Boolean job) {
        this.job = job;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
