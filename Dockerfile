FROM maven:3.6.3-jdk-8-slim AS builder
COPY 
RUN cd /gsip && mvn package


FROM tomcat:8.5.40-jre8-alpine AS deploy
EXPOSE 8080 8080
COPY --from=builder /gsip/target/gsip.war /usr/local/tomcat/webapps/

