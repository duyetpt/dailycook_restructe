/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.restmodel.model;

import com.vn.dailycookapp.cache.user.CompactUserInfo;
import com.vn.dailycookapp.cache.user.UserCache;
import com.vn.dailycookapp.restmodel.InvalidParamException;
import com.vn.dailycookapp.utils.FmBuilder;
import org.bson.types.ObjectId;
import org.dao.RecipeDAO;
import org.entity.Recipe;

/**
 *
 * @author duyetpt
 */
public class GetRecipeDetailPageModel {

    private String recipeId;
    private static final GetRecipeDetailPageModel instance = new GetRecipeDetailPageModel();
    
    private GetRecipeDetailPageModel(){
    }
    
    public static GetRecipeDetailPageModel getInstance() {
        return instance;
    }

    public String execute() throws Exception {
        validateData();
        Recipe recipe = RecipeDAO.getInstance().get(recipeId);
        CompactUserInfo owner = UserCache.getInstance().get(recipe.getOwner());
        recipe.setOwner(owner.getDisplayName());

        return FmBuilder.getInstance().build(owner.getLanguage(), recipe);
    }

    private void validateData() throws InvalidParamException {
        if (!ObjectId.isValid(recipeId)) {
            throw new InvalidParamException();
        }
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

}
