/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.logs;

import java.util.logging.Level;
import org.TimeUtils;
import org.dao.ActivityLogDAO;
import org.dao.DAOException;
import org.entity.ActivityLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author duyetpt
 */
public class LogWorker extends Thread {

    Logger logger = LoggerFactory.getLogger(LogWorker.class);

    @Override
    public void run() {
        if (LogQueue.getInstance().isEmpty()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                logger.error("LogWorker -> sleeping error", ex);
            }
        } else {
            ActivityLog log = LogQueue.getInstance().get();
            log.setTime(TimeUtils.getStartDay(TimeUtils.getCurrentGMTTime()));
            try {
                ActivityLogDAO.getInstance().save(log);
            } catch (DAOException ex) {
                logger.error("LogWorker -> save activity error", ex);
            }
        }
    }
}
