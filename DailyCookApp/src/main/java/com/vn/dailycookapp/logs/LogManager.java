/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.logs;

/**
 *
 * @author duyetpt
 */
public class LogManager {

    private static final int NUMBER_WORKER = 2;

    public void start() {
        for (int i = 0; i < NUMBER_WORKER; i++) {
            LogWorker worker = new LogWorker();
            worker.start();
        }
    }
}
