package com.pgs.mailservice.service;

import com.pgs.mailservice.payload.MailRequest;

import javax.mail.MessagingException;

public interface MailService {
    void sendMail(MailRequest mailRequest) throws MessagingException;
}
