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
import javax.ws.rs.PUT;

@Path("/dailycook/user")
public class UserService {

    @POST
    @Produces(MediaTypeWithUtf8.APPLICATION_JSON_UTF8)
    @Path("/login")
    public Response login(@HeaderParam(HeaderField.AUTHORIZATION) String authInfo,
            @HeaderParam(HeaderField.LOGIN_METHOD) String loginMethod) {

        String data = ModelResolver.getApi(ModelDefine.LOGIN).doProcess(authInfo, loginMethod);
        return Response.ok(data).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/logout")
    public Response logout(@HeaderParam(HeaderField.TOKEN) String token) {
        String data = ModelResolver.getApi(ModelDefine.LOGOUT).doProcess(token);
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
    public Response follow(@HeaderParam(HeaderField.USER_ID) String myId, @QueryParam("flag") String flag,
            @PathParam("userId") String userId) {
        String data = ModelResolver.getApi(ModelDefine.FOLLOW).doProcess(myId, userId, flag);
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

    // TODO - DESCRIPTON DOCUMENT
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/planmeal")
    public Response getPlanMeal(@HeaderParam(HeaderField.USER_ID) String owner) {
        String data = ModelResolver.getApi(ModelDefine.GET_PLAN_MEAL).doProcess(owner);
        return Response.ok(data).build();
    }

    // TODO - DESCRIPTON DOCUMENT
    // add/meal/{day}/{time}/{recipeId}
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/meal/{day}/{time}/add/{recipeId}")
    public Response addRecipeToMeal(@HeaderParam(HeaderField.USER_ID) String owner, @PathParam("day") String day,
            @PathParam("time") String time, @PathParam("recipeId") String recipeId) {
        String data = ModelResolver.getApi(ModelDefine.ADD_RECIPE_TO_MEAL).doProcess(owner, recipeId, day, time);
        return Response.ok(data).build();
    }

    // add/meal/{day}/{time}/{recipeId}
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/meal/{day}/{time}/remove/{recipeId}")
    public Response removeRecipeToMeal(@HeaderParam(HeaderField.USER_ID) String owner,
            @PathParam("recipeId") String recipeId, @PathParam("day") String day, @PathParam("time") String time) {
        String data = ModelResolver.getApi(ModelDefine.REMOVE_RECIPE_TO_MEAL).doProcess(owner, recipeId, day, time);
        return Response.ok(data).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/planmeal/{day}/{time}")
    public Response getPlanMealDetail(@HeaderParam(HeaderField.USER_ID) String owner, @PathParam("day") String day,
            @PathParam("time") String time) {
        String data = ModelResolver.getApi(ModelDefine.GET_PLAN_MEAL_DETAIL).doProcess(owner, day, time);
        return Response.ok(data).build();
    }

    // http://dailycook.cloudapp.net:8998/dailycook/user/leftside
    @GET
    @Path("/leftside")
    @Produces(MediaTypeWithUtf8.APPLICATION_JSON_UTF8)
    public Response getPlanMealDetail(@HeaderParam(HeaderField.USER_ID) String owner, @HeaderParam(HeaderField.TOKEN) String token) {
        String data = ModelResolver.getApi(ModelDefine.GET_LEFT_SIDE_INFO).doProcess(owner, token);
        return Response.ok(data).build();
    }

    //
    @GET
    @Path("/{userId}/profile")
    @Produces(MediaTypeWithUtf8.APPLICATION_JSON_UTF8)
    public Response getUserProfile(@HeaderParam(HeaderField.USER_ID) String owner, @PathParam("userId") String userId) {
        String data = ModelResolver.getApi(ModelDefine.GET_USER_PROFILE).doProcess(owner, userId);
        return Response.ok(data).build();
    }

    //{userId}/recipes?skip={skip}&take={take}
    @GET
    @Path("/{userId}/recipes")
    @Produces(MediaTypeWithUtf8.APPLICATION_JSON_UTF8)
    public Response getRecipeOfUser(@HeaderParam(HeaderField.USER_ID) String owner, @PathParam("userId") String userId, @QueryParam("skip") String skip, @QueryParam("take") String take) {
        String data = ModelResolver.getApi(ModelDefine.GET_RECIPE_OF_USER).doProcess(owner, userId, skip, take);
        return Response.ok(data).build();
    }

    //language?lang=vi|en
    @GET
    @Path("/language")
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeLanguage(@HeaderParam(HeaderField.USER_ID) String owner, @QueryParam("lang") String lang) {
        String data = ModelResolver.getApi(ModelDefine.CHANGE_LANGUAGE).doProcess(owner, lang);
        return Response.ok(data).build();
    }

    /**
     * Change password
     *
     * @param owner
     * @param requestData
     * @return
     */
    @POST
    @Path("/password")
    @Produces(MediaType.APPLICATION_JSON)
    public Response changePassword(@HeaderParam(HeaderField.USER_ID) String owner, String requestData) {
        String data = ModelResolver.getApi(ModelDefine.CHANGE_PASSWORD).doProcess(owner, requestData);
        return Response.ok(data).build();
    }

    @POST
    @Path("/profile")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProfile(@HeaderParam(HeaderField.USER_ID) String owner, String requestData) {
        String data = ModelResolver.getApi(ModelDefine.UPDATE_USER_PROFILE).doProcess(owner, requestData);
        return Response.ok(data).build();
    }

    // follow= follower | following
    @GET
    @Path("/{userId}/{follow}/list")
    public Response getFollowing(@HeaderParam(HeaderField.USER_ID) String myId, @PathParam("userId") String userId, @PathParam("follow") String follow) {
        String data = ModelResolver.getApi(ModelDefine.GET_FOLLOWING).doProcess(userId, follow, myId);
        return Response.ok(data).build();
    }
    
    /**
     * Ban user api
     *
     * @param userId
     * @return
     */
    @PUT
    @Path("/{userId}/ban")
    @Produces(MediaType.APPLICATION_JSON)
    public Response banUser(@PathParam("userId") String userId, String adminInfo) {
        String data = ModelResolver.getApi(ModelDefine.BAN_USER).doProcess(userId, adminInfo);
        return Response.ok(data).build();
    }
}
