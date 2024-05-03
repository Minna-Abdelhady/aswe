package com.example.aswe.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import org.assertj.core.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.example.aswe.project.models.Product;
import com.example.aswe.project.repositories.productRepository;

public class UserTest {
    
    // @Test
    // public void testGetProducts() {
    //     // Mocking the ProductRepository
    //     productRepository productRepositorymock = mock(productRepository.class);
    //     when(productRepositorymock.findAll().thenReturn(Arrays.asList(new Product(), new Product())));
        
    //     // Creating the controller instance
    //     ProductController controller = new ProductController(productRepositorymock);

    //     ModelAndView mav = controller.getproducts();
    //     assertNotNull(mav);
    //     assertEquals("html/user/list-products.html", mav.getViewName());
    //     assertNotNull(mav.getModel().get("products"));
    //     assertEquals(2, ((List<Product>) mav.getModel().get("products")).size());
    // }
}
