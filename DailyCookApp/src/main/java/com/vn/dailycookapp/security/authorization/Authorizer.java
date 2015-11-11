package com.vn.dailycookapp.security.authorization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.entity.Session;
import com.vn.dailycookapp.security.session.SessionClosedException;
import com.vn.dailycookapp.security.session.SessionManager;
import com.vn.dailycookapp.security.session.TokenInvalidException;

public class Authorizer {
	private static Authorizer	instance	= new Authorizer();
	private final Logger		logger		= LoggerFactory.getLogger(getClass());
	
	private Authorizer() {
		
	}
	
	public static Authorizer getInstance() {
		return instance;
	}
	
	public String authorize(String token) throws AuthorizationException {
		if (token == null || token.isEmpty()) {
			// response error with unauthorized error
			logger.error("not has token for authen...unknow who are you?");
			throw new AuthorizationException();
		} else {
			try {
				Session session = SessionManager.getInstance().getSession(token);
				// update last active time
				session.updateLastActiveTime();
				return session.getUserId();
			} catch (TokenInvalidException e) {
				logger.error("token invalid", e);
				throw new AuthorizationException();
			} catch (SessionClosedException e) {
				logger.error("session is closed", e);
				throw new AuthorizationException();
			}
		}
	}
}
