package com.vn.dailycookapp.restmodel.model;

import org.dao.MealDAO;

import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.restmodel.InvalidParamException;
import com.vn.dailycookapp.utils.ErrorCodeConstant;

public class AddRecipeToMeal extends AbstractModel {
	
	private String				day;
	private String				time;
	
	// owner, recipeId, mealId, flag, day, time
	@Override
	protected void preExecute(String... data) throws InvalidParamException {
		myId = data[0];
		recipeId = data[1];
		day = data[2];
		time = data[3];
	}
	
	@Override
	protected DCAResponse execute() throws Exception {
		DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
		MealDAO.getInstance().addRecipeToMeal(day, time, recipeId, myId);
		return response;
	}
	
}
