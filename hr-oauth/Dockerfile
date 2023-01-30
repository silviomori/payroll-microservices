# docker build -t hr-oauth:0.0.1 .
# docker run -P --network payroll-microservices_hr-net hr-oauth:0.0.1
FROM openjdk:11
VOLUME [ "/tmp" ]
ADD ./build/libs/hr-oauth-0.0.1-SNAPSHOT.jar hr-oauth-0.0.1.jar
ENTRYPOINT [ "java", "-jar", "/hr-oauth-0.0.1.jar" ]
