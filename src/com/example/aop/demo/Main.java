package com.example.aop.demo;

import com.example.aop.wannabeioc.AOPProxy;
import com.example.aop.wannabeioc.ComponentScanner;

public class Main {
  public static void main(String[] args) throws Exception {

    EmailService emailService = new EmailService();
    ComponentScanner.scanAndAdvise(emailService);
    EmailServiceInterface emailServiceProxy = (EmailServiceInterface) AOPProxy.createProxy(emailService);

    MyService service = new MyService(emailServiceProxy);
    ComponentScanner.scanAndAdvise(service);
    MyServiceInterface serviceProxy = (MyServiceInterface) AOPProxy.createProxy(service);
    App app = new App(serviceProxy);

    // ***** magic below ****
    // App app = DependencyInjector.getBean(App.class);

    app.run();
  }
}
