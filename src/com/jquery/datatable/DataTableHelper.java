package com.jquery.datatable;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

/**
* jQuery DataTable Plugin With Hibernate and Spring
*  
* @author CHAB SIHENG
* @version v1.0
* @email {@link chabsiheng@gmail.com}
* 
*/

@Service
@Configurable
public abstract class DataTableHelper {
	protected final String REQ_ORDERABLE = "orderable";
	protected final String REQ_SEARCHABLE = "searchable";
	protected final String REQ_COLUMN = "column";
	protected final String REQ_DIR = "dir";
	protected final String REQ_START = "start";
	protected final String REQ_LENGTH = "length";
	protected final String REQ_SEARCH_VALUE = "search[value]";
	protected final String REQ_DRAW = "draw";

	public DataTableRequest dataTableRequest;
	public ArrayList<DataTableColumn> lstColumns;
	public DataTableColumn primaryKey;

	protected HttpServletRequest request;

	public DataTableHelper(HttpServletRequest request,
			ArrayList<DataTableColumn> columns) throws Exception {
		super();
		dataTableRequest = new DataTableRequest();
		this.request = request;
		this.lstColumns = columns;
		this.primaryKey = columns.get(0);
	}

	public DataTableHelper(HttpServletRequest request,
			ArrayList<DataTableColumn> columns, DataTableColumn primaryKey)
			throws Exception {
		super();
		dataTableRequest = new DataTableRequest();
		this.request = request;
		this.lstColumns = columns;
		this.primaryKey = primaryKey;
	}

	/**
	 * @return FROM Query for Hibernate Ex. FROM evd_users
	 * */
	public abstract String populateDataTable();

	/**
	 * customize WHERE Query for DataTable Only
	 * @return WHERE Hibernate Query Ex. WHERE status="ACT"
	 * */
	public String setWhereQuery(){
		return null;
	}
	
	/**
	 * customize ORDER Query for DataTable Only
	 * @return ORDER Hibernate Query Ex. ORDER BY user_id 
	 * */
	public String setOrderQuery(){
		return null;
	}
	
	/**
	 * customize GROUP BY Query for DataTable Only
	 * @return GROUP BY Hibernate Query Ex. ORDER BY user_id 
	 * */
	public String setGroupQuery(){
		return null;
	}
	
	protected DataTableRequest getDataTableRequest() throws HibernateException {

		// get draw request
		String draw = request.getParameter(REQ_DRAW);
		if (draw != null) {
			dataTableRequest.setDraw(Integer.parseInt(draw));
		}

		// Paging request
		if (!(request.getParameter(REQ_START) == null || request.getParameter(
				REQ_LENGTH).equals("-1"))) {
			dataTableRequest.setStart(Integer.parseInt(request
					.getParameter(REQ_START)));
			dataTableRequest.setLength(Integer.parseInt(request
					.getParameter(REQ_LENGTH)));
		}

		// Ordering request
		for (int i = 0; i < lstColumns.size(); i++) {
			String orderColumn = "columns[" + i + "][" + REQ_ORDERABLE + "]";
			String orderable = request.getParameter(orderColumn);
			if (orderable == null) {
				break;
			}
			DataTableOrder order = new DataTableOrder();
			order.setColumn(lstColumns.get(i));
			order.setOrderable(Boolean.parseBoolean(orderable));
			dataTableRequest.getOrders().add(order);
		}

		for (int i = 0; i < dataTableRequest.getOrders().size(); i++) {
			String orderColumn = "order[" + i + "][" + REQ_COLUMN + "]";
			String orderCol = request.getParameter(orderColumn);
			String orderDir = "order[" + i + "][" + REQ_DIR + "]";
			String dir = request.getParameter(orderDir);
			if (orderCol == null || dir == null) {
				break;
			}
			DataTableOrder order = new DataTableOrder();
			order.setColumn(lstColumns.get(Integer.parseInt(orderCol)));
			order.setOrderDir(dir.equals("asc") ? "ASC" : "DESC");
			dataTableRequest.getOrderColumn().add(order);
		}

		// Searching Value
		String searchValue = request.getParameter(REQ_SEARCH_VALUE);
		if (searchValue != null && searchValue.length() > 0) {
			dataTableRequest.setSearchValue(searchValue);

			// Searching Filtering
			for (int i = 0; i < lstColumns.size(); i++) {
				String searchColumn = "columns[" + i + "][" + REQ_SEARCHABLE
						+ "]";
				String searchable = request.getParameter(searchColumn);
				if (searchable == null) {
					break;
				}
				DataTableFilter filter = new DataTableFilter();
				filter.setColumn(lstColumns.get(i));
				filter.setSearchable(Boolean.parseBoolean(searchable));
				dataTableRequest.getFilters().add(filter);
			}
		}
		return dataTableRequest;
	}

