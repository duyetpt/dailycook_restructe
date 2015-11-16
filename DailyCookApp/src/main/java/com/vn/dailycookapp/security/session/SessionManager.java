package com.vn.dailycookapp.security.session;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vn.dailycookapp.utils.ErrorCodeConstant;
import org.dao.DAOException;
import org.dao.SessionDAO;
import org.entity.Session;

public class SessionManager implements Runnable {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final int TOKEN_LENGTH = 26;
    private final int SLEEP_TIME = 1000 * 60 * 10;

    private final Hashtable<String, Session> tokenMap;

    private static final SessionManager instance = new SessionManager();

    private SessionManager() {
        tokenMap = new Hashtable<>();
    }

    /**
     *
     * @return instance of SessionManager
     */
    public static SessionManager getInstance() {
        return instance;
    }

    /**
     *
     * @param token
     * @return Session of token
     * @throws TokenInvalidException
     * @throws SessionClosedException
     */
    public Session getSession(String token) throws TokenInvalidException, SessionClosedException {
        if (token == null || token.length() > TOKEN_LENGTH) {
            logger.error("!!!!! token invalid token:" + token);
            throw new TokenInvalidException(ErrorCodeConstant.INVALID_TOKEN.getErrorCode());
        }

        Session session = tokenMap.get(token);
        if (session == null) {
            // get from DB
            session = SessionDAO.getInstance().getSession(token);
            if (session == null) {
                logger.error("!!!!! token is closed:" + token);
                throw new SessionClosedException(ErrorCodeConstant.CLOSED_SESSION.getErrorCode());
            }
            // cache token
            tokenMap.put(token, session);
        }
        // update active time in db
        SessionDAO.getInstance().updateSession(session);
        return session;
    }

    /**
     *
     * @param userId
     * @return token of this session
     */
    public String addSession(String userId) {
        // Generate token
        String token = TokenGenerator.getToken();

        Session session = new Session();
        session.setToken(token);
        session.setUserId(userId);

        tokenMap.put(token, session);
        // save to db
        try {
            SessionDAO.getInstance().save(session);
        } catch (DAOException ex) {
            logger.error("save session to db error", ex);
        }
        return token;

    }

    public void closeAllSessionOfUser(String userId) {
        List<String> removedList = new ArrayList<>();
        // find all token of this user
        for (Map.Entry<String, Session> entry : tokenMap.entrySet()) {
            try {
                Session session = entry.getValue();
                if (session.getUserId().equals(userId)) {
                    removedList.add(entry.getKey());
                }
            } catch (Exception e) {
                logger.error("logout: remove session error", e);
            }
        }

        // remove token
        for (String str : removedList) {
            logger.info("=====>:User close session: " + str);
            tokenMap.remove(str);
        }
        // remove in db
        SessionDAO.getInstance().removeAllSession(userId);
    }

    public void closeSessionOfToken(String token) {
        Session session = tokenMap.get(token);
        if (session != null) {
            logger.info("=====>:User close session: token=" + token + ", userId=" + session.getUserId());
            tokenMap.remove(token);
            SessionDAO.getInstance().logout(token);
        }
    }

    /**
     * only for test
     *
     * @return
     */
    public Map<String, Session> getAllSession() {
        return tokenMap;
    }

    @Override
    public void run() {
        while (true) {
            List<String> removedToken = new ArrayList<String>();
            for (Map.Entry<String, Session> entry : tokenMap.entrySet()) {
                Session session = entry.getValue();
                session.downTimeToLife(SLEEP_TIME);
                if (session.getTimeToLife() <= 0) {
                    removedToken.add(entry.getKey());
                }
            }

            for (String token : removedToken) {
                logger.info("=====>:Scan close session: " + token);
                tokenMap.remove(token);
                SessionDAO.getInstance().logout(token);
            }
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                logger.error("expire session process exception", e);
            }
        }
    }
}
