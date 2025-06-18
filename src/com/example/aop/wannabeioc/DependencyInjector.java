package com.example.aop.wannabeioc;

import com.example.aop.annotations.Autowired;
import com.example.aop.core.EmailService;
import com.example.aop.core.EmailServiceInterface;
import com.example.aop.core.MyService;
import com.example.aop.core.MyServiceInterface;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DependencyInjector {
  private static final Map<Class<?>, Object> beans = new HashMap<>();

  private static final Map<Class<?>, Class<?>> interfaceToImpl = new HashMap<>();

  // Register interface to implementation mapping .. this can also be done dynamically at app startup ... spring IOC does this
  static {
    interfaceToImpl.put(MyServiceInterface.class, MyService.class);
    interfaceToImpl.put(EmailServiceInterface.class, EmailService.class);
  }

  public static <T> T getBean(Class<T> clazz) throws Exception {
    if (clazz.isInterface()) {
      clazz = (Class<T>) interfaceToImpl.get(clazz);
    }
    if (beans.containsKey(clazz)) {
      return clazz.cast(beans.get(clazz));
    }
    T instance = clazz.getDeclaredConstructor().newInstance();
    for (Field field : clazz.getDeclaredFields()) {
      if (field.isAnnotationPresent(Autowired.class)) {
        field.setAccessible(true);
        Object dependency = getBean(field.getType());
        Object proxy = AOPProxy.createProxy(dependency);
        field.set(instance, proxy);
      }
    }
    beans.put(clazz, instance);
    ComponentScanner.scanAndAdvise(instance);
    return instance;
  }
}
