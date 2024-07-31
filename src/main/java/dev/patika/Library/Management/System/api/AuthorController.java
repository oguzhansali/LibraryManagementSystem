package dev.patika.Library.Management.System.api;

import dev.patika.Library.Management.System.business.abstracts.IAuthorService;
import dev.patika.Library.Management.System.core.config.modelmapper.IModelMapperService;
import dev.patika.Library.Management.System.core.result.Result;
import dev.patika.Library.Management.System.core.result.ResultData;
import dev.patika.Library.Management.System.core.utillies.ResultHelper;
import dev.patika.Library.Management.System.dto.request.author.AuthorSaveRequest;
import dev.patika.Library.Management.System.dto.request.author.AuthorUpdateRequest;
import dev.patika.Library.Management.System.dto.response.author.AuthorResponse;
import dev.patika.Library.Management.System.dto.response.CursorResponse;
import dev.patika.Library.Management.System.entities.Author;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final IAuthorService authorService;
    private IModelMapperService modelMapper;

    public AuthorController(IAuthorService authorService, IModelMapperService modelMapper) {
        this.authorService = authorService;
        this.modelMapper = modelMapper;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AuthorResponse> save(@Valid @RequestBody AuthorSaveRequest authorSaveRequest){
        //Author verilerini Dto'dan Entity'ye dönüştürür
        Author saveAuthor = this.modelMapper.forRequest().map(authorSaveRequest,Author.class);
        //AAuthor kaydeder.
        this.authorService.save(saveAuthor);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveAuthor,AuthorResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public  ResultData<AuthorResponse> get(@PathVariable("id" )int id){
        Author author = this.authorService.get(id);
        AuthorResponse authorResponse=this.modelMapper.forResponse().map(author,AuthorResponse.class);
        return ResultHelper.success(authorResponse);
    }

    //Author sayfalı listesini alır
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AuthorResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ) {
        Page<Author> authorPage = this.authorService.cursor(page, pageSize);
        Page<AuthorResponse> authorResponsePage = authorPage
                .map(author -> this.modelMapper.forResponse().map(author, AuthorResponse.class));

        return ResultHelper.cursor(authorResponsePage);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AuthorResponse> update(@Valid @RequestBody AuthorUpdateRequest authorUpdateRequest){
        this.authorService.get(authorUpdateRequest.getId());
        Author updateAuthor = this.modelMapper.forRequest().map(authorUpdateRequest,Author.class);
        this.authorService.update(updateAuthor);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateAuthor,AuthorResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") int id) {
        this.authorService.delete(id);
        return ResultHelper.ok();
    }
}
