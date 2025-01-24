package com.suitpay.product_manager.repository.category;

import com.suitpay.product_manager.model.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
}
