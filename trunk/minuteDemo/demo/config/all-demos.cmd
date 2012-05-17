@rem standard
echo "standard generations"
call demo-fitnesse.cmd
call demo-hibernate.cmd
call demo-ibatis.cmd
call demo-jpa.cmd
call demo-last-features-openxava.cmd
call demo-JPA2.cmd
call demo-JPA2-BSLA.cmd

@rem last feature generations
echo "last feature generations"
call demo-last-features-spring-hibernate.cmd
call demo-last-features-spring-jpa.cmd

@rem incubator generations
echo "incubator generations"
call demo-JOOQ.cmd
call demo-JOOQ-tech.cmd
call demo-REST-JEE.cmd
call demo-REST-CXF-Spring.cmd
call demo-REST-SpringMVC.cmd
call demo-WS-JEE.cmd
call demo-JSF-primefaces.cmd
call demo-JSF-Spring-primefaces.cmd
call demo-draft-features-vaadin-spring-hibernate.cmd
call demo-last-features-roo.cmd
call demo-last-features-grails.cmd
call demo-last-features-play.cmd
call demo-solr.cmd
