package com.example.aswe.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import com.example.aswe.project.models.productDto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.aswe.project.controllers.adminController;
import com.example.aswe.project.controllers.ProductController; 

import com.example.aswe.project.models.Product;
import com.example.aswe.project.models.User;
import com.example.aswe.project.models.UserType;
import com.example.aswe.project.models.productDto;
import com.example.aswe.project.repositories.UserRepository;
import com.example.aswe.project.repositories.adminRepository;
import com.example.aswe.project.repositories.productRepository;

@RunWith(MockitoJUnitRunner.class)
public class AdminTest<ProductRepository> {
  

    @InjectMocks
    private ProductController productController;
    @Mock
    private adminRepository adminRepo;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private adminController adminCont;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test the User CRUD

    @Test
    public void testAddUser() {
        ModelAndView mav = adminCont.addUser();
        assertNotNull(mav);
        assertEquals("/html/admin/add-user.html", mav.getViewName());
        assertNotNull(mav.getModel().get("user"));
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setFName("Minna");
        user.setLName("Hany");
        user.setEmail("minna.hany@email.com");
        user.setPassword("password");
        UserType userType = new UserType();
        userType.setId(2); // id 2 corresponds to User type
        userType.setName(UserType.TYPE_USER);
        user.setType(userType);

        when(userRepository.save(user)).thenReturn(user);

        RedirectView redirectView = adminCont.saveUser(user);
        assertNotNull(redirectView);
        assertEquals("/Admin/List-Users", redirectView.getUrl());

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        User savedUser = userRepository.findById(user.getId()).orElse(null);
        assertNotNull(savedUser);
        assertEquals("Minna", savedUser.getFName());
        assertEquals("Hany", savedUser.getLName());
    }

    @Test
    public void testEditUser() {
        // Step 1: Mock the original user returned by findByid
        User originalUser = new User();
        originalUser.setId(1);
        originalUser.setFName("Minna");
        originalUser.setLName("Hany");
        originalUser.setEmail("minna.hany@gmail.com");
        originalUser.setPassword("password");

        // Mock findByid to return the originalUser
        when(userRepository.findByid(1)).thenReturn(originalUser);

        // Step 2: Prepare the updated user details
        User updatedUser = new User();
        updatedUser.setFName("Sara");
        updatedUser.setLName("Hany");
        updatedUser.setEmail("sara.hany@gmail.com");
        updatedUser.setPassword("newpassword");

        // Mock the save method to return the updated user
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // Step 4: Call the saveProfile method
        RedirectView redirectView = adminCont.saveProfile(1, updatedUser);
        assertNotNull(redirectView);
        assertEquals("/Admin/List-Users", redirectView.getUrl());

        // Step 5: Verify the updated user details
        User savedUser = userRepository.findByid(1);
        assertNotNull(savedUser);
        assertEquals("Sara", savedUser.getFName());
        assertEquals("Hany", savedUser.getLName());
        assertEquals("sara.hany@gmail.com", savedUser.getEmail());
    }

    @Test
    public void testDeleteUserAccount() {
        User user = new User();
        user.setId(1);
        user.setFName("Minna");
        user.setLName("Hany");
        user.setEmail("minna.hany@gmail.com");
        user.setPassword("password");

        // Mock the findById method to return the user
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        RedirectView redirectView = adminCont.deleteUserAccount(1);
        assertEquals("/Admin/List-Users", redirectView.getUrl());

        // Mock the findById method to return empty after the user is deleted
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        User deletedUser = userRepository.findById(1).orElse(null);
        assertNull(deletedUser);
    }

    // Test the Admin CRUD

    @Test
    public void testAddAdmin() {
        ModelAndView mav = adminCont.addAdmin();
        assertNotNull(mav);
        assertEquals("/html/admin/add-admin.html", mav.getViewName());
        assertNotNull(mav.getModel().get("admin"));
    }

    @Test
    public void testSaveAdmin() {
        User admin = new User();
        admin.setFName("Admin");
        admin.setLName("Admin1");
        admin.setEmail("Admin.Admin1@email.com");
        admin.setPassword("password");
        UserType adminType = new UserType();
        // id 1 corresponds to Admin type
        adminType.setId(1); 
        adminType.setName(UserType.TYPE_ADMIN);
        admin.setType(adminType);

        when(userRepository.save(admin)).thenReturn(admin);

        RedirectView redirectView = adminCont.saveAdmin(admin);
        assertNotNull(redirectView);
        assertEquals("/Admin/List-Admins", redirectView.getUrl());

        when(userRepository.findById(admin.getId())).thenReturn(Optional.of(admin));
        User savedUser = userRepository.findById(admin.getId()).orElse(null);
        assertNotNull(savedUser);
        assertEquals("Admin", savedUser.getFName());
        assertEquals("Admin1", savedUser.getLName());
    }

