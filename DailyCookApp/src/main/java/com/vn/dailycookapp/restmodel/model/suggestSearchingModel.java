package com.vn.dailycookapp.restmodel.model;

import java.util.List;

import org.Unicode;

import com.vn.dailycookapp.cache.RecipeManager;
import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.utils.ErrorCodeConstant;

public class suggestSearchingModel extends AbstractModel {
	
	private static final String	INGREDIENT_TYPE	= "ingredients";
	private static final String	TAG_TYPE		= "tags";
	private static final String	NAME_TYPE		= "name";
	
	private String				type;
	private String				keyword;
	
	@Override
	protected void preExecute(String... data) throws Exception {
		type = data[0];
		// normalize keyword to search
		keyword = Unicode.toAscii(data[1]);
	}
	
	@Override
	protected DCAResponse execute() throws Exception {
		DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
		List<String> result = null;
		switch (type) {
			case INGREDIENT_TYPE:
				result = RecipeManager.getInstance().suggestIngredients(keyword);
				break;
			case TAG_TYPE:
				result = RecipeManager.getInstance().suggestTags(keyword);
				break;
			case NAME_TYPE:
				result = RecipeManager.getInstance().suggestTags(keyword);
				break;
		}
		
		response.setData(result);
		return response;
	}
	
}
