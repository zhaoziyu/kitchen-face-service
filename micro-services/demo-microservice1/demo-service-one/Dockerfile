FROM java:8-jre
MAINTAINER 赵梓彧 - kitchen_dev@163.com

#可在镜像构建时通过--build-arg参数传入（docker build --build-arg TIME_ZONE=Asia/Shanghai Dockerfile）
#List of tz database time zones：https://en.wikipedia.org/wiki/List_of_tz_database_time_zones
ARG TIME_ZONE=Asia/Shanghai

ENV JAVA_OPTS=""

#设置时区
RUN ln -snf /usr/share/zoneinfo/${TIME_ZONE} /etc/localtime \
    && echo ${TIME_ZONE} >/etc/timezone \
    && dpkg-reconfigure -f noninteractive tzdata

#TODO 注意替换demo-service-1.0.0.jar
COPY demo-service-1.0.0.jar /usr/local/rpc/micro-service.jar

WORKDIR /usr/local/rpc/

CMD ["/bin/sh", "-c", "java $JAVA_OPTS -jar /usr/local/rpc/micro-service.jar"]