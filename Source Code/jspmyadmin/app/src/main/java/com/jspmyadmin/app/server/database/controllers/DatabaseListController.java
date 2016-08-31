/**
 * 
 */
package com.jspmyadmin.app.server.database.controllers;

import com.jspmyadmin.app.server.database.beans.DatabaseListBean;
import com.jspmyadmin.app.server.database.logic.DatabaseLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/09
 *
 */
@WebController(authentication = true, path = "/server_databases.html", requestLevel = RequestLevel.SERVER)
public class DatabaseListController extends Controller<DatabaseListBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(DatabaseListBean bean, View view) throws Exception {
		DatabaseLogic databaseLogic = null;
		try {
			super.fillBasics(bean);
			databaseLogic = new DatabaseLogic();
			databaseLogic.fillBean(bean);
			bean.setToken(super.generateToken());
		} finally {
			databaseLogic = null;
		}
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_SERVER_DATABASE_DATABASELIST);
	}

	@Override
	protected void handlePost(DatabaseListBean bean, View view) throws Exception {
		this.handleGet(bean, view);
	}

}
