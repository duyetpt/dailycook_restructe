package com.vn.dailycookapp.restmodel.model;

import org.dao.MealDAO;

import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.utils.ErrorCodeConstant;

public class RemoveRecipeFromMealModel extends AbstractModel {
	
	private String	day;
	private String	time;
	
	@Override
	protected void preExecute(String... data) throws Exception {
		myId = data[0];
		recipeId = data[1];
		day = data[2];
		time = data[3];
	}
	
	@Override
	protected DCAResponse execute() throws Exception {
		DCAResponse dcaResponse = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
		MealDAO.getInstance().removeRecipeToMeal(myId, day, time, recipeId);
		
		return dcaResponse;
	}
	
}
