package com.justsayyes.record.service;

import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Map;

@Service
public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
}
