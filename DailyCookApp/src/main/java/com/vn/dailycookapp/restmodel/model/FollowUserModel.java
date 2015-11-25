package com.vn.dailycookapp.restmodel.model;

import org.dao.FollowingDAO;
import org.dao.UserDAO;
import org.entity.Notification;

import com.vn.dailycookapp.cache.user.CompactUserInfo;
import com.vn.dailycookapp.cache.user.UserCache;
import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.entity.response.FollowResponseData;
import com.vn.dailycookapp.notification.NotificationActionImp;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.restmodel.InvalidParamException;
import com.vn.dailycookapp.utils.ErrorCodeConstant;

public class FollowUserModel extends AbstractModel {

    private static final String FOLLOW_FLAG = "1";
    private static final String UNFOLLOW_FLAG = "-1";

    private String starId;
    private String flag;

    @Override
    protected void preExecute(String... data) throws Exception {
        try {
            myId = data[0];
            starId = data[1];
            flag = data[2];
        } catch (Exception ex) {
            throw new InvalidParamException();
        }

    }

    //
    @Override
    protected DCAResponse execute() throws Exception {
        DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
        boolean success = false;
        switch (flag) {
            case FOLLOW_FLAG:
                // add following
                success = FollowingDAO.getInstance().following(myId, starId);
                if (success) {
                    // add follower
                    FollowingDAO.getInstance().addFollower(starId, myId);

                    // increase following number
                    UserDAO.getInstance().increateFollowingNumber(myId);
                    // increase follower number
                    UserDAO.getInstance().increateFollowerNumber(starId);
                    // Cache update
                    UserCache.getInstance().get(myId).increaseNumberFollowing();
                    UserCache.getInstance().get(starId).increaseNumberFollower();
                    // Notification
                    NotificationActionImp.getInstance().addNotification(null, null, myId, starId,
                            Notification.NEW_FOLLOWER_TYPE);
                }
                break;
            case UNFOLLOW_FLAG:
                // add following
                success = FollowingDAO.getInstance().unfollow(myId, starId);
                if (success) {
                    // add follower
                    FollowingDAO.getInstance().removeFollower(starId, myId);
                    FollowingDAO.getInstance().removeFollowing(myId, starId);
                    // increase following number
                    UserDAO.getInstance().decreaseFollowingNumber(myId);
                    // increase follower number
                    UserDAO.getInstance().decreaseFollowerNumber(starId);
                    // Cache update
                    UserCache.getInstance().get(myId).decreaseNumberFollowing();
                    UserCache.getInstance().get(starId).decreaseNumberFollower();
                }
                break;
        }

        CompactUserInfo user = UserCache.getInstance().get(myId);
        FollowResponseData data = new FollowResponseData();
        data.setFollowingNumber(user.getNumberFollowing());

        response.setData(data);
        return response;
    }

}
