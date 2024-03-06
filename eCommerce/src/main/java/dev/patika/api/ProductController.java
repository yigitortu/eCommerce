package dev.patika.api;

import dev.patika.business.abstracts.ICategoryService;
import dev.patika.business.abstracts.IProductService;
import dev.patika.business.abstracts.ISupplierService;
import dev.patika.core.config.config.modelMapper.IModelMapperService;
import dev.patika.core.config.result.ResultData;
import dev.patika.core.config.utilies.ResultHelper;
import dev.patika.dto.request.product.ProductSaveRequest;
import dev.patika.dto.response.category.CategoryResponse;
import dev.patika.dto.response.product.ProductResponse;
import dev.patika.dto.response.supplier.SupplierResponse;
import dev.patika.entities.Category;
import dev.patika.entities.Product;
import dev.patika.entities.Supplier;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/products")
public class ProductController {
    private final IProductService productService;
    private final IModelMapperService modelMapper;
    // bağlantı oldugu için eklenen eksrta kodlar aşağıdadır.
    private final ICategoryService categoryService;
    private final ISupplierService supplierService;


    public ProductController(IProductService productService, IModelMapperService modelMapper, ICategoryService categoryService, ISupplierService supplierService) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
        this.supplierService = supplierService;
    }

    // Product OLUŞTURMA
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<ProductResponse> save(@Valid @RequestBody ProductSaveRequest productSaveRequest){
        Product saveProduct = this.modelMapper.forRequest().map(productSaveRequest, Product.class);

        Category category = this.categoryService.get(productSaveRequest.getCategoryId());
        saveProduct.setCategory(category);

        Supplier supplier = this.supplierService.get(productSaveRequest.getSupplierId());
        saveProduct.setSupplier(supplier);


        this.productService.save(saveProduct);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveProduct , ProductResponse.class));
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<ProductResponse> get(@PathVariable("id") int id){
        Product product = this.productService.get(id);

        return ResultHelper.success(this.modelMapper.forResponse().map(product,ProductResponse.class));
    }

}
