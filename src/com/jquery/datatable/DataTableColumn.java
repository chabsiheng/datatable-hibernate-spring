package com.jquery.datatable;

/**
* jQuery DataTable Plugin With Hibernate and Spring
*  
* @author CHAB SIHENG
* @version v1.0
* @email {@link chabsiheng@gmail.com}
* 
*/

public class DataTableColumn {

	public DataTableColumn() {
		super();
	}

	public DataTableColumn(int columnIndex, String columnName) {
		super();
		this.columnIndex = columnIndex;
		this.columnName = columnName;
	}

	private int columnIndex;
	private String columnName;

	public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}
