## 构建
从本地war包构建（详见dubbo-ops/dubbo-admin）
https://github.com/apache/incubator-dubbo-ops.git


## 运行
### 自有镜像
### 其它镜像
https://hub.docker.com/r/chenchuxin/dubbo-admin/
更改zk和密码都通过环境变量改即可
```
docker run -d \
-p 8080:8080 \
-e dubbo.registry.address=zookeeper://192.168.30.137:2181 \
-e dubbo.admin.root.password=root \
-e dubbo.admin.guest.password=guest \
chenchuxin/dubbo-admin
``` 
运行成功后，稍等一下，访问ip:port即可。
默认账号密码是
管理员：root：root
游客：guest：guest


## 关于日志
docker run 命令中增加参数，实现对日志的控制：
限制docker日志文件（/var/lib/docker/containers/***-json.log）的大小和保留文件的最大个数
--log-opt max-size=10m
--log-opt max-file=3
或者直接关闭docker容器的日志
--log-driver="none"