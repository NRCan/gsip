# build
FROM maven:3.8-openjdk-18-slim AS build

# Latest Docker Maven - prob too new
#---------------------------------------------------
# FROM maven AS build

COPY src /usr/src/gsip/src
COPY WebContent /usr/src/gsip/WebContent
COPY pom.xml /usr/src/gsip

# Lovely NRCan, etc. Certifcates
#---------------------------------------------------
COPY certs /usr/src/gsip/certs
RUN mkdir -p /usr/local/share/ca-certificates/nrcanca
COPY certs /usr/local/share/ca-certificates/nrcanca

RUN update-ca-certificates

# Must get the cert for maven repo or no go!!!!
#---------------------------------------------------
COPY certs-maven/repo.maven.apache.org.crt /usr/src/gsip/
RUN keytool -noprompt -storepass changeit -keypass changeit -importcert -keystore $JAVA_HOME/lib/security/cacerts -file /usr/src/gsip/repo.maven.apache.org.crt -alias "root_cert"


WORKDIR /usr/src/gsip
RUN mkdir -p /root/.m2

# RUN mvn -f /usr/src/gsip/pom.xml clean package
RUN mvn clean package

#tomcat 10
###
# Expose ports
###
FROM tomcat:jre11-openjdk-slim-buster AS app

EXPOSE 8080 8443

WORKDIR ${CATALINA_HOME}

COPY --from=build /usr/src/gsip/target/gsip.war /usr/local/tomcat/webapps/
HEALTHCHECK CMD curl --fail http://localhost:8080/gsip/id/x/x || exit 1


