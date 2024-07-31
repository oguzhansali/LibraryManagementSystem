package dev.patika.Library.Management.System.api;

import dev.patika.Library.Management.System.business.abstracts.IBookService;
import dev.patika.Library.Management.System.business.abstracts.ICategoryService;
import dev.patika.Library.Management.System.core.config.modelmapper.IModelMapperService;
import dev.patika.Library.Management.System.core.result.Result;
import dev.patika.Library.Management.System.core.result.ResultData;
import dev.patika.Library.Management.System.core.utillies.ResultHelper;
import dev.patika.Library.Management.System.dto.request.category.CategorySaveRequest;
import dev.patika.Library.Management.System.dto.request.category.CategoryUpdateRequest;
import dev.patika.Library.Management.System.dto.response.CursorResponse;
import dev.patika.Library.Management.System.dto.response.category.CategoryResponse;
import dev.patika.Library.Management.System.entities.Book;
import dev.patika.Library.Management.System.entities.Category;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final ICategoryService categoryService;
    private final IModelMapperService modelMapper;
    private final IBookService bookService;

    public CategoryController(ICategoryService categoryService, IModelMapperService modelMapper, IBookService bookService) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.bookService = bookService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<CategoryResponse> save (@Valid @RequestBody CategorySaveRequest categorySaveRequest){
        Category saveCategory = this.modelMapper.forRequest().map(categorySaveRequest,Category.class);
        this.categoryService.save(saveCategory);

        return ResultHelper.created(this.modelMapper.forResponse().map(saveCategory,CategoryResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CategoryResponse> get (@PathVariable("id")int id) {
        Category category = this.categoryService.get(id);
        CategoryResponse categoryResponse = this.modelMapper.forResponse().map(category, CategoryResponse.class);
        return ResultHelper.success(categoryResponse);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<CategoryResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ) {
        Page<Category> categoryPage = this.categoryService.cursor(page, pageSize);
        Page<CategoryResponse> categoryResponsePage = categoryPage
                .map(category -> this.modelMapper.forResponse().map(category, CategoryResponse.class));

        return ResultHelper.cursor(categoryResponsePage);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CategoryResponse> update (@Valid @RequestBody CategoryUpdateRequest categoryUpdateRequest){
        this.categoryService.get(categoryUpdateRequest.getId());
        Category updateCategory = this.modelMapper.forRequest().map(categoryUpdateRequest,Category.class);
        this.categoryService.update(updateCategory);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateCategory,CategoryResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") int id) {
        this.categoryService.delete(id);
        return ResultHelper.ok();
    }

}
