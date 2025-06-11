package by.northdakota.productservice.Repository;

import by.northdakota.productservice.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsBySku(String sku);

    @Query("""
            select p from Product p
            where p.id In :ids
            """)
    List<Product> findExistingProductByProductsId(List<Long> ids);
}
