package org.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.TimeUtils;
import org.bson.types.ObjectId;
import org.entity.Recipe;
import org.mongodb.morphia.query.Query;

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

	public List<Recipe> listRecipeByIngredient(String ingredients)
			throws DAOException {
		try {
			Query<Recipe> query = datastore.createQuery(Recipe.class);
			query.field("ingredients.$.normalize_name").containsIgnoreCase(
					ingredients);

			return query.asList();
		} catch (Exception ex) {
			throw new DAOException();
		}
	}
	
	public List<Recipe> listRecipeByTag(String tag)
			throws DAOException {
		try {
			Query<Recipe> query = datastore.createQuery(Recipe.class);
			query.field("tags.$").containsIgnoreCase(
					tag);

			return query.asList();
		} catch (Exception ex) {
			throw new DAOException();
		}
	}
}
