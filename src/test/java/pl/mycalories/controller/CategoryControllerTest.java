package pl.mycalories.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import pl.mycalories.CalorieeCApplication;
import pl.mycalories.model.Category;
import pl.mycalories.service.CategoryService;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CalorieeCApplication.class)
@WebAppConfiguration
public class CategoryControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CategoryService categoryServiceMock;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void should_CreateAccount_When_ValidRequest() throws Exception {
        Category category = new Category();
        category.setId(123L);
        category.setName("Category Test");
        when(categoryServiceMock.save(any(Category.class))).thenReturn(category);

        mockMvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"Category Test\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.name").value("Category Test"));
    }

    @Test
    public void should_GetCategory_When_ValidRequest() throws Exception {
        Category category = new Category();
        category.setId(1L);
        category.setName("Category Test");
        when(categoryServiceMock.findById(1L)).thenReturn(category);

        mockMvc.perform(get("/category/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Category Test"));
    }

}
