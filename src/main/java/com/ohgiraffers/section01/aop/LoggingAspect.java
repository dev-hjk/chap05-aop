package com.ohgiraffers.section01.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.Map;

@Aspect
@Component
public class LoggingAspect {

    //@Pointcut: 여러 조인포인트를 매치하기 위해 지정한 표현식,  joinpoint 의 집합, pointcut 표현식
    //execution: ([수식어] 리턴타입 [클래스이름]. 이름(파라미터))
    //리턴타입 부분에 *을 쓰면 어떤 타입이어도 상관은 없다~!라는 의미!
    // 수식어 생략 가능 (public, private, protected, default)
    @Pointcut("execution(* com.ohgiraffers.section01.aop.*Service.*(..))")
    public void logPointcut() {}

    //advice 리턴 타입을 void 로 작성하고, 매개변수는 JoinPoint joinPoint 로 작성! joinPoint는 타켓메소드! 이 정보를 넣어서 해당 내용을 쓸 수 있게~!
    @Before("logPointcut()")
    public void logBefore(JoinPoint joinPoint) {

        System.out.println("Before joinPoint.getTarget() : " + joinPoint.getTarget());
        System.out.println("Before joinPoint.getSignature() : " + joinPoint.getSignature());
        if(joinPoint.getArgs().length > 0) {
            System.out.println("Before joinPoint.getArgs()[0] : " + joinPoint.getArgs()[0]);
        }
    }

    @After("logPointcut()")
    public void logAfter(JoinPoint joinPoint) {

        System.out.println("After joinPoint.getTarget() : " + joinPoint.getTarget());
        System.out.println("After joinPoint.getSignature() : " + joinPoint.getSignature());
        if(joinPoint.getArgs().length > 0) {
            System.out.println("After joinPoint.getArgs()[0] : " + joinPoint.getArgs()[0]);
        }
    }
    //
    //속성들을 여러개 써야 할 때는 무조건 앞에 속성을 써줘야함!
    //@AfterReturning :
    @AfterReturning(pointcut = "logPointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        // Object result :result가 같아야 함
        System.out.println("AfterReturning result : " + result);

        if(result != null && result instanceof Map) {
            ((Map<Long, MemberDTO>) result).put(100L, new MemberDTO(100L, "반환값 가공"));
        }
    }

}
