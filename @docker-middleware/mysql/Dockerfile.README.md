## 构建镜像（In Jenkins）
docker build -t kitchen-db:2.0 ./@docker-middleware/mysql/

# 运行
docker run -d \
    --publish 3306:3306 \
    --name kitchen-db \
    --volume /opt/mysql/data:/var/lib/mysql \
    --env MYSQL_ROOT_PASSWORD="123456" \
    --restart=always \
kitchen-db:2.0

## 关于日志
docker run 命令中增加参数，实现对日志的控制：
限制docker日志文件（/var/lib/docker/containers/***-json.log）的大小和保留文件的最大个数
--log-opt max-size=10m
--log-opt max-file=3
或者直接关闭docker容器的日志
--log-driver="none"