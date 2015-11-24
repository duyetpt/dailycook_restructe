package com.vn.dailycookapp.service;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.vn.dailycookapp.restmodel.ModelDefine;
import com.vn.dailycookapp.restmodel.ModelResolver;
import com.vn.dailycookapp.restmodel.model.GetRecipeDetailPageModel;
import com.vn.dailycookapp.service.mediatypeopen.MediaTypeWithUtf8;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MediaType;

@Path("/dailycook/recipe")
public class RecipeService {

    @POST
    @Path("/add")
    @Produces(MediaTypeWithUtf8.APPLICATION_JSON_UTF8)
    public Response addRecipe(@HeaderParam(HeaderField.USER_ID) String userId, String data) {
        String responseData = ModelResolver.getApi(ModelDefine.CREATE_RECIPE).doProcess(userId, data);
        return Response.ok().entity(responseData).build();
    }

    @GET
    @Path("/get")
    @Produces(MediaTypeWithUtf8.APPLICATION_JSON_UTF8)
    public Response getRecipe(@HeaderParam(HeaderField.USER_ID) String userId, @QueryParam("recipe") String recipeId) {
        String responseData = ModelResolver.getApi(ModelDefine.GET_RECIPE).doProcess(userId, recipeId);
        return Response.ok().entity(responseData).build();
    }
    
    @GET
    @Path("/detailpage")
    @Produces(MediaTypeWithUtf8.TEXT_HTML_UTF8)
    public Response getRecipeDetailPage(@QueryParam("recipe") String recipeId) {
        GetRecipeDetailPageModel.getInstance().setRecipeId(recipeId);
        String responseData;
        try {
            responseData = GetRecipeDetailPageModel.getInstance().execute();
            return Response.ok().entity(responseData).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    @GET
    @Path("/{lang}/ingredient_type")
    @Produces(MediaTypeWithUtf8.APPLICATION_JSON_UTF8)
    public Response getIngredientType(@PathParam("lang") String language) {
        String data = ModelResolver.getApi(ModelDefine.GET_INGREDIENT_TYPE).doProcess(language);
        return Response.ok().entity(data).build();
    }

    @GET
    @Path("/{lang}/units")
    @Produces(MediaTypeWithUtf8.APPLICATION_JSON_UTF8)
    public Response getUnits(@PathParam("lang") String language) {
        String data = ModelResolver.getApi(ModelDefine.GET_UNITS).doProcess(language);
        return Response.ok().entity(data).build();
    }

    @GET
    @Path("/{recipeId}/comment/get")
    @Produces(MediaTypeWithUtf8.APPLICATION_JSON_UTF8)
    public Response getComment(@PathParam("recipeId") String recipeId, @QueryParam("skip") String skip,
            @QueryParam("take") String take) {
        String data = ModelResolver.getApi(ModelDefine.GET_COMMENT).doProcess(recipeId, skip, take);
        return Response.ok().entity(data).build();
    }

    @GET
    @Path("/{recipeId}/favorite")
    @Produces(MediaTypeWithUtf8.APPLICATION_JSON_UTF8)
    public Response favorite(@HeaderParam(HeaderField.USER_ID) String userId, @PathParam("recipeId") String recipeId,
            @QueryParam("flag") String flag) {
        String data = ModelResolver.getApi(ModelDefine.FAVORITE).doProcess(recipeId, flag, userId);
        return Response.ok().entity(data).build();
    }

    // http://dailycookapp.cloudapp.net:8181/dailycook/recipe/{recipeId}/comment
    @POST
    @Path("/{recipeId}/comment")
    @Produces(MediaTypeWithUtf8.APPLICATION_JSON_UTF8)
    public Response comment(@HeaderParam(HeaderField.USER_ID) String userId, @PathParam("recipeId") String recipeId,
            String content) {
        String data = ModelResolver.getApi(ModelDefine.COMMENT).doProcess(recipeId, userId, content);
        return Response.ok().entity(data).build();
    }

    // http://dailycookapp.cloudapp.net:8181/dailycook/recipe/{name |
    // ingredients | tags}/suggest?keyword={keyword}
    @GET
    @Path("/{suggestion}/suggest")
    @Produces(MediaTypeWithUtf8.APPLICATION_JSON_UTF8)
    public Response suggestSearching(@QueryParam("keyword") String keyword, @PathParam("suggestion") String type) {
        String data = ModelResolver.getApi(ModelDefine.SUGGEST_SEARCHING).doProcess(type, keyword);
        return Response.ok().entity(data).build();
    }

    // http://dailycookapp.cloudapp.net:8181/dailycook/recipe/search?keyword={keyword}&filter={name
    // | ingredients | tags}
    @GET
    @Path("/search")
    @Produces(MediaTypeWithUtf8.APPLICATION_JSON_UTF8)
    public Response search(@HeaderParam(HeaderField.USER_ID) String userId, @QueryParam("keyword") String keyword,
            @QueryParam("filter") String filter, @QueryParam("skip") String skip, @QueryParam("take") String take) {
        String data = ModelResolver.getApi(ModelDefine.SEARCH_RECIPE).doProcess(filter, keyword, userId, skip, take);
        return Response.ok().entity(data).build();
    }

    @POST
    @Path("/report")
    @Produces(MediaType.APPLICATION_JSON)
    public Response report(@HeaderParam(HeaderField.USER_ID) String userId, String reportInfo) {
        String data = ModelResolver.getApi(ModelDefine.REPORT_RECIPE).doProcess(userId, reportInfo);
        return Response.ok().entity(data).build();
    }
    
    @GET
    @Path("/report/{language}/reasons")
    @Produces(MediaTypeWithUtf8.APPLICATION_JSON_UTF8)
    public Response getReason(@PathParam("language") String language) {
        String data = ModelResolver.getApi(ModelDefine.GET_REPORT_REASON).doProcess(language);
        return Response.ok().entity(data).build();
    }
}
