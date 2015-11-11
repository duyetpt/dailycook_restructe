package org.entity;

import org.TimeUtils;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

@Entity(noClassnameStored = true)
public final class Session {

    static final long TTL = 30 * 24 * 60 * 60 * 1000l;	// ten day
    @Id
    private String id;

    @Indexed(unique = true)
    private String token;
    private long timeToLife;
    @Indexed
    private String userId;

    public Session() {
        timeToLife = TTL;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getTimeToLife() {
        return timeToLife;
    }

    /**
     * Reset time to life as Default
     */
    public void resetTimeToLife() {
        this.timeToLife = TTL;
    }

    /**
     * Decrease time to life
     *
     * @param time
     */
    public void downTimeToLife(long time) {
        this.timeToLife -= time;
    }

    public void updateLastActiveTime() {
        this.timeToLife = TTL;
    }
}
