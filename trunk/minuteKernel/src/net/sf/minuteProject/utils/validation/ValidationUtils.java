package net.sf.minuteProject.utils.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.enrichment.validation.EntityValidationTwoFieldDependency;
import net.sf.minuteProject.configuration.bean.enrichment.validation.FieldValidationAmongValue;
import net.sf.minuteProject.configuration.bean.enrichment.validation.FieldValidationPattern;
import net.sf.minuteProject.configuration.bean.enrichment.validation.FieldValidationRangeChar;
import net.sf.minuteProject.configuration.bean.enrichment.validation.FieldValidationRangeValue;
import net.sf.minuteProject.configuration.bean.enrichment.validation.Validation;
import net.sf.minuteProject.configuration.bean.enrichment.validation.ValidationPattern;
import net.sf.minuteProject.utils.FormatUtils;

public class ValidationUtils {

	public enum JavaReference {
		IMPORT, DECLARE;
		public boolean isImport() {
			return this == IMPORT;
		}
	}
	
	public static List<String> getJavaImportValidationAnnotations(Validation validation) {
		return getJavaValidation(validation, JavaReference.IMPORT).stream().distinct().collect(Collectors.toList());
	}
	
	public static List<String> getJavaValidationAnnotations(Validation validation) {
		return getJavaValidation(validation, JavaReference.DECLARE).stream().distinct().collect(Collectors.toList());
	}
	
	public static List<String> getJavaValidation(Validation validation, JavaReference javaReference) {
		List<String> list = new ArrayList<>();
		if (validation instanceof FieldValidationPattern) {
			fillList(list, (FieldValidationPattern)validation, javaReference);
		}
		if (validation instanceof FieldValidationRangeChar) {
			fillList(list, (FieldValidationRangeChar)validation, javaReference);
		}
		if (validation instanceof FieldValidationRangeValue) {
			fillList(list, (FieldValidationRangeValue)validation, javaReference);
		}
		if (validation instanceof FieldValidationAmongValue) {
			fillList(list, (FieldValidationAmongValue)validation, javaReference);
		}
		if (validation instanceof EntityValidationTwoFieldDependency) {
			fillList(list, (EntityValidationTwoFieldDependency)validation, javaReference);
		}
		if (validation instanceof ValidationPattern) {
			fillList(list, (ValidationPattern)validation, javaReference);
		}
		return list;
	}

	private static void fillList(List<String> list, ValidationPattern validation, JavaReference javaReference) {
		String type = validation.getType();
		if (type!=null) {
			String format = format(type);
			list.add((javaReference.isImport())?"javax.validation.constraints."+format:"@"+format);
		}
	}
	
	private static String format(String type) {
		return FormatUtils.firstUpperCase(type.toLowerCase());
	}

	private static void fillList(List<String> list, EntityValidationTwoFieldDependency validation, JavaReference javaReference) {
		if (validation.getFirstFieldName()!=null && validation.getSecondFieldName()!=null && validation.getOperand()!=null) {
			if (!javaReference.isImport()) {
				list.add("@FieldCompare (first=\""+validation.getFirstFieldName()+"\", second=\""+validation.getSecondFieldName()+"\", operator=CompareOperatorEnum."+validation.getOperand()+")");
			}
		}
	}
	
	private static void fillList(List<String> list, FieldValidationAmongValue validation, JavaReference javaReference) {
		FieldValidationPattern pattern = new FieldValidationPattern();
		pattern.setRegex(validation.getValues().replace(",", "|"));
		fillList (list, pattern, javaReference);
	}

	private static void fillList(List<String> list, FieldValidationRangeValue validation, JavaReference javaReference) {
		if (validation.getMax()>0){
			list.add((javaReference.isImport())?"javax.validation.constraints.Max":"@Max ("+validation.getMax()+")");
		}
		if (validation.getMin()>0){
			list.add((javaReference.isImport())?"javax.validation.constraints.Min":"@Min ("+validation.getMin()+")");
		}
	}
	
	private static void fillList(List<String> list, FieldValidationRangeChar validation, JavaReference javaReference) {
		if (validation==null 
				|| (validation.getMax()!=null
				&& validation.getMin()!=null)){
			return;
		}
		if (javaReference.isImport()) {				
			if (validation.getMax()!=null || validation.getMin()!=null){
				list.add("javax.validation.constraints.Size");
			}
		} else {
			if (validation.getMax()!=null && validation.getMin()!=null){
				list.add("@Size (min="+validation.getMin()+", max="+validation.getMax()+")");
			} else if (validation.getMin()!=null) {
				list.add("@Size (min="+validation.getMin()+")");
			} else {
				list.add("@Size (max="+validation.getMax()+")");
			}
		}
	}

	private static void fillList(List<String> list, FieldValidationPattern validation, JavaReference javaReference) {
		if (StringUtils.isNotEmpty(validation.getRegex())){//TODO and regex correct
			String s = (javaReference.isImport())?"javax.validation.constraints.Pattern":"@Pattern (regexp=\""+validation.getRegex()+"\", flags=Pattern.Flag.CASE_INSENSITIVE)";
			list.add(s);
		}
		if (validation.getValidationType()!=null){
			switch (validation.getValidationType()) {
				case EMAIL:
					list.add ((javaReference.isImport())?"javax.validation.constraints.Email":"@Email");
					break;
			}
		}
	}
	
	
}
