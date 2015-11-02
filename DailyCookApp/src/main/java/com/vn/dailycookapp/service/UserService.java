package com.vn.dailycookapp.service;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.vn.dailycookapp.restmodel.ModelDefine;
import com.vn.dailycookapp.restmodel.ModelResolver;
import com.vn.dailycookapp.service.mediatypeopen.MediaTypeWithUtf8;

@Path("/dailycook/user")
public class UserService {
	
	@POST
	@Produces(MediaTypeWithUtf8.APPLICATION_JSON_UTF8)
	// @Consumes(MediaType.APPLICATION_JSON)
	@Path("/login")
	public Response login(@HeaderParam(HeaderField.AUTHORIZATION) String authInfo,
			@HeaderParam(HeaderField.LOGIN_METHOD) String loginMethod) {
		
		String data = ModelResolver.getApi(ModelDefine.LOGIN).doProcess(authInfo, loginMethod);
		return Response.ok(data).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/logout")
	public Response logout(@HeaderParam(HeaderField.USER_ID) String userId) {
		String data = ModelResolver.getApi(ModelDefine.LOGOUT).doProcess(userId);
		return Response.ok(data).build();
	}
	
	@POST
	@Produces(MediaTypeWithUtf8.APPLICATION_JSON_UTF8)
	@Path("/register")
	public Response register(String userInfo) {
		String data = ModelResolver.getApi(ModelDefine.REGISTER).doProcess(userInfo);
		return Response.ok(data).build();
	}
	
	// http://168.63.239.92:8181/dailycook/user/newfeed?skip={skip}&take={take}&sort={sort}
	@GET
	@Produces(MediaTypeWithUtf8.APPLICATION_JSON_UTF8)
	@Path("/newfeed")
	public Response getNewFeed(@HeaderParam(HeaderField.USER_ID) String userId, @QueryParam("skip") String skip,
			@QueryParam("take") String take, @QueryParam("sort") String sort) {
		String data = ModelResolver.getApi(ModelDefine.NEW_FEED).doProcess(userId, skip, take, sort);
		return Response.ok(data).build();
	}
	
	// http://dailycookapp.cloudapp.net:8181/dailycook/user/follow/{userId}?flag={flag}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/follow/{userId}")
	public Response follow(@HeaderParam(HeaderField.USER_ID) String owner, @QueryParam("flag") String flag,
			@PathParam("userId") String userId) {
		String data = ModelResolver.getApi(ModelDefine.FOLLOW).doProcess(owner, userId, flag);
		return Response.ok(data).build();
	}
	
	// http://dailycookapp.cloudapp.net:8181/dailycook/user/search?keyword={keyword}
	@GET
	@Produces(MediaTypeWithUtf8.APPLICATION_JSON_UTF8)
	@Path("/search")
	public Response search(@HeaderParam(HeaderField.USER_ID) String owner, @QueryParam("keyword") String username,
			@QueryParam("skip") String skip, @QueryParam("take") String take) {
		String data = ModelResolver.getApi(ModelDefine.SEARCH_USER).doProcess(owner, username, skip, take);
		return Response.ok(data).build();
	}
	
	// http://dailycookapp.cloudapp.net:8181/dailycook/user/notification?skip={skip}&take={take}
	@GET
	@Produces(MediaTypeWithUtf8.APPLICATION_JSON_UTF8)
	@Path("/notification")
	public Response getNotification(@HeaderParam(HeaderField.USER_ID) String owner, @QueryParam("skip") String skip,
			@QueryParam("take") String take) {
		String data = ModelResolver.getApi(ModelDefine.GET_NOTIFICATION).doProcess(owner, skip, take);
		return Response.ok(data).build();
	}
	
	// http://dailycookapp.cloudapp.net:8181/dailycook/user/notification/{notificationId}/read
	@POST
	@Produces(MediaTypeWithUtf8.APPLICATION_JSON_UTF8)
	@Path("/notification/{notificationId}/read")
	public Response updateNotification(@HeaderParam(HeaderField.USER_ID) String owner,
			@PathParam("notificationId") String notiId) {
		String data = ModelResolver.getApi(ModelDefine.UPDATE_NOTIFICATION).doProcess(owner, notiId);
		return Response.ok(data).build();
	}
	
	// http://dailycookapp.cloudapp.net:8181/dailycook/user/frecipe?skip={skip}&take={take}
	@GET
	@Produces(MediaTypeWithUtf8.APPLICATION_JSON_UTF8)
	@Path("/frecipe")
	public Response getFavoriteRecipe(@HeaderParam(HeaderField.USER_ID) String owner, @QueryParam("skip") String skip,
			@QueryParam("take") String take) {
		String data = ModelResolver.getApi(ModelDefine.GET_FAVORITE_RECIPE).doProcess(owner, skip, take);
		return Response.ok(data).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/planmeal")
	public Response getPlanMeal(@HeaderParam(HeaderField.USER_ID) String owner) {
		String data = ModelResolver.getApi(ModelDefine.GET_PLAN_MEAL).doProcess(owner);
		return Response.ok(data).build();
	}
}
