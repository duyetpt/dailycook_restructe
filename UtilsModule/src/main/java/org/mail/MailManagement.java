package org.mail;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 
 * @author duyetpt
 * De co the gui mail, can vao tai khoan cua nguoi gui (from) config lai phan security -> Allow less secure apps: ON
 */
public class MailManagement {

	private Queue<MailCompose> queue;

	private static final MailManagement instance = new MailManagement();

	private MailManagement() {
		queue = new ArrayDeque<MailCompose>();
	}

	public static MailManagement getInstance() {
		return instance;
	}

	public void addMail(MailCompose mail) {
		synchronized (queue) {
			queue.add(mail);
			queue.notify();
		}
	}

	public MailCompose getMail() {
		synchronized (queue) {
			while (queue.isEmpty())
				try {
					queue.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return queue.poll();
		}
	}
}
