/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.notification.appleservice;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.EnhancedApnsNotification;
import com.notnoop.apns.ReconnectPolicy;
import com.vn.dailycookapp.cache.user.CompactUserInfo;
import com.vn.dailycookapp.cache.user.UserCache;
import com.vn.dailycookapp.utils.ConfigurationLoader;
import com.vn.dailycookapp.utils.lang.Language;
import java.io.File;
import java.util.Date;
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
public class APNSManager {

    private static final String APNS_PASS = "123@123a";
//    private static final String APNS_PATH_P12 = "C:\\Users\\duyetpt\\Documents\\dailycook_restructe\\DailyCookApp\\src\\resources\\p12\\Certificates2.p12";
    private ApnsService service;

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final APNSManager instance = new APNSManager();

    public static APNSManager getInstance() {
        return instance;
    }

    private APNSManager() {
        try {
            String APNS_PATH_P12 = ConfigurationLoader.getInstance().getDeloyDirectory() + File.separator + "p12" + "/Certificates2.p12";
            service = APNS.newService().asPool(5).withCert(APNS_PATH_P12, APNS_PASS)
                    .withReconnectPolicy(ReconnectPolicy.Provided.NEVER)
                    .withSandboxDestination().build();
            service.start();
        } catch (Exception ex) {
            logger.error("Start APNSManager error", ex);
        }
    }

    public void testPush(String deviceId) {
        String payload = APNS.newPayload().alertBody("Dailycook test...").alertTitle("Dailycook")
                .badge(1).sound("ring-ring.aiff").build();

        int now = (int) (new Date().getTime() / 1000);
        EnhancedApnsNotification enhanceApnsNoti = new EnhancedApnsNotification(
                EnhancedApnsNotification.INCREMENT_ID(),
                now + 60 * 60 * 24/* Expire in one day */,
                deviceId, payload);
        service.push(enhanceApnsNoti);
    }

    public void push(Notification noti) {
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

            String payload = APNS.newPayload().alertBody(sbuffer.toString()).alertTitle("Dailycook")
                    .badge(toUser.getNumberNotification()).sound("ring-ring.aiff").build();

            for (DeviceToken token : deviceTokens) {
                logger.info("APNS-" + token.getDeviceToken());
                int now = (int) (new Date().getTime() / 1000);
                EnhancedApnsNotification enhanceApnsNoti = new EnhancedApnsNotification(
                        EnhancedApnsNotification.INCREMENT_ID(),
                        now + 60 * 60 * 24/* Expire in one day */,
                        token.getDeviceToken(), payload);
                // push notification
                service.push(enhanceApnsNoti);
            }
        } catch (Exception ex) {
            logger.error("push notification error", ex);
        }
    }

    public void pushs(List<Notification> listNotification) {
        for (Notification noti : listNotification) {
            push(noti);
        }
    }
}
