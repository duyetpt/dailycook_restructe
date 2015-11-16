/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.restmodel.model;

import com.vn.dailycookapp.cache.user.CompactUserInfo;
import com.vn.dailycookapp.cache.user.UserCache;
import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.restmodel.InvalidParamException;
import com.vn.dailycookapp.utils.ErrorCodeConstant;
import java.util.HashMap;
import java.util.Map;
import org.dao.FollowingDAO;

/**
 *
 * @author duyetpt
 */
public class GetUserProfileModel extends AbstractModel{
    
    private String userId;
    
    @Override
    protected void preExecute(String... data) throws InvalidParamException {
        myId = data[0];
        userId = data[1];
    }

    @Override
    protected DCAResponse execute() throws Exception {
        DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
        CompactUserInfo user = UserCache.getInstance().get(userId);
        
        Map<String, Object> data = new HashMap<String, Object>();
        boolean following = false;
        if (!userId.equals(myId)) {
            following = FollowingDAO.getInstance().isFollowing(myId, userId);
        }
        
        data.put("profile", user);
        data.put("following", following);
        
        response.setData(data);
        return response;
    }
}
