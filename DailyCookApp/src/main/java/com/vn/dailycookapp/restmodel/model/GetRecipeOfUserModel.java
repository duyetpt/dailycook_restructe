/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.restmodel.model;

import com.vn.dailycookapp.cache.user.CompactUserInfo;
import com.vn.dailycookapp.cache.user.UserCache;
import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.entity.response.NewFeedResponseData;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.utils.ErrorCodeConstant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.dao.FavoriteDAO;
import org.dao.RecipeDAO;
import org.entity.Favorite;
import org.entity.Recipe;

/**
 *
 * @author duyetpt
 */
public class GetRecipeOfUserModel extends AbstractModel {

    private String userId;
    private int skip;
    private int take;

    @Override
    protected void preExecute(String... data) throws Exception {
        myId = data[0];
        userId = data[1];
        skip = Integer.parseInt(data[2]);
        take = Integer.parseInt(data[3]);
    }

    @Override
    protected DCAResponse execute() throws Exception {
        DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
        List<NewFeedResponseData> datas = new ArrayList<>();
        
        List<Recipe> recipes = RecipeDAO.getInstance().getRecipeOfUser(userId, skip, take);
        if (!recipes.isEmpty()) {
            Set<String> userIds = new TreeSet<>();
            // get list owner of list recipe
            for (Recipe recipe : recipes) {
                userIds.add(recipe.getOwner());
            }

            // Get owners for list recipe
            List<CompactUserInfo> users = UserCache.getInstance().list(userIds);

            // Get list favorite recipe of this user
            Favorite favorite = null;
            if (myId != null) {
                favorite = FavoriteDAO.getInstance().get(myId, Favorite.class);
            }
            // Merger data for response
            for (Recipe recipe : recipes) {
                NewFeedResponseData data = new NewFeedResponseData();
                data.setnComment(recipe.getCommentNumber());
                data.setnFavorite(recipe.getFavoriteNumber());
                data.setRecipeName(recipe.getTitle());
                data.setRecipePicture(recipe.getPictureUrl());
                data.setRecipeId(recipe.getId());
                data.setView(recipe.getView());
                if (favorite != null) {
                    data.setIsFavorite(favorite.getRecipeIds().contains(recipe.getId()));
                }

                for (CompactUserInfo user : users) {
                    if (user.getUserId().equals(recipe.getOwner())) {
                        data.setAvatarUrl(user.getAvatarUrl());
                        data.setUsername(user.getDisplayName());
                    }
                }

                datas.add(data);
            }
        }
        response.setData(datas);
        return response;
    }

}
