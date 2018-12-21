create table tb_file_detail
(
  file_id         int primary key auto_increment comment '文件id 主键',
  file_name       varchar(256) not null comment '存储在服务器的文件名',
  file_path       varchar(512) not null comment '存储在服务器的相对文件路径且不包含文件名 如: /file/path/',
  sha_512         char(128)    not null comment '文件的sha-512值 非空 不重索引',
  sha_256         char(64) comment '文件的sha-256值 可为空',
  sha_1           char(40) comment '文件的sha1值 可为空',
  md5             char(32) comment '文件的md5值 可为空',
  create_datetime datetime default now() comment '文件存到服务器的时间 默认当前时间'
) comment '文件存储明细表';


create unique index idun_filedetail_sha256 on tb_file_detail(sha_256);



insert into tb_file_detail (file_name,
                            file_path,
                            sha_512,
                            sha_256,
                            sha_1,
                            md5)
values ('apache-maven-3.6.0-bin.zip', '/file/',
        '7d14ab2b713880538974aa361b987231473fbbed20e83586d542c691ace1139026f232bd46fdcce5e8887f528ab1c3fbfc1b2adec90518b6941235952d3868e9',
        '85f80bb198037223827e459a813180be0f623bff26da16a101e46245a60f06b5',
        '8589fe27a6e0dd831ff967d3a7073bcb6d41b083', '6cccb858a8142b18d860e4b2830eebdc');


create table tb_user_info(
	user_id int primary key auto_increment comment '用户id',
    signin_name varchar(32) not null comment '登录名',
    signin_passwdmd5 varchar(256) not null comment '登陆密码md5加密',
    signin_passwdaes varchar(256) not null comment '登陆密码aes加密',
    passwd_lastdate datetime default CURRENT_TIMESTAMP not null comment '上次修改密码时间',
    signup_date datetime default CURRENT_TIMESTAMP not null comment '注册日期 默认插入时间',
    signin_lastdate datetime comment '上次登陆时间',
    nick_name varchar(256) default '未设置昵称' not null comment '用户昵称',
    user_email varchar(128) comment '用户邮箱',
    user_mphone varchar(64) comment '用户手机号',
    user_address varchar(512) comment '用户地址',
    user_sex boolean comment '用户性别',
    head_portrait int(11) comment '用户头像文件id'
) comment '用户基础信息表';
create unique index idun_userinfo_signinname on tb_user_info(signin_name);
create unique index idun_userinfo_useremail on tb_user_info(user_email);
create unique index idun_userinfo_usermphone on tb_user_info(user_mphone);


create table tb_user_file(
	data_id int primary key auto_increment comment 'id',
    user_id int comment 'user_info的用户id',
    file_id int comment 'file_detail的文件id',
    file_name varchar(128) not null comment '用户所见文件名称',
    file_path varchar(256) not null comment '用户所见文件路径',
    file_date datetime default current_timestamp comment '用户获取文件的时间'
) comment '用户文件关联表';
create index id_userfile_userid on tb_user_file(user_id) comment '用户id索引';
alter table tb_user_file add foreign key fk_userfile_userid(user_id) references tb_user_info(user_id) on delete no action on update no action;
