package org.UtilsModule;

import org.mail.MailCompose;
import org.mail.MailManagement;
import org.mail.MailWorker;

public class TestSentMail {

	public static void main(String[] args) {
		MailWorker worker = new MailWorker();
		worker.start();

		MailCompose mail = new MailCompose();
		mail.setContent("Xin chao, toi dang test chuc nang gui mail");
		mail.setTitle("Test");
		mail.setFrom("duyetentertainment@gmail.com");
		mail.setPassword("nguoiviet1993");
		mail.setTo("duyetptse02451@fpt.edu.vn");

		MailManagement.getInstance().addMail(mail);
	}
}
