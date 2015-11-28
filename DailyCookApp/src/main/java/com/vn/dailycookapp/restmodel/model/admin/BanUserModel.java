/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.restmodel.model.admin;

import com.vn.dailycookapp.entity.request.RegisterInfo;
import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.restmodel.InvalidParamException;
import com.vn.dailycookapp.security.session.SessionManager;
import com.vn.dailycookapp.utils.ErrorCodeConstant;
import org.json.JsonTransformer;

/**
 *
 * @author duyetpt
 */
public class BanUserModel extends AbstractModel{

    RegisterInfo adminAcc;
    @Override
    protected void preExecute(String... data) throws InvalidParamException {
        myId = data[0];
        adminAcc = JsonTransformer.getInstance().unmarshall(data[1], RegisterInfo.class);
    }

    @Override
    // TODO : ENCRYPTE ADMIN INFOR
    protected DCAResponse execute() throws Exception {
        DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
        // authentiation admin
        AdminAuth.auth(adminAcc);
        
        SessionManager.getInstance().closeAllSessionOfUser(myId);
        return response;
    }
}
