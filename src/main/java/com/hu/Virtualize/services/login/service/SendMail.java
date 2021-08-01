package com.hu.Virtualize.services.login.service;

public interface SendMail {
    boolean sendEmail(String subject, String message, String to);
}
