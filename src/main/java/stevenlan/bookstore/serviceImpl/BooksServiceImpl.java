package stevenlan.bookstore.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import stevenlan.bookstore.dto.BooksDto;
import stevenlan.bookstore.dto.BooksRequest;
import stevenlan.bookstore.dto.BooksResponse;
import stevenlan.bookstore.entity.Books;

import stevenlan.bookstore.repository.BooksRepository;
import stevenlan.bookstore.service.BooksService;

@Service
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {

    private final BooksRepository booksRepository;

    private final EmployeesServiceImpl employeesService;

    @Override
    public BooksResponse getBooksByIds(BooksRequest booksRequest) {
        if (booksRequest.getBooksId().isEmpty() || booksRequest.getBooksId() == null) {
            List<BooksDto> AllBooks = new ArrayList<>();
            for (Books book : booksRepository.findAll()) {
                AllBooks.add(booksToBooksDto(book));
            }
            return new BooksResponse(
                    employeesService.tokenGenerate(booksRequest.getRequest()),
                    AllBooks, "資料查詢完畢");
        }
        List<BooksDto> booksDto = new ArrayList<>();
        String nonExistId = "";
        for (Long id : booksRequest.getBooksId()) {
            if (booksRepository.findById(id).isPresent()) {
                booksDto.add(booksToBooksDto(booksRepository.findById(id).orElseThrow()));
            } else {
                nonExistId += id;
                nonExistId += ", ";
            }
        }
        if (!nonExistId.isBlank()) {
            return new BooksResponse(
                    employeesService.tokenGenerate(booksRequest.getRequest()),
                    booksDto, "查詢完畢, id為 " + nonExistId + "的資料不存在");
        } else {
            return new BooksResponse(
                    employeesService.tokenGenerate(booksRequest.getRequest()),
                    booksDto, "查詢完畢");
        }
    }

    private BooksDto booksToBooksDto(Books book) {
        return BooksDto.builder().id(book.getId()).title(book.getTitle()).author(book.getAuthor())
                .description(book.getDescription()).listPrice(book.getListPrice())
                .salePrice(book.getSalePrice()).build();
    }

    private Books booksDtoToBooks(BooksDto bookDto) {
        return Books.builder().title(bookDto.getTitle()).author(bookDto.getAuthor())
                .description(bookDto.getDescription()).listPrice(bookDto.getListPrice())
                .salePrice(bookDto.getSalePrice()).build();
    }

    @Override
    @PreAuthorize("hasAuthority('BOOK_MANAGER')")
    public BooksResponse addNewBooks(BooksRequest booksRequest) {
        if (booksRepository.findBooksByTitle(booksRequest.getBooks().getTitle()).isPresent()) {
            return new BooksResponse(null,
                    Arrays.asList(booksRequest.getBooks()), "此書本已存在");
        }
        booksRepository.save(booksDtoToBooks(booksRequest.getBooks()));
        return new BooksResponse(
                employeesService.tokenGenerate(booksRequest.getRequest()),
                Arrays.asList(booksToBooksDto(
                        booksRepository.findBooksByTitle(booksRequest.getBooks().getTitle()).orElseThrow())),
                "新增完成");
    }

    @Override
    @PreAuthorize("hasAuthority('BOOK_MANAGER')")
    public BooksResponse deleteBooks(BooksRequest booksRequest) {
        if (booksRequest.getBooksId().isEmpty()) {
            return new BooksResponse(null, null, "請輸入欲刪除的書本id");
        }
        String nonExistId = "";
        for (Long id : booksRequest.getBooksId()) {
            if (booksRepository.existsById(id)) {
                booksRepository.deleteById(id);
            } else {
                nonExistId += id;
                nonExistId += ", ";
            }
        }
        if (!nonExistId.isBlank()) {
            return new BooksResponse(
                    employeesService.tokenGenerate(booksRequest.getRequest()),
                    null, "刪除完畢, id為 " + nonExistId + "的資料不存在");
        } else {
            return new BooksResponse(
                    employeesService.tokenGenerate(booksRequest.getRequest()),
                    null, "刪除完畢");
        }
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('BOOK_MANAGER')")
    public BooksResponse updateBooks(BooksRequest booksRequest) {
        if (booksRequest.getBooksId().isEmpty()) {
            return new BooksResponse(null, null, "請輸入欲更新的書本id");
        }
        Books book = booksRepository.findById(booksRequest.getBooksId().get(0)).orElse(null);
        if (book == null) {
            return new BooksResponse(
                    employeesService.tokenGenerate(booksRequest.getRequest()),
                    null, "更新失敗, 此id之資料不存在");
        } else {
            BooksDto newBookDto = booksRequest.getBooks();
            if (newBookDto.getTitle() != null && newBookDto.getTitle().length() > 0
                    && !Objects.equals(book.getTitle(), newBookDto.getTitle())) {
                book.setTitle(newBookDto.getTitle());
            }
            if (newBookDto.getAuthor() != null && newBookDto.getAuthor().length() > 0
                    && !Objects.equals(book.getAuthor(), newBookDto.getAuthor())) {
                book.setAuthor(newBookDto.getAuthor());
            }
            if (newBookDto.getDescription() != null && newBookDto.getDescription().length() > 0
                    && !Objects.equals(book.getDescription(), newBookDto.getDescription())) {
                book.setDescription(newBookDto.getDescription());
            }
            if (newBookDto.getListPrice() != null && newBookDto.getListPrice() > 0
                    && !Objects.equals(book.getListPrice(), newBookDto.getListPrice())) {
                book.setListPrice(newBookDto.getListPrice());
            }
            if (newBookDto.getSalePrice() != null && newBookDto.getSalePrice() > 0
                    && !Objects.equals(book.getSalePrice(), newBookDto.getSalePrice())) {
                book.setSalePrice(newBookDto.getSalePrice());
            }
            return new BooksResponse(
                    employeesService.tokenGenerate(booksRequest.getRequest()),
                    null, "更新完畢");
        }
    }
}