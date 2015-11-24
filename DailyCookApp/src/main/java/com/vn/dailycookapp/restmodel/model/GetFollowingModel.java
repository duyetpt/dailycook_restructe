/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.restmodel.model;

import com.vn.dailycookapp.cache.user.CompactUserInfo;
import com.vn.dailycookapp.cache.user.UserCache;
import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.entity.response.SearchUserResponseData;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.restmodel.InvalidParamException;
import com.vn.dailycookapp.utils.ErrorCodeConstant;
import java.util.ArrayList;
import java.util.List;
import org.dao.FollowingDAO;
import org.entity.Following;

/**
 *
 * @author duyetpt
 */
public class GetFollowingModel extends AbstractModel {

    private static final String FOLLOWER = "follower";
    private static final String FOLLOWING = "following";

    private String userId;
    private String follow;

    @Override
    protected void preExecute(String... data) throws InvalidParamException {
        userId = data[0];
        follow = data[1];
        myId = data[2];
    }

    @Override
    protected DCAResponse execute() throws Exception {
        DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
        Following following = FollowingDAO.getInstance().get(userId, Following.class);
        List<SearchUserResponseData> datas = new ArrayList<>();

        List<CompactUserInfo> compactUsers = null;
        switch (follow) {
            case FOLLOWER:
                if (following == null || following.getFollowers()== null || following.getFollowers().isEmpty()) {
                    compactUsers = new ArrayList<>();
                } else {
                    compactUsers = UserCache.getInstance().list(following.getFollowers());
                }
                break;
            case FOLLOWING:
                if (following == null || following.getStarIds() == null || following.getStarIds().isEmpty()) {
                    compactUsers = new ArrayList<>();
                } else {
                    compactUsers = UserCache.getInstance().list(following.getStarIds());
                }

        }

        for (CompactUserInfo cUser : compactUsers) {
            SearchUserResponseData info = new SearchUserResponseData();
            info.setAvatarUrl(cUser.getAvatarUrl());
            info.setIntroduce(cUser.getIntroduce());
            info.setNumberFollower(cUser.getNumberFollower());
            info.setNumberRecipes(cUser.getNumberRecipes());
            info.setUserId(cUser.getUserId());
            info.setUsername(cUser.getDisplayName());
            if (follow.equals(FOLLOWING)) {
                info.setFollowing(true);
            } else {
                if (myId == null) {
                    info.setFollowing(false);
                } else {
                    if(following.getStarIds() != null) {
                        info.setFollowing(following.getStarIds().contains(myId));
                    }
                }
            }

            datas.add(info);
        }

        response.setData(datas);
        return response;
    }

}
