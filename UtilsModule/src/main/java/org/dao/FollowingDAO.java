package org.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.entity.Following;
import org.mongodb.morphia.query.Query;

/**
 *
 * @author duyetpt recipe is favorited by
 */
public class FollowingDAO extends AbstractDAO<Following> {

    private static final FollowingDAO instance = new FollowingDAO();

    private FollowingDAO() {

    }

    public static FollowingDAO getInstance() {
        return instance;
    }

    public boolean following(String ownerId, String starId) throws DAOException {
        Query<Following> query = datastore.createQuery(Following.class)
                .field("_id").equal(new ObjectId(ownerId));
        if (query.countAll() == 0) {
            Following foll = new Following();
            foll.setOwner(new ObjectId(ownerId));

            List<String> list = new ArrayList<>();
            list.add(starId);
            foll.setStarIds(list);

            save(foll);
            return true;
        } else {
            return pushToArray(ownerId, "following", starId, Following.class);
        }
    }

    public boolean unfollow(String ownerId, String starId) throws DAOException {
        return pullToArray(ownerId, "following", starId, Following.class);
    }

    public void addFollower(String ownerId, String follower)
            throws DAOException {
        Query<Following> query = datastore.createQuery(Following.class)
                .field("_id").equal(new ObjectId(ownerId));
        if (query.countAll() == 0) {
            Following foll = new Following();
            foll.setOwner(new ObjectId(ownerId));

            List<String> list = new ArrayList<>();
            list.add(follower);
            foll.setFollowers(list);

            save(foll);
        } else {
            pushToArray(ownerId, "followers", follower, Following.class);
        }

    }

    public void removeFollower(String ownerId, String follower)
            throws DAOException {
        pullToArray(ownerId, "followers", follower, Following.class);
    }
    
    public void removeFollowing(String ownerId, String startId)
            throws DAOException {
        pullToArray(ownerId, "following", startId, Following.class);
    }
    
    public boolean isFollowing(String ownerId, String starId)
            throws DAOException {
        try {
            Query<Following> query = datastore.createQuery(Following.class)
                    .field("_id").equal(new ObjectId(ownerId));
            query.field("following").equal(starId);

            Following foll = query.retrievedFields(true, "_id").get();
            if (foll != null) {
                return foll.getOwner() != null;
            }
        } catch (Exception ex) {
            logger.error("check following fail", ex);
            throw new DAOException();
        }

        return false;
    }
}
