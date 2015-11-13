package com.vn.dailycookapp.restmodel;

import com.vn.dailycookapp.restmodel.model.AddRecipeToMeal;
import com.vn.dailycookapp.restmodel.model.CommentModel;
import com.vn.dailycookapp.restmodel.model.CreateRecipeModel;
import com.vn.dailycookapp.restmodel.model.FavoriteRecipeModel;
import com.vn.dailycookapp.restmodel.model.FollowUserModel;
import com.vn.dailycookapp.restmodel.model.GetCommentModel;
import com.vn.dailycookapp.restmodel.model.GetFavoriteRecipeModel;
import com.vn.dailycookapp.restmodel.model.GetIngredientTypesModel;
import com.vn.dailycookapp.restmodel.model.GetLeftSideInfoModel;
import com.vn.dailycookapp.restmodel.model.GetNotificationsModel;
import com.vn.dailycookapp.restmodel.model.GetPlanMeal;
import com.vn.dailycookapp.restmodel.model.GetPlanMealDetail;
import com.vn.dailycookapp.restmodel.model.GetRecipeModel;
import com.vn.dailycookapp.restmodel.model.GetRecipeOfUserModel;
import com.vn.dailycookapp.restmodel.model.GetReportReasonModel;
import com.vn.dailycookapp.restmodel.model.GetUnitsModel;
import com.vn.dailycookapp.restmodel.model.GetUserProfileModel;
import com.vn.dailycookapp.restmodel.model.LoginModel;
import com.vn.dailycookapp.restmodel.model.LogoutModel;
import com.vn.dailycookapp.restmodel.model.NewFeedModel;
import com.vn.dailycookapp.restmodel.model.RegisterModel;
import com.vn.dailycookapp.restmodel.model.RemoveRecipeFromMealModel;
import com.vn.dailycookapp.restmodel.model.ReportRecipeModel;
import com.vn.dailycookapp.restmodel.model.SearchRecipeModel;
import com.vn.dailycookapp.restmodel.model.SearchUserModel;
import com.vn.dailycookapp.restmodel.model.UpdateNotificationModel;
import com.vn.dailycookapp.restmodel.model.suggestSearchingModel;

public enum ModelDefine {
	
	REGISTER("register", RegisterModel.class),
	LOGIN("login", LoginModel.class),
	LOGOUT("logout", LogoutModel.class),
	FOLLOW("follow_user", FollowUserModel.class),
	GET_NOTIFICATION("get_notification", GetNotificationsModel.class),
	UPDATE_NOTIFICATION("update_notification", UpdateNotificationModel.class),
	GET_FAVORITE_RECIPE("get_favorite_recipe", GetFavoriteRecipeModel.class),
	ADD_RECIPE_TO_MEAL("add_recipe_to_meal", AddRecipeToMeal.class),
	GET_PLAN_MEAL("get_plan_meal", GetPlanMeal.class),
	GET_PLAN_MEAL_DETAIL("get_plan_meal_detail", GetPlanMealDetail.class),
	REMOVE_RECIPE_TO_MEAL("remove_recipe_to_meal", RemoveRecipeFromMealModel.class),
	GET_LEFT_SIDE_INFO("get_left_side_info", GetLeftSideInfoModel.class),
	GET_USER_PROFILE("get_user_profile", GetUserProfileModel.class),
	GET_RECIPE_OF_USER("get_recipe_of_user", GetRecipeOfUserModel.class),
        
        GET_INGREDIENT_TYPE("get_ingredient_type", GetIngredientTypesModel.class),
	GET_UNITS("get_units", GetUnitsModel.class),
	CREATE_RECIPE("create_recipe", CreateRecipeModel.class),
	GET_RECIPE("get_recipe", GetRecipeModel.class),
	GET_COMMENT("get_comment", GetCommentModel.class),
	NEW_FEED("new_feed", NewFeedModel.class),
	FAVORITE("favorite", FavoriteRecipeModel.class),
	COMMENT("comment", CommentModel.class),
	REPORT_RECIPE("report_recipe", ReportRecipeModel.class),
        GET_REPORT_REASON("get_report_reason", GetReportReasonModel.class),
        
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
