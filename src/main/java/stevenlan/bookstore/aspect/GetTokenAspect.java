package stevenlan.bookstore.aspect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import stevenlan.bookstore.dto.BooksDto;
import stevenlan.bookstore.dto.BooksRequest;
import stevenlan.bookstore.dto.BooksResponse;

@Aspect
@Component
// 是不需要的
public class GetTokenAspect {

    private static final ThreadLocal<BooksRequest> booksRequestThreadLocal = new ThreadLocal<>();

    @Before("execution(* stevenlan.bookstore.controller.BooksController.*(..))")
    public void booksRequestGenerate(JoinPoint joinPoint) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {

            HttpServletRequest request = attributes.getRequest();

            List<Long> bookIds = getRequestParamValue(request, "bookIds");

            BooksDto booksDto = getRequestBodyValue(request);

            BooksRequest booksRequest = new BooksRequest(booksDto, request, bookIds);
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
        String unSplitValue = request.getParameter(paramName);
        List<Long> result = new ArrayList<>();
        if (unSplitValue == null) {
            return result;
        }
        String[] values = unSplitValue.split(",");

        for (String value : values) {
            if (value != null && !value.isBlank()) {
                Long id = Long.parseLong(value);
                result.add(id);
            }
        }
        return result;
    }

    private BooksDto getRequestBodyValue(HttpServletRequest request) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(request.getInputStream(), BooksDto.class);
        } catch (IOException e) {

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
