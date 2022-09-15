package com.disneydemo.demo.service.implementation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

@Service
public class MailService {

    @Value("${yourapp.http.auth-token}")
    private String apiKey;
    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    public String sendTextEmail(String receiver) throws IOException {
        // the sender email should be the same as we used to Create a Single Sender Verification
        Email from = new Email("francortiz.c@gmail.com");
        String subject = "Welcome";
        Email to = new Email(receiver);
        Content content = new Content("text/plain", "Welcome to the Disney App!");
        Mail mail = new Mail(from, subject, to, content);


        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            logger.info(response.getBody());
            return response.getBody();
        } catch (IOException ex) {
            throw ex;
        }
    }
}
