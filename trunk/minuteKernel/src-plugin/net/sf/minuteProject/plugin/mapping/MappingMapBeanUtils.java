package net.sf.minuteProject.plugin.mapping;

import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.loader.mapping.node.Bean;
import net.sf.minuteProject.loader.mapping.node.BeanAttribute;
import net.sf.minuteProject.loader.mapping.node.BeanMap;
import net.sf.minuteProject.loader.mapping.node.BeanMapping;
import net.sf.minuteProject.loader.mapping.node.BeanMappingProperties;
import net.sf.minuteProject.loader.mapping.node.BeanMappingProperty;
import net.sf.minuteProject.loader.mapping.node.BeanMappings;
import net.sf.minuteProject.loader.mapping.node.Beans;
import net.sf.minuteProject.utils.CommonUtils;
import net.sf.minuteProject.utils.FormatUtils;

public class MappingMapBeanUtils {
	
	public static final String BEAN_OBJECT_TEMPLATE = "BeanObject";
	
	public String getTypeFormObjectVariable (String variable, String objectName, BeanMapping beanMapping) {
		Bean bean = getBean(beanMapping, objectName);
		for (BeanAttribute att : bean.getAttributes()) {
			if (att.getName().equals(variable))
				return getType(att);
		}
		return "NOT TYPE FOUND";
	}
	
	private String getType (BeanAttribute beanAttribute) {
		String type = beanAttribute.getType();
		if (type!=null && type.equals(""))
			return beanAttribute.getRefType();
		return type;
	}
	
	public String getPackageName(BeanAttribute beanAttribute) {
		String packageName = beanAttribute.getPackageName();
		if (!packageName.equals(""))
			return packageName;
		Bean attributeBean = getBean(beanAttribute);
		return attributeBean.getPackageName();
	}
	public String getPackageName(BeanMapping beanMapping, Template template) {
		return beanMapping.getPackageName();
	}
	
	public String getPackageForBean (BeanMapping beanMapping, String beanName) {
		return getPackageForBean(getBean(beanMapping, beanName));
	}
	
	public String getPackageForBean (Bean bean) {
		if (bean!=null)
			return bean.getPackageName();
		return "NO_PACKAGE_SPECIFIED";
	}
	
	public Bean getBean (BeanMapping beanMapping, String beanName) {
		BeanMap beanMap = beanMapping.getBeanMap();
		for (Bean bean: beanMap.getBeans().getBeans()) {
			if (bean.getName().equals(beanName))
				return bean;
		}
		return null;
	}
	
	public Bean getBean (BeanAttribute beanAttribute) {
		
//		BeanMap beanMap = beanMapping.getBeanMap();
		for (Bean bean: beanAttribute.getBeanMap().getBeans().getBeans()) {
			if (bean.getName().equals(beanAttribute.getRefType()))
				return bean;
		}
		// no reference found
		return null;
	}
	
	public String getBeanClassName (BeanAttribute beanAttribute, Template template) {
		Bean bean = getBean(beanAttribute);
		if (bean!=null) {
//			#set($beanObjectTemplate=$commonUtils.getTargetTemplate($template, "BeanObject"))
//			#if ($typeAlias ==null)
//			#set($typeClass=$commonUtils.getClassName($typeAlias, $beanObjectTemplate))
			Template templateTarget = CommonUtils.getTargetTemplate(template, BEAN_OBJECT_TEMPLATE);
			return CommonUtils.getClassName(bean, templateTarget);
		}
		return FormatUtils.firstUpperCase(beanAttribute.getRefType());
	}
	
	public BeanMappingProperty[] getSimpleProperties (BeanMappingProperties beanMappingProperties) {
		return beanMappingProperties.getMapPropertysArray();
	}

	public boolean isCallMapping (BeanMappingProperty beanMappingProperty) {
		if (beanMappingProperty.getCallMapping()!=null 
			&& !beanMappingProperty.getCallMapping().equals(""))
			return true;
		return false;
	}

	public boolean isLoop (BeanMappingProperty beanMappingProperty) {
		if (beanMappingProperty.getIsLoop()!=null 
			&& !beanMappingProperty.getIsLoop().equals(""))
			return true;
		return false;
	}
	
	public BeanMapping getCalledBeanMapping (BeanMappingProperty beanMappingProperty) {
		if (isCallMapping(beanMappingProperty)) {
			String calledBeanMapping = beanMappingProperty.getCallMapping();
			BeanMappings beanMappings = beanMappingProperty.getBeanMap().getMappings();
			for (BeanMapping beanMapping : beanMappings.getMappings()) {
				if (beanMapping.getName().equals(calledBeanMapping))
					return beanMapping;
			}
		}
		return null; 
	}
	
	public String getMappingPropertyMethodResult (BeanMapping beanMapping, BeanMappingProperty beanMappingProperty) {
//		String whatVariable = getWhatVariable (beanMappingProperty);
//		$formatUtils.getJavaName($mapProperty.what)
		String function = beanMappingProperty.getFunction();
		String what = beanMappingProperty.getWhat();
		String originBeanVariable = FormatUtils.getJavaNameVariable(beanMapping.getOriginBean());
		if (function.equals("")) {
			String whatMappingMethod = FormatUtils.getJavaName(what);
			return originBeanVariable+".get"+whatMappingMethod+"()";
		} else {
			return getFunction (originBeanVariable, function, what);
		}
	}
	
	public String getFunction (String originBeanVariable, String function, String what) {
		if (function.equals("concat")) {
			return getConcatFunction (originBeanVariable, what);
		}
		return "FUNCTION_NOT_AVAILABLE";
	}
	
	private String getConcatFunction(String originBeanVariable, String what) {
		StringTokenizer st = new StringTokenizer(what, ",");
		StringBuffer sb = new StringBuffer();
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			String tokenJavaName = FormatUtils.getJavaName(token);
			sb.append(originBeanVariable);
			sb.append(".");
			sb.append("get");
			sb.append(tokenJavaName);
			sb.append("()");
			if (st.hasMoreTokens())
				sb.append("+");
		}
		return sb.toString();
	}
}
