package net.sf.minuteProject.configuration.bean.model.data.impl;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.enrichment.Stereotype;
import net.sf.minuteProject.configuration.bean.model.data.BaseColumn;
import net.sf.minuteProject.configuration.bean.model.data.Column;

public abstract class ColumnBaseAbstract extends AbstractConfiguration implements BaseColumn{

	private String typeAlias;
	private Boolean isHidden=false, isTransient=false, isSearchable=true, isEditable=true;
	
	@Override
	public String getTypeAlias() {
		if (typeAlias==null)
			typeAlias=getType();
		return typeAlias;
	}
	
	public void setTypeAlias(String typeAlias){
		this.typeAlias = typeAlias;
	}
	
	@Override
	public Boolean isHidden() {
		return isHidden;
	}
	@Override
	public void setHidden(Boolean isHidden) {
		if (isHidden!=null)
		this.isHidden = isHidden;
	}
//	@Override
//	public boolean isHidden() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void setHidden(boolean isHidden) {
//		// TODO Auto-generated method stub
//		
//	}
	@Override
	public Boolean isEditable() {
		return isEditable;
	}
	@Override
	public void setEditable(Boolean isEditable) {
		if (isEditable!=null)
			this.isEditable = isEditable;
	}
	@Override
	public Boolean isSearchable() {
		return isSearchable;
	}
	@Override
	public void setSearchable(Boolean isSearchable) {
		if (isSearchable!=null)
			this.isSearchable = isSearchable;
	}
	@Override
	public Boolean isTransient() {
		return isTransient;
	}
	@Override
	public void setTransient(Boolean isTransient) {
		if (isTransient!=null)
			this.isTransient = isTransient;
	}
//
//    public String getType()
//    {
//        return getTypeAlias();
//    }
//    
//	protected abstract String getRealType();
}
