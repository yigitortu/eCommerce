package dev.patika.business.concrets;

import dev.patika.business.abstracts.ICategoryService;
import dev.patika.core.config.exception.NotFoundException;
import dev.patika.core.config.utilies.Msg;
import dev.patika.dao.CategoryRepo;
import dev.patika.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.NotActiveException;

@Service
public class CategoryManager implements ICategoryService {

    private final CategoryRepo categoryRepo;

    public CategoryManager(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    // Save
    @Override
    public Category save(Category category) {
        return this.categoryRepo.save(category);
    }


    // Listeleme
    @Override
    public Category get(int id) {
        return this.categoryRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    // Sayfalandırma.
    @Override
    public Page<Category> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        return this.categoryRepo.findAll(pageable);
    }
    // Update
    @Override
    public Category update(Category category) {
        this.get(category.getId());
        return this.categoryRepo.save(category);
    }

    @Override
    public boolean delete(int id) {
        Category category = this.get(id);
        this.categoryRepo.delete(category);
        return true;
    }
}
