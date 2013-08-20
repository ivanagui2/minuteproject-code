package net.sf.minuteProject.utils;

import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Database;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.ColumnDDLUtils;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.DatabaseDDLUtils;
import net.sf.minuteProject.configuration.bean.model.data.impl.DDLUtils.TableDDLUtils;
import net.sf.minuteproject.model.db.type.FieldType;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class ConvertUtilsTest {

	Database database;
	Table table;
	Column column;
	@Before
	public void init(){
		database = mock(DatabaseDDLUtils.class);
		when(database.getType()).thenReturn("ORACLE");
		table = mock(TableDDLUtils.class);
		table.setDatabase(database);
		column = mock(ColumnDDLUtils.class);
		when(column.getTable()).thenReturn(table);
		
	}
	@Test
	public void testColumnOracleVarchar() {
		when(column.getType()).thenReturn(FieldType.VARCHAR.toString());
		String s = ConvertUtils.getJavaTypeClassFromDBType(column);
		Assert.assertTrue("s = "+s,s.equals("String"));
	}
	
	@Test
	public void testColumnOracleBigInt() {
		when(column.getType()).thenReturn("NUMBER");
		when(column.getSize()).thenReturn("19");
		
		String s = ConvertUtils.getJavaTypeClassFromDBType(column);
		Assert.assertTrue("s = "+s,s.equals("java.lang.Long"));
	}
	
}
