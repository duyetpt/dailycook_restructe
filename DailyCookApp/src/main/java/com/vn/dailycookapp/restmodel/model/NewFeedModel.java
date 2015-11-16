package com.vn.dailycookapp.restmodel.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.dao.FavoriteDAO;
import org.dao.FollowingDAO;
import org.dao.RecipeDAO;
import org.entity.Favorite;
import org.entity.Following;
import org.entity.Recipe;

import com.vn.dailycookapp.cache.user.CompactUserInfo;
import com.vn.dailycookapp.cache.user.UserCache;
import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.entity.response.NewFeedResponseData;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.restmodel.InvalidParamException;
import com.vn.dailycookapp.utils.ErrorCodeConstant;

/**
 *
 * @author duyetpt Get User info Search recipes Get owner of recipes do user
 * favorite recipes; Response data
 */
public class NewFeedModel extends AbstractModel {

    public static final String SORT_BY_NEWEST = "new";
    public static final String SORT_BY_HOTEST = "hot";
    public static final String SORT_BY_FOLLOWING = "following";

    private int skip;
    private int take;
    private String sort;

    @Override
    protected void preExecute(String... data) throws InvalidParamException {
        myId = data[0];
        skip = Integer.parseInt(data[1]);
        take = Integer.parseInt(data[2]);
        sort = data[3];
    }

    @Override
    protected DCAResponse execute() throws Exception {
        DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());

        List<NewFeedResponseData> datas = new ArrayList<NewFeedResponseData>();

        List<String> followingIds = null;
        if (SORT_BY_FOLLOWING.equals(sort) && myId != null) {
            Following following = FollowingDAO.getInstance().get(myId, Following.class);
            followingIds = following.getStarIds();
        }
        // Get recipes
        List<Recipe> recipes = RecipeDAO.getInstance().getRecipes(skip, take, sort, followingIds);
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
