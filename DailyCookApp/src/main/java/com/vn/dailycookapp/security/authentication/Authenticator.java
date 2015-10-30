package com.vn.dailycookapp.security.authentication;

import org.EncryptDataException;
import org.dao.DAOException;

import com.vn.dailycookapp.utils.DCAException;

/**
 * 
 * @author duyetpt
 *         login process is here
 */

public final class Authenticator {
	
	public static CurrentUser login(String username, String password) throws DCAException, EncryptDataException, DAOException {
		
		UsernamePasswordToken token = new UsernamePasswordToken();
		token.setPassword(password);
		token.setUsername(username);
		
		CurrentUser cUser = new CurrentUser();
		cUser.login(token);
		return cUser;
	}
	
	public static CurrentUser loginByFb(String fbId, String refreshToken) throws DCAException, DAOException {
		FbToken fbToken = new FbToken();
		fbToken.setFbId(fbId);
		fbToken.setRefreshToken(refreshToken);
		
		CurrentUser cUser = new CurrentUser();
		cUser.login(fbToken);
		return cUser;
	}
}
