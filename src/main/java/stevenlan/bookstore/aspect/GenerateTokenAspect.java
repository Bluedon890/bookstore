package stevenlan.bookstore.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import stevenlan.bookstore.dto.BooksResponse;
import stevenlan.bookstore.dto.EmployeesResponse;
import stevenlan.bookstore.serviceImpl.EmployeesServiceImpl;

@Aspect
@Component
@RequiredArgsConstructor
public class GenerateTokenAspect {

    private final EmployeesServiceImpl employeesServiceImpl;

    @Around("execution(* stevenlan.bookstore.controller.*.*(..))")
    public Object modifyReturnValue(ProceedingJoinPoint joinPoint) throws Throwable {

        // ResponseEntity<EmployeesResponse> responseEntity =
        // (ResponseEntity<EmployeesResponse>) joinPoint.proceed();

        // EmployeesResponse employeesResponse = responseEntity.getBody();

        // employeesResponse.setJwtToken(employeesServiceImpl.tokenGenerateNew());

        // // 將修改後的 EmployeesResponse 包裹到 ResponseEntity 中並返回
        // return ResponseEntity.ok(employeesResponse);

        Object result = joinPoint.proceed();

        ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
        Object body = responseEntity.getBody();

        if (body instanceof EmployeesResponse) {

            EmployeesResponse employeesResponse = (EmployeesResponse) body;
            employeesResponse.setJwtToken(employeesServiceImpl.tokenGenerateNew());

            return ResponseEntity.ok(employeesResponse);
        } else if (body instanceof BooksResponse) {

            BooksResponse bookResponse = (BooksResponse) body;
            bookResponse.setToken(employeesServiceImpl.tokenGenerateNew());

            return ResponseEntity.ok(bookResponse);
        }
        return result;
    }
}
