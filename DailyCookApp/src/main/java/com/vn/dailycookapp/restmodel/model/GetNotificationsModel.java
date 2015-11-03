package com.vn.dailycookapp.restmodel.model;

import java.util.ArrayList;
import java.util.List;

import org.dao.NotificationDAO;
import org.entity.Notification;

import com.vn.dailycookapp.cache.user.CompactUserInfo;
import com.vn.dailycookapp.cache.user.UserCache;
import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.entity.response.GetNotificationResponseData;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.utils.ErrorCodeConstant;
import com.vn.dailycookapp.utils.lang.Language;

public class GetNotificationsModel extends AbstractModel {
	private int	skip;
	private int	take;
	
	@Override
	protected void preExecute(String... data) throws Exception {
		myId = data[0];
		skip = Integer.parseInt(data[1]);
		take = Integer.parseInt(data[2]);
	}
	
	@Override
	protected DCAResponse execute() throws Exception {
		DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
		List<Notification> list = NotificationDAO.getInstance().list(myId, skip, take);
		List<GetNotificationResponseData> data = new ArrayList<GetNotificationResponseData>();
		CompactUserInfo user = UserCache.getInstance().get(myId);
		
		if (list != null)
			for (Notification noti : list) {
				GetNotificationResponseData responseInfo = new GetNotificationResponseData();
				responseInfo.setMsg(Language.getInstance().getMessage(noti.getType(), user.getLanguage()));
				responseInfo.setRecipeId(noti.getRecipeId());
				responseInfo.setStatus(noti.getStatus() == Notification.READED_STATUS);
				responseInfo.setFromAvatar(noti.getFromAvatar());
				responseInfo.setFromName(noti.getFromName());
				responseInfo.setRecipeTitle(noti.getRecipeTitle());
				responseInfo.setType(noti.getType());
				responseInfo.setFrom(noti.getFrom());
				
				data.add(responseInfo);
			}
		response.setData(data);
		return response;
	}
	
}
