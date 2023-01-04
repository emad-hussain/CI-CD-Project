# CI-CD-Project
This GitHub repository is a java application for practicing CI/CD using jenkins jobs. First job is created and configured manually which perform tests using MAVEN at the code pulled from GitHub repository and then builds the whole code. The GitHub repository is configured with Jenkins to trigger the action whenever a change is commited by developer using SCM Polling functionality. If build succeeds, the deployment process will be initiated and automated by Ansible and build is deployed to production and staging environment with the help of Docker Container with Apache Tomcat (a webserver for java base applications) already installed. Parameterized building allows to push latest build into action to either Production or Staging server.

Further, DSL (Domain Specific Language) script is used to automate the management of Jenkin Jobs allowing developers to create new Jobs via coding, without relying upon System Admins or DevOps Eningeers or any manual intervention.
