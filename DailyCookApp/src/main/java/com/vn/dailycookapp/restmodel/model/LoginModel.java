package com.vn.dailycookapp.restmodel.model;

import java.security.InvalidParameterException;

import org.glassfish.jersey.internal.util.Base64;

import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.security.authentication.Authenticator;
import com.vn.dailycookapp.security.authentication.CurrentUser;
import com.vn.dailycookapp.security.authentication.LoginMethod;
import com.vn.dailycookapp.utils.DCAException;
import com.vn.dailycookapp.utils.ErrorCodeConstant;

public class LoginModel extends AbstractModel {
	
	private String	accountInfo;
	private String	loginMethod;
	
	@Override
	protected void preExecute(String... data) throws DCAException {
		if (data == null || data.length < 2) {
			throw new InvalidParameterException();
		}
		accountInfo = data[0];
		loginMethod = data[1];
		if (accountInfo == null || loginMethod == null) {
			throw new InvalidParameterException();
		}
	}
	
	@Override
	protected DCAResponse execute() throws Exception {
		DCAResponse response = null;
		String[] infors = accountInfo.split(" ");
		if (infors.length != 2) {
			response = new DCAResponse(ErrorCodeConstant.INVALID_DATA.getErrorCode());
		} else {
			String accString = Base64.decodeAsString(infors[1]);
			String[] m_username_pass = accString.split(":");
			logger.info("!...!...! user:" + m_username_pass[0] + ", pass:" + m_username_pass[1] + " !...!...!");
			if (m_username_pass.length != 2) {
				response = new DCAResponse(ErrorCodeConstant.INVALID_DATA.getErrorCode());
			} else {
				CurrentUser cUser = null;
				switch (loginMethod) {
					case LoginMethod.EMAIL_PASSWORD:
						cUser = Authenticator.login(m_username_pass[0], m_username_pass[1]);
						break;
					case LoginMethod.FACEBOOK:
						cUser = Authenticator.loginByFb(m_username_pass[0], m_username_pass[1]);
						break;
				}
				
				response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
				response.setData(cUser);
			}
		}
		return response;
	}
	
}
