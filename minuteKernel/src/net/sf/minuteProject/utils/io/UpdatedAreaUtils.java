package net.sf.minuteProject.utils.io;

import groovyjarjarasm.asm.ClassAdapter;

import java.util.Map;

import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.model.data.Column;

public class UpdatedAreaUtils {

	public static final String MP_MANAGED_STOP_GENERATING = "MP-MANAGED-STOP-GENERATING";
	public static final String MP_MANAGED_ADDED_AREA = "MP-MANAGED-ADDED-AREA";
	public static final String MP_MANAGED_ADDED_AREA_BEGIN_APPENDIX = "-BEGINNING";
	public static final String MP_MANAGED_ADDED_AREA_END_APPENDIX = "-ENDING";
	public static final String MP_MANAGED_REFERENCE_MARKER = "#";
	
	public static final String MP_MANAGED_ADDED_AREA_BEGINNING = MP_MANAGED_ADDED_AREA+MP_MANAGED_ADDED_AREA_BEGIN_APPENDIX;
	public static final String MP_MANAGED_ADDED_AREA_ENDING = MP_MANAGED_ADDED_AREA+MP_MANAGED_ADDED_AREA_END_APPENDIX;

	public static final String MP_MANAGED_UPDATABLE_BEGINNING = "MP-MANAGED-UPDATABLE-BEGINNING";
	public static final String MP_MANAGED_UPDATABLE_ENDING = "MP-MANAGED-UPDATABLE-ENDING";

	public static final String STATUS_DISABLE_APPENDIX = "-DISABLE";
	public static final String STATUS_ENABLE_APPENDIX = "-ENABLE";

	public static final String MP_MANAGED_UPDATABLE_BEGINNING_ENABLE = MP_MANAGED_UPDATABLE_BEGINNING+STATUS_ENABLE_APPENDIX;
	public static final String MP_MANAGED_UPDATABLE_BEGINNING_DISABLE = MP_MANAGED_UPDATABLE_BEGINNING+STATUS_DISABLE_APPENDIX;
	private static final String IMPORT = "import";
	private static final String IMPLEMENTATION = "implementation";
	private static final String CLASS_ANNOTATION = "class-annotation";
	private static final String FIELD_ANNOTATION = "field-annotation";
	private static final String CONNECTOR = "-";
	private static final String GETTER_SETTER = "GETTER-SETTER";
	private static final String ATTRIBUTE = "ATTRIBUTE";
	private static final String CONSTRUCTOR_WITH_FIELDS = "CONSTRUCTOR-WITH-FIELDS";

	public static String getFieldAnnotationSnippet (Template template, Column column, Map<String, String> updatedAreas) {
		return getAddedAreaSnippet(template, updatedAreas, getColumnAnnotation(column));
	}
	
	private static String getColumnAnnotation(Column column) {
		return column.getName()+CONNECTOR+FIELD_ANNOTATION;
	}

	public static String getClassAnnotationSnippet (Template template, Map<String, String> updatedAreas) {
		return getAddedAreaSnippet(template, updatedAreas, CLASS_ANNOTATION);
	}
	
	public static String getImplementationSnippet (Template template, Map<String, String> updatedAreas) {
		return getAddedAreaSnippet(template, updatedAreas, IMPLEMENTATION);
	}
	
	public static String getImportSnippet (Template template, Map<String, String> updatedAreas) {
		return getAddedAreaSnippet(template, updatedAreas, IMPORT);
	}

	public static String getAddedAreaSnippet (Template template, Map<String, String> updatedAreas, String key) {
		String s = getSnippet (template, updatedAreas, key);
		if (s!=null) return s;
		return getAddedAreaSnippet (template, key);
	}
	
	public static String getSnippet (Template template, Map<String, String> updatedAreas, String key) {
		String s = getChunk (updatedAreas, key);
		if (s!=null) return s;
		return getAddedAreaSnippet (template, key);
	}
	
	private static String getChunk(Map<String, String> updatedAreas, String key) {
		if (updatedAreas==null || key==null) return null;
		return updatedAreas.get(key);
	}

	public static String getAddedAreaSnippet(Template template, String s) {
		StringBuffer sb = new StringBuffer();
		sb.append(comment(template, MP_MANAGED_ADDED_AREA_BEGINNING+ " "+MP_MANAGED_REFERENCE_MARKER+s+MP_MANAGED_REFERENCE_MARKER+"\n"));
		sb.append(comment(template, MP_MANAGED_ADDED_AREA_ENDING+ " "+MP_MANAGED_REFERENCE_MARKER+s+MP_MANAGED_REFERENCE_MARKER+"\n"));
		return sb.toString();
	}

	public static String getUpdatedAreaBeginSnippet(Template template, String s) {
		StringBuffer sb = new StringBuffer();	
		sb.append(MP_MANAGED_UPDATABLE_BEGINNING_DISABLE+ " "+MP_MANAGED_REFERENCE_MARKER+s+MP_MANAGED_REFERENCE_MARKER+"\n");
		return comment(template, sb.toString());
	}
	
	private static String comment(Template template, String string) {
		return template.getBeginningCommentSnippet()+string+template.getEndingCommentSnippet();
	}

	public static String getUpdatedAreaEndSnippet(Template template, String s) {
		StringBuffer sb = new StringBuffer();
		sb.append("// "+MP_MANAGED_UPDATABLE_ENDING+"\n");
		return sb.toString();
	}	

	public static UpdatedAreaHolder getConstructorWithFieldSnippet(Template template, Map<String, String> updatedAreas) {
		return getBeginSnippet (template, getConstructorWithFieldSnippet(), updatedAreas);
	}
	
	private static String getConstructorWithFieldSnippet() {
		return CONSTRUCTOR_WITH_FIELDS;
	}

	public static UpdatedAreaHolder getColumnAttributeBeginSnippet(Template template, Column column, Map<String, String> updatedAreas) {
		return getBeginSnippet (template, getColumnAttribute(column), updatedAreas);
	}
	
	public static UpdatedAreaHolder getColumnGetterSetterBeginSnippet(Template template, Column column, Map<String, String> updatedAreas) {
		return getBeginSnippet (template, getColumnGetterSetter(column), updatedAreas);
	}

	private static String getColumnAttribute(Column column) {
		return ATTRIBUTE+CONNECTOR+column.getName();
	}
	
	private static String getColumnGetterSetter(Column column) {
		return GETTER_SETTER+CONNECTOR+column.getName();
	}

	public static UpdatedAreaHolder getBeginSnippet(Template template, String key, Map<String, String> updatedAreas) {
		UpdatedAreaHolder updatedAreaHolder = new UpdatedAreaHolder();
		String s = getChunk (updatedAreas, key);
		if (s!=null) {
			updatedAreaHolder.setUpdated(true);
			updatedAreaHolder.setBeginSnippet(s);
		} else {
			updatedAreaHolder.setUpdated(false);
			updatedAreaHolder.setBeginSnippet(getUpdatedAreaBeginSnippet(template, key));
		}
		return updatedAreaHolder;
	}
	

	public static String getColumnEndSnippet(Template template, Column column) {
		
		return getUpdatedAreaEndSnippet(template, column.getName());
	}
	

}
