package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.system.Plugin;
import net.sf.minuteProject.configuration.bean.target.ImportTargets;
import net.sf.minuteProject.configuration.bean.target.TargetParams;
import net.sf.minuteProject.utils.io.FileUtils;

/**
 * @author Florian Adler
 *
 */
public class Targets extends AbstractConfigurationRoot {
	
	private Configuration configuration;
	private List<Target> targets;

	public List<Target> getTargets() {
		if (targets==null)
			targets = new ArrayList<Target>();
		return targets;
	}

	public void setTargets(List<Target> targets) {
		this.targets = targets;
	}
	
	public void addTargets(Target target) {
		setTarget(target, configuration);
		getTargets().add(target);
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	
	
}