	// Construct the SELECT clause for server-side processing
	protected String select() throws HibernateException {
		String baseQuery;
		if (lstColumns == null || lstColumns.size() <= 0) {
			return null;
		}
		baseQuery = "SELECT ";
		for (DataTableColumn column : lstColumns) {
			baseQuery += column.getColumnName() + ", ";
		}
		baseQuery = baseQuery.substring(0, baseQuery.length() - 2)+" ";
		baseQuery += this.populateDataTable().trim();
		return baseQuery;
	}

	// Construct the COUNT clause for server-side processing
	protected String count() throws HibernateException {
		String baseQuery;
		if (primaryKey == null) {
			return null;
		}
		baseQuery = "SELECT COUNT(" + primaryKey.getColumnName() + ") ";
		baseQuery += this.populateDataTable().trim();
		if (this.setWhereQuery() != null){
			baseQuery += " "+this.setWhereQuery().trim();
		}
		if (this.setOrderQuery() != null){
			baseQuery += " "+this.setOrderQuery().trim();
		}
		return baseQuery;
	}
	
	// Construct the COUNT clause for filter server-side processing
	protected String countFilter() throws HibernateException {
		String baseQuery;
		if (primaryKey == null) {
			return null;
		}
		baseQuery = "SELECT COUNT(" + primaryKey.getColumnName() + ") ";
		baseQuery += this.populateDataTable().trim();
		return baseQuery;
	}

	// Construct the LIMIT clause for server-side processing
	protected void limit(Query query) throws HibernateException {
		if (dataTableRequest.getLength() > 0) {
			query.setFirstResult(dataTableRequest.getStart());
			query.setMaxResults(dataTableRequest.getLength());
		}
	}

	// Construct the ORDER BY clause for server-side processing
	protected String order() throws HibernateException {
		String baseQuery = "";
		if (dataTableRequest.getOrderColumn().size() > 0) {
			if (this.setGroupQuery() != null){
				baseQuery += " "+this.setGroupQuery().trim()+" ";
			}
			if (this.setOrderQuery() != null){
				baseQuery += " "+this.setOrderQuery().trim()+", ";
			}
			else{
				baseQuery += " ORDER BY";
			}
		}
		for (int i = 0; i < dataTableRequest.getOrderColumn().size(); i++) {
			DataTableOrder order = dataTableRequest.getOrderColumn().get(i);
			DataTableOrder orderable = dataTableRequest.getOrders().get(i);
			if (orderable.isOrderable()) {
				DataTableColumn column = order.getColumn();
				baseQuery += " " + column.getColumnName() + " "
						+ order.getOrderDir() + ",";
			}
		}
		if (dataTableRequest.getOrderColumn().size() > 0) {
			baseQuery = baseQuery.substring(0, baseQuery.length() - 1);
		}
		return baseQuery;
	}

	// Construct the WHERE clause for server-side processing
	protected String filter() throws HibernateException {
		String baseQuery = "";
		if (dataTableRequest.getSearchValue() ==null ||
				dataTableRequest.getSearchValue().length() < 0) {
			if (this.setWhereQuery() != null){
				return " "+this.setWhereQuery().trim();
			}
			return "";
		}
		if (dataTableRequest.getFilters().size() > 0) {
			if (this.setWhereQuery() != null){
				baseQuery += " "+this.setWhereQuery().trim()+" AND (";
			}
			else{
				baseQuery += " WHERE ";
			}
		}
		for (DataTableFilter filter : dataTableRequest.getFilters()) {
			DataTableColumn column = filter.getColumn();
			if (filter.isSearchable()) {
				baseQuery += " " + column.getColumnName() + " LIKE";
				baseQuery += " \'%" + dataTableRequest.getSearchValue() + "%\'";
				baseQuery += " OR";
			}
		}
		if (dataTableRequest.getFilters().size() > 0) {
			baseQuery = baseQuery.substring(0, baseQuery.length() - 3);
		}
		if (this.setWhereQuery() != null){
			baseQuery += ")";
		}
		return baseQuery;
	}

}
