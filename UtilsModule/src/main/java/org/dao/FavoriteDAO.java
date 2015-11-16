package org.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.entity.Favorite;
import org.mongodb.morphia.query.Query;

/**
 * 
 * @author duyetpt recipe is favorited by
 */
public class FavoriteDAO extends AbstractDAO<Favorite> {

	private static final FavoriteDAO instance = new FavoriteDAO();

	private FavoriteDAO() {

	}

	public static FavoriteDAO getInstance() {
		return instance;
	}

	/**
	 * true if favorite success other return false
	 * 
	 * @param userId
	 * @param recipeId
	 * @return
	 * @throws DAOException
	 */
	public boolean push(String userId, String recipeId) throws DAOException {
		Query<Favorite> query = datastore.createQuery(Favorite.class)
				.field("_id").equal(new ObjectId(userId));
		if (query.get() == null) {
			Favorite fav = new Favorite();
			fav.setId(new ObjectId(userId));

			List<String> recipeIds = new ArrayList<>();
			recipeIds.add(recipeId);
			fav.setRecipeIds(recipeIds);

			save(fav);
			return true;
		} else {
			return pushToArray(userId, "recipe_ids", recipeId, Favorite.class);
		}
	}

	public boolean pull(String userId, String recipeId) throws DAOException {
		return pullToArray(userId, "recipe_ids", recipeId, Favorite.class);
	}

	public boolean isFavorite(String userId, String recipeId)
			throws DAOException {
		try {
			Query<Favorite> query = datastore.createQuery(Favorite.class)
					.field("_id").equal(new ObjectId(userId));
			query.field("recipe_ids").equal(recipeId);

			Favorite fav = query.get();
			if (fav != null) {
				return fav.getId() != null;
			}
		} catch (Exception ex) {
			logger.error("check favorited fail", ex);
			throw new DAOException();
		}

		return false;
	}
}
