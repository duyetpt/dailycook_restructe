/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.restmodel.model;

import com.relayrides.pushy.apns.util.TokenUtil;
import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.restmodel.InvalidParamException;
import com.vn.dailycookapp.utils.ErrorCodeConstant;
import org.dao.DeviceTokenDAO;
import org.entity.DeviceToken;
import org.json.JsonTransformer;

/**
 *
 * @author duyetpt
 */
public class DeviceTokenRegisterModel extends AbstractModel {

    private DeviceToken token;
    private String platform;

    @Override
    protected void preExecute(String... data) throws InvalidParamException {
        myId = data[0];
        token = JsonTransformer.getInstance().unmarshall(data[1], DeviceToken.class);
        platform = data[2];
        validate();
    }

    @Override
    protected DCAResponse execute() throws Exception {
        DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
        token.setUserId(myId);
        token.setPlatform(platform);

        if (DeviceTokenDAO.getInstance().getDevice(token.getDeviceToken()) != null) {
            response.setError(ErrorCodeConstant.EXISTED_DEVICE_TOKEN.getErrorCode());
            return response;
        }
        DeviceTokenDAO.getInstance().save(token);

        return response;
    }

    public void validate() throws InvalidParamException {
        if (token.getDeviceToken().length() != 64) {
            logger.error("device token length invalid");
            throw new InvalidParamException();
        }
    }
}
