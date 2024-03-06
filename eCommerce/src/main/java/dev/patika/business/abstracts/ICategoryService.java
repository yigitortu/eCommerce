package dev.patika.business.abstracts;

import dev.patika.entities.Category;
import org.springframework.data.domain.Page;

public interface ICategoryService {

    Category save(Category category);

    Category get(int id);
    Page<Category> cursor(int page,int pageSize);

    Category update(Category category);

    boolean delete(int id);

}
