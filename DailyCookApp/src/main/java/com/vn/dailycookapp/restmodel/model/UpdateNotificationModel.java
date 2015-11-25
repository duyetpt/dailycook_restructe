package com.vn.dailycookapp.restmodel.model;

import com.vn.dailycookapp.cache.user.UserCache;
import org.dao.NotificationDAO;

import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.utils.ErrorCodeConstant;
import org.dao.UserDAO;

public class UpdateNotificationModel extends AbstractModel {

    private String notiId;

    @Override
    protected void preExecute(String... data) throws Exception {
        myId = data[0];
        notiId = data[1];
    }

    @Override
    protected DCAResponse execute() throws Exception {
        DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
        NotificationDAO.getInstance().update(notiId);
        UserDAO.getInstance().decreaseNotificationNumber(myId);
        UserCache.getInstance().get(myId).decreaseNumberNotificaion();
        return response;
    }

}
