/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.server;

import com.vn.dailycookapp.logs.LogManager;
import com.vn.dailycookapp.security.session.SessionManager;
import org.dao.ConnectionDAO;

/**
 *
 * @author duyetpt
 */
public class Main {

    public static void main(String[] args) {
        // start management session
        ConnectionDAO.DB_HOST = "localhost";
        ConnectionDAO.CONFIG_FOR = ConnectionDAO.CONFIG_FOR_WEB_SERVICE;
        Thread mSession = new Thread(SessionManager.getInstance());
        mSession.start();
        
        // LogWorker
        LogManager logManager = new LogManager();
        logManager.start();
        
        DCAServerOnlyHttp.start();
    }
}
