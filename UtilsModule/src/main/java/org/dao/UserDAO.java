package org.dao;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.DCAUtilsException;
import org.bson.types.ObjectId;
import org.entity.User;
import org.mongodb.morphia.query.Query;

public class UserDAO extends AbstractDAO<User> {

	private static final UserDAO instance = new UserDAO();

	private UserDAO() {
		datastore.ensureIndexes(User.class);
	}

	public static final UserDAO getInstance() {
		return instance;
	}

	public void saveWithSynchronized(User user) throws DCAUtilsException {
		try {
			synchronized (user) {
				save(user);
			}
		} catch (Exception ex) {
			throw new AccountExistedException();
		}

	}

	public User getUserInfoByEmail(String email) throws DAOException {
		try {
			Query<User> query = datastore.createQuery(User.class)
					.field("email").equal(email);
			User user = query.get();

			return user;
		} catch (Exception ex) {
			throw new DAOException();
		}
	}

	/**
	 * Get Users by display name or email
	 * 
	 * @param username
	 * @return
	 * @throws DAOException
	 */
	public List<User> listUserByName(String username) throws DAOException {
		try {
			Query<User> query = datastore.createQuery(User.class);

			query.or(query.criteria("email").containsIgnoreCase(username),
					query.criteria("display_name").containsIgnoreCase(username));

			return query.asList();
		} catch (Exception ex) {
			throw new DAOException();
		}
	}

	/**
	 * count number user match with username
	 * 
	 * @param username
	 * @return
	 * @throws DAOException
	 */
	public int countUserByName(String username) throws DAOException {
		try {
			Query<User> query = datastore.createQuery(User.class);

			query.or(query.criteria("email").containsIgnoreCase(username),
					query.criteria("display_name").containsIgnoreCase(username));

			return (int) query.countAll();
		} catch (Exception ex) {
			throw new DAOException();
		}
	}

	public User getUser(String userId) throws DAOException {
		try {
			Query<User> query = datastore.createQuery(User.class).field("_id")
					.equal(new ObjectId(userId));
			User user = query.get();

			return user;
		} catch (Exception ex) {
			throw new DAOException();
		}
	}

	public List<User> list(Collection<String> userIds) throws DAOException {
		Set<ObjectId> objIds = new TreeSet<>();
		for (String userId : userIds) {
			ObjectId objId = new ObjectId(userId);
			objIds.add(objId);
		}

		Query<User> query = datastore.createQuery(User.class).field("_id")
				.in(objIds);

		return query.asList();
	}

	public boolean increateRecipeNumber(String userId) throws DAOException {
		return increaseForField(userId, 1, User.class, "n_recipes");
	}

	public boolean decreaseRecipeNumber(String userId) throws DAOException {
		// return updateRecipeNumber(userId, -1);
		return increaseForField(userId, -1, User.class, "n_recipes");
	}

	public boolean increateFollowingNumber(String userId) throws DAOException {
		return increaseForField(userId, 1, User.class, "n_following");
	}

	public boolean decreaseFollowingNumber(String userId) throws DAOException {
		// return updateRecipeNumber(userId, -1);
		return increaseForField(userId, -1, User.class, "n_following");
	}

	public boolean increateFollowerNumber(String userId) throws DAOException {
		return increaseForField(userId, 1, User.class, "n_follower");
	}

	public boolean decreaseFollowerNumber(String userId) throws DAOException {
		// return updateRecipeNumber(userId, -1);
		return increaseForField(userId, -1, User.class, "n_follower");
	}
}
