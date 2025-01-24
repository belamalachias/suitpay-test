package com.suitpay.product_manager.service.category;

import com.suitpay.product_manager.model.dto.CategoryDTO;
import com.suitpay.product_manager.model.entity.CategoryEntity;
import com.suitpay.product_manager.repository.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryDTO> getAllCategories() {
        List<CategoryEntity> entities = categoryRepository.findAll();
        return entities.stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(Integer id) {
        CategoryEntity entity = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrado com ID: " + id));
        return categoryMapper.toDto(entity);
    }

    public CategoryDTO save(CategoryDTO produtoDTO) {
        CategoryEntity entity = categoryMapper.toEntity(produtoDTO);
        CategoryEntity savedEntity = categoryRepository.save(entity);
        return categoryMapper.toDto(savedEntity);
    }

    public CategoryDTO update(Integer id, CategoryDTO produtoDTO) {
        CategoryEntity entityExistente = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
        entityExistente.setName(produtoDTO.getName());
        CategoryEntity updatedEntity = categoryRepository.save(entityExistente);
        return categoryMapper.toDto(updatedEntity);
    }

    public void delete(Integer id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado com ID: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
