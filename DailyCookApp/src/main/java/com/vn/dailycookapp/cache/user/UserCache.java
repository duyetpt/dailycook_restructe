package com.vn.dailycookapp.cache.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.Unicode;
import org.dao.DAOException;
import org.dao.UserDAO;
import org.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserCache {

    Logger logger = LoggerFactory.getLogger(getClass());
    private Map<String, CompactUserInfo> userMap;
    private Map<String, String> emailMap;

    private static final UserCache instance = new UserCache();

    private UserCache() {
        userMap = new TreeMap<>();
        emailMap = new TreeMap<String, String>();
    }

    public static UserCache getInstance() {
        return instance;
    }

    public void cache(User user) {
        if (user == null) {
            return;
        }
        CompactUserInfo cUser = new CompactUserInfo();
        cUser.setAvatarUrl(user.getAvatarUrl());
        cUser.setCoverUrl(user.getCoverUrl());
        cUser.setDisplayName(user.getDisplayName());
        cUser.setEmail(user.getEmail());
        cUser.setNumberFollower(user.getNumberFollower());
        cUser.setNumberFollowing(user.getNumberFollowing());
        cUser.setNumberRecipes(user.getNumberRecipes());
        cUser.setUserId(user.getId());
        cUser.setIntroduce(user.getIntroduce());
        cUser.setLanguage(user.getLanguage());
        cUser.setNumberNotification(user.getNotificationNumber());
        cUser.setDob(user.getDob());

        userMap.put(user.getId(), cUser);
        emailMap.put(user.getEmail(), user.getId());
    }

    /**
     * Get user info by userId. Check user is cached. If not cache, get info
     * from database => cache
     *
     * @param userId
     * @return
     * @throws DAOException
     */
    public CompactUserInfo get(String userId) throws DAOException {
        CompactUserInfo cUser = userMap.get(userId);
        if (cUser == null) {
            User user = UserDAO.getInstance().get(userId, User.class);
            if (user != null) {
                cache(user);
                cUser = get(userId);
            }
        }

        return cUser;
    }

    /**
     * Get user info by email. Check cache, if not find, get from data base =>
     * cache
     *
     * @param email
     * @return
     * @throws DAOException
     */
    public CompactUserInfo getInfoByEmail(String email) throws DAOException {
        String id = emailMap.get(email);
        if (id == null) {
            User user = UserDAO.getInstance().getUserInfoByEmail(email);
            if (user != null && user.getRole().equals(User.NORMAL_USER_ROLE)) {
                cache(user);
                id = user.getId();
            }
        }
        return id == null ? null : userMap.get(id);
    }

//    /**
//     * Get list user info by username
//     *
//     * @param username
//     * @return
//     * @throws DAOException
//     */
//    public List<CompactUserInfo> list(String username, int skip, int take) throws DAOException {
//        Set<String> userIds = new HashSet<String>();
//        for (CompactUserInfo cUser : userMap.values()) {
//            if (Unicode.toAscii(cUser.getDisplayName()).toLowerCase().contains(username)) {
//                userIds.add(cUser.getUserId());
//            }
//            if (cUser.getEmail().contains(username)) {
//                userIds.add(cUser.getUserId());
//            }
//        }
//
//        logger.info("Find_user_in_cache:" + userIds.toString());
//
//        if (userIds.isEmpty()) {
//            List<User> users = UserDAO.getInstance().listUserByName(username);
//            if (users != null) {
//                for (User user : users) {
//                    if (!user.getRole().equals(User.NORMAL_USER_ROLE)) {
//                        continue;
//                    }
//                    userIds.add(user.getId());
//                    cache(user);
//                }
//                logger.info("Find_user_in_db:" + users.toString());
//            }
//        } else if (userIds.size() < skip + take) {
//            int foundNumber = UserDAO.getInstance().countUserByName(username);
//            if (foundNumber > userIds.size()) {
//                List<User> users = UserDAO.getInstance().listUserByName(username);
//                if (users != null) {
//                    for (User user : users) {
//                        if (!user.getRole().equals(User.NORMAL_USER_ROLE)) {
//                            continue;
//                        }
//
//                        userIds.add(user.getId());
//                        cache(user);
//                    }
//                    logger.info("Find_user_in_db:" + users.toString());
//                }
//            }
//        }
//
//        List<CompactUserInfo> cUsers = new ArrayList<CompactUserInfo>();
//        for (String userId : userIds) {
//            cUsers.add(userMap.get(userId));
//        }
//
//        logger.info("Find_user_in_cache_response:" + cUsers.toString());
//        return cUsers;
//    }
    /**
     * Get list user info by username
     *
     * @param username
     * @return
     * @throws DAOException
     */
    public List<CompactUserInfo> list(String username) throws DAOException {
        Set<String> userIds = new HashSet<String>();

        List<User> users = UserDAO.getInstance().listUserByName(Unicode.toAscii(username));
        if (users != null) {
            for (User user : users) {
                if (!user.getRole().equals(User.NORMAL_USER_ROLE)) {
                    continue;
                }

                userIds.add(user.getId());
                cache(user);
            }
            logger.info("Find_user_in_db:" + users.toString());
        }

        List<CompactUserInfo> cUsers = new ArrayList<CompactUserInfo>();
        for (String userId : userIds) {
            cUsers.add(userMap.get(userId));
        }

        logger.info("Find_user_in_cache_response:" + cUsers.toString());
        return cUsers;
    }

    public List<CompactUserInfo> list(Set<String> userIds) throws DAOException {
        List<CompactUserInfo> users = new ArrayList<CompactUserInfo>();
        for (String userId : userIds) {
            CompactUserInfo user = get(userId);
            users.add(user);
        }

        return users;
    }

    public List<CompactUserInfo> list(List<String> userIds) throws DAOException {
        List<CompactUserInfo> users = new ArrayList<CompactUserInfo>();
        for (String userId : userIds) {
            CompactUserInfo user = get(userId);
            users.add(user);
        }

        return users;
    }

    public void cleanCache() {
        this.emailMap.clear();
        this.userMap.clear();
    }
}
