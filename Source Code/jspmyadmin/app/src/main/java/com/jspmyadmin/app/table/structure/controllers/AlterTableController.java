/**
 * 
 */
package com.jspmyadmin.app.table.structure.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jspmyadmin.app.table.structure.beans.AlterColumnBean;
import com.jspmyadmin.app.table.structure.logic.StructureLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/22
 *
 */
@WebController(authentication = true, path = "/table_alter.html", requestLevel = RequestLevel.TABLE)
public class AlterTableController extends Controller<AlterColumnBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(AlterColumnBean bean, View view) throws Exception {
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_HOME);
	}

	@Override
	@ValidateToken
	protected void handlePost(AlterColumnBean bean, View view) throws Exception {

		StructureLogic structureLogic = new StructureLogic(bean.getRequest_table(), messages);
		structureLogic.fillAlterBean(bean);

		Map<String, List<String>> data_types_map = new LinkedHashMap<String, List<String>>();
		data_types_map.putAll(FrameworkConstants.Utils.DATA_TYPES_MAP);
		bean.setData_types_map(data_types_map);
		bean.setToken(super.generateToken());
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_TABLE_STRUCTURE_ALTER_TABLE);
	}
}
