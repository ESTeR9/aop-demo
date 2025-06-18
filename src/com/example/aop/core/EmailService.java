package com.example.aop.core;

import com.example.aop.annotations.Auditable;
import com.example.aop.annotations.LogExecution;

@Auditable
public class EmailService implements EmailServiceInterface {
  //sendEmail method would just print email sent to email param
  @Override
  @LogExecution
  @Auditable
  public void sendEmail(String email) {
    System.out.println("Email sent to: " + email);
  }
}
