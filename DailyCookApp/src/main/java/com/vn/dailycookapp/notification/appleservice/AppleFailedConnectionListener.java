/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.notification.appleservice;

import com.relayrides.pushy.apns.FailedConnectionListener;
import com.relayrides.pushy.apns.PushManager;
import com.relayrides.pushy.apns.util.SimpleApnsPushNotification;
import javax.net.ssl.SSLHandshakeException;
import org.slf4j.LoggerFactory;

/**
 *
 * @author duyetpt
 */
public class AppleFailedConnectionListener implements FailedConnectionListener<SimpleApnsPushNotification>{

    org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void handleFailedConnection(PushManager<? extends SimpleApnsPushNotification> pm, Throwable thrwbl) {
        if (thrwbl instanceof SSLHandshakeException) {
            try {
                // This is probably a permanent failure, and we should shut down
                // the PushManager.
                logger.error("Cannot connect to APNs, shutdown service...");
                pm.shutdown();
            } catch (InterruptedException ex) {
                logger.error("shuted");
                ex.printStackTrace();
            }
        }
    }
    
}
