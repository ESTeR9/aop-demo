package com.example.aop.core;

import com.example.aop.annotations.*;

public class MyService implements MyServiceInterface {
  @Autowired
  private EmailServiceInterface emailService;

  public MyService(EmailServiceInterface emailService) {
    this.emailService = emailService;
  }

  public MyService(){
  }

  @LogExecution
  public void process(@Validate String email) {
    emailService.sendEmail(email);
  }
}
