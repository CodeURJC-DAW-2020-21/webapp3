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

    public void sendEmailRegister(String to)  {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mailMessage = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            String message = "<h1> Welcome to Growing! </h1>" +
                    "<p> Este correo sirve para poder confirmar tu identidad. Para ello ponga el enlace que puedes encontrar a continuación:<br>" +
                    "<a href =\"https://www.urjc.es\"> this will be a link </a><br>" +
                    "<strong>Gracias por confiar en nosotros</strong><br><br>Atentamente, el equipo de Growing <br><br> <p style=\"color: grey;\"</p>";
            mailMessage.setText(message, true);
            mailMessage.setFrom("dawequipo3sup@gmail.com");
            mailMessage.setTo(to);
            mailMessage.setSubject("Need to confirm your identity");
            javaMailSender.send(mimeMessage);
        }
        catch (MessagingException ignored){
        }
    }

    public void sendEmailHeight(String to, String category, int height){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("dawequipo3sup@gmail.com");
        mailMessage.setTo(to);
        mailMessage.setSubject("Congratulations");
        mailMessage.setText("We are delighted to tell you that you have achieved the increible score of "+height+"cm on the " +
                category+" category!\n Keep pushing mate!\n\nDo not try to reply this message\n\n\nGrowing team");
        javaMailSender.send(mailMessage);
    }

}
