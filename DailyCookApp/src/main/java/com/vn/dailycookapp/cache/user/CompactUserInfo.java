package com.vn.dailycookapp.cache.user;

public class CompactUserInfo {

    private String userId;
    private String email;
    private String displayName;
    private int numberRecipes;
    private int numberFollower;
    private int numberFollowing;
    private String avatarUrl;
    private String coverUrl;
    private String introduce;
    private String language;
    private int numberNotification;
    private String dob;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public int getNumberNotification() {
        return numberNotification;
    }

    public void setNumberNotification(int numberNotification) {
        this.numberNotification = numberNotification;
    }

    public void increaseNumberRecipe() {
        this.numberRecipes++;
    }

    public void increaseNumberFollowing() {
        this.numberFollowing++;
    }

    public void increaseNumberFollower() {
        this.numberFollower++;
    }

    public void increaseNumberNotificaion() {
        this.numberNotification++;
    }

    public void decreaseNumberRecipe() {
        this.numberRecipes--;
    }

    public void decreaseNumberFollowing() {
        this.numberFollowing--;
    }

    public void decreaseNumberFollower() {
        this.numberFollower--;
    }

    public void decreaseNumberNotificaion() {
        this.numberNotification--;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

}
