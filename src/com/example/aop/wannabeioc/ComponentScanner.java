package com.example.aop.wannabeioc;

import com.example.aop.annotations.Auditable;
import com.example.aop.annotations.Validate;
import com.example.aop.aspects.Auditor;
import com.example.aop.aspects.Logger;
import com.example.aop.aspects.Validator;

import java.lang.reflect.Field;

public class ComponentScanner {
  private static final Auditor auditor = new Auditor();

  public static void scanAndAdvise(Object obj) throws Exception {
    Class<?> clazz = obj.getClass();

    if (clazz.isAnnotationPresent(Auditable.class)) {
      auditor.markForAudit(clazz);
    }
  }
}
