/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.logs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author duyetpt
 */
public class LogManager {

    private static final int NUMBER_WORKER = 2;
    private final static Logger logger = LoggerFactory.getLogger(LogManager.class);

    public void start() {
        for (int i = 0; i < NUMBER_WORKER; i++) {
            try {
                logger.error("LogManager -> start worker " + i);
                LogWorker worker = new LogWorker();
                worker.start();
            } catch (Exception ex) {
                logger.error("LogManager -> start service error", ex);
            }
        }
    }
}
