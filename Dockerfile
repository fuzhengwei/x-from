# 基础镜像
FROM registry.cn-hangzhou.aliyuncs.com/xfg-studio/openjdk:17-jdk-slim

# 作者
MAINTAINER xiaofuge

# 时区配置
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 添加应用 JAR
ADD x-from-app/target/x-from-app-1.0.0-SNAPSHOT.jar /x-from-app.jar

# 暴露端口
EXPOSE 8091

# 启动命令
ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS /x-from-app.jar $PARAMS"]
