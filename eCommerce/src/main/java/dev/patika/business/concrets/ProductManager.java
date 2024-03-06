package dev.patika.business.concrets;

import dev.patika.business.abstracts.IProductService;
import dev.patika.core.config.exception.NotFoundException;
import dev.patika.core.config.utilies.Msg;
import dev.patika.dao.ProductRepo;
import dev.patika.entities.Category;
import dev.patika.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductManager implements IProductService {

    private final ProductRepo productRepo;

    public ProductManager(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }


    @Override
    public Product save(Product product) {
        return this.productRepo.save(product);
    }

    @Override
    public Product get(int id) {
        return this.productRepo.findById(id).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public Page<Product> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);

        return this.productRepo.findAll(pageable);
    }

    @Override
    public Product update(Product product) {
        this.get(product.getId());
        return this.productRepo.save(product);
    }

    @Override
    public boolean delete(int id) {
        Product product = this.get(id);
        this.productRepo.delete(product);
        return true;
    }
}
