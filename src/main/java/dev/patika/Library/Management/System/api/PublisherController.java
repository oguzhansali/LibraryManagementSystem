package dev.patika.Library.Management.System.api;

import dev.patika.Library.Management.System.business.abstracts.IBookService;
import dev.patika.Library.Management.System.business.abstracts.IPublisherService;
import dev.patika.Library.Management.System.core.config.modelmapper.IModelMapperService;
import dev.patika.Library.Management.System.core.result.Result;
import dev.patika.Library.Management.System.core.result.ResultData;
import dev.patika.Library.Management.System.core.utillies.ResultHelper;
import dev.patika.Library.Management.System.dto.request.publisher.PublisherSaveRequest;
import dev.patika.Library.Management.System.dto.request.publisher.PublisherUpdateRequest;
import dev.patika.Library.Management.System.dto.response.CursorResponse;
import dev.patika.Library.Management.System.dto.response.publisher.PublisherResponse;
import dev.patika.Library.Management.System.entities.Book;
import dev.patika.Library.Management.System.entities.Publisher;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherController {
    private final IPublisherService publisherService;
    private final IModelMapperService modelMapper;
    private final IBookService bookService;

    public PublisherController(IPublisherService publisherService, IModelMapperService modelMapper, IBookService bookService) {
        this.publisherService = publisherService;
        this.modelMapper = modelMapper;
        this.bookService = bookService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<PublisherResponse> save (@Valid @RequestBody PublisherSaveRequest publisherSaveRequest){
        Publisher savePublisher = this.modelMapper.forRequest().map(publisherSaveRequest,Publisher.class);

        this.publisherService.save(savePublisher);
        return ResultHelper.created(this.modelMapper.forResponse().map(savePublisher,PublisherResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<PublisherResponse> get (@PathVariable("id")int id){
        Publisher publisher = this.publisherService.get(id);
        PublisherResponse publisherResponse = this.modelMapper.forResponse().map(publisher,PublisherResponse.class);
        return ResultHelper.success(publisherResponse);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<PublisherResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ) {
        Page<Publisher> publisherPage = this.publisherService.cursor(page, pageSize);
        Page<PublisherResponse> publisherResponsePage = publisherPage
                .map(publisher -> this.modelMapper.forResponse().map(publisher, PublisherResponse.class));

        return ResultHelper.cursor(publisherResponsePage);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<PublisherResponse> update(@Valid @RequestBody PublisherUpdateRequest publisherUpdateRequest){
        this.publisherService.get(publisherUpdateRequest.getId());
        Publisher updatePublisher = this.modelMapper.forRequest().map(publisherUpdateRequest,Publisher.class);
        this.publisherService.update(updatePublisher);
        return ResultHelper.success(this.modelMapper.forResponse().map(updatePublisher,PublisherResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") int id) {
        this.publisherService.delete(id);
        return ResultHelper.ok();
    }



}
