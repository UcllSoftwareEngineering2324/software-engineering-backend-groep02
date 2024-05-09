package be.ucll.se.groep02backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.List;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmailWithEmbeddedImages(String to, String subject, String htmlContent, List<String> imagePaths) throws MessagingException, IOException {
        jakarta.mail.internet.MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("carrentalucll@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);

        // Set HTML content with embedded images
        helper.setText(htmlContent, true);

        // Add each image as an embedded resource
        for (String imagePath : imagePaths) {
            FileSystemResource imageResource = new FileSystemResource(imagePath);
            helper.addInline(imageResource.getFilename(), imageResource);
        }

        // Send the email
        javaMailSender.send(message);
    }
}
