package com.jquery.datatable;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
* jQuery DataTable Plugin With Hibernate and Spring
*  
* @author CHAB SIHENG
* @version v1.0
* @email {@link chabsiheng@gmail.com}
* 
*/

public class DataTableDAO extends HibernateDaoSupport implements DataTableDAOI {

	private int start = -1;
	private int length = -1;

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

	@SuppressWarnings("deprecation")
	@Override
	public List<?> getDataTableRecord(String strQuery)
			throws HibernateException {
		try {
			Session session = getSession();
			Query query = session.createQuery(strQuery);
			if (!(this.start == -1 || this.length == -1)) {
				query.setFirstResult(this.start);
				query.setMaxResults(this.length);
			}
			return query.list();
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new HibernateException("E-00004");
		}
	}

	@Override
	public int getDataTableTotalRecord(String strQuery)
			throws HibernateException {
		try {
			List<?> list = getHibernateTemplate().find(strQuery);
			if (list.size() < 0) {
				return -1;
			}
			return Integer.parseInt(list.get(0).toString());
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new HibernateException("E-00004");
		}
	}

}
