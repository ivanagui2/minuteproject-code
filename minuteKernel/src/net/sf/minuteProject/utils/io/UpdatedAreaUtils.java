package net.sf.minuteProject.utils.io;

import java.util.Map;

import net.sf.minuteProject.configuration.bean.model.data.Column;

public class UpdatedAreaUtils {

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
	private static final String IMPORT = "import";
	private static final String IMPLEMENTATION = "implementation";

	public static String getImplementationSnippet (Map<String, String> updatedAreas) {
		String s = getSnippet (updatedAreas, IMPLEMENTATION);
		if (s!=null) return s;
		return getAddedAreaSnippet (IMPLEMENTATION);
	}
	
	public static String getImportSnippet (Map<String, String> updatedAreas) {
		String s = getSnippet (updatedAreas, IMPORT);
		if (s!=null) return s;
		return getAddedAreaSnippet (IMPORT);
	}

	public static String getSnippet (Map<String, String> updatedAreas, String key) {
		String s = getChunk (updatedAreas, key);
		if (s!=null) return s;
		return getAddedAreaSnippet (key);
	}
	
	private static String getChunk(Map<String, String> updatedAreas, String key) {
		if (updatedAreas==null || key==null) return null;
		return updatedAreas.get(key);
	}

	public static String getAddedAreaSnippet(String s) {
		StringBuffer sb = new StringBuffer();
		sb.append("// "+MP_MANAGED_ADDED_AREA_BEGINNING+ " "+MP_MANAGED_REFERENCE_MARKER+s+MP_MANAGED_REFERENCE_MARKER+"\n\n");
		sb.append("// "+MP_MANAGED_ADDED_AREA_ENDING+ " "+MP_MANAGED_REFERENCE_MARKER+s+MP_MANAGED_REFERENCE_MARKER+"\n");
		return sb.toString();
	}

	public static String getUpdatedAreaBeginSnippet(String s) {
		StringBuffer sb = new StringBuffer();
		sb.append("// "+MP_MANAGED_UPDATABLE_BEGINNING_ENABLE+ " "+MP_MANAGED_REFERENCE_MARKER+s+MP_MANAGED_REFERENCE_MARKER+"\n\n");
		return sb.toString();
	}
	
	public static String getUpdatedAreaEndSnippet(String s) {
		StringBuffer sb = new StringBuffer();
		sb.append("// "+MP_MANAGED_UPDATABLE_ENDING+"\n");
		return sb.toString();
	}	
	
	public static String getColumnBeginSnippet(Column column, Map<String, String> updatedAreas) {
		return getUpdatedAreaBeginSnippet(column.getName());
	}
	
	public static String getColumnEndSnippet(Column column) {
		return getUpdatedAreaEndSnippet(column.getName());
	}
	
}
