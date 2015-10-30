package com.vn.dailycookapp.restmodel.model;

import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.security.session.SessionManager;
import com.vn.dailycookapp.utils.ErrorCodeConstant;

public class LogoutModel extends AbstractModel {
	
	@Override
	protected void preExecute(String... data) throws Exception {
		userId = data[0];
	}
	
	@Override
	protected DCAResponse execute() throws Exception {
		DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
		SessionManager.getInstance().closeSessionOfUser(userId);
		return response;
	}
	
}
