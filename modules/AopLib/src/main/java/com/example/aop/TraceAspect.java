package com.example.aop;

import android.util.Log;
import android.view.View;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * creator huangyong
 * createTime 2018/11/7 上午11:49
 * path com.example.gintonic
 * description:
 */
@Aspect
public class TraceAspect {


    public static final String TAG = "Statistics";


    /**
     * 示例1：埋点统计
     * @param joinPoint
     * @param statistics
     * @throws Throwable
     */
    @Around("execution(@Statistics * *(..)) && @annotation(statistics)")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint, Statistics statistics) throws Throwable {
        calculate(statistics);
        joinPoint.proceed();//执行原方法
    }

    private void calculate(Statistics statistics){
        if(statistics != null){
            Log.e(TAG, "对" + statistics.function().getFunctionName() + "进行统计");
        }
    }


    /**
     * 示例2：检查权限
     *
     * description：方法运行前先检查权限，如果有权限，执行原有方法，并返回其结果
     * 如果没有权限，返回自定义的结果。
     */
    @Pointcut("execution(@AspectJAnnotation  * *(..))")
    public void executionAspectJ() {

    }

    @Around("executionAspectJ()")
    public Object aroundAspectJ(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Log.e(TAG, "检查权限aroundAspectJ(ProceedingJoinPoint joinPoint)");
        AspectJAnnotation aspectJAnnotation = methodSignature.getMethod().getAnnotation(AspectJAnnotation.class);
        String permission = aspectJAnnotation.value();
        if(permission.equals("权限A")) {
            Object result=joinPoint.proceed();
            Log.e(TAG, "有权限："+permission);
            return result;
        }
        return "没有权限";
    }

    /**
     * 对activity的所有on开头的方法打点，并获取其类名
     * @param joinPoint
     */
    @Before("execution(* android.app.Activity+.on**(..))")
    public void onClickBefore(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Log.e(TAG, "对onresume" + "进行统计"+method.getDeclaringClass().getSimpleName()+"-----"+method.getName());
    }

    /**
     * 对目标路径的方法打点,并获取入参
     * @param joinPoint
     */
    @Before("execution(* dev.baofeng.com.supermovie.view.MovieDetailActivity.clicked**(..))")
    public void onTestBefore(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        Log.e(TAG, "onTestBefore进行统计"+method.getDeclaringClass().getSimpleName()+"-----"+method.getName()+"---"+Strings.toString(joinPoint.getArgs())
        );
    }
}
