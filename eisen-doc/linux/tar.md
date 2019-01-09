#打tar包命令 c=create v=显示文件列表 f=递归
tar -cvf foo.tar foo/
#查看tar包里的文件 t=list 列出
tar -tvf foo.tar
#解包 x=extract get 解包
tar -xvf foo.tar

#压缩 J=xz z=gz 
tar -Jcvf foo.tar.xz foo
#解压
tar -Jxvf foo.tar.xz

#解压gz包
gzip -d foo.gz