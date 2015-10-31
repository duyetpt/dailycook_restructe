package com.vn.dailycookapp.restmodel.model;

import java.util.ArrayList;
import java.util.List;

import org.Unicode;
import org.dao.IngredientDAO;
import org.dao.RecipeDAO;
import org.dao.TagDAO;
import org.entity.Ingredient;
import org.entity.Recipe;
import org.entity.Tag;

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
	
	// TODO
	@Override
	protected DCAResponse execute() throws Exception {
		DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
		 List<String> result = new ArrayList<String>();
		 switch (type) {
		 case INGREDIENT_TYPE:
			 List<Ingredient> list = IngredientDAO.getInstance().list(keyword);
			 if (list != null)
				 for (Ingredient in : list)
					 result.add(in.getNormalizedName());
		 break;
		 case TAG_TYPE:
			 List<Tag> tags = TagDAO.getInstance().list(keyword);
			 if (tags != null)
				 for (Tag tag : tags)
					 result.add(tag.getNormalizeTag()); 
		 break;
		 case NAME_TYPE:
			 List<Recipe> recipes = RecipeDAO.getInstance().list(keyword);
			 if (recipes != null)
				 for (Recipe recipe : recipes)
					 result.add(recipe.getNormalizedTitle()); 
		 break;
		 }
		
		 response.setData(result);
		return response;
	}
	
}
