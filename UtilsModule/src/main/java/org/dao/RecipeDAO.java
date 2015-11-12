package org.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.TimeUtils;
import org.bson.types.ObjectId;
import org.entity.Recipe;
import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.Query;

import com.mongodb.DBRef;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

public class RecipeDAO extends AbstractDAO<Recipe> {

    public static final String SORT_BY_NEWEST = "new";
    public static final String SORT_BY_HOTEST = "hot";
    public static final String SORT_BY_FOLLOWING = "following";

    private static final RecipeDAO instance = new RecipeDAO();

    private RecipeDAO() {
        datastore.ensureIndexes(Recipe.class);
    }
    

    public static RecipeDAO getInstance() {
        return instance;
    }

    public boolean increateFavoriteNumber(String recipeId) throws DAOException {
        return increaseForField(recipeId, 1, Recipe.class, "favorite_number");
    }

    public boolean decreateFavoriteNumber(String recipeId) throws DAOException {
        return increaseForField(recipeId, -1, Recipe.class, "favorite_number");
    }

    public boolean increateCommentNumber(String recipeId) throws DAOException {
        return increaseForField(recipeId, 1, Recipe.class, "comment_number");
    }

    public boolean increateView(String recipeId) throws DAOException {
        return increaseForField(recipeId, 1, Recipe.class, "view");
    }

    public Recipe get(String recipeId) throws DAOException {
        return get(recipeId, Recipe.class);
    }

    /**
     *
     * @param skip
     * @param take
     * @param sort
     * @param followingIds : userIds of following user
     * @return
     * @throws DAOException
     */
    public List<Recipe> getRecipes(int skip, int take, String sort,
            List<String> followingIds) throws DAOException {
        try {
            Query<Recipe> query = datastore.createQuery(Recipe.class).filter("status_flag", Recipe.APPROVED_FLAG)
                    .offset(skip).limit(take);
            switch (sort) {
                case SORT_BY_FOLLOWING:
                    if (followingIds != null) {
                        query.field("owner").in(followingIds).order("-create_time");
                    } else {
                        return new ArrayList<>();
                    }
                    break;
                case SORT_BY_HOTEST:
                    long time = TimeUtils.getCurrentGMTTime() - TimeUtils.A_MONTH_MILI;
                    query.field("created_time")
                            .greaterThanOrEq(time)
                            .order("-favorite_number");
                    break;
                case SORT_BY_NEWEST:
                    query.order("-created_time");
                    break;
            }

            return query.asList();
        } catch (Exception ex) {
            logger.error("get recipes error", ex);
            throw new DAOException();
        }
    }

    /**
     * List favorite recipe, sorted by create time
     *
     * @param skip
     * @param take
     * @return
     * @throws DAOException
     */
    public List<Recipe> listFavoriteRecipe(List<String> recipeIds, int skip,
            int take) throws DAOException {
        try {
            List<ObjectId> listObjId = new ArrayList<ObjectId>();
            for (String recipeId : recipeIds) {
                listObjId.add(new ObjectId(recipeId));
            }
            Query<Recipe> query = datastore.createQuery(Recipe.class)
                    .field("_id").in(listObjId).offset(skip).limit(take).filter("status_flag", Recipe.APPROVED_FLAG)
                    .order("-created_time");
            return query.asList();
        } catch (Exception ex) {
            logger.error("get recipes error", ex);
            throw new DAOException();
        }
    }

    public List<Recipe> getRecipes(Set<String> recipeIds) throws DAOException {
        try {
            List<ObjectId> listObjId = new ArrayList<ObjectId>();
            for (String recipeId : recipeIds) {
                listObjId.add(new ObjectId(recipeId));
            }

            Query<Recipe> query = datastore.createQuery(Recipe.class)
                    .field("_id").in(listObjId).filter("status_flag", Recipe.APPROVED_FLAG);

            return query.asList();

        } catch (Exception ex) {
            logger.error("search recipes exception", ex);
            throw new DAOException();
        }

    }

