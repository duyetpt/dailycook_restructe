/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.logs;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import org.entity.ActivityLog;

/**
 *
 * @author duyetpt
 */
public class LogQueue {

    private Queue<ActivityLog> queue;
    private static final LogQueue instance = new LogQueue();

    private LogQueue() {
        queue = new ConcurrentLinkedDeque<>();
    }

    public static LogQueue getInstance() {
        return instance;
    }

    public synchronized void add(ActivityLog log) {
        queue.add(log);
    }

    public synchronized ActivityLog get() {
        return queue.poll();
    }
    
    public boolean isEmpty() {
        return this.queue.isEmpty();
    }
}
