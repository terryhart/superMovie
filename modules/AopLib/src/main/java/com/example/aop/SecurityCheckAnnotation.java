package com.example.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * creator huangyong
 * createTime 2018/11/7 下午3:05
 * path com.example.gintonic
 * description:
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SecurityCheckAnnotation {
    String declaredPermission();
}
