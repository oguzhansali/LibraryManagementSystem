package dev.patika.Library.Management.System.api;

import dev.patika.Library.Management.System.business.abstracts.IBookBorrowingService;
import dev.patika.Library.Management.System.business.abstracts.IBookService;
import dev.patika.Library.Management.System.core.config.modelmapper.IModelMapperService;
import dev.patika.Library.Management.System.core.result.Result;
import dev.patika.Library.Management.System.core.result.ResultData;
import dev.patika.Library.Management.System.core.utillies.ResultHelper;
import dev.patika.Library.Management.System.dto.request.bookBorrowing.BookBorrowingSaveRequest;
import dev.patika.Library.Management.System.dto.request.bookBorrowing.BookBorrowingUpdateRequest;
import dev.patika.Library.Management.System.dto.response.CursorResponse;
import dev.patika.Library.Management.System.dto.response.bookBorrowing.BookBorrowingResponse;
import dev.patika.Library.Management.System.entities.BookBorrowing;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookBorrowings")
public class BookBorrowingController {
    private final IBookBorrowingService bookBorrowingService;
    private final IModelMapperService modelMapper;
    private final IBookService bookService;

    public BookBorrowingController(IBookBorrowingService bookBorrowingService,
                                   IModelMapperService modelMapper,
                                   IBookService bookService) {
        this.bookBorrowingService = bookBorrowingService;
        this.modelMapper = modelMapper;
        this.bookService = bookService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<BookBorrowingResponse> save (@Valid @RequestBody BookBorrowingSaveRequest bookBorrowingSaveRequest){
        BookBorrowing saveBookBorrowing = this.modelMapper.forRequest().map(bookBorrowingSaveRequest,BookBorrowing.class);
        this.bookBorrowingService.save(saveBookBorrowing);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveBookBorrowing,BookBorrowingResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<BookBorrowingResponse> get (@PathVariable("id") int id){
        BookBorrowing bookBorrowing = this.bookBorrowingService.get(id);
        BookBorrowingResponse bookBorrowingResponse = this.modelMapper.forResponse().map(bookBorrowing,BookBorrowingResponse.class);
        return ResultHelper.success(bookBorrowingResponse);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<BookBorrowingResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ) {
        Page<BookBorrowing> bookBorrowingPage = this.bookBorrowingService.cursor(page, pageSize);
        Page<BookBorrowingResponse> bookBorrowingResponsePage = bookBorrowingPage
                .map(bookBorrowing -> this.modelMapper.forResponse().map(bookBorrowing, BookBorrowingResponse.class));

        return ResultHelper.cursor(bookBorrowingResponsePage);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<BookBorrowingResponse> update (@Valid @RequestBody BookBorrowingUpdateRequest bookBorrowingUpdateRequest){
        this.bookBorrowingService.get(bookBorrowingUpdateRequest.getId());
        BookBorrowing updateBookBorrowing = this.modelMapper.forRequest().map(bookBorrowingUpdateRequest,BookBorrowing.class);
        this.bookBorrowingService.update(updateBookBorrowing);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateBookBorrowing,BookBorrowingResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id")int id){
        this.bookBorrowingService.delete(id);
        return ResultHelper.ok();
    }











}
