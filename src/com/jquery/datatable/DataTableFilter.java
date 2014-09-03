package com.jquery.datatable;

/**
* jQuery DataTable Plugin With Hibernate and Spring
*  
* @author CHAB SIHENG
* @version v1.0
* @email {@link chabsiheng@gmail.com}
* 
*/

public class DataTableFilter {

	private DataTableColumn column;
	private boolean searchable;

	public DataTableFilter() {
		super();
		column = new DataTableColumn();
	}

	public DataTableColumn getColumn() {
		return column;
	}

	public void setColumn(DataTableColumn column) {
		this.column = column;
	}

	public boolean isSearchable() {
		return searchable;
	}

	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}
}
