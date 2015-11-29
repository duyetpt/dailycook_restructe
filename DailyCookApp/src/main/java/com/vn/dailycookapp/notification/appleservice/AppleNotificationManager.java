/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.notification.appleservice;

import com.relayrides.pushy.apns.ApnsEnvironment;
import com.relayrides.pushy.apns.PushManager;
import com.relayrides.pushy.apns.PushManagerConfiguration;
import com.relayrides.pushy.apns.util.ApnsPayloadBuilder;
import com.relayrides.pushy.apns.util.SSLContextUtil;
import com.relayrides.pushy.apns.util.SimpleApnsPushNotification;
import com.vn.dailycookapp.cache.user.CompactUserInfo;
import com.vn.dailycookapp.cache.user.UserCache;
import com.vn.dailycookapp.utils.ConfigurationLoader;
import com.vn.dailycookapp.utils.lang.Language;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.dao.DAOException;
import org.dao.DeviceTokenDAO;
import org.entity.DeviceToken;
import org.entity.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author duyetpt
 */
public class AppleNotificationManager {

    private final String P12_PATH = ConfigurationLoader.getInstance().getDeloyDirectory() + File.separator + "P12";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private PushManager<SimpleApnsPushNotification> pushManager;
//    private final ExecutorService exeService;
//    private final PushManagerConfiguration pushManagerConfig;
    private final StringBuffer sbuffer;

    private AppleNotificationManager() {
        sbuffer = new StringBuffer();
//        exeService = new ThreadPoolExecutor(2, 4, 3, TimeUnit.MINUTES, new SynchronousQueue<Runnable>());

//        pushManagerConfig = new PushManagerConfiguration();
//        pushManagerConfig.setConcurrentConnectionCount(5);
        try {
            // TODO config
            pushManager
                    = new PushManager<>(
                            ApnsEnvironment.getSandboxEnvironment(),
                            SSLContextUtil.createDefaultSSLContext(P12_PATH, ""),
                            null, // Optional: custom event loop group
                            null, // Optional: custom ExecutorService for calling listeners
                            null, // Optional: custom BlockingQueue implementation
                            new PushManagerConfiguration(),
                            "DCA_IOS_NOTIFICATION");
            pushManager.registerRejectedNotificationListener(new AppleRejectedNotificationListener());
            pushManager.registerFailedConnectionListener(new AppleFailedConnectionListener());
            pushManager.registerExpiredTokenListener(new AppleExpiredTokenListener());
            pushManager.start();
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException | KeyManagementException | IOException ex) {
            logger.error("init apple notificatin manager error", ex);
        }
    }

    public void push(Notification noti) {
        try {
            CompactUserInfo toUser = UserCache.getInstance().get(noti.getTo());
            CompactUserInfo fromUser = UserCache.getInstance().get(noti.getFrom());

            List<DeviceToken> deviceTokens = DeviceTokenDAO.getInstance().getUserDevices(noti.getTo());
            if (deviceTokens == null || deviceTokens.isEmpty()) {
                return;
            }

            String msg = Language.getInstance().getMessage(noti.getType(), toUser.getLanguage());

            final ApnsPayloadBuilder payloadBuilder = new ApnsPayloadBuilder();

            sbuffer.append(fromUser.getDisplayName()).append(" ").append(msg);
            payloadBuilder.setAlertBody(sbuffer.toString());
            payloadBuilder.setSoundFileName("ring-ring.aiff");
            payloadBuilder.setAlertTitle("Dailycook");
            payloadBuilder.setLocalizedActionButtonKey("VIEW");
            payloadBuilder.setBadgeNumber(toUser.getNumberNotification());
            
            final String payload = payloadBuilder.buildWithDefaultMaximumLength();

            for (DeviceToken token : deviceTokens) {
                pushManager.getQueue().put(new SimpleApnsPushNotification(token.getDeviceTokenByte(), payload));
            }

            pushManager.requestExpiredTokens();
        } catch (DAOException | InterruptedException ex) {
            logger.error("push notification error", ex);
        }
    }
    
    public void pushs(List<Notification> listNotification) {
        for (Notification noti : listNotification) {
            push(noti);
        }
    }
    
    private static AppleNotificationManager instance = new AppleNotificationManager();
    public static AppleNotificationManager getInstance() {
        return instance;
    }
}
