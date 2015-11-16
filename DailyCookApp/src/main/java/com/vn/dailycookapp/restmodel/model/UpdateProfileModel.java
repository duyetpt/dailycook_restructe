/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.restmodel.model;

import com.vn.dailycookapp.entity.request.UpdateUserInfo;
import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.utils.ErrorCodeConstant;
import org.dao.UserDAO;
import org.json.JsonTransformer;

/**
 *
 * @author duyetpt
 */
public class UpdateProfileModel extends AbstractModel{

    UpdateUserInfo updateUserInfo;
    @Override
    protected void preExecute(String... data) throws Exception {
        myId = data[0];
        updateUserInfo = JsonTransformer.getInstance().unmarshall(data[1], UpdateUserInfo.class);
    }

    @Override
    protected DCAResponse execute() throws Exception {
        DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
        UserDAO.getInstance().updatUserProfile(myId, updateUserInfo.getAvatarUrl(), updateUserInfo.getDisplayName(), updateUserInfo.getDob());
        return response;
    }
    
}
