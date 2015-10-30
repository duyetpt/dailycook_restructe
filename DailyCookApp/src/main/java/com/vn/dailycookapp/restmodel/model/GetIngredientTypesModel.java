package com.vn.dailycookapp.restmodel.model;

import java.util.List;

import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.utils.ErrorCodeConstant;
import com.vn.dailycookapp.utils.lang.Language;

public class GetIngredientTypesModel extends AbstractModel {
	private String	language;
	
	@Override
	protected void preExecute(String... data) throws Exception {
		language = data[0];
	}
	
	@Override
	protected DCAResponse execute() throws Exception {
		DCAResponse response = new DCAResponse();
		List<String> ingredientTypes = Language.getInstance().listIngredientType(language);
		response.setData(ingredientTypes);
		response.setError(ErrorCodeConstant.SUCCESSUL.getErrorCode());
		return response;
	}
	
}
