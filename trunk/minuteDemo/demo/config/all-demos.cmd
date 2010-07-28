@rem standard
echo "standard generations"
call demo-fitnesse.cmd
call demo-hibernate.cmd
call demo-ibatis.cmd
call demo-jpa.cmd

@rem standard
echo "last feature generations"
call demo-last-features-spring-hibernate.cmd
call demo-last-features-spring-jpa.cmd

@rem standard
echo "incubator generations"
call demo-draft-features-vaadin-spring-hibernate.cmd
call demo-last-features-openxava.cmd
