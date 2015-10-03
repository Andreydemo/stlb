package stlb;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Created by demos_000 on 03.10.2015.
 */
@Aspect
@Component
public class NotVeryUsefulAspect {

    public NotVeryUsefulAspect() {
        System.out.println("Aspects inited");
    }

    @Pointcut("execution(* perf*(..))")
    private void anyPublicOperation() {
        System.out.println("Aspect working");
    }


    @Before("execution(* perf*(..))")
    public void before() {
        System.out.println("Aspect working Before");
    }
    @After("execution(* perf*(..))")
    public void after() {
        System.out.println("Aspect working After");
    }

    @Around("execution(* perf*(..))")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("Aspect working Around Before");
        Object retVal = pjp.proceed();
        System.out.println("Aspect working Around After");
        return retVal;
    }
}
