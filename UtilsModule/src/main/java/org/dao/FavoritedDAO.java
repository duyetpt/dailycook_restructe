package org.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.entity.Favorited;
import org.mongodb.morphia.query.Query;

/**
 * 
 * @author duyetpt
 *         recipe is favorited by
 */
public class FavoritedDAO extends AbstractDAO<Favorited> {
	
	private static final FavoritedDAO	instance	= new FavoritedDAO();
	
	private FavoritedDAO() {
		
	}
	
	public static FavoritedDAO getInstance() {
		return instance;
	}
	
	public void push(String recipeId, String userId) throws DAOException {
		Query<Favorited> query = datastore.createQuery(Favorited.class).field("_id").equal(new ObjectId(recipeId));
		
		if (query.get() == null) {
			Favorited fav = new Favorited();
			fav.setId(new ObjectId(recipeId));
			
			List<String> userIds = new ArrayList<>();
			userIds.add(userId);
			fav.setUserIds(userIds);
			
			save(fav);
		} else {
			pushToArray(recipeId, "user_ids", recipeId, Favorited.class);
		}
	}
	
	public void pull(String recipeId, String userId) throws DAOException {
		pullToArray(recipeId, "user_ids", recipeId, Favorited.class);
	}
}
