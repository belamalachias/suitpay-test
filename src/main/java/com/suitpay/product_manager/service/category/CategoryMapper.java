package com.suitpay.product_manager.service.category;

import com.suitpay.product_manager.model.dto.CategoryDTO;
import com.suitpay.product_manager.model.dto.ProductDTO;
import com.suitpay.product_manager.model.entity.CategoryEntity;
import com.suitpay.product_manager.model.entity.ProductEntity;
import com.suitpay.product_manager.service.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper extends BaseMapper<CategoryEntity, CategoryDTO> {

    @Override
    public CategoryDTO toDto(CategoryEntity entity) {
        if (entity == null) return null;
        return CategoryDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    @Override
    public CategoryEntity toEntity(CategoryDTO dto) {
        if (dto == null) return null;
        CategoryEntity entity = new CategoryEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }
}
