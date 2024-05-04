package com.example.aswe.project;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.web.servlet.ModelAndView;

import com.example.aswe.project.controllers.UserController;
import com.example.aswe.project.models.Product;
import com.example.aswe.project.repositories.productRepository;

public class UserTest {
    
    @Test
    public void testGetProducts() {
        // Mock data
        List<Product> mockProducts = new ArrayList<>();
        mockProducts.add(new Product("Product 1", 10.0));
        mockProducts.add(new Product("Product 2", 20.0));

        // Mock repository behavior
        ListCrudRepository<Product, Integer> productRepository = Mockito.mock(ListCrudRepository.class);
        when(productRepository.findAll()).thenReturn(mockProducts);

        // Call the method         
        productRepository productRepo = Mockito.mock(productRepository.class);
        UserController userController = new UserController();
        userController.setProductRepository(productRepo);
        ModelAndView mav = userController.getproducts();

        //Assert view name
        assertEquals("html/user/list-products.html", mav.getViewName());

        // Assert data
        List<Product> returnedProducts = (List<Product>) mav.getModel().get("products");
        assertEquals(mockProducts.size(), returnedProducts.size());
        for (int i = 0; i < mockProducts.size(); i++) {
            assertEquals(mockProducts.get(i).getProductName(), returnedProducts.get(i).getProductName());
            assertEquals(mockProducts.get(i).getProductPrice(), returnedProducts.get(i).getProductPrice());
        }
    }
}
