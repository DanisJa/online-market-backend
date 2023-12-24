FROM amazoncorretto:17-alpine
ARG JAR_FILE=target/*.jar
COPY ./online-market-1.0.0-PRODUCTION.jar online-market.jar
ENTRYPOINT ["java","-jar","/online-market.jar"]