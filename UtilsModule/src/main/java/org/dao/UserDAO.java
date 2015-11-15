package org.dao;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.DCAUtilsException;
import org.bson.types.ObjectId;
import org.entity.User;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

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

    public boolean increateNotificationNumber(String userId) throws DAOException {
        return increaseForField(userId, 1, User.class, "n_notification");
    }

    public boolean decreaseNotificationNumber(String userId) throws DAOException {
        // return updateRecipeNumber(userId, -1);
        return increaseForField(userId, -1, User.class, "n_notification");
    }

    // TOD0 - MODIFIED
    public boolean increateReportNumber(String userId) {
        return updateReportNumber(userId, 1);
    }

    public boolean decreaseReportNumber(String userId) {
        return updateReportNumber(userId, -1);
    }

    // TODO - REMOVED - DUPLICATE
    private boolean updateReportNumber(String userId, int number) {
        try {
            Query<User> query = datastore.createQuery(User.class).field("_id").equal(new ObjectId(userId));
            UpdateOperations<User> updateO = datastore.createUpdateOperations(User.class).inc("n_reports", number);
            UpdateResults result = datastore.update(query, updateO);
            return result.getUpdatedCount() == 1;
        } catch (Exception ex) {
        }
        return false;
    }

    //reset passWord

    public boolean updateAdminPassWord(String userId, String pass) {
        try {
            Query<User> query = datastore.createQuery(User.class).field("_id").equal(new ObjectId(userId));
            UpdateOperations<User> updateO = datastore.createUpdateOperations(User.class).set("pass", pass);
            UpdateResults result = datastore.update(query, updateO);
            return result.getUpdatedCount() == 1;
        } catch (Exception ex) {
        }
        return false;
    }

    public boolean banUser(String userId, int flag) {
        try {
            Query<User> query = datastore.createQuery(User.class).field("_id").equal(new ObjectId(userId));
            UpdateOperations<User> updateO = datastore.createUpdateOperations(User.class).set("active_flag", flag);
            UpdateResults result = datastore.update(query, updateO);
            return result.getUpdatedCount() == 1;
        } catch (Exception ex) {
        }
        return false;
    }

    public boolean unBanUser(String userId) {
        try {
            Query<User> query = datastore.createQuery(User.class).field("_id").equal(new ObjectId(userId));
            UpdateOperations<User> updateO = datastore.createUpdateOperations(User.class).set("active_flag", User.ACTIVE_FLAG);
            UpdateResults result = datastore.update(query, updateO);
            return result.getUpdatedCount() == 1;
        } catch (Exception ex) {
        }
        return false;
    }

    public List<User> getAllUser() {
        try {
            Query<User> query = datastore.createQuery(User.class);
            return query.asList();
        } catch (Exception ex) {
        }
        return null;
    }

    // count number user registed in period
    public long getNumberRegisteredUser(long from, long to) {
        Query<User> query = datastore.createQuery(User.class);
        query.and(query.criteria("registered_time").greaterThanOrEq(from).and(query.criteria("registered_time").lessThanOrEq(to)));
        return query.countAll();
    }

    public long getNumberUser() {
        Query<User> query = datastore.createQuery(User.class);
        return query.countAll();
    }

    public void updateLanguage(String userId, String language) throws DAOException {
        try {
            Query<User> query = datastore.createQuery(User.class).field("_id").equal(new ObjectId(userId));
            UpdateOperations<User> updateO = datastore.createUpdateOperations(User.class).set("language", language);
            datastore.update(query, updateO);
        } catch (Exception ex) {
            logger.error("update language error", ex);
            throw new DAOException();
        }
    }

    public boolean updatePassword(String userId, String oldPassword, String password) throws DAOException {
        try {
            Query<User> query = datastore.createQuery(User.class).field("_id").equal(new ObjectId(userId)).filter("pass", oldPassword);
            UpdateOperations<User> updateO = datastore.createUpdateOperations(User.class).set("pass", password);
            UpdateResults result = datastore.update(query, updateO);
            return result.getUpdatedCount() == 1;
        } catch (Exception ex) {
            logger.error("update password error", ex);
            throw new DAOException();
        }
    }
}
