# Dockerfile后续命令

## 构建镜像（In Jenkins）
docker build -t <hub ip>:<port>/api-portal:2.0.0 ./api-portal/target/
## 上传镜像到私有镜像中心
docker push <hub ip>:<port>/api-portal:2.0.0

## 在宿主机中拉取镜像
docker pull <hub ip>:<port>/api-portal:2.0.0

## 运行
## 方式一：默认命令运行
docker run -d --name api-portal -p 8080:8080 \
    --env spring.profiles.active=prod \
<hub ip>:<port>/api-portal:2.0.0
## 方式二：自定义命令运行
docker run -d --name api-portal -p 8080:8080 \
    --env spring.profiles.active=prod \
<hub ip>:<port>/api-portal:2.0.0 \
java -jar api-portal.jar

## 关于日志
docker run 命令中增加参数，实现对日志的控制：
限制docker日志文件（/var/lib/docker/containers/***-json.log）的大小和保留文件的最大个数
--log-opt max-size=10m
--log-opt max-file=3
或者直接关闭docker容器的日志
--log-driver="none"

    


