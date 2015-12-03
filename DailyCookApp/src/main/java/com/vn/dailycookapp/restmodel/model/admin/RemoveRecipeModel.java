/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.restmodel.model.admin;

import com.vn.dailycookapp.cache.user.UserCache;
import com.vn.dailycookapp.entity.request.RegisterInfo;
import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.notification.NotificationActionImp;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.restmodel.InvalidParamException;
import com.vn.dailycookapp.utils.ErrorCodeConstant;
import java.util.List;
import org.dao.CommentDAO;
import org.dao.FavoriteDAO;
import org.dao.FavoritedDAO;
import org.dao.NotificationDAO;
import org.dao.RecipeDAO;
import org.dao.UserDAO;
import org.entity.Favorited;
import org.entity.Notification;
import org.entity.Recipe;
import org.entity.User;
import org.json.JsonTransformer;

/**
 *
 * @author duyetpt
 */
public class RemoveRecipeModel extends AbstractModel{
    
    private RegisterInfo adminAcc;;
    @Override
    protected void preExecute(String... data) throws InvalidParamException {
        recipeId = data[0];
        adminAcc = JsonTransformer.getInstance().unmarshall(data[1], RegisterInfo.class);
    }

    @Override
    protected DCAResponse execute() throws Exception {
        DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
        // authen admin
        AdminAuth.auth(adminAcc);
        
        // Remove relation of this recipeId
        // all notification
        List<Notification> notis = NotificationDAO.getInstance().allNotificationOfRecipe(recipeId);
        for (Notification noti : notis) {
            UserDAO.getInstance().decreaseNotificationNumber(noti.getTo());
            UserCache.getInstance().get(noti.getTo()).decreaseNumberFollowing();
        }
        NotificationDAO.getInstance().deleteNotificatonOfRecipe(recipeId);
        // all favorite
        Favorited favorited = FavoritedDAO.getInstance().get(recipeId, Favorited.class);
        if (favorited != null) {
            for (String userId : favorited.getUserIds()) {
                FavoriteDAO.getInstance().pull(userId, recipeId);
            }
        }
        FavoritedDAO.getInstance().delete(recipeId, Favorited.class);
        
        // all comment
        CommentDAO.getInstance().removeAllCommentOfRecipe(recipeId);
        RecipeDAO.getInstance().updateRecipeStatus(recipeId, Recipe.REMOVED_FLAG);
        
        Recipe recipe = RecipeDAO.getInstance().getRecipe(recipeId);
        User user = UserDAO.getInstance().get(recipe.getOwner(), User.class);
        // decrease recipe number
        UserDAO.getInstance().decreaseRecipeNumber(user.getId());
        
        // NOTI TO USER
        if (user.getActiveFlag() != User.DELETED_FLAG) {
            NotificationActionImp.getInstance().addNotification(recipeId, recipe.getTitle(), "Dailycook", recipe.getOwner(), Notification.REMOVE_RECIPE_TYPE);
        }
        
        return response;
    }
    
}
