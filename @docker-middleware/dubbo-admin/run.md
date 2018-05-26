## 构建
从本地war包构建


## 运行


## 关于日志
docker run 命令中增加参数，实现对日志的控制：
限制docker日志文件（/var/lib/docker/containers/***-json.log）的大小和保留文件的最大个数
--log-opt max-size=10m
--log-opt max-file=3
或者直接关闭docker容器的日志
--log-driver="none"