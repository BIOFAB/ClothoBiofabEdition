package org.clothocad.widget.errorreporter;


/*
* Created on Feb 21, 2005
*
*/

import java.security.Security;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

public class sendmail {

    private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final String SMTP_PORT = "465";
    private static final String emailMsgTxt = "Test Message Contents";
    private static final String emailSubjectTxt = "A test from gmail";
    private static String emailFromAddress = "jcanderson2167@gmail.com";
    private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
    private static final String[] sendTo = { "jcanderson@berkeley.edu"};


    public static void main(String args[]) throws Exception {

        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

        new sendmail().sendSSLMessage(sendTo, emailSubjectTxt, emailMsgTxt, emailFromAddress);
    }

    public void sendSSLMessage(String recipients[], String subject,
        String message, String from) throws MessagingException {
        boolean debug = true;

        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.socketFactory.port", SMTP_PORT);
        props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.put("mail.smtp.socketFactory.fallback", "false");

        Session session = Session.getDefaultInstance(props,
        new javax.mail.Authenticator() {

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            String name = null;
            if(emailFromAddress!=null) {
                name = emailFromAddress;
            } else {
                name = JOptionPane.showInputDialog( "What is your gmail account name?" );
            }

            int index = name.indexOf("@gmail.com");
            if(index > 0) {
                String newname = name.substring(0, index);
                name = newname;
            }

            String password = JOptionPane.showInputDialog( "What is your gmail password?" );

            if(name==null) {
                return null;
            }
            if(password==null) {
                return null;
            }
            return new PasswordAuthentication(name, password);
        }
        });

        session.setDebug(debug);

        Message msg = new MimeMessage(session);
        InternetAddress addressFrom = new InternetAddress(from);
        msg.setFrom(addressFrom);

        InternetAddress[] addressTo = new InternetAddress[recipients.length];
        for (int i = 0; i < recipients.length; i++) {
        addressTo[i] = new InternetAddress(recipients[i]);
        }
        msg.setRecipients(Message.RecipientType.TO, addressTo);

        // Setting the Subject and Content Type
        msg.setSubject(subject);
        msg.setContent(message, "text/plain");
        Transport.send(msg);
    }
}