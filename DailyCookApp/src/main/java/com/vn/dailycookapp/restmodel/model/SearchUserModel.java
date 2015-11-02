package com.vn.dailycookapp.restmodel.model;

import java.util.ArrayList;
import java.util.List;

import org.dao.FollowingDAO;
import org.entity.Following;

import com.vn.dailycookapp.cache.user.CompactUserInfo;
import com.vn.dailycookapp.cache.user.UserCache;
import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.entity.response.SearchUserResponseData;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.utils.ErrorCodeConstant;
import com.vn.dailycookapp.utils.validate.InvalidEmailFormatException;
import com.vn.dailycookapp.utils.validate.Validator;

public class SearchUserModel extends AbstractModel {
	private String	username;
	private int		skip;
	private int		take;
	
	@Override
	protected void preExecute(String... data) throws Exception {
		userId = data[0];
		username = data[1].toLowerCase();
		skip = Integer.parseInt(data[2]);
		take = Integer.parseInt(data[3]);
	}
	
	@Override
	protected DCAResponse execute() throws Exception {
		DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
		List<SearchUserResponseData> data = new ArrayList<SearchUserResponseData>();
		List<CompactUserInfo> cUsers = null;
		try {
			Validator.getInstance().validateEmail(username);
			// search by email
			CompactUserInfo cUser = UserCache.getInstance().getInfoByEmail(username);
			
			cUsers = new ArrayList<CompactUserInfo>();
			if (cUser != null)
				cUsers.add(cUser);
		} catch (InvalidEmailFormatException ex) {
			// search by username
			cUsers = UserCache.getInstance().list(username);
			logger.info("search_user_login_debug:" + cUsers.toString());
		}
		
		Following following = null;
		if (userId != null) {
			following = FollowingDAO.getInstance().get(userId, Following.class);
		}
		
		int countSkip = 0;
		int countTake = 0;
		for (CompactUserInfo cUser : cUsers) {
			if (countSkip < skip) {
				countSkip++;
				continue;
			}
			if (countTake == take) {
				countTake++;
				break;
			}
			SearchUserResponseData info = new SearchUserResponseData();
			info.setAvatarUrl(cUser.getAvatarUrl());
			info.setIntroduce(cUser.getIntroduce());
			info.setNumberFollower(cUser.getNumberFollower());
			info.setNumberRecipes(cUser.getNumberRecipes());
			info.setUserId(cUser.getUserId());
			info.setUsername(cUser.getDisplayName());
			
			if (userId == null)
				info.setFollowing(false);
			else
				info.setFollowing(following.getStarIds().contains(cUser.getUserId()));
			
			data.add(info);
		}
		
		response.setData(data);
		return response;
	}
	
}
