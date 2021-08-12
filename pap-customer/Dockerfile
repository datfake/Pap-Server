FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD pap-customer/target/*.jar customer.jar
ENV JAVA_OPTS=""
EXPOSE 8002
CMD ["java", "-jar", "customer.jar"]