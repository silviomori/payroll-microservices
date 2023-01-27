# docker build -t hr-eureka-server:0.0.1 .
# docker run -p 8761:8761 --name hr-eureka-server --network payroll-microservices_hr-net hr-eureka-server:0.0.1
FROM openjdk:11
VOLUME [ "/tmp" ]
EXPOSE 8761
ADD ./build/libs/hr-eureka-server-0.0.1-SNAPSHOT.jar hr-eureka-server-0.0.1.jar
ENTRYPOINT [ "java", "-jar", "/hr-eureka-server-0.0.1.jar" ]
