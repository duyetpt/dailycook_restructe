/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.notification.appleservice;

import com.relayrides.pushy.apns.ExpiredToken;
import com.relayrides.pushy.apns.ExpiredTokenListener;
import com.relayrides.pushy.apns.PushManager;
import com.relayrides.pushy.apns.util.SimpleApnsPushNotification;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author duyetpt
 */
public class AppleExpiredTokenListener implements ExpiredTokenListener<SimpleApnsPushNotification>{
    
    Logger logger = LoggerFactory.getLogger(getClass());
    
    @Override
    public void handleExpiredTokens(PushManager<? extends SimpleApnsPushNotification> pm, Collection<ExpiredToken> clctn) {
        for (final ExpiredToken expiredToken : clctn) {
            // Stop sending push notifications to each expired token if the expiration
            // time is after the last time the app registered that token.
            logger.warn("ExpireToken: " + new String(expiredToken.getToken()));
        }
    }
    
}
