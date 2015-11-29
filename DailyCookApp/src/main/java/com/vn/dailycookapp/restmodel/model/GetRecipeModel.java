package com.vn.dailycookapp.restmodel.model;

import org.bson.types.ObjectId;
import org.dao.FavoriteDAO;
import org.dao.FollowingDAO;
import org.dao.RecipeDAO;
import org.entity.Recipe;

import com.vn.dailycookapp.cache.user.CompactUserInfo;
import com.vn.dailycookapp.cache.user.UserCache;
import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.entity.response.RecipeResponseData;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.restmodel.InvalidParamException;
import com.vn.dailycookapp.utils.ErrorCodeConstant;

/**
 *
 * @author duyetpt Check recipe id Get recipe by id Get owner information Do
 * user favour this recipe Do user following this owner
 */
public class GetRecipeModel extends AbstractModel {

    @Override
    protected void preExecute(String... data) throws Exception {
        try {
            myId = data[0];
            recipeId = data[1];
        } catch (Exception ex) {
            throw new InvalidParamException();
        }
        validateData();

    }

    @Override
    protected DCAResponse execute() throws Exception {
        DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
        Recipe recipe = RecipeDAO.getInstance().get(recipeId);
        CompactUserInfo owner = UserCache.getInstance().get(recipe.getOwner());

        // check user favorite recipe
        recipe.setIsFavorite(myId == null ? false : FavoriteDAO.getInstance().isFavorite(myId, recipeId));
        // check userid is following owner of recipe
        boolean isFollowingOwner = myId == null ? false : FollowingDAO.getInstance().isFollowing(myId, owner.getUserId());

        RecipeResponseData data = new RecipeResponseData(recipe, owner);
        data.setIsFollowing(isFollowingOwner);

        // Increase view number
        RecipeDAO.getInstance().increateView(recipeId);

        response.setData(data);
        return response;
    }

    private void validateData() throws InvalidParamException {
        if (!ObjectId.isValid(recipeId)) {
            throw new InvalidParamException();
        }
    }
}
