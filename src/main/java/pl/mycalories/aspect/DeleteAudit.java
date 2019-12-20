package pl.mycalories.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class DeleteAudit {

    @Pointcut(value = "execution(public * pl.mycalories.service.*.delete(..))")
    public void deleteLog() {
    }

    @Around("deleteLog()")
    public Object logAspect(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("Delete service called: " + pjp.getTarget());
        return pjp.proceed();
    }
}
