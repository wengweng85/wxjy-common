package com.insigma.common.email;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.*;

public class SimpleMailSender {
    public SimpleMailSender() {
    }

    public boolean sendTextMail(MailSenderInfo mailInfo) {
        MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        if(mailInfo.isValidate()) {
            authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }

        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);

        try {
            Message mailMessage = new MimeMessage(sendMailSession);
            Address from = new InternetAddress(mailInfo.getFromAddress());
            mailMessage.setFrom(from);
            Address to = new InternetAddress(mailInfo.getToAddress());
            mailMessage.setRecipient(RecipientType.TO, to);
            mailMessage.setSubject(mailInfo.getSubject());
            mailMessage.setSentDate(new Date());
            String mailContent = mailInfo.getContent();
            mailMessage.setText(mailContent);
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException var9) {
            var9.printStackTrace();
            return false;
        }
    }

    public static boolean sendHtmlMail(MailSenderInfo mailInfo) {
        MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        if(mailInfo.isValidate()) {
            authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }

        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);

        try {
            Message mailMessage = new MimeMessage(sendMailSession);
            Address from = new InternetAddress(mailInfo.getFromAddress());
            mailMessage.setFrom(from);
            Address to = new InternetAddress(mailInfo.getToAddress());
            mailMessage.setRecipient(RecipientType.TO, to);
            mailMessage.setSubject(mailInfo.getSubject());
            mailMessage.setSentDate(new Date());

            Multipart mainPart = new MimeMultipart();
            //邮件正文
            BodyPart html = new MimeBodyPart();
            html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            //邮件附件
            if(mailInfo.getAttachFileNames().length > 0) {
                for(int i=0;i<mailInfo.getAttachFileNames().length;i++) {
                    File attachment = new File(mailInfo.getAttachFileNames()[i]);
                    BodyPart attachmentPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(attachment);
                    attachmentPart.setDataHandler(new DataHandler(source));
                    //避免中文乱码的处理
                    attachmentPart.setFileName(MimeUtility.encodeWord(attachment.getName()));
                    mainPart.addBodyPart(attachmentPart);
                }
            }

            mailMessage.setContent(mainPart);
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException var9) {
            var9.printStackTrace();
            return false;
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
