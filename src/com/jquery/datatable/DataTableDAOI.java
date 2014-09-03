package com.jquery.datatable;

import java.util.List;

import org.hibernate.HibernateException;

/**
* jQuery DataTable Plugin With Hibernate and Spring
*  
* @author CHAB SIHENG
* @version v1.0
* @email {@link chabsiheng@gmail.com}
* 
*/

public interface DataTableDAOI {
	
	public int getDataTableTotalRecord(String strQuery) throws HibernateException;
	public List<?> getDataTableRecord(String strQuery) throws HibernateException;
}
