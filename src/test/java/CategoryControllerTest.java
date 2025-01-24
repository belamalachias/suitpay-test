import com.fasterxml.jackson.databind.ObjectMapper;
import com.suitpay.product_manager.config.TestSecurityConfig;
import com.suitpay.product_manager.controller.category.CategoryController;
import com.suitpay.product_manager.model.dto.CategoryDTO;
import com.suitpay.product_manager.service.category.CategoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@ContextConfiguration(classes = CategoryController.class)
@Import(TestSecurityConfig.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllCategories() throws Exception {
        Mockito.when(categoryService.getAllCategories())
                .thenReturn(Arrays.asList(new CategoryDTO(1L, "Category 1"), new CategoryDTO(2L, "Category 2")));

        mockMvc.perform(get("/api/category")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Category 1"));
    }

    @Test
    void testGetCategoryById() throws Exception {
        Mockito.when(categoryService.getCategoryById(1))
                .thenReturn(new CategoryDTO(1L, "Category 1"));

        mockMvc.perform(get("/api/category/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Category 1"));
    }

    @Test
    void testSaveCategory() throws Exception {
        CategoryDTO category = new CategoryDTO(null, "New Category");
        CategoryDTO savedCategory = new CategoryDTO(1L, "New Category");

        Mockito.when(categoryService.save(any(CategoryDTO.class)))
                .thenReturn(savedCategory);

        mockMvc.perform(post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New Category"));
    }

    @Test
    void testUpdateCategory() throws Exception {
        CategoryDTO updatedCategory = new CategoryDTO(1L, "Updated Category");

        Mockito.when(categoryService.update(anyInt(), any(CategoryDTO.class)))
                .thenReturn(updatedCategory);

        mockMvc.perform(put("/api/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCategory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Category"));
    }

    @Test
    void testDeleteCategory() throws Exception {
        mockMvc.perform(delete("/api/category/1"))
                .andExpect(status().isNoContent());
    }
}
