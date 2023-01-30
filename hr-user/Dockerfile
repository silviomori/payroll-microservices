# docker build -t hr-user:0.0.1 .
# docker run -P --network payroll-microservices_hr-net hr-user:0.0.1
FROM openjdk:11
VOLUME [ "/tmp" ]
ADD ./build/libs/hr-user-0.0.1-SNAPSHOT.jar hr-user-0.0.1.jar
ENTRYPOINT [ "java", "-jar", "/hr-user-0.0.1.jar" ]
