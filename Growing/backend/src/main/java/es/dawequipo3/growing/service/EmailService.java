package es.dawequipo3.growing.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmailRegister(String to){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("dawequipo3sup@gmail.com");
        mailMessage.setTo(to);
        mailMessage.setSubject("Need to confirm your identity");
        mailMessage.setText("We need to confirm your data. Click on the link to proceed: \n");
        javaMailSender.send(mailMessage);
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
