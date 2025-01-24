package com.suitpay.product_manager.service.product;

import com.suitpay.product_manager.model.dto.ProductDTO;
import com.suitpay.product_manager.model.entity.ProductEntity;
import com.suitpay.product_manager.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<ProductDTO> getAllProducts() {
        List<ProductEntity> entities = productRepository.findAll();
        return entities.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Integer id) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
        return productMapper.toDto(entity);
    }

    public Page<ProductDTO> getProductByFilters(PageRequest pageRequest, String name, Double priceMin, Double priceMax) {
        Page<ProductEntity> pageEntity = productRepository.findAll(pageRequest);

        if (name != null) {
            pageEntity = productRepository.findByNameContaining(name, pageRequest);
        }

        if (priceMin != null && priceMax != null) {
            pageEntity = productRepository.findByPriceBetween(priceMin, priceMax, pageRequest);
        } else if (priceMin != null) {
            pageEntity = productRepository.findByPriceGreaterThanEqual(priceMin, pageRequest);
        } else if (priceMax != null) {
            pageEntity = productRepository.findByPriceLessThanEqual(priceMax, pageRequest);
        }

        return pageEntity.map(productMapper::toDto);
    }

    public List<ProductDTO> getProductsByCategory(Integer categoryId) {
        List<ProductEntity> products = productRepository.findByCategoryId(categoryId);
        return products.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDTO save(ProductDTO produtoDTO) {
        ProductEntity entity = productMapper.toEntity(produtoDTO);
        ProductEntity savedEntity = productRepository.save(entity);
        return productMapper.toDto(savedEntity);
    }

    public ProductDTO update(Integer id, ProductDTO produtoDTO) {
        ProductEntity existentEntity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));

        existentEntity.setName(produtoDTO.getName());
        existentEntity.setPrice(produtoDTO.getPrice());
        existentEntity.setDescription(produtoDTO.getDescription());
        existentEntity.setQuantity(produtoDTO.getQuantity());
        if (produtoDTO.getCategory() != null && produtoDTO.getCategory().getId() != null) {
            existentEntity.getCategory().setId(produtoDTO.getCategory().getId());
        }

        ProductEntity updatedEntity = productRepository.save(existentEntity);
        return productMapper.toDto(updatedEntity);
    }

    public void delete(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado com ID: " + id);
        }
        productRepository.deleteById(id);
    }
}
