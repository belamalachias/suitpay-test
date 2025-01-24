import com.fasterxml.jackson.databind.ObjectMapper;
import com.suitpay.product_manager.config.TestSecurityConfig;
import com.suitpay.product_manager.controller.product.ProductController;
import com.suitpay.product_manager.model.dto.CategoryDTO;
import com.suitpay.product_manager.model.dto.ProductDTO;
import com.suitpay.product_manager.service.product.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@ContextConfiguration(classes = ProductController.class)
@Import(TestSecurityConfig.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllProducts() throws Exception {
        CategoryDTO category = new CategoryDTO(1L, "Category 1");
        ProductDTO product1 = ProductDTO.builder()
                .id(1L)
                .name("Product 1")
                .price(10.0)
                .quantity(5)
                .category(category)
                .build();
        ProductDTO product2 = ProductDTO.builder()
                .id(2L)
                .name("Product 2")
                .price(15.0)
                .quantity(8)
                .category(category)
                .build();

        Mockito.when(productService.getAllProducts())
                .thenReturn(Arrays.asList(product1, product2));

        mockMvc.perform(get("/api/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Product 1"));
    }

    @Test
    void testGetProductById() throws Exception {
        CategoryDTO category = new CategoryDTO(1L, "Category 1");
        ProductDTO product = ProductDTO.builder()
                .id(1L)
                .name("Product 1")
                .price(10.0)
                .quantity(5)
                .category(category)
                .build();

        Mockito.when(productService.getProductById(1))
                .thenReturn(product);

        mockMvc.perform(get("/api/product/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Product 1"));
    }

    @Test
    void testSaveProduct() throws Exception {
        CategoryDTO category = new CategoryDTO(1L, "Category 1");
        ProductDTO product = ProductDTO.builder()
                .name("New Product")
                .price(20.0)
                .quantity(10)
                .category(category)
                .build();
        ProductDTO savedProduct = ProductDTO.builder()
                .id(1L)
                .name("New Product")
                .price(20.0)
                .quantity(10)
                .category(category)
                .build();

        Mockito.when(productService.save(any(ProductDTO.class)))
                .thenReturn(savedProduct);

        mockMvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New Product"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        CategoryDTO category = new CategoryDTO(2L, "Updated Category");
        ProductDTO updatedProduct = ProductDTO.builder()
                .id(1L)
                .name("Updated Product")
                .price(30.0)
                .quantity(15)
                .category(category)
                .build();

        Mockito.when(productService.update(anyInt(), any(ProductDTO.class)))
                .thenReturn(updatedProduct);

        mockMvc.perform(put("/api/product/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Product"));
    }


    @Test
    void testDeleteProduct() throws Exception {
        mockMvc.perform(delete("/api/product/1"))
                .andExpect(status().isNoContent());
    }
}
