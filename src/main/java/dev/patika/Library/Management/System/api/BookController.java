package dev.patika.Library.Management.System.api;

import dev.patika.Library.Management.System.business.abstracts.IAuthorService;
import dev.patika.Library.Management.System.business.abstracts.IBookService;
import dev.patika.Library.Management.System.core.config.modelmapper.IModelMapperService;
import dev.patika.Library.Management.System.core.result.Result;
import dev.patika.Library.Management.System.core.result.ResultData;
import dev.patika.Library.Management.System.core.utillies.ResultHelper;
import dev.patika.Library.Management.System.dto.request.book.BookSaveRequest;
import dev.patika.Library.Management.System.dto.request.book.BookUpdateRequest;
import dev.patika.Library.Management.System.dto.response.CursorResponse;
import dev.patika.Library.Management.System.dto.response.book.BookResponse;
import dev.patika.Library.Management.System.entities.Author;
import dev.patika.Library.Management.System.entities.Book;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {
    private final IModelMapperService modelMapper;
    private final IBookService bookService;
    private final IAuthorService authorService;


    public BookController(IModelMapperService modelMapper, IBookService bookService, IAuthorService authorService) {
        this.modelMapper = modelMapper;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<BookResponse> save(@Valid @RequestBody BookSaveRequest bookSaveRequest){
        Book saveBook = this.modelMapper.forRequest().map(bookSaveRequest,Book.class);
        //İLlgili Author bilgilerini alır
        Author author = this.authorService.get(bookSaveRequest.getAuthorId());
        saveBook.setAuthor(author);
        //Book kaydeder
        this.bookService.save(saveBook);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveBook,BookResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<BookResponse> get(@PathVariable("id") int id){
        Book book = this.bookService.get(id);
        BookResponse bookResponse = this.modelMapper.forResponse().map(book,BookResponse.class);
        return ResultHelper.success(bookResponse);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<BookResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ) {
        Page<Book> bookPage = this.bookService.cursor(page, pageSize);
        Page<BookResponse> bookResponsePage = bookPage
                .map(book -> this.modelMapper.forResponse().map(book, BookResponse.class));

        return ResultHelper.cursor(bookResponsePage);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<BookResponse> update(@Valid @RequestBody BookUpdateRequest bookUpdateRequest){
        this.bookService.get(bookUpdateRequest.getId());
        Book updateBook = this.modelMapper.forRequest().map(bookUpdateRequest,Book.class);
        this.bookService.update(updateBook);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateBook,BookResponse.class));

    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") int id) {
        this.bookService.delete(id);
        return ResultHelper.ok();
    }


}
