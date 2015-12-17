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
import java.util.Iterator;
import org.Unicode;
import org.mongodb.morphia.aggregation.AggregationPipeline;
import org.mongodb.morphia.aggregation.Sort;
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
            Query<Recipe> query = datastore.createQuery(Recipe.class).filter("status_flag !=", Recipe.REMOVED_FLAG);
            switch (sort) {
                case SORT_BY_FOLLOWING:
                    if (followingIds != null) {
                        query.field("owner").in(followingIds).order("-created_time").offset(skip).limit(take);;
                    } else {
                        return new ArrayList<>();
                    }
                    break;
                case SORT_BY_HOTEST:
                    long time = TimeUtils.getCurrentGMTTime() - TimeUtils.A_MONTH_MILI;
                    query.field("created_time")
                            .greaterThanOrEq(time)
                            .order("-favorite_number").order("-created_time").offset(skip).limit(take);
                    break;
                case SORT_BY_NEWEST:
                    query.order("-created_time").offset(skip).limit(take);
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
                    .field("_id").in(listObjId).offset(skip).limit(take).filter("status_flag !=", Recipe.REMOVED_FLAG)
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
                    .field("_id").in(listObjId).filter("status_flag !=", Recipe.REMOVED_FLAG);

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
            Query<Recipe> query = datastore.createQuery(Recipe.class).filter("status_flag !=", Recipe.REMOVED_FLAG);
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
            Query<Recipe> query = datastore.createQuery(Recipe.class).filter("status_flag !=", Recipe.REPORTED_FLAG);
            Criteria[] criterias = new Criteria[tagIds.size()];

            for (int i = 0; i < tagIds.size(); i++) {
                String tag = tagIds.get(i);
                DBRef dbR = new DBRef("Tag", new ObjectId(tag));
                criterias[i] = query.criteria("tags")
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
            Query<Recipe> query = datastore.createQuery(Recipe.class).filter("status_flag !=", Recipe.REMOVED_FLAG);
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
            Query<Recipe> query = datastore.createQuery(Recipe.class).filter("status_flag !=", Recipe.REMOVED_FLAG);
            query.field("normalize_title").contains(Unicode.toAscii(name));

            return query.asList();
        } catch (Exception ex) {
            throw new DAOException();
        }
    }

    public List<Recipe> searchRecipeByName(String name, int skip, int take) throws DAOException {
        try {
            Query<Recipe> query = datastore.createQuery(Recipe.class).filter("status_flag !=", Recipe.REMOVED_FLAG);
            query.field("normalize_title").contains(Unicode.toAscii(name)).offset(skip).limit(take);
            query.retrievedFields(true, "picture_url", "status_flag", "owner", "title", "created_time" ,"favorite_number");
            
            return query.asList();
        } catch (Exception ex) {
            throw new DAOException();
        }
    }
    public List<Recipe> searchAllAndFilterRecipeByName(String name, int skip, int take,int flag,String Oder) throws DAOException {
        try {
            Query<Recipe> query = datastore.createQuery(Recipe.class).filter("status_flag", flag);
            query.field("normalize_title").containsIgnoreCase(Unicode.toAscii(name)).offset(skip).limit(take);
            query.retrievedFields(true, "picture_url", "status_flag", "owner", "title", "created_time","favorite_number").order(Oder);
            return query.asList();
        } catch (Exception ex) {
            throw new DAOException();
        }
    }
    public List<Recipe> searchAllRecipeByName(String name, int skip, int take, String order) throws DAOException {
        try {
            Query<Recipe> query = datastore.createQuery(Recipe.class);
            query.field("normalize_title").containsIgnoreCase(Unicode.toAscii(name)).offset(skip).limit(take);
            query.retrievedFields(true, "picture_url", "status_flag", "owner", "title", "created_time","favorite_number").order(order);
            
            return query.asList();
        } catch (Exception ex) {
            throw new DAOException();
        }
    }
    public List<Recipe> searchAllAndFilterRecipeByOwner(String name,String owner, int skip, int take,int flag,String Oder) throws DAOException {
        try {
            Query<Recipe> query = datastore.createQuery(Recipe.class).filter("status_flag", flag);
            query.filter("owner", owner);
            query.field("normalize_title").containsIgnoreCase(Unicode.toAscii(name)).offset(skip).limit(take);
            query.retrievedFields(true, "picture_url", "status_flag", "owner", "title", "created_time","favorite_number").order(Oder);
            return query.asList();
        } catch (Exception ex) {
            throw new DAOException();
        }
    }
    public List<Recipe> searchAllRecipeByOwner(String name, String owner, int skip, int take, String order) throws DAOException {
        try {
            Query<Recipe> query = datastore.createQuery(Recipe.class).filter("owner", owner);
            query.field("normalize_title").containsIgnoreCase(Unicode.toAscii(name)).offset(skip).limit(take);
            query.retrievedFields(true, "picture_url", "status_flag", "owner", "title", "created_time","favorite_number").order(order);
            
            return query.asList();
        } catch (Exception ex) {
            throw new DAOException();
        }
    }
    
    /**
     * count number recipe match name
     * @param name
     * @param skip
     * @param take
     * @return
     * @throws DAOException 
     */
    public long getSearchResultNumber(String name) throws DAOException {
        try {
            Query<Recipe> query = datastore.createQuery(Recipe.class).filter("status_flag !=", Recipe.REMOVED_FLAG);
            query.field("normalize_title").contains(Unicode.toAscii(name));
            return query.countAll();
        } catch (Exception ex) {
            throw new DAOException();
        }
    }
    public long getSearchAndFilterResultNumber(String name,int flag_active) throws DAOException {
        try {
            Query<Recipe> query = datastore.createQuery(Recipe.class).filter("status_flag", flag_active);
            query.field("normalize_title").contains(Unicode.toAscii(name));
            return query.countAll();
        } catch (Exception ex) {
            throw new DAOException();
        }
    }
    public long getSearchAllResultNumber(String name) throws DAOException {
        try {
            Query<Recipe> query = datastore.createQuery(Recipe.class);
            query.field("normalize_title").contains(Unicode.toAscii(name));
            return query.countAll();
        } catch (Exception ex) {
            throw new DAOException();
        }
    }
    public long getSearchByOwnerAndFilterResultNumber(String name,int flag_active, String owner) throws DAOException {
        try {
            Query<Recipe> query = datastore.createQuery(Recipe.class).filter("status_flag", flag_active);
            query.field("normalize_title").contains(Unicode.toAscii(name)).filter("owner", owner);
            return query.countAll();
        } catch (Exception ex) {
            throw new DAOException();
        }
    }
    public long getSearchByOwnerAllResultNumber(String name, String owner) throws DAOException {
        try {
            Query<Recipe> query = datastore.createQuery(Recipe.class).filter("owner", owner);
            query.field("normalize_title").contains(Unicode.toAscii(name));
            return query.countAll();
        } catch (Exception ex) {
            throw new DAOException();
        }
    }
    public long getNumberRecipeByOwner(String owner) throws DAOException {
        try {
            Query<Recipe> query = datastore.createQuery(Recipe.class).filter("owner", owner);
            return query.countAll();
        } catch (Exception ex) {
            throw new DAOException();
        }
    }
    
    public List<Recipe> getAllRecipe() {
        try {
            Query<Recipe> query = datastore.createQuery(Recipe.class);
            query.retrievedFields(true, "picture_url", "status_flag", "owner", "title", "created_time");
            
            return query.asList();
        } catch (Exception ex) {
            logger.error("getAllRecipe fail", ex);
        }

        return null;
    }

    public List<Recipe> getRecipes(int skip, int take) {
        try {
            Query<Recipe> query = datastore.createQuery(Recipe.class).offset(skip).limit(take);
            query.retrievedFields(true, "picture_url", "status_flag", "owner", "title", "created_time","favorite_number");
            return query.asList();
        } catch (Exception ex) {
            logger.error("getAllRecipe fail", ex);
        }

        return null;
    }
    
    // for webservice when user report recipe
    public boolean updateRecipeStatus(String recipeId, int flag) {
        try {
            Query<Recipe> query = datastore.createQuery(Recipe.class).field("_id").equal(new ObjectId(recipeId));
            UpdateOperations<Recipe> updateO = datastore.createUpdateOperations(Recipe.class).set("status_flag", flag);
            UpdateResults result = datastore.update(query, updateO);
            return result.getUpdatedCount() == 1;
        } catch (Exception ex) {
            logger.error("update recipe status fail", ex);
        }
        return false;
    }

    // for webapp admin when admin verify report
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

    // TOD0 - removed - duplicate
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

    public long getNumberRecipe() {
        Query<Recipe> query = datastore.createQuery(Recipe.class);
        return query.countAll();
    }
    
    // get recipe of user
    public List<Recipe> getRecipeOfUser(String userId, int skip, int take) throws DAOException {
        try {
            Query<Recipe> query = datastore.createQuery(Recipe.class).filter("status_flag !=", Recipe.REMOVED_FLAG).filter("owner", userId).limit(take).offset(skip).order("-created_time");
            
            return query.asList();
        } catch (Exception ex) {
            logger.error("get recipe of user error:" + userId, ex);
            throw new DAOException();
        }
    }
    
    // count number recipe with flag
    public long getNumberRecipeWithFlag(int flag) {
        Query<Recipe> query = datastore.createQuery(Recipe.class);
        query.and(query.criteria("status_flag").equal(flag));
        return query.countAll();
    }
    
    //get all recipes of user
    public List<Recipe> getRecipeOfUser(String userId) throws DAOException {
        try {
            Query<Recipe> query = datastore.createQuery(Recipe.class);
            query.and(query.criteria("owner").equal(userId));
            return query.asList();
        } catch (Exception ex) {
            logger.error("get recipe of user error:" + userId, ex);
            throw new DAOException();
        }
    }
    
    public Iterator<Recipe> getTop(int top) throws DAOException {
        try {
            AggregationPipeline aggregation = datastore.createAggregation(Recipe.class);
            Iterator<Recipe> list = aggregation.sort(new Sort("favorite_number", -1)).limit(top).aggregate(Recipe.class);
            return list;
        } catch (Exception ex) {
            throw new DAOException();
        }
    }
    
}
