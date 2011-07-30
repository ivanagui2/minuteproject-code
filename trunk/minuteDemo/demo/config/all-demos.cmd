@rem standard
echo "standard generations"
call demo-fitnesse.cmd
call demo-hibernate.cmd
call demo-ibatis.cmd
call demo-jpa.cmd
call demo-last-features-openxava.cmd
call demo-JPA2.cmd

@rem last feature generations
echo "last feature generations"
call demo-last-features-spring-hibernate.cmd
call demo-last-features-spring-jpa.cmd

@rem incubator generations
echo "incubator generations"
call demo-JSF-primefaces.cmd
call demo-draft-features-vaadin-spring-hibernate.cmd
call demo-last-features-roo.cmd
call demo-last-features-grails.cmd
call demo-last-features-play.cmd
call demo-solr.cmd
