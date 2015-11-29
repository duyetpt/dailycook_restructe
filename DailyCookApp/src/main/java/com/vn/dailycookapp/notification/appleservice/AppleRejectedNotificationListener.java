/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.notification.appleservice;

import com.relayrides.pushy.apns.PushManager;
import com.relayrides.pushy.apns.RejectedNotificationListener;
import com.relayrides.pushy.apns.RejectedNotificationReason;
import com.relayrides.pushy.apns.util.SimpleApnsPushNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author duyetpt
 */
public class AppleRejectedNotificationListener implements RejectedNotificationListener<SimpleApnsPushNotification>{

    Logger logger = LoggerFactory.getLogger(getClass());
    
    @Override
    public void handleRejectedNotification(PushManager<? extends SimpleApnsPushNotification> pm, SimpleApnsPushNotification notification, RejectedNotificationReason reason) {
        logger.warn("%s was rejected with rejection reason %s\n", notification, reason);
    }
    
}
