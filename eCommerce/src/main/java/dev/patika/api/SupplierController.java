package dev.patika.api;


import dev.patika.business.abstracts.ISupplierService;
import dev.patika.core.config.config.modelMapper.IModelMapperService;
import dev.patika.core.config.result.Result;
import dev.patika.core.config.result.ResultData;
import dev.patika.core.config.utilies.ResultHelper;
import dev.patika.dto.request.category.CategorySaveRequest;
import dev.patika.dto.request.supplier.SupplierSaveRequest;
import dev.patika.dto.response.CursorResponse;
import dev.patika.dto.response.category.CategoryResponse;
import dev.patika.dto.response.supplier.SupplierResponse;
import dev.patika.dto.response.supplier.SupplierUpdateRequest;
import dev.patika.entities.Category;
import dev.patika.entities.Supplier;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/suppliers")
public class SupplierController {

    private final ISupplierService supplierService;
    private final IModelMapperService modelMapper;


    public SupplierController(ISupplierService supplierService, IModelMapperService modelMapper) {
        this.supplierService = supplierService;
        this.modelMapper = modelMapper;
    }

    // Supplier OLUŞTURMA
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<SupplierResponse> save(@Valid @RequestBody SupplierSaveRequest supplierSaveRequest){
        Supplier saveSupplier = this.modelMapper.forRequest().map(supplierSaveRequest, Supplier.class);
        this.supplierService.save(saveSupplier);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveSupplier,SupplierResponse.class));
    }

    // UPDATE
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<SupplierResponse> update(@Valid @RequestBody SupplierUpdateRequest supplierUpdateRequest){
        Supplier updateSupplier = this.modelMapper.forRequest().map(supplierUpdateRequest, Supplier.class);
        this.supplierService.update(updateSupplier);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateSupplier,SupplierResponse.class));
    }


    // Supplier SİLME
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") int id){
        this.supplierService.delete(id);
        return ResultHelper.ok();
    }

    // Supplier GETİRME
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<SupplierResponse> get(@PathVariable("id") int id){
        Supplier supplier = this.supplierService.get(id);
        SupplierResponse supplierResponse = this.modelMapper.forResponse().map(supplier,SupplierResponse.class);
        return ResultHelper.success(supplierResponse);
    }

    // Sayfa olarak getirme

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<SupplierResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ){
        Page<Supplier> supplierPage = this.supplierService.cursor(page,pageSize);
        Page<SupplierResponse> supplierResponsePage = supplierPage
                .map(supplier -> this.modelMapper.forResponse().map(supplier,SupplierResponse.class));

        return  ResultHelper.cursor(supplierResponsePage);
    }


}
