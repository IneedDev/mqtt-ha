FROM openjdk:17-jdk-slim
ADD temp/ha-mqtt-client.jar /opt/ha-mqtt-client.jar
USER test
ENTRYPOINT ["java","-jar","opt/ha-mqtt-client.jar"]