package com.vn.dailycookapp.service;

import com.vn.dailycookapp.notification.appleservice.AppleNotificationManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JsonTransformer;

import com.vn.dailycookapp.security.session.SessionManager;
import javax.ws.rs.PathParam;

@Path("/dailycook/admin/putin93/")
public class AdminService {
	
	@GET
	@Path("list/session")
	@Produces(MediaType.APPLICATION_JSON)
	public String listSession() {
		String result = null;
		result = JsonTransformer.getInstance().marshall(SessionManager.getInstance().getAllSession());
		return result;
	}
        
        @GET
        @Path("push/{userId}/noti")
        @Produces(MediaType.APPLICATION_JSON)
	public String pushNotification(@PathParam("userId") String userId) {
            AppleNotificationManager.getInstance().testPush(userId);
            return "Ok";
	}
}
