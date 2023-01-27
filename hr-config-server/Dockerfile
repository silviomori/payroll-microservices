# docker build -t hr-config-server:0.0.1 .
# docker run -p 8888:8888 --name hr-config-server --network payroll-microservices_hr-net -e GITHUB_USER=silviomori -e GITHUB_PASS= hr-config-server:0.0.1
FROM openjdk:11
VOLUME [ "/tmp" ]
EXPOSE 8888
ADD ./build/libs/hr-config-server-0.0.1-SNAPSHOT.jar hr-config-server-0.0.1.jar
ENTRYPOINT [ "java", "-jar", "/hr-config-server-0.0.1.jar" ]
