/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.notification;

import com.vn.dailycookapp.utils.ConfigurationLoader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.DCAHttpRequest;
import org.entity.Notification;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author duyetpt
 */
public class APNSPusher {

    private static final APNSPusher instance = new APNSPusher();
    Logger logger = LoggerFactory.getLogger(getClass());

    private APNSPusher() {
    }

    public static APNSPusher getInstance() {
        return instance;
    }

    public void push(List<Notification> list) {
        try {
            List<String> notiIds = new ArrayList<>();
            for (Notification noti : list) {
                notiIds.add(noti.getId());
            }

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("notifications", notiIds);
            String data = jsonObj.toString();
            logger.info("Push notification: " + data);
            URI uri = DCAHttpRequest.getInstance().buildUrl(ConfigurationLoader.getInstance().getNotificationHost(), ConfigurationLoader.getInstance().getNotificationPort(), "", null);
            DCAHttpRequest.getInstance().post(uri, data);
        } catch (Exception ex) {
            logger.error("push notification error", ex);
        }
    }
}
