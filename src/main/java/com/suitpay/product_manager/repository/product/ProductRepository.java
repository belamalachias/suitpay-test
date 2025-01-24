package com.suitpay.product_manager.repository.product;

import com.suitpay.product_manager.model.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    @Query("SELECT p FROM ProductEntity p WHERE p.name LIKE %:name%")
    Page<ProductEntity> findByNameContaining(@Param("name") String name, PageRequest pageRequest);

    @Query("SELECT p FROM ProductEntity p WHERE p.price BETWEEN :priceMin AND :priceMax")
    Page<ProductEntity> findByPriceBetween(@Param("priceMin") Double priceMin, @Param("priceMax") Double priceMax, PageRequest pageRequest);

    @Query("SELECT p FROM ProductEntity p WHERE p.category.id = :categoryId")
    List<ProductEntity> findByCategoryId(@Param("categoryId") Integer categoryId);

    @Query("SELECT p FROM ProductEntity p WHERE p.price >= :priceMin")
    Page<ProductEntity> findByPriceGreaterThanEqual(@Param("priceMin") Double priceMin, Pageable pageable);

    @Query("SELECT p FROM ProductEntity p WHERE p.price <= :priceMax")
    Page<ProductEntity> findByPriceLessThanEqual(@Param("priceMax") Double priceMax, Pageable pageable);

}
