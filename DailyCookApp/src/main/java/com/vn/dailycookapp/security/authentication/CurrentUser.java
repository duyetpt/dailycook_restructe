package com.vn.dailycookapp.security.authentication;

import org.EncryptDataException;
import org.EncryptHelper;
import org.dao.DAOException;
import org.dao.UserDAO;
import org.entity.User;

import com.vn.dailycookapp.cache.user.UserCache;
import com.vn.dailycookapp.entity.response.AccountInfo;
import com.vn.dailycookapp.security.session.SessionManager;
import com.vn.dailycookapp.utils.DCAException;
import com.vn.dailycookapp.utils.ErrorCodeConstant;
import com.vn.dailycookapp.utils.lang.Language;
import com.vn.dailycookapp.utils.validate.Validator;
import org.TimeUtils;

public class CurrentUser {

    private static final String FB_EMAIL = "@facebook.com";

    private String userId;
    private String displayName;
    private String avatarUrl;
    private String coverUrl;
    private String dob;
    private String language;
    private String token;
    private int numberRecipes;
    private int numberFollower;
    private int numberFollowing;
    private int numberNotification;
    private boolean notificationFlag;

    public void login(FbToken fbToken) throws DCAException, DAOException {
        // get data into database
        User user = UserDAO.getInstance().getUserInfoByEmail(fbToken.getFbId() + FB_EMAIL);

        // not have account in system
        if (user == null) {
            AccountInfo acc = VerifyFacebookAccount.getInstance().sentGet(fbToken.getRefreshToken());
            if (acc == null) {
                throw new LoginFailException(ErrorCodeConstant.LOGIN_FB_FAIL);
            } else {
                if (fbToken.getFbId().equals(acc.getFbId())) {
                    displayName = acc.getDisplayName();
                    avatarUrl = acc.getAvatarUrl();
                    coverUrl = acc.getCoverUrl();
                    dob = acc.getDob();
                    language = Language.ENGLISH;
                    // Update DATABASE
                    String userId = saveToDB(fbToken);
                    token = SessionManager.getInstance().addSession(userId);
                    this.userId = userId;
                    this.notificationFlag = true;
                } else {
                    throw new LoginFailException(ErrorCodeConstant.LOGIN_FB_FAIL);
                }
            }
        } else {
            if (user.getActiveFlag() == User.DELETED_FLAG || user.getBanToTime() > TimeUtils.getCurrentGMTTime()) {
                throw new BanedUserException(ErrorCodeConstant.BANED_USER);
            }
            this.userId = user.getId();
            displayName = user.getDisplayName();
            avatarUrl = user.getAvatarUrl();
            coverUrl = user.getCoverUrl();
            dob = user.getDob();
            language = user.getLanguage();
            this.numberFollower = user.getNumberFollower();
            this.numberFollowing = user.getNumberFollowing();
            this.numberRecipes = user.getNumberRecipes();
            this.notificationFlag = user.getNotificationFlag();
            token = SessionManager.getInstance().addSession(user.getId());
        }

    }

    private String saveToDB(FbToken fbToken) throws DCAException, DAOException {
        User user = new User();
        user = new User();
        user.setDisplayName(displayName);
        user.setAvatarUrl(avatarUrl);
        user.setCoverUrl(coverUrl);
        user.setDob(dob);
        user.setEmail(fbToken.getFbId() + FB_EMAIL);

        // save to dao
        UserDAO.getInstance().save(user);

        // Cache user info
        UserCache.getInstance().cache(user);

        return user.getId();
    }

    public void login(UsernamePasswordToken token) throws DCAException, EncryptDataException, DAOException {
        Validator.getInstance().validateEmail(token.getUsername());
        Validator.getInstance().validatePassword(token.getPassword());

        User user = UserDAO.getInstance().getUserInfoByEmail(token.getUsername());
        if (user != null) {
            String password = EncryptHelper.encrypt(token.getPassword());

            if (password.equals(user.getPassword())) {
                if (user.getActiveFlag() == User.DELETED_FLAG || user.getBanToTime() > TimeUtils.getCurrentGMTTime()) {
                    throw new BanedUserException(ErrorCodeConstant.BANED_USER);
                }

                if (user.getDisplayName() != null) {
                    displayName = user.getDisplayName();
                } else {
                    displayName = user.getEmail().split("@")[0];
                }
                this.userId = user.getId();
                avatarUrl = user.getAvatarUrl();
                coverUrl = user.getCoverUrl();
                dob = user.getDob();
                language = user.getLanguage();
                this.token = SessionManager.getInstance().addSession(user.getId());
                this.numberFollower = user.getNumberFollower();
                this.numberFollowing = user.getNumberFollowing();
                this.numberRecipes = user.getNumberRecipes();
                this.notificationFlag = user.getNotificationFlag();
                // cache data
                UserCache.getInstance().cache(user);
            } else {
                throw new LoginFailException(ErrorCodeConstant.PASSWORD_INCORRECT);
            }
        } else {
            throw new LoginFailException(ErrorCodeConstant.USER_NOT_FOUND);
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public String getDob() {
        return dob;
    }

    public String getToken() {
        return token;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getNumberRecipes() {
        return numberRecipes;
    }

    public void setNumberRecipes(int numberRecipes) {
        this.numberRecipes = numberRecipes;
    }

    public int getNumberFollower() {
        return numberFollower;
    }

    public void setNumberFollower(int numberFollower) {
        this.numberFollower = numberFollower;
    }

    public int getNumberFollowing() {
        return numberFollowing;
    }

    public void setNumberFollowing(int numberFollowing) {
        this.numberFollowing = numberFollowing;
    }

    public int getNumberNotification() {
        return numberNotification;
    }

    public void setNumberNotification(int notificationNumber) {
        this.numberNotification = notificationNumber;
    }

    public boolean getNotificationFlag() {
        return notificationFlag;
    }

    public void setNotificationFlag(boolean notificationFlag) {
        this.notificationFlag = notificationFlag;
    }

}
