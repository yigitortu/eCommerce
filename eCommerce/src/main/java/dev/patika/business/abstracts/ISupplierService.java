package dev.patika.business.abstracts;

import dev.patika.entities.Supplier;
import org.springframework.data.domain.Page;

public interface ISupplierService {
    Supplier save(Supplier supplier);

    Supplier get(int id);
    Page<Supplier> cursor(int page, int pageSize);

    Supplier update(Supplier supplier);

    boolean delete(int id);
}