    @Test
    public void testEditAdmin() {
        // Step 1: Mock the original admin returned by findByid
        User originalAdmin = new User();
        originalAdmin.setId(1);
        originalAdmin.setFName("Admin1");
        originalAdmin.setLName("Admin1");
        originalAdmin.setEmail("Admin1.Admin1@gmail.com");
        originalAdmin.setPassword("password");

        // Mock findByid to return the originalAdmin
        when(userRepository.findByid(1)).thenReturn(originalAdmin);

        // Step 2: Prepare the updated admin details
        User updatedAdmin = new User();
        updatedAdmin.setFName("Admin");
        updatedAdmin.setLName("Admin");
        updatedAdmin.setEmail("Admin.Admin@gmail.com");
        updatedAdmin.setPassword("newpassword");

        // Mock the save method to return the updated admin
        when(userRepository.save(any(User.class))).thenReturn(updatedAdmin);

        // Step 4: Call the saveProfile method
        RedirectView redirectView = adminCont.saveAdminProfile(1, updatedAdmin);
        assertNotNull(redirectView);
        assertEquals("/Admin/List-Admins", redirectView.getUrl());

        // Step 5: Verify the updated user details
        User savedAdmin = userRepository.findByid(1);
        assertNotNull(savedAdmin);
        assertEquals("Admin", savedAdmin.getFName());
        assertEquals("Admin", savedAdmin.getLName());
        assertEquals("Admin.Admin@gmail.com", savedAdmin.getEmail());
    }

    @Test
    public void testDeleteAdminAccount() {
        User admin = new User();
        admin.setId(1);
        admin.setFName("Minna");
        admin.setLName("Hany");
        admin.setEmail("minna.hany@gmail.com");
        admin.setPassword("password");

        // Mock the findById method to return the admin
        when(userRepository.findById(1)).thenReturn(Optional.of(admin));

        RedirectView redirectView = adminCont.deleteAdminAccount(1);
        assertEquals("/Admin/List-Admins", redirectView.getUrl());

        // Mock the findById method to return empty after the user is deleted
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        User deletedAdmin = userRepository.findById(1).orElse(null);
        assertNull(deletedAdmin);
    }
 
   
   
    // test the product crud
    
    @Test
    public void testaddproduct(){
        ModelAndView mav = adminCont.createProduct();
        assertNotNull(mav);
        assertEquals("products/createProduct",  mav.getViewName());
        assertNotNull(mav.getModel().get("ProductDto"));

    }

   
// @Test
//     public void testUpdateProduct() {
//         // Step 1: Mock the original product returned by findById
//         Product originalProduct = new Product();
//         originalProduct.setId(1);
//         originalProduct.setName("Product1");
//         originalProduct.setBrand("Brand1");
//         originalProduct.setCategory("Category1");
//         originalProduct.setPrice(100);
//         originalProduct.setDescription("Description1");
//         originalProduct.setImgFileName("image1.jpg");

//         // Mock findById to return the originalProduct
//         productRepository mockProductRepository = mock(productRepository.class);
//         when(mockProductRepository.findById(1)).thenReturn(Optional.of(originalProduct));

//         // Step 2: Prepare the updated product details
//         productDto updatedProductDto = new productDto();
//         updatedProductDto.setName("Product");
//         updatedProductDto.setBrand("Brand");
//         updatedProductDto.setCategory("Category");
//         updatedProductDto.setPrice(200);
//         updatedProductDto.setDescription("Description");
//         updatedProductDto.setImagFile(null); // Mocking without changing image

//         // Mock the save method to return the updated product
//         when(mockProductRepository.save(any(Product.class))).thenReturn(originalProduct);

//         // Step 4: Call the updateProduct methodare
//         ProductController productController = new ProductController();
//         RedirectView redirectView = productController.updateProduct(productDto ,1, updatedProductDto);
//         assertNotNull(redirectView);
//         assertEquals("/products", redirectView.getUrl());

//         // Step 5: Verify the updated product details
//         Product savedProduct = mockProductRepository.findById(1).get();
//         assertNotNull(savedProduct);
//         assertEquals("Product", savedProduct.getName());
//         assertEquals("Brand", savedProduct.getBrand());
//         assertEquals("Category", savedProduct.getCategory());
//         assertEquals(200, savedProduct.getPrice());
//         assertEquals("Description", savedProduct.getDescription());
//         assertEquals("image1.jpg", savedProduct.getImgFileName());
//     }
   






}
