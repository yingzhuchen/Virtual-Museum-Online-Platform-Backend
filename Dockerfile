# 使用官方的 Maven 镜像作为构建环境
FROM maven:3.8.4-openjdk-11-slim as build
WORKDIR /app

# 设置时区为上海
ENV TZ=Asia/Shanghai
RUN apt-get update && apt-get install -y tzdata \
  && ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
  && echo "Asia/Shanghai" > /etc/timezone \
  && dpkg-reconfigure -f noninteractive tzdata

# 将 pom.xml 文件复制到容器中
COPY pom.xml .

# 下载项目依赖
RUN mvn dependency:go-offline -B

# 将源代码复制到容器中
COPY src src

# 构建项目
RUN mvn package -DskipTests

# 使用官方的 OpenJDK 镜像作为运行环境
FROM openjdk:11-jre-slim

# 设置时区为上海
ENV TZ=Asia/Shanghai
RUN apt-get update && apt-get install -y tzdata \
  && ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
  && echo "Asia/Shanghai" > /etc/timezone \
  && dpkg-reconfigure -f noninteractive tzdata

# 将构建得到的 JAR 文件复制到容器中
COPY --from=build /app/target/openapi-spring-1.0.0.jar /openapi-spring.jar

# 暴露 8080 端口
EXPOSE 8080

# 运行应用
ENTRYPOINT ["java","-jar","/openapi-spring.jar"]
