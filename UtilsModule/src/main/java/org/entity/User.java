package org.entity;

import org.TimeUtils;
import org.json.JsonIgnoreEmpty;
import org.json.JsonIgnoreProperty;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Property;

@Entity(value = "User", noClassnameStored = true)
@JsonIgnoreEmpty
public class User {

    public static final String LANG_ENGLISH = "en";
    public static final String LANG_VIETNAMESE = "vi";

    public static final String NORMAL_USER_ROLE = "normal_user";
    public static final String SUPER_ADMIN_ROLE = "super_admin";
    public static final String ADMIN_ROLE = "admin";

//    public static final String SORT_BY_NAME = "display_name";/
    public static final String SORT_BY_DATE = "-registered_time";
    public static final String SORT_BY_RECIPE = "-n_recipes";
    public static final String SORT_BY_BAN = "-n_bans";
    public static final String SORT_BY_Name = "display_name";
    

    public static final int ACTIVE_FLAG = 1;
    public static final int BAN_FLAG_ONCE = 0;
    public static final int BAN_FLAG_SECOND = -1;
    public static final int DELETED_FLAG = -2;

    public static final long BAN_FIRST_TIME = 7 * 24 * 60 * 60 * 1000L;
    public static final long BAN_SECOND_TIME = 30 * 24 * 60 * 60 * 1000L;

    @Id
    private String id;

    @Property("display_name")
    private String displayName;

    @JsonIgnoreProperty
    @Property("display_name_normalize")
    private String displayNameNormalize;

    @Property("email")
    @Indexed(background = false, unique = true)
    private String email;

    @Property("pass")
    private String password;

    @Property("login_method")
    private String loginMethod;

    @Property("n_recipes")
    private int numberRecipes;

    @Property("n_reports")
    private int numberReport;

    @Property("n_bans")
    private int numberBans;

    @Property("n_follower")
    private int numberFollower;

    @Property("n_following")
    private int numberFollowing;

    @Property("avatar_url")
    private String avatarUrl;

    @Property("cover_url")
    private String coverUrl;

    @Property("introduce")
    private String introduce;

    @Property("role")
    private String role = NORMAL_USER_ROLE;

    @Property("registered_time")
    private long registeredTime = TimeUtils.getCurrentGMTTime();

    @Property("lang")
    private String language = LANG_ENGLISH;

    @Property("active_flag")
    private int activeFlag = ACTIVE_FLAG;

    @Property("dob")
    private String dob;

    @Property("phone")
    @JsonIgnoreProperty
    private String phone;

    @Property("n_notification")
    private int notificationNumber;

    @JsonIgnoreProperty
    @Property("ban_to_time")
    private long banToTime;

    @Property("notification_flag")
    private boolean notificationFlag = true;

    public String getId() {
        return id;
    }

    @JsonIgnoreProperty
    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginMethod() {
        return loginMethod;
    }

    public void setLoginMethod(String loginMethod) {
        this.loginMethod = loginMethod;
    }

    public int getNumberRecipes() {
        return numberRecipes;
    }

    public void setNumberRecipes(int numberRecipes) {
        this.numberRecipes = numberRecipes;
    }

    public int getNumberReport() {
        return numberReport;
    }

    public void setNumberReport(int numberReport) {
        this.numberReport = numberReport;
    }

    public int getNumberBans() {
        return numberBans;
    }

    public void setNumberBans(int numberBans) {
        this.numberBans = numberBans;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getRegisteredTime() {
        return registeredTime;
    }

    public void setRegisteredTime(long registeredTime) {
        this.registeredTime = registeredTime;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(int activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getNotificationNumber() {
        return notificationNumber;
    }

    public void setNotificationNumber(int notificationNumber) {
        this.notificationNumber = notificationNumber;
    }

    public long getBanToTime() {
        return banToTime;
    }

    public void setBanToTime(long banToTime) {
        this.banToTime = banToTime;
    }

    public boolean getNotificationFlag() {
        return notificationFlag;
    }

    public void setNotificationFlag(boolean notificationFlag) {
        this.notificationFlag = notificationFlag;
    }

    public String getDisplayNameNormalize() {
        return displayNameNormalize;
    }

    public void setDisplayNameNormalize(String displayNameNormalize) {
        this.displayNameNormalize = displayNameNormalize;
    }

}
