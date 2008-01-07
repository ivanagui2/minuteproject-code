package net.sf.minuteProject.architecture.bsla.factory;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.commons.logging.Log;

public class MyProjectFactory {
    private static BeanFactory beanFactory;
    private static boolean isLoaded = false;
    private static String config;

    public static String getConfig() {
    	if (config==null)
    		return "classpath:spring-myproject-config.xml";
		return "classpath:"+config;
	}

	public static void setConfig(String config) {
		MyProjectFactory.config = config;
	}

	/**
     * Returns a bean factory
     * @return BeanFactory
     * @throws SADBELException
     */
    private static BeanFactory getFactory() {
        if (!isLoaded) {
            loadFactory();
        }

        return beanFactory;
    }

    /**
     * Instantiates a bean
     *
     * @param name String
     * @return Object
     */
    public static Object getBean(String name) {
        return getFactory().getBean(name);

    }

    /**
     * Loads an configures a Bean Factory
     * @throws SADBELException
     */

    private static void loadFactory() {
        try {
            String[] configLocations = new String[] {"classpath:spring-myproject-config.xml"};
            // Instantiate the object but do not process yet;
            beanFactory = new ClassPathXmlApplicationContext(configLocations, false);
            isLoaded = true;
            // Process the configuration now so that no infinite loop happens
            ((ClassPathXmlApplicationContext) beanFactory).refresh();
            System.out.println("load of the spring factory done");
        } catch (Exception ex) {
            System.out.println("Could not load Spring myproject Bean Factory");
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Returns a logger for a given category
     *
     * @param category String
     * @return Log
     
    public static Log getLogger(String category) {
        Log logger = null;
        try {
            SystemLoggerFactory sytemLoggerFactory = (SystemLoggerFactory)
                getFactory().getBean(
                    "systemLoggerFactory");
            logger = sytemLoggerFactory.getLogger(category);
        } catch (Exception ex) {
            System.err.println("Unexpected exception caught when retrieving logger");
            ex.printStackTrace();
            throw new SystemSADBELException(
                "Unexpected exception caught when retrieving logger",
                "Unexpected exception caught when retrieving logger", ex);
        }
        return logger;
    }
*/
    /**
     * Returns a property reader
     *
     * @return PropertyReader
    
    public static PropertyReader getPropertyReader() {
        PropertyReader reader = null;
        try {
            PropertyReaderFactory propertyReaderFactory = (PropertyReaderFactory) getFactory().getBean(
                "propertyReaderFactory");
            reader = propertyReaderFactory.getPropertyReader();
        } catch (Exception ex) {
            throw new SystemSADBELException(
                "Unexpected exception caught when retrieving property reader",
                "Unexpected exception caught when retrieving property reader", ex);
        }
        return reader;
    } */
}
