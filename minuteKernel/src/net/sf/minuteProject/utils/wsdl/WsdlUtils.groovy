package net.sf.minuteProject.utils.wsdl;

import net.sf.minuteProject.configuration.bean.Model;

public class WsdlUtils {

//	#set($wsdlDirectory=$wsdlUtils.getWsdlDirectory($model))
//	#set($wsdlFile=$wsdlUtils.getWsdlFile($model))
	static getWsdlDirectory (Model model) {
		model.getWebServiceModel().getWsdl().getDir()
	}
	
	static getWsdlFile (Model model) {
		model.getWebServiceModel().getWsdl().getFile()
	}
}
