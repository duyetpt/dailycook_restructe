package com.vn.dailycookapp.restmodel.model;

import java.util.List;

import org.dao.MealDAO;
import org.entity.Meal;

import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.utils.ErrorCodeConstant;

public class GetPlanMeal extends AbstractModel {
	
	@Override
	protected void preExecute(String... data) throws Exception {
		myId = data[0];
	}
	
	@Override
	protected DCAResponse execute() throws Exception {
		DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
		List<Meal> list = MealDAO.getInstance().getPlanMeal(myId);
		
		if (list != null)
			for (Meal meal : list) {
				meal.setNumRecipe(meal.getRecipeIds() == null ? 0 : meal.getRecipeIds().size());
			}
		response.setData(list);
		return response;
	}
	
}
