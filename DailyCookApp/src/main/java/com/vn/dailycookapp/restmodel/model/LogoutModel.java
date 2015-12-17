package com.vn.dailycookapp.restmodel.model;

import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.security.session.SessionManager;
import com.vn.dailycookapp.utils.ErrorCodeConstant;
import org.dao.DeviceTokenDAO;

public class LogoutModel extends AbstractModel {

    private String token;

    @Override
    protected void preExecute(String... data) throws Exception {
        myId = data[0];
        token = data[1];
    }

    @Override
    protected DCAResponse execute() throws Exception {
        DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
        SessionManager.getInstance().closeSessionOfToken(token);
        // close notification
        DeviceTokenDAO.getInstance().removeUserDevices(myId);
        return response;
    }

}
