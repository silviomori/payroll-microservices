# docker build -t hr-api-gateway-zuul:0.0.1 .
# docker run -p 8765:8765 --name hr-api-gateway-zuul --network payroll-microservices_hr-net hr-api-gateway-zuul:0.0.1
FROM openjdk:11
VOLUME [ "/tmp" ]
EXPOSE 8765
ADD ./build/libs/hr-api-gateway-zuul-0.0.1-SNAPSHOT.jar hr-api-gateway-zuul-0.0.1.jar
ENTRYPOINT [ "java", "-jar", "/hr-api-gateway-zuul-0.0.1.jar" ]
