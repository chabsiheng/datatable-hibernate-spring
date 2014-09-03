package com.jquery.datatable;

import java.util.ArrayList;

/**
* jQuery DataTable Plugin With Hibernate and Spring
*  
* @author CHAB SIHENG
* @version v1.0
* @email {@link chabsiheng@gmail.com}
* 
*/

public class DataTableRequest {
	private int draw;
	private int start;
	private int length;
	private String searchValue;
	private ArrayList<DataTableOrder> orderColumn;
	private ArrayList<DataTableOrder> orders;
	private ArrayList<DataTableFilter> filters;

	public DataTableRequest() {
		super();
		orderColumn = new ArrayList<DataTableOrder>();
		orders = new ArrayList<DataTableOrder>();
		filters = new ArrayList<DataTableFilter>();
	}
	
	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public ArrayList<DataTableOrder> getOrders() {
		return orders;
	}

	public ArrayList<DataTableOrder> getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(ArrayList<DataTableOrder> orderColumn) {
		this.orderColumn = orderColumn;
	}

	public void setOrders(ArrayList<DataTableOrder> orders) {
		this.orders = orders;
	}

	public ArrayList<DataTableFilter> getFilters() {
		return filters;
	}

	public void setFilters(ArrayList<DataTableFilter> filters) {
		this.filters = filters;
	}

}
