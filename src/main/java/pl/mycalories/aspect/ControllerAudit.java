package pl.mycalories.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ControllerAudit {

    @Pointcut(value = "execution(public * pl.mycalories.controller.*.*(..))")
    public void controllerLogAspect() {
    }

    @Pointcut(value = "execution(public * pl.mycalories.controller.AbstractController.*(..))")
    public void abstractController() {
    }

    @Around("controllerLogAspect()")
    public Object logAspect(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println(pjp.getTarget() + " has been invoked");
        return pjp.proceed();
    }
}
