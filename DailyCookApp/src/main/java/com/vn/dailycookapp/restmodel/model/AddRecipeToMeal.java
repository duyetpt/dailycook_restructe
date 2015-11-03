package com.vn.dailycookapp.restmodel.model;

import org.dao.MealDAO;

import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.restmodel.InvalidParamException;
import com.vn.dailycookapp.utils.ErrorCodeConstant;

public class AddRecipeToMeal extends AbstractModel {
	private static final String	ADD_FLAG	= "1";
	private static final String	REMOVE_FLAG	= "-1";
	
	private String				flag;
	private String				day;
	private String				time;
	private String				mealId;
	
	// owner, recipeId, mealId, flag, day, time
	@Override
	protected void preExecute(String... data) throws InvalidParamException {
		myId = data[0];
		recipeId = data[1];
		mealId = data[2];
		flag = data[3];
		day = data[4];
		time = data[5];
	}
	
	@Override
	protected DCAResponse execute() throws Exception {
		DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
		if (ADD_FLAG.equals(flag)) {
			MealDAO.getInstance().addRecipeToMeal(mealId, day, time, recipeId, myId);
		} else if (REMOVE_FLAG.equals(flag)) {
			MealDAO.getInstance().removeRecipeToMeal(mealId, recipeId);
		}
		
		return response;
	}
	
}
