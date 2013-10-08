package net.sf.minuteProject.plugin.jsf;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.plugin.presentation.PresentationUtils;
import static net.sf.minuteProject.plugin.presentation.PresentationUtils.*;

public class PrimefacesUtils extends PresentationUtils{

	private static final int MAX_COLUMNS_DISPLAY_SIZE_TEXTAREA = 70;
	private static final int MAX_ROWS_DISPLAY_SIZE_TEXTAREA = 15;

	public static int getDisplayColumns(Column column) {
		if (isTextArea(column))
			return Math.min(column.getSizeAsInt(),MAX_COLUMNS_DISPLAY_SIZE_TEXTAREA);
		return PresentationUtils.getDisplayColumns(column);
	}

	public static int getDisplayRows(Column column) {
		if (isTextArea(column))
			return MAX_ROWS_DISPLAY_SIZE_TEXTAREA;
		return PresentationUtils.getDisplayRows(column);
	}
}
