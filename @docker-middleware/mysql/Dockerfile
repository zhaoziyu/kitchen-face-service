FROM mysql:5.7
MAINTAINER 赵梓彧 - kitchen_dev@163.com

#可在build时通过--build-arg参数传入（docker build --build-arg TIME_ZONE=Asia/Shanghai Dockerfile）
#List of tz database time zones
#https://en.wikipedia.org/wiki/List_of_tz_database_time_zones
ARG TIME_ZONE=Asia/Shanghai

#将所需文件放到容器中
COPY init.sql docker-entrypoint-initdb.d/init.sql
#将配置文件拷入mysql目录中
COPY my.cnf /etc/mysql/conf.d/my.cnf

#设置时区&&给执行文件增加可执行权限
RUN /bin/cp /usr/share/zoneinfo/${TIME_ZONE} /etc/localtime \
    && echo ${TIME_ZONE} >/etc/timezone \
    && chmod 777 docker-entrypoint-initdb.d/init.sql \
    && chmod 644 /etc/mysql/conf.d/my.cnf
