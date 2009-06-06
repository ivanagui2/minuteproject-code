package net.sf.minuteProject.configuration.bean.model.data.impl.UMLNotation;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Reference;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.impl.TableAbstract;

public class TableUMLNotation extends TableAbstract {
	
	List<Column> noPrimaryKeyNoForeignKeyColumnsName;
	List<Reference> children;
	List<Reference> parents;
	
	public TableUMLNotation (Table table) {
		super (table);
	}

	public Column [] getAttributes () {
		if (noPrimaryKeyNoForeignKeyColumnsName == null) {
			noPrimaryKeyNoForeignKeyColumnsName = new ArrayList<Column>();
			for (int i = 0; i < getNoPrimaryKeyNoForeignKeyColumns().length; i++) {
				ColumnUMLNotation columnUMLNotation = new ColumnUMLNotation (getNoPrimaryKeyNoForeignKeyColumns()[i], this);
				noPrimaryKeyNoForeignKeyColumnsName.add(columnUMLNotation);
			}
		}
		return (Column[])noPrimaryKeyNoForeignKeyColumnsName.toArray(new Column[noPrimaryKeyNoForeignKeyColumnsName.size()]);//(ColumnUMLNotation[])getNoPrimaryKeyNoForeignKeyColumns();
	}	
	
	public Reference [] getChildren() {
		if (children == null) {
			children = new ArrayList<Reference>();
			for (int i = 0; i < super.getTable().getChildren().length; i++) {
				ReferenceUMLNotation referenceUMLNotation = new ReferenceUMLNotation (super.getTable().getChildren()[i]);
				children.add(referenceUMLNotation);
			}
		}
		return (Reference[])children.toArray(new Reference[children.size()]);//(ColumnUMLNotation[])getNoPrimaryKeyNoForeignKeyColumns();
	}
	
	public Reference [] getParents() {
		if (parents == null) {
			parents = new ArrayList<Reference>();
			for (int i = 0; i < super.getTable().getParents().length; i++) {
				ReferenceUMLNotation referenceUMLNotation = new ReferenceUMLNotation (super.getTable().getParents()[i]);
				parents.add(referenceUMLNotation);
			}
		}
		return (Reference[])parents.toArray(new Reference[parents.size()]);//(ColumnUMLNotation[])getNoPrimaryKeyNoForeignKeyColumns();
	}

	public boolean hasLob() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasUniqueIndex() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
