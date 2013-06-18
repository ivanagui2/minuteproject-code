package net.sf.minuteProject.utils.catalog;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import net.sf.minuteProject.configuration.bean.Configuration;
import net.sf.minuteProject.configuration.bean.Targets;
import net.sf.minuteProject.exception.MinuteProjectException;


public class TechnologyCatalogUtilsTest {

	Configuration configuration;
	
	@Before
	public void init() {
		configuration = new Configuration();
		Targets t = new Targets();
		t.setCatalogEntry("JPA2");
		t.setOutputdirRoot("../../JPA2");
		t.setTemplatedirRoot("../../template");
		configuration.setTargets(t);
	}
	
	@Test
	public void testIt () throws MinuteProjectException {
		Targets targets = TechnologyCatalogUtils.getTargets(
				configuration.getTargets().getCatalogEntry(), 
				"catalog", 
				configuration.getTargets().getOutputdirRoot(), 
				configuration.getTargets().getTemplatedirRoot());
		assertNotNull(targets);
		assertTrue(targets.getTargets().size()>0);
	}
}
