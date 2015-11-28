/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entity;

import org.json.JsonIgnoreEmpty;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Property;

/**
 *
 * @author duyetpt
 */
@Entity(noClassnameStored = true)
@JsonIgnoreEmpty
public class DeviceToken {

    public static final String IOS_PLATFORM = "ios";
    public static final int IOS_TOKEN_LENGTH_SIZE = 64;
    
    public static final String ANDROID_PLATFORM = "android";

    @Id
    private String id;

    @Indexed
    @Property("user_id")
    private String userId;

    @Indexed(unique = true)
    @Property("device_token")
    private String deviceToken;

    @Property("device_token_byte")
    private byte[] deviceTokenByte;

    private String platform;

    public DeviceToken(String userId, String deviceToke, String platform) {
        this.userId = userId;
        this.deviceToken = deviceToke;
        this.platform = platform;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceToke() {
        return deviceToken;
    }

    public void setDeviceToke(String deviceToke) {
        this.deviceToken = deviceToke;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public byte[] getDeviceTokenByte() {
        return deviceTokenByte;
    }

    public void setDeviceTokenByte(byte[] deviceTokenByte) {
        this.deviceTokenByte = deviceTokenByte;
    }

}
