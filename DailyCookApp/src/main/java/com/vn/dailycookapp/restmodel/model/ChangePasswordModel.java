/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.restmodel.model;

import com.vn.dailycookapp.entity.request.ChangePasswordInfo;
import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.restmodel.InvalidParamException;
import com.vn.dailycookapp.security.session.SessionManager;
import com.vn.dailycookapp.utils.ErrorCodeConstant;
import com.vn.dailycookapp.utils.validate.InvalidPasswordException;
import com.vn.dailycookapp.utils.validate.Validator;
import java.util.HashMap;
import java.util.Map;
import org.EncryptHelper;
import org.dao.UserDAO;
import org.json.JsonTransformer;

/**
 *
 * @author duyetpt
 */
public class ChangePasswordModel extends AbstractModel{

    ChangePasswordInfo changePasswordInfo;
    
    @Override
    protected void preExecute(String... data) throws InvalidParamException, InvalidPasswordException {
        myId = data[0];
        changePasswordInfo = JsonTransformer.getInstance().unmarshall(data[1], ChangePasswordInfo.class);
        validateData();
    }

    @Override
    protected DCAResponse execute() throws Exception {
        DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
        String oldPassEncrypt = EncryptHelper.encrypt(changePasswordInfo.getOldPassword());
        String newPassEncrypt = EncryptHelper.encrypt(changePasswordInfo.getNewPassword());
               
        boolean changePass = UserDAO.getInstance().updatePassword(myId, oldPassEncrypt, newPassEncrypt);
        if (changePass) {
            SessionManager.getInstance().closeAllSessionOfUser(myId);
            String token = SessionManager.getInstance().addSession(myId);
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", token);
            response.setData(tokenMap);
        } else {
            response.setError(ErrorCodeConstant.PASSWORD_INCORRECT.getErrorCode());
        }
        
        return response;
    }
    
    private void validateData() throws InvalidPasswordException {
        Validator.getInstance().validatePassword(changePasswordInfo.getOldPassword());
        Validator.getInstance().validatePassword(changePasswordInfo.getNewPassword());
    }
}
