package net.sf.minuteProject.architecture.bsla.unittest;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

public abstract class AbstractTransactionalDataSourceSpringContextBslaTests 
	extends AbstractTransactionalDataSourceSpringContextTests {
    
	protected BasicDataSource datasource;
	
	protected AbstractTransactionalDataSourceSpringContextBslaTests() {
        setPopulateProtectedVariables(true);
        setDefaultRollback(true);
    }    
	
    /**
     * Initialize the database
     *
     * @throws Exception
     */
    protected final void onSetUpInTransaction()
            throws Exception {
        //jdbcTemplate = new JdbcTemplate(dataSource);
    	onSetUpInBslaTestTransaction();
    }	
    
    public abstract void onSetUpInBslaTestTransaction();
    
}
