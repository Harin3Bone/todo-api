FROM openjdk:17-alpine as builder
MAINTAINER HARIN3BONE

WORKDIR /workspace/app

COPY mvnw .
COPY .mvn/ .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw clean install -DskipTests

FROM openjdk:17-alpine as runner
MAINTAINER HARIN3BONE

WORKDIR /workspace/app
EXPOSE 8080
COPY --from=builder /workspace/app/target/*.jar /workspace/app/app.jar

ARG SERVICE_KEY
ARG SERVICE_SECRET
ARG MYSQL_NAME
ARG MYSQL_DATABASE
ARG MYSQL_USER
ARG MYSQL_PASSWORD

ENV SERVICE_KEY $SERVICE_KEY
ENV SERVICE_SECRET $SERVICE_SECRET
ENV MYSQL_NAME $MYSQL_NAME
ENV MYSQL_DATABASE $MYSQL_DATABASE
ENV MYSQL_USER $MYSQL_USER
ENV MYSQL_PASSWORD $MYSQL_PASSWORD

CMD java -jar /workspace/app/app.jar \
    --SERVICE_KEY=$(echo ${SERVICE_KEY}) \
    --SERVICE_SECRET=$(echo ${SERVICE_SECRET}) \
    --MYSQL_NAME=$(echo ${MYSQL_NAME}) \
    --MYSQL_DATABASE=$(echo ${MYSQL_DATABASE}) \
    --MYSQL_USER=$(echo ${MYSQL_USER}) \
    --MYSQL_PASSWORD=$(echo ${MYSQL_PASSWORD})