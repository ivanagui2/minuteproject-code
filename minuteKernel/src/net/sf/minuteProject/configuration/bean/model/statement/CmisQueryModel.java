package net.sf.minuteProject.configuration.bean.model.statement;

import java.util.Collection;
import java.util.List;

import net.sf.minuteProject.configuration.bean.DataModel;
import net.sf.minuteProject.configuration.bean.enrichment.Entity;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Function;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.View;
import net.sf.minuteProject.configuration.bean.model.data.constant.Direction;
import net.sf.minuteProject.exception.MinuteProjectException;

public class CmisQueryModel extends QueryModel {


	public void setEntityModelSpecific(Query query, Direction dir,
			org.apache.ddlutils.model.Table table, Table entity) {
		initFieldAndRelationship(query, dir, table);
	}

	@Override
	public QueryParams getOutputParams(Query query)
			throws MinuteProjectException {
		Database database = new Database() {
			
			@Override
			public String toVerboseString() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void setVersion(String version) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setName(String name) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setIdMethod(String idMethod) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void removeTable(int idx) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void removeTable(Table table) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Database loadDatabase(DataModel dataModel) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public View[] getViews() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getViewCount() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public String getVersion() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Table[] getTables() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getTableCount() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Table getTable(int idx) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getIdMethod() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Function[] getFunctions() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Table[] getEntities() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public DataModel getDataModel() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Table findTable(String name, boolean caseSensitive) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Table findTable(String name) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public View addView(Table table) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void addView(View view) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void addTables(Collection tables) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void addTable(int idx, Table table) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void addTable(Table table) {
				// TODO Auto-generated method stub
				
			}
		};
		return new QueryParams();
	}
	
	private void initFieldAndRelationship(Query query, Direction dir,
			org.apache.ddlutils.model.Table table) {
		if (dir.equals(Direction.IN)) {
			List<QueryParam> list = query.getQueryParams(Direction.IN);
		}
	}
}
