package com.vn.dailycookapp.restmodel.model;

import org.EncryptHelper;
import org.dao.UserDAO;
import org.entity.User;
import org.json.JsonTransformer;

import com.vn.dailycookapp.cache.user.UserCache;
import com.vn.dailycookapp.entity.request.RegisterInfo;
import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.restmodel.InvalidParamException;
import com.vn.dailycookapp.security.authentication.CurrentUser;
import com.vn.dailycookapp.security.session.SessionManager;
import com.vn.dailycookapp.utils.DCAException;
import com.vn.dailycookapp.utils.ErrorCodeConstant;
import com.vn.dailycookapp.utils.validate.Validator;

/**
 *
 * @author duyetpt transform data to RegisterInfo Validate data Encrypt password
 * Save to DB Get session Response
 */
public class RegisterModel extends AbstractModel {

    private User user;
    private RegisterInfo regInfo;

    @Override
    protected void preExecute(String... data) throws InvalidParamException, DCAException {
        regInfo = JsonTransformer.getInstance().unmarshall(data[0], RegisterInfo.class);
        validateInfo();

    }

    @Override
    protected DCAResponse execute() throws Exception {
        DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
        String encryptPass = EncryptHelper.encrypt(regInfo.getPassword());

        user = new User();
        user.setEmail(regInfo.getEmail());
        user.setPassword(encryptPass);
        user.setDisplayName(regInfo.getEmail().split("@")[0]);
        user.setLanguage(regInfo.getLanguage());
        // save to db
        UserDAO.getInstance().saveWithSynchronized(user);
        // get session token
        String token = SessionManager.getInstance().addSession(user.getId());

        // login
        CurrentUser cUser = new CurrentUser();
        cUser.setDisplayName(user.getDisplayName());
        cUser.setLanguage(user.getLanguage());
        cUser.setToken(token);
        cUser.setUserId(user.getId());
        cUser.setNotificationFlag(user.getNotificationFlag());
        
        // Cache user info
        UserCache.getInstance().cache(user);
        // response
        response.setData(cUser);
        return response;
    }

    private void validateInfo() throws DCAException {
        Validator.getInstance().validateEmail(regInfo.getEmail());
        Validator.getInstance().validatePassword(regInfo.getPassword());
        if (!regInfo.getPassword().equals(regInfo.getRe_password())) {
            throw new InvalidParamException();
        }
    }

}
