package org.dao;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.DCAUtilsException;
import org.Unicode;
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

    //ban admin
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
    
    //ban normal user
    public boolean banUser(String userId, int flag, long banToTime) {
        try {
            Query<User> query = datastore.createQuery(User.class).field("_id").equal(new ObjectId(userId));
            UpdateOperations<User> updateO = datastore.createUpdateOperations(User.class).set("active_flag", flag).inc("n_bans", 1).set("ban_to_time", banToTime);
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

    public long getNumberUserNomal() {
        Query<User> query = datastore.createQuery(User.class).filter("role", User.NORMAL_USER_ROLE);
        return query.countAll();
    }
    public long getNumberResultSearchUserNomal(String name) {
        Query<User> query = datastore.createQuery(User.class).filter("role", User.NORMAL_USER_ROLE);
        query.field("display_name").contains(Unicode.toAscii(name));
        return query.countAll();
    }
    public long getNumberResultSearchAndFillUserNomal(String name, int flag) {
        Query<User> query = datastore.createQuery(User.class).filter("role", User.NORMAL_USER_ROLE);
        query.filter("active_flag", flag);
        query.field("display_name").contains(Unicode.toAscii(name));
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

    public void updatUserProfile(String userId, String avatarUrl, String displayName, String dob) throws DAOException {
        try {
            Query<User> query = datastore.createQuery(User.class).filter("_id", new ObjectId(userId));
            UpdateOperations<User> updateOperation = datastore.createUpdateOperations(User.class);
            if (avatarUrl != null) {
                updateOperation.set("avatar_url", avatarUrl);
            }
            if (displayName != null) {
                updateOperation.set("display_name", displayName);
            }
            if (dob != null) {
                updateOperation.set("dob", dob);
            }

            datastore.update(query, updateOperation);
        } catch (Exception ex) {
            logger.error("update user profile error", ex);
            throw new DAOException();
        }
    }
    
    public void updateNotificationFlag(String userId, boolean notificatonFlag) throws DAOException {
        try {
            Query<User> query = datastore.createQuery(User.class).field("_id").equal(new ObjectId(userId));
            UpdateOperations<User> updateO = datastore.createUpdateOperations(User.class).set("notification_flag", notificatonFlag);
            datastore.update(query, updateO);
        } catch (Exception ex) {
            logger.error("update language error", ex);
            throw new DAOException();
        }
    }
    // count number user with flag
    public long getNumberUserWithFlag(int flag) {
        Query<User> query = datastore.createQuery(User.class);
        query.and(query.criteria("active_flag").equal(flag).and(query.criteria("role").equal(User.NORMAL_USER_ROLE)));
        return query.countAll();
    }


    public List<User> searchAllUserNomal(String name, int skip, int take, String order) throws DAOException {
        try {
            Query<User> query = datastore.createQuery(User.class).filter("role", User.NORMAL_USER_ROLE);
            query.field("display_name").containsIgnoreCase(Unicode.toAscii(name)).offset(skip).limit(take);
            query.retrievedFields(true,"id", "display_name", "registered_time", "email", "n_bans", "n_recipes", "active_flag").order(order);
            return query.asList();
        }
        catch (Exception ex) {
            throw new DAOException();
        }

    }
//    public List<Recipe> searchAllAndFilterRecipeByName(String name, int skip, int take,int flag,String Oder) throws DAOException {
//        try {
//            Query<Recipe> query = datastore.createQuery(Recipe.class).filter("status_flag", flag);
//            query.field("normalize_title").containsIgnoreCase(Unicode.toAscii(name)).offset(skip).limit(take);
//            query.retrievedFields(true, "picture_url", "status_flag", "owner", "title", "created_time","favorite_number").order(Oder);
//            return query.asList();
//        } catch (Exception ex) {
//            throw new DAOException();
//        }
//    }
    public List<User> searchAndFillAllUserNomal(String name, int skip, int take, String order, int flag) throws DAOException {
        try {
            Query<User> query = datastore.createQuery(User.class).filter("role", User.NORMAL_USER_ROLE);
            query.filter("active_flag", flag);
            query.field("display_name").containsIgnoreCase(Unicode.toAscii(name)).offset(skip).limit(take);
            query.retrievedFields(true,"id", "display_name", "registered_time", "email", "n_bans", "n_recipes", "active_flag").order(order);
            return query.asList();
        }
        catch (Exception ex) {
            throw new DAOException();
        }

    }
}
