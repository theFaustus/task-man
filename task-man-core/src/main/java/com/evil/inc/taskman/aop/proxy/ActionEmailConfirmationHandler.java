package com.evil.inc.taskman.aop.proxy;

import com.evil.inc.taskman.annotations.ActionEmailConfirmation;
import com.evil.inc.taskman.entity.Email;
import com.evil.inc.taskman.service.EmailService;
import com.evil.inc.taskman.service.ServiceFactory;
import com.evil.inc.taskman.service.UserService;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
public class ActionEmailConfirmationHandler implements InvocationHandler {
    private final UserService target;
    private final EmailService emailService = ServiceFactory.getInstance().getEmailService();

    public ActionEmailConfirmationHandler(final UserService target) {
        this.target = target;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        if (method.isAnnotationPresent(ActionEmailConfirmation.class)) {
            final ActionEmailConfirmation annotation = method.getAnnotation(ActionEmailConfirmation.class);
            log.info("Sending email to {}", Arrays.toString(annotation.email()));
            log.info("Action [{}] from {} with {} completed successfully", method.getName(),
                     proxy.getClass().getSimpleName(), Arrays.toString(args));
            final Object result = method.invoke(target, args);
            final String content = String.format(
                    "<p>Action <b>[%s]</b> with following arguments <b>%s</b> and following result <b>[%s]</b> completed successfully</p>",
                    method.getName(), Arrays.toString(args), result);
            Arrays.stream(annotation.email()).forEach(
                    e -> emailService.sendEmail(new Email(e, "task-man action completed", content)));
            return result;
        }
        return method.invoke(target, args);
    }
}
