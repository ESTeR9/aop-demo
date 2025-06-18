package com.example.aop.aspects;

public class Auditor {
  //audit method would just print that the given class is marked for auditing
  public void markForAudit(Class<?> clazz) {
    System.out.println("Auditing class: " + clazz.getName());
  }

  public void auditMethodExecution(String methodName, Object[] args) {
    System.out.println("Executing method: " + methodName + " with arguments: " + java.util.Arrays.toString(args));
  }
}
