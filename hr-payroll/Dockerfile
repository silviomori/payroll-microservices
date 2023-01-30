# docker build -t hr-payroll:0.0.1 .
# docker run -P --network payroll-microservices_hr-net hr-payroll:0.0.1
FROM openjdk:11
VOLUME [ "/tmp" ]
ADD ./build/libs/hr-payroll-0.0.1-SNAPSHOT.jar hr-payroll-0.0.1.jar
ENTRYPOINT [ "java", "-jar", "/hr-payroll-0.0.1.jar" ]
