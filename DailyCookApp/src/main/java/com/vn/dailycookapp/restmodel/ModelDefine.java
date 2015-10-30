package com.vn.dailycookapp.restmodel;

import com.vn.dailycookapp.restmodel.model.CommentModel;
import com.vn.dailycookapp.restmodel.model.CreateRecipeModel;
import com.vn.dailycookapp.restmodel.model.FavoriteRecipeModel;
import com.vn.dailycookapp.restmodel.model.FollowUserModel;
import com.vn.dailycookapp.restmodel.model.GetCommentModel;
import com.vn.dailycookapp.restmodel.model.GetIngredientTypesModel;
import com.vn.dailycookapp.restmodel.model.GetRecipeModel;
import com.vn.dailycookapp.restmodel.model.GetUnitsModel;
import com.vn.dailycookapp.restmodel.model.LoginModel;
import com.vn.dailycookapp.restmodel.model.LogoutModel;
import com.vn.dailycookapp.restmodel.model.NewFeedModel;
import com.vn.dailycookapp.restmodel.model.RegisterModel;
import com.vn.dailycookapp.restmodel.model.SearchRecipeModel;
import com.vn.dailycookapp.restmodel.model.SearchUserModel;
import com.vn.dailycookapp.restmodel.model.suggestSearchingModel;

public enum ModelDefine {
	
	GET_INGREDIENT_TYPE("get_ingredient_type", GetIngredientTypesModel.class),
	GET_UNITS("get_units", GetUnitsModel.class),
	CREATE_RECIPE("create_recipe", CreateRecipeModel.class),
	GET_RECIPE("get_recipe", GetRecipeModel.class),
	REGISTER("register", RegisterModel.class),
	LOGIN("login", LoginModel.class),
	LOGOUT("logout", LogoutModel.class),
	FOLLOW("follow_user", FollowUserModel.class),
	
	GET_COMMENT("get_comment", GetCommentModel.class),
	NEW_FEED("new_feed", NewFeedModel.class),
	FAVORITE("favorite", FavoriteRecipeModel.class),
	COMMENT("comment", CommentModel.class),
	
	SUGGEST_SEARCHING("suggest_searching", suggestSearchingModel.class),
	SEARCH_RECIPE("search_recipe", SearchRecipeModel.class),
	SEARCH_USER("search_user", SearchUserModel.class);
	
	private final String							name;
	private final Class<? extends AbstractModel>	model;
	
	private ModelDefine(String name, Class<? extends AbstractModel> model) {
		this.name = name;
		this.model = model;
	}
	
	public String getName() {
		return name;
	}
	
	public Class<? extends AbstractModel> getModel() {
		return model;
	}
}
