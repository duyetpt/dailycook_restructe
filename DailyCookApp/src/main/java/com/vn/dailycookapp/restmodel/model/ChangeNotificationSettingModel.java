/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.restmodel.model;

import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.restmodel.InvalidParamException;
import com.vn.dailycookapp.utils.ErrorCodeConstant;
import org.dao.DeviceTokenDAO;
import org.dao.UserDAO;
import org.entity.DeviceToken;

/**
 *
 * @author duyetpt
 */
public class ChangeNotificationSettingModel extends AbstractModel{
    private static final String ON_NOTIFICATION = "1";
    private static final String OFF_NOTIFICATION = "0";
    
    private String deviceToken;
    private String notificationFlag;
    
    @Override
    protected void preExecute(String... data) throws InvalidParamException{
        myId = data[0];
        deviceToken = data[1];
        this.notificationFlag = data[2];
    }

    @Override
    protected DCAResponse execute() throws Exception {
        DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
        if (this.notificationFlag.equals(ON_NOTIFICATION)) {
            UserDAO.getInstance().updateNotificationFlag(myId, true);
            DeviceToken deviceToken = new DeviceToken(myId, this.deviceToken, DeviceToken.IOS_PLATFORM);
            DeviceTokenDAO.getInstance().save(deviceToken);
        } else {
            UserDAO.getInstance().updateNotificationFlag(myId, false);
            DeviceTokenDAO.getInstance().removeUserDevices(myId);
        }
        
        return response;
    }
    
    public void validate() throws InvalidParamException{
        if (deviceToken.length() != 64) {
            throw new InvalidParamException();
        }
    }
    
}
