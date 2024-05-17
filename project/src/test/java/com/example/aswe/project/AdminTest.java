package com.example.aswe.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.aswe.project.controllers.adminController;
import com.example.aswe.project.models.User;
import com.example.aswe.project.repositories.UserRepository;
import com.example.aswe.project.repositories.adminRepository;

public class AdminTest {
    
    private adminRepository adminRepo = mock(adminRepository.class);
    private adminController adminCont = new adminController(adminRepo);

    private UserRepository userRepository;
    // @Mock
    // private adminRepository adminRepo = mock(adminRepository.class);;

    // @InjectMocks
    // private adminController adminCont = new adminController(adminRepo);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddUser() {
        ModelAndView mav = adminCont.addUser();
        assertNotNull(mav);        
        assertEquals("/html/admin/add-user.html", mav.getViewName());
        assertNotNull(mav.getModel().get("user"));
        // User user = (User) mav.getModel().get("user");
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setFName("Minna");
        user.setLName("Hany");
        user.setEmail("minna.hany@email.com");
        user.setPassword("password");

        RedirectView redirectView = adminCont.saveUser(user);
        assertNotNull(redirectView);
        assertEquals("/Admin/List-Users", redirectView.getUrl());

        // verify(adminRepo, ((adminRepository) times(1)).save(user));
        User savedUser = userRepository.findById(user.getId()).orElse(null);
        assertNotNull(savedUser);
        assertEquals("Minna", savedUser.getFName());
        assertEquals("Hany", savedUser.getLName());
    }

    @Test
    public void testEditUser() {
        User originalUser = new User();
        originalUser.setId(1);
        originalUser.setFName("Minna");
        originalUser.setLName("Hany");
        originalUser.setEmail("minna.hany@gmail.com");
        originalUser.setPassword("password");
        userRepository.save(originalUser);

        User updatedUser = new User();
        updatedUser.setFName("Sara");
        updatedUser.setLName("Hany");
        updatedUser.setEmail("sara.hany@gmail.com");
        updatedUser.setPassword("newpassword");

        RedirectView redirectView = adminCont.saveProfile(1, updatedUser);
        assertEquals("/Admin/List-Users", redirectView.getUrl());

        User savedUser = userRepository.findById(1).orElse(null);
        assertEquals("Sara", savedUser.getFName());
        assertEquals("Hany", savedUser.getLName());
        assertEquals("sara.hany@gmail.com", savedUser.getEmail());
    }

    // @Test 
    // public void testRemoveUser() {
    //     User user1 = new User();
    //     User user2 = new User();
    //     User user3 = new User();

    //     adminCont.addUser(user1);
    //     adminCont.deleteAccount(user1.getId());
    //     assertEquals(2, );
    // }

}
