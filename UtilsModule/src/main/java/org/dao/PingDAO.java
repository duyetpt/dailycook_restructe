/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dao;

import org.entity.Ping;

/**
 *
 * @author duyetpt
 */
public class PingDAO extends AbstractDAO<Ping> {

    private static final PingDAO instance = new PingDAO();
    private PingDAO() {
    }
    public static PingDAO getInstance() {
        return instance;
    }
    
    public int get() {
        return (int) datastore.getCount(Ping.class);
    }
}
