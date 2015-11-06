package com.vn.dailycookapp.restmodel.model;

import com.vn.dailycookapp.cache.user.CompactUserInfo;
import com.vn.dailycookapp.cache.user.UserCache;
import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.restmodel.InvalidParamException;
import com.vn.dailycookapp.security.authentication.CurrentUser;
import com.vn.dailycookapp.utils.ErrorCodeConstant;

public class GetLeftSideInfoModel extends AbstractModel {
	
	@Override
	protected void preExecute(String... data) throws InvalidParamException {
		myId = data[0];
	}
	
	@Override
	protected DCAResponse execute() throws Exception {
		DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
		CompactUserInfo user = UserCache.getInstance().get(myId);
		
		CurrentUser cUser = new CurrentUser();
		cUser.setAvatarUrl(user.getAvatarUrl());
		cUser.setCoverUrl(user.getCoverUrl());
		cUser.setDisplayName(user.getDisplayName());
		cUser.setNumberFollower(user.getNumberFollower());
		cUser.setNumberFollowing(user.getNumberFollowing());
		cUser.setNumberRecipes(user.getNumberRecipes());
		
		response.setData(cUser);
		return response;
	}
	
}
