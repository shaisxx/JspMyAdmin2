/**
 * 
 */
package com.jspmyadmin.app.database.structure.controllers;

import java.io.PrintWriter;

import org.json.JSONObject;

import com.jspmyadmin.app.database.structure.beans.CreateViewBean;
import com.jspmyadmin.app.database.structure.logic.StructureLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ResponseBody;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/22
 *
 */
@WebController(authentication = true, path = "/database_create_view_post.text", requestLevel = RequestLevel.DATABASE)
public class CreateViewPostController extends Controller<CreateViewBean> {

	private static final long serialVersionUID = 1L;

	@Override
	@ResponseBody
	protected void handleGet(CreateViewBean bean, View view) throws Exception {
	}

	@Override
	@ValidateToken
	@ResponseBody
	protected void handlePost(CreateViewBean bean, View view) throws Exception {

		StructureLogic structureLogic = null;
		JSONObject jsonObject = new JSONObject();
		try {
			structureLogic = new StructureLogic();
			if (structureLogic.isTableExisted(bean.getView_name(), bean.getRequest_db())) {
				jsonObject.put(FrameworkConstants.ERR, messages.getMessage(AppConstants.MSG_VIEW_ALREADY_EXISTED));
			} else {
				String result = structureLogic.createView(bean);
				jsonObject.append(FrameworkConstants.ERR, FrameworkConstants.BLANK);
				if (result != null) {
					jsonObject.append(FrameworkConstants.DATA, result);
				} else {
					JSONObject msg = new JSONObject();
					msg.put(FrameworkConstants.MSG_KEY, AppConstants.MSG_VIEW_CREATED);
					jsonObject.append(FrameworkConstants.MSG, super.encode(msg.toString()));
				}
			}
		} catch (Exception e) {
			jsonObject.append(FrameworkConstants.ERR, e.getMessage());
		} finally {
			structureLogic = null;
		}

		jsonObject.put(FrameworkConstants.TOKEN, super.generateToken());
		PrintWriter writer = response.getWriter();
		try {
			writer.print(super.encrypt(jsonObject));
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
}
