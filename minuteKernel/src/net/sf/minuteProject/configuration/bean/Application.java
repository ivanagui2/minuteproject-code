package net.sf.minuteProject.configuration.bean;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.utils.ModelUtils;
import net.sf.minuteProject.utils.StringUtils;

public class Application extends GeneratorQualifier{

	private List<Model> models;
	
	public List<Model> getModels() {
		if (models == null) models = new ArrayList<Model>();
		return models;
	}
	
	public void addModel (Model model) {
		model.setApplication(this);
		getModels().add(model);
	}
	
//	public String getTechnicalPackage(Template template) {
	//		String packageRoot2 = ModelUtils.getPackageRoot(template);
	//		StringBuffer sb = new StringBuffer(packageRoot2);
		/*
		if ((template.getAddModelDirName()==null 
			|| template.getAddModelDirName().equals("")
			|| template.getAddModelDirName().equals("true"))	
			&& 
			!(template.getApplicationSpecific()!=null && template.getApplicationSpecific().equals("true"))
			) {
				sb.append((StringUtils.isEmpty(packageRoot2)? getName() :"."+getName()));
			}
			*/
	//		boolean isEmpty = StringUtils.isEmpty(sb.toString());
	//		String technicalPackage = template.getTechnicalPackage();
	//		if (technicalPackage!=null && !technicalPackage.equals(""))
			//			sb.append(isEmpty?technicalPackage:"."+technicalPackage);
	//		return sb.toString();
	//}
	
}
