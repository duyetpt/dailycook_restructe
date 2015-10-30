package org.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.entity.Favorite;
import org.entity.Favorited;
import org.mongodb.morphia.query.Query;

import com.mongodb.BasicDBObject;

/**
 * 
 * @author duyetpt
 *         recipe is favorited by
 */
public class FavoriteDAO extends AbstractDAO<Favorite> {
	
	private static final FavoriteDAO	instance	= new FavoriteDAO();
	
	private FavoriteDAO() {
		
	}
	
	public static FavoriteDAO getInstance() {
		return instance;
	}
	
	public void push(String userId, String recipeId) throws DAOException {
		Query<Favorite> query = datastore.createQuery(Favorite.class).field("_id").equal(new ObjectId(userId));
		if (query.countAll() == 0) {
			Favorite fav = new Favorite();
			fav.setId(new ObjectId(userId));
			
			List<String> recipeIds = new ArrayList<>();
			recipeIds.add(recipeId);
			fav.setRecipeIds(recipeIds);
			
			save(fav);
		} else {
			pushToArray(userId, "recipe_ids", recipeId, Favorite.class);
		}
	}
	
	public void pull(String userId, String recipeId) throws DAOException {
		pullToArray(userId, "recipe_ids", recipeId, Favorite.class);
	}
	
	public boolean isFavorited(String userId, String recipeId) throws DAOException {
		try {
			Query<Favorited> query = datastore.createQuery(Favorited.class).field("_id").equal(new ObjectId(userId));
			query.field("user_ids").hasThisElement(new BasicDBObject("$eq", recipeId));
			
			Favorited fav = query.retrievedFields(false, "user_ids").get();
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
