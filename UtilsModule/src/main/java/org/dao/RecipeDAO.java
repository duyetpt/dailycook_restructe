package org.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.TimeUtils;
import org.bson.types.ObjectId;
import org.entity.Recipe;
import org.mongodb.morphia.query.Query;

import com.mongodb.DBRef;

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
	 * Get recipe for new feed api
	 * 
	 * @param skip
	 * @param take
	 * @param sort
	 * @param followingIds
	 *            : userIds of following user
	 * @return
	 * @throws DAOException
	 */
	public List<Recipe> getRecipes(int skip, int take, String sort,
			List<String> followingIds) throws DAOException {
		try {
			Query<Recipe> query = datastore.createQuery(Recipe.class)
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
				query.field("created_time")
						.greaterThanOrEq(
								TimeUtils.getCurrentGMTTime()
										- TimeUtils.A_MONTH_MILI)
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
					.field("_id").in(listObjId).offset(skip).limit(take)
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
					.field("_id").in(listObjId);

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
			List<DBRef> dbRefs = new ArrayList<DBRef>();
			for (String ingId : ingredientIds) {
				DBRef dbR = new DBRef("Ingredient", new ObjectId(ingId));
				dbRefs.add(dbR);
			}
			Query<Recipe> query = datastore.createQuery(Recipe.class);
			query.field("ingredients").hasAnyOf(dbRefs);

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
			List<DBRef> dbRefs = new ArrayList<DBRef>();
			for (String ingId : tagIds) {
				DBRef dbR = new DBRef("Tag", new ObjectId(ingId));
				dbRefs.add(dbR);
			}
			Query<Recipe> query = datastore.createQuery(Recipe.class);
			query.field("ingredients").hasAnyOf(dbRefs);

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
	public List<Recipe> getRecipeForSuggestion(String name) throws DAOException {
		try {
			Query<Recipe> query = datastore.createQuery(Recipe.class);
			query.field("normalize_title").contains(name).order("-view")
					.order("-popularPoint").limit(10);

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
			Query<Recipe> query = datastore.createQuery(Recipe.class);
			query.field("normalize_title").contains(name);

			return query.asList();
		} catch (Exception ex) {
			throw new DAOException();
		}
	}
}
