package com.vn.dailycookapp.restmodel.model;

import java.util.ArrayList;
import java.util.List;

import org.dao.NotificationDAO;
import org.entity.Notification;

import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.entity.response.GetNotificationResponseData;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.utils.ErrorCodeConstant;

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
		if (list != null)
			for (Notification noti : list) {
				GetNotificationResponseData responseInfo = new GetNotificationResponseData();
				responseInfo.setMsg(noti.getMsg());
				responseInfo.setRecipeId(noti.getRecipeId());
				responseInfo.setStatus(noti.getStatus() == Notification.READED_STATUS);
				data.add(responseInfo);
			}
		response.setData(data);
		return response;
	}
	
}
