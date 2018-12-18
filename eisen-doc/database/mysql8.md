#增加用户组
groupadd mysql
#增加系统账号
useradd -r -g mysql -s /bin/false mysql #设置mysql用户的密码 passwd mysql
#解压包
tar xvf mysql-8.0.12-linux-glibc2.12-x86_64.tar.xz
#创建快捷方式
ln -s /usr/local/mysql/mysql-8.0.12-linux-glibc2.12-x86_64 base
#创建文件夹设置权限
mkdir mysql
chown -R mysql:mysql mysql
chmod -R 750 mysql


[client]
#port=3306
#socket=/usr/local/mysql/mysql.sock
[mysqld]
#port=3306
#user=mysql
#socket=/usr/local/mysql/mysql.sock
basedir=/usr/local/mysql/base
datadir=/usr/local/mysql/data
log-error=/usr/local/mysql/log/log-error.log
default-time_zone = '+8:00'

#初始化
bin/mysqld --defaults-file=/usr/local/mysql/my.cnf --initialize --user=mysql
#启动
nohup /usr/local/mysql/base/bin/mysqld_safe  --defaults-file=/usr/local/mysql/my.cnf --user=mysql >/usr/local/mysql/log/mysql.log2>&1&

#登陆 密码在log-error.log文件里
base/bin/mysql -uroot -p

#修改密码
ALTER USER 'root'@'localhost' IDENTIFIED BY 'root';
#停止服务
shutdown; 或者bash中 bin/mysqladmin -uroot -p shutdown

#创建数据库
create database eisendb;
#创建用户
create user 'eisen'@'%' identified by 'eisen';
#分配权限 with grant option表示给i啊用户可以将拥有的权限授予别人
grant all privileges on eisendb.* to 'eisen'@'%' with grant option;
#查看账户权限
SHOW GRANTS FOR 'eisen'@'%';
#查看没有权限的值
SHOW CREATE USER 'eisen'@'%'\G

#创建类似于访客的用户
CREATE USER 'eisen2'@'192.168.%' IDENTIFIED BY 'eisen2';
GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP ON eisen.* TO 'eisen2'@'192.168.%';
#刷新权限
flush privileges;
#撤销权限
revoke all privileges on eisen.* from  `eisen`@`%`;

#修改密码认证方式 caching_sha2_password就挺好  不用改
ALTER USER 'eisen'@'%' IDENTIFIED WITH mysql_native_password(caching_sha2_password) BY 'eisen';

#java连接配置  useUnicode=ture characterEncoding=gbk
#serverTimezone=GMT%2B8 时区 
#autoReconnect=true 开启异常断开重连 
#autoReconnectForPools=true 开启针对连接池的重连策略 
#failOverReadOnly=false 关闭自动重连后设为只读
url=jdbc:mysql://192.168.200.177:3306/eisendb?serverTimezone=GMT%2B8&autoReconnect=true&failOverReadOnly=false&autoReconnect=true&autoReconnectForPools=true
username=eisen
password=eisen
driver=com.mysql.cj.jdbc.Driver

#展示当前所有连接
show full processlist;
#展示当前 前100条连接
show processlist;


