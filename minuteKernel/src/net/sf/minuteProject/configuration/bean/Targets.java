package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.system.GenerationAction;
import net.sf.minuteProject.exception.MinuteProjectException;
import net.sf.minuteProject.utils.io.FileUtils;

import org.apache.commons.lang.StringUtils;

/**
 * @author Florian Adler
 *
 */
public class Targets extends AbstractConfiguration {
	
	private String outputdirRoot, catalog, catalogEntry, templatedirRoot;
	public static final String productionPath = "../../template";
	public static final String developmentPath = "../minuteTemplate/template";
	private AbstractConfigurationRoot abstractConfigurationRoot;
	private List<Target> targets;
	private GenerationAction postGenerationAction;

	public List<Target> getTargets() {
		if (targets==null)
			targets = new ArrayList<Target>();
		return targets;
	}

	public void setTargets(List<Target> targets) {
		this.targets = targets;
	}
	
	public AbstractConfigurationRoot getAbstractConfigurationRoot() {
		return abstractConfigurationRoot;
	}
	public void setAbstractConfigurationRoot(
			AbstractConfigurationRoot abstractConfigurationRoot) {
		this.abstractConfigurationRoot = abstractConfigurationRoot;
	}
	
	public void addTarget(Target target) {
		target.setTargets(this);
		target.setAbstractConfigurationRoot(getAbstractConfigurationRoot());
		getTargets().add(target);
	}

	public String getOutputdirRoot() {
		if (hasCatalogEntry() && outputdirRoot==null) {
			String output = FileUtils.exists(productionPath)?"../output/":"../../output/";
			outputdirRoot = output+getCatalogEntry()+"/"+((Configuration)getAbstractConfigurationRoot()).getModel().getName(); // default
		}
		return outputdirRoot;
	}

	private boolean hasCatalogEntry() {
		return !StringUtils.isEmpty(catalogEntry);
	}

	public void setOutputdirRoot(String outputdirRoot) {
		this.outputdirRoot = outputdirRoot;
	}

	public String getTemplatedirRoot() throws MinuteProjectException {
		if (hasCatalogEntry() && templatedirRoot==null) {
			// try default for release
			if (FileUtils.exists(productionPath))
				return productionPath;
			// try default for dev
			if (FileUtils.exists(developmentPath))
				return developmentPath;			
			throw new MinuteProjectException("targets node with catalog entry does not seems to have correct template association");
		}
		return templatedirRoot;
	}

	public void setTemplatedirRoot(String templatedirRoot) {
		this.templatedirRoot = templatedirRoot;
	}

	public GenerationAction getPostGenerationAction() {
		return postGenerationAction;
	}

	public void setPostGenerationAction(GenerationAction postGenerationAction) {
		postGenerationAction.setTargets(this);
		this.postGenerationAction = postGenerationAction;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	
	public String getCatalogEntry() {
		return catalogEntry;
	}

	public void setCatalogEntry(String catalogEntry) {
		this.catalogEntry = catalogEntry;
	}

	
}
