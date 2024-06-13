package stevenlan.bookstore.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import stevenlan.bookstore.dto.BooksResponse;
import stevenlan.bookstore.dto.EmployeesResponse;
import stevenlan.bookstore.jwt.service.AuthenticationService;

@Aspect
@Component
@RequiredArgsConstructor
public class GenerateTokenAspect {

    private final AuthenticationService authenticationService;

    //delete自己帳號就不用給token了
    @Around("execution(* stevenlan.bookstore.controller.*.*(..)) && !execution(* stevenlan.bookstore.controller.EmployeesController.deleteEmployees(..))")
    public Object modifyReturnValue(ProceedingJoinPoint joinPoint) throws Throwable {

        Object result = joinPoint.proceed();

        ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
        Object body = responseEntity.getBody();

        //判斷不同回傳型態來賦值
        if (body instanceof EmployeesResponse) {

            EmployeesResponse employeesResponse = (EmployeesResponse) body;
            employeesResponse.setJwtToken(authenticationService.tokenGenerateTokenFromContextholder());

            return ResponseEntity.ok(employeesResponse);
        } else if (body instanceof BooksResponse) {

            BooksResponse bookResponse = (BooksResponse) body;
            bookResponse.setToken(authenticationService.tokenGenerateTokenFromContextholder());

            return ResponseEntity.ok(bookResponse);
        }
        return result;
    }
}
