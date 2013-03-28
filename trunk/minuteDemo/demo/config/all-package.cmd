echo install mp-bsla in maven
cd ..\..\target\mp-bsla
call install_maven.cmd

echo bsla-jpa2
cd ..\..\demo\output
cd bsla-jpa2\petshop
call mvn clean package

echo CXF 
cd ..\..\cxf\petshop
call mvn clean package

echo JOOQ TECH
cd ..\..\JOOQ\jooq-tech
call mvn clean package

echo JOOQ petshop
cd ..\..\JOOQ\petshop
call mvn clean package

echo JPA2
cd ..\..\JPA2
call mvn clean package

echo JSF 
cd ..\JSF\petshop
call mvn clean package

echo Mvn Spring Hibernate 
cd ..\..\MvnSpringHibernate
call mvn clean package

echo Mvn Spring JPA 
cd ..\MvnSpringJpa
call mvn clean package

echo REST-JEE
cd ..\REST-JEE\petshop
call mvn clean package

echo REST-SPRING MVC
cd ..\..\rest-springmvc\petshop
call mvn clean package

echo WebService
cd ..\..\WS-JEE\petshop
call mvn clean package

echo JSF-Spring
cd ..\..\JSF-Spring
call mvn clean package


