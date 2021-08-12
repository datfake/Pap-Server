FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD pap-courier/target/*.jar courier.jar
ENV JAVA_OPTS=""
EXPOSE 8001
CMD ["java", "-jar", "courier.jar"]