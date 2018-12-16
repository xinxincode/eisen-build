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

