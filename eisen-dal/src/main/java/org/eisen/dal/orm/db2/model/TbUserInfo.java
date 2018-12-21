package org.eisen.dal.orm.db2.model;

import java.util.Date;

/**
 * @Author: eisen
 * @DateTime: 2018-12-21 10:54:37
 * 开发描述:
 *   用户基础信息表
 */
public class TbUserInfo {
    //用户id tb_user_info.user_id
    private Integer userId;

    //登录名 tb_user_info.signin_name
    private String signinName;

    //登陆密码md5加密 tb_user_info.signin_passwdmd5
    private String signinPasswdmd5;

    //登陆密码aes加密 tb_user_info.signin_passwdaes
    private String signinPasswdaes;

    //上次修改密码时间 tb_user_info.passwd_lastdate
    private Date passwdLastdate;

    //注册日期 默认插入时间 tb_user_info.signup_date
    private Date signupDate;

    //上次登陆时间 tb_user_info.signin_lastdate
    private Date signinLastdate;

    //用户昵称 tb_user_info.nick_name
    private String nickName;

    //用户邮箱 tb_user_info.user_email
    private String userEmail;

    //用户手机号 tb_user_info.user_mphone
    private String userMphone;

    //用户地址 tb_user_info.user_address
    private String userAddress;

    //用户性别 tb_user_info.user_sex
    private Boolean userSex;

    //用户头像文件id tb_user_info.head_portrait
    private Integer headPortrait;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSigninName() {
        return signinName;
    }

    public void setSigninName(String signinName) {
        this.signinName = signinName;
    }

    public String getSigninPasswdmd5() {
        return signinPasswdmd5;
    }

    public void setSigninPasswdmd5(String signinPasswdmd5) {
        this.signinPasswdmd5 = signinPasswdmd5;
    }

    public String getSigninPasswdaes() {
        return signinPasswdaes;
    }

    public void setSigninPasswdaes(String signinPasswdaes) {
        this.signinPasswdaes = signinPasswdaes;
    }

    public Date getPasswdLastdate() {
        return passwdLastdate;
    }

    public void setPasswdLastdate(Date passwdLastdate) {
        this.passwdLastdate = passwdLastdate;
    }

    public Date getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(Date signupDate) {
        this.signupDate = signupDate;
    }

    public Date getSigninLastdate() {
        return signinLastdate;
    }

    public void setSigninLastdate(Date signinLastdate) {
        this.signinLastdate = signinLastdate;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMphone() {
        return userMphone;
    }

    public void setUserMphone(String userMphone) {
        this.userMphone = userMphone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public Boolean getUserSex() {
        return userSex;
    }

    public void setUserSex(Boolean userSex) {
        this.userSex = userSex;
    }

    public Integer getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(Integer headPortrait) {
        this.headPortrait = headPortrait;
    }
}