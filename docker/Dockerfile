#FROM tomcat:8.5.40-jre8-alpine
FROM nirmata/tomcat9-alpine-jre12-openj9

###
# Expose ports
###

EXPOSE 8080 8443

WORKDIR ${CATALINA_HOME}

COPY gsip.war /usr/local/tomcat/webapps/
HEALTHCHECK CMD curl --fail http://localhost:8080/gsip/id/x/x || exit 1


