FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD pap-restaurant/target/*.jar restaurant.jar
ENV JAVA_OPTS=""
EXPOSE 8003
CMD ["java", "-jar", "restaurant.jar"]