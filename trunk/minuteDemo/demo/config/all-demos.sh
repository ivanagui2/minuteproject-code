#rem standard
echo "standard generations"
./demo-fitnesse.sh
./demo-hibernate.sh
./demo-ibatis.sh
./demo-jpa.sh
./demo-last-features-openxava.sh
./demo-JPA2.sh

#rem last feature generations
echo "last feature generations"
./demo-last-features-spring-hibernate.sh
./demo-last-features-spring-jpa.sh

#rem incubator generations
echo "incubator generations"
./demo-draft-features-vaadin-spring-hibernate.sh
./demo-last-features-roo.sh
./demo-last-features-grails.sh
./demo-last-features-play.sh
./demo-solr.sh
