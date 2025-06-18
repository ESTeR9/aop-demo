package com.example.aop.core;

import java.util.Scanner;

import com.example.aop.annotations.Autowired;

public class App {

  @Autowired
  private MyServiceInterface service;

  public App(MyServiceInterface service) {
    this.service = service;
  }

  public App() {
  }

  public void run() {
    Scanner scanner = new Scanner(System.in);
    while (true) {
      try {
        System.out.print("Enter email (or 'exit' to quit): ");
        String input = scanner.nextLine();
        if ("exit".equalsIgnoreCase(input)) {
          break;
        }
        service.process(input);
      } catch (Exception e) {
        System.err.println("Error: " + e.getMessage());
      }
    }
  }
}
