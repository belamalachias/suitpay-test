package com.suitpay.product_manager.service.product;

import com.suitpay.product_manager.model.dto.ProductDTO;
import com.suitpay.product_manager.model.entity.CategoryEntity;
import com.suitpay.product_manager.model.entity.ProductEntity;
import com.suitpay.product_manager.service.BaseMapper;
import com.suitpay.product_manager.service.category.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper extends BaseMapper<ProductEntity, ProductDTO> {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public ProductDTO toDto(ProductEntity entity) {
        if (entity == null) return null;
        return ProductDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .description(entity.getDescription())
                .quantity(entity.getQuantity())
                .category(categoryMapper.toDto(entity.getCategory()))
                .build();
    }

    @Override
    public ProductEntity toEntity(ProductDTO dto) {
        if (dto == null) return null;
        ProductEntity entity = new ProductEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());
        entity.setQuantity(dto.getQuantity());
        entity.setCategory(categoryMapper.toEntity(dto.getCategory()));
        return entity;
    }
}
