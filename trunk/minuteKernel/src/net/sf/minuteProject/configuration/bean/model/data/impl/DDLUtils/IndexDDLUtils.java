package net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Index;

public class IndexDDLUtils implements Index{

	private org.apache.ddlutils.model.Index index;
	
	public IndexDDLUtils(org.apache.ddlutils.model.Index index) {
		this.index = index;
	}
	
	public void addColumn(Column column) {
		// TODO Auto-generated method stub
		
	}

	public boolean equalsIgnoreCase(Index otherIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	public Column getColumn(int idx) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Column[] getColumns() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasColumn(Column column) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isUnique() {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeColumn(Column column) {
		// TODO Auto-generated method stub
		
	}

	public void removeColumn(int idx) {
		// TODO Auto-generated method stub
		
	}

	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	public String toVerboseString() {
		// TODO Auto-generated method stub
		return null;
	}

}
