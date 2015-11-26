package com.vn.dailycookapp.restmodel.model;

import com.vn.dailycookapp.cache.user.CompactUserInfo;
import com.vn.dailycookapp.cache.user.UserCache;
import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.restmodel.InvalidParamException;
import com.vn.dailycookapp.security.authentication.CurrentUser;
import com.vn.dailycookapp.utils.ErrorCodeConstant;

public class GetLeftSideInfoModel extends AbstractModel {

    private String token;

    @Override
    protected void preExecute(String... data) throws InvalidParamException {
        myId = data[0];
        token = data[1];
    }

    @Override
    protected DCAResponse execute() throws Exception {
        DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
        CurrentUser cUser = new CurrentUser();
        if (myId != null) {
            CompactUserInfo user = UserCache.getInstance().get(myId);
            cUser.setUserId(user.getUserId());
            cUser.setAvatarUrl(user.getAvatarUrl());
            cUser.setCoverUrl(user.getCoverUrl());
            cUser.setDisplayName(user.getDisplayName());
            cUser.setNumberFollower(user.getNumberFollower());
            cUser.setNumberFollowing(user.getNumberFollowing());
            cUser.setNumberRecipes(user.getNumberRecipes());
            cUser.setNumberNotification(user.getNumberNotification());
            // get token
            cUser.setToken(token);
        }

        response.setData(cUser);
        return response;
    }

}
