package ftn.xml.autor.service;

import ftn.xml.autor.model.EmailDataDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    @Value(value = "${spring.mail.username}")
    private String sender;

    @Async
    public void sendEmail(EmailDataDTO emailDataDTO) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setText(emailDataDTO.getContent());
            helper.setTo(emailDataDTO.getRecipient());
            helper.setSubject(emailDataDTO.getSubject());
            helper.setFrom(sender);
            DataSource resenje = new FileDataSource(emailDataDTO.getDocumentPath());
            helper.addAttachment("Resenje_zahteva", resenje);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("failed to send email");
        }
    }


    public static EmailDataDTO buildEmailDTO(String email, String documentPath) {
        EmailDataDTO emailDataDTO = new EmailDataDTO();
        emailDataDTO.setRecipient(email);
        emailDataDTO.setDocumentPath(documentPath);
        emailDataDTO.setSubject("Resenje podnetog zahteva");
        emailDataDTO.setContent("Postovani,\nU prilogu Vam saljemo kopiju resenja podnetog zahteva\n\nSve najbolje\nVasa EUprava");
        return emailDataDTO;
    }

}
