package stevenlan.bookstore.aspect;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import stevenlan.bookstore.dto.BooksRequest;
import stevenlan.bookstore.entity.Books;

@Aspect
@Component
public class GetTokenAspect {

    @Pointcut("execution(* src.main.java.stevenlan.bookstore.controller.*(..))")
    public void pointcut(){}

    @Before("execution(* stevenlan.bookstore.controller.BooksController.*(..))")
    public void booksRequestGenerate(){

            
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        
        // 取得 @RequestParam 和 @RequestBody 的值
        String bookIdParam = request.getParameter("bookId");
        List<Long> bookId = bookIdParam != null ? Arrays.stream(bookIdParam.split(",")).map(Long::parseLong).collect(Collectors.toList()) : null;
        
        // 取得 @RequestBody 的值
        Books books = null;
        try {
            books = new ObjectMapper().readValue(request.getReader(), Books.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // 創建 BooksRequest 對象
        BooksRequest booksRequest = new BooksRequest(books, request, bookId);
    }
}
