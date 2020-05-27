package com.kok.sport.utils;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.Properties;

public class MailUtil {

    private static final String username = "testsmtp";
    private static final String password = "testsmtp123";
    private static final String smtp = "kok.work";//邮件发送服务器

    private Properties props;//系统属性
    private Session session;//邮件会话对象
    private MimeMessage mimeMsg; //MIME邮件对象
    private Multipart mp; //Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象

    /**
     * Constructor
     *
     * @param
     */
    public MailUtil() {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        props = System.getProperties();
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
        } catch (GeneralSecurityException e) {
        }
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", smtp);
        props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.socketFactory", sf);

        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        session.setDebug(true);
        mimeMsg = new MimeMessage(session);
        mp = new MimeMultipart();
    }

    /**
     * 发送邮件
     */
    public boolean sendMail(String to, String subject, String content) {
        try {
            //不被当作垃圾邮件的关键代码--Begin
            //mimeMsg.addHeader("X-Priority", "3");

            //mimeMsg.addHeader("X-MSMail-Priority", "Normal");

            mimeMsg.addHeader("X-Mailer","Microsoft Outlook Express 6.00.2900.2869");   //本文以outlook名义发送邮件，不会被当作垃圾邮件

            //mimeMsg.addHeader("X-MimeOLE", "Produced By Microsoft MimeOLE V6.00.2900.2869");

            //mimeMsg.addHeader("ReturnReceipt", "1");

            //不被当作垃圾邮件的关键代码--End

            //设置发信人
            mimeMsg.setFrom(new InternetAddress(smtp));
            //设置接收人
            //for (int i = 0; i < to.length; i++) {
                mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            //}
            //设置抄送人
            //   for (int i = 0; i < copyto.length; i++) {
            //    mimeMsg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(copyto[i]));
            //   }
            //设置主题
            mimeMsg.setSubject(subject);
            //设置正文
            BodyPart bp = new MimeBodyPart();
            bp.setContent(content, "text/html;charset=utf-8");
            mp.addBodyPart(bp);
            //设置附件
            //bp = new MimeBodyPart();
            //FileDataSource fileds = new FileDataSource(filename);
            //bp.setDataHandler(new DataHandler(fileds));
            //bp.setFileName(MimeUtility.encodeText(fileds.getName(), "UTF-8", "B"));
            //mp.addBodyPart(bp);
            mimeMsg.setContent(mp);
            mimeMsg.saveChanges();
            //发送邮件
            if (props.get("mail.smtp.auth").equals("true")) {
                Transport transport = session.getTransport("smtp");
                transport.connect((String) props.get("mail.smtp.host"), (String) props.get("username"), (String) props.get("password"));
                transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
            //  transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.CC));
                transport.close();
            } else {
                Transport.send(mimeMsg);
            }
            System.out.println("邮件发送成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
