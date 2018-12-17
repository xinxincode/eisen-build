package org.eisen.dao.orm.model;

import java.util.Date;

/**
 * @Author: eisen
 * @DateTime: 2018-12-17 20:07:28
 * 开发描述:
 *   用户基础信息表
 */
public class TbUserInfo {
    // 用户id tb_user_info.user_id
    private Integer userId;

    // 登录名 tb_user_info.signin_name
    private String signinName;

    // 登陆密码md5加密 tb_user_info.signin_passwdmd5
    private String signinPasswdmd5;

    // 登陆密码aes加密 tb_user_info.signin_passwdaes
    private String signinPasswdaes;

    // 上次修改密码时间 tb_user_info.passwd_lastdate
    private Date passwdLastdate;

    // 注册日期 默认插入时间 tb_user_info.signup_date
    private Date signupDate;

    // 上次登陆时间 tb_user_info.signin_lastdate
    private Date signinLastdate;

    // 用户昵称 tb_user_info.nick_name
    private String nickName;

    // 用户邮箱 tb_user_info.user_email
    private String userEmail;

    // 用户手机号 tb_user_info.user_mphone
    private String userMphone;

    // 用户地址 tb_user_info.user_address
    private String userAddress;

    // 用户性别 tb_user_info.user_sex
    private Boolean userSex;

    // 用户头像文件id tb_user_info.head_portrait
    private Integer headPortrait;

    //获取 用户id
    public Integer getUserId() {
        return userId;
    }

    //设置 用户id
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    //获取 登录名
    public String getSigninName() {
        return signinName;
    }

    //设置 登录名
    public void setSigninName(String signinName) {
        this.signinName = signinName;
    }

    //获取 登陆密码md5加密
    public String getSigninPasswdmd5() {
        return signinPasswdmd5;
    }

    //设置 登陆密码md5加密
    public void setSigninPasswdmd5(String signinPasswdmd5) {
        this.signinPasswdmd5 = signinPasswdmd5;
    }

    //获取 登陆密码aes加密
    public String getSigninPasswdaes() {
        return signinPasswdaes;
    }

    //设置 登陆密码aes加密
    public void setSigninPasswdaes(String signinPasswdaes) {
        this.signinPasswdaes = signinPasswdaes;
    }

    //获取 上次修改密码时间
    public Date getPasswdLastdate() {
        return passwdLastdate;
    }

    //设置 上次修改密码时间
    public void setPasswdLastdate(Date passwdLastdate) {
        this.passwdLastdate = passwdLastdate;
    }

    //获取 注册日期 默认插入时间
    public Date getSignupDate() {
        return signupDate;
    }

    //设置 注册日期 默认插入时间
    public void setSignupDate(Date signupDate) {
        this.signupDate = signupDate;
    }

    //获取 上次登陆时间
    public Date getSigninLastdate() {
        return signinLastdate;
    }

    //设置 上次登陆时间
    public void setSigninLastdate(Date signinLastdate) {
        this.signinLastdate = signinLastdate;
    }

    //获取 用户昵称
    public String getNickName() {
        return nickName;
    }

    //设置 用户昵称
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    //获取 用户邮箱
    public String getUserEmail() {
        return userEmail;
    }

    //设置 用户邮箱
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    //获取 用户手机号
    public String getUserMphone() {
        return userMphone;
    }

    //设置 用户手机号
    public void setUserMphone(String userMphone) {
        this.userMphone = userMphone;
    }

    //获取 用户地址
    public String getUserAddress() {
        return userAddress;
    }

    //设置 用户地址
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    //获取 用户性别
    public Boolean getUserSex() {
        return userSex;
    }

    //设置 用户性别
    public void setUserSex(Boolean userSex) {
        this.userSex = userSex;
    }

    //获取 用户头像文件id
    public Integer getHeadPortrait() {
        return headPortrait;
    }

    //设置 用户头像文件id
    public void setHeadPortrait(Integer headPortrait) {
        this.headPortrait = headPortrait;
    }
}