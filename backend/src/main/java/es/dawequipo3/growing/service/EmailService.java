package es.dawequipo3.growing.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmailRegister(String to){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mailMessage = new MimeMessageHelper(mimeMessage,"utf-8");
        try {
            String message = "<section style=\"border-style:groove; border-color: #12a294; align-self: center\">"+
                    "<div style=\"text-align: center; margin: 25px;\">"+
                    "</div>"+
                    "<div style=\"background-color: #12a292; text-align: center; margin: 25px; border-radius: 8px\">"+
                    "<h1 style=\"padding-top: 20px; color: white; font-size: 35px\">Confirm your email</h1>"+
                    "<p style=\"padding: 5px; color: white; font-size: 25px\">"+
                    "Welcome to Growing! Thank you very much for trusting us.<br> With Growing you will achieve your goals and see your progress.<br>"+
                    "To confirm your email, click the button below:<br>"+
                    "<a href =\"https://localhost:8443/\" style=\"padding: 10px; border-radius: 10px; background-color: white; margin: 10px\">Confirm my account →</a>"+
                    "<br>"+
                    "Sincerely, the team of Growing <br><br>"+
                    "</p>"+
                    "</div>"+
                    "<div>"+
                    "<p  style=\"text-align: center; margin: 25px;\">Remember, Growing will never ask" +
                    " you for bank details or your credit or debit card details by email. <br>If you" +
                    " receive such an email, delete it immediately. <br>In addition, we recommend that you" +
                    " change your access password regularly.</p>"+
                    "</div>"+
                    "</section>";

            mailMessage.setText(message, true);
            mailMessage.setFrom("dawequipo3sup@gmail.com");
            mailMessage.setTo(to);
            mailMessage.setSubject("Need to confirm your identity");
            javaMailSender.send(mimeMessage);
        }
        catch (MessagingException ignored){
        }
    }

    public void sendEmailHeight(String to, String category, int height) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mailMessage = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            String message = "<section style=\"border-style:groove; border-color: #12a294; align-self: center\">" +
                    "<div style=\"text-align: center; margin: 25px;\">" +
                    "<img alt= \"GROWING\" onclick=\"/\" src='cid:image'/>" +
                    "</div>" +
                    "<div style=\"background-color: #12a292; text-align: center; margin: 25px; border-radius: 8px\">" +
                    "<h1 style=\"padding-top: 20px; color: white; font-size: 35px\">Confirm your email</h1>" +
                    "<p style=\"padding: 5px; color: white; font-size: 25px\">" +
                    "Congratulations! We are delighted to inform you recently achieved the incredible height of "+ height +" cm" +
                    "on the next category: "+category+" Keep pushing! Big fan of your progress.<br>" +
                    "Sincerely, the team of Growing <br><br>" +
                    "</p>" +
                    "</div>" +
                    "<div>" +
                    "<p  style=\"text-align: center; margin: 25px;\">Remember, Growing will never ask" +
                    " you for bank details or your credit or debit card details by email. <br>If you" +
                    " receive such an email, delete it immediately. <br>In addition, we recommend that you" +
                    " change your access password regularly.</p>" +
                    "</div>" +
                    "</section>";

            mailMessage.setText(message, true);
            mailMessage.setFrom("dawequipo3sup@gmail.com");
            mailMessage.setTo(to);
            mailMessage.setSubject("Congratulations!");
            javaMailSender.send(mimeMessage);
        } catch (MessagingException ignored) {
        }
    }
}
