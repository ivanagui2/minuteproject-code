To generate the demo code for this example proceed as follow:

Prerequisites:
Have a JKD 6 on your machine.

A) Generate the code

1) check that you are using a JAVA jdk 6 : open a command prompt and issue: java -version; if not
1)1) open demo-*.cmd and replace '@rem set JAVA_HOME=..' with 'set JAVA_HOME=<java_home_6_path>'

2) start your sample database
2)1) in <minuteProject install dir>/sample/ open a prompt and execute start-petshop-database.bat

3) in a command prompt: issue demo-*.cmd
The generated code goes to ../output

B) compile the code
Generation is done against a target architecture:
MinuteProject currently propose 2 architecture:
*BSLA : Basic Spring Layer Architecture
For Backend DAO project based on Spring/(hibernate/iBatis/JPA)
*FitNesse

1) If you generate for against BSLA (This is the case if you execute demo-hibernate.cmd, demo-ibatis.cmd, demo-jpa.cmd) 
Then add the /target/mp-bsla/**.jar (mp-bsla-xx.jar and the jar in /dep)

2) If you generate for against FitNesse (This is the case if you execute demo-fitnesse.cmd) 
Then add the /target/mp-fitnesse/**.jar (mp-bsla-xx.jar and the jar in /dep)

Rem: The dependencies are shipped, but they may not correspond to the latest version.
It will later be change when using maven.

C) test the generated code
1) If you generate for against BSLA
TODO more information soon on http://minuteproject.blogspot.com

    // main spring configuration
	private final String config = "classpath:sf/net/mp/demo/petshop/factory/configuration/spring-config-Hibernate-BE-Petshop.xml";
	
	protected void init() {
		beanFactory = new ClassPathXmlApplicationContext (config);
		productDao = (ProductDao)beanFactory.getBean("productDao");
		// enjoy the advanced CRUD functions
	}

2) If you generate for against FitNesse
TODO more information soon on http://minuteproject.blogspot.com

It generates 2 FitNesse wiki snippet (one for Insert/Update/Delete; one for Select) and their associated fixtures.

Copy the wiki snippet into the FitNesse wiki structure (FitNesseRoot/FitNesse/)

Add the wiki fixture classes to your development source (do not forget the dependencies in /target/mp-fitnesse)

Now you can copy in you business wiki scenario the wiki snippet for CRUD operation:
* First copy the snippet for connection param to your DB (it is in output/TestFitNesse/DbConnection)
* Copy the CRUD snippet to populate/update/delete and query your db







