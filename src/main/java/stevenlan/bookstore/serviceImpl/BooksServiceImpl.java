package stevenlan.bookstore.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import stevenlan.bookstore.dto.BooksDto;
import stevenlan.bookstore.dto.BooksIdsRequest;
import stevenlan.bookstore.dto.BooksRequestDto;
import stevenlan.bookstore.dto.BooksResponse;
import stevenlan.bookstore.dto.BooksUpdateRequestDto;
import stevenlan.bookstore.entity.Books;
import stevenlan.bookstore.jwt.service.AuthenticationService;
import stevenlan.bookstore.repository.BooksRepository;
import stevenlan.bookstore.service.BooksService;

@Service
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {

    private static final Logger logger = Logger.getLogger(AuthenticationService.class.getName());

    private final BooksRepository booksRepository;

    @Override
    public BooksResponse getBooksByIds(BooksIdsRequest ids) {
        //判斷ids有沒有值 預計移至dto判斷
        if (ids.getBooksIds().isEmpty() || ids.getBooksIds() == null) {
            //若無輸入 則視為查詢全部
            List<BooksDto> AllBooks = new ArrayList<>();
            for (Books book : booksRepository.findAll()) {
                AllBooks.add(booksToBooksDto(book));
            }
            return new BooksResponse(null, AllBooks, "查詢完畢");
        }
        List<BooksDto> booksDto = new ArrayList<>();
        //紀錄無效id之訊息 思考能否挪出為一個方法
        String nonExistId = "";
        for (Long id : ids.getBooksIds()) {
            if (booksRepository.findById(id).isPresent()) {
                booksDto.add(booksToBooksDto(booksRepository.findById(id).orElseThrow()));
            } else {
                nonExistId += id;
                nonExistId += ", ";
            }
        }
        if (!nonExistId.isBlank()) {
            return new BooksResponse(null,
                    booksDto, "查詢完畢, id為 " + nonExistId + "的資料不存在");
        } else {
            return new BooksResponse(null,
                    booksDto, "查詢完畢");
        }
    }

    private BooksDto booksToBooksDto(Books book) {
        return BooksDto.builder().id(book.getId()).title(book.getTitle()).author(book.getAuthor())
                .description(book.getDescription()).listPrice(book.getListPrice())
                .salePrice(book.getSalePrice()).build();
    }

    private Books booksDtoToBooks(BooksRequestDto bookDto) {
        return Books.builder().title(bookDto.getTitle()).author(bookDto.getAuthor())
                .description(bookDto.getDescription()).listPrice(bookDto.getListPrice())
                .salePrice(bookDto.getSalePrice()).build();
    }

    @Override
    @PreAuthorize("hasAuthority('BOOK_MANAGER')")
    public BooksResponse addNewBooks(BooksRequestDto booksRequest) {
        //判斷是否已存在書本(同名)
        Optional<Books> presentBook = booksRepository.findBooksByTitle(booksRequest.getTitle());
        if (presentBook.isPresent()) {
            return new BooksResponse(null,
                    Arrays.asList(booksToBooksDto(presentBook.orElseThrow())), "此書本已存在");
        }
        logger.info(booksRequest.toString());
        booksRepository.save(booksDtoToBooks(booksRequest));
        return new BooksResponse(
                null,
                Arrays.asList(booksToBooksDto(
                    //藉由查詢順便驗證是否成功存入 日後考慮做好unit test後簡化程式碼方便閱讀
                    booksRepository.findBooksByTitle(booksRequest.getTitle()).orElseThrow())),
                "新增完成");
    }

    @Override
    @PreAuthorize("hasAuthority('BOOK_MANAGER')")
    public BooksResponse deleteBooks(BooksIdsRequest booksRequest) {
        //同上
        if (booksRequest.getBooksIds().isEmpty()) {
            return new BooksResponse(null, null, "請輸入欲刪除的書本id");
        }
        String nonExistId = "";
        for (Long id : booksRequest.getBooksIds()) {
            if (booksRepository.existsById(id)) {
                booksRepository.deleteById(id);
            } else {
                nonExistId += id;
                nonExistId += ", ";
            }
        }
        if (!nonExistId.isBlank()) {
            return new BooksResponse(
                    null, null, "id為 " + nonExistId + "的資料不存在, 其餘刪除完畢");
        } else {
            return new BooksResponse(
                    null, null, "刪除完畢");
        }
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('BOOK_MANAGER')")
    public BooksResponse updateBooks(BooksUpdateRequestDto booksRequest) {
        // if (booksRequest.getBookIds().) {
        //     return new BooksResponse(null, null, "請輸入欲更新的書本id");
        // }

        //應該能簡化 之後試
        Books book = booksRepository.findById(booksRequest.getBookId()).orElse(null);
        if (book == null) {
            return new BooksResponse(
                    null,
                    null, "更新失敗, 此id之資料不存在");
        } else {
            //應該移到dto做判斷
            BooksRequestDto newBookDto = booksRequest.getBooksRequestDto();
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
                    null,
                    Arrays.asList(booksToBooksDto(book)), "更新完畢");
        }
    }
}