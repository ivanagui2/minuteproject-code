cd ..\..\target\mp-bsla
call install_maven.cmd
cd ..\..\demo\output
cd bsla-jpa2\petshop
call mvn clean package
cd ..\..\cxf\petshop
call mvn clean package
cd ..\..\JOOQ\jooq-tech
call mvn clean package
cd ..\..\JOOQ\petshop
call mvn clean package
cd ..\..\JPA2
call mvn clean package
cd ..\..\JSF\petshop
call mvn clean package
cd ..\..\MvnSpringHibernate
call mvn clean package
cd ..\..\REST-JEE\petshop
call mvn clean package
cd ..\..\rest-springmvc\petshop
call mvn clean package
cd ..\..\WS-JEE\petshop
call mvn clean package
cd ..\MvnSpringJPA
call mvn clean package
cd ..\JSF-Spring
call mvn clean package

