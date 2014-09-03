package com.jquery.datatable;

/**
* jQuery DataTable Plugin With Hibernate and Spring
*  
* @author CHAB SIHENG
* @version v1.0
* @email {@link chabsiheng@gmail.com}
* 
*/

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

@Service
public interface DataTableServiceI {

	public DataTableResponse getDataTableResult(DataTableHelper datatable)
			throws HibernateException;

}