    /**
     * list all recipe match one of IngredientIds
     *
     * @param ingredientIds
     * @return
     * @throws DAOException
     */
    public List<Recipe> listRecipeByIngredient(List<String> ingredientIds)
            throws DAOException {
        try {
            Query<Recipe> query = datastore.createQuery(Recipe.class).filter("status_flag", Recipe.APPROVED_FLAG);
            Criteria[] criterias = new Criteria[ingredientIds.size()];

            for (int i = 0; i < ingredientIds.size(); i++) {
                String ingId = ingredientIds.get(i);
                DBRef dbR = new DBRef("Ingredient", new ObjectId(ingId));
                criterias[i] = query.criteria("ingredients")
                        .hasThisElement(dbR);
            }

            query.or(criterias);

            return query.asList();
        } catch (Exception ex) {
            throw new DAOException();
        }
    }

    /**
     * list all recipe match one of tags
     *
     * @param tagIds
     * @return
     * @throws DAOException
     */
    public List<Recipe> listRecipeByTag(List<String> tagIds)
            throws DAOException {
        try {
            Query<Recipe> query = datastore.createQuery(Recipe.class).filter("status_flag", Recipe.APPROVED_FLAG);
            Criteria[] criterias = new Criteria[tagIds.size()];

            for (int i = 0; i < tagIds.size(); i++) {
                String tag = tagIds.get(i);
                DBRef dbR = new DBRef("Tag", new ObjectId(tag));
                criterias[i] = query.criteria("ingredients")
                        .hasThisElement(dbR);
            }

            query.or(criterias);

            return query.asList();
        } catch (Exception ex) {
            throw new DAOException();
        }
    }

    /**
     * get list recipe by title
     *
     * @param name
     * @return
     * @throws DAOException
     */
    public List<Recipe> getRecipeForSuggestion(String name, int skip, int take) throws DAOException {
        try {
            Query<Recipe> query = datastore.createQuery(Recipe.class).filter("status_flag", Recipe.APPROVED_FLAG);
            query.field("normalize_title").contains(name).order("-view")
                    .order("-popularPoint").limit(take).offset(skip);

            return query.asList();
        } catch (Exception ex) {
            throw new DAOException();
        }
    }

    /**
     * get list recipe by title
     *
     * @param name
     * @return
     * @throws DAOException
     */
    public List<Recipe> searchRecipeByName(String name) throws DAOException {
        try {
            Query<Recipe> query = datastore.createQuery(Recipe.class).filter("status_flag", Recipe.APPROVED_FLAG);
            query.field("normalize_title").contains(name);

            return query.asList();
        } catch (Exception ex) {
            throw new DAOException();
        }
    }

    public List<Recipe> getAllRecipe() {
        try {
            Query<Recipe> query = datastore.createQuery(Recipe.class);
            return query.asList();
        } catch (Exception ex) {
            logger.error("getAllRecipe fail", ex);
        }

        return null;
    }

    public boolean updateRecipeStatus(String recipeId, int flag, long time) {
        try {
            Query<Recipe> query = datastore.createQuery(Recipe.class).field("_id").equal(new ObjectId(recipeId));
            UpdateOperations<Recipe> updateO = datastore.createUpdateOperations(Recipe.class).set("status_flag", flag).set("deleted_time", time);
            UpdateResults result = datastore.update(query, updateO);
            return result.getUpdatedCount() == 1;
        } catch (Exception ex) {
            logger.error("update recipe status fail", ex);
        }
        return false;
    }
    
    public Recipe getRecipe(String recipeId) {
        try {
            Query<Recipe> query = datastore.createQuery(Recipe.class).field("_id").equal(new ObjectId(recipeId));
            return query.get();
        } catch (Exception ex) {

        }

        return null;
    }
    
    // count number recipe create in period
    public long getNumberCreatedRecipe(long from, long to) {

        Query<Recipe> query = datastore.createQuery(Recipe.class);
        query.and(query.criteria("created_time").greaterThanOrEq(from).and(query.criteria("created_time").lessThanOrEq(to)));
        return query.countAll();
    }
    
    public long getNumberDeletedRecipe(long from, long to) {

        Query<Recipe> query = datastore.createQuery(Recipe.class);
        query.and(query.criteria("deleted_time").greaterThanOrEq(from).and(query.criteria("deleted_time").lessThanOrEq(to)));
        return query.countAll();
    }
}
