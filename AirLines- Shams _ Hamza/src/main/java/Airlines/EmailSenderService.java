/////////////////HEADER/////////////////
///Presentation Date: Jan 17 2023
///Partners: Shams, Hamza
///ISU | Air Hub Booking Agency Application

package Airlines;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMailWithAttachment(String toEmail, String body, String subject, String attachment) throws MessagingException {
        //Create Mime Object
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        //Create a Mime Helper Object
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
        //set the sender email 
        mimeMessageHelper.setFrom("agencymailer@gmail.com");
        //set to Email
        mimeMessageHelper.setTo(toEmail);
        //set body text
        mimeMessageHelper.setText(body);
        //set subject
        mimeMessageHelper.setSubject(subject);

        FileSystemResource fileSystemResource= new FileSystemResource(new File(attachment));
        //add file attachment
        mimeMessageHelper.addAttachment(fileSystemResource.getFilename(),fileSystemResource);
        javaMailSender.send(mimeMessage);
        //prompt to let you know it worked
        System.out.printf("Mail sent successfully..");

    }
}
