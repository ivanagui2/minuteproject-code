package net.sf.minuteproject.utils.query;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class QueryUtils {

	public static String buildInsertStatement (
			String table,
			Map<Integer, String> columnIndex,
			Map<String, String>  columnValue) {
		StringBuffer sb = new StringBuffer();
		sb.append(getInsert(table, columnIndex, columnValue));
		sb.append(getInsertValue(columnIndex, columnValue));
		System.out.println("Insert query = "+sb.toString());
		return sb.toString();
	}
	
	public static String buildUpdateStatement (
			String table,
			Map<Integer, String> columnIndex,
			Map<String, String>  columnValue,
			Map<Integer, String> columnWhereIndex,
			Map<String, String>  columnWhereValue) {
		StringBuffer sb = new StringBuffer();
		sb.append(getUpdate(table, columnIndex, columnValue));
		sb.append(getWhereQuery(columnWhereIndex, columnWhereValue));		
		System.out.println("Update query = "+sb.toString());
		return sb.toString();
	}
	
	public static String buildDeleteStatement (
			String table,
			Map<Integer, String> columnWhereIndex,
			Map<String, String>  columnWhereValue) {
		StringBuffer sb = new StringBuffer();
		sb.append(getDelete(table));
		sb.append(getWhereQuery(columnWhereIndex, columnWhereValue));		
		System.out.println("Delete query = "+sb.toString());
		return sb.toString();
	}
	
	private static String getDelete(String table) {
		return "DELETE FROM "+table+" ";	
	}
	
	private static String getUpdate(String table, Map<Integer, String>  columnIndex, Map<String, String>  columnValue) {
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE "+table+" ");
		sb.append(getUpdateSetQuery(columnIndex, columnValue));
		sb.append(" ");
		return sb.toString();		
	}	

	private static String getInsert(String table, Map<Integer, String>  columnIndex, Map<String, String>  columnValue) {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO "+table+" (");
		sb.append(getColumnQuery2(columnIndex, columnValue));
		sb.append(") ");
		return sb.toString();		
	}
	
	private static String getInsertValue(Map<Integer, String>  columnIndex, Map<String, String>  columnValue) {
		StringBuffer sb = new StringBuffer();
		sb.append(" VALUES (");
		sb.append(getInsertValues(columnIndex, columnValue));
		sb.append(") ");
		return sb.toString();		
	}
	
	private static String getInsertValues (			 
			 Map<Integer, String> columnIndex,
			 Map<String, String>  columnValue) {
		int size = columnIndex.size();
		StringBuffer sb = new StringBuffer("");
		boolean isBeginning = true;
		for (int i = 0; i < size+1; i++) {
			String columnName = columnIndex.get(Integer.valueOf(i));
			String value = columnValue.get(columnName);		
			if (value!=null && !value.equals("")) {
				if (isBeginning) {
					isBeginning=false;
				}else
					sb.append(",");
				if (isQuoted(columnName)){
					sb.append("'"+value+"'");
				}
//				if (i<size)
//					sb.append(",");
			}
		}
		return sb.toString();		
	}
	
	public static String buildQuery(
			 String table,
			 Map<Integer, String> columnIndex,
			 Map<String, String>  columnExpressionValue,
			 Map<String, String>  columnValue,
			 Map<String, String>  columnOrderValue) {
		
		StringBuffer sb = new StringBuffer();
		sb.append(getWhatQuery(columnIndex));
		sb.append(getQueryFrom(table));
		sb.append(getWhereQuery(columnIndex, columnExpressionValue, columnValue));
		sb.append(getQueryOrder(columnIndex, columnOrderValue));
		System.out.println(" query = "+ sb.toString());
		return sb.toString();
	}
	

	private static String getWhatQuery (Map<Integer, String> columnIndex) {
//		int size = columnIndex.size();
		StringBuffer sb = new StringBuffer("SELECT ");
		sb.append(getColumnQuery(columnIndex));
//		for (int i = 1; i <= size; i++) {
//			String s = columnIndex.get(Integer.valueOf(i));
//			sb.append(s);
//			if (i!=size)
//				sb.append(", ");
//		}
		return sb.toString();
	}
	
	private static String getColumnQuery (Map<Integer, String> columnIndex) {
		int size = columnIndex.size();
		StringBuffer sb = new StringBuffer("");
		for (int i = 1; i <= size; i++) {
			String s = columnIndex.get(Integer.valueOf(i));
			sb.append(s);
			if (i!=size)
				sb.append(", ");
		}
		return sb.toString();
	}
	
	private static String getUpdateSetQuery (Map<Integer, String> columnIndex, Map<String, String>  columnValue) {
		int size = columnIndex.size();
		StringBuffer sb = new StringBuffer("");
		boolean isBeginning = true;
		for (int i = 0; i <size; i++) {
			String columnName = columnIndex.get(Integer.valueOf(i));
			String value = columnValue.get(columnName);
			if (value!=null && !value.equals("")) {
				if (isBeginning) {
					isBeginning=false;
				}else
					sb.append(",");
				sb.append(" SET "+columnName+" = ");
				if (isQuoted(value)){
					sb.append("'"+value+"'");
				}
			}
		}
		return sb.toString();
	}
	
	private static String getWhereQuery (Map<Integer, String> columnWhereIndex, Map<String, String>  columnWhereValue) {
		int size = columnWhereIndex.size();
		StringBuffer sb = new StringBuffer("");
		boolean isBeginning = true;
		for (int i = 0; i <size; i++) {
			String columnName = columnWhereIndex.get(Integer.valueOf(i));
			String value = columnWhereValue.get(columnName);
			if (value!=null && !value.equals("")) {				
				if (isBeginning) {
					isBeginning=false;
					sb.append(" WHERE ");
					
				}else
					sb.append(" AND ");
				sb.append(columnName+" = ");
				if (isQuoted(value)){
					sb.append("'"+value+"'");
				}
			}
		}
		return sb.toString();
	}
	
	private static String getColumnQuery2 (Map<Integer, String> columnIndex, Map<String, String>  columnValue) {
		int size = columnIndex.size();
		StringBuffer sb = new StringBuffer("");
		boolean isBeginning = true;
		for (int i = 0; i <size; i++) {
			String columnName = columnIndex.get(Integer.valueOf(i));
			String value = columnValue.get(columnName);
			if (value!=null && !value.equals("")) {
				if (isBeginning) {
					isBeginning=false;
				}else
					sb.append(",");
				if (isQuoted(columnName)){
					sb.append(columnName);
				}
			}
		}
		return sb.toString();
	}	
	
	private static String getQueryFrom (String table) {
		return " FROM "+table+" ";
	}
	
	private static String getQueryOrder(Map<Integer, String> columnIndex, Map<String, String> columnOrderValue) {
		int size = columnIndex.size();
		StringBuffer sb = new StringBuffer("");
		boolean isCommaSet = false;
		for (int i = 1; i < size; i++) {
			String columnName = columnIndex.get(Integer.valueOf(i));
			String order = columnOrderValue.get(columnName);
			if (order!=null && !order.equals("") && !order.equals("blank") && !order.equals("null")) {
				if (isCommaSet==false) {
					sb.append(" ORDER BY ");
					isCommaSet = true;
				} else {
					sb.append(",");
				}
				sb.append(columnName);
				sb.append(" "+order);
			}
		}
		return sb.toString();			
	}
	
	private static String getWhereQuery (			 
			 Map<Integer, String> columnIndex,
			 Map<String, String>  columnExpressionValue,
			 Map<String, String>  columnValue) {
		int size = columnIndex.size();
		StringBuffer sb = new StringBuffer("");
		boolean isWhereSet = false;
		for (int i = 0; i < size+1; i++) {
			String columnName = columnIndex.get(Integer.valueOf(i));
			String expression = columnExpressionValue.get(columnName);
			String value = columnValue.get(columnName);
			if (expression!=null && !expression.equals("") && !expression.equals("blank") && !expression.equals("null")) {
				if (isWhereSet==false) {
					sb.append(" WHERE ");
					isWhereSet = true;
				} else {
					sb.append(" AND ");
				}
				sb.append(columnName);
				if (expression.equals("=")) {
					sb.append(" = ");
				}
				if (isQuoted(columnName)){
					sb.append("'"+value+"'");
				}
			}
		}
		return sb.toString();		
	}
	
	public static boolean isQuoted (String columnName) {
		return true;
	}
	
}
