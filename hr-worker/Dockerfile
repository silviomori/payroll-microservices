# docker build -t hr-worker:0.0.1 .
# docker run -P --network payroll-microservices_hr-net hr-worker:0.0.1
FROM openjdk:11
VOLUME [ "/tmp" ]
ADD ./build/libs/hr-worker-0.0.1-SNAPSHOT.jar hr-worker-0.0.1.jar
ENTRYPOINT [ "java", "-jar", "/hr-worker-0.0.1.jar" ]
