#列出public域开放的端口
firewall-cmd --zone=public --list-ports

#永久开放22tcp
firewall-cmd --zone=public --add-port=22/tcp --permanent

#永久移除22udp
firewall-cmd --zone=public --remove-port=22/udp --permanent

#开启防火墙
systemctl start firewalld.service

#停止防火墙
systemctl stop firewalld.service

#重启防火墙
service firewalld restart

#禁止防火墙开机启动
systemctl disable firewalld.service

#查看默认防火墙状态（关闭后显示notrunning，开启后显示running）
firewall-cmd --state

#查开机启动状态
systemctl list-unit-files | grep fire

#设置开机启动
systemctl enable firewalld.service