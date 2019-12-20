package pl.mycalories.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
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

//    @Before("execution(public * pl.mycalories.controller.*.*(..))")
//    public void logAspect(JoinPoint joinPoint) {
//        System.out.println(joinPoint.getTarget() + " has been invoked");
//    }
}
