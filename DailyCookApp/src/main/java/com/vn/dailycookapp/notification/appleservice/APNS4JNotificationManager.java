/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.notification.appleservice;

import cn.teaey.apns4j.keystore.KeyStoreHelper;
import cn.teaey.apns4j.keystore.KeyStoreWraper;
import cn.teaey.apns4j.network.AppleGateway;
import cn.teaey.apns4j.network.AppleNotificationServer;
import cn.teaey.apns4j.network.SecurityConnection;
import cn.teaey.apns4j.network.SecuritySocketFactory;
import cn.teaey.apns4j.protocol.NotifyPayload;
import com.vn.dailycookapp.cache.user.CompactUserInfo;
import com.vn.dailycookapp.cache.user.UserCache;
import com.vn.dailycookapp.utils.lang.Language;
import java.util.List;
import org.dao.DeviceTokenDAO;
import org.entity.DeviceToken;
import org.entity.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author duyetpt
 */
public class APNS4JNotificationManager {

//    private final String P12_PATH = ConfigurationLoader.getInstance().getDeloyDirectory() + File.separator + "p12" + File.separator + "CertificatesPushDailyCook.p12";
    private final String P12_PATH = getClass().getClassLoader().getResource("CertificatesPushDailyCook.p12").getPath();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private SecuritySocketFactory socketFactory;

    private APNS4JNotificationManager() {
        startWithAPNS4J();
    }

    public final void startWithAPNS4J() {
        try {
            //get a keystore
            KeyStoreWraper keyStoreWrapper = KeyStoreHelper.getKeyStoreWraper(P12_PATH, "");
            //get apple server with env
            AppleNotificationServer appleNotificationServer = AppleNotificationServer.get(AppleGateway.ENV_DEVELOPMENT);
            //init ssl socket factory
            socketFactory = SecuritySocketFactory.Builder.newBuilder().appleServer(appleNotificationServer).keyStoreWrapper(keyStoreWrapper).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("start push notification error", ex);
        }
    }

    public void push(Notification noti) {
        //create & init a notify payload
        SecurityConnection connection = null;
        try {
            StringBuilder sbuffer = new StringBuilder();
            CompactUserInfo toUser = UserCache.getInstance().get(noti.getTo());
            logger.info("prepare push Notification:" + toUser.getDisplayName() + ":" + toUser.getEmail());
            CompactUserInfo fromUser = UserCache.getInstance().get(noti.getFrom());

            List<DeviceToken> deviceTokens = DeviceTokenDAO.getInstance().getUserDevices(noti.getTo());
            if (deviceTokens == null || deviceTokens.isEmpty()) {
                logger.warn(toUser.getDisplayName() + " don't regist any device");
                return;
            }

            String msg = Language.getInstance().getMessage(noti.getType(), toUser.getLanguage());
            sbuffer.append(fromUser.getDisplayName()).append(" ").append(msg);

            final NotifyPayload notifyPayload = NotifyPayload.newNotifyPayload();
            notifyPayload.alert(sbuffer.toString());
            notifyPayload.badge(toUser.getNumberNotification());
            notifyPayload.sound("ring-ring.aiff");

            for (DeviceToken token : deviceTokens) {
                logger.info("APNS-" + token.getDeviceToken());
                connection = SecurityConnection.newSecurityConnection(socketFactory);
                connection.sendAndFlush(token.getDeviceToken(), notifyPayload);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("push notification error", ex);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void pushs(List<Notification> listNotification) {
        for (Notification noti : listNotification) {
            push(noti);
        }
    }

    private static APNS4JNotificationManager instance = new APNS4JNotificationManager();

    public static APNS4JNotificationManager getInstance() {
        return instance;
    }

}
