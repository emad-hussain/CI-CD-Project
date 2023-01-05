FROM tomcat:9-jdk17-corretto
COPY ./ROOT.war /usr/local/tomcat/webapps
RUN cp -r /usr/local/tomcat/webapps.dist/* /usr/local/tomcat/webapps/
RUN rm -r /usr/local/tomcat/webapps/ROOT
