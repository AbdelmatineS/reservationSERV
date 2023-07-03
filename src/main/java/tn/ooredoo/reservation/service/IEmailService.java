package tn.ooredoo.reservation.service;

import javax.mail.MessagingException;

public interface IEmailService {
    public void sendEmail(String to, String subject, String body) throws MessagingException;
}
