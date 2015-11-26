package com.vn.dailycookapp.restmodel.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.Unicode;
import org.dao.DAOException;
import org.dao.FavoriteDAO;
import org.dao.IngredientDAO;
import org.dao.RecipeDAO;
import org.dao.TagDAO;
import org.entity.Favorite;
import org.entity.Ingredient;
import org.entity.Recipe;
import org.entity.Tag;

import com.vn.dailycookapp.cache.user.CompactUserInfo;
import com.vn.dailycookapp.cache.user.UserCache;
import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.entity.response.SearchRecipeResponseData;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.restmodel.InvalidParamException;
import com.vn.dailycookapp.utils.ErrorCodeConstant;

public class SearchRecipeModel extends AbstractModel {

    private static final String INGREDIENT_TYPE = "ingredients";
    private static final String TAG_TYPE = "tags";
    private static final String NAME_TYPE = "name";

    private String filter;
    private String keyword;
    private int skip;
    private int take;

    @Override
    protected void preExecute(String... data) throws InvalidParamException {
        filter = data[0] == null ? NAME_TYPE : data[0];
        keyword = Unicode.toAscii(data[1]).toLowerCase();
        myId = data[2];
        skip = Integer.parseInt(data[3]);
        take = Integer.parseInt(data[4]);
    }

    // TODO
    @Override
    protected DCAResponse execute() throws Exception {
        DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
        //
        Map<String, Integer> nIngredientMatchMap = null;
        List<Recipe> recipes = null;
        switch (filter) {
            case INGREDIENT_TYPE:
                // parse keyword
                List<String> ingredients = parseKeyword();
                // get list Ingredient by keyword
                List<Ingredient> listIngredient = IngredientDAO.getInstance().list(ingredients);
                if (listIngredient != null && !listIngredient.isEmpty()) {
                    List<String> ingredientIds = new ArrayList<String>();
                    for (Ingredient in : listIngredient) {
                        ingredientIds.add(in.getId());
                        System.out.println(in.getId());
                    }
                    // get all recipe match ingredient list
                    recipes = RecipeDAO.getInstance().listRecipeByIngredient(ingredientIds);
                    nIngredientMatchMap = new HashMap<String, Integer>();
                    for (Recipe recipe : recipes) {
                        int count = 0;
                        for (Ingredient ing : recipe.getIngredients()) {
                            if (ingredientIds.contains(ing.getId())) {
                                count++;
                            }
                        }
                        nIngredientMatchMap.put(recipe.getId(), count);
                    }
                }

                break;
            case TAG_TYPE:
                List<String> tags = parseKeyword();
                List<Tag> list = TagDAO.getInstance().list(tags);
                if (list != null && !list.isEmpty()) {
                    List<String> tagIds = new ArrayList<String>();
                    for (Tag tag : list) {
                        tagIds.add(tag.getId().toHexString());
                    }
                    recipes = RecipeDAO.getInstance().listRecipeByTag(tagIds);
                }
                break;
            case NAME_TYPE:
                recipes = RecipeDAO.getInstance().searchRecipeByName(keyword);
                break;
        }

        List<SearchRecipeResponseData> result = getResult(recipes, nIngredientMatchMap);

        int resultLength = result.size();
        if (resultLength <= skip) {
            skip = 0;
            take = 0;
        } else {
            take = resultLength - skip < take ? resultLength : take + skip;
        }
        response.setData(result.subList(skip, take));
        return response;
    }

    private List<String> parseKeyword() {
        List<String> result = new ArrayList<String>();
        String[] keys = keyword.split(",");

        for (String key : keys) {
            result.add(key);
        }

        return result;
    }

    private List<SearchRecipeResponseData> getResult(List<Recipe> recipes, Map<String, Integer> nPercentMap)
            throws DAOException {
        List<SearchRecipeResponseData> result = new ArrayList<SearchRecipeResponseData>();
        // check recipes
        if (recipes == null || recipes.isEmpty()) {
            return result;
        }

        Favorite favorite = null;
        if (myId != null) {
            favorite = FavoriteDAO.getInstance().get(myId, Favorite.class);
        }

        for (Recipe recipe : recipes) {
            SearchRecipeResponseData resData = new SearchRecipeResponseData();
            CompactUserInfo user = UserCache.getInstance().get(recipe.getOwner());
            resData.setUsername(user.getDisplayName());
            resData.setCreateTime(recipe.getCreatedTime());
            resData.setNFavorite(recipe.getFavoriteNumber());
            resData.setRecipeId(recipe.getId());
            resData.setTitlel(recipe.getTitle());
            resData.setRecipePicture(recipe.getPictureUrl());
            resData.setRecipeStory(recipe.getStory());
            resData.setFavorite(favorite == null ? false : favorite.getRecipeIds().contains(recipe.getId()));
            if (nPercentMap != null) {
                resData.setPercentMatch(100 * nPercentMap.get(recipe.getId()) / recipe.getIngredients().size());
            }

            result.add(resData);
        }

        Collections.sort(result);
        return result;
    }
    
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        
        List<String> result = list.subList(2, 7);
        System.out.println(result);
    }
}
