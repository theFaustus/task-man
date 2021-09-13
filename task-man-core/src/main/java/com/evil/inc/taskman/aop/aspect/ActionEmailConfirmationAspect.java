package com.evil.inc.taskman.aop.aspect;

import com.evil.inc.taskman.annotations.ActionEmailConfirmation;
import com.evil.inc.taskman.annotations.AspectActionEmailConfirmation;
import com.evil.inc.taskman.annotations.UserCreatedEmailConfirmation;
import com.evil.inc.taskman.entity.Email;
import com.evil.inc.taskman.service.EmailService;
import com.evil.inc.taskman.service.ServiceFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.Arrays;

@Aspect
public class ActionEmailConfirmationAspect {
    private static final Logger log = LoggerFactory.getLogger(ActionEmailConfirmationAspect.class);
    private static final EmailService emailService = ServiceFactory.getInstance().getEmailService();

    @Around("@annotation(com.evil.inc.taskman.annotations.AspectActionEmailConfirmation)")
    public Object emailConfirmation(ProceedingJoinPoint joinPoint) throws Throwable {
        final Object[] args = joinPoint.getArgs();
        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        final String methodName = signature.getName();
        final AspectActionEmailConfirmation annotation = signature.getMethod().getAnnotation(
                AspectActionEmailConfirmation.class);
        final Object result = joinPoint.proceed();
        final String content = String.format(
                "<p>Action <b>[%s]</b> with following arguments <b>%s</b> and following result <b>[%s]</b> completed successfully</p>",
                methodName, Arrays.toString(args), result);
        Arrays.stream(annotation.email()).forEach(
                e -> emailService.sendEmail(new Email(e, "task-man action completed", content)));
        return result;
    }

//    @Around("execution(* com.evil.inc.taskman.service.impl..*.*(..)) && @args(com.evil.inc.taskman.annotations.UserCreatedEmailConfirmation)")
//    public Object userCreationEmailConfirmation(ProceedingJoinPoint joinPoint) throws Throwable {
//        final Object[] args = joinPoint.getArgs();
//        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        final String methodName = signature.getName();
//        if(methodName.equals("create")) {
//            Parameter[] parameters = signature.getMethod().getParameters();
//            for (Parameter parameter : parameters) {
//                UserCreatedEmailConfirmation annotation = parameter.getDeclaringExecutable().getAnnotation(UserCreatedEmailConfirmation.class);
//                if (annotation != null) {
//                    final Object result = joinPoint.proceed();
//                    final String content = String.format(
//                            "<p>Action <b>[%s]</b> with following arguments <b>%s</b> and following result <b>[%s]</b> completed successfully</p>",
//                            methodName, Arrays.toString(args), result);
//                    Arrays.stream(annotation.email()).forEach(
//                            e -> emailService.sendEmail(new Email(e, "task-man action completed", content)));
//                    return result;
//                }
//            }
//        }
//        return joinPoint.proceed();
//    }
}
