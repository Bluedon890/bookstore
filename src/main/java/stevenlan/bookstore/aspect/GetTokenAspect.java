package stevenlan.bookstore.aspect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import stevenlan.bookstore.dto.BooksRequest;
import stevenlan.bookstore.dto.BooksResponse;
import stevenlan.bookstore.entity.Books;

@Aspect
@Component
public class GetTokenAspect {

    private static final ThreadLocal<BooksRequest> booksRequestThreadLocal = new ThreadLocal<>();

    @Pointcut("execution(* src.main.java.stevenlan.bookstore.controller.*(..))")
    public void pointcut(){}

    @Before("execution(* stevenlan.bookstore.controller.BooksController.*(..))")
    public void booksRequestGenerate(){

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();

            List<Long> bookIds = getRequestParamValue(request, "bookIds");

            Books books = getRequestBodyValue(request);
            System.out.println("這呢" + bookIds);
            System.out.println(books);

            BooksRequest booksRequest = new BooksRequest(books, request, bookIds);
            booksRequestThreadLocal.set(booksRequest);
        }
    }

    public static BooksRequest getBooksRequestFromThreadLocal() {
        return booksRequestThreadLocal.get();
    }

    public static void clearThreadLocal() {
        booksRequestThreadLocal.remove();
    }
    
    private List<Long> getRequestParamValue(HttpServletRequest request, String paramName) {
        String[] values = request.getParameterValues(paramName);
    
        List<Long> result = new ArrayList<>();
        if (values == null) {
            return result;
        }
        for (String value : values) {
            if(value!=null && !value.isBlank()){
                Long id = Long.parseLong(value);
                result.add(id);
            }
        }
        return result;
    }

    private Books getRequestBodyValue(HttpServletRequest request) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(request.getInputStream(), Books.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResponseEntity<BooksResponse> executeService(Function<BooksRequest, BooksResponse> serviceMethod) {
        BooksRequest booksRequest = getBooksRequestFromThreadLocal();
        BooksResponse booksResponse = serviceMethod.apply(booksRequest);
        clearThreadLocal();
        return ResponseEntity.ok(booksResponse);
    }
} 

