FROM unidata/tomcat-docker:8.5


###
# Expose ports
###

EXPOSE 8080 8443

WORKDIR ${CATALINA_HOME}

COPY target/gsip.war /usr/local/tomcat/webapps/

###
# Inherited from parent container
###

ENTRYPOINT ["/entrypoint.sh"]

###
# Start container
###

CMD ["catalina.sh", "run"]