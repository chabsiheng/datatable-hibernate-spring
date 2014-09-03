package com.jquery.datatable;

public class DataTableOrder {

	private DataTableColumn column;
	private boolean orderable;
	private String orderDir;

	
	public DataTableOrder() {
		super();
		column = new DataTableColumn();
	}

	public DataTableColumn getColumn() {
		return column;
	}

	public void setColumn(DataTableColumn column) {
		this.column = column;
	}

	public boolean isOrderable() {
		return orderable;
	}

	public void setOrderable(boolean orderable) {
		this.orderable = orderable;
	}

	public String getOrderDir() {
		return orderDir;
	}

	public void setOrderDir(String orderDir) {
		this.orderDir = orderDir;
	}

}
