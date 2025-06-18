package com.example.aop.wannabeioc;

import com.example.aop.annotations.Auditable;
import com.example.aop.annotations.LogExecution;
import com.example.aop.annotations.Validate;
import com.example.aop.aspects.Validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

public class AOPProxy implements InvocationHandler {
  private final Object target;
  private final Validator validator = new Validator();

  public AOPProxy(Object target) {
    this.target = target;
  }

  public static Object createProxy(Object target) {
    return Proxy.newProxyInstance(
        target.getClass().getClassLoader(),
        target.getClass().getInterfaces(),
        new AOPProxy(target)
    );
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    Method realMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());

    Annotation[][] paramAnnotations = realMethod.getParameterAnnotations();
    for (int i = 0; i < paramAnnotations.length; i++) {
      for (Annotation annotation : paramAnnotations[i]) {
        if (annotation instanceof Validate) {
          Object arg = args[i];
          validator.validateEmail(arg.toString());
        }
      }
    }

    handleBefore(realMethod, method);
    Object result = realMethod.invoke(target, args);
    handleAfter(realMethod, method);

    return result;
  }

  private void handleBefore(Method realMethod, Method method) {
    if (realMethod.isAnnotationPresent(LogExecution.class)) {
      System.out.println("[LOG] Before method: " + method.getName());
    }
    if (realMethod.isAnnotationPresent(Auditable.class)){
      System.out.println("[AUDIT] Invoked: " + method.getName());
    }
  }

  private void handleAfter(Method realMethod, Method method) {
    if (realMethod.isAnnotationPresent(LogExecution.class)) {
      System.out.println("[LOG] After method: " + method.getName());
    }
  }
}
