/**
 * 
 */
package com.jspmyadmin.app.table.data.controllers;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.app.table.data.beans.DataUpdateBean;
import com.jspmyadmin.app.table.data.logic.DataSelectLogic;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandlePost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.Rest;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;
import com.jspmyadmin.framework.web.utils.RequestLevel;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/22
 *
 */
@WebController(authentication = true, path = "/table_data_update.text", requestLevel = RequestLevel.TABLE)
@Rest
public class TableDataUpdateController {

	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private HttpServletResponse response;
	@Model
	private DataUpdateBean bean;

	@HandlePost
	@ValidateToken
	private JSONObject updateData() throws JSONException, EncodingException {

		DataSelectLogic dataSelectLogic = null;
		JSONObject jsonObject = new JSONObject();
		try {
			dataSelectLogic = new DataSelectLogic(bean.getRequest_table());
			String result = dataSelectLogic.update(bean);
			jsonObject.append(Constants.ERR, Constants.BLANK);
			jsonObject.append(Constants.DATA, result);
		} catch (SQLException e) {
			jsonObject.append(Constants.ERR, e.getMessage());
		}
		jsonObject.put(Constants.TOKEN, requestAdaptor.generateToken());
		return jsonObject;
	}
}
