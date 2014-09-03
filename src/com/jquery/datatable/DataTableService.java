package com.jquery.datatable;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
* jQuery DataTable Plugin With Hibernate and Spring
*  
* @author CHAB SIHENG
* @version v1.0
* @email {@link chabsiheng@gmail.com}
* 
*/


public class DataTableService extends HibernateDaoSupport implements
		DataTableServiceI {
	private int start = -1;
	private int length = -1;
	private DataTableRequest request;

	@Autowired
	DataTableDAO datatableDao;

	public void setPagination(int start, int length) {
		this.start = start;
		this.length = length;
	}

	public List<?> getDataTableResult(String query) throws HibernateException {

		if (!(this.start == -1 || this.length == -1)) {
			datatableDao.setStart(start);
			datatableDao.setLength(length);
		}
		List<?> list = datatableDao.getDataTableRecord(query);
		return list;
	}

	@Override
	public DataTableResponse getDataTableResult(DataTableHelper helper)
			throws HibernateException {
		DataTableResponse response = new DataTableResponse();
		request = helper.getDataTableRequest();

		String countQuery = helper.count();
		int total = datatableDao.getDataTableTotalRecord(countQuery);
		if (total < 0) {
			return null;
		}
		countQuery = helper.countFilter();
		String baseQuery = helper.select();

		if (request.getSearchValue() != null
				&& request.getSearchValue().length() > 0) {
			baseQuery += helper.filter();
			countQuery += helper.filter();
		} else if (helper.setWhereQuery() != null) {
			baseQuery += helper.filter();
			countQuery += helper.filter();
		}

		if (request.getOrders() != null) {
			baseQuery += helper.order();
			countQuery += helper.order();
		} else if (helper.setOrderQuery() != null
				|| helper.setGroupQuery() != null) {
			baseQuery += helper.order();
			countQuery += helper.order();
		}

		if (request.getStart() >= 0 && request.getLength() >= 0) {
			this.setPagination(request.getStart(), request.getLength());
		} else {
			this.setPagination(-1, -1);
		}

		int filter = datatableDao.getDataTableTotalRecord(countQuery);
		if (filter < 0) {
			return null;
		}
		List<?> lstData = this.getDataTableResult(baseQuery);

		response.setDraw(request.getDraw() + 1);
		response.setRecordsTotal(total);
		response.setRecordsFiltered(filter);
		response.setData(lstData);
		return response;
	}

}
