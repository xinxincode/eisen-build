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

create
unique
index
idun_filedetail_sha256
on
tb_file_detail
(
sha_256
);

