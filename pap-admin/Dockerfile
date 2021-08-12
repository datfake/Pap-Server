FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD pap-admin/target/*.jar admin.jar
ENV JAVA_OPTS=""
EXPOSE 8004
CMD ["java", "-jar", "admin.jar"]