package com.jquery.datatable;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/backend/simcard")
public class SimCardController {

	@Autowired
	DataTableServiceI datatableService;

	@RequestMapping(value = "getSimCardJson", method = RequestMethod.GET)
	public @ResponseBody DataTableResponse getSimCardJson(
			HttpServletRequest request) throws Exception {
		ArrayList<DataTableColumn> columns = new ArrayList<DataTableColumn>();
		columns.add(new DataTableColumn(0, "simcardSerial"));
		columns.add(new DataTableColumn(1, "ccId"));
		columns.add(new DataTableColumn(2, "msisdn"));
		columns.add(new DataTableColumn(3, "simcardSerial"));
		DataTableHelper datatable = new DataTableHelper(request, columns) {

			@Override
			public String populateDataTable() {
				return "FROM evd_simcards";
			}
		};
		return datatableService.getDataTableResult(datatable);
	}

}
